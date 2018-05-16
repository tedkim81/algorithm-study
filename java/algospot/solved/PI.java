package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 239페이지 원주율 외우기
 * 몇차례 실수로 인하여 2시간이나 걸렸다.
 * 
 * 1. 런타임에러가 발생했다.
 * String.substring() 메소드의 두번째 인자에 endindex를 넘겨야하는데 size를 넘기는 바람에 에러가 발생했다.
 * 
 * 2. 시간초과가 발생했다.
 * 최악의 상황을 만들어서 실행을 시켜보니 1350ms 정도가 걸렸다. 시간을 줄이기위해 substring사용하는 부분을 모두 없애고 나니 250ms정도로 단축됐다.
 * 
 * 3. 오답처리가 되었다.
 * 3~5조각으로 나눠야한다는 조건을 만족하지 않는 경우가 있었다. 1111122의 결과가 11이 되어야 하는데 2가 되었다.
 * getPoint()의 기저사례에를 if(str.length()-idx <= 5) return getPoint(idx, str.length()-idx); 와 같이 했었는데 
 * str.length()-idx이 3보다 작아지는 경우가 문제가 된 것이다.
 * 여기서 깨달은 한가지 사실은, 기저사례는 최대한 단순하게 하는것이 좋겠다는 것이었다.
 * 그리고 조건 만족하지 않는 경우 Integer.MAX_VALUE를 리턴하도록 했었는데 여기에 다른 값이 더해지면 음수가 되기 때문에 문제가 됐다.
 */
public class PI {
	
	private String str;
	private int[] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			str = sc.next();
			memo = new int[str.length()];
			
			String result = ""+getResult(0);
			System.out.println(result);
		}
	}
	
	private int getResult(int idx){
		if(idx == str.length()) return 0;
		if(memo[idx] > 0) return memo[idx];
		
		int result = 100000;
		for(int i=3; i<=5; i++){
			if(idx+i <= str.length())
				result = Math.min(result, getPoint(idx, i)+getResult(idx+i));
		}
		memo[idx] = result;
		return result;
	}
	
	private int getPoint(int idx, int size){
		char[] chs = new char[size];
		for(int i=0; i<size; i++) chs[i] = str.charAt(idx+i);
		
		boolean pass1 = true;
		for(int i=0; i<size; i++){
			if(chs[i] != chs[0]){ 
				pass1 = false; 
				break; 
			}
		}
		if(pass1) return 1;
		
		boolean pass2 = true;
		int diff = chs[1] - chs[0];
		for(int i=0; i<size-1; i++){
			if(chs[i+1]-chs[i] != diff){
				pass2 = false;
				break;
			}
		}
		if(pass2 && (diff == 1 || diff == -1)) return 2;
		
		boolean pass3 = true;
		for(int i=0; i<size; i++){
			if(chs[i] != chs[i%2]){
				pass3 = false;
				break;
			}
		}
		if(pass3) return 4;
		if(pass2) return 5;
		return 10;
	}
	
	public static void main(String[] args) {
		new PI().goodluck();
	}
}