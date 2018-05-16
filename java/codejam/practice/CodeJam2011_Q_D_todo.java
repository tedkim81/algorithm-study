package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2011 : Problem D. GoroSort
 * 순열을 무식하게 정렬하는데 일부는 손으로 잡고있고 나머지는 바닥을 쳐서 무작위로 섞이게 하는 방식을 쓸때 바닥을 몇번 칠지에 대한 기대값을 구하는 문제
 * 
 * 문제이해 16분, 해결방법 32분, 코딩 26분, 그리고 오답!
 * 내가 생각한 것은, 서로 섞여있는 수가 1인 경우(제자리에 있는 경우)는 0, 섞여있는 수가 2인 경우 2, 3인 경우 4, 4인 경우 6... 이렇게 된다고 생각했다.
 * 2 3 1 인 경우를 가정하면, 하나를 잡고 나머지 2개에 대해서는 2번 치면 하나가 제자리를 찾고 나머지 2개에 대하여 2번을 치면 되기 때문에 4가 된다.
 * 다른 경우들도 귀납적으로 적용하여 섞여있는 수를 i라 하면 바닥을 치는 경우의 수는 (i-1)*2 가 된다고 생각했다.
 * 그러나 이를 구현한 결과가 오답이었고 결과를 확인해봤을때 코드상의 오류는 없어보였다.
 * 
 * 그래서 Contest Analysis를 확인했다.
 * 정답을 구하는 과정은 상당히 간단했다. 주어진 순열에서 정렬된 위치에 있지 않은 수의 갯수를 구하면 되는 것이었다. ( 소수점 이하까지 구하라고 한 것은 fake였다. )
 * 2 3 1 인 경우에 4번이 아니라 3번이었던 것이다. 왜 3이 되는지 계산해보자.
 * 2 3 1 을 섞을 경우 가능한 경우들은, (1 2 3), (1 3 2), (2 1 3), (2 3 1), (3 1 2), (3 2 1) 로 총 6개이다.
 * 이 경우들을 가지고 정렬된 상태가 될때까지 몇번이나 바닥을 쳐야할지 계산해보자. ( 2개를 정렬할 경우 2번친다는 것은 이미 계산되었다고 가정한다. 같은 방식으로 계산 가능하다. )
 * (1/6)*1 + (3/6)*2 + (2/6)*(x+1) = x 이 식을 풀면 x=3 이다. 그리고 4개,5개,..인 경우에 대해서도 이와 같이 구할 수 있다.
 * 따라서 정렬되지 않은 수의 갯수가 바닥을 치는 기대값과 같다고 할 수 있는 것이다. 
 * 
 * TODO:
 * 위와 같은 방식은 수학적으로는 완벽하지 않은 증명이다. 심증을 얻어내는 역할만 했을 뿐이다.
 * Contest Analysis에서의 증명과정을 이해하도록 하자.
 */
public class CodeJam2011_Q_D_todo {
	
	private int N;
	private int[] m;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			m = new int[N+1];
			for(int i=1; i<=N; i++){
				m[i] = sc.nextInt();
			}
			
			String result = String.format("%.6f", getResult2());
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private double getResult(){
		double result = 0;
		for(int i=1; i<=N; i++){
			for(int j=1; j<=N; j++){
				int num = 1;
				int idx = j;
				while(m[idx] > 0 && m[idx] != j){
					idx = m[idx];
					num++;
				}
				if(num == i){
					result += (i-1) * 2 / (double)num;
				}
			}
		}
		return result;
	}
	
	private double getResult2(){
		double result = 0;
		for(int i=1; i<=N; i++){
			if(m[i] != i) result++;
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2011_Q_D_todo().goodluck();
			
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
