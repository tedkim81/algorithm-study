package com.codejam;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2012 : Problem D. Hall of Mirrors
 * 2013년 4월 4일 오후 9:04:17
 * 
 * othersolution 코드를 읽어본후 large input 에 대해서도 해결 가능하도록 새로 코딩했다.
 * 몇 가지 새롭게 배운 컨셉이 있다.
 * 1. EPS 사용 : 수학에서 아주작은 양수를 뜻하는 엡실론. 어떤 경계를 최소한으로 벗어나는 수를 구하기 위해 사용한다.
 * 2. 실수 연산에 전산학적 오차가 발생하기 때문에 계산결과가 정수가 되는지를 확인하는 isInteger 함수 추가
 * 
 * 코딩 및 디버깅에 상당한 시간이 걸렸다.
 * 1. 기본적으로, 계획한 방식을 구현하는데에 시간이 오려 걸렸다. 
 * 이것은 사전계획을 구체적으로 하고, 코딩 연습을 많이 하는 것 외에 별다른 방법이 없어 보인다.
 * 2. 종료 조건을 제대로 구현하지 못하여 디버깅하는데 상당한 시간이 걸렸다. 
 * dx,dy,D 의 개념을 명확히 인지하지 못했다. dx,dy는 방향벡터로서 거리와는 상관 없는 값이다.
 * 거리는 각 교차점까지의 거리(pdist)들 합으로 구하며, 마지막 목표지점까지의 값은 pdist/2 이다.
 */
public class CodeJam2012_Q_D {
	
	private int H,W,D;
	private char[][] map;
	private static final double EPS = 1E-7;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			H = sc.nextInt();
			W = sc.nextInt();
			D = sc.nextInt();
			map = new char[H][W];
			for(int i=0; i<H; i++){
				String line = sc.next();
				for(int j=0; j<W; j++){
					map[i][j] = line.charAt(j);
				}
			}
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getResult(){
		double sx = 0, sy = 0;
		for(int i=0; i<H; i++){
			for(int j=0; j<W; j++){
				if(map[i][j] == 'X'){
					sx = j - 0.5;
					sy = i - 0.5;
					break;
				}
			}
		}
		
		int result = 0;
		for(int dyy=-D; dyy<=D; dyy++){
			for(int dxx=-D; dxx<=D; dxx++){
				
				if(gcd(Math.abs(dxx), Math.abs(dyy)) != 1) continue;
				
				double nx=sx,ny=sy,tx,ty;
				double dist = 0;
				double dx=dxx, dy=dyy;
				
				while(true){
					tx = nx;
					ty = ny;
					if(dx == 0){
						if(dy > 0) ny = Math.ceil(ty+EPS);
						else ny = Math.floor(ty-EPS);
					}
					else if(dy == 0){
						if(dx > 0) nx = Math.ceil(tx+EPS);
						else nx = Math.floor(tx-EPS);
					}
					else{
						if(dx > 0) nx = Math.ceil(tx+EPS);
						else nx = Math.floor(tx-EPS);
						ny = (dy/dx)*(nx-tx) + ty;
						if(dy > 0 && ny > Math.ceil(ty+EPS)){
							ny = Math.ceil(ty+EPS);
							nx = (dx/dy)*(ny-ty) + tx;
						}
						else if(dy < 0 && ny < Math.floor(ty-EPS)){
							ny = Math.floor(ty-EPS);
							nx = (dx/dy)*(ny-ty) + tx;
						}
					}
					
					// {{ while문의 종료 조건. 제대로 구현하지 못하여 디버깅하는데 오랜 시간을 소비한 부분
					double pdist = Math.sqrt((nx-tx)*(nx-tx) + (ny-ty)*(ny-ty));
					if(dist+(pdist/2) > D+EPS) break;
					if(Math.abs(nx+tx-sx-sx) <= EPS && Math.abs(ny+ty-sy-sy) <= EPS){
						result++;
						break;
					}
					dist += pdist;
					// }}
					
					boolean isIntX = isInteger(nx);
					boolean isIntY = isInteger(ny);
					
					int row,col;
					if(isIntX && isIntY){ // 교차점
						row = (int)Math.floor(ny+EPS);
						col = (int)Math.floor(nx+EPS);
						
						// c1 c2
						// c3 c4
						char c1 = map[row][col];
						char c2 = map[row][col+1];
						char c3 = map[row+1][col];
						char c4 = map[row+1][col+1];
						int cnt = (c1=='#'?1:0) + (c2=='#'?1:0) + (c3=='#'?1:0) + (c4=='#'?1:0);
						
						if(cnt == 3){
							dx = -dx;
							dy = -dy;
						}
						else if((dx > 0 && c2 == '#' && c4 == '#') || (dx < 0 && c1 == '#' && c3 == '#')){
							dx = -dx;
						}
						else if((dy > 0 && c3 == '#' && c4 == '#') || (dy < 0 && c1 == '#' && c2 == '#')){
							dy = -dy;
						}
						else if((dx>0 && dy>0 && c4=='#') || (dx>0 && dy<0 && c2=='#') || (dx<0 && dy>0 && c3=='#') || (dx<0 && dy<0 && c1=='#')){
							// 흡수
							break;
						}
					}
					else if(isIntX){  // 횡으로 인접한 박스로 이동
						row = (int)Math.floor(ny)+1;
						if(dx > 0) col = (int)Math.floor(nx+EPS)+1;
						else col = (int)Math.floor(nx+EPS);
						if(map[row][col] == '#'){
							dx = -dx;
						}
					}
					else{  // 종으로 인접한 박스로 이동
						col = (int)Math.floor(nx)+1;
						if(dy > 0) row = (int)(ny+1);
						else row = (int)ny;
						if(map[row][col] == '#'){
							dy = -dy;
						}
					}
				}
			}
		}
		return result;
	}
	
	private boolean isInteger(double val){
		if(Math.abs(val-Math.floor(val)) <= EPS || Math.abs(val-Math.ceil(val)) <= EPS)
			return true;
		return false;
	}
	
	private int gcd(int a, int b){
		while(a > 0 && b > 0){
			if(a > b) a %= b;
			else b %= a;
		}
		return a+b;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJam2012_Q_D().goodluck();
			
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
