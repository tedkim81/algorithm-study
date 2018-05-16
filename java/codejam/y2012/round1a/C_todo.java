package gcj.y2012.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Problem C. Cruise Control
 * 
 * 핵심조건 : 속도가 일정하다.
 * 그에 따른 명제 : 한번 만난 차는 다시 만나지 않는다. N=6이라면 만나는 횟수는 최대 15번이다.
 * 해결방법 : t=0부터 모든 차가 만나는 상황마다 어떻게 움직일지를 정한다.
 * 
 * 모든 차의 조합을 확인하여 차들이 만나게 되는 시간값들을 시간순서로 정렬해 둔다.
 * 각 시간에서 위치들을 업데이트하고, 만나는 차가 있는지 확인한다.
 * 만나는 차를 추월할 수 있는지 확인한다.
 * 추월할 수 없는 경우 그때의 시간값을 리턴하고, 추월할 수 있는 경우 다음 시간에 대하여 재귀호출한다. 
 * 이때 경우의 수가 2개이상이라면 모든 상황에 대하여 호출한 후 최대값을 리턴한다.
 * 
 * 추월할 수 없는 경우 : 앞차 두대가 모두 막힌 경우, 차한대가 앞차와 뒷차 모두 막고 있는 경우, 앞차 뒷차가 같은레인에 있고 모두 이동이 안되는 경우
 *                  , 뒷차 두대가 같은 위치에 있고 속도가 모두 앞차보다 빠를때 ( 테스트 케이스에 대하여 디버깅 중 뒤늦게 발견 )
 * 추월할 수 있는 경우 : 앞차와 뒷차가 모두 이동가능 상태면 가능한 배치가 2가 되며 동일 시간에 두차가 만나는 경우가 n이라면, 2^n 의 배치 경우의 수가 생긴다.
 * 
 * 위의 알고리즘을 구현하는데에 시간이 오래 걸렸고, 디버깅 하는데에도 시간이 상당히 걸렸다.
 * 추월할 수 있는 경우가 차 배치의 경우의 수가 2^n 이 되는 경우 어떻게 재귀호출을 할 것인가에 대하여 꽤 오랜 시간 헤맸다.
 * ( 앞차 뒷차가 모두 이동가능한 경우를 배열로 저장하고 이것을 반복문을 통해 모든 상황을 만들어서 호출하도록 했다. )
 * 디버깅 하는데에 어려웠던 점은, 추월할 수 없는 경우에 대하여 모두 처리해야 문제를 풀 수 있는데 몇가지가 누락된 조건들이 있어서 그것을 찾느라 어려웠다.
 * 추월할 수 없는 경우와 있는 경우 중 어느것을 선택하는게 더 나을까? 이것을 신중하게 생각해야했고 지금에 와서 보면 추월할 수 있는 경우를 찾는게 더 나은 방법이었다.
 * 
 * 이 알고리즘으로 large input도 해결할 수 있을까? 시간복잡도를 생각해보자.
 * process() 내에서도 반복은 O(N^2) 이고, 재귀호출깊이는 최대 O(N^2) 이며, 재귀호출너비가 평균적으로 O(2) 정도 된다고 보면 ( 아직 복잡도 계산은 어렵다.. )
 * 시간복잡도는 O((N^2) * 2^(N^2)) 가 된다. N=6이면, 가능은 하지만(그래도 큰 수가 된다.) N=50 이면 계산할 필요도 없다.
 * 
 * analysis 를 보고 힌트를 얻고 나서 solve() 를 만들었다.
 * 경우의 수들을 재귀호출하지 않고, fixed[] 및 samelane[] 에 상태를 담아둔후 막히는 상황인지를 확인할때 사용하는 것이다.
 * 어느 정도 틀을 만들고 나서부터 테스트 케이스와 small input 에 대하여 실행시켜보면서 예외가 되는 상황들을 일일히 확인하고 
 * 그에 대한 대응을 코드에 추가하다보니 어느새 코드는 너무 복잡해져 버렸다.
 * 그리고 small input에 대해 정상적으로 돌아가는 코드를 작성했지만 결국 large input에 대해서는 정답을 구하지 못했다.
 * 
 * TODO: 추후 문제를 다시 이해하고 풀어볼 필요가 있다. 모든 상황에 대하여 좀더 일반화하여 코드를 심플하게 만들어보자.
 */
