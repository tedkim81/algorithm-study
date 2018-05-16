package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 D 창문 깨기
 * K개의 창문에 H개가 1번 강화되어있고, M명이 한번씩 강화했을때 N명이 돌을 하나씩 던져서 창문이 1개 이상 깨질 확률을 구하는 문제
 * 2013년 3월 17일 오전 1:51:30
 * 
 * 문제이해 6분, 해결방법 3시간 23분, 코딩 3시간, 그리고 실패..
 * 오랜시간 투자하여 test output은 제대로 만들었으나, small output에서 1이 넘는 수들이 출력되었다.
 * 왜 이럴까? calc()에서 zeroCnt 넘기는 부분에 오류가 있었고 이를 수정한 후에는 1이 넘는수가 나오지는 않았다.
 * 그러나 정답과 비교해보니 상당한 차이가 있었고 다른 참가자의 solution을 확인해 봤다.
 * 확실한 차이점 하나는 창문이 최소 한개 깨질 확률을 바로 구하지 않고 창문이 깨지지 않을 확률을 구한다는 것이었다.
 * 그리고 double을 곱하는 과정에서 범위를 벗어나는 문제가 발생했다.
 * 코드잼 코리아에서는 해결방법을 제공해 주지 않기 때문에 타참가자의 소스를 보고 이해해야 하는데 이번 문제의 소스들은 이해하기가 너무 난해했다.
 * 
 * TODO: 계속 시간을 소비할 수 없으니 일단은 넘어가도록 하자. 추후 반드시 다시 풀어봐야할 문제이다.
 * 문제가 풀리지 않는다면 othersolution2 를 참조하자.
 */
public class CodeJamKorea2012_F_D_todo {
	
	private int K,N,M,H;
	private int[] window,window2;
	private Map<String, String> dpMap, dpMap2;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			String[] strs = br.readLine().split(" ");
			K = Integer.parseInt(strs[0]);
			N = Integer.parseInt(strs[1]);
			M = Integer.parseInt(strs[2]);
			H = Integer.parseInt(strs[3]);
			window = new int[K];
			window2 = new int[K];
			dpMap = new HashMap<String, String>();
			dpMap2 = new HashMap<String, String>();
			for(int i=0; i<K; i++){
				if(i<H) window[i] = 1;
				else window[i] = 0;
			}
			
			String result = String.format("%.8f", solve(0).getValue());
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			System.out.println("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private FractNum solve(int addCnt){
		if(addCnt == M){
			int zeroCnt = 0;
			for(int i=0; i < K; i++){
				window2[i] = window[K-1-i];
				if(window2[i] == 0) zeroCnt++;
			}
			return calc(N, zeroCnt);
		}
		
		String key = getWindowKey(window, addCnt);
		if(dpMap.containsKey(key)){
			return new FractNum(dpMap.get(key));
		}
		
		FractNum result = new FractNum(0, 1);
		for(int i=0; i<K; i++){
			if(i == 0 || window[i-1] != window[i]){
				int sameCnt = 0;
				for(int j=i; j<K; j++){
					if(window[j] != window[i]) break;
					sameCnt++;
				}
				window[i]++;
				FractNum fn = solve(addCnt+1);
				fn.mult(sameCnt, 1);
				result.add(fn);
				window[i]--;
				i += sameCnt-1;
			}
		}
		result.mult(1, K);
		dpMap.put(key, result.getKey());
		return result;
	}
	
	private String getWindowKey(int[] arr, int num){
		String str = "";
		for(int i=0; i<arr.length; i++) str += arr[i]+",";
		str += num;
		return str;
	}
	
	private FractNum calc(int n, int zeroCnt){
		if(n == 1){
			return new FractNum(zeroCnt, K);
		}
		
		String key = getWindowKey(window2, n);
		if(dpMap2.containsKey(key)){
			return new FractNum(dpMap2.get(key));
		}
		
		FractNum result = new FractNum(0, 1);
		for(int i=0; i<K; i++){
			if(i == 0 || window2[i-1] != window2[i]){
				int sameCnt = 0;
				for(int j=i; j<K; j++){
					if(window2[j] != window2[i]) break;
					sameCnt++;
				}
				if(window2[i] == 0) result.add(sameCnt, K);
				else{
					window2[i]--;
					FractNum calcfn;
					if(window2[i] == 0)
						calcfn = calc(n-1, zeroCnt+1);
					else
						calcfn = calc(n-1, zeroCnt);
					calcfn.mult(sameCnt, K);
					result.add(calcfn);
					window2[i]++;
				}
				if(window2[i] >= n) break;
				i += sameCnt-1;
			}
		}
		dpMap2.put(key, result.getKey());
		return result;
	}
	
	private class FractNum {
		private long numerator;
		private long denominator;
		
		public FractNum(int nume, int deno){
			numerator = (long)(nume);
			denominator = (long)(deno);
		}
		
		public FractNum(String key){
			String[] strs = key.split(",");
			numerator = Long.parseLong(strs[0]);
			denominator = Long.parseLong(strs[1]);
		}
		
		public void mult(int nume, int deno){
			numerator *= (long)(nume);
			denominator *= (long)(deno);
		}
		
		public void add(int nume, int deno){
			numerator = numerator*(long)deno + (long)nume*denominator;
			denominator *= (long)deno;
		}
		
		public void add(FractNum fn){
			numerator = numerator*fn.denominator + fn.numerator*denominator;
			denominator *= fn.denominator;
		}
		
		public double getValue(){
			return numerator / (double)denominator;
		}
		
		public String getKey(){
			return numerator+","+denominator;
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_F_D_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void test(){
//		K=3; N=2; M=1; H=0;
//		window2 = new int[K];
//		window2[0]=0; window2[1]=0; window2[2]=1;
//		FractNum fn = calc(N, 2);
//		print("result: "+fn.getValue());
		
		FractNum fn = new FractNum(2, 3);
		fn.add(3, 9);
		print("result: "+fn.getKey());
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
	
	public void print(int[] arr){
		if(arr == null) print("null");
		else{
			String str = "[";
			if(arr.length > 0){
				for(int i=0; i<arr.length; i++) str += arr[i]+",";
				str = str.substring(0, str.length()-1);
			}
			str += "]";
			print(str);
		}
	}
}
