package gcj.y2013.round1b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem C. Garbled Email
 * 
 * 왠지 풀 수 있을듯한 문제였으나 시간에 쫓겨 결국은 시도도 못해보고 타임아웃 당했다.
 * 따로 풀어봤는데 오답처리가 되었고 원인을 확인해보니.. 두가지 문제가 있었다.
 * 1. 알고리즘의 수행시간에 문제가 있었다. solve() 는 solve2() 에 비해 10배 느리다.
 * 2. 문제를 잘못 이해했다. 잘못된 단어수를 찾는것이 아니고 잘못된 글자수를 찾는것이 문제였다.
 * 
 * TODO: solve2()는 large input을 해결하지 못한다. 추후 analysis를 확인한 후 다시 풀어보자.
 */
public class C_todo {
	
	private String[] dic;
	private String S;
	private int MIN = -5;
	private int MAX = 5000;
	private int[][] memo;
	
	private void loadfile() throws Exception {
		dic = new String[521196];
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/garbled_email_dictionary.txt";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		for(int i=0; i<521196; i++){
			dic[i] = br.readLine();
		}
		br.close();
		fr.close();
	}
	
	private void goodluck() throws Exception {
		loadfile();
		
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"C-small-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			S = sc.next();
			memo = new int[S.length()][S.length()-MIN];
			for(int i=0; i<S.length(); i++) Arrays.fill(memo[i], -1);
			
			String result = ""+solve2(0, MIN);
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int solve2(int idx, int fidx){
		if(idx == S.length()) return 0;
		if(memo[idx][fidx-MIN] >= 0) return memo[idx][fidx-MIN];
		
		int result = 987654321;
		for(String dicword : dic){
			if(dicword.length() > S.length()-idx) continue;
			int last = fidx;
			int wrongCnt = 0;
			for(int i=0; i<dicword.length(); i++){
				if(dicword.charAt(i) != S.charAt(idx+i)){
					if(idx+i - last >= 5){
						last = idx+i;
						wrongCnt++;
					}
					else{
						last = MAX;
						break;
					}
				}
			}
			if(last < S.length()){
				result = Math.min(result, wrongCnt+solve2(idx+dicword.length(), last));
			}
		}
		memo[idx][fidx-MIN] = result;
		return result;
	}
	
	/*
	private int solve(int idx, int fidx){
		if(idx == S.length()) return 0;
		if(memo[idx][fidx+maxsize] >= 0) return memo[idx][fidx+maxsize];
		
		int result = Integer.MAX_VALUE;
		for(int i=idx; i<S.length(); i++){
			String s = S.substring(idx, i+1);
			if(s.length() > maxsize) break;
			int matchResult = match(s, fidx);
			if(matchResult == MAX) continue;
			
			int wrongword = 1;
			if(matchResult == MIN) wrongword = 0;
			int nextfidx = MIN;
			if(matchResult >= 0 && matchResult < s.length()) nextfidx = matchResult - s.length();
			
			int tmp = solve(i+1, nextfidx);
			if(tmp == Integer.MAX_VALUE) continue;
			tmp += wrongword;
			
			result = Math.min(result, tmp);
		}
		memo[idx][fidx+maxsize] = result;
		return result;
	}
	
	private int match(String str, int fidx){
		int result = MAX;
		for(String dicstr : dic){
			if(dicstr.length() != str.length()) continue;
			int last = fidx;
			for(int j=0; j<str.length(); j++){
				if(dicstr.charAt(j) != str.charAt(j)){
					if(j-5 >= last){
						last = j;
					}
					else{
						last = MAX;
						break;
					}
				}
			}
			if(last < str.length()) result = Math.min(result, last);
		}
		return result;
	}
	*/
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C_todo().goodluck();
//			new C().test();
			
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
