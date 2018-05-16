package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 D 한강
 * 일렬로 집이 늘어서 있을때 맨 뒷집(N)을 기준으로 어떤 조건을 만족하는 수를 구하는 문제
 * 조건: N과 자신을 나눌 수 있는 수(약수)가 같은 수들 중에 막내(최소수)가 M 이상인 경우
 * 2013년 2월 23일 오후 1:17:22
 * 
 * 문제이해 8분, 해결방법 하루정도?, 코딩 및 제출 하루정도? 그리고.. 오답처리!
 * 시간을 많이 소비했지만 얻은 것도 꽤 있었다. 소수 및 소인수분해에 대해 생각해 볼 수 있었다. 그러나.. 반성할게 더 많다. 
 * 문제를 이해하고 나서 처음 떠올린 생각은 문제 그대로 푸는 것이었다.
 * small input의 최악의 케이스는 N:1000000, M:2 일 것이다.
 * N의 약수 개수를 계산하고, N미만의 수에 대하여 모두 약수개수를 계산하여 같은 경우 막내의 수가 M보다 크면 +1 하는 것이다.
 * 이 경우 N개에 대하여 각각 N/2번 확인해야 하므로 수행시간은 O(N^2) 가 된다. 즉, 불가능하다.
 * 실제로 문제풀던 시점에서는 이렇게 계산까지는 해보지 않고 막연하게 될 거라고만 생각했었다.
 * 어쨌든 이 방법으로 large output 을 계산할 수는 없기 때문에 바로 다른 방법을 고민해야 했다.
 * 
 * N의 약수는 몇개일까? N을 소인수분해하면 a1^b1 x a2^b2 x...x an^bn 이 된다. N의 모든 약수는 a1,..,an 으로 구성된다.
 * 따라서 N의 약수개수는 (b1+1)x(b2+1)x..x(bn+1) 이 된다. ( 1이 포함되지만 다른 수들의 약수개수와 비교할때 사용되므로 상관없다. )
 * 그렇다면 N 미만의 수를 소인수분해하여 위와 같은 방법으로 약수개수를 계산하면 될 것이다. 그러나.. 이것 또한 수행시간이 만만치 않다.
 * 각각의 수에 대하여 정답조건을 만족하는지 확인하려면 수행시간이 길어진다. 그렇다면 반대로 정답조건으로 수를 계산할 수는 없을까?
 * 그래서 생각한 것이, (b1+1)x..x(bn+1)를 ncnt 라 하고 정답을 만족시키는 수를 찾아봤다.
 * N의 여동생들을 c1^d1 x..x cm^dm 라고 할때 (d1+1)x..x(dm+1) == ncnt 를 만족하는 수를 찾아보자.
 * c1은 M이상의 최소소수가 되어야 한다. 그리고 모든 경우의 수를 생각해 보자.
 * c1 하나만 있는 경우(1<=d1<=max), c1,c2 두개인 경우(0<=d1<=max, 1<=d2<=max), ... 이런 식으로 소수를 하나씩 추가해 가면서 가능한 갯수를 계산했다.
 * c1,..,cm 라 할때 지수부는 d1,..,dm 이고, dm이 1이상이고 나머지는 0이상이 되는 경우 중 (d1+1)x..x(dm+1) == ncnt 를 만족하는 경우의 수를 구하는 것이다.
 * d1,..,d(m-1) 로 부분문제를 나눌 수 있다고 생각하여 재귀호출방식을 사용했고, 재귀호출될 경우는 dm이 0이상이면 되도록 해야했다.
 * 이 방식을 구현하기 위해서도 코드가 복잡해지는 바람에 코딩 및 디버깅에 상당한 시간을 소비해야 했다.
 * 그렇게 test output을 바르게 구하고 나서 small input에 대하여 실행하는데.. 이런.. 실행시간이 5분이 넘고 제출결과도 오답이었다..
 * 
 * 알고리즘 공부를 꽤 했는데 아직 문제를 푸는게 너무 서툴다. 무작정 공부에 시간만 소비한다고 느는게 아닌듯 하니 반성하는 시간을 가져야겠다.
 * 반성의 시간을 갖고나서, 실행시간이 길어진 원인과 오답이 된 원인을 찾아보자.
 * 
 * 현재 가장 문제가 되는 부분은 문제를 이해하고 나서 제출하는데까지 걸리는 시간이 너무 길다는 것이다.
 * 정답/오답 여부와 상관없이 해결방법을 생각하고 그것을 코딩하는데 너무 많은 시간이 소비된다. 왜 그런걸까? 
 * 연습장을 살펴봤다. 문제를 풀기 위해 정리해가는 과정에 큰 문제는 없어 보인다. 시행착오가 많지도 않다. 그러나 시간은 오래 걸렸다.
 * 그렇다면 문제는 집중력이다. 문제에 집중하지 못하고 다른 생각을 했다가 다시 문제로 돌아올때 생각하던 부분까지 찾아오는데 시간이 걸리는 것이다.
 * 그리고 연관되는 문제지만 기억력이 문제인 듯 하다. 다른 생각을 했다가 돌아올때 잘 기억이 나지 않는다.
 * 그리고 해결방법을 도출하기 위해 추론할때 이해가 되지 않아 진행이 잘 되지 않을때가 많다. 머릿속에서 생각의 데드락이 걸리는 경우다.
 * 각 문제점별로 조치방안을 생각해보자.
 * 1. 집중력 : 연습을 실전처럼. 시간을 정해놓고 다른 영향을 최대한 차단한 상태에서 문제를 풀자. 집중하기 어려운 환경에서 연습하면 집중력 향상에 도움이 될거라 생각했지만 오히려 시간낭비가 심한 듯 하다.
 *            그리고 문제는 small output 부터 최대한 빠른 시간안에 해결하고 large input에 대해 고민하는 방향으로 풀어보자.
 * 2. 기억력 : 단시간에 문제에 집중할 수 있다면 기억력 문제도 해소되지 않을까 기대한다.
 * 3. 생각의 데드락 : 알아볼 수 있게 계속 추론과정을 메모하는게 중요하다. 연습장을 아끼지 말고 계속 쓰는 습관을 기르자. 생각하고 쓰는게 아니라 쓰고나서 그것을 보며 생각하는 연습을 하자.
 */
