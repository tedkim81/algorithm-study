package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 201페이지 팬미팅(FANMEETING)
 * 분할 정복으로 쉽게 풀었다고 생각했는데 한가지 오류가 있었다.
 * 가운데 두개 판자를 걸치는 영역중 최대 영역을 구하는 과정에서 area를 구하는 부분이 while문 하단에 있어서 최초 2칸짜리 영역이 무시되는 오류가 있었다.
 */
public class FANMEETING {
	
	private int N;
	private int[] heights;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			N = sc.nextInt();
			heights = new int[N];
			for(int i=0; i<N; i++){
				heights[i] = sc.nextInt();
			}
			
			String result = ""+getArea(0,N-1);
			System.out.println(result);
		}
	}
	
	private int getArea(int start, int end){
		if(start == end) return heights[start];
		
		int middle = (start+end) / 2;
		int area = Math.max(getArea(start, middle), getArea(middle+1, end));
		
		int s = middle;
		int e = middle+1;
		int height = Math.min(heights[middle], heights[middle+1]);
		while(s >= start && e <= end){
			area = Math.max(area, (e-s+1)*height);  // 여기 두줄이 while문 하단에 있었다. 그 때문에 2칸짜리 영역이 무시되는 오류가 있었다.
			if(s == start && e == end) break;
			
			if(s-1 >= start && e+1 <= end){
				if(heights[s-1] > heights[e+1]){
					s--;
					height = Math.min(height, heights[s]);
				}
				else{
					e++;
					height = Math.min(height, heights[e]);
				}
			}
			else if(s-1 >= start){
				s--;
				height = Math.min(height, heights[s]);
			}
			else if(e+1 <= end){
				e++;
				height = Math.min(height, heights[e]);
			}
		}
		return area;
	}
	
	public static void main(String[] args) {
		new FANMEETING().goodluck();
	}
}