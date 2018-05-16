package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2012 : Problem B. Dancing With the Googlers
 * N명이 3개씩의 점수를 받는데 가장큰 점수가 p이상인 사람수의 최대값을 구하는 문제
 * 
 * 문제를 이해하고 정답을 도출하는데에 약 1시간반 정도가 걸렸다. 결과적으로 참 쉬운 문제였는데 빨리 해결하지 못한 것이 너무 아쉽다.
 * 문제 자체가 다소 독특하다 보니 문제를 이해하는데 약 20분 정도 걸렸다. 이건 뭐.. 별다른 방법이 없다. 영어 학습만이 살길..
 * 문제를 너무 얕보고 바로 코딩으로 들어간 것이 시간을 오히려 더 지연시켰다. 부분문제를 정의하고 재귀호출 메소드 형태와 매개변수를 어떻게 할지 결정하는 절차를 반드시 거치자.
 */
public class CodeJam2012_Q_B {
	
	private int N,S,p;
	private int[] t;
	private int[][][] memo;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/B-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			S = sc.nextInt();
			p = sc.nextInt();
			t = new int[N];
			memo = new int[N][N+1][S+1];
			for(int i=0; i<N; i++){
				t[i] = sc.nextInt();
			}
			
			String result = ""+getMax(0, 0, S);
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getMax(int index, int sum, int remain){
		if(index == N) return sum;
		
		if(memo[index][sum][remain] > 0) return memo[index][sum][remain];
		
		int result = 0;
		if(remain > 0){
			int t1 = t[index] / 3;
			int t2 = t[index] % 3;
			if(t1 > 0 && t2 == 0) t1 += 1;
			else t1 += t2;
			int add = 0;
			if(t1 >= p) add = 1;
			result = getMax(index+1, sum+add, remain-1);
		}
		if(N-index > remain){
			int t1 = t[index] / 3;
			int t2 = t[index] % 3;
			if(t2 > 0) t1 += 1;
			int add = 0;
			if(t1 >= p) add = 1;
			result = Math.max(result, getMax(index+1, sum+add, remain));
		}
		memo[index][sum][remain] = result;
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2012_Q_B().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	
	/**********************
	 * code for debugging *
	 **********************/
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			print("exit: "+log);
			System.exit(0);
		}
	}
	
	public static void print(String str){
		System.out.println(str);
	}
	
	public static void print(int[] arr){
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
