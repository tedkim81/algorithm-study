package gcj.y2012.round1c;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem B. Out of Gas
 * 
 * 문제를 쉽게 풀었다고 생각했으나, Sample의 3번째 케이스가 10141.4213562 라고 나왔다. (정답은 10140.974143 )
 * 차이가 아주 크지 않은걸로 보아 자료구조의 범위를 벗어난 문제는 아닌 듯 하고, 차이가 아주 작지 않은걸로 보아 게산과정에서 값이 유실돼서 발생한 문제도 아닌 듯 했다.
 * 내가 생각한 방식 : t=0일때 출발하여 중간에 다른차와 만난다면 다른차 속도로 뒤따라가고, 만나지 않는다면 만날때까지 가속도 a 적용하여 이동한다.
 *                 N개의 구간에서 다른차가 속도가 달라질 수 있기 때문에 모든 구간을 순환하면서 현재위치와 속도를 갱신한다.
 *                 
 * 알고리즘상의 오류를 발견하지 못하여 analysis 를 확인했다.
 * 내가 생각했던 방식은 최선이 아니었다. x축을 시간으로 y축을 거리로 하는 그래프를 그려서 확인해보면 최초에 어느정도 기다렸다가 다른차와 만나지 않고
 * 계속 가속도 a를 이용하여 달리는 것이 더 빠른시간에 D까지 이동할 수 있다는 것을 알 수 있다.
 * 수식으로만 증명하기 까다로운 부분이 많다. 따라서 그래프 또는 그림을 그려보는 습관을 더 들이도록 하자.
 * 
 * (a*(t-tmid)^2)/2 인 2차함수 그래프가 다른차 그래프와 만나지 않도록 하는 tmid의 최소값을 구하면 문제를 해결할 수 있다.
 * 구현하면서 한가지 실수를 했다. tmid를 이분법으로 구하는데 right초기값이 초과값으로 설정되는 경우가 있었다. ( x[N-1] >= D )
 * 그리고 이를 수정하여 tt를 구하도록 했는데 x[]가 D보다 큰 경우가 걸러지지 않아 tt가 음수로 나오는 문제가 있었다.
 * 마지막으로 위 오류 수정을 위해 n을 구했는데 이를 collide()에 넘기지 않고 그대로 N을 사용하여 잘못된 값이 출력됐다.
 * 위 오류들을 수정한 후 small 및 large input 에 대하여 정답을 구할 수 있었다.
 * 
 * 이 문제를 풀기 위해 어떻게 생각했어야 할까?
 * 1. 내가 생각한 방식을 증명했어야 했다. 정말 이게 최선인가?
 * 2. 다른 방법이 있을까를 생각했어야 했다. 다른차를 만나지 않고 도착지까지 갈 수 있을까?
 * 3. 그래프를 그려 봤어야 했다. 그랬다면 위 2번에서 생각하지 못한 방법을 찾을 수 있었을 수도 있다.
 * 4. 가능한 방법들 중 어느것이 최선인가를 결정해야 한다.
 */
public class B {
	
	private double D;
	private int N,A;
	private double[] t,x,a;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"B-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			D = sc.nextDouble();
			N = sc.nextInt();
			A = sc.nextInt();
			t = new double[N];
			x = new double[N];
			for(int i=0; i<N; i++){
				t[i] = sc.nextDouble();
				x[i] = sc.nextDouble();
			}
			a = new double[A];
			for(int i=0; i<A; i++) a[i] = sc.nextDouble();
			
			fw.write("Case #"+(casenum+1)+":\n");
			print("Case #"+(casenum+1)+":");
			for(int i=0; i<A; i++){
//				String result = String.format("%.7f", solve(a[i]));
				String result = String.format("%.7f", solve2(a[i]));
//				String result = ""+solve2(a[i]);
				fw.write(result+"\n");
				print(result);
			}
		}
		fw.close();
	}
	
	private double solve2(double ac){
		int n = 0;
		for(; n<N; n++){
			if(x[n] >= D) break;
		}
		if(n < 1){
			return Math.sqrt(2*D/ac);
		}
		else{
			double left = 0;
			// (x[n-1]-x[n-2]) / (t[n-1]-t[n-2]) = (D-x[n-2]) / (tt-t[n-2]) 에서 tt를 구한다.
			double tt = (D-x[n-1])*(t[n]-t[n-1])/(x[n]-x[n-1])+t[n-1];
			double right = tt;
			for(int i=0; i<100; i++){
				double mid = (left+right) / 2.0;
				if(collide(mid, ac, tt, n)) left = mid;
				else right = mid;
			}
			return ((left+right)/2.0) + Math.sqrt(2*D/ac);
		}
	}
	
	private boolean collide(double tmid, double ac, double tt, int n){
		for(int i=0; i<n; i++){
			if(tmid >= t[i]) continue;
			if(ac*(t[i]-tmid)*(t[i]-tmid)/2 > x[i]) return true;
		}
		if(tt > tmid && ac*(tt-tmid)*(tt-tmid)/2 > D) return true;
		return false;
	}
	
	private double solve(double ac){
		double speed = 0;
		double dist = 0;
		double tmpt = 0;
		for(int i=1; i<N-1; i++){
			tmpt = t[i]-t[i-1];
			dist += speed*tmpt + 0.5*ac*tmpt*tmpt;
			speed += ac * tmpt;
			if(dist > x[i]){
				dist = x[i];
				speed = (x[i]-x[i-1]) / tmpt;
			}
		}
//		print("dist:"+dist+", speed:"+speed);
		tmpt = (D-x[N-2]) / ((x[N-1]-x[N-2]) / (t[N-1]-t[N-2]));
		dist += speed*tmpt + 0.5*ac*tmpt*tmpt;
		speed += ac * tmpt;
		double time = t[N-2]+tmpt;
//		print("dist:"+dist+", time:"+time+", tmpt:"+tmpt+", speed:"+speed);
		if(dist >= D){
			return time;
		}
		
		double b = speed * 2;
		double c = (-2) * (D-dist);
		double xx = ((-b) + Math.sqrt(b*b - 4*ac*c)) / (2*ac);
		print("xx:"+xx);
		return time+xx;
	}
	
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
