package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Round 1C 2010 : Problem C. Making Chess Boards
 * 2013년 1월 14일 오후 10:39:18
 * 주어진 나무판에서 정사각형의 체스보드용 나무를 잘라내는데, 크기가 큰게 많아야 한다.
 * 
 * 이 문제도 결국은 많은 시간을 투자했지만 정답을 이끌어내지 못했다.
 * sample input으로는 정상적인 output 이 나오지만 small input 을 통과하지 못했다.
 * 문제의 의미는 일찍 파악했지만, 해결방법을 찾는데 꽤 많은 시간이 걸렸다.
 * 그리고 생각한 해결방법대로 구현하는데도 굉장히 오랜 시간이 걸렸다.
 * 구현 시간을 단축하기 위해서는? 잘짜여진 코드를 보고 배우며 연습해야 한다.
 * 
 * 내가 생각한 해결방법은, 맵의 좌상단부터 차례대로 모든 점에 대하여 가장 큰 사각 영역을 찾는 것이었다.
 * 현재 점을 좌상단으로 하는 사각영역을 찾고 그 영역의 구성점으로 만들 수 있는 사각 영역을 반복적으로 찾아서 가장 큰 사각영역을 찾는 방식이다.
 * 이렇게 찾은 사각영역은 더큰 사각영역과 겹치지 않기 때문에 원하는 영역이라 판단하여 맵에서 제거했다.
 * 그러나 small input을 통과하지 못했고 원인파악을 위해 해답을 봤다.
 * 
 * 해답은 전혀 다른 방법이었다.
 * (!) 각 점을 우하단 코너로 가정하고 좌측,상측,좌상측의 점과 격자관계가 성립하면 해당점들에서의 크기값 중 최소값+1 을 그 크기로 하는 방식으로 맵을 만든다.
 * 그리고 그 점들을 (크기, 행위치, 열위치) 로 정렬하여 최상위부터 차례대로 제거한다.
 * 제거하는 사각영역내에 포함되는 점들도 모두 제거하고, 사각영역내에 포함되지는 않으나 영향을 받는 영역(사각영역의 4배크기)에 대하여 재계산 및 재정력을 한다.
 * 위와 같이 하면 가장큰 사각영역부터 차례대로 제거해 나갈 수 있다.
 * 
 * 이 방법이 이해는 된다. 그러나 내가 과연 이 방법을 생각해 낼 수 있었을까? (!)표시한 부분을 생각해 냈어야 한다.
 * 생각을 다양하게 하는 훈련을 해야한다. 그리고 추론을 할때 근접한 요소에 대하여 관계를 만들 수 있는지 확인해 봐야 한다.
 * 
 * 마지막으로 정답과 내가 생각한 해결방법으로 구한 답 사이의 차이를 비교해 봤다. 어디가 틀렸을까?
 * 내 방법은 생각한 대로 구현이 제대로 되었다면 정답을 찾았을 수도 있다. 즉 임의의 점에서 시작하여 찾은 사각영역에서 그 구성점들로 계속 사각영역을 찾아서 가장 큰 사각영역을 찾아야 한다.
 * 그러나 사각영역의 구성점들로 더큰 사각영역을 찾는 부분에서 오류가 있었다. 그 구성점을 포함(!)하는 가장 큰 사각영역을 찾아야 하는데 그러자면 구성점의 상하좌우를 모두 고려해야 한다.
 * 그러나 그 방법은 실행횟수가 너무 많고 복잡할 것이다. 따라서 제대로 구현했다 하더라도 분명 large input 을 시간내에 통과하지는 못했을 것이다.
 */
public class CodeJam2010_1C_C {
	
	private static void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs1 = br.readLine().split(" ");
			int m = Integer.parseInt(strs1[0]);
			int n = Integer.parseInt(strs1[1]);
			boolean[][] boolmap = new boolean[m][n];
			
			// input을 boolean map으로 변환한다.
			for(int j=0; j<m; j++){
				String str = br.readLine();
				for(int k=0; k<str.length(); k++){
					String boolStr = String.format("%4s", Integer.toBinaryString(Integer.parseInt(""+str.charAt(k), 16))).replace(' ', '0');
					for(int l=0; l<4; l++){
						if(boolStr.charAt(l) == '1')
							boolmap[j][k*4+l] = true;
						else
							boolmap[j][k*4+l] = false;
					}
				}
			}
			
			boolean[][] markmap = new boolean[m][n];
//			if(i == 0){
//				Rect t = findRect(boolmap, markmap, m, n, 0, 13);
//				System.out.println("size: "+t.size+" , ("+t.sRow+","+t.sCol+"), ("+t.eRow+","+t.eCol+")");
//			}
			
			TreeMap<Integer, Integer> resultmap = new TreeMap<Integer, Integer>();
			
