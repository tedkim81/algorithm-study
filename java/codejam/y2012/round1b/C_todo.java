package gcj.y2012.round1b;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Problem C. Equal Sums
 * 
 * 비트마스크 방법을 사용하여 반복적으로 모든 조합의 합을 저장하면서 이미 저장된 값이 발견되면 값을 리턴하고 종료하는 알고리즘으로 small input을 해결했다.
 * 그러나 이 방법은 large input 에 대해서는 불가능하다. 몇 가지 명제를 찾았다.
 * 1. 정답을 aset과 bset 이라고 가정하면, aset과 bset은 서로 중복되는 원소가 없다.
 * 2. asum + bsum <= totalsum
 * 
 * TODO: 문제 풀고 있는중..
 */
public class C_todo {
	
	private int N;
	private long[] S;
	
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
			S = new long[N];
			for(int i=0; i<N; i++) S[i] = sc.nextLong();
			
			String result = "\n"+solve();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String solve(){
		List<Long> list = new ArrayList<Long>();
		for(int i=0; i<N; i++) list.add(S[i]);
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		for(int i=0; i<1000000; i++){
			Collections.shuffle(list);
			for(int j=0; j<N-6; j+=6){
				long[] set = new long[6];
				long sum = 0;
				for(int k=0; k<6; k++){
					set[k] = list.get(j+k);
					sum += list.get(j+k);
				}
				if(map.containsKey(sum)){
					long[] set2 = map.get(sum);
					
					boolean pass = false;
					for(int k=0; k<6; k++){
						boolean pass2 = false;
						for(int kk=0; kk<6; kk++){
							if(set[k] == set2[kk]){
								pass2 = true;
								break;
							}
						}
						if(pass2 == false){
							pass = true;
							break;
						}
					}
					if(pass == false) continue;
					
					String result = "";
					for(int k=0; k<6; k++) result += set[k]+" ";
					result += "\n";
					for(int k=0; k<6; k++) result += set2[k]+" ";
					return result;
				}
				map.put(sum, set);
			}
		}
		return "Impossible";
	}
	
	/*
	private String solve(){
		TreeSet<Integer> set = new TreeSet<Integer>();
		int end = (1<<20);
		int[] map = new int[end];
		for(int i=0; i<end; i++){
			int sum = 0;
			for(int j=0; j<N; j++){
				if(((1<<j) & i) > 0) sum += S[j];
			}
			if(set.contains(sum)){
				for(int k=0; k<end; k++){
					if(map[k] == sum){
						String result = "";
						for(int kk=0; kk<N; kk++){
							if(((1<<kk) & k) > 0) result += S[kk]+" ";
						}
						result += "\n";
						for(int kk=0; kk<N; kk++){
							if(((1<<kk) & i) > 0) result += S[kk]+" ";
						}
						return result;
					}
				}
			}
			set.add(sum);
			map[i] = sum;
		}
		return "Impossible";
	}
	*/
	
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
