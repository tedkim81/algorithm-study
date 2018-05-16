package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 A 가로수
 * N그루의 가로수를 세우는데 M종류의 막대를 써서 지지를 해야한다고 할때 지지력 총합의 최소값을 구하는 문제
 * 2013년 3월 9일 오후 5:43:04
 * 
 * 문제이해 5분, 해결방법 44분, 코딩 22분. 그리고 또 실패!
 * 아직 갈길이 멀긴 하지만 첫제출까지 71분으로 나름 뿌듯했지만 결국은 또 오답처리 되었다.
 * 나무에 지지대를 세우는 경우들에 대하여 순서는 중요하지 않다. 각 나무 지지대의 지지력합이 B와 가장 가까운 수가 되도록 찾아나가면 될 것이라고 생각했다.
 * 가장 B에 가까운 쌍 (Pi, Pj)가 있을때 (Pi, Pj)를 사용하지 않고 최소값이 될 수 있을지를 생각해보고 그런 경우는 없다는 것을 증명해 봤다. ( 귀류법 )
 * 만약, 최소가 되는 지지대쌍들이 있다고 할 때, (Pi, Pj) 가 남아있다면 임의의 한쌍과 바꿔서 값이 작아지기 때문에 최소가 된다는 결과에 모순이 된다.
 * (Pi, Pj)가 남아있지 않다면 사용된 지지대 집합에 Pi, Pj가 모두 쓰였다는 것이고 Pi는 Pj보다 큰값과 쌍인 상태, Pj는 Pi보다 큰값과 쌍인 상태다.
 * 그렇다면 두쌍에서 Pi와 Pj가 쌍이 되도록 교환을 하면 나머지 한쌍은 (Pi, Pj)보다 큰 값이 되므로 교환이 가능하고, 그렇다면 (Pi, Pj)를 사용하지 않는다는 조건에 모순이 된다.
 * 따라서 현재 상태에서 가장 B에 가까운 (Pi, Pj)를 구하여 지지대로 사용하면 그 결과값이 최소값이 된다고 생각했다.
 * 
 * 과연 현재 알고리즘의 문제는 뭘까? 정답을 구하여 비교해보자.
 * test input에서는 발견할 수 없는 오류가 있었다. 최소값이 되는 (Pi, Pj) 를 찾는 과정에서 (Pi, Pi) 인 경우에 대하여 판단하고 나서 continue하여 다음 루프를 돌도록 한 것이 문제였다.
 * 해당 부분 수정후 small output을 제대로 구할 수 있었다. 이런 실수를 줄이려면 어떻게 해야할까? 집중력을 키우고 좀더 신중해지도록 노력하자.
 * 
 * 이제 large output을 구해보자.
 * 현재 알고리즘으로는 수행시간이 너무 오래 걸린다. O(NM^2) 이고 10^11 정도가 되니 불가능하다.
 * 정답을 구하기 위해 참고했던 타참가자의 소스를 보고 해결 가능한 방식을 알게 되었다.
 * 매번 최소가 되는 (Pi, Pj)를 찾는 것이 아니라, 가능한 (Pi, Pj)를 모두 구하고 정렬한 후 사용하는 것이다.
 * 이로써 large output 도 구할 수 있었다.
 * 
 * 이번 문제는 상당히 쉬운 문제였다. 쉬운 문제는 쉽게 풀 수 있는 경지에 이르러야 한다.
 */
public class CodeJamKorea2012_F_A {
	
	private int N,M,B;
	private int[] P,Q;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			N = Integer.parseInt(strs[0]);
			M = Integer.parseInt(strs[1]);
			B = Integer.parseInt(strs[2]);
			P = new int[M];
			Q = new int[M];
			for(int j=0; j<M; j++){
				strs = br.readLine().split(" ");
				P[j] = Integer.parseInt(strs[0]);
				Q[j] = Integer.parseInt(strs[1]);
			}
			
			String result = ""+getCount2();
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int getCount(){
		int cnt = 0;
		for(int i=0; i<N; i++){
			
			int minj = -1;
			int mink = -1;
			int minb = Integer.MAX_VALUE;
			for(int j=0; j<M; j++){
				if(Q[j] == 0) continue;
				
				if(P[j] >= B && P[j] < minb && Q[j] > 0){
					minj = j;  mink = -1;
					minb = P[j];
					continue;
				}
				else if(P[j]+P[j] >= B && P[j]+P[j] < minb && Q[j] > 1){
					minj = j;  mink = j;
					minb = P[j]+P[j];
//					continue;  여기가 문제였다!!
				}
				for(int k=j+1; k<M; k++){
					if(Q[k] == 0) continue;
					
					if(P[j]+P[k] >= B && P[j]+P[k] < minb && Q[j] > 0 && Q[k] > 0){
						minj = j;  mink = k;
						minb = P[j]+P[k];
					}
				}
			}
			if(minj == -1 && mink == -1) return -1;
			
			cnt += minb;
			if(minj != -1) Q[minj]--;
			if(mink != -1) Q[mink]--;
			
		}
		return cnt;
	}
	
	private int getCount2(){
		TreeSet<PQ> pqset = new TreeSet<PQ>();
		for(int i=0; i<M; i++){
			if(P[i] >= B){
				pqset.add(new PQ(i, -1, P[i]));
			}
			for(int j=i; j<M; j++){
				if(P[i]+P[j] >= B){
					pqset.add(new PQ(i, j, P[i]+P[j]));
				}
			}
		}
		
		int cnt = 0;
		Iterator<PQ> it = pqset.iterator();
		PQ pq = null;
		boolean pqChange = true;
		while(N > 0){
			if(pqChange){
				if(it.hasNext()){ pq = it.next(); pqChange = false; }
				else{ cnt = -1; break; }
			}
			if(pq.j == -1){
				if(Q[pq.i] > 0){
					cnt += pq.b;
					Q[pq.i]--;
					N--;
				}
				else{
					pqChange = true;
				}
			}
			else{
				if((pq.i == pq.j && Q[pq.i] > 1)
						|| (pq.i != pq.j && Q[pq.i] > 0 && Q[pq.j] > 0)){
					cnt += pq.b;
					Q[pq.i]--;
					Q[pq.j]--;
					N--;
				}
				else{
					pqChange = true;
				}
			}
		}
		return cnt;
	}
	
	private class PQ implements Comparable<PQ> {
		private int i,j,b;
		public PQ(int i, int j, int b){
			this.i = i;  this.j = j;  this.b = b;
		}
		@Override
		public int compareTo(PQ o) {
			if(this.b == o.b){
				return this.i - o.i;
			}
			return this.b - o.b;
		}
		@Override
		public String toString() {
			return "("+i+","+j+","+b+")";
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_F_A().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			System.out.println("exit: "+log);
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
}
