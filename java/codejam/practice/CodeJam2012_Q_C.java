package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2012 : Problem C. Recycled Numbers
 * A,B 가 주어질때 A <= n < m <= B 만족하는 m이 n의 recycled number 인 경우의 수를 구하는 문제
 * recycled number는 10진수를 좌로 shift 한 경우를 말한다. 예를 들면, 34512는 12345의 recycled number이다.
 * 2013년 4월 2일 오전 11:56:49
 * 
 * 문제이해 5분, 해결방법 7분, 코딩 및 제출에 20분. 총 32분 사용하여 small input은 제대로 풀었으나 large input은 오답 처리 되었다.
 * 뭐가 잘못됐을까? 지문을 끝까지 읽고 확인했어야 했다. 너무 확신해 차서 test input을 제대로 실행시켜보지 않은 것도 문제였다.
 * 문제 지문의 하단에 보면 4번째 케이스가 287이 맞다는 것을 강조하고 있다. 실행시켜보니 288이 나온다. 뭔가가 중복된다는 뜻일 것이다.
 * 디버깅 하는데 25분이 걸렸고 총 시간은 57분이 된다. 아직도 길다. 쉬운 문제는 최대한 빨리 풀 수 있도록 노력하자.
 */
public class CodeJam2012_Q_C {
	
	private int A,B;
	
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
			
			A = sc.nextInt();
			B = sc.nextInt();
			
			String result = ""+getCnt();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getCnt(){
		int cnt = 0;
		for(int i=A; i<B; i++){
			int num = (""+i).length() - 1;
			int shifted = i;
			int lastnum;
			int pow = (int)Math.pow(10, num);
			int[] log = new int[num];
			for(int j=0; j<num; j++){
				lastnum = shifted % 10;
				shifted = (shifted/10) + (lastnum * pow);
				if(lastnum > 0 && shifted > i && shifted <= B){
					boolean dup = false;
					for(int k=0; k<num; k++) 
						if(log[k] == shifted) {
							dup = true;
							break;
						}
					if(dup == false){
						log[j] = shifted;
						cnt++;
					}
				}
			}
		}
		return cnt;
	}
	
	private void test(){
		A = 1111; B = 2222;
		print("cnt : "+getCnt());
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2012_Q_C().goodluck();
			
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
