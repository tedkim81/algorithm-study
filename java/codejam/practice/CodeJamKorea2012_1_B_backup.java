package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 B 장터판
 * NxM 격자 게임판에 K면 다면체가 고정되었거나 그렇지 않은 상태로 있고 게임판을 흔들어서 각 영역에 대하여 연속4인지 연속3인지 연속2인지에 따라 점수를 계산한다고 할때
 * 그 점수의 총합의 기대값을 구하는 문제이다.
 * 2013년 2월 15일 오후 4:48:53
 * 
 * 문제이해 45분, 해결방법 4시간 2분, 코딩에 4시간.. 다시말해 하루 종일 매달렸고, 해결방법의 오류를 발견하여 중단하고 말았다.
 * 먼저 완전탐색을 적용하면 ?에 모든 경우를 대입하여 계산결과합을 경우의 수로 나누면 될 것이라 생각했다.
 * 이 경우 small output은 구할 수 있겠으나 large output은 구할 수가 없었다.
 * 
 * 1. large input에서 worst case는 100x100, all ? mark, K=9 이다.
 * 2. 전체합에 대한 기대값은 각 구간 기대값의 합과 같다.
 * 3. 연속 4개까지만 확인하면 되기 때문에 구하고자 하는 구간을 중심으로 하는 7x7 영역으로 범위를 좁힐 수 있다.
 * 4. 가로, 세로, 대각선2방향 총 4방향은 각각 독립적이라고 전제하고 각 방향에 대하여 기대값을 각각 구한후 4방향중 최대값이 그 구간의 기대값이다.
 * 위 내용들을 기반으로 하여 알고리즘을 작성했다. 그러나 4번이 문제였다. 4번에 오류가 있다는 것을 미리 검증하지 못해서 결국 하루를 통째로 날린 것이다.
 * 각 방향은 서로 독립적이지 않다. 각 방향에서 x점이 나올 확률이 p로 같다고 할 경우 전체에 대하여 x점이 나올 확률은 p보다 큰값이 된다.
 * 따라서 어떤 구간의 기대값을 구하기 위해서는 4방향 모두를 고려해야 한다.
 * 
 * 이 파일은 backup 파일로 남겨두도록 한다.
 * 문제의 해결방법을 다시 찾아 다시 풀어보도록 하자.
 */
public class CodeJamKorea2012_1_B_backup {
	
	private char[][] map;
	private Map<String, Integer> dp;
	private int N;
	private int M;
	private int K;
	private int S4;
	private int S3;
	private int S2;
	private char Ki;
	private char[] arr;
	private int center = 3;
	
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
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			N = Integer.parseInt(strs[0]);
			M = Integer.parseInt(strs[1]);
			K = Integer.parseInt(strs[2]);
			S4 = Integer.parseInt(strs[3]);
			S3 = Integer.parseInt(strs[4]);
			S2 = Integer.parseInt(strs[5]);
			map = new char[N][M];
			dp = new HashMap<String, Integer>();
			
			for(int ni=0; ni<N; ni++){
				String str = br.readLine();
				for(int mi=0; mi<M; mi++){
					map[ni][mi] = str.charAt(mi);
				}
			}
			
			Rat ret = null;
			for(int ni=0; ni<N; ni++){
				for(int mi=0; mi<M; mi++){
					Ki = map[ni][mi];
					
					char arrA[] = new char[7];
					arrA[center] = Ki;
					for(int j=1; j<4; j++){
						if(mi-j < 0) arrA[center-j] = '.';
						else arrA[center-j] = map[ni][mi-j];
					}
					for(int j=1; j<4; j++){
						if(mi+j >= M) arrA[center+j] = '.';
						else arrA[center+j] = map[ni][mi+j];
					}
					
					char arrB[] = new char[7];
					arrB[center] = Ki;
					for(int j=1; j<4; j++){
						if(ni-j < 0) arrB[center-j] = '.';
						else arrB[center-j] = map[ni-j][mi];
					}
					for(int j=1; j<4; j++){
						if(ni+j >= N) arrB[center+j] = '.';
						else arrB[center+j] = map[ni+j][mi];
					}
					
					char arrC[] = new char[7];
					arrC[center] = Ki;
					for(int j=1; j<4; j++){
						if(mi-j < 0 || ni-j < 0) arrC[center-j] = '.';
						else arrC[center-j] = map[ni-j][mi-j];
					}
					for(int j=1; j<4; j++){
						if(mi+j >= M || ni+j >= N) arrC[center+j] = '.';
						else arrC[center+j] = map[ni+j][mi+j];
					}
					
					char arrD[] = new char[7];
					arrD[center] = Ki;
					for(int j=1; j<4; j++){
						if(mi-j < 0 || ni+j >= N) arrD[center-j] = '.';
						else arrD[center-j] = map[ni+j][mi-j];
					}
					for(int j=1; j<4; j++){
						if(mi+j >= M || ni-j < 0) arrD[center+j] = '.';
						else arrD[center+j] = map[ni-j][mi+j];
					}
					
					Rat retMax = null;
					Rat retA = null;
					Rat retB = null;
					Rat retC = null;
					Rat retD = null; 
					if(Ki == '?'){
						arr = arrA;
						for(Ki='1'; Ki<='9'; Ki++) retA = sum(retA, getValue(center-1, 0, 0, false));
						retMax = bigger(retMax, retA);
						arr = arrB;
						for(Ki='1'; Ki<='9'; Ki++) retB = sum(retB, getValue(center-1, 0, 0, false));
						retMax = bigger(retMax, retB);
						arr = arrC;
						for(Ki='1'; Ki<='9'; Ki++) retC = sum(retC, getValue(center-1, 0, 0, false));
						retMax = bigger(retMax, retC);
						arr = arrD;
						for(Ki='1'; Ki<='9'; Ki++) retD = sum(retD, getValue(center-1, 0, 0, false));
						retMax = bigger(retMax, retD);
					}
					else{
						arr = arrA; retA = getValue(center-1, 0, 0, false); retMax = bigger(retMax, retA);
						arr = arrB; retB = getValue(center-1, 0, 0, false); retMax = bigger(retMax, retB);
						arr = arrC; retC = getValue(center-1, 0, 0, false); retMax = bigger(retMax, retC);
						arr = arrD; retD = getValue(center-1, 0, 0, false); retMax = bigger(retMax, retD);
					}
					
					print("arrA : ", arrA);
					System.out.println("retA("+ni+","+mi+"): "+retA);
					print("arrB : ", arrB);
					System.out.println("retB("+ni+","+mi+"): "+retB);
					print("arrC : ", arrC);
					System.out.println("retC("+ni+","+mi+"): "+retC);
					print("arrD : ", arrD);
					System.out.println("retD("+ni+","+mi+"): "+retD);
					System.out.println("retMax("+ni+","+mi+"): "+retMax);
					ret = sum(ret, retMax);
				}
			}
			