			// 모든 점에 대하여 가장 큰 사각영역을 찾는다.
			for(int j=0; j<m; j++){
				for(int k=0; k<n; k++){
					if(markmap[j][k] == true) continue;  // 마킹된 사각영역인 경우 패스
					
					Rect t = findRectRight(boolmap, markmap, m, n, j, k);
					int o = t.sRow;
					int p = t.sCol;
					while(o <= t.eRow && p <= t.eCol){
						Rect t21 = findRectLeft(boolmap, markmap, m, n, o, p);
						Rect t22 = findRectRight(boolmap, markmap, m, n, o, p);
						Rect t2;
						if(t21.size >= t22.size)
							t2 = t21;
						else
							t2 = t22;
						
						if(t2.size > t.size){
							t = t2;
							o = t.sRow;
							p = t.sCol;
						}
						else{
							p++;
							if(p > t.eCol){
								o++;
								p = t.sCol;
							}
						}
					}
					
					// 찾은 사각 영역에 대하여 마킹한다.
					for(o=t.sRow; o<=t.eRow; o++){
						for(p=t.sCol; p<=t.eCol; p++){
							markmap[o][p] = true;
						}
					}
					if(resultmap.containsKey(t.size)){
						int cnt = resultmap.get(t.size);
						resultmap.put(t.size, cnt+1);
					}
					else{
						resultmap.put(t.size, 1);
					}
//					if(i == 0){
//						System.out.println(t.size+": ("+t.sRow+","+t.sCol+"), ("+t.eRow+","+t.eCol+")");
//					}
					
					// 한번더 체크하도록 하기 위해..
					k--;
				}
			}
			
			fw.write("Case #"+(i+1)+": "+resultmap.size()+"\n");
			Iterator<Integer> it = resultmap.descendingKeySet().iterator();
			while(it.hasNext()){
				int key = it.next();
				fw.write(key+" "+resultmap.get(key)+"\n");
			}
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static Rect findRectLeft(boolean[][] boolmap, boolean[][] markmap, int rows, int cols, int sr, int sc){
		int er = sr, ec = sc;
		int tr, tc;
		
		// 좌측하단으로 확장
		tr = er+1; tc = sc-1;
		while(tr < rows && tc >= 0){
			boolean iscomplete = true;
			
			for(int i=sr; i<=tr; i++){
				int diff = (1+(i-sr)) % 2;  // 시작점과 짝수만큼 차이나는 경우는 같은 색이어야 성공이고, 그렇지 않은 경우 다른 색이어야 성공
				if(markmap[i][tc] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[i][tc])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[i][tc])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			for(int i=sc; i<=ec; i++){
				int diff = ((tr-sr)+(i-sc)) % 2;
				if(markmap[tr][i] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[tr][i])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[tr][i])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			// 한바퀴 제대로 돌았다면
			er = tr; sc = tc;
			tr++; tc--;
		}
		
		// sr,sc 기준으로 우측하단으로 확장
		tr = er+1; tc = ec+1;
		while(tr < rows && tc < cols){
			boolean iscomplete = true;
			
			for(int i=sr; i<=tr; i++){
				int diff = ((tc-sc)+(i-sr)) % 2;  // 시작점과 짝수만큼 차이나는 경우는 같은 색이어야 성공이고, 그렇지 않은 경우 다른 색이어야 성공
				if(markmap[i][tc] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[i][tc])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[i][tc])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			for(int i=sc; i<tc; i++){
				int diff = ((tr-sr)+(i-sc)) % 2;
				if(markmap[tr][i] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[tr][i])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[tr][i])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			// 한바퀴 제대로 돌았다면
			er = tr; ec = tc;
			tr++; tc++;
		}
		
		return new Rect(er-sr+1, sr, sc, er, ec);
	}
	
	private static Rect findRectRight(boolean[][] boolmap, boolean[][] markmap, int rows, int cols, int sr, int sc){
		int er = sr, ec = sc;
		int tr, tc;
		
		// sr,sc 기준으로 우측하단으로 확장
		tr = er+1; tc = ec+1;
		while(tr < rows && tc < cols){
			boolean iscomplete = true;
			
			for(int i=sr; i<=tr; i++){
				int diff = ((tc-sc)+(i-sr)) % 2;  // 시작점과 짝수만큼 차이나는 경우는 같은 색이어야 성공이고, 그렇지 않은 경우 다른 색이어야 성공
				if(markmap[i][tc] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[i][tc])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[i][tc])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			for(int i=sc; i<tc; i++){
				int diff = ((tr-sr)+(i-sc)) % 2;
				if(markmap[tr][i] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[tr][i])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[tr][i])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			// 한바퀴 제대로 돌았다면
			er = tr; ec = tc;
			tr++; tc++;
		}
		
		// 좌측하단으로 확장
		tr = er+1; tc = sc-1;
		while(tr < rows && tc >= 0){
			boolean iscomplete = true;
			
			for(int i=sr; i<=tr; i++){
				int diff = (1+(i-sr)) % 2;  // 시작점과 짝수만큼 차이나는 경우는 같은 색이어야 성공이고, 그렇지 않은 경우 다른 색이어야 성공
				if(markmap[i][tc] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[i][tc])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[i][tc])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			for(int i=sc; i<=ec; i++){
				int diff = ((tr-sr)+(i-sc)) % 2;
				if(markmap[tr][i] == true
						|| (diff == 0 && boolmap[sr][sc] != boolmap[tr][i])
						|| (diff == 1 && boolmap[sr][sc] == boolmap[tr][i])){  // 비교 실패하는 경우
					iscomplete = false;
					break;
				}
			}
			if(iscomplete == false) break;
			
			// 한바퀴 제대로 돌았다면
			er = tr; sc = tc;
			tr++; tc--;
		}
		
		return new Rect(er-sr+1, sr, sc, er, ec);
	}
	
