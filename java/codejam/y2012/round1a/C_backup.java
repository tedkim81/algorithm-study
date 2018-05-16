package gcj.y2012.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Problem C. Cruise Control
 * 
 * 1. 모든 가능한 조합에 대하여 두 차가 만나는 시간들을 정렬한다.
 * 2. 각 시간들에 대하여 모든차의 현재위치를 업데이트한다.
 * 3. 다른 레인으로 이동가능 여부와 추월해야할차를 만났는지 여부를 확인하여 추월상황에 레인 이동이 되면 이동시키고 안되면 그때의 시간을 리턴한다.
 * 4. 테스트 케이스를 만족하는 수준으로 코딩은 했으나 small input을 해결하지는 못했다.
 * 5. 1시간이 넘었기 때문에 solution을 확인했고, 차이점을 통해 오류를 찾으려 했으나 그렇지 못했다.
 * ( 30개의 케이스 중에 7개가 틀렸다. 해결방법 자체는 가능한 방법인데 누락된 조건이 있는 상황이라고 판단된다. 일단은 solution(C_other)를 이해하자. )
 * 
 * 아.. 첫번째로 깨달은 사실하나, 가장 합리적인 상황을 가정해야 한다는 것이다. 뒷차만 피할 수 있는 것이 아니라 앞차도 비켜줄 수 있다.
 * 예를 들어 입력이, (L 30 100) , (R 30 95) , (L 50 0) 이라고 가정하면 앞차가 비켜주지 않는 경우 뒷차가 95미터까지 가서 결국은 속도를 줄여야겠지만
 * 앞차 둘중에 하나가 비켜주면 지나갈 수 있다.
 * 
 * 위 문제를 해결했더니 이번엔 다른 문제가 발견됐다. (L 100 0) , (R 100 0) , (L 50 505) 와 같이 2대가 동시에 추격해 오는 경우다.
 * 앞차가 비켜주면 동시에 다른차와 문제가 생기는데 그것이 반영되지 않았다.
 * 
 * 위 문제를 해결해도 문제가 남아있었다. t>0 인 어느 순간에 1:(L 15 100) , 2:(R 16 97) , 3:(L 15 91) , 4:(R 18 92) 에서
 * 4번이 2번과 만난 상황이라고 가정하자. 현재 알고리즘에서는 이때 4번은 3번 때문에 이동을 못하고 2번은 1번 때문에 이동을 못하기 때문에 t를 리턴한다.
 * 그러나 t보다 전에 3번이 R로 이동해 있었다면? 4번은 L에서 1번과 만나게 될 것이며 t보다 더 큰값을 리턴하게 될 것이다.
 * 이것은 현재 알고리즘에서 단순하게 변경할 수 있는 부분이 아니다. t 전에 3번이 R로 이동할 수 있는지 확인해야 하는데 현재 알고리즘은 t에 대한 루프를
 * 한번만 돌기 때문이다. 
 * 이 문제로 인하여 small input 30개 중 1개만 제대로 값을 구하지 못하고 있다.
 * 이제 내 알고리즘의 문제는 파악됐다. 다시 solution을 확인하고 어떤 알고리즘을 사용해야 일반적으로 해결할 수 있을지 알아보자.
 */
public class C_backup {
	
	private int N;
	private char[] C;
	private int[] S;
	private double[] P;
	private static final double EPS = 1e-5;
	
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
			
			N = sc.nextInt();
			C = new char[N];
			S = new int[N];
			P = new double[N];
			for(int i=0; i<N; i++){
				C[i] = sc.next().charAt(0);
				S[i] = sc.nextInt();
				P[i] = sc.nextInt();
			}
			
			double result = getResult();
			String res;
			if(result >= 0){
				res = "Case #"+(casenum+1)+": "+String.format("%.6f", result);
			}
			else{
				res = "Case #"+(casenum+1)+": Possible";
			}
			fw.write(res+"\n");
			print(res);
		}
		fw.close();
	}
	
	private double getResult(){
		TreeSet<Double> times = new TreeSet<Double>();
		for(int i=0; i<N; i++){
			for(int j=i+1; j<N; j++){
				if(Math.abs(P[i]-P[j]) < 5) continue;
				
				double t = -1;
				if(P[i] > P[j]) t = (P[i]-P[j]-5) / (double)(S[j]-S[i]);
				else if(P[i] < P[j]) t = (P[j]-P[i]-5) / (double)(S[i]-S[j]);
				if(t >= 0) times.add(t);
			}
		}
		double[] cp = new double[N];
		for(int i=0; i<N; i++) cp[i] = P[i];
		
//		for(int k=0; k<N; k++){
//			print("C:"+C[k]+", CP:"+(P[k] + (S[k] * 1.4))+", S:"+S[k]);
//		}
//		for(double t : times){
//			print("t:"+t);
//		}
		
		double result = -1;
		for(double t : times){
			for(int i=0; i<N; i++) cp[i] = P[i] + (S[i] * t);
			for(int i=0; i<N; i++){
				int previdx = -1;
				for(int j=0; j<N; j++){
					if(i==j) continue;
					if(cp[i] < cp[j] && cp[j]-cp[i] < 5+EPS && C[i] == C[j] && S[i] > S[j]){
						previdx = j;
						break;
					}
				}
				if(previdx >= 0){
					boolean blocked = false;
					for(int j=0; j<N; j++){
						if(Math.abs(cp[previdx]-cp[j]) < EPS && C[previdx] != C[j]){
							blocked = true;
							break;
						}
					}
					if(blocked == false){
						for(int j=0; j<N; j++){
							if(Math.abs(cp[i]-cp[j]) < EPS && C[i] != C[j] && S[j] > S[previdx]){
								blocked = true;
								break;
							}
						}
					}
					boolean canChange = true;
					for(int j=0; j<N; j++){
						if(i==j) continue;
						if(Math.abs(cp[i]-cp[j]) < 5 && C[i] != C[j]){  // TODO: EPS 관련하여 확인필요
							canChange = false;
							break;
						}
					}
					boolean canChangePrev = true;
					for(int j=0; j<N; j++){
						if(previdx==j) continue;
						if(Math.abs(cp[previdx]-cp[j]) < 5 && C[previdx] != C[j]){  // TODO: EPS 관련하여 확인필요
							canChangePrev = false;
							break;
						}
					}
					if(blocked == false && canChange){
						C[i] = (C[i]=='L') ? 'R' : 'L';
					}
					else if(blocked == false && canChangePrev){
						C[previdx] = (C[previdx]=='L') ? 'R' : 'L';
					}
					else{
						print("i:"+i+", previdx:"+previdx);
						for(int k=0; k<N; k++){
							print("idx:"+k+", C:"+C[k]+", CP:"+cp[k]+", S:"+S[k]);
						}
						result = t;
						return result;
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C_backup().goodluck();
			
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
