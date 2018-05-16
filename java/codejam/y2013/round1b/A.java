package gcj.y2013.round1b;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class A {
	
	private int A,N;
	private List<Integer> list;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"A-large.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"A-large.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			A = sc.nextInt();
			N = sc.nextInt();
			list = new ArrayList<Integer>();
			for(int i=0; i<N; i++) list.add(sc.nextInt());
			Collections.sort(list);
			
			String result = ""+solve(0, A);
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int solve(int idx, int mote){
		if(idx == N) return 0;
		
		int n = list.get(idx);
		if(mote > n){
			mote += n;
			return solve(idx+1, mote);
		}
		else{
			int result = N-idx;
			int j = 0;
			while(mote-1 > 0 && mote <= n){
				mote += mote-1;
				j++;
			}
			if(mote > n){
				result = Math.min(result, j+solve(idx+1, mote+n));
			}
			return result;
		}
	}
	
	/*
	private int solve(){
		Collections.sort(list);
		int result = 0;
		int result2 = Integer.MAX_VALUE;
		int mote = A;
		for(int i=0; i<N; i++){
			int n = list.get(i);
			if(mote > n) mote += n;
			else{
				int remain = N-i;
				int j = 0;
				while(mote-1 > 0 && mote <= n){
					mote += mote-1;
					j++;
				}
				if(mote <= n || j >= remain){
					return Math.min(result2, result+remain);
				}
				result2 = Math.min(result2, result+remain);
				result += j;
			}
		}
		return Math.min(result, result2);
	}
	*/
	
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
