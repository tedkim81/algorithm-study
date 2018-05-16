package gcj.y2012.round1b;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Problem B. Tide Goes In, Tide Goes Out
 * 
 * small input 은 완전탐색으로 해결 가능할 것으로 판단했고, 결과도 간신히 시간내에 나왔다.
 * 그러나.. 첫시도가 오답처리되었고, 이번에는 solution을 확인하지 않고 찾아보기로 했다.
 * 알고리즘을 다시 훑어보다가.. 헉! 카약을 타고 이동할 수 있는 최소수심이 10이 아니라 20이었다.. 문제를 잘못 본 것이다.
 * 1라운드를 통과하기 위해 나에게 필요한 능력은 알고리즘 지식수준보다는 영어로 된 문제의 정확한 이해능력이다.
 * 당분간은 문제를 스스로 푸는데 드는 시간을 제한하고 지문 및 분석을 읽는데 시간을 더 할애해야겠다.
 * 
 * 완전탐색으로 시간이 상당히 걸려서, 혹시나 시간안에 해결이 안된다면 어떻게 해결할까를 고민하다가 동적계획법을 다소 예쁘지 않게 적용해 봤다.
 * 높이가 double 타입이라 이대로는 동적계획법을 적용할 수 없었기 때문에 소수점을 버리고 int 타입으로 변환하여 동적계획법을 적용했더니 제대로 동작했다.
 * 이러한 방법은 정확하지는 않겠지만 조건의 단위가 모두 정수형이었기에 가능했던 것으로 보인다. ( 진입가능한 높이가 50 이상이어야 한다는 등의 조건 )
 * 즉, 높이가 100.0 인 경우와 100.5 인 경우가 같이 존재하지는 않았던 것이다.
 * 
 * 그러나, 동적계획법을 적용해도 large input은 해결할 수 없다. 메모이제이션의 크기가 1억이 넘어 메모리 오류가 발생하기 때문이다.
 * 이것은 아마도 그래프 문제로 변환하여 생각해 봐야 할 듯 하다. 시간 절약을 위해 analysis 를 먼저 보고 문제를 다시 풀어보도록 하자.
 * 
 * 이 문제는 전형적인 dijkstra 최단거리알고리즘 문제였다.
 * 몇 군데 구현하면서 실수한 부분들이 있어서 시행착오를 겪었다. 지속적으로 연습하도록 하자.
 */
public class B {
	
	private int H,N,M;
	private int[][] C,F;
	private double EPS = 1e-7;
//	private double[][][] memo;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"B-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			H = sc.nextInt();
			N = sc.nextInt();
			M = sc.nextInt();
			C = new int[N][M];
			F = new int[N][M];
			for(int i=0; i<N; i++) for(int j=0; j<M; j++) C[i][j] = sc.nextInt();
			for(int i=0; i<N; i++) for(int j=0; j<M; j++) F[i][j] = sc.nextInt();
//			memo = new double[N][M][H+1];
			
//			String result = String.format("%.7f", search(0, 0, H, new boolean[N][M]));
			String result = String.format("%.7f", dijkstra());
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private double dijkstra(){
		double[][] dist = new double[N][M];
		for(int i=0; i<N; i++) Arrays.fill(dist[i], Double.MAX_VALUE);
		PriorityQueue<VT> pq = new PriorityQueue<VT>();
		pq.add(new VT(0,0,0));
		dist[0][0] = 0;  // 이걸 빼먹었었다!! 이런거 주의하자.
		int[][] dir = {{0,-1},{-1,0},{0,1},{1,0}};
		
		while(pq.isEmpty() == false){
			VT here = pq.poll();
			if(dist[here.row][here.col] < here.t) continue;
			
			for(int i=0; i<dir.length; i++){
				int nextRow = here.row + dir[i][0];
				int nextCol = here.col + dir[i][1];
				if(nextRow>=0 && nextRow<N && nextCol>=0 && nextCol<M){
					VT there = getThere(here, nextRow, nextCol);
					if(there != null && dist[there.row][there.col] > there.t){
						pq.add(there);
						dist[there.row][there.col] = there.t;
					}
				}
			}
		}
		return dist[N-1][M-1];
	}
	