			String result = ""+ret.getDouble();
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private class Rat {
		private double numerator;
		private double denominator;
		
		public Rat(double nume, double deno){
			this.numerator = nume;
			this.denominator = deno;
		}
		
		public double getDouble(){
			return numerator / denominator;
		}

		@Override
		public String toString() {
			return "(nume:"+(int)numerator+", deno:"+(int)denominator+")";
		}
	}
	
	private Rat getValue(int idx, int cnt, int notCnt, boolean isEnd){
		System.out.println("idx:"+idx+", cnt:"+cnt+", notCnt:"+notCnt);
		if(cnt == 4 || isEnd){
			int point = 0;
			if(cnt == 3) point = S4;
			else if(cnt == 2) point = S3;
			else if(cnt == 1) point = S2;
			
			double nume = point * Math.pow((K-1), notCnt);
			double deno = Math.pow(K, (cnt+notCnt));
			
			Rat r = new Rat(nume, deno);
			System.out.println("K:"+K+", Rat:"+r);
			return r;
		}
		
		if(arr[idx] == '?'){
			if(idx < center){
				Rat v1 = getValue(idx-1, cnt+1, notCnt, false);
				Rat v2 = getValue(center+1, cnt, notCnt+1, false);
				return sum(v1, v2);
			}
			else{
				Rat v1 = getValue(idx+1, cnt+1, notCnt, false);
				Rat v2 = getValue(idx+1, cnt, notCnt+1, true);
				return sum(v1, v2);
			}
		}
		else if(arr[idx] == '.'){
			if(idx < center){
				return getValue(center+1, cnt, notCnt, false);
			}
			else{
				return getValue(idx+1, cnt, notCnt, true);
			}
		}
		else{
			if(idx < center){
				if(arr[idx] == Ki){
					return getValue(idx-1, cnt+1, notCnt, false);
				}
				else{
					return getValue(center+1, cnt, notCnt+1, false);
				}
			}
			else{
				if(arr[idx] == Ki){
					return getValue(idx+1, cnt+1, notCnt, false);
				}
				else{
					return getValue(idx+1, cnt, notCnt+1, true);
				}
			}
		}
	}
	
	private Rat sum(Rat a, Rat b){
		System.out.println("sum: a="+a+", b="+b);
		if(a == null) return b;
		if(b == null) return a;
		if(a.denominator > b.denominator){
			double ratio = a.denominator / b.denominator;
			return new Rat(a.numerator+(b.numerator*ratio), a.denominator);
		}
		else{
			double ratio = b.denominator / a.denominator;
			return new Rat(b.numerator+(a.numerator*ratio), b.denominator);
		}
	}
	
	private Rat bigger(Rat a, Rat b){
		if(a == null) return b;
		if(b == null) return a;
		if(a.denominator > b.denominator){
			double ratio = a.denominator / b.denominator;
			if(a.numerator > (b.numerator*ratio)) return a;
			else return b;
		}
		else{
			double ratio = b.denominator / a.denominator;
			if(b.numerator > (a.numerator*ratio)) return b;
			else return a;
		}
	}
	
	private void print(String pre, char[] arr){
		String str = "[";
		for(int i=0; i<arr.length; i++){
			str += arr[i]+",";
		}
		str = str.substring(0, str.length()-1)+"]";
		System.out.println(str);
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_B_backup().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
