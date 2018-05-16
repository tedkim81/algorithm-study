package com.teuskim.solved;
import java.util.Scanner;
import java.util.Stack;

/**
 * 2권 633페이지 짝이 맞지 않는 괄호
 * 
 * 스택을 사용하면 쉽게 풀 수 있는 문제이다. 그러나 아래 주석으로 표시된 부분을 생각하지 못하여 1차시도에서는 실패했었다.
 * 주어지는 테스트 케이스 이외에도 상황별로 테스트를 먼저 해보도록 노력하자.
 */
public class BRACKETS2 {
	
	private String brs;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			brs = sc.next();
			
			String result = solve() ? "YES" : "NO";
			System.out.println(result);
		}
	}
	
	private boolean solve(){
		Stack<Character> st = new Stack<Character>();
		for(int i=0; i<brs.length(); i++){
			char br = brs.charAt(i);
			if(br=='(' || br=='{' || br=='[') st.push(br);
			else if(st.size() == 0) return false;  // 여기!!
			else if((br==')' && st.peek()!='(') || (br=='}' && st.peek()!='{') || (br==']' && st.peek()!='[')) return false;
			else st.pop();
		}
		if(st.size() == 0) return true;  // 여기!!
		else return false;
	}
	
	public static void main(String[] args) {
		new BRACKETS2().goodluck();
	}
}