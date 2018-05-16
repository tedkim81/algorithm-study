package gcj.y2013.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem C. Good Luck
 * 
 * 문제 지문이 너무 길어서 콘테스트 당일에는 문제를 이해도 못했었는데, 다시 보니 small input은 꽤 쉽게 해결할 수 있는 문제였다.
 * 테스트케이스가 1개이고, N,M,K가 고정되어 있기 때문에 3중 for문으로 small input-1은 해결할 수 있다.
 * 문제는 small input-2 다. 이름만 small이지 large input과 같다. ( 단지, retry를 허용하기 위해 small input이라 이름 붙인 것이다. )
 * 
 * 정답을 {a1,a2,...,a12} 라고 하자. ai가 prods를 하나라도 나눌 수 있으면 가능한 수라고 보고 재귀적으로 확인하는 방식을 사용해 봤다.
 * 이렇게 하고 어떻게든 동적계획법을 적용하면 되지 않을까하는 막연한 생각으로 코딩을 했으나, 이는 몇가지 문제가 있었다.
 * 1. 알고리즘 자체에 오류가 있었다. prods가 {2,8,16} 이면 224가 되어야 하는데, 242가 된다. 8은 2x4 이므로 2로 두번 먼저 나누면 실패한다.
 * 2. 동적계획법을 적용할 수가 없었다. 각 자리마다 2부터 M까지 순차적으로 확인하기 때문에 중복되는 문제가 발생하지 않기 때문이다.
 * solution을 확인해보자.
 * 
 * wata의 solution을 보고 한가지 중요한 힌트를 얻었다. 오름차순 정렬된 조합의 모든 경우의 수가 그다지 크지 않다는 것이다.
 * 2이상 8이하인 수로 12자리 수를 만들때 이 중 오름차순으로 정렬된 수의 갯수는 18564개밖에 되지 않는다.(test code로 확인)
 * 이 힌트만 가지고도 문제를 해결할 수 있을 듯 하여 wata의 나머지 코드는 더 이상 이해하지 않았다.(어려워서 이해할 수도 없었다..)
 * 
 * TODO: 분명 조건을 만족하는 output을 만들었는데 오답이다. 내가 이해하지 못한 뭔가가 있는 듯 하다. analysis가 나오면 확인해보자.
 */
public class C_todo {
	
	private int R,N,M,K;
	private long[][] kprod;
//	private int[] resarr;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File(path+"C-small-practice-1.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"C-small-practice-1.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			R = sc.nextInt();
			N = sc.nextInt();
			M = sc.nextInt();
			K = sc.nextInt();
			kprod = new long[R][K];
			for(int i=0; i<R; i++){
				for(int j=0; j<K; j++){
					kprod[i][j] = sc.nextLong();
				}
			}
			
			String result = "";
			for(int i=0; i<R; i++){
				result += "\n"+solve(i);
			}
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String solve(int r){
		int[] vs = new int[N];
		Arrays.fill(vs, 2);
		find(vs, 0, M, r);
		String result = "";
		for(int i=N-1; i>=0; i--) result += vs[i];
		return result;
	}
	
	private boolean find(int[] vs, int idx, int v, int r){
		if(idx == N){
			return isValid(vs, r);
		}
		else{
			vs[idx] = v;
			boolean result = find(vs, idx+1, v, r);
			if(result == false && v > 2) result = find(vs, idx, v-1, r);
			return result;
		}
	}
	
	private boolean isValid(int[] vs, int r){
		for(int i=0; i<K; i++){
			long prod = kprod[r][i];
			for(int j=0; j<N; j++){
				if(prod != 1 && prod % vs[j] == 0) prod /= vs[j];
			}
			if(prod > 1) return false;
		}
		return true;
	}
	
	/*
	private String solve(int r){
		resarr = new int[N];
		findNum(0, kprod[r]);
		String result = "";
		for(int i=0; i<N; i++) result += resarr[i];
		print("result: "+result);
		return result;
	}
	
	private boolean findNum(int aidx, long[] prods){
		if(aidx == N){
			boolean result = true;
			for(int i=0; i<prods.length; i++){
				if(prods[i] != 1){
					result = false;
					break;
				}
			}
			return result;
		}
		for(int i=2; i<=M; i++){
			boolean dividable = false;
			long[] nextprods = new long[prods.length];
			for(int j=0; j<prods.length; j++){
				if(prods[j] == 1 || prods[j] % i == 0){
					dividable = true;
					if(prods[j] == 1) nextprods[j] = 1;
					else nextprods[j] = prods[j] / i;
				}
				else nextprods[j] = prods[j];
			}
			if(dividable){
				int tmp = resarr[aidx];
				resarr[aidx] = i;
				
				if(findNum(aidx+1, nextprods)) return true;
				
				resarr[aidx] = tmp;
			}
		}
		return false;
	}
	*/

	/*
	// N=3 이므로 for문 3개 사용
	private String solve(int r){
		for(int i=2; i<=M; i++){
			for(int j=2; j<=M; j++){
				for(int k=2; k<=M; k++){
					boolean valid = true;
					for(int l=0; l<K; l++){
						if(isValid(new int[]{i,j,k} ,new boolean[K], 0, kprod[r][l]) == false){
							valid = false;
							break;
						}
					}
					if(valid){
						return i+""+j+""+k;
					}
				}
			}
		}
		return "222";
	}
	
	private boolean isValid(int[] set, boolean[] checked, int checkCnt, int prod){
		if(prod == 1) return true;
		if(checkCnt == K) return false;
		
		for(int i=0; i<set.length; i++){
			if(checked[i]) continue;
			if(prod % set[i] > 0){
				checked[i] = true;
				continue;
			}
			checked[i] = true;
			if(isValid(set, checked, checkCnt+1, prod)) return true;
			if(isValid(set, checked, checkCnt+1, prod/set[i])) return true;
			checked[i] = false;
		}
		return false;
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
	
	public void test(){
		R=1; N=3; M=4; K=4;
		kprod = new long[R][K];
		kprod[0] = new long[]{9,4,36,1};
		
		boolean result = isValid(new int[]{3,3,4}, 0);
		print("result: "+result);
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
