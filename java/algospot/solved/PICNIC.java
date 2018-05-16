package com.teuskim.solved;
import java.util.Scanner;

public class PICNIC {
	
	private int n,m;
	private boolean[][] frmap;
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			n = sc.nextInt();
			m = sc.nextInt();
			frmap = new boolean[n][n];
			for(int i=0; i<m; i++){
				int fr1 = sc.nextInt();
				int fr2 = sc.nextInt();
				frmap[fr1][fr2] = true;
				frmap[fr2][fr1] = true;
			}
			
			String result = ""+getCnt(0,0);
			System.out.println(result);
		}
	}
	
	private int getCnt(int checked, int idx){
		if((1<<n)-1 == checked) return 1;
		if(idx >= n) return 0;
		if((checked & (1<<idx)) == (1<<idx)) return 0;
		
		int cnt = 0;
		for(int i=idx+1; i<n; i++){
			if(frmap[idx][i] && ((checked & (1<<i)) != (1<<i))){
				int nextChecked = (checked | (1<<idx) | (1<<i));
				int nextIdx;
				for(nextIdx=idx+1; nextIdx<=n; nextIdx++)
					if((nextChecked & (1<<nextIdx)) != (1<<nextIdx)) break;
				cnt += getCnt(nextChecked, nextIdx);
			}
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		new PICNIC().goodluck();
	}
}