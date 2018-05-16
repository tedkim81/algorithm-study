package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Round 1B 2010 : Problem A. File Fix-it
 * 2013년 1월 18일 오후 12:10:40
 * 주어진 경로들에 대하여 디렉토리를 생성할때 mkdir 이 실제로 발생한 횟수를 구하는 문제 ( 이미 만들어진 디렉토리에 대한 고려 )
 * 
 * 문제가 상당히 쉬웠다. 문제이해에 30분, 해결방법구상에 20분, 코딩에 30분 정도 걸린듯 하다.
 * 쉬운 문제를 더 빨리 풀수 있도록 노력해야 한다. 집중력이 부족했다.
 */
public class CodeJam2010_1B_A {
	
	private static void goodluck() throws Exception {
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
			
			String[] strs = br.readLine().split(" ");
			int n = Integer.parseInt(strs[0]);
			int m = Integer.parseInt(strs[1]);
			Dir root = new Dir();
			
			// 이미 생성되어있는 디렉토리
			for(int j=0; j<n; j++){
				makeDirs(root, br.readLine().split("/"), 0);
			}
			// 새로 생성하는 디렉토리 ( 여기서만 카운트 증가 )
			int cnt = 0;
			for(int j=0; j<m; j++){
				cnt = makeDirs(root, br.readLine().split("/"), cnt);
			}
			
			fw.write("Case #"+(i+1)+": "+cnt+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static int makeDirs(Dir root, String[] paths, int currCnt){
		Dir currDir = root;
		for(int k=1; k<paths.length; k++){
			Dir nextDir = currDir.findNext(paths[k]);
			if(nextDir == null){
				Dir tmpDir = new Dir();
				currDir.addNext(paths[k], tmpDir);
				currDir = tmpDir;
				currCnt++;
			}
			else{
				currDir = nextDir;
			}
		}
		return currCnt;
	}
	
	private static class Dir {
		public Map<String, Dir> next;
		
		public Dir(){
			next = new HashMap<String, Dir>();
		}
		public Dir findNext(String name){
			if(next.containsKey(name)){
				return next.get(name);
			}
			return null;
		}
		public void addNext(String name, Dir nextDir){
			this.next.put(name, nextDir);
		}
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
