package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Qualification Round 2013 : Problem D. Treasure
 */
public class D_backup2 {
	
	private int K,N;
	private int[] startK,Ti,Ki;
	private List<Integer>[] KiList;
	private List<Integer>[] memo;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-small-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			K = sc.nextInt();
			N = sc.nextInt();
			startK = new int[K];
			for(int i=0; i<K; i++) startK[i] = sc.nextInt();
			Ti = new int[N+1];
			Ki = new int[N+1];
			KiList = new List[N+1];
			KiList[0] = new ArrayList<Integer>();
			for(int i=0; i<K; i++) KiList[0].add(startK[i]);
			for(int i=1; i<=N; i++){
				Ti[i] = sc.nextInt();
				Ki[i] = sc.nextInt();
				KiList[i] = new ArrayList<Integer>();
				for(int j=0; j<Ki[i]; j++) KiList[i].add(sc.nextInt());
			}
			memo = new List[(1 << 20)];
			
			int[] keycnts = new int[41];
			for(int i : KiList[0]) keycnts[i]++;
			List<Integer> list = unlock(0, 0, keycnts);
			
			String result = "IMPOSSIBLE";
			if(list.size() == N){
				result = "";
				for(int i : list){
					result += i+" ";
				}
				result = result.substring(0, result.length()-1);
			}
			
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private List<Integer> unlock(int visited, int visitcnt, int[] keycnts){
		if(visitcnt == N) return new ArrayList<Integer>();
		
		if(memo[visited] != null) return memo[visited];
		
		List<Integer> result = new ArrayList<Integer>();
		for(int chest=1; chest<=N; chest++){
			if((visited & (1 << (chest-1))) > 0) continue;
			if(keycnts[Ti[chest]] == 0) continue;
			
			keycnts[Ti[chest]] --;
			visited += (1 << (chest-1));
			for(int nextkey : KiList[chest]) keycnts[nextkey]++;
			
			List<Integer> tmp = new ArrayList<Integer>();
			tmp.add(chest);
			tmp.addAll(unlock(visited, visitcnt+1, keycnts));
			
			for(int nextkey : KiList[chest]) keycnts[nextkey]--;
			visited -= (1 << (chest-1));
			keycnts[Ti[chest]] ++;
			
			if(result.size() < tmp.size()){
				result = tmp;
				if(visitcnt+result.size() == N) break;
			}
		}
		
		memo[visited] = result;
		
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new D_backup2().goodluck();
			
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
