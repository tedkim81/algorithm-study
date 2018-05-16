package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Round 1A 2010 : Problem C. Number Game
 * 2013년 1월 30일 오전 2:11:10
 * 숫자 A,B 가 주어질때 만약 A>B라면, k>=1, A=A-kB 와 같이 숫자를 변형해 나가면서 0이하의 수를 만드는 사람이 지는 게임이다.
 * 
 * 문제를 이해하는데는 약 15분이 걸렸고, 최초 문제해결방법이라고 생각한 것을 도출해내는데 약 30분정도 걸렸다.
 * 이때까지는 문제를 쉽게 풀 수 있을 줄 알았다. 그러나, 생각한대로 코딩하는 것이 쉽지 않았고 약 2시간 정도를 소비하여 결과물을 만들었으나 틀렸다.
 * 문제 해결을 위해 내가 도출해낸 힌트는, 
 * 나의 턴에서 A=B 를 만들면 win
 * 2*small > big 인 경우를 만들 수 있는 경우들(2가지)에 대하여 재귀호출
 * 작은수가 1인 경우 1을 제외한 모든 경우에 win, 2인 경우 1과 2를 제외한 모든 2의 배수, ...
 * 작은수를 A라고 할때 큰수 B의 확인할 범위는 A <= B < 3A 로 하고 A미만의 영역은 이전 계산값을 참조하고, 3A이상의 영역은 2A <= B < 3A 영역값을 반복한다.
 * 위와 같이 코딩하려고 했으나, 모호한 부분들 때문에 실패했다.
 * 
 * 답안을 보고 다시금 깨달은 것은 문제의 해답은 보통 위와 같이 난잡하지 않다는 것이다. 
 * 그리고 너무 직관에 의존하려고 한 것이 문제였다. 완전탐색으로 small case 에 대해서는 답을 구할 수 있겠지만 large case 에 대해서는 구할 수 없다.
 * 이럴 때는 귀납적인 접근으로 공식을 유도해 내야 한다.
 * 다시 풀어보자!!
 */
public class CodeJam2010_1A_C_todo {
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			int A1 = Integer.parseInt(strs[0]);
			int A2 = Integer.parseInt(strs[1]);
			int B1 = Integer.parseInt(strs[2]);
			int B2 = Integer.parseInt(strs[3]);
			
			int resultCount = 0;
			for(int j=A1; j<=A2; j++){
				for(int k=B1; k<=B2; k++){
					int A,B;
					if(j < k){
						A = j; B = k;
					}
					else{
						A = k; B = j;
					}
					if(winningPosition2(A, B)){
						resultCount++;
					}
				}
			}
			
			fw.write("Case #"+(i+1)+": "+resultCount+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private boolean winningPosition(int A, int B, boolean isWinnerTurn){
//		System.out.println("A:"+A+", B:"+B+", turn:"+isWinnerTurn);
		if(A <= 0 || B <= 0) return isWinnerTurn;
		if(A == B) return !isWinnerTurn;
		if(A > B){
			int t = A;
			A = B;
			B = t;
		}
		
		if(B >= 2*A && isWinnerTurn == false) return false;  // 이부분이 없어서 틀렸었다.
		
		int tB = B % A;
		if(tB == 0) return isWinnerTurn;
		
		boolean nextTurn = !isWinnerTurn;
		if(winningPosition(tB, A, nextTurn)) return true;
		if(A+tB != B && winningPosition(A, A+tB, nextTurn)) return true;
		return false;
	}
	
	private boolean winningPosition2(int A, int B){  // A < B
		if(A == 0) return true;
		if(B >= A*2) return true;
		return !winningPosition2(B-A, A);
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJam2010_1A_C_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
