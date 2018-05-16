package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Qualification Round 2013 : Problem D. Treasure
 * 보물상자가 N개 있고, 처음에 열쇠를 K개 가지고 시작하며 열쇠는 한번만 쓸수있고 상자안에 열쇠들이 또 있다고 할때 상자를 모두 여는 방법(여는순서)을 묻는 문제
 * 
 * 상당히 어려웠던 문제이다. 본 게임때 풀지 못했고, Contest Analysis 를 보며 이틀동안 쩔쩔맨 문제이다.
 * 그러나 이 문제를 풀면서 문제에 대한 접근 방식을 하나더 배웠다.
 * 이 문제를 풀려면 내가 어떻게 이 문제에 접근했어야했나 되짚어보자.
 * 
 * 1. small input에 대해서는 문제 그대로 시뮬레이션 하면서 동적계획법을 적용하여 풀었다. 
 * opened[]가 최대 20개밖에 되지 않기 때문에 비트마스크 기법 사용하여 메모이제이션을 할 수 있었다.
 * 그러나, large input에서는 opened[]가 200개이기 때문에 정상적으로 메모이제이션을 할 수 없다.
 * 
 * 2. 재귀호출 메소드의 입력 범위를 줄일 수 있을까? 
 * 동일한 큰줄기 내에서 다른 방법을 찾아보고 그것이 가능한지를 검토하자.( 여기서 중요한 한가지! "가능성 검토"를 빨리 할 수 있도록 노력해야 한다. )
 * 만약, 별다른 방법을 찾지 못하겠다면 아예 다른 방향을 생각해 보자.
 * 
 * 3. 부분문제로 나누거나, 그래프로 만들어서 탐색하는 방법으로는 방법을 찾지 못하겠다면, "탐욕법"을 고민해보자.
 * 경우의 수들을 모두 탐색해보는 방식으로 할 수 없다면, 현재 상태에서 최선의 답을 찾을 수 있는지 확인해 봐야 한다.
 * 
 * 4. 현재 가지고 있는 key, 남아있는 상자들(얻을 수 있는 키들) 정보를 가지고 지금 열어야 하는 상자를 바로 알 수 있을까?
 * 현재 상태에서 상자를 모두 열 수 있는지 여부(boolean)는 어떻게 알 수 있을까? 어떤 조건들이 만족되어야 상자를 모두 열수 있다고 확신할 수 있을까?
 * 그리고 여기서 두가지 조건을 생각해 낼 수 있어야 한다. ( 이건 끊임없는 학습 및 연습으로 노력해야 할 부분이다. )
 * 1) 각 키의 갯수가 충분히 있어야 한다. 즉, needkeys[] <= haskeys[] + rkey[] 을 만족해야 한다. 이것은 최초 1회만 확인하면 된다.
 * 2) 상자 열때 필요한 모든 키가 "연결"되어 있어야 한다. 즉, 도달하지 못하는 키가 있으면 안된다. 이는 haskeys[]를 시작으로 너비우선탐색을 이용하여 확인할 수 있다.
 * 
 * 5. 가지고 있는 키를 가지고 열 수 있는 여러 상자를 번호 순서대로 연다고 가정하고 위 조건을 만족하는지 확인한다.
 * 이런식으로 O(N)번에 결과 하나씩을 얻어낼 수 있다. 결과적으로 이중반복문을 사용하면 될 것이다.( O(N^2) )
 * 그리고 위의 4-2) 조건은 N개 정도의 V와 rkey[]의 sum 정도의 E를 갖는 그래프의 너비우선탐색 수행시간이 걸리므로 대략 O(N)정도라 할 수 있겠다.
 * 그러면 결론적으로 수행시간은 O(N^3) 이고 이는 대략 최대 8백만 정도니까 충분히 풀 수 있는 문제가 된다.
 */
public class D {
	
	private int K,N;
	private int[] skey,T,ki;
	private List<Integer>[] rkey;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/D-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			K = sc.nextInt();
			N = sc.nextInt();
			skey = new int[K];
			for(int i=0; i<K; i++) skey[i] = sc.nextInt();
			T = new int[N];
			ki = new int[N];
			rkey = new List[N];
			for(int i=0; i<N; i++){
				T[i] = sc.nextInt();
				ki[i] = sc.nextInt();
				rkey[i] = new ArrayList<Integer>();
				for(int j=0; j<ki[i]; j++) rkey[i].add(sc.nextInt());
			}
			
			int[] needkeys = new int[201];
			for(int i=0; i<N; i++){
				needkeys[T[i]]++;
			}
			int[] recvkeys = new int[201];
			int[] haskeys = new int[201];
			int hascnt = 0;
			for(int i=0; i<K; i++){
				recvkeys[skey[i]]++;
				haskeys[skey[i]]++;
				hascnt++;
			}
			for(int i=0; i<N; i++){
				for(int rk : rkey[i]){
					recvkeys[rk]++;
				}
			}
			
			boolean success = true;
			for(int i=1; i<201; i++){
				if(needkeys[i] > recvkeys[i]){
					success = false;
					break;
				}
			}
			
			String result = "IMPOSSIBLE";
			if(success){
				List<Integer> resultList = new ArrayList<Integer>();
				boolean[] opened = new boolean[N];
				for(int i=0; i<N; i++){
					for(int j=0; j<N; j++){
						if(opened[j]) continue;
						
						int key = T[j];
						if(haskeys[key] == 0) continue;
						
						haskeys[key]--; needkeys[key]--;
						for(int rk : rkey[j]) haskeys[rk]++;
						opened[j] = true;
						
						if(isConnected(haskeys, needkeys, opened)){
							resultList.add(j+1);
							break;
						}
						
						haskeys[key]++; needkeys[key]++;
						for(int rk : rkey[j]) haskeys[rk]--;
						opened[j] = false;
					}
				}
				if(resultList.size() == N){
					result = "";
					for(int res : resultList) result += res+" ";
					result = result.substring(0, result.length()-1);
				}
			}
			
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private boolean isConnected(int[] haskeys, int[] needkeys, boolean[] opened){
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[201];
		
		for(int i=1; i<201; i++) if(haskeys[i] > 0){
			q.push(i);
			checked[i] = true;
		}
		while(q.isEmpty() == false){
			int curr = q.pop();
			checked[curr] = true;
			for(int i=0; i<N; i++){
				if(opened[i] || T[i] != curr) continue;
				for(int rk : rkey[i]){
					if(checked[rk] == false) q.push(rk);
				}
			}
		}
		for(int i=1; i<201; i++){
			if(needkeys[i] > 0 && checked[i] == false) return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new D().goodluck();
			
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
