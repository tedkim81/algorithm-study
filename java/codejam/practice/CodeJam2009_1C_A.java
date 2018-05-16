package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Round 1C 2009 : Problem A. All Your Base
 * 2013년 1월 30일 오후 6:23:46
 * 외계인의 단어를 해석하는 문제이다. 단어는 전쟁까지의 남은 시간(seconds) 을 나타낸다. 각각의 char는 unique한 수를 의미한다.
 * 
 * 매우 쉬운 문제였다. 문제이해 10분, 해결방법 5분, 코딩 35분이 걸렸다. 코딩시간이 다소 길게 나온게 아쉬운 부분이다.
 * 그러나 한번 틀렸다. input과 output을 보며 이상한 부분을 찾아보니 111111111 가 9라고 되어있었다.
 * 1진법은 사용하지 않는다는 가정을 깜박했었던 것이다. 문제에서 제시하는 전제를 잘 요약해두고 잊지 않도록 해야겠다.
 */
public class CodeJam2009_1C_A {
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String str = br.readLine();
			Map<Character, Integer> map = new HashMap<Character, Integer>();
			for(int j=0; j<str.length(); j++){
				char ch = str.charAt(j);
				if(map.containsKey(ch) == false)
					map.put(ch, -1);
			}
			int a = map.size(); // 문자의 unique count, 진법
			if(a == 1) a = 2;
			int last = 1;
			boolean didZeroMapped = false;
			BigDecimal result = new BigDecimal(0);
			for(int j=0; j<str.length(); j++){
				char ch = str.charAt(j);
				int num = map.get(ch);
				if(num < 0){
					if(j > 0 && didZeroMapped == false){
						map.put(ch, 0);
						num = 0;
						didZeroMapped = true;
					}
					else{
						map.put(ch, last);
						num = last;
						last++;
					}
				}
				result = result.add(power(a, str.length()-j-1).multiply(new BigDecimal(num)));
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private BigDecimal power(int a, int b){
		BigDecimal result = new BigDecimal(1);
		BigDecimal mul = new BigDecimal(a);
		for(int i=0; i<b; i++){
			result = result.multiply(mul);
		}
		return result;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJam2009_1C_A().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
