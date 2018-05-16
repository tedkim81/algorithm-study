package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Qualification Round 2011 : Problem B. Magicka
 * Magicka라는 게임을 컨셉으로 한 문제로, 주어진 문자열에서 char하나씩 element list에 넣을때 combine 또는 delete 한 최종 결과를 구하는 문제
 * 
 * 문제이해 22분, 해결방법 7분, 코딩 110분, 그리고 오답!
 * test input이 부실하여 문제를 잘못 이해하고서도 test output을 제대로 출력하는 오답을 여러차례 만들었다.
 * 결국은 Contest Analysis 를 확인해 봤고, opposed 상태인 경우 element list를 "완전히" 비워야 한다는 것을 깨달았다.
 */
public class CodeJam2011_Q_B {
	
	private int C,D,N;
	private String[] Cstrs,Dstrs;
	private String Nstr;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/B-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			C = sc.nextInt();
			Cstrs = new String[C];
			for(int i=0; i<C; i++){
				Cstrs[i] = sc.next();
			}
			D = sc.nextInt();
			Dstrs = new String[D];
			for(int i=0; i<D; i++){
				Dstrs[i] = sc.next();
			}
			N = sc.nextInt();
			Nstr = sc.next();
			
			String result = getResult3().toString();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private List<Character> getResult(){
		List<Character> result = new ArrayList<Character>();
		for(int s=0; s<N; s++){
			boolean combined = false;
			boolean deleted = false;
			for(int e=s+1; e<N; e++){
				char sch = Nstr.charAt(s);
				char ech = Nstr.charAt(e);
				char echBefore = Nstr.charAt(e-1);
				for(int i=0; i<C; i++){
					if((echBefore == Cstrs[i].charAt(0) && ech == Cstrs[i].charAt(1))
							|| (echBefore == Cstrs[i].charAt(1) && ech == Cstrs[i].charAt(0))){
						for(int j=s; j<e-1; j++) result.add(Nstr.charAt(j));
						result.add(Cstrs[i].charAt(2));
						combined = true;
						break;
					}
				}
				if(combined){
					s = e;
					break;
				}
				else{
					for(int i=0; i<D; i++){
						if((sch == Dstrs[i].charAt(0) && ech == Dstrs[i].charAt(1))
								|| (sch == Dstrs[i].charAt(1) && ech == Dstrs[i].charAt(0))){
							deleted = true;
							break;
						}
					}
					if(deleted){
						s = e;
						break;
					}
				}
			}
			if(combined == false && deleted == false){
				result.add(Nstr.charAt(s));
			}
		}
		return result;
	}
	
	private List<Character> getResult2(){  
		List<Character> result = new ArrayList<Character>();
		for(int s=0; s<N; s++){
			char sch = Nstr.charAt(s);
			if(s == N-1){
				result.add(sch);
				break;
			}
			char schNext = Nstr.charAt(s+1);
			boolean combined = canCombine(sch, schNext, result);
			if(combined){
				s++;
				continue;
			}
			
			int laste = s;
			boolean deleted = false;
			boolean stop = false;
			int e=s+1;
			for(; e<N; e++){
				char ech = Nstr.charAt(e);
				char echBefore = Nstr.charAt(e-1);
				if(canCombine(echBefore, ech, null)){
					stop = true;
					break;
				}
				for(int i=0; i<D; i++){
					if((sch == Dstrs[i].charAt(0) && ech == Dstrs[i].charAt(1))
							|| (sch == Dstrs[i].charAt(1) && ech == Dstrs[i].charAt(0))){
						laste = e;
						deleted = true;
					}
				}
			}
			if(combined == false && deleted == false){
				result.add(sch);
			}
			else if(deleted){
				s = laste;
			}
			else if(stop){
				s = e-1;
			}
		}
		return result;
	}
	
	private boolean canCombine(char sch, char schNext, List<Character> result){
		for(int i=0; i<C; i++){
			if((sch == Cstrs[i].charAt(0) && schNext == Cstrs[i].charAt(1))
					|| (sch == Cstrs[i].charAt(1) && schNext == Cstrs[i].charAt(0))){
				if(result != null) result.add(Cstrs[i].charAt(2));
				return true;
			}
		}
		return false;
	}
	
	private List<String> getResult3(){
		List<String> result = new ArrayList<String>();
		Map<String, String> cmap = new HashMap<String, String>();
		for(int i=0; i<C; i++){
			char c1 = Cstrs[i].charAt(0);
			char c2 = Cstrs[i].charAt(1);
			char c3 = Cstrs[i].charAt(2);
			cmap.put(c1+""+c2, ""+c3);
			cmap.put(c2+""+c1, ""+c3);
		}
		Set<String> dset = new HashSet<String>();
		for(int i=0; i<D; i++){
			char d1 = Dstrs[i].charAt(0);
			char d2 = Dstrs[i].charAt(1);
			dset.add(d1+""+d2);
			dset.add(d2+""+d1);
		}
		
		String str;
		for(int i=0; i<N; i++){
			char ch = Nstr.charAt(i);
			if(result.size() > 0){
				str = result.get(result.size()-1)+ch;
				if(cmap.containsKey(str)){
					result.set(result.size()-1, cmap.get(str));
					continue;
				}
			}
			boolean delete = false;
			for(String s : result){
				str = s+ch;
				if(dset.contains(str)){
					delete = true;
					break;
				}
			}
			if(delete){
				result.clear();
				continue;
			}
			result.add(""+ch);
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2011_Q_B().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	private void test(){
//		C = 1;
//		Cstrs = new String[]{"QRI"};
//		D = 0;
//		Dstrs = new String[]{};
//		N = 4;
//		Nstr = "RRQR";
		
		// 1 EDK 1 SF 10 SWADEASAEF
//		C = 1;
//		Cstrs = new String[]{"EDK"};
//		D = 1;
//		Dstrs = new String[]{"SF"};
//		N = 10;
//		Nstr = "SWADEASAEF";
		
		// 1 ARG 1 QF 10 ARQRSQFFSQ
		C = 1;
		Cstrs = new String[]{"ARG"};
		D = 1;
		Dstrs = new String[]{"QF"};
		N = 10;
		Nstr = "ARQRSQFFSQ";
		
		print(getResult3().toString());
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
