package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Round 1C 2009 : Problem C. Bribe the Prisoners
 * 2013년 2월 1일 오후 10:31:29
 * 죄수들의 방이 일렬로 되어있고 각 경계에 있는 창문으로 이웃에게만 정보를 전달할 수 있다. 
 * 죄수가 출감할때 소식을 접하고 화가난 죄수들을 달래기 위해 gold coin이 필요하다고 할때 최소금액을 구하는 문제이다.
 * 
 * 알고보면 쉬운 문제였는데 헤맸다. 문제이해 20분, 해결방법 13분, 1차코딩에 약30분 정도 걸렸고 1차시도에 실패했다. 그리고 정답을 제출하는데 약 3시간이 걸렸다.
 * 1차 해결방법의 핵심은 "가장자리를 빨리 쳐내야 한다"는 것이었다. 
 * 나름 귀류법으로 가장자리가 아닌곳을 먼저 나눌 경우 나누어진 조각들도 전체에 대하여 탐색을 해야하지만 가장자리를 나누면 빈 쪽은 그만큼을 탐색할 필요가 없기 때문이었다.
 * 그러나 이 방법이 실패했고, 코드상으로는 별문제가 없는 듯 하여 완전탐색으로 답을 구해보기로 했다.
 * 여기서 완전탐색방법을 아주 대강만 생각하고 1차 해결방법에서의 연결선상에서 처리를 하려다 오히려 굉장한 시간 손실을 봤다.
 * 코드도 막히는 부분만 어떻게든 해결하려고 하다 보니 점점 비상식적으로 작성되었다.
 * 그래서 결국 잠시 생각을 멈췄다가 완전탐색으로 해결할 방법을 다시 찾고 나서야 정답을 제출할 수 있었다.
 * 그리고 오답과 정답을 비교해 보니 3 3에 1 2 3 일 때가 오답에서는 3이었고, 정답에서는 2였다. 
 * 그렇다. 2를 먼저 빼면 1과 3이 빠질때는 코인이 소비되지 않는다. 가장자리를 먼저 빼야한다는 가정이 무너지는 순간이었다.
 * 완전탐색 방식으로 혹시나 하고 large input을 돌려봤다. 역시나 단시간에 결과가 나오지 않았다. 그래서 동적계획법을 적용하기 위해 hashmap을 써봤다.
 * 약 2초 정도 걸렸지만 정답이었다.
 * 
 * 계속 반성하고 있는 부분이지만, 코딩하면서 생각하는것은 굉장히 안좋은 버릇이다.
 * 코딩을 시작하기 전에 어떻게 코딩할지에 대한 설계를 분명히 하자. 변수명까지 정할 필요는 없겠지만 적어도 변수 구성 및 논리적인 부분은 정리를 하고 시작해야 할 것이다.
 * 아니다 싶으면 집착하지 말고 다시 생각하는 훈련을 하자. 코드잼은 대회다. 비상식적으로 푸는 문제는 거의 없을거라고 가정한다.
 * 코딩을 하다가 뭔가 아니다 싶으면 고집부려서 어떻게든 되게하려 하지 말고 처음부터 다시 차근차근 생각해 보도록 하자.
 */
public class CodeJam2009_1C_C {
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		map = new HashMap<Integer, Integer>();
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			int P = Integer.parseInt(strs[0]);
			int Q = Integer.parseInt(strs[1]);
			q = new int[Q];
			strs = br.readLine().split(" ");
			for(int j=0; j<Q; j++){
				q[j] = Integer.parseInt(strs[j]) - 1;
			}
			
			map.clear();
			int result = gold3(0, P-1);
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int[] q;
	
	private int gold(int start, int end, int minIdx, int maxIdx){
		if(minIdx == maxIdx){
			return end-start;
		}
		int res1 = gold(q[minIdx]+1, end, minIdx+1, maxIdx);
		int res2 = gold(start, q[maxIdx]-1, minIdx, maxIdx-1);
		return (end-start) + Math.min(res1, res2);
	}
	
	private int gold2(int start, int end, int minIdx, int maxIdx){
		if(minIdx == -1 || maxIdx == -1){
			return 0;
		}
		if(minIdx == maxIdx){
			return end-start;
		}
		int minResult = 123456789;
		for(int i=minIdx; i<=maxIdx; i++){
			int leftMinIdx, leftMaxIdx, rightMinIdx, rightMaxIdx;
			leftMinIdx = leftMaxIdx = rightMinIdx = rightMaxIdx = -1;
			for(int j=0; j<q.length; j++){
				if(leftMinIdx == -1 && q[j] >= start) leftMinIdx = j;
				if(q[j] < q[i]) leftMaxIdx = j;
				if(rightMinIdx == -1 && q[j] > q[i]) rightMinIdx = j;
				if(q[j] <= end) rightMaxIdx = j;
			}
			int result = (end-start) 
					+ gold2(start,q[i]-1,leftMinIdx,leftMaxIdx)
					+ gold2(q[i]+1,end,rightMinIdx,rightMaxIdx);
			minResult = Math.min(minResult, result);
		}
		return minResult;
	}
	
	Map<Integer, Integer> map;
	private int gold3(int start, int end){
		if(start >= end) return 0;
		int key = (10000*start) + end;
		if(map.containsKey(key)) return map.get(key);
		
		int minResult = 123456789;
		for(int i=0; i<q.length; i++){
			if(q[i] >= start && q[i] <= end){
				minResult = Math.min(minResult, gold3(start,q[i]-1) + gold3(q[i]+1,end));
			}
		}
		int result = 0;
		if(minResult < 123456789)
			result = (end-start) + minResult;
		map.put(key, result);
		return result;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJam2009_1C_C().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
