package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Round 1A 2010 : Problem A. Rotate
 * 2013년 1월 20일 오후 12:18:03
 * Join-K 게임이다. 두가지의 블록이 쌓여있고 시계방향으로 회전시켜 같은색상의 K개의 블록이 가로 or 세로 or 대각선으로 연결되면 승리하는 게임이다.
 * 
 * 나에게 잠시 멘붕을 안겨준 문제.. 문제이해에 약30분, 해결방법도출에 약20분, 코딩에 약1시간 정도 걸렸고.. 틀렸다.
 * 문제를 이해하는 단계에서 문제의 대략적인 컨셉을 이해하고 나서 꽤 쉬운문제라고 생각했다.
 * 그러나 복병은 역시 영어였다. 대략적으로 뭘 원하는지는 알듯한데 구체적인 조건들, 즉 게임의 규칙이 제대로 파악되지 않았다.
 * 내가 처음 이해했던 게임의 규칙은 이렇다. 주어진 블록배치에서 먼저 성립(K개의 연결)하는지 확인하고, 상대방과 내가 번갈아가며 90도 회전시켜서 성립하는 것이 있는지 확인하는 것이다.
 * 그리고 4방향에 대하여 회전시킬때 각각의 방향에 대하여 블록배치가 같을 것이라고 생각하고 4번만 회전시켜보면 될거라고 생각했다. ( 완전한 착각이었다! )
 * 그러나 문제는 이보다 훨씬 단순했다. 그냥 주어진 배치에서 한번 회전시키고 성립하는지 확인하여 성립된 블록을 찾는 것이었다.
 * 
 * 문제 지문을 다시 해석해보면, ( 아직 확신은 없다.. )
 * 정사각영역 안에서 red/blue 블록을 쌓아 K개의 연결을 만드는 게임을 한다(Join-K). 
 * 이때 상대방 모르게 90도로 한번 돌려서 K개의 연결이 만들어지는지 그리고 누가 승리하게 될것인지를 예측해보는 프로그램을 작성하는 것이 문제이다.
 * 바둑둘때 돌 한두개의 위치를 몰래 바꾸는 것은 이해가 되는데 이렇게 배열 자체를 완전히 바꾸는걸 상대방 몰래 한다는게 무슨의미가 있는지 아직도 잘 모르겠다.
 * 
 * 문제를 잘못 이해하여 오답을 제출했고, 해답은.. 잘못봤었다.. A문제를 봤어야 했는데 C를 보고 있었고 문제와 전혀 상관이 없어보여 이해가 되지 않았다.
 * 그래서 참가자들의 정답중 자바 파일을 다운로드하여 차이점을 찾고 내가 잘못이해했던 부분도 찾을 수 있었다.
 * 
 * 연습을 실전처럼! 덤벙거리지 말고 차분하게 학습하자.
 */
public class CodeJam2010_1A_A {
	
	public static final String both = "Both";
	public static final String red = "Red";
	public static final String blue = "Blue";
	public static final String neither = "Neither";
	
	private static void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String strs[] = br.readLine().split(" ");
			int N = Integer.parseInt(strs[0]);
			int K = Integer.parseInt(strs[1]);
			
			char[][] map = new char[N][N];
			for(int j=0; j<N; j++){
				String line = br.readLine();
				for(int k=0; k<N; k++){
					map[j][k] = line.charAt(k);
				}
			}
			
			String result = neither;
//			result = scan(map, N, K);
//			printMap(map, i+1);
			if(neither.equals(result)){
				map = rotate(map, N);
//				printMap(map, i+1);
				result = scan(map, N, K);
			}
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void printMap(char[][] map, int casenum){
		System.out.print(casenum+": =========================\n");
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[i].length; j++){
				System.out.print(String.format("%2s", map[i][j]));
			}
			System.out.print("\n");
		}
		System.out.print(casenum+": =========================\n");
	}
	
	public static String scan(char[][] map, int N, int K){
		boolean Rsuccess = false;
		boolean Bsuccess = false;
		
		// 상하로 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			for(int k=0; k<N; k++){
				if(map[j][k] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[j][k] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		
		// 좌우로 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			for(int k=0; k<N; k++){
				if(map[k][j] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[k][j] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		
		// 좌상단에서 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			int row = 0;
			int col = j;
			while(col >= 0 && row < N){
				if(map[row][col] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[row][col] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
				col--;
				row++;
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		
		// 좌하단에서 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			int row = N-1;
			int col = j;
			while(col >= 0 && row >= 0){
				if(map[row][col] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[row][col] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
				col--;
				row--;
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		
		// 우상단에서 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			int row = 0;
			int col = N-j-1;
			while(col < N && row < N){
				if(map[row][col] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[row][col] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
				col++;
				row++;
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		
		// 우하단에서 스캔
		for(int j=0; j<N; j++){
			int Rcnt = 0;
			int Bcnt = 0;
			int row = N-1;
			int col = N-j-1;
			while(col < N && row >= 0){
				if(map[row][col] == 'R'){
					Rcnt++;
					if(Bcnt < K) Bcnt = 0;
				}
				else if(map[row][col] == 'B'){
					Bcnt++;
					if(Rcnt < K) Rcnt = 0;
				}
				else{
					if(Bcnt < K) Bcnt = 0;
					if(Rcnt < K) Rcnt = 0;
				}
				col++;
				row--;
			}
			if(Rcnt >= K) Rsuccess = true;
			if(Bcnt >= K) Bsuccess = true;
		}
		if(Rsuccess && Bsuccess) return both;
		else if(Rsuccess) return red;
		else if(Bsuccess) return blue;
		return neither;
	}
	
	public static char[][] rotate(char[][] map, int N){
		char[][] rotated = new char[N][N];
		for(int i=0; i<N; i++){
			int insertedRow = N-1;
			for(int j=N-1; j>=0; j--){
				if(map[i][j] == 'R' || map[i][j] == 'B'){
					rotated[insertedRow][N-i-1] = map[i][j];
					insertedRow--;
				}
			}
		}
		return rotated;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
