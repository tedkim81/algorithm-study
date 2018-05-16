package com.teuskim.solved;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 1권 218페이지의 와일드카드
 * 첫번째 제출에 런타임 오류가 발생하고, 두번째에 오답처리되고, 세번째에야 정답처리 되었다.
 * 아래 표시한 부분에서 런타임 오류가 발생할거라 예측하고 수정한 후 해결되었다. 그러나 에러발생을 재현하지는 못했다. 기존 코드는 아래와 같다.
 * 오답 처리가 된 이유는 각 테스트 케이스별로 출력할때 알파벳 순서대로 하지 않았기 때문이다. 이것은 문제를 제대로 읽지 않았기 때문이며 주의가 필요하다.
 */
public class WILDCARD {
	
	private String wstr,pstr;
	private int[][] memo;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			wstr = sc.next();
			int N = sc.nextInt();
			TreeSet<String> set = new TreeSet<String>();
			for(int i=0; i<N; i++){
				pstr = sc.next();
				memo = new int[wstr.length()][pstr.length()+1];
				if(isMatch(0,0)) set.add(pstr);
			}
			for(String result : set){
				System.out.println(result);
			}
		}
	}
	
	private boolean isMatch(int widx, int pidx){
		if(widx == wstr.length()){
			if(pidx == pstr.length()) return true;
			else return false;
		}
		
		if(memo[widx][pidx] != 0){
			return memo[widx][pidx]==1 ? true : false;
		}
		
		char ch = wstr.charAt(widx);
		boolean result = false;
		if(ch == '*'){
			for(int i=pidx; i<=pstr.length(); i++){
				if(isMatch(widx+1, i)){
					result = true;
					break;
				}
			}
		}
//		else if(ch == '?' || (pidx < pstr.length() && ch == pstr.charAt(pidx))){  // 런타임 오류가 발생했던 부분
		else if(pidx < pstr.length() && (ch == '?' || ch == pstr.charAt(pidx))){  // 수정한 코드
			result = isMatch(widx+1, pidx+1);
		}
		memo[widx][pidx] = result ? 1 : -1;
		return result;
	}
	
	public static void main(String[] args) {
		new WILDCARD().goodluck();
	}
}