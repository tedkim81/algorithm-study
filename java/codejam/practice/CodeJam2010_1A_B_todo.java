package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Round 1A 2010 : Problem B. Make it Smooth
 * 2013년 1월 26일 오후 9:54:15
 * 문제를 풀지 못했다. 추후 다시 풀어보자. 문제를 푼후 The Fancy Solution 에 대해서도 다시 구현해 보자.
 */
public class CodeJam2010_1A_B_todo {
	
	private static void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			int D = Integer.parseInt(strs[0]);
			int I = Integer.parseInt(strs[1]);
			int M = Integer.parseInt(strs[2]);
			int N = Integer.parseInt(strs[3]);
			
			strs = br.readLine().split(" ");
			List<Integer> list = new ArrayList<Integer>();
			for(int j=0; j<N; j++){
				list.add(Integer.parseInt(strs[j]));
			}
			
			int cost = 0;
			for(int j=1; j<N-1; j++){
				int left = list.get(j-1);
				int center = list.get(j);
				int right = list.get(j+1);
				int diffLC = center-left;
				int diffCR = right-center;
				int tmpCost = 0;
				
				if(Math.abs(diffLC) <= M) continue;
				
			}
			
			String result = "";
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
