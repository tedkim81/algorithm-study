package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Round 1C 2010 : Problem B. Load Testing
 * 2013년 1월 7일 오후 2:55:23
 * 올해 사용자수를 L이라하고, 내년 예측사용자수를 P라 할때, L 지원가능하고 P 지원되지 않는 것을 알고 있다.
 * 내년 사용자에 대비하기 위해 장비를 몇대 늘릴지 정해야 하기 때문에 현재 서버가 얼마나 버티는지를 확인해 봐야 한다.
 * a <= X < a*C 를 만족하는 a를 구하기 위해 최악의 상황에 최소 몇번의 테스트를 해야할지는 구하는 문제이다.
 * 
 * 문제 자체를 이해하기가 굉장히 어려웠던 문제이다. 문제를 이해하는데 약 2시간 정도 걸린듯 하다.
 * 이 부분의 개선방법은 연습 및 언어 학습 이외의 다른 방법이 없을 듯 하다.
 * 그러나 binary search 와 같은 알고리즘들이 몸에 베어 있었다면 문제에서 요구하는 바를 좀더 일찍 알수도 있지 않았을까 하는 생각이 든다.
 * 
 * 문제를 이해하고 나서 small 에 대해서는 성공했지만, large 에서 실패했고 결국은 답안을 확인했다. 
 * 원인을 파악하기 위해 참가자들 중 정답자들의 소스를 통해 정답 출력물을 구하고 비교를 해본 결과 몇몇 케이스에서 1만큼이 컸다.
 * 문제풀이의 컨셉은 이해를 했으나 방법을 잘못 잡았다. P를 계속 C로 나누는 과정으로 테스트할 숫자를 정하도록 했는데 
 * 이때 발생하는 소수점 이하 숫자들을 올림처리하면서 경계에서의 결과가 잘못 나왔던 것이다.
 * L*C 에서 C를 곱하는 방식으로 바꾸니 정확한 결과가 도출되었다.
 * 
 * 여기서도 이전과 마찬가지의 교훈을 얻을 수 있다.
 * 소수점이 발생할때 단순히 올림처리 해버린것이 실패의 원인이었다. 여기서 이상하다는 생각을 했다면 L*C 에서 시작하자는 생각으로 이어졌을 수 있다.
 */
public class CodeJam2010_1C_B {
	
	private static void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/B-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			long l = Long.parseLong(strs[0]);
			long p = Long.parseLong(strs[1]);
			long c = Long.parseLong(strs[2]);
			int t1 = 0;
			long t2 = p;
			while(t2 > l*c){
				t1++;
				t2 = (long)Math.ceil(t2/(float)c);
			}
			int result = 0;
			while(t1 > 0){
				result++;
				t1 /= 2;
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void test(){
		long l = 553;
		long p = 216015625;
		long c = 5;
		int t1 = 0;
		long t2 = p;
		while(t2 > l*c){
			t1++;
			t2 = (long)Math.ceil(t2/(float)c);
			System.out.println("t2: "+t2);
		}
		int result = 0;
		while(t1 > 0){
			result++;
			t1 /= 2;
		}
		
		System.out.println("Case #test: "+result+"\n");
	}
	
	private static void goodluck2() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/B-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			long l = Long.parseLong(strs[0]);
			long p = Long.parseLong(strs[1]);
			long c = Long.parseLong(strs[2]);
			int t1 = 0;
			long t2 = l*c;
			while(t2 < p){
				t1++;
				t2 = t2 * c;
			}
			int result = 0;
			while(t1 > 0){
				result++;
				t1 /= 2;
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		try{
			
			goodluck2();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
