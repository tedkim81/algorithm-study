package gcj.y2012.round1c;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class A {
	
	private int N;
	private boolean[][] M;
	private boolean result;
	
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
			M = new boolean[N][N];
			for(int i=0; i<N; i++){
				int cnt = sc.nextInt();
				for(int j=0; j<cnt; j++){
					M[i][sc.nextInt()-1] = true;
				}
			}
			
			result = false;
			for(int i=0; i<N; i++) solve(i, new boolean[N]);
			
			fw.write("Case #"+(casenum+1)+": "+(result?"Yes":"No")+"\n");
			print("Case #"+(casenum+1)+": "+(result?"Yes":"No"));
		}
		fw.close();
	}
	
	private void solve(int idx, boolean[] visited){
		if(result) return;
		
		visited[idx] = true;
		for(int i=0; i<M[idx].length; i++){
			if(M[idx][i] == false) continue;
			if(visited[i]){
				result = true;
				return;
			}
			solve(i, visited);
		}
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
