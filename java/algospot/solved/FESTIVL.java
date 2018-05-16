package com.teuskim.solved;
import java.util.Scanner;

public class FESTIVL {
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			int N = sc.nextInt();
			int L = sc.nextInt();
			int[] cost = new int[N];
			int i,j;
			for(i=0; i<N; i++) cost[i] = sc.nextInt();
			
			double avgmin = 101;
			int sum,cnt;
			for(i=0; i<=N-L; i++){
				sum = 0;
				for(j=i; j<N; j++){
					sum += cost[j];
					cnt = j-i+1;
					if(cnt >= L) avgmin = Math.min(avgmin, sum/(double)cnt);
				}
			}
			
			String result = String.format("%.11f", avgmin);
			System.out.println(result);
		}
	}
	
	public static void main(String[] args) {
		new FESTIVL().goodluck();
	}
}