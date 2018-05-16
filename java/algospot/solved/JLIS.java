package com.teuskim.solved;
import java.util.Scanner;

public class JLIS {
	
	private int n,m;
	private int[] A,B;
	private int[][][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			m = sc.nextInt();
			A = new int[n+1];
			B = new int[m+1];
			memo = new int[n+1][m+1][2];
			A[0] = Integer.MIN_VALUE;
			B[0] = Integer.MIN_VALUE;
			for(int i=1; i<n+1; i++) A[i] = sc.nextInt();
			for(int i=1; i<m+1; i++) B[i] = sc.nextInt();
			
			String result = ""+(getResult(0,0,1)-1);
			System.out.println(result);
		}
	}
	
	private int getResult(int aidx, int bidx, int isa){
		if(memo[aidx][bidx][isa] > 0) return memo[aidx][bidx][isa];
		
		int result = 1;
		int curr,i,j;
		if(isa == 1){
			curr = A[aidx];
			i = aidx+1;
			j = bidx;
		}
		else{
			curr = B[bidx];
			i = aidx;
			j = bidx+1;
		}
		for(; i<n+1; i++){
			if(curr < A[i]) result = Math.max(result, 1+getResult(i, bidx, 1));
		}
		for(; j<m+1; j++){
			if(curr < B[j]) result = Math.max(result, 1+getResult(aidx, j, 0));
		}
		memo[aidx][bidx][isa] = result;
		return result;
	}
	
	public static void main(String[] args) {
		new JLIS().goodluck();
	}
}