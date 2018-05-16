package gcj.y2013.round1a;
import java.util.Scanner;

/**
 * CodeJam 2013 Round 1A
 * Problem B. Manage your Energy
 */
public class B_qna {
	
	private int E,R,N;
	private int[] v;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(System.in);
		numberOfCases = sc.nextInt();
		
		// make output
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			E = sc.nextInt();
			R = sc.nextInt();
			N = sc.nextInt();
			v = new int[N];
			for(int i=0; i<N; i++) v[i] = sc.nextInt();
			
			String result = ""+solve(0, N-1, E, Math.min(R, E));
			
			System.out.println("Case #"+(casenum+1)+": "+result);
		}
	}
	
	/**
	 * 분할정복방식을 사용하여 각 (energe)*(activity value) 총합의 최대값을 구한다.
	 * 주어진 구간에서 최대값을 구하여 Vprev, Vmax, Vnext 로 구간을 분할하여 Vmax에 최선을 다한다.
	 * Vmax에 최대한 E에 가까운 에너지를 사용할 수 있도록 Vprev에서는 가능한 최대의 에너지를 남겨준다.
	 * 
	 * @param sidx  v[]에 대한 start index
	 * @param eidx  v[]에 대한 end index
	 * @param se    start energy 구간 시작시 사용가능 에너지 
	 * @param ee    end energy 구간이 끝날때 남아있어야 하는 에너지
	 * @return  (energe)*(activity value) 총합의 최대값
	 */
	private long solve(int sidx, int eidx, int se, int ee){
		if(sidx == eidx){
			int ue = se - ee + R;
			if(ue < 0) ue = 0;
			if(ue > E) ue = E;
			return ue * (long)v[sidx];
		}
		
		int vmaxIdx = 0;
		int vmax = Integer.MIN_VALUE;
		for(int i=sidx; i<=eidx; i++) if(v[i] > vmax){
			vmax = v[i];
			vmaxIdx = i;
		}
		
		long result = 0;
		int maxRecover,needRecover;
		
		// Vprev 에 대해 계산
		int nexte = se;
		if(vmaxIdx-1 >= sidx){
			maxRecover = R * (vmaxIdx-sidx);
			if(maxRecover < 0) maxRecover = 0;
			if(maxRecover > E) maxRecover = E;
			needRecover = E - se;
			if(needRecover < 0) needRecover = 0;
			if(needRecover > E) needRecover = E;
			if(maxRecover >= needRecover) nexte = E;
			else nexte = E-(needRecover-maxRecover);
			result += solve(sidx, vmaxIdx-1, se, nexte);
		}
		
		// Vmax 에 대해 계산
		maxRecover = 0;
		if(vmaxIdx+1 <= eidx){
			maxRecover = R * (eidx-vmaxIdx);
			if(maxRecover < 0) maxRecover = 0;
		}
		int ue = nexte + R + maxRecover - ee;
		if(ue < 0) ue = 0;
		if(ue > nexte) ue = nexte;
		result += ue * (long)v[vmaxIdx];
		
		// Vnext 에 대해 계산
		if(vmaxIdx+1 <= eidx){
			result += solve(vmaxIdx+1, eidx, Math.min(nexte-ue+R, E), ee);
		}
		
		return result;
	}
	
	public static void main(String[] args){
		try{
			
			new B_qna().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