public class C_todo {
	
	private int N;
	private char[] C;
	private int[] S;
	private double[] P;
	private Double[] T;
	
	private static int MAX = Integer.MAX_VALUE;
	private static double EPS = 1e-7;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"C-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			C = new char[N];
			S = new int[N];
			P = new double[N];
			for(int i=0; i<N; i++){
				C[i] = sc.next().charAt(0);
				S[i] = sc.nextInt();
				P[i] = sc.nextInt();
			}
			TreeSet<Double> tset = new TreeSet<Double>();
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					if(i == j) continue;
					if(P[j] - P[i] > 5-EPS && S[i] > S[j]){
						tset.add((P[j]-P[i]-5) / (double)(S[i]-S[j]));  // 추월전
						tset.add((P[j]-P[i]+5) / (double)(S[i]-S[j]));  // 추월후
					}
				}
			}
			T = tset.toArray(new Double[0]);
			
//			double result = process(0, C);
			double result = solve();
			if(result == MAX){
				fw.write("Case #"+(casenum+1)+": Possible\n");
				print("Case #"+(casenum+1)+": Possible");
			}
			else{
				String str = String.format("%.7f", result);
				fw.write("Case #"+(casenum+1)+": "+str+"\n");
				print("Case #"+(casenum+1)+": "+str);
			}
		}
		fw.close();
	}
	
	private double solve(){
		// fixed 초기화
		boolean[] fixed = new boolean[N];
		for(int i=0; i<N; i++)
			for(int j=0; j<N; j++)
				if(i != j && Math.abs(P[i]-P[j]) < 5+EPS) fixed[i] = true;
		// samelane 생성
		int[][] samelane = new int[N][2];
		for(int i=0; i<N; i++) Arrays.fill(samelane[i], -1);
		
		// T loop
		double[] currp = P.clone();
		for(double t : T){
			// 위치 P 업데이트
			for(int i=0; i<N; i++) currp[i] = P[i] + (S[i] * t);
			// peer 생성
			int[][] peer = new int[N][2];
			for(int i=0; i<N; i++){
				Arrays.fill(peer[i], -1);
				for(int j=0; j<N; j++){
					if(i == j) continue;
					if(C[i] != C[j] && Math.abs(currp[i]-currp[j]) < 5-EPS){
						if(peer[i][0] == -1) peer[i][0] = j;
						else peer[i][1] = j;
					}
				}
			}
			// fixed 업데이트
			for(int ii=0; ii<N; ii++){
				for(int i=0; i<N; i++){
					if(peer[i][0] == -1 && peer[i][1] == -1) fixed[i] = false;
					else if(peer[i][0] >= 0 && peer[i][1] == -1){
						if(fixed[peer[i][0]] == false) fixed[i] = false;
						else fixed[i] = true;
					}
					else if(peer[i][0] >= 0 && peer[i][1] >= 0){
						if(fixed[peer[i][0]] == false && fixed[peer[i][1]] == false) fixed[i] = false;
						else fixed[i] = true;
					}
				}
			}
			// samelane 업데이트
			for(int i=0; i<N; i++){
				// 추월할 차를 만나는지 찾는다.
				int j = -1;
				int jj = -1; // 지금 막 추월한 차
				for(int k=0; k<N; k++){
					if(Math.abs(currp[k]-currp[i]-5) < EPS && S[i] > S[k]) j = k;
					if(Math.abs(currp[i]-currp[k]-5) < EPS && S[i] > S[k]) jj = k;
				}
				
				if(j >= 0){
					// 추월할 차를 만나는 상황에 같은 레인에 있어야 하는 쌍이 있는지 찾는다.
					if(samelane[i][0] != j && peer[j][0] >= 0){
						samelane[i][0] = peer[j][0];
						samelane[i][1] = j;
						samelane[peer[j][0]][0] = i;
						samelane[peer[j][0]][1] = j;
					}
					// 추월할 차와 나의 peer가 같은 레인에 있어야 하는지 확인
					else if(peer[i][0] >= 0){
						samelane[j][0] = peer[i][0];
						samelane[j][1] = i;
						samelane[peer[i][0]][0] = j;
						samelane[peer[i][0]][1] = i;
					}
				}
				if(jj >= 0){
					// 지금 막 추월한차가 있다면, samelane 업데이트
					if(samelane[i][1] == jj){
						if(currp[i] > currp[samelane[i][0]]){ // 현재차가 앞서 있는 차라면
							if(peer[i][0] == -1){
								samelane[samelane[i][0]][0] = -1;
								samelane[samelane[i][0]][1] = -1;
								samelane[i][0] = -1;
								samelane[i][1] = -1;
							}
						}
						else{ // 현재차가 뒤에 있는 차라면
							samelane[samelane[i][0]][0] = -1;
							samelane[samelane[i][0]][1] = -1;
							samelane[i][0] = -1;
							samelane[i][1] = -1;
						}
					}
					else if(samelane[jj][1] == i && peer[jj][0] == -1){
						samelane[samelane[jj][0]][0] = -1;
						samelane[samelane[jj][0]][1] = -1;
						samelane[jj][0] = -1;
						samelane[jj][1] = -1;
					}
				}
			}
			
			// test print
//			String testStr = "----------------\nt:"+t+", peer:[";
//			for(int i=0; i<peer.length; i++) testStr += "("+peer[i][0]+","+peer[i][1]+"),";
//			testStr += "] , fixed:[";
//			for(int i=0; i<fixed.length; i++) testStr += (fixed[i]?1:0)+",";
//			print(testStr+"]");
			
			// 모든차 상태 체크 및 업데이트
			for(int ii=0; ii<2; ii++)
			for(int i=0; i<N; i++){
				// 추월할 차를 만나는지 찾는다.
				int j = -1;
				int jj = -1; // 지금 막 추월한 차
				for(int k=0; k<N; k++){
					if(C[i] == C[k] && Math.abs(currp[k]-currp[i]-5) < EPS && S[i] > S[k]) j = k;
					if(Math.abs(currp[i]-currp[k]-5) < EPS && S[i] > S[k]) jj = k;
				}
//				print("i:"+i+", j:"+j+", jj:"+jj+" , currp:"+currp[i]+", C:"+C[i]+", samelane:("+samelane[i][0]+","+samelane[i][1]+")");
				if(j >= 0){
					if(samelane[i][0] == j){
						// 추월해야할 차와 같은레인에 있어야한다면 추월할 수 없다.
						return t; 
					}
					else if(fixed[i] == false){
						C[i] = (C[j]=='L') ? 'R' : 'L';
						for(int k=0; k<=1; k++) 
							if(peer[i][k] >= 0) C[peer[i][k]] = (C[i]=='L') ? 'R' : 'L';
						if(fixed[j]){
							fixed[i] = true;
							for(int k=0; k<=1; k++){
								if(peer[i][k] >= 0){
									check(fixed[peer[i][k]]==false, "fixed[peer[i][k]] is must be false!!");
									C[peer[i][k]] = (C[i]=='L') ? 'R' : 'L';
									fixed[peer[i][k]] = true;
								}
							}
						}
					}
					else if(fixed[j] == false){
						C[j] = (C[i]=='L') ? 'R' : 'L';
						fixed[j] = true;
						for(int k=0; k<=1; k++){
							if(peer[j][k] >= 0){
								check(fixed[peer[j][k]]==false, "fixed[peer[j][k]] is must be false!!");
								C[peer[j][k]] = (C[j]=='L') ? 'R' : 'L';
								fixed[peer[j][k]] = true;
							}
						}
					}
					else if(C[i] == C[j]){
						// 위 조건을 만족하지 않고 같은 레인에 있다면 i는 막힌 상태이다.
						return t; 
					}
				}
			}
		}
		return MAX;
	}
	
	private double process(int tidx, char[] c){
		if(tidx == T.length) return MAX;
		
		double[] p = new double[N];
		for(int i=0; i<N; i++) p[i] = P[i] + T[tidx] * S[i];
		char[] cc = new char[N];
		for(int i=0; i<N; i++) cc[i] = c[i];
		
		// test print
		print("\n--- "+T[tidx]+" ---");
		for(int i=0; i<N; i++){
			print(i+" : "+cc[i]+" "+p[i]+"  , speed:"+S[i]);
		}
		
		int[] peer = new int[N];
		Arrays.fill(peer, -1);
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				if(i == j) continue;
				if(Math.abs(p[i]-p[j]) < 5-EPS){
					peer[i] = j;
				}
			}
		}
		
		int[] twoway = new int[N];
		Arrays.fill(twoway, -1);
		int cntTwoway = 0;
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				if(i == j) continue;
				
				// 만나는 차가 있나?
				if(Math.abs(p[j]-p[i]-5) < EPS && S[j] < S[i]){
					print("i:"+i+", j:"+j+", pi:"+peer[i]+", pj:"+peer[j]);
					// 추월할 수 없는 경우는?
					if(peer[j] >= 0 && i != peer[j]){
						// 앞이 둘다 막힌 경우
						if(Math.abs(p[peer[j]]-p[j]) < EPS && S[peer[j]] < S[i]) return T[tidx];
						// 차한대가 앞차와 뒷차 모두 이동 못하도록 막는경우
						if(p[peer[j]] < p[j]) return T[tidx];
						// 앞차와 동일한 레인에 있고, 앞차와 뒷차 모두 이동 못하는 경우
						if(cc[i] == cc[j] && peer[i] >= 0 && peer[j] >= 0) return T[tidx];
					}
					if(peer[i] >= 0 && j != peer[i]){
						// 뒷차 두대가 동일한 위치에 있고, 둘의 속도가 모두 앞차보다 빠를때
						if(Math.abs(p[i]-p[peer[i]]) < EPS && S[peer[i]] > S[j]) return T[tidx];
					}
					
					// 추월할 수 있다면 C[] 변경
					if(peer[i] == -1 && peer[j] == -1){
						// 2가지 경우가 가능하다면 따로 loop돌면서 재귀호출할 수 있도록 저장
						twoway[i] = j;
						cntTwoway++;
					}
					else if(peer[i] == -1 && cc[i] == cc[j]) cc[i] = (cc[i]=='L') ? 'R' : 'L';
					else if(peer[j] == -1 && cc[i] == cc[j]) cc[j] = (cc[j]=='L') ? 'R' : 'L';
				}
			}
		}
		
		// 여기까지 왔다면 모든차가 추월가능, 즉 막히지 않은 상태. 다음시간에 대하여 재귀적으로 확인
		double result = 0;
		if(cntTwoway > 0){
			int[] twowayKeys = new int[cntTwoway];
			int tmpIdx = 0;
			for(int i=0; i<N; i++) if(twoway[i] >= 0) twowayKeys[tmpIdx++] = i;
			for(int i=(1<<cntTwoway)-1; i>=0; i--){
				for(int j=0; j<cntTwoway; j++){
					if((i & (1<<j)) > 0){
						cc[twowayKeys[j]] = 'L';
						cc[twoway[twowayKeys[j]]] = 'R';
					}
					else{
						cc[twowayKeys[j]] = 'R';
						cc[twoway[twowayKeys[j]]] = 'L';
					}
				}
				result = Math.max(result, process(tidx+1, cc));
			}
		}
		else{
			result = process(tidx+1, cc);
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C_todo().goodluck();
			
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
