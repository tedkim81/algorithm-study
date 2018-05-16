package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 D 한강
 * 
 * 문제를 다시 생각해보고 문제 그대로 생각하여 small output을 구했고 정답이었다.
 * 그러나 수행시간이 너무 길었다. 약 7분정도 걸렸다. 실전에서는 문제가 될만한 상황이다.
 * 일단 수행시간이 왜 길어졌는지 확인해보고 현재 알고리즘에서 dynamic programming으로 단출시킬 수 있는지 확인해 봤다.
 * 수행시간 지연 : getCount()의 수행시간이 N.sqrt 인데 답의 수만큼은 호출을 해야하고 답의 수는 N에 비례하므로 수행시간은 O(Nx(N.sqrt)) 라 할 수 있다.
 * 결과 중 큰값이 대략 200000 정도고 N.sqrt는 1000 이하가 되므로 2x10^8 만큼 계산이 필요하다는 결론이 나온다. 케이스가 1000개니까 7분이라는 시간이 납득이 된다.
 * dynamic programming으로 해결 가능한가? : 재귀호출이 없고 같은 값이 반복되는 케이스가 적기 때문에 오히려 메모리 사용 측면에서 독이 된다.
 * 
 * 답의 수만큼 getCount()를 호출하는것은 실행시간이 길어진다. 그렇다면 조건만족하는 경우를 찾았을때 다른 경우들을 더 찾을 수는 없을까?
 * CodeJamKorea2012_1_D_backup 에서 사용했던 방법과 유사한 방법을 사용했다. 
 * M <= C < N 인 소수 (c1, c2, ..., cm) 을 구하고, (c1,..), (c2,..),.. 에 대해서 가능한 경우의 수들을 구하여 그 합을 구하는 방법이다.
 * 이전(_backup)의 ci가 없을경우와 있을경우를 구분하다 보니 코드가 복잡해졌고, 없는경우에 m만큼 call stack이 차야하기 때문에 stack overflow가 나는 문제가 있었다.
 * 그러나 현재 방법은 getCount에서 ci가 1이상인 경우에 대해서만 처리하고 반복문을 적절하게 사용함으로써 위의 문제를 피해갈 수 있었다.
 * 현재 알고리즘으로 small output은 정상적으로 구할 수 있다.
 * 
 * TODO: 이번 문제에 너무 많은 시간을 소비했다. large input에 대해서는 다음번에 다시 시도해 보자. 
 */
public class CodeJamKorea2012_1_D_todo {
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/D-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			int N = Integer.parseInt(strs[0]);
			int M = Integer.parseInt(strs[1]);
			
			String result = ""+getResult(N, M);
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private List<Integer> clist;
	
	private int getResult(int N, int M){
		clist = new ArrayList<Integer>();
		
		PrimeGen pg = new PrimeGen(10000);
		int prime;
		while((prime=pg.next()) > 0){
			if(prime < M) continue;
			if(prime >= N) break;
			clist.add(prime);
		}
		
		int max = (int)Math.sqrt(N);
		int ncnt = 0;
		for(int i=2; i<=max; i++){
			if(N % i == 0){
				if(i == N/i) ncnt++;
				else ncnt+=2;
			}
		}
//		print("clist first:"+clist.get(0)+", size:"+clist.size()+", ncnt:"+ncnt);
		
		int sum = 0;
		if(ncnt > 0){
			ncnt += 2;
			for(int i=0; i<clist.size(); i++){
				int cnt = getCount(N, ncnt, i);
				if(cnt == 0) break;
				sum += cnt;
			}
		}
		
		return sum;
	}
	
