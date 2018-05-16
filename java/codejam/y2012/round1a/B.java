package gcj.y2012.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem B. Kingdom Rush
 * 문제를 풀긴 했으나 오답처리되었고, 오답 원인을 파악하지 못하여 결국 solution을 확인해보고 나서야 오류를 수정할 수 있었다.
 * 동적계획법으로 small input은 해결이 가능했으나, large input에 대해서는 불가능했기 때문에 한꺼번에 해결하기 위해서 탐욕법을 택했다.
 * 그러나 탐욕법은 (이번에 절실히 느꼈지만) 철저한 증명이 필요하다. 테스트 해본 케이스들이 모두 성공했다고 하더라도 예외가 되는 상황이 얼마든지 있을 수 있다.
 * 탐욕법을 시도하여 small input에서 오답처리되었다면 먼저 오답원인을 어느정도 시간동안만 확인해보고 확인하기 어렵다면 
 * 다른 가능한 방법(동적 계획법 등)으로 문제를 해결한후 올바른 small output으로 탐욕법을 검증하는 방식을 사용하자.
 * 그러려면 시간이 배로 들기 때문에 생각한 방식을 구현하는 속도가 지금보다 훨씬 빨라져야 제한시간내에 문제를 풀 수 있을 것이다.
 * 
 * 탐욕법을 처음 구상할때는 2-star 먼저 다돌고 1-star를 도는 방식이었는데 정답보다 큰 수가 나오는 문제가 있었다.
 * star를 얻을 수 있는 경우만 도는 것으로 조건을 제한했더니 테스트 케이스들은 제대로 나왔는데 small output이 오답처리되었다.
 * 2-star 선택할때 요구값이 작은 것부터 하면 되지 않을까 하는 막연한 기대감에 ( 증명을 하지 않고 ) 수정을 해봤으나 그래도 오답처리되었다.
 * 여기서 더이상 문제점을 찾지 못하고 solution을 확인했고, 출력을 비교해보면서 문제점을 확인할 수 있었다.
 * 2-star를 선택못했을때 1-star를 선택하는 과정에서 요구값이 작은 순서대로 선택하도록 했었는데 이러한 경우 2-star 선택시 이미 1-star가 선택된 경우라면
 * 1밖에 획득하지 못하기 때문에 최적화 되지 않는다. 따라서 1-star를 선택할때는 2-star의 요구값이 가장 큰값부터 ( 즉 가장 나중에 선택되는 2-star에 대한 1-star )
 * 선택하도록 수정한 후 small input 및 large input 에 대하여 정답을 제출할 수 있었다.
 */
public class B {
	
	private int N;
	private int[] a,b;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"B-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			N = sc.nextInt();
			a = new int[N];
			b = new int[N];
			for(int i=0; i<N; i++){
				a[i] = sc.nextInt();
				b[i] = sc.nextInt();
			}
			
			int result = getResult();
			if(result == 0){
				fw.write("Case #"+(casenum+1)+": Too Bad\n");
				print("Case #"+(casenum+1)+": Too Bad");
			}
			else{
				fw.write("Case #"+(casenum+1)+": "+result+"\n");
				print("Case #"+(casenum+1)+": "+result);
			}
		}
		fw.close();
	}
	
	private int getResult(){
		int result = 0;
		boolean[][] played = new boolean[2][N];
		int stars = 0;
		int playedCnt = 0;
		while(true){
			boolean checked = false;
			int min = Integer.MAX_VALUE;
			int k = 0;
			for(int j=0; j<N; j++){
				if(played[1][j]) continue;
				if(stars >= b[j] && b[j] < min){
					min = b[j];
					k = j;
					checked = true;
				}
			}
			if(checked){
				result++;
				playedCnt++;
				if(played[0][k]) stars += 1;
				else stars += 2;
				played[1][k] = true;
//				print("2-star! k:"+k+", stars:"+stars);
			}
			else{
				int max = Integer.MIN_VALUE;
				k = 0;
				for(int j=0; j<N; j++){
					if(played[0][j] || played[1][j]) continue;
					if(stars >= a[j] && b[j] > max){
						max = b[j];
						k = j;
						checked = true;
					}
				}
				if(checked){
					result++;
					stars += 1;
					played[0][k] = true;
//					print("1-star! k:"+k+", stars:"+stars);
				}
			}
			
			if(playedCnt == N || checked == false) break;
		}
//		print(played[1]);
		if(playedCnt == N) return result;
		else return 0;
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
	
	public static void print(boolean[] arr){
		if(arr == null) print("null");
		else{
			String str = "[";
			if(arr.length > 0){
				for(int i=0; i<arr.length; i++) str += (arr[i]?1:0)+",";
				str = str.substring(0, str.length()-1);
			}
			str += "]";
			print(str);
		}
	}
}
