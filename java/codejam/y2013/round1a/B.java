package gcj.y2013.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem B. Manage your Energy
 * 
 * 자존심으로 끝까지 풀어보고 싶었는데 결국은 large input에 대해서 해결하지 못했다.
 * 최초에는 동적계획법이나 탐욕법으로 접근을 시도해 봤으나 부분문제로 분리할때 필요한 값들이 캐쉬할 수 있는 수준의 값이 아니었다.
 * 한참을 해메다 전열을 정비하고 다시 문제를 바라보니 분할정복법이 보였다. 구체적인 방법이 쉽게 전개되지는 않았지만 심증은 확실했다.
 * Vmax 로 구간을 분할하여 해결해보려고 하다가 제대로 생각을 전개하지 못하고 Vmin으로 동일하게 시도해봤고 이마저도 실패했다.
 * 그러나 분할정복으로 풀수 있는 문제라는 것을 거의 확신했기 때문에 다시 차근차근 조건을 생각해 봤다.
 * Vprev, Vmin, Vnext 로 분할하여 문제를 풀때 문제점은 Vprev가 독립적이지 않다는 것이다. Vnext에 최대값이 있는 경우 Vprev의 동작이 달라져야 한다.
 * Vprev, Vmax, Vnext 로 분할하여 문제를 풀때도 마찬가지로 각 구간이 서로 완전히 독립적이지 않다.
 * 결국은 최대값에서 최선을 다해야 한다는 조건 충족을 위해 Vprev, Vmax, Vnext로 구간을 분할하고 정복해 보기로 했다.
 * Vmax에 최선을 다하기 위해서는 Vprev에서 마지막에 가장 E에 가까운 에너지를 남겨줘야 하고,
 * Vmax에서는 Vnext에서 re를 남겨줄 수 있는 범위안에서 최선을 다하도록 했다.
 * 이러한 과정을 꽤나 오랜시간 투자하여 small input에 대해 제대로 동작하도록 만들었으나.. large input은 실패하고 말았다.
 * input이 워낙 크기 때문에 디버깅도 사실상 어려워졌다.
 * 
 * solution을 확인해보고 해결방법이 내 방법과 유사하다면 문제점을 파악해 보자.
 * 종만님의 solution을 확인해보니 시작점을 기준으로 부분구간을 확장해 가면서 모든 조합에 대하여 적절히 에너지를 분배하는 방식이었다.
 * 내 문제의 오류는 알고스팟에 질문으로 포스팅을 해뒀다. 추후 확인해보자.
 */
public class B {
	
	private int E,R,N;
	private int[] v;
//	private long result;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"test.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			E = sc.nextInt();
			R = sc.nextInt();
			N = sc.nextInt();
			v = new int[N];
			for(int i=0; i<N; i++) v[i] = sc.nextInt();
			
//			String result = ""+getResult();
//			String result = ""+getResult(-1, E);
			
//			result = 0;
//			solve(E, 0, N-1, true);
			
//			String result = ""+solve(0, N-1, E, Math.min(R, E));
			String result = ""+solve();
			
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private long solve(int sidx, int eidx, int se, int re){
//		print("sidx:"+sidx+", eidx:"+eidx+", se:"+se+", re:"+re);
		if(sidx == eidx){
			int ue = se - re + R;
			if(ue < 0) ue = 0;
			if(ue > E) ue = E;
//			print("sidx:"+sidx+", eidx:"+eidx+", se:"+se+", re:"+re+", result:"+(ue * v[sidx]));
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
		int ue = nexte + R + maxRecover - re;
		if(ue < 0) ue = 0;
		if(ue > nexte) ue = nexte;
		result += ue * (long)v[vmaxIdx];
		
		// Vnext 에 대해 계산
		if(vmaxIdx+1 <= eidx){
			result += solve(vmaxIdx+1, eidx, Math.min(nexte-ue+R, E), re);
		}
		
//		print("sidx:"+sidx+", eidx:"+eidx+", se:"+se+", re:"+re+", result:"+result);
		return result;
	}
	
	/**
	 * other solution
	 */
	private long solve() {
		long ret = 0;
	
		int[] distributed = new int[N];
		distributed[0] = E;
		for(int i = 1; i < N; ++i) distributed[i] = Math.min(R, E);
	
		print(distributed);
		for(int i = 1; i < N; ++i) {
			int j = i-1;
			while(j >= 0 && 
					distributed[i] < E && 
					v[j] < v[i]) {
				long take = Math.min(E - distributed[i], distributed[j]);
				print("j:"+j+", i:"+i+", take:"+take);
				distributed[j] -= take;
				distributed[i] += take;
				--j;
				print(distributed);
			}
		}
	
		for(int i = 0; i < N; ++i) 
			ret += distributed[i] * (long)v[i];
	
		return ret;
	}
	
	/*
	private int solve(int se, int sidx, int eidx, boolean usemax){
		if(sidx == eidx){
//			print("usemax:"+usemax+", se:"+se+", 2*R:"+(2*R));
			if(!usemax && se-R > R){
				result += 2*R * (long)v[sidx];
//				print("se:"+se+", sidx:"+sidx+", eidx:"+eidx+", 2added:"+(2*R * v[sidx]));
				return se - R;
			}
			else{
				result += se * (long)v[sidx];
//				print("se:"+se+", sidx:"+sidx+", eidx:"+eidx+", 1added:"+(se * v[sidx]));
				return R;
			}
		}
		
		int vminIdx = 0;
		int vmin = Integer.MAX_VALUE;
		int vmaxIdx = 0;
		int vmax = Integer.MIN_VALUE;
		for(int i=sidx; i<=eidx; i++){
			if(v[i] < vmin){
				vmin = v[i];
				vminIdx = i;
			}
			if(v[i] > vmax){
				vmax = v[i];
				vmaxIdx = i;
			}
		}
		
		if(vminIdx > sidx){
			if(vminIdx < vmaxIdx) se = solve(se, sidx, vminIdx-1, false);
			else se = solve(se, sidx, vminIdx-1, true);
		}
		
		if(vminIdx < eidx){
			int tmp = se + R - E;
			if(tmp < 0) tmp = 0;
//			print("se:"+se+", sidx:"+sidx+", eidx:"+eidx+", tmp:"+(tmp * v[vminIdx]));
			result += tmp * (long)v[vminIdx];
			se = se - tmp + R;
			se = solve(se, vminIdx+1, eidx, true);
		}
		else{
			se = solve(se, vminIdx, eidx, true);
		}
		
		return se;
	}
	
	private int getResult(){
		int vmaxIdx = -1;
		int vmax = 0;
		for(int i=0; i<v.length; i++) if(v[i] > vmax) {
			vmax = v[i];
			vmaxIdx = i;
		}
		
		int result = 0;
		for(int i=0; i<v.length; i++){
			if(i == vmaxIdx) result += v[i] * E;
			else result += v[i] * R;
		}
		return result;
	}
	
	private int getResult(int aidx, int e){
		if(aidx == N) return 0;
		
		int result = 0;
		for(int i=0; i<=e; i++){
			int nexte = e-i+R;
			if(nexte > E) nexte = E;
			int vv = 0;
			if(aidx >= 0) vv = v[aidx];
			result = Math.max(result, i*vv + getResult(aidx+1, nexte));
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
