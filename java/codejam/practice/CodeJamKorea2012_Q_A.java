package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Code Jam Korea 2012 예선 라운드 : 문제 A 새로운 달력
 * 연당월수, 월당일수, 주당일수가 주어질 때 1년치 달력에는 몇줄이 채워질 것인지 계산하는 문제
 * 2013년 2월 4일 오전 1:18:12
 * 
 * 쉬운 문제였지만, 역시나 또 시간이 오래 걸렸다. 문제이해 8분, 해결방법 20분, 그러나 정답제출에는 1시간 걸렸다.
 * small input 에 대해서는 이른 시간에 정답 제출을 했지만 large input 해결을 위해 반복되는 구간을 찾은 후 계산하고자 했으나
 * 중간중간 사소한 오류로 인하여 정답을 쉽게 찾지 못했다.
 * 별다른 해결책은 없을듯.. 집중하고 해결방법을 확실히 숙지한 후 코딩을 해야한다.
 */
public class CodeJamKorea2012_Q_A {
	
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
			
			String[] strs = br.readLine().split(" ");
			long mpy = Long.parseLong(strs[0]);  // month per year
			int dpm = Integer.parseInt(strs[1]);  // day per month
			int dpw = Integer.parseInt(strs[2]);  // day per week
			
			long result = 0;
			int startIdx = 0;
			int j;
			for(j=0; j<mpy; j++){
				int tmpDpm = dpm + startIdx;
				int line = (int)Math.ceil(tmpDpm / (double)dpw);
				result += line;
				startIdx = tmpDpm % dpw;
				if(startIdx == 0) break;
			}
			if(j < mpy-1){
				long repeatCnt = mpy / (j+1) - 1;
				result += result * repeatCnt;
				
				int end = (int)(mpy % (j+1));
				for(int k=0; k<end; k++){
					int tmpDpm = dpm + startIdx;
					int line = (int)Math.ceil(tmpDpm / (double)dpw);
					result += line;
					startIdx = tmpDpm % dpw;
				}
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_Q_A().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
