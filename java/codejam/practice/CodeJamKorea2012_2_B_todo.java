package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Code Jam Korea 2012 본선 2차 라운드 : 문제 B 윷놀이
 * 윳놀이에서 윷 던진 결과들은 알지만 누가 던졌는지는 모른다고 할때 각팀 말들의 결과 위치가 가능한 위치인지를 판단하는 문제
 * 2013년 2월 28일 오전 1:48:19
 * 
 * 문제이해 11분, 해결방법 28분, 코딩 2시간 걸렸고, 또 오답처리 되었다.
 * 이제는 좀.. 한번에 정답을 맞추고 싶다.. 더 열심히 노력하자..
 * 몇군데 오류를 찾긴 했지만 그래도 결과는 오답이었다. 결국 다른 참가자의 솔루션으로 small output을 구한 후 비교했다.
 * 이런.. getNextLoc()에서 현재위치가 0이면 한바퀴 돌고 마지막 점에 있는 것이므로 다음 위치는 -1이 되어야 하는데 그렇지 않았다.
 * 그 부분을 수정하고 나서 정답인 small output을 구할 수 있었다.
 * 실전에서 이런 오류들은 어떻게 찾을 수 있을까? 그때는 어떤 케이스가 잘못되었는지를 알 수가 없다.
 * 문제의 조건 및 구현된 코드를 꼼꼼히 살펴보고, 문제가 발견되지 않으면 단위테스트를 해보는 것, 이외에 아직은 특별한 수단이 없는 듯 하다.
 * 
 * TODO: large output은 오답처리 되었고, 아직 문제점을 파악하지는 못했다. 방법을 다시 생각해보고 재시도 해보자.
 * U가 large input에서는 U가 2인 경우가 있기 때문에 기존에 int로 했던 것을 int[] 로 변경하고 그와 관련하여 몇가지를 변경했다.
 * 그리고 실행한 결과 수행시간이 너무 길어진다는 사실을 발견했다. U가 2이면 수행시간이 O(2^N) 이 된다.
 * 그래서 dynamic programming 을 위해 map을 만들었고 large output을 만들어 낼 수는 있었다. 그러나.. 오답처리 되었다. 
 */
public class CodeJamKorea2012_2_B_todo {
	
	private int U, N, A, B;
	private int[] narr, goalA, goalB;
	private Map<Long, Boolean> map;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			U = Integer.parseInt(strs[0]);
			N = Integer.parseInt(strs[1]);
			A = Integer.parseInt(strs[2]);
			B = Integer.parseInt(strs[3]);
			narr = new int[N];
			goalA = new int[A];
			goalB = new int[B];
			map = new HashMap<Long, Boolean>();
			
			strs = br.readLine().split(" ");
			for(int j=0; j<N; j++){
				if("Do".equals(strs[j])) narr[j] = 0;
				else if("Gae".equals(strs[j])) narr[j] = 1;
				else if("Gul".equals(strs[j])) narr[j] = 2;
				else if("Yut".equals(strs[j])) narr[j] = 3;
				else narr[j] = 4;
			}
			
			strs = br.readLine().split(" ");
			for(int j=0; j<A; j++) goalA[j] = strs[j].length()==0 ? 0 : Integer.parseInt(strs[j]);
			strs = br.readLine().split(" ");
			for(int j=0; j<B; j++) goalB[j] = strs[j].length()==0 ? 0 : Integer.parseInt(strs[j]);
			
			String result = ""+(dfs(0, new State(U), 'A') ? "YES" : "NO");
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private long getMapKey(int nth, State s, char who){
		StringBuilder sb = new StringBuilder();
		sb.append(who=='A' ? 0 : 1);
		
		int a1 = s.A[0];
		if(a1 < 0) a1 += 100; // -1이면 99, -2면 98
		int a2 = 98;
		if(s.A.length > 1) a2 = s.A[1];
		if(a2 < 0) a2 += 100;
		if(a1 < a2) sb.append(a1+""+a2);
		
		int b1 = s.B[0];
		if(b1 < 0) b1 += 100; // -1이면 99, -2면 98
		int b2 = 98;
		if(s.B.length > 1) b2 = s.B[1];
		if(b2 < 0) b2 += 100;
		if(b1 < b2) sb.append(b1+""+b2);
		
		sb.append(nth);
		
		return Long.parseLong(sb.toString());
	}
	
