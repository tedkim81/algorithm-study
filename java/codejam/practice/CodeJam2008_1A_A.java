package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Round 1A 2008 : Problem A. Minimum Scalar Product
 * 2012년 12월 26일 오후 6:05:25
 * 요소의 순서를 변경 가능한 두개의 벡터가 있다고 할때 벡터의 스칼라곱의 최소값을 구하는 문제
 *
 * 처음에는 벡터의 조합을 변경하여 각 조합마다 벡터곱을 계산하고 그 최소값을 구하려고 했다.
 * 그러나 그렇게 하면 large의 경우 문제가 생길 수도 있겠다하는 생각에 벡터곱의 계산을 줄이려고 노력했다. ( 여기서 상당한 시간 소비 )
 * 어느정도 생각을 정리하고 벡터의 각 조합을 구하려고 하는데 조합을 구하는 프로그램을 구상하기가 어려웠다. ( 여기서도 상당한 시간 소비 )
 * 그러다 문득 총 800개수의 조합의 수가 800! 이고 이것이 엄청난 수라는 것을 깨닫게 되었고 다른 방법을 강구하게 되었다.
 * 그리고 선택되지 않은 순열 중에서 최소값과 최대값을 선택하여 곱하고 해당 값들을 모두 더한 값이 최소값이 될 것이라고 추정했고, 몇가지 예제를 통해 확신했다. ( 증명은 하지 못했다. )
 * 
 * 생각한 방법이 실현가능한지를 먼저 판단해봐야 한다. 800! 이 엄청난 수라는 것을 진작에 알았어야 했다.
 * 
 * long = int * int 와 같은 형태를 사용하면 안된다. 우변의 int*int 가 범위를 벗어날 경우 올바르지 않은 값이 좌변에 assign된다.
 * 
 * 수열의 조합을 구하는 것은 추가적으로 연습했다.
 * 그러나 재귀적으로 호출되는 메소드 안에서 리스트를 계속 생성하기 때문에 메모리 문제가 발생할 여지가 있다.
 * 추후 시간이 나면 더 좋은 알고리즘을 생각해 보도록 하자.
 * 
 * 해답에서는 첫 벡터는 오름차순 정렬하고, 두번째 벡터는 내림차순 정렬하여 스칼라곱을 구하도록 되어 있다.
 * 시간이 나면 코딩해 보자.
 */
public class CodeJam2008_1A_A {
	
	private static void test1_MinimumScalarProduct() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			int coord = Integer.parseInt(br.readLine());
			
			Coordinates v1 = new Coordinates(br.readLine().split(" "));
			Coordinates v2 = new Coordinates(br.readLine().split(" "));
			
			long scalarProduct = 0;
			for(int j=0; j<coord; j++){
				long max1 = v1.max();
				long max2 = v2.max();
				if(max1 > max2){
					v1.setMaxExcept();
					scalarProduct += max1 * v2.min();
				}
				else{
					v2.setMaxExcept();
					scalarProduct += max2 * v1.min();
				}
			}
			String result = "Case #"+(i+1)+": "+scalarProduct+"\n";
			fw.write(result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static class Coordinates {
		public List<Long> nums;
		public List<Integer> except;
		public int maxIdx;
		
		public Coordinates(String[] strs){
			nums = new ArrayList<Long>();
			for(int i=0; i<strs.length; i++){
				nums.add(Long.parseLong(strs[i]));
			}
			except = new ArrayList<Integer>();
		}
		
		public Long min(){
			long min = Long.MAX_VALUE;
			int idx = -1;
			for(int i=0; i<nums.size(); i++){
				if(except.contains(i) == false && nums.get(i) < min){
					min = nums.get(i);
					idx = i;
				}
			}
			if(idx >= 0){
				except.add(idx);
				return min;
			}
			return null;
		}
		
		public Long max(){
			long max = Long.MIN_VALUE;
			maxIdx = -1;
			for(int i=0; i<nums.size(); i++){
				if(except.contains(i) == false && nums.get(i) > max){
					max = nums.get(i);
					maxIdx = i;
				}
			}
			if(maxIdx >= 0){
				return max;
			}
			return null;
		}
		
		public void setMaxExcept(){
			if(maxIdx >= 0){
				except.add(maxIdx);
			}
		}
	}
	
	public static int[] comb = new int[10];
	public static int cnt = 0;
	public static void test2_Combination(){
		long start = new Date().getTime();
		List<Integer> list = new ArrayList<Integer>();
		for(int i=1; i<=comb.length; i++){
			list.add(i);
		}
		combination(list);
		System.out.println("total cnt : "+cnt+" , time : "+((new Date().getTime())-start));
	}
	
	public static void combination(List<Integer> list){
		int size = list.size();
		for(int i=0; i<size; i++){
			List<Integer> clone = new ArrayList<Integer>(list);
			int j = clone.remove(i);
			comb[comb.length-size] = j;
			if(clone.size() > 0){
				combination(clone);
			}
			else{
				String result = "";
				for(int k=0; k<comb.length; k++){
					result += comb[k]+",";
				}
				System.out.println(result.substring(0, result.length()-1));
				cnt++;
			}
		}
	}
	
	public static void main(String[] args){
		try{
			
			test2_Combination();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
