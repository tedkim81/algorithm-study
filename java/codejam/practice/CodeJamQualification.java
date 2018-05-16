package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CodeJamQualification {
	
	private static void test1_StoreCredit() throws Exception {
		// ready variables
		int numberOfCases;
		CaseItem[] caseItems;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		caseItems = new CaseItem[numberOfCases];
		
		for(int i=0; i<numberOfCases; i++){
			CaseItem ci = new CaseItem();
			ci.credit = Integer.valueOf(br.readLine());
			ci.itemCount = Integer.valueOf(br.readLine());
			
			String[] splited = br.readLine().split(" ");
			ci.itemArray = new int[ci.itemCount];
			for(int j=0; j<ci.itemCount; j++){
				ci.itemArray[j] = Integer.valueOf(splited[j]);
			}
			
			caseItems[i] = ci;
		}
		
		br.close();
		fr.close();
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			CaseItem ci = caseItems[i];
			ResultItem ri = new ResultItem();
			for(int j=0; j<ci.itemCount-1; j++){
				for(int k=j+1; k<ci.itemCount; k++){
					if(ci.itemArray[j]+ci.itemArray[k] == ci.credit){
						ri.sum = ci.itemArray[j]+ci.itemArray[k];
						ri.index1 = j+1;
						ri.index2 = k+1;
						ri.finished = true;
						break;
					}
					else if(ci.itemArray[j]+ci.itemArray[k] < ci.credit && ci.itemArray[j]+ci.itemArray[k] > ri.sum){
						ri.sum = ci.itemArray[j]+ci.itemArray[k];
						ri.index1 = j+1;
						ri.index2 = k+1;
					}
				}
				if(ri.finished == true) break;
			}
			fw.write(ri.getString(i+1)+"\n");
		}
		fw.close();
	}
	
	private static class CaseItem {
		public int credit;
		public int itemCount;
		public int[] itemArray;
	}
	
	private static class ResultItem {
		public int sum;
		public int index1;
		public int index2;
		public boolean finished = false;
		
		public String getString(int caseNumber){
			String result = "Case #"+caseNumber+": ";
			if(index1 < index2)
				result += index1+" "+index2;
			else
				result += index2+" "+index1;
			return result;
		}
	}
	
	private static void test2_ReverseWords() throws Exception {
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
			String[] splited = br.readLine().split(" ");
			String result = "Case #"+(i+1)+": ";
			for(int j=0; j<splited.length; j++){
				result += splited[splited.length-j-1]+" ";
			}
			result = result.substring(0, result.length()-1)+"\n";
			fw.write(result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static void test3_T9Spelling() throws Exception {
		// ready variables
		int numberOfCases;
		char lastPressedNumber = '.';  // excepted char
		Map<Character, String> spellMap = new HashMap<Character, String>();
		spellMap.put(' ', "0");
		spellMap.put('a', "2");
		spellMap.put('b', "22");
		spellMap.put('c', "222");
		spellMap.put('d', "3");
		spellMap.put('e', "33");
		spellMap.put('f', "333");
		spellMap.put('g', "4");
		spellMap.put('h', "44");
		spellMap.put('i', "444");
		spellMap.put('j', "5");
		spellMap.put('k', "55");
		spellMap.put('l', "555");
		spellMap.put('m', "6");
		spellMap.put('n', "66");
		spellMap.put('o', "666");
		spellMap.put('p', "7");
		spellMap.put('q', "77");
		spellMap.put('r', "777");
		spellMap.put('s', "7777");
		spellMap.put('t', "8");
		spellMap.put('u', "88");
		spellMap.put('v', "888");
		spellMap.put('w', "9");
		spellMap.put('x', "99");
		spellMap.put('y', "999");
		spellMap.put('z', "9999");
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			String str = br.readLine();
			String result = "Case #"+(i+1)+": ";
			for(int j=0; j<str.length(); j++){
				char ch = str.charAt(j);
				String keyStr = spellMap.get(ch);
				if(keyStr.charAt(0) == lastPressedNumber){
					result += " ";
				}
				result += keyStr;
				lastPressedNumber = keyStr.charAt(0);
			}
			result += "\n";
			fw.write(result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	/**
	 * 외계 언어(단어)에 대한 사전을 만들고 주어진 패턴에 해당하는 단어가 몇개인지를 찾는 문제
	 * 
	 * 패턴을 파싱하면서 동시에 단어 사전과 비교하는 부분에서 한가지 변수 초기화 구문을 누락하여 첫 1회 오답처리 되었다.
	 * 이러한 부분은 테스트 케이스를 적당히 뽑아 꼼꼼히 살펴보도록 하자.
	 */
	public static void test4_AlienLanguage() throws Exception{
		// ready variables
		int wordLength;
		int wordCount;
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		String[] strs = br.readLine().split(" ");
		wordLength = Integer.valueOf(strs[0]);
		wordCount = Integer.valueOf(strs[1]);
		numberOfCases = Integer.valueOf(strs[2]);
		
		List<String> wordList = new ArrayList<String>();
		for(int i=0; i<wordCount; i++){
			wordList.add(br.readLine());
		}
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			String str = br.readLine();
			int cnt = 0;
			for(String word : wordList){
				boolean inBracket = false;
				int idx = 0;
				boolean bracketPass = false;
				boolean isMatch = true;
				for(int j=0; j<str.length(); j++){
					char ch = str.charAt(j);
					if(ch == '('){
						inBracket = true;
						bracketPass = false;  // 사고지점: 이 구문을 넣지 않아서 첫번째 시도에 실패했다. 이런 실수를 줄여야 한다. ㅠ.ㅠ
					}
					else if(ch == ')'){
						if(bracketPass == false){
							isMatch = false;
							break;
						}
						inBracket = false;
						idx++;
					}
					else if(inBracket){
						if(word.charAt(idx) == ch)
							bracketPass = true;
					}
					else{
						if(word.charAt(idx) != ch){
							isMatch = false;
							break;
						}
						idx++;
					}
				}
				if(isMatch)
					cnt++;
			}
			
			String result = ""+cnt;
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	/**
	 * 배수지역 지도를 만드는 문제. 고도를 표현하는 지도를 기반으로 배수지역이 같은 곳을 같은 label로 표시한다.
	 * 
	 * 문제를 푸는데 있어 장애가 되는 상황은 없었으나 문제의 난이도에 비해 여전히 시간이 오래 걸린다.
	 * 약 2시간 정도 걸린듯 하다. 한 문제 푸는데 30분 내외가 되도록 연습해야 한다.
	 * 
	 * 시간 될때, simple brute-force simulation algorithm 학습하자.
	 */
	public static void test5_Watersheds() throws Exception{
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
			int h = Integer.parseInt(strs[0]);
			int w = Integer.parseInt(strs[1]);
			int[][] map = new int[h][w];
			
			for(int j=0; j<h; j++){
				String[] strs2 = br.readLine().split(" ");
				for(int k=0; k<w; k++){
					map[j][k] = Integer.parseInt(strs2[k]);
				}
			}
			
			char[][] resultMap = new char[h][w];
			char label = 'a' - 1;
			for(int j=0; j<h; j++){
				for(int k=0; k<w; k++){
					int jj = j;
					int kk = k;
					
					while(true){
						int north = 10000; if(jj > 0) north = map[jj-1][kk];
						int west = 10000; if(kk > 0) west = map[jj][kk-1];
						int east = 10000; if(kk < w-1) east = map[jj][kk+1];
						int south = 10000; if(jj < h-1) south = map[jj+1][kk];
						int curr = map[jj][kk];
						
						char way = ' ';
						if(curr > north){
							way = 'n';
							curr = north;
						}
						if(curr > west){
							way = 'w';
							curr = west;
						}
						if(curr > east){
							way = 'e';
							curr = east;
						}
						if(curr > south){
							way = 's';
							curr = south;
						}
						
						if(way == ' '){
							if(resultMap[jj][kk] == 0){
								label++;
								resultMap[jj][kk] = label;
							}
							resultMap[j][k] = resultMap[jj][kk];
							break;
						}
						else if(way == 'n'){
							jj--;
						}
						else if(way == 'w'){
							kk--;
						}
						else if(way == 'e'){
							kk++;
						}
						else if(way == 's'){
							jj++;
						}
					}
				}
			}
			
			fw.write("Case #"+(i+1)+":\n");
			for(int j=0; j<h; j++){
				String result = "";
				for(int k=0; k<w; k++){
					result += resultMap[j][k]+" ";
				}
				result += "\n";
				fw.write(result);
			}
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	/**
	 * 주어진 텍스트에서 "welcome to code jam" 을 만들 수 있는 경우의 수를 구하는 문제 ( 끝 4자리 )
	 * 순서 변경 없이 공백문자 포함하여 텍스트의 글자들로 문구를 완성해야 한다.
	 * 
	 * 문제를 이해하는데 몇시간이 걸렸고, 문제해결방법을 찾는데도 몇시간이 걸렸다.
	 * 참가자 중 qualification round 에서 10점 이상 받은자가 8300명 정도인데 이 중 2400명 정도가 제한시간안에 만점을 받았다.
	 * top 25 에 들려면 3문제 모두 1시간 안에 해결해야 한다.
	 * 문제 해결 방법을 찾는 것은 물론 연습 및 전략에 따라 시간을 단축할 수도 있긴 하겠지만 그래도 머리탓으로 돌릴 수도 있다.
	 * 그러나 문제를 이해하는 시간과 코딩 시간은 연습으로 충분히 단축시킬 수 있다. 노력하자!
	 */
	public static void test6_WelcomeToCodeJam() throws Exception{
		// ready variables
		int numberOfCases;
		String msg = "welcome to code jam";
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String txt = br.readLine();
			int[] arr = new int[txt.length()];
			char ch = msg.charAt(0);
			for(int j=0; j<txt.length(); j++){
				if(txt.charAt(j) == ch){
					arr[j] = 1;
				}
				else{
					arr[j] = 0;
				}
			}
			int r = 0;
			for(int j=1; j<msg.length(); j++){
				char ch2 = ch;
				ch = msg.charAt(j);
				int t = 0;
				r = 0;
				for(int k=0; k<txt.length(); k++){
					char ch3 = txt.charAt(k);
					if(ch3 == ch2){
						t += arr[k];
					}
					else if(ch3 == ch){
						arr[k] = t % 10000;
						r += arr[k];
					}
				}
			}
			r %= 10000;
			String result;
			if(r < 10) result = "000"+r;
			else if(r < 100) result = "00"+r;
			else if(r < 1000) result = "0"+r;
			else result = ""+r;
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		try{
			
			test6_WelcomeToCodeJam();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