	private static class Rect {
		public int size;
		public int sRow, sCol, eRow, eCol;
		public Rect(int size, int sr, int sc, int er, int ec){
			this.size = size;
			this.sRow = sr;
			this.sCol = sc;
			this.eRow = er;
			this.eCol = ec;
		}
	}
	
	public static void test(){
		String a = "F";
		String b = Integer.toBinaryString(Integer.parseInt(a, 16));
		System.out.println("b:"+b);
	}
	
	//===============================================================
	
	public static void goodluck2() throws Exception{
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
			
			String[] strs1 = br.readLine().split(" ");
			int m = Integer.parseInt(strs1[0]);
			int n = Integer.parseInt(strs1[1]);
			boolean[][] boolmap = new boolean[m][n];
			
			// input을 boolean map으로 변환한다.
			for(int j=0; j<m; j++){
				String str = br.readLine();
				for(int k=0; k<str.length(); k++){
					String boolStr = String.format("%4s", Integer.toBinaryString(Integer.parseInt(""+str.charAt(k), 16))).replace(' ', '0');
					for(int l=0; l<4; l++){
						if(boolStr.charAt(l) == '1')
							boolmap[j][k*4+l] = true;
						else
							boolmap[j][k*4+l] = false;
					}
				}
			}
			
			// right-bottom-corner map/set 을 만든다.
			RbcObj[][] rbcmap = new RbcObj[m][n];
			TreeSet<RbcObj> rbcset = new TreeSet<RbcObj>();
			for(int j=0; j<m; j++){
				for(int k=0; k<n; k++){
					RbcObj rbc = getRbc(j, k, boolmap, rbcmap);
					rbcmap[j][k] = rbc;
					rbcset.add(rbc);
				}
			}
			
			// rbc set을 순차적으로 제거하면서 result map 을 만든다.
			TreeMap<Integer, Integer> resultmap = new TreeMap<Integer, Integer>();
			while(rbcset.isEmpty() == false){
				RbcObj rbc = rbcset.first();
				if(resultmap.containsKey(rbc.size)){
					int cnt = resultmap.get(rbc.size);
					resultmap.put(rbc.size, cnt+1);
				}
				else{
					resultmap.put(rbc.size, 1);
				}
				int sr = rbc.row - rbc.size + 1;
				int sc = rbc.col - rbc.size + 1;
				int er = Math.min(rbc.row + rbc.size, m-1);
				int ec = Math.min(rbc.col + rbc.size, n-1);
				for(int j=sr; j<=er; j++){
					for(int k=sc; k<=ec; k++){
						rbcset.remove(rbcmap[j][k]);
						if(j <= rbc.row && k <= rbc.col){  // 제거되는 영역
							rbcmap[j][k].size = 0;
						}
						else if(rbcmap[j][k].size > 0){  // 재계산해야하는 영역
							RbcObj rbc2 = getRbc(j, k, boolmap, rbcmap);
							rbcmap[j][k] = rbc2;
							rbcset.add(rbc2);
						}
					}
				}
			}
			
			fw.write("Case #"+(i+1)+": "+resultmap.size()+"\n");
			Iterator<Integer> it = resultmap.descendingKeySet().iterator();
			while(it.hasNext()){
				int key = it.next();
				fw.write(key+" "+resultmap.get(key)+"\n");
			}
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static RbcObj getRbc(int j, int k, boolean[][] boolmap, RbcObj[][] rbcmap){
		RbcObj rbc;
		if(j > 0 && k > 0
				&& boolmap[j][k] != boolmap[j][k-1]
				&& boolmap[j][k] != boolmap[j-1][k]
				&& boolmap[j][k] == boolmap[j-1][k-1]){
			int size = Math.min(rbcmap[j][k-1].size, Math.min(rbcmap[j-1][k].size, rbcmap[j-1][k-1].size)) + 1;
			rbc = new RbcObj(size, j, k);
		}
		else{
			rbc = new RbcObj(1, j, k);
		}
		return rbc;
	}
	
	public static class RbcObj implements Comparable<RbcObj> {
		public int size;
		public int row;
		public int col;
		public RbcObj(int size, int row, int col){
			this.size = size;
			this.row = row;
			this.col = col;
		}
		@Override
		public int compareTo(RbcObj o) {
			if(this.size != o.size)
				return o.size - this.size;
			if(this.row != o.row)
				return this.row - o.row;
			return this.col - o.col;
		}
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
