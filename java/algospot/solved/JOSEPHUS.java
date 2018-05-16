package com.teuskim.solved;
import java.util.Scanner;

public class JOSEPHUS {
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			int N = sc.nextInt();
			int K = sc.nextInt();
			boolean[] removed = new boolean[N];
			int idx = 0;
			int removeCnt = 0;
			while(removeCnt < N-2){
				removed[idx] = true;
				removeCnt++;
				int moveCnt = 0;
				while(moveCnt < K){
					idx++;
					if(idx == N) idx = 0;
					if(removed[idx]) continue;
					moveCnt++;
				}
			}
			
			String result = "";
			for(int i=0; i<N; i++){
				if(removed[i] == false) result += (i+1)+" ";
			}
			System.out.println(result);
		}
	}
	
	public static void main(String[] args) {
		new JOSEPHUS().goodluck();
	}
}