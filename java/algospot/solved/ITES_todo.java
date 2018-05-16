package com.teuskim.solved;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Scanner;

/**
 * 2권 635페이지 외계 신호 분석
 * 
 * TODO: JAVA의 실행시간 한계로 소스 제출시 시간초과가 발생한다. 추후 다른 언어로 다시 시도하자.
 */
public class ITES_todo {
	
	private long K,N;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			K = sc.nextLong();
			N = sc.nextLong();

//			long time = new Date().getTime();
			
			String result = ""+solve();
			System.out.println(result);
			
//			pt("time:"+(new Date().getTime() - time));
		}
	}
	
	private int solve(){
		Deque<Long> dq = new ArrayDeque<Long>();
		int result = 0;
		long A = 1983;
		long sum = 0;
		long mod = (1L << 32);
		long sign;
		for(int i=0; i<N; i++){
			sign = A % 10000 + 1;
			dq.addLast(sign);
			sum += sign;
			
			while(sum > K){
				sum -= dq.removeFirst();
			}
			if(sum == K) result++;
			A = (A * 214013 + 2531011) % mod;
		}
		return result;
	}
	
	public static void main(String[] args) {
		new ITES_todo().goodluck();
	}
	
	public static void pt(String str){
		System.out.println(str);
	}
}