public class CodeJamKorea2012_1_D_backup {
	
	private Map<String, Long> map;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out2.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			BigDecimal N = new BigDecimal(strs[0]);
			BigDecimal M = new BigDecimal(strs[1]);
			PrimeGen pg = new PrimeGen(10000);
			map = new HashMap<String, Long>();
			
			List<Num> nums = primeFactorize(N, M, pg);
			int ncnt = 1;
			for(Num num : nums){
				ncnt *= (num.power+1);
			}
			
			long sum = 0;
			if(ncnt > 2){
				
				PrimeGen powpg = new PrimeGen(100);
				List<Num> ncntNums = primeFactorize(new BigDecimal(ncnt), null, powpg);
				Num first = ncntNums.get(0);
				int powForPrime = (int)first.base - 1;
				int powForMin = (ncnt / (int)first.base) - 1;
			
				List<BigDecimal> list = new ArrayList<BigDecimal>();
				PrimeGen snapshot = pg.getSnapshot();
				BigDecimal prime;
				BigDecimal min = null;
				while((prime=snapshot.next()).compareTo(N) <= 0){
					
					// 비교되는 수는 ncnt에 따라 limit의 반보다 더 작아질 수 있다. prime이 조건만족 불가능한 수라면 break
					if(min == null) min = prime;
					if(N.divide(prime.pow(powForPrime), RoundingMode.FLOOR).compareTo(min.pow(powForMin)) < 0) break;
					
					list.add(prime);
					long cnt = getCount(N, list, ncnt, true);
					sum += cnt;
				}
			}
			
