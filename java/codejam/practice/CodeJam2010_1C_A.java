package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Round 1C 2010 : Problem A. Rope Intranet
 * 2013년 1월 6일 오후 6:23:15
 * 두 건물 사이에 n개의 선을 연결할 때 만나는 교차점의 수를 구하는 문제
 * 
 * 문제는 굉장히 쉬웠으나, 방심하다가 한번 틀렸다.
 * 물론 시간 조절을 잘 해야하는 것도 중요하지만 문제의 해결방법을 생각할때 좀더 집중하고 주의를 하도록 하자.
 */
public class CodeJam2010_1C_A {
	
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
			
			int lineCnt = Integer.parseInt(br.readLine());
			Line[] lines = new Line[lineCnt];
			for(int j=0; j<lineCnt; j++){
				lines[j] = new Line(br.readLine().split(" "));
			}
			
			int intersectCnt = 0;
			for(int j=0; j<lineCnt; j++){
				for(int k=j+1; k<lineCnt; k++){
					if((lines[j].left < lines[k].left && lines[j].right > lines[k].right)
							|| (lines[j].left > lines[k].left && lines[j].right < lines[k].right))
						intersectCnt++;
				}
			}
			
			fw.write("Case #"+(i+1)+": "+intersectCnt+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static class Line {
		public int left;
		public int right;
		public Line(String[] strs){
			this.left = Integer.parseInt(strs[0]);
			this.right = Integer.parseInt(strs[1]);
		}
	}
	
	public static void main(String[] args){
		try{
			
			goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
