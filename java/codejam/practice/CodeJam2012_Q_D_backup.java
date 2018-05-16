package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Qualification Round 2012 : Problem D. Hall of Mirrors
 * 거울의 방에서 빛이 D 만큼의 거리 이상 투과하지 못한다고 할 때 내가 반사되어 보이는 수를 구하는 문제
 * 
 * 문제의 제한 조건에서 small input에서는 #이 2W+2H-4 개라는 것은 외벽만 주어진 경우를 뜻한다.
 * 이 경우 주어진 공간을 대칭이동하여 현재 위치에서 이동된 위치까지의 거리가 D 이하인 경우를 구하는 방식으로 문제를 해결했다.
 * 이 방식은 small input에 대해서만 적용 가능하다.
 */
public class CodeJam2012_Q_D_backup {
	
	private int H,W,D;
	private float top,left,right,bottom;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-small-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			H = sc.nextInt();
			W = sc.nextInt();
			D = sc.nextInt();
			for(int i=0; i<H; i++){
				String line = sc.next();
				for(int j=0; j<W; j++){
					if(line.charAt(j) == 'X'){
						 top = (i-1) + 0.5f;
						 left = (j-1) + 0.5f;
						 right = W-2-left;
						 bottom = H-2-top;
					}
				}
			}
//			print("top:"+top+", left:"+left+", right:"+right+", bottom:"+bottom);
			
			String result = ""+getCnt();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getCnt(){
		float sx = 0;
		boolean sxflag = true;
		while(true){
			if(sxflag) sx -= left+left;
			else sx -= right+right;
			if(Math.abs(sx) > D) break;
			sxflag = !sxflag;
		}
		float sy = 0;
		boolean syflag = true;
		while(true){
			if(syflag) sy -= top+top;
			else sy -= bottom+bottom;
			if(Math.abs(sy) > D) break;
			syflag = !syflag;
		}
		float ex = 0;
		boolean exflag = true;
		while(true){
			if(exflag) ex += right+right;
			else ex += left+left;
			if(Math.abs(ex) > D) break;
			exflag = !exflag;
		}
		float ey = 0;
		boolean eyflag = true;
		while(true){
			if(eyflag) ey += bottom+bottom;
			else ey += top+top;
			if(Math.abs(ey) > D) break;
			eyflag = !eyflag;
		}
		
		Set<Integer> set = new HashSet<Integer>();
		for(float y=sy; y<=ey;){
			boolean sxflag2 = sxflag;
			for(float x=sx; x<=ex;){
				if((x*x)+(y*y) <= D*D && !(x==0 && y==0)){
					set.add((int)(Math.atan2(y, x)*1000000));
				}
				if(sxflag2) x += left+left;
				else x += right+right;
				sxflag2 = !sxflag2;
			}
			if(syflag) y += top+top;
			else y += bottom+bottom;
			syflag = !syflag;
		}
		return set.size();
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2012_Q_D_backup().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	
	/**********************
	 * code for debugging *
	 **********************/
	
	public void test(){
		D = 2;
		top = 0.5f;
		left = 0.5f;
		right = 0.5f;
		bottom = 0.5f;
		
		int cnt = getCnt();
		print("cnt: "+cnt);
	}
	
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