	private VT getThere(VT here, int nextRow, int nextCol){
		int minC = Math.min(C[here.row][here.col], C[nextRow][nextCol]);
		int maxF = Math.max(F[here.row][here.col], F[nextRow][nextCol]);
		if(minC - maxF < 50) return null;
		
		double h = H - (here.t * 10);
		double t = here.t;  // 헉! 여기를 t=0 이라고 해놨었다..ㅠ.ㅠ
		if(minC - h < 50-EPS){
			t += (50-minC+h) / (double)10;
			h = minC - 50;
		}
		if(h < H-EPS){
			if(h-F[here.row][here.col] > 20-EPS) t += 1;
			else t += 10;
		}
		return new VT(nextRow, nextCol, t);
	}
	
	private class VT implements Comparable<VT>{
		
		private int row;
		private int col;
		private double t;
		
		public VT(int row, int col, double t){
			this.row = row;
			this.col = col;
			this.t = t;
		}

		@Override
		public int compareTo(VT o) {
			return (this.t > o.t ? 1 : (this.t < o.t ? -1 : 0));
		}
		
	}
	
	/*
	private double search(int row, int col, double h, boolean[][] visited){
		if(row == N-1 && col == M-1) return 0;
		
		if(h < 0) h = 0;
		if(memo[row][col][(int)h] > 0) return memo[row][col][(int)h];
		
		double result = Double.MAX_VALUE;
				
		// 서쪽
		if(col > 0 && visited[row][col-1] == false){
			result = Math.min(result, searchNext(row, col, row, col-1, h, visited));
		}
		
		// 북쪽
		if(row > 0 && visited[row-1][col] == false){
			result = Math.min(result, searchNext(row, col, row-1, col, h, visited));
		}
		
		// 동쪽
		if(col < M-1 && visited[row][col+1] == false){
			result = Math.min(result, searchNext(row, col, row, col+1, h, visited));
		}
		
		// 남쪽
		if(row < N-1 && visited[row+1][col] == false){
			result = Math.min(result, searchNext(row, col, row+1, col, h, visited));
		}
		
		memo[row][col][(int)h] = result;
		
		return result;
	}
	
	private double searchNext(int row, int col, int nextRow, int nextCol, double h, boolean[][] visited){
		double minC, maxF, time, nexth;
		double result = Double.MAX_VALUE;
		
		time = 0;
		minC = Math.min(C[row][col], C[nextRow][nextCol]);
		maxF = Math.max(F[row][col], F[nextRow][nextCol]);
		if(minC-maxF > 50-EPS){
			nexth = h;
			if(minC-h < 50-EPS){
				time += (50-(minC-h)) / (double)10;
				nexth = minC - 50;
			}
			if(nexth < H-EPS){
				if(nexth - F[row][col] > 20-EPS){
					time += 1;
					nexth -= 10;
				}
				else{
					time += 10;
					nexth -= 100;
				}
			}
			
			visited[nextRow][nextCol] = true;
			result = Math.min(result, time+search(nextRow, nextCol, nexth, visited));
			visited[nextRow][nextCol] = false;
		}
		return result;
	}
	*/
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new B().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	
	/**********************
	 * code for debugging *
	 **********************/
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			print("exit: "+log);
			System.exit(0);
		}
	}
	
	public static void print(String str){
		System.out.println(str);
	}
	
	public static void print(int[] arr){
		if(arr == null) print("null");
		else{
			String str = "[";
			if(arr.length > 0){
				for(int i=0; i<arr.length; i++) str += arr[i]+",";
				str = str.substring(0, str.length()-1);
			}
			str += "]";
			print(str);
		}
	}
}
