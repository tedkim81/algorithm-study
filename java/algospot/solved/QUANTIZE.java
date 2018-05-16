package com.teuskim.solved;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1권 244페이지. Quantization ( 양자화 )
 * 실수로 꽤 오랜 시간을 보냈다.
 * 
 * 1. getResult()에서 eidx-sidx+1<=scnt 인 경우에 대해 따로 처리하지 못했다.
 * sidx, eidx, scnt 의 관계에 대하여 더 신중하게 생각했어야 했다.
 * 
 * 2. arr 를 sort 하지 않았었다.
 * 문제에 대하여 처음 고민할때 정렬된 상태를 가정하고 부분문제를 나눴는데, 코딩시 Arrays.sort 를 누락했다.
 */
public class QUANTIZE {
	
	private int N,S;
	private int[] arr;
	private int[][][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			N = sc.nextInt();
			S = sc.nextInt();
			arr = new int[N];
			memo = new int[N][N][S+1];
			for(int i=0; i<N; i++) for(int j=0; j<N; j++) Arrays.fill(memo[i][j], -1);
			for(int i=0; i<N; i++) arr[i] = sc.nextInt();
			Arrays.sort(arr);
			
			String result = ""+getResult(0, N-1, S);
			System.out.println(result);
		}
	}
	
	private int getResult(int sidx, int eidx, int scnt){
		if(memo[sidx][eidx][scnt] >= 0) return memo[sidx][eidx][scnt];
		
		int result = Integer.MAX_VALUE;
		if(eidx-sidx+1 <= scnt){
			result = 0;
		}
		else if(scnt == 1){
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for(int i=sidx; i<=eidx; i++){
				if(arr[i] < min) min = arr[i];
				if(arr[i] > max) max = arr[i];
			}
			for(int i=min; i<=max; i++){
				int tmp = 0;
				for(int j=sidx; j<=eidx; j++){
					tmp += (arr[j]-i) * (arr[j]-i);
				}
				result = Math.min(result, tmp);
			}
		}
		else{
			for(int i=sidx; i<=eidx-1; i++){
				result = Math.min(result, getResult(sidx, i, 1) + getResult(i+1, eidx, scnt-1));
			}
		}
		memo[sidx][eidx][scnt] = result;
//		System.out.println("sidx:"+sidx+", eidx:"+eidx+", scnt:"+scnt+", result:"+result);
		return result;
	}
	
	public static void main(String[] args) {
		new QUANTIZE().goodluck();
//		new Main().test();
	}
	
	private void test(){
		N = 3;
		S = 1;
		arr = new int[]{1,2,3};
		int result = getResult(0,N-1,S);
		System.out.println("result:"+result);
	}
}