	private boolean dfs(int nth, State s, char who){
//		print("nth:"+nth+" , state:"+s+", who:"+who);
		// base case 1 : 마지막에 도달했을 때, 결과가 일치하는지 확인
		if(nth == N){
			int aleng = 0;
			int[] cmpA = Arrays.copyOf(s.A, s.A.length);
			for(int i=0; i<goalA.length; i++){
				if(goalA[i] >= 0){
					aleng++;
					boolean same = false;
					for(int j=0; j<cmpA.length; j++){
						if(goalA[i] == cmpA[j]){
							same = true;
							cmpA[j] = -1;
							break;
						}
					}
					if(same == false) return false;
				}
			}
			if(aleng != A) return false;
			
			int bleng = 0;
			int[] cmpB = Arrays.copyOf(s.B, s.B.length);
			for(int i=0; i<goalB.length; i++){
				if(goalB[i] >= 0){
					bleng++;
					boolean same = false;
					for(int j=0; j<cmpB.length; j++){
						if(goalB[i] == cmpB[j]){
							same = true;
							cmpB[j] = -1;
							break;
						}
					}
					if(same == false) return false;
				}
			}
			if(bleng != B) return false;
			
			return true;
		}
		
		// base case 2 : 더이상 시도해볼 필요 없는 경우 찾기. 움직일 수 있는 말의 수가 모자라는 경우!
		int acnt = 0;
		for(int i=0; i<s.A.length; i++) if(s.A[i] >= -1) acnt++;
		if(acnt < A) return false;
		int bcnt = 0;
		for(int i=0; i<s.B.length; i++) if(s.B[i] >= -1) bcnt++;
		if(bcnt < B) return false;
		
		// dynamic programming
		long mapKey = getMapKey(nth, s, who);
		if(map.containsKey(mapKey)){
			return map.get(mapKey);
		}

		int what = narr[nth];
		char nextWho;
		int nextLoc;
		boolean result = false;
		
		if(who == 'A'){
			for(int i=0; i<s.A.length; i++){
				if(s.A[i] == -2) continue;
				
				int a = s.A[i];
				int[] currLocA = Arrays.copyOf(s.A, s.A.length);
				int[] currLocB = Arrays.copyOf(s.B, s.B.length);
				
				nextLoc = getNextLoc(a, what);
				if(a == -1) s.A[i] = nextLoc;
				else{
					for(int j=0; j<s.A.length; j++){
						if(s.A[j] == a) s.A[j] = nextLoc;
					}
				}
				nextWho = 'B';
				if(what == 3 || what == 4) nextWho = 'A';
				if(nextLoc >= 0){
					for(int j=0; j<s.B.length; j++){
						if(nextLoc == s.B[j]){
							nextWho = 'A';
							s.B[j] = -1;
						}
					}
				}
				
				if(dfs(nth+1, s, nextWho)){
					result = true;
					break;
				}
				
				s.A = currLocA;
				s.B = currLocB;
			}
		}
		else{  // 'B'
			for(int i=0; i<s.B.length; i++){
				if(s.B[i] == -2) continue;
				
				int b = s.B[i];
				int[] currLocB = Arrays.copyOf(s.B, s.B.length);
				int[] currLocA = Arrays.copyOf(s.A, s.A.length);
				
				nextLoc = getNextLoc(b, what);
				if(b == -1) s.B[i] = nextLoc;
				else{
					for(int j=0; j<s.B.length; j++){
						if(s.B[j] == b) s.B[j] = nextLoc;
					}
				}
				nextWho = 'A';
				if(what == 3 || what == 4) nextWho = 'B';
				if(nextLoc >= 0){
					for(int j=0; j<s.A.length; j++){
						if(nextLoc == s.A[j]){
							nextWho = 'B';
							s.A[j] = -1;
						}
					}
				}
				
				if(dfs(nth+1, s, nextWho)){
					result = true;
					break;
				}
				
				s.B = currLocB;
				s.A = currLocA;
			}
		}
		
		map.put(getMapKey(nth, s, who), result);
		return result;
	}
	
	private int getNextLoc(int currLoc, int what){
		check(currLoc != -2, "getNextLoc error!");
		int nextLoc;
		if(currLoc == 5){
			nextLoc = 20 + what;
		}
		else if(currLoc == 10){
			if(what == 0) nextLoc = 25;
			else if(what == 1) nextLoc = 26;
			else if(what == 2) nextLoc = 22;
			else if(what == 3) nextLoc = 27;
			else nextLoc = 28;
		}
		else if(currLoc == 22){
			if(what == 0) nextLoc = 27;
			else if(what == 1) nextLoc = 28;
			else if(what == 2) nextLoc = 0;
			else nextLoc = -2;
		}
		else if(currLoc <= 19){
			if(currLoc == -1) nextLoc = what + 1;
			else if(currLoc == 0) nextLoc = -2;
			else nextLoc = currLoc + what + 1;
			if(nextLoc == 20) nextLoc = 0;
			else if(nextLoc > 20) nextLoc = -2;
		}
		else if(currLoc >= 20 && currLoc <= 24){
			nextLoc = currLoc + what + 1;
			if(nextLoc > 24) nextLoc = 15 + (nextLoc-24) - 1;
		}
		else if(currLoc >= 25 && currLoc <= 26){
			nextLoc = currLoc + what + 1;
			if(nextLoc == 27) nextLoc = 22;
			else if(nextLoc >= 28 && nextLoc <= 29) nextLoc--;
			else if(nextLoc == 30) nextLoc = 0;
			else if(nextLoc > 30) nextLoc = -2;
		}
		else{ // (currLoc >= 27 && currLoc <= 28)
			nextLoc = currLoc + what + 1;
			if(nextLoc == 29) nextLoc = 0;
			else if(nextLoc > 29) nextLoc = -2;
		}
		return nextLoc;
	}
	
	private class State {
		private int[] A;
		private int[] B;
		
		public State(int size){
			this.A = new int[size];
			this.B = new int[size];
			Arrays.fill(this.A, -1);
			Arrays.fill(this.B, -1);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i=0; i<A.length; i++) sb.append(A[i]+",");
			sb.append("] , [");
			for(int i=0; i<B.length; i++) sb.append(B[i]+",");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_2_B_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			System.out.println("exit: "+log);
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
}
