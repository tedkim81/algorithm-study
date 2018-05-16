package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Qualification Round 2013 : Problem B. Lawnmower
 */
public class B {
	
	private int N,M;
	private int[][] start,end;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/B-large.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			M = sc.nextInt();
			start = new int[N][M];
			end = new int[N][M];
			for(int i=0; i<N; i++){
				for(int j=0; j<M; j++){
					start[i][j] = 100;
					end[i][j] = sc.nextInt();
				}
			}
			
			String result = getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String getResult(){
		TreeSet<Integer> hset = new TreeSet<Integer>();
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				hset.add(end[i][j]);
			}
		}
		Iterator<Integer> it = hset.descendingIterator();
		while(it.hasNext()){
			int h = it.next();
			if(cut(h) == false) return "NO";
		}
		return "YES";
	}
	
	private boolean cut(int h){
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(start[i][j] > end[i][j] && start[i][j] > h){
					boolean a = true;
					for(int k=0; k<N; k++){
						if(start[k][j] < end[k][j] || end[k][j] > h){
							a = false;
							break;
						}
					}
					if(a){
						for(int k=0; k<N; k++) 
							if(start[k][j] > h) start[k][j] = h;
					}
					
					boolean b = true;
					for(int k=0; k<M; k++){
						if(start[i][k] < end[i][k] || end[i][k] > h){
							b = false;
							break;
						}
					}
					if(b){
						for(int k=0; k<M; k++) 
							if(start[i][k] > h) start[i][k] = h;
					}
					
					if(!a && !b) return false;
				}
			}
		}
		return true;
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
	
	private static void print(int[][] arr){
		for(int i=0; i<arr.length; i++){
			print(arr[i]);
		}
	}
}
