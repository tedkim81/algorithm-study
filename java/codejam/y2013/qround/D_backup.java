package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Qualification Round 2013 : Problem D. Treasure
 */
public class D_backup {
	
	private int K,N;
	private int[] startK,Ti,Ki;
	private TreeSet<Integer>[] ChestSet;
	private List<Integer>[] KiList;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-small-attempt1.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-small-attempt1.out.txt");
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
			KiList = new ArrayList[N+1];
			KiList[0] = new ArrayList<Integer>();
			for(int i=0; i<K; i++) KiList[0].add(startK[i]);
			for(int i=1; i<=N; i++){
				Ti[i] = sc.nextInt();
				Ki[i] = sc.nextInt();
				KiList[i] = new ArrayList<Integer>();
				for(int j=0; j<Ki[i]; j++) KiList[i].add(sc.nextInt());
			}
			ChestSet = new TreeSet[41];
			for(int i=1; i<41; i++){
				ChestSet[i] = new TreeSet<Integer>();
				for(int j=1; j<=N; j++){
					if(Ti[j] == i) ChestSet[i].add(j);
				}
			}
			
			try{
//				List<Integer> list = unlock(0, 0, new boolean[N+1], KiList[0]);
//				List<Integer> list = unlock2(new boolean[N+1], 0, KiList[0]);
				int[] keycnts = new int[41];
				for(int i : KiList[0]) keycnts[i]++;
				List<Integer> list = unlock2(new boolean[N+1], 0, keycnts);
				
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
			}catch(Exception e){
				fw.close();
				throw e;
			}
		}
		fw.close();
	}
	
	private List<Integer> unlock(int chest, int visitcnt, boolean[] visited, List<Integer> keylist){
		print("chest:"+chest+", visitcnt:"+visitcnt+", keylist:"+keylist);
		if(visitcnt == N) return new ArrayList<Integer>();
		
		Map<Integer, List<Integer>> result = new TreeMap<Integer, List<Integer>>();
		visited[chest] = true;
		
		while(keylist.size() > 0){
			int key = keylist.remove(0);
			
			TreeSet<Integer> chestset = ChestSet[key];
//			print("key:"+key+", chestset:"+chestset);
			List<Integer> nextlist = new ArrayList<Integer>();
			for(int nextchest : chestset){
				if(visited[nextchest]) continue;
				
				visited[nextchest] = true;
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.add(nextchest);
				List<Integer> nextkeylist = new ArrayList<Integer>(keylist);
				nextkeylist.addAll(KiList[nextchest]);
				tmp.addAll(unlock(nextchest, visitcnt+1, visited, nextkeylist));
				visited[nextchest] = false;
				
//				print("nextlist:"+nextlist+", tmp:"+tmp);
//				if(key == 6) print("nextchest:"+nextchest+", nextlist:"+nextlist+", tmp:"+tmp+", visitcnt:"+visitcnt+", chestset:"+chestset);
				if(nextlist.size() < tmp.size()){
					nextlist = tmp;
				}
			}
			if(nextlist.size() > 0){
				for(int nextchest : nextlist) visited[nextchest] = true;
				result.put(nextlist.get(0), nextlist);
			}
		}
		
		List<Integer> sumlist = new ArrayList<Integer>();
		for(List<Integer> list : result.values()){
			sumlist.addAll(list);
		}
		
		return sumlist;
	}
	
	private Map<String, List<Integer>> memo = new HashMap<String, List<Integer>>();
	private List<Integer> unlock2(boolean[] visited, int visitcnt, int[] keycnts){
		if(visitcnt == N) return new ArrayList<Integer>();
		
		String memokey = getMemoKey(visited);
		if(memo.containsKey(memokey)) return memo.get(memokey);
		
		List<Integer> result = new ArrayList<Integer>();
		for(int chest=1; chest<=N; chest++){
			if(visited[chest]) continue;
			if(keycnts[Ti[chest]] == 0) continue;
			
			keycnts[Ti[chest]] --;
			visited[chest] = true;
			for(int nextkey : KiList[chest]) keycnts[nextkey]++;
			
			List<Integer> tmp = new ArrayList<Integer>();
			tmp.add(chest);
			tmp.addAll(unlock2(visited, visitcnt+1, keycnts));
			
			for(int nextkey : KiList[chest]) keycnts[nextkey]--;
			visited[chest] = false;
			keycnts[Ti[chest]] ++;
			
			if(result.size() < tmp.size()){
				result = tmp;
				if(visitcnt+result.size() == N) break;
			}
		}
		
		memo.put(memokey, result);
		
		return result;
	}
	
	private String getMemoKey(boolean[] visited){
		String result = "";
		for(int i=0; i<visited.length; i++) result += visited[i] ? "1" : "0";
		return result;
	}
	
	private List<Integer> unlock3(){
		boolean[] discovered = new boolean[N+1];
		LinkedList<Integer> q = new LinkedList<Integer>();
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> keylist = KiList[0];
		q.push(0);
		while(q.isEmpty() == false){
			int curr = q.pop();
			for(int chest=1; chest<=N; chest++){
				if(discovered[chest]) continue;
				if(keylist.contains(Ti[chest]) == false) continue;
				
				q.push(chest);
				discovered[chest] = true;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new D_backup().goodluck();
			
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
