package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2011 : Problem A. Bot Trust
 * O,B 로봇이 1~100 의 버튼을 주어진 순서대로 누를 경우 걸리는 시간을 구하는 문제
 * 
 * 문제이해 11분, 해결방법 13분, 코딩 53분. 코딩할때 변수의 의미를 혼동하여 시간이 상당히 걸렸다.
 * R,P,O,B 의 index는 단순히 index일뿐인데 버튼의 번호와 매칭된다고 착각하여 잘못 코딩했었다.
 */
public class CodeJam2011_Q_A {
	
	private int N;
	private char[] R;
	private int[] P,O,B;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/A-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			R = new char[N];
			P = new int[N];
			O = new int[N];
			B = new int[N];
			int oidx = 0;
			int bidx = 0;
			for(int i=0; i<N; i++){
				R[i] = sc.next().charAt(0);
				P[i] = sc.nextInt();
				if(R[i] == 'O') O[oidx++] = P[i];
				else B[bidx++] = P[i];
			}
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getResult(){
		int result = 0;
		int oidx = 0;
		int bidx = 0;
		int obtn = 1;
		int bbtn = 1;
		for(int i=0; i<N; i++){
			while(true){
				result++;
				boolean needBreak = false;
				if(R[i] == 'O'){
					if(obtn == P[i]){
						needBreak = true;
						oidx++;
					}
					else if(obtn < P[i]) obtn++;
					else obtn--;
					if(B[bidx] > 0){
						if(bbtn < B[bidx]) bbtn++;
						else if(bbtn > B[bidx]) bbtn--;
					}
				}
				else{
					if(bbtn == P[i]){
						needBreak = true;
						bidx++;
					}
					else if(bbtn < P[i]) bbtn++;
					else bbtn--;
					if(O[oidx] > 0){
						if(obtn < O[oidx]) obtn++;
						else if(obtn > O[oidx]) obtn--;
					}
				}
				
				if(needBreak) break;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2011_Q_A().goodluck();
			
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
