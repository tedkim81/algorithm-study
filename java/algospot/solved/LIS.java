package com.teuskim.solved;
import java.util.Scanner;

public class LIS {
	
	private int N;
	private int[] nums, memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			N = sc.nextInt();
			nums = new int[N];
			for(int i=0; i<N; i++)
				nums[i] = sc.nextInt();
			memo = new int[N];
			
			int result = 0;
			for(int i=0; i<N; i++)
				result = Math.max(result, getResult(i));
			
			System.out.println(result);
		}
	}
	
	private int getResult(int idx){
		if(idx == N-1) return 1;  // 이 부분은 없어도 될듯
		if(memo[idx] > 0) return memo[idx];
		
		int result = 1;
		for(int i=idx+1; i<N; i++){
			if(nums[idx] < nums[i]){
				result = Math.max(result, 1+getResult(i));
			}
		}
		memo[idx] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new LIS().goodluck();
	}
}