			String result = ""+sum;
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private List<Num> primeFactorize(BigDecimal n, BigDecimal m, PrimeGen pg){
		pg.setSnapshotStart(m);
		BigDecimal max = new BigDecimal((long)Math.pow(10, ((n.toString().length()+1) / 2)));
		List<Num> list = new ArrayList<Num>();
		BigDecimal prime = pg.next();
		BigDecimal one = new BigDecimal(1);
		BigDecimal zero = new BigDecimal(0);
		
		while(n.compareTo(one) > 0 && prime.compareTo(max) <= 0){
			if(n.remainder(prime).equals(zero)){
				Num num = new Num();
				num.base = prime.longValue();
				num.power = 0;
				while(n.remainder(prime).equals(zero)){
					n = n.divide(prime);
					num.power++;
				}
				list.add(num);
				max = new BigDecimal((long)Math.pow(10, ((n.toString().length()+1) / 2)));
			}
			prime = pg.next();
			if(prime.equals(zero)){
				System.out.println("prime is zero!!");
				System.exit(0);
			}
		}
		if(n.compareTo(one) > 0){
			Num num = new Num();
			num.base = n.longValue();
			num.power = 1;
			list.add(num);
		}
		return list;
	}
	
	private long getCount(BigDecimal limit, List<BigDecimal> list, int ncnt, boolean isFirst){
		if(ncnt == 1) return 1;
		if(ncnt == 0) return 0;
		
//		String key = getKey(limit, list.size(), ncnt);
//		long cached = 0;
//		if(isFirst == false && map.containsKey(key)){
//			System.out.println("cache! limit:"+limit+", list("+list.size()+"):"+list+", ncnt:"+ncnt+", result:"+map.get(key));
//			cached = map.get(key);
//			return map.get(key);
//		}
		
		BigDecimal lastc = list.get(list.size()-1);
		BigDecimal tmp = new BigDecimal(lastc.toString());
		int dmax = 0;
		while(tmp.compareTo(limit) < 0){
			tmp = tmp.multiply(lastc);
			dmax++;
		}
		if(list.size() == 1){
			long result = 0;
			if(dmax+1 >= ncnt) result = 1;
//			System.out.println("getCount( limit:"+limit+", list:"+list+", ncnt:"+ncnt+", isFirst:"+isFirst+" ) => result:"+result);
//			if(isFirst == false) map.put(key, result);
//			if(cached > 0 && cached != result) System.out.println("cache error! limit:"+limit+", list("+list.size()+"):"+list+", ncnt:"+ncnt+", results:"+result+","+cached); 
			return result;
		}
		
		long sum = 0;
		list.remove(list.size()-1);
		if(isFirst == false){
			for(int i=list.size()-1; i>=0; i--){
				BigDecimal last = list.get(i);
				if(limit.compareTo(last) > 0){
					sum += getCount(limit, list.subList(0, i+1), ncnt, false);
					break;
				}
			}
		}
		if(limit.compareTo(lastc) > 0){
			for(int i=1; i<=dmax; i++){
				if(ncnt % (i+1) == 0){
					BigDecimal divisor = lastc.pow(i);
					BigDecimal limit2 = limit.divide(divisor, RoundingMode.CEILING);
					int ncnt2 = ncnt;
					if(i > 0) ncnt2 = ncnt / (i+1);
					sum += getCount(limit2, list, ncnt2, false);
				}
			}
		}
		list.add(lastc);
//		System.out.println("getCount( limit:"+limit+", list:"+list+", ncnt:"+ncnt+", isFirst:"+isFirst+" ) => result:"+sum);
//		if(isFirst == false) map.put(key, sum);
//		if(cached > 0 && cached != sum) System.out.println("cache error! limit:"+limit+", list("+list.size()+"):"+list+", ncnt:"+ncnt+", results:"+sum+","+cached);
//		if(list.size() == 2 && ncnt == 2) System.out.println("!! limit:"+limit+", list("+list.size()+"):"+list+", ncnt:"+ncnt+", results:"+sum+","+cached);
		return sum;
	}
	
	private String getKey(BigDecimal limit, int size, int ncnt){
		return limit+","+size+","+ncnt;
	}
	
	private class Num {
		private long base;
		private long power;
	}
	
	private class PrimeGen {
		
		private BitSet sieve;
		private BigDecimal size;
		private BigDecimal last;
		private BigDecimal curr;
		private BigDecimal two = new BigDecimal(2);
		private BigDecimal now;
		private BigDecimal snapshotStart;
		private PrimeGen snapshot = null;

		public PrimeGen (int buffer) {
			size = new BigDecimal(buffer);
			last = new BigDecimal(buffer);
			curr = new BigDecimal(2);
			sieve = new BitSet ( buffer ) ;
			makeSieve(size);
		}
		
		public PrimeGen getSnapshot(){
			if(snapshot == null && snapshotStart != null){
				while(snapshotStart.compareTo(next()) > 0) break;
				setSnapshot();
			}
			return snapshot;
		}
		
		private void setSnapshot(){
			snapshot = new PrimeGen(size.intValue());
			snapshot.last = new BigDecimal(last.toString());
			snapshot.curr = new BigDecimal(now.toString());
			snapshot.now = now;
			snapshot.makeSieve(snapshot.last);
		}
		
		public void setSnapshotStart(BigDecimal snapshotStart){
			this.snapshotStart = snapshotStart;
		}
		
		private void expand(){
			last = last.add(size);
			makeSieve(last);
		}
		
		private void makeSieve(BigDecimal end){
			sieve.clear();
			for (BigDecimal i=new BigDecimal(3); i.pow(2).compareTo(end) <= 0; i=i.add(two)){

				// We increment by 2*i to skip even multiples of i
				BigDecimal slimit = end.subtract(size);
				BigDecimal multiple_i = i.pow(2);
				BigDecimal add = i.multiply(two);
				if(multiple_i.compareTo(slimit) < 0){
					multiple_i = multiple_i.add(slimit.subtract(multiple_i).divide(add, RoundingMode.CEILING).multiply(add));
				}
				for (; multiple_i.compareTo(end) < 0 ; multiple_i=multiple_i.add(add))
					setComposite ( multiple_i ) ;
			}
			
//			String str = "sieve("+sieve.length()+") :";
//			for(int i=0; i<sieve.length(); i++){
//				if(sieve.get(i)) str += i+", ";
//			}
//			System.out.println(str);
		}
		
		private int getLoopedIdx(BigDecimal idx){
			return idx.remainder(size).intValue();
		}
		 
		public boolean isComposite ( BigDecimal k )
		{
			int idx = getLoopedIdx(k);
			return sieve. get ( idx ) ;
		}
		 
		public void setComposite ( BigDecimal k )
		{
			int idx = getLoopedIdx(k);
			sieve. set ( idx ) ;
		}
		     
