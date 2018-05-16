package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code Jam Korea 2012 예선 라운드 : 문제 C 약속장소 정하기
 * N개의 도시가 M개의 도로로 연결되어 있을때 친구들이 한 도시에 모일려면 걸리는 최소한의 시간을 구하는 문제
 * 
 * 최단경로 맵을 구하고, 모든 도시에 대하여 모든 친구들의 이동시간합의 최소값을 구하는 문제로 이해했다.
 * 최단경로 맵을 만드는 방법으로, 일단 완전탐색을 사용했다. a1에서 a2까지의 최단경로를 구하기 위해서는 a1에 연결된 도시들로 계속 이동하면서 a2가 나올때까지
 * 재귀호출하고 그 결과들의 최소값을 구하는 것이었다. test input에 대해서는 정답과 일치했다.
 * 그러나.. small input 에 대해서는 실행이 끝나지 않았다. 무한반복 상황인지 처리시간이 너무 길어서인지 판단해야 했는데, 일단은 무한반복일거라고 생각했다.
 * 그리고 오류를 찾으려고 오랜 시간 노력했으나 결국은 삽질이라는 것을 깨달았다. 시간이 오래 걸리는 부분은 입력값이 N 100, P 10, M 100 인 경우였는데
 * 이것은 100개의 도시가 25개 정도의 연결된 도로를 갖는 상황이었고, K<25 인 K의 100제곱정도가 된다는 것을 알았다.
 * 대회분석을 확인하니 Floyd-Warshall 알고리즘과 Dijkstra 알고리즘을 알아야 했다.
 * 
 * 해당 알고리즘을 학습하고 문제를 다시 풀어보도록 하자.
 * 
 */
public class CodeJamKorea2012_Q_C_todo {
	
	private Map<Integer, Integer> pathMap;  // 도시 ci와 cj가 연결된 거리를 확인
	private Map<Integer, List<Integer>> nearMap;  // 도시 c가 주변의 어떤 도시들과 연결되어 있는지 확인
	private int N;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			N = Integer.parseInt(strs[0]);  // 도시수
			int P = Integer.parseInt(strs[1]);  // 친구수
			int M = Integer.parseInt(strs[2]);  // 도로수
			int[] X = new int[P];
			int[] V = new int[P];
			
			for(int j=0; j<P; j++){
				strs = br.readLine().split(" ");
				X[j] = Integer.parseInt(strs[0]);  // 친구가 출발하는 도시
				V[j] = Integer.parseInt(strs[1]);  // 1만큼 이동하는데 걸리는 시간
			}
			
			pathMap = new HashMap<Integer, Integer>();
			nearMap = new HashMap<Integer, List<Integer>>();
			for(int j=0; j<M; j++){
				strs = br.readLine().split(" ");
				int D = Integer.parseInt(strs[0]);
				int L = Integer.parseInt(strs[1]);
				int prevCity = Integer.parseInt(strs[2]);
				if(nearMap.containsKey(prevCity) == false) nearMap.put(prevCity, new ArrayList<Integer>());
				for(int k=3; k<L+2; k++){
					int C = Integer.parseInt(strs[k]);
					pathMap.put(getMapKey(prevCity, C), D);
					if(nearMap.containsKey(C) == false) nearMap.put(C, new ArrayList<Integer>());
					nearMap.get(prevCity).add(C);
					nearMap.get(C).add(prevCity);
					prevCity = C;
				}
			}
//			System.out.println("pathMap: "+pathMap);
//			System.out.println("nearMap: "+nearMap);
			
			long[] costs = new long[N];
			for(int j=0; j<P; j++){
				for(int k=0; k<N; k++){
					System.out.println("s:"+X[j]+", e:"+(k+1)+", v:"+V[j]);
					lognum = 0;
					long cost = sum(findPath(X[j], k+1, new ArrayList<Integer>())) * V[j];
					System.out.println("cost:"+cost);
					if(cost == 0 && X[j] != k+1) costs[k] = -1;
					else if(costs[k] >= 0) costs[k] = costs[k] + cost;
				}
			}
			long result = Long.MAX_VALUE;
			for(int j=0; j<N; j++){
				if(result > costs[j]) result = costs[j];
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int getMapKey(int c1, int c2){
		if(c1 > c2) return 10000*c2 + c1;
		else return 10000*c1 + c2;
	}
	
	private int lognum = 0;
	private List<Integer> findPath(int start, int end, List<Integer> paths){
		if(lognum++ < 1000) System.out.println("num:"+lognum+", s:"+start+", e:"+end+", paths:"+paths+", size:"+paths.size());
		List<Integer> result = null;
		if(start == end){
			result = new ArrayList<Integer>();
			result.add(start);
			return result;
		}
		if(paths.size() == N-1){
			return null;
		}
		
		List<Integer> nearList = nearMap.get(start);
		if(nearList == null) return result;
		paths = new ArrayList<Integer>(paths);
		paths.add(start);
		for(int nearCity : nearList){
			if(paths.contains(nearCity)) continue;
			List<Integer> path = findPath(nearCity, end, paths);
			if(path == null) continue;
			path.add(start);
			result = min(result, path);
		}
		return result;
	}
	
	private List<Integer> min(List<Integer> lista, List<Integer> listb){
		if(lista == null) return listb;
		if(listb == null) return lista;
		if(sum(lista) < sum(listb)) return lista;
		return listb;
	}
	
	private long sum(List<Integer> list){
		long sum = 0;
		int prev = 0;
		if(list != null){
			for(int city : list){
				int key = getMapKey(prev, city);
				if(pathMap.containsKey(key)) sum += pathMap.get(key);
				prev = city;
			}
		}
		return sum;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_Q_C_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
