package gcj.y2012.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem A. Password Problem
 * 문제를 읽고 해석하는데 상당히 오래 걸렸고, 문제를 푸는데까지 1시간이 걸렸다.
 * 전체 2시간반중에 가장 쉬운문제에 1시간이나 소비한다는 것은 문제가 있다. 영어 공부를 많이 해야할 듯 하다.
 */
public class A {
	
	private int A,B;
	private double[] P,pmul;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/Documents/workspace/android-src/CodeJam/src/A-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/Documents/workspace/android-src/CodeJam/src/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			A = sc.nextInt();
			B = sc.nextInt();
			P = new double[A];
			for(int i=0; i<A; i++) P[i] = sc.nextDouble();
			pmul = new double[A];
			pmul[0] = P[0];
			for(int i=1; i<A; i++) pmul[i] = pmul[i-1] * P[i];
			
			String result = String.format("%.6f", getResult());
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private double getResult(){
		double result = 0;
		
		// 끝까지 입력
		int a = B-A+1;
		double p = 1 - pmul[A-1];
		result = a + (p * (B+1));
		
		// 백스페이스를 i번 입력
		for(int i=1; i<=A; i++){
			a = B-A+1+(2*i);
			if(A-1-i >= 0) p = 1 - pmul[A-1-i];
			else p = 1;
			result = Math.min(result, (a + (p * (B+1))));
		}
		
		// 엔터 바로 입력
		result = Math.min(result, B+2);
		
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new A().goodluck();
			
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
