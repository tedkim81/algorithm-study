package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code Jam Korea 2012 본선 2차 라운드 : 문제 C 박테리아
 * 건물에 세균실들이 위아래층으로 맞닿으면 안된다는 조건하에서 세균실의 최대수를 구하는 문제
 * 2013년 3월 7일 오후 2:23:39
 * 
 * 문제이해 6분, 해결방법 하루정도, 코딩 2시간, 그리고 실패..
 * 해결방법을 생각하면서 의사코드를 작성하는데 무척이나 애를 먹은 문제이다. 
 * 먼저 전체 방의 수를 구하고 살균실의 최소값을 구해 전체 방에서 뺄셈하여 세균실의 최대값을 구하고자 했다.
 * 1층부터 시작하여 하위 두개층의 겹치는 점들을 구하고 각 점을 포함한 방이 아래층일때와 위층일때에 대한 모든 경우에 대하여 그 다음층으로 이동하도록 했다.
 * 이를 구현하기 위해 다소 기형적인 방법을 적용했는데, getCount()에서 살균실을 배치할 수 있는 모든 경우를 만들기 위해 getCount2()를 호출했고
 * getCount2()에서 각각의 경우에 다시 getCount() 를 호출하도록 한 것이다. 이 방식으로 test output은 정상적으로 구할 수 있었으나
 * small output을 구할때는 실행이 끝나지 않는 문제가 발생했다. 
 * small input의 48번은 겹치는 점이 최대 13개가 되며 5층이므로 (2^13)^(5-1) = 2^52 = 약 4x(10^15) 으로 실행 불가능한 수이다.
 * 
 * TODO: 추후 다시 풀어보도록 하자.
 */
public class CodeJamKorea2012_2_C_todo {
	
	private int N,M,K;
	private char[][][] B;
	
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
			N = Integer.parseInt(strs[0]);
			M = Integer.parseInt(strs[1]);
			K = Integer.parseInt(strs[2]);
			B = new char[K][N][M];
			for(int k=0; k<K; k++){
				for(int n=0; n<N; n++){
					String str = br.readLine();
					for(int m=0; m<M; m++){
						B[k][n][m] = str.charAt(m);
					}
				}
			}
			
			String result = ""+(getRoomCount()-getCount(0,0));
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int getRoomCount(){
		int roomCount = 0;
		for(int k=0; k<K; k++){
			for(int n=0; n<N; n++){
				for(int m=0; m<M; m++){
					if(B[k][n][m] == '.'){
						roomCount++;
						fill(k, n, m, null, '.', 'r');
					}
				}
			}
		}
		print("roomCount : "+roomCount);
		return roomCount;
	}
	
	private class Point {
		private int row;
		private int col;
		public Point(int row, int col){
			this.row = row;
			this.col = col;
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Point){
				Point p = (Point)obj;
				if(this.row == p.row && this.col == p.col) 
					return true;
			}
			return false;
		}
		@Override
		public String toString() {
			return "("+row+","+col+")";
		}
	}
	
	private List<Point> getDupPoints(int f1, int f2){
		List<Point> list = new ArrayList<Point>();
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(B[f1][i][j] == 'r' && B[f1][i][j] == B[f2][i][j]){
					list.add(new Point(i, j));
				}
			}
		}
		return list;
	}
	
	private int getCount(int floor, int cnt){
		List<Point> dups = getDupPoints(floor, floor+1);
//		print("floor:"+floor+", cnt:"+cnt+", dups : "+dups);
		return getCount2(floor, dups, cnt);
	}
	
	private int getCount2(int floor, List<Point> dups, int cnt){
		if(dups.size() == 0){
			if(floor == K-2){
				return cnt;
			}
			else{
				return getCount(floor+1, cnt);
			}
		}
		
		List<Point> dups2 = new ArrayList<Point>(dups);
		char[][] b1 = copyFloor(floor);
		char[][] b2 = copyFloor(floor+1);
		
		Point lastDup = dups.get(dups.size()-1);
		fill(floor, lastDup.row, lastDup.col, dups, 'r', 'p');
		int cnt1 = getCount2(floor, dups, cnt+1);
		B[floor] = b1;
		fill(floor+1, lastDup.row, lastDup.col, dups2, 'r', 'p');
		int cnt2 = getCount2(floor, dups2, cnt+1);
		B[floor+1] = b2;
		
		return Math.min(cnt1, cnt2);
	}
	
	private char[][] copyFloor(int floor){
		char[][] b = new char[N][M];
		for(int n=0; n<N; n++){
			for(int m=0; m<M; m++){
				b[n][m] = B[floor][n][m];
			}
		}
		return b;
	}
	
	private void fill(int floor, int row, int col, List<Point> dups, char from, char to){
		if(row < 0 || row >= N || col < 0 || col >= M) return;
		if(dups != null) dups.remove(new Point(row, col));
		if(B[floor][row][col] != from) return;
		
		B[floor][row][col] = to;
		
		fill(floor, row-1, col, dups, from, to);
		fill(floor, row+1, col, dups, from, to);
		fill(floor, row, col-1, dups, from, to);
		fill(floor, row, col+1, dups, from, to);
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_2_C_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	private void test(){
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(1,1));
		list.add(new Point(2,2));
		list.add(new Point(3,3));
		list.remove(new Point(2,2));
		print(""+list);
	}
	
	private void test2(){
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(0,0));
		list.add(new Point(0,2));
		list.add(new Point(1,1));
		list.add(new Point(1,3));
		list.add(new Point(2,0));
		fill(1, 2, 0, list, '.', 'r');
		print("list: "+list);
		System.exit(0);
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
	
	private Map<String, Integer> delayedStopMap = new HashMap<String, Integer>();
	public void delayedStop(String key, String str, int delayCount){
		if(delayedStopMap.containsKey(key)){
			int remain = delayedStopMap.get(key) - 1;
			if(remain <= 0){
				print(key+" : "+str);
				new Exception().printStackTrace();
				System.exit(0);
			}
			delayedStopMap.put(key, remain);
		}
		else{
			delayedStopMap.put(key, delayCount);
		}
	}
}