	private int getCount(int limit, int ncnt, int i){
		int prime = clist.get(i);
		long tmp = prime;
		int dmax = 0;
		while(tmp < limit){
			tmp *= prime;
			dmax++;
		}
//		String log = "limit:"+limit+", ncnt:"+ncnt+", i:"+i+", prime:"+prime+", dmax:"+dmax;
//		if(dmax == 0) { System.out.println(log+", result:0"); }
		if(dmax == 0) return 0;
//		if(ncnt == 1 || ncnt == 2) { System.out.println(log+", result:1"); }
		if(ncnt == 1 || ncnt == 2) return 1;
		
		int cnt = 0;
		for(int j=1; j<=dmax; j++){
			if(ncnt % (j+1) > 0) continue;
			int nextLimit = (int)Math.ceil(limit / Math.pow(prime, j));
			int nextNcnt = ncnt / (j+1);
			if(nextNcnt == 1){
				cnt++;
				break;
			}
			for(int k=i+1; k<clist.size(); k++){
				if(nextLimit <= clist.get(k)) break;
				cnt += getCount(nextLimit, nextNcnt, k);
			}
		}
//		System.out.println(log+", result:"+cnt);
		return cnt;
	}

	private class PrimeGen {
		
		private BitSet sieve;
		private int size;
		private int last;
		private int curr;
		private int now;

		public PrimeGen (int buffer) {
			size = buffer;
			last = buffer;
			curr = 2;
			sieve = new BitSet ( buffer ) ;
			makeSieve(size);
		}
		
		private void expand(){
			last += size;
			makeSieve(last);
		}
		
		private void makeSieve(int end){
			sieve.clear();
			for (int i=3; i*i <= end; i+=2){

				// We increment by 2*i to skip even multiples of i
				int slimit = end-size;
				int multiple_i = i*i;
				int add = i*2;
				if(multiple_i < slimit){
					multiple_i = multiple_i+( (int)Math.ceil((slimit-multiple_i) / (double)add) * add );
				}
				for (; multiple_i < end ; multiple_i+=add)
					setComposite ( multiple_i ) ;
			}
		}
		
		private int getLoopedIdx(int idx){
			return idx % size;
		}
		 
		public boolean isComposite ( int k )
		{
			int idx = getLoopedIdx(k);
			return sieve. get ( idx ) ;
		}
		 
		public void setComposite ( int k )
		{
			int idx = getLoopedIdx(k);
			sieve. set ( idx ) ;
		}
		     
		public int next(){
			if(curr == 2){
				curr = 3;
				now = 2;
				return now;
			}
			for (int i=curr ; true ; i+=2){
				if(i >= last) expand();
				if ( ! isComposite ( i ) ){
					curr = i+2;
					now = i;
					return now;
				}
			}
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_D_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void check(boolean isRight, String log){
		System.out.println(log);
		if(isRight == false){
			System.out.println("exit!");
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
	
	private void primeTest(){
		PrimeGen pg = new PrimeGen(10000);
		for(int i=0; i<100; i++){
			System.out.println(pg.next());
		}
	}
	
	private void getResultTest(){
		int result = getResult(404994, 13);
		System.out.println("result:" +result);
	}
	
	private void getCountTest(){
		int N = 404994;
		int M = 13;
		int ncnt = 6;
		
		clist = new ArrayList<Integer>();
		PrimeGen pg = new PrimeGen(10000);
		int prime;
		while((prime=pg.next()) > 0){
			if(prime < M) continue;
			if(prime >= N) break;
			clist.add(prime);
		}
		int i = 34091;
//		clist = clist.subList(0, 3);
		int cnt = getCount(N, ncnt, i);
		System.out.println("count: "+cnt);
	}
	
	
	/* 정답은 맞았으나 수행시간이 7분 걸린 코드 
	private int getResult(int N, int M){
		boolean[] checked = new boolean[N];
		int ncnt = getCount(N, 2);
		int result = 0;
		if(ncnt > 0){
			for(int j=M; j<N; j++){
				if(checked[j]) continue;
				int cnt = getCount(j,M);
				if(cnt == ncnt){
					result++;
				}
				if(cnt >= ncnt){
					for(int k=1; k*j<N; k++) checked[k*j] = true;
				}
			}
		}
//		print("ncnt:"+ncnt+", result:"+result);
		return result;
	}
	
	private int getCount(int n, int m){
		int max = (int)Math.sqrt(n);
		int ncnt = 0;
		for(int i=2; i<=max; i++){
			if(n % i == 0){
				if(i >= m){
					if(i == n/i) ncnt++;
					else ncnt+=2;
				}
				else{
					return 0;
				}
			}
		}
//		System.out.println("n:"+n+", m:"+m+", ncnt:"+ncnt);
		return ncnt;
	}
	*/
}
