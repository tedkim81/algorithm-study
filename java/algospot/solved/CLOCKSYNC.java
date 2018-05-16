package com.teuskim.solved;
import java.util.Scanner;

/**
 * 1권 168페이지 시계 맞추기
 * 시간 지연 원인들
 * 1. times, switches, pressed(스위치 눌러진 횟수. 현재는 삭제됨) 을 정확히 인지하지 못하여 index 값을 잘못 넣어서 오답이 발생했다.
 * 2. times 를 변경하는 loop 안에서 pressed 를 변경하는 실수를 하여 답이 실제보다 큰 값이 나왔었다.
 * 3. 최초에는 times를 변경하고 재귀호출 한 후 times를 다시 원복하는 과정을 거쳤는데 이 결과 실행시간이 8초가 넘었다. times를 4번 변경하면 제자리로 돌아온다는 특성을 활용하여 이 부분을 최적화 했다.
 */
public class CLOCKSYNC {
	
	private int[] times;
	private int[][] switches;
	private final int MAX_CNT = 100;
	
	public void goodluck(){
		switches = new int[10][];
		switches[0] = new int[]{0,1,2};
		switches[1] = new int[]{3,7,9,11};
		switches[2] = new int[]{4,10,14,15};
		switches[3] = new int[]{0,4,5,6,7};
		switches[4] = new int[]{6,7,8,10,12};
		switches[5] = new int[]{0,2,14,15};
		switches[6] = new int[]{3,14,15};
		switches[7] = new int[]{4,5,7,14,15};
		switches[8] = new int[]{1,2,3,4,5};
		switches[9] = new int[]{3,4,5,9,13};
		times = new int[16];
		
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			for(int i=0; i<16; i++) times[i] = sc.nextInt();
			
			int cnt = getCnt(0);
			if(cnt >= MAX_CNT) cnt = -1;
			String result = ""+cnt;
			System.out.println(result);
		}
	}
	
	private int getCnt(int switchNum){
		if(switchNum == switches.length){
			boolean success = true;
			for(int i=0; i<times.length; i++){
				if(times[i] != 12){
					success = false;
					break;
				}
			}
			if(success) return 0;
			else return MAX_CNT;
		}
		
		int cnt = MAX_CNT;
		for(int i=0; i<4; i++){
			cnt = Math.min(cnt, i+getCnt(switchNum+1));
			
			for(int j=0; j<switches[switchNum].length; j++){
				times[switches[switchNum][j]] += 3;
				if(times[switches[switchNum][j]] == 15) times[switches[switchNum][j]] = 3;
			}
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		new CLOCKSYNC().goodluck();
	}
}