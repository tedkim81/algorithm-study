package gcj.y2013.round1c;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class C {
	
	private int N;
	private int[] d,n,w,e,s,d_d,d_p,d_s;
	private int[] wall;
	private static final int OFFSET = 500;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"C-small-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			d = new int[N];
			n = new int[N];
			w = new int[N];
			e = new int[N];
			s = new int[N];
			d_d = new int[N];
			d_p = new int[N];
			d_s = new int[N];
			for(int i=0; i<N; i++){
				d[i] = sc.nextInt();
				n[i] = sc.nextInt();
				w[i] = sc.nextInt();
				e[i] = sc.nextInt();
				s[i] = sc.nextInt();
				d_d[i] = sc.nextInt();
				d_p[i] = sc.nextInt();
				d_s[i] = sc.nextInt();
			}
			wall = new int[1000];
			
			String result = ""+solve();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int solve(){
		int result = 0;
		while(true){
			// 공격이 모두 종료되었다면 나간다.
			boolean ended = true;
			for(int i=0; i<N; i++){
				if(n[i] > 0){
					ended = false;
					break;
				}
			}
			if(ended) break;
			
			// 공격날짜 가장 작은것을 찾는다.
			int min = Integer.MAX_VALUE;
			for(int i=0; i<N; i++){
				if(n[i] > 0 && d[i] < min) min = d[i];
			}

			for(int idx=0; idx<N; idx++){
				if(n[idx] <= 0 || d[idx] != min) continue;
				
				// 공격 실행
				for(int i=w[idx]; i<e[idx]; i++){
					if(wall[i+OFFSET] < s[idx]){
						result++;
						break;
					}
				}
			}
			
			for(int idx=0; idx<N; idx++){
				if(n[idx] <= 0 || d[idx] != min) continue;
				
				for(int i=w[idx]; i<e[idx]; i++){
					if(wall[i+OFFSET] < s[idx]) wall[i+OFFSET] = s[idx];
				}
				
				// 적 정보 업데이트
				d[idx] += d_d[idx];
				n[idx] -= 1;
				w[idx] += d_p[idx];
				e[idx] += d_p[idx];
				s[idx] += d_s[idx];
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C().goodluck();
			
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
