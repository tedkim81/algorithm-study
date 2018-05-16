package com.teuskim.solved;
import java.util.Scanner;

public class QUADTREE {
	
	private String qstr;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			qstr = sc.next();
			
			String result = ""+reverse(0);
			System.out.println(result);
		}
	}
	
	private String reverse(int idx){
		char ch = qstr.charAt(idx);
		if(ch == 'b' || ch == 'w') return ""+ch;
		
		String[] strs = new String[4];
		int nextidx = idx+1;
		for(int i=0; i<4; i++){
			strs[i] = reverse(nextidx);
			nextidx += strs[i].length();
		}
		return "x"+strs[2]+strs[3]+strs[0]+strs[1];
	}
	
	public static void main(String[] args) {
		new QUADTREE().goodluck();
	}
}