		public BigDecimal next(){
			if(curr.equals(two)){
				curr = new BigDecimal(3);
				now = new BigDecimal(2);
				if(snapshot == null && snapshotStart != null && snapshotStart.compareTo(two) <= 0) setSnapshot();
				return now;
			}
			for (BigDecimal i = new BigDecimal(curr.toString()) ; true ; i=i.add(two)){
				if(i.compareTo(last) >= 0) expand();
				if ( ! isComposite ( i ) ){
					curr = i.add(two);
					now = new BigDecimal(i.toString());
					if(snapshot == null && snapshotStart != null && snapshotStart.compareTo(i) <= 0) setSnapshot();
					return now;
				}
			}
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_D_backup().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	
	// ======  테스트 코드 =================================
	
	private void primeTest(){
		PrimeGen pg = new PrimeGen(20);
		PrimeGenOld pgold = new PrimeGenOld(1000000);
		for(int i=0; i<10000; i++){
			int p1 = pg.next().intValue();
			int p2 = pgold.next();
//			System.out.println("i:"+i+", p1:"+p1+", p2:"+p2);
			if(p1 != p2){
				System.out.println("diff!!");
				break;
			}
		}
	}
	
	private void primeFactorizeTest(){
		List<Num> list = primeFactorize(new BigDecimal("378"), null, new PrimeGen(10000)); 
		String str = "";
		for(Num num : list){
			str += num.base+"^"+num.power+" x ";
		}
		System.out.println(str);
	}
	
	private void primeCheckTest(){
		int a = 265371653;
		for(int i=3; i<265371653; i+=2){
			if(a % i == 0){
				System.out.println(a+" % "+i);
				break;
			}
		}
	}
	
	private void getCountTest(){
		BigDecimal limit = new BigDecimal("378");
		BigDecimal m = new BigDecimal("4");
//		BigDecimal limit = new BigDecimal("35");
//		BigDecimal m = new BigDecimal("2");
		PrimeGen pg = new PrimeGen(10000);
		map = new HashMap<String, Long>();
		
		List<Num> nums = primeFactorize(limit, m, pg);
		int ncnt = 1;
		for(Num num : nums) ncnt *= (num.power+1);
		PrimeGen sspg = pg.getSnapshot();
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		long sum = 0;
		
		if(ncnt > 2){
			
			PrimeGen powpg = new PrimeGen(100);
			List<Num> ncntNums = primeFactorize(new BigDecimal(ncnt), null, powpg);
			Num first = ncntNums.get(0);
			int powForPrime = (int)first.base - 1;
			int powForMin = (ncnt / (int)first.base) - 1;
			
			BigDecimal prime;
			BigDecimal min = null;
			while((prime=sspg.next()).compareTo(limit) < 0){
				
				// 비교되는 수는 ncnt에 따라 limit의 반보다 더 작아질 수 있다. prime이 조건만족 불가능한 수라면 break
				if(min == null) min = prime;
				if(limit.divide(prime.pow(powForPrime), RoundingMode.FLOOR).compareTo(min.pow(powForMin)) < 0) break;
				
				list.add(prime);
//				System.out.println("list:"+list.size()+", limit:"+limit+", prime:"+prime+"^"+powForPrime+", min:"+min+"^"+powForMin);
				long cnt = getCount(limit, list, ncnt, true);
				sum += cnt;
			}
		}
		
//		list.add(2);
//		list.add(3);
//		list.add(5);
//		list.add(7);
//		list.add(11);
//		sum = getCount(limit, list, ncnt, true);
		
		System.out.println("result: "+sum);
	}
	
	private void snapshotTest(){
		PrimeGen pg = new PrimeGen(1000000);
		pg.setSnapshotStart(new BigDecimal(5));
		
		for(int i=0; i<10; i++){
			System.out.println(pg.next());
		}
		
		PrimeGen snapshot = pg.getSnapshot();
		System.out.println("\n\n=============\n\n");
		for(int i=0; i<10; i++){
			System.out.println("ss: "+snapshot.next());
		}
	}
	
	private class PrimeGenOld {
		
		private BitSet sieve;
		private int size;
		private int curr = 2;
		private int prime;

		public PrimeGenOld ( int size ) {
			this.size = size;
			sieve = new BitSet ( ( size + 1 ) / 2 ) ;
			         
			for ( int i = 3 ; i * i <= size ; i += 2 ) {
				if ( isComposite ( i ) )
					continue ;
				 
				// We increment by 2*i to skip even multiples of i
				for ( int multiple_i = i * i ; multiple_i <= size ; multiple_i += 2 * i )
					setComposite ( multiple_i ) ;
			}
		}
		 
		public boolean isComposite ( int k )
		{
			assert k >= 3 && ( k % 2 ) == 1 ;
			return sieve. get ( ( k - 3 ) / 2 ) ;
		}
		 
		public void setComposite ( int k )
		{
			assert k >= 3 && ( k % 2 ) == 1 ;
			sieve. set ( ( k - 3 ) / 2 ) ;
		}
		     
		public int next(){
			if(curr == 2){
				curr = 3;
				return 2;
			}
			for ( int i = curr ; i <= size ; i += 2 ){
				if ( ! isComposite ( i ) ){
					curr = i+2;
					prime = i;
					return i;
				}
			}
			System.out.println("prime overflow !! last prime is "+prime);
			return 0;
		}
	}
}
