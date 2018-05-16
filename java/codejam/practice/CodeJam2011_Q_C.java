package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2011 : Problem C. Candy Splitting
 * 번호가 매겨진 사탕들이 있을때 번호의 합이 같도록 형제가 둘로 나눠 갖는데 동생의 덧셈방식이 적용될 경우 형이 가질 수 있는 사탕의 최대합을 구하는 문제
 * 
 * 문제이해 27분, 해결방법 96분, 코딩 10분. 그리고 정답!
 * 문제를 이해하는데도 상당히 오래 걸렸고, 해결방법을 고민하는데도 매우 오래 걸렸다. 그러나 해결방법은 의외로 간단했다.
 * 모든 C[]의 xor결과가 0이 되어야만 한다는 것을 빨리 생각할 수만 있었다면 상당히 빠른 시간에 해결할 수 있었을 것이다.
 * 간단한 상황을 가정하여 시뮬레이션 해보는 습관을 가질 필요가 있다. 그랬다면 핵심이 되는 통찰을 빨리 구했을 것이다.
 */
public class CodeJam2011_Q_C {
	
	private int N;
	private int[] C;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/C-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			C = new int[N];
			for(int i=0; i<N; i++) C[i] = sc.nextInt();
			
			String result = getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String getResult(){
		int babysum = 0;
		int min = Integer.MAX_VALUE;
		int sum = 0;
		for(int i=0; i<N; i++){
			babysum = babysum ^ C[i];
			if(C[i] < min) min = C[i];
			sum += C[i];
		}
		if(babysum != 0) return "NO";
		
		return ""+(sum-min);
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2011_Q_C().goodluck();
			
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
