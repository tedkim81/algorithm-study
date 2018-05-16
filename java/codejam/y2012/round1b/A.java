package gcj.y2012.round1b;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem A. Safety in Numbers
 * 
 * 문제 해결하는데 거의 1시간 가까이 걸렸다. 가장 쉬운 문제인데 왜이렇게 오래 걸리는걸까?
 * 일단 한차례 오답처리가 있었다. 개인총점이 평균이상이 되도록 하면 된다는 것을 확인했고, Y의 최소값을 계산하여 결과로 출력했다.
 * 여기에 문제가 있었다. Y가 음수가 되는 경우가 발생하는 것이다. 미리 Y의 범위가 어떻게 될지를 확인했어야 했다.
 * 이것을 해결하기 위해 애매한 방식을 한차례 택했다. 음수인 값을 0으로 만들면서 나머지 값들에 보정값을 나눠주는 방식이었다. 
 * 그러나 이 방식으로 할때 보정값 나눠주다가 양수였던 값이 음수로 바뀌는 문제가 있었고 이를 해결하는 것이 다소 복잡해 질 수 있겠다 판단하여 현재의 알고리즘으로 수정했다.
 * Y값이 음수가 되는 경우들은 0으로 정하고 나머지들에 대하여 다시 계산하는 방식으로 문제를 해결했다.
 * 
 * 쉬운 문제에 오히려 더 집중해야 한다. 시간을 절약해야하기 때문이다.
 * 증명되지 않은 방식으로 애매하게 구현하는 것은 오히려 시간을 더 소모하게 된다.
 * 쉬운 문제는 증명도 대체로 쉽다. 따라서 증명하는데에 망설이지 말자.
 */
public class A {
	
	private int N;
	private int[] S;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"A-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			S = new int[N];
			for(int i=0; i<N; i++) S[i] = sc.nextInt();
			
			String result = solve();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String solve(){
		double[] results = new double[N];
		Arrays.fill(results, -1);
		int X = 0;
		for(int i=0; i<N; i++) X += S[i];
		
		int sum = 2*X;
		int cnt = N;
		while(true){
			if(cnt == 0) break;
			double avg = sum / (double)cnt;
			boolean end = true;
			for(int i=0; i<N; i++){
				if(results[i] == 0) continue;
				results[i] = ((avg-S[i]) / X) * 100;
				if(results[i] < 0){
					end = false;
					results[i] = 0;
					sum -= S[i];
					cnt--;
				}
			}
			if(end) break;
		}
		
		String result = "";
		for(int i=0; i<N; i++){
			result += String.format("%.6f ", results[i]);
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new A().goodluck();
			
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
