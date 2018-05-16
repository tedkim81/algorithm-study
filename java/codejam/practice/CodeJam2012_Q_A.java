package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Qualification Round 2012 : Problem A. Speaking in Tongues
 * 주어진 글자 매핑을 이용하여 Googlerese 라는 가상의 언어로 만들어진 문장을 원문으로 번역하는 문제.
 * 
 * 예제에 주어진 문장 3개를 이용해 map을 만들면 띄어쓰기 포함하여 25개가 나온다. 즉 2개가 빈다.
 * q와 z였는데 별 생각없이 q->q, z->z 로 매핑한 것이 실수가 되어 첫 시도를 실패했다.
 * 지문을 다시 읽어보니 지문에서 이미 3개의 매핑을 알려주고 있었다.
 * 지문을 꼼꼼히 읽는 습관을 들여야 한다..
 */
public class CodeJam2012_Q_A {
	
	private Map<Character, Character> map;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		setMap();
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			String str = br.readLine();
			String result = "";
			for(int i=0; i<str.length(); i++){
				result += map.get(str.charAt(i));
			}
			
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private void setMap(){
		String[] a = {
			"ejp mysljylc kd kxveddknmc re jsicpdrysi",
			"rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd",
			"de kr kd eoya kw aej tysr re ujdr lkgc jv"
		};
		String[] b = {
			"our language is impossible to understand",
			"there are twenty six factorial possibilities",
			"so it is okay if you want to just give up"
		};
		map = new HashMap<Character, Character>();
		for(int i=0; i<3; i++){
			for(int j=0; j<a[i].length(); j++){
				map.put(a[i].charAt(j), b[i].charAt(j));
			}
		}
		map.put('q', 'z');
		map.put('z', 'q');
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2012_Q_A().goodluck();
			
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
