package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Qualification Round 2013 : Problem C. Fair and Square
 * 좌우 대칭이면서 제곱근도 좌우 대칭인 수가 해당 범위 안에 몇개인지 구하는 문제
 * 
 * small input에 대해서는 C_backup2 로 정답을 제출했으나, large input에 대해서는 정답을 제출하지 못했다.
 * 미리 전체 범위에 대하여 조건을 만족하는 set을 구해두고 A,B 사이의 갯수를 구하는 방식으로 하는 것은 바로 생각해 낼 수 있었다.
 * 그러나 해당 set을 제대로 구하지 못하여 결국은 정답 제출에 실패했다. 문제점 몇가지는 아래와 같다.
 * 
 * 1. getPalindSet() 에서 0 에 대한 고려가 제대로 되지 않았다. 처음에는 아예 0을 제외하여 문제가 됐고, 그 뒤로도 0을 적절히 넣지 못하여 시간이 지연되었다.
 *    결국은 getPalindSet()에서 0을 자리에 상관없이 무조건 넣고, palindsquareset을 만들때 0으로 시작하는 수를 제외하도록 처리했다.
 *    이 문제를 해결하니 large input 1 에 대해서는 정답을 구할 수 있었다. 그러나 large input 2에서는 50자리까지 palind를 구해야 하기 때문에 실행시간이 문제가 됐다.
 * 2. 위의 방법으로 50자리까지 palind를 구하려면 10^25개가 넘는다. 따라서 필터링을 할 수 있는 규칙을 더 있을것이라 확신하고 규칙을 찾았다.
 *    palindset 은 palindsquareset 을 구하기 위한 재료이기 때문에 제곱하여 palind가 되지 않는 것들은 이미 대상이 아닌 것들이 된다.
 *    이것들을 미리 제거해야 재사용되는 숫자가 줄어들어 50자리까지도 무난히 구할 수 있게 되는 것이다.
 *    이로써 large input 2에 대해서도 정답을 구할 수 있었다.
 *    
 * TODO: Contest Analysis 를 읽고 이해하도록 하자. 한번 읽어 봤으나 이해가 되지 않았다..
 */
public class C {
	
	private BigInteger A,B;
	private TreeSet<String> palindset;
	private TreeSet<BigInteger> palindsquareset;
	private Map<Integer, TreeSet<String>> memo;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		memo = new HashMap<Integer, TreeSet<String>>();
		palindset = new TreeSet<String>();
		for(int i=1; i<=50; i++){
			palindset.addAll(getPalindSet(i));
		}
		print("palid size:"+palindset.size());
		
		palindsquareset = new TreeSet<BigInteger>();
		palindsquareset.add(new BigInteger("1"));
		for(String palind : palindset){
			BigInteger bi = new BigInteger(palind);
			if(palind.equals("0") || palind.length() != bi.toString().length()) continue;
			
			bi = bi.pow(2);
			String bistr = bi.toString();
			if(isPalindrome(bistr)) palindsquareset.add(new BigInteger(bistr));
		}
		print("palidsquare size:"+palindsquareset.size());
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/C-large-practice-2.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice-2.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			A = new BigInteger(""+sc.next());
			B = new BigInteger(""+sc.next());
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
//			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private TreeSet<String> getPalindSet(int size){
		if(memo.containsKey(size)){
			return memo.get(size);
		}
		
		TreeSet<String> result = new TreeSet<String>();
		if(size == 1){
			for(long i=0; i<=9; i++) result.add(""+i);
			memo.put(1, result);
			return result;
		}
		if(size == 2){
			for(long i=0; i<=9; i++) result.add(i+""+i);
			memo.put(2, result);
			return result;
		}
		
		TreeSet<String> midset = getPalindSet(size-2);
		for(int i=0; i<=9; i++){
			for(String mid : midset){
				String tmp = i + mid + i;
				if(i==0 || checkValid(tmp)) result.add(tmp);
			}
		}
		memo.put(size, result);
		
		return result;
	}
	
	private boolean checkValid(String palind){
		BigInteger bi = new BigInteger(palind);
		String sqr = bi.pow(2).toString();
		return isPalindrome(sqr);
	}
	
	private int getResult(){
		int result = 0;
		for(BigInteger bi : palindsquareset){
			if(bi.compareTo(A) >= 0 && bi.compareTo(B) <= 0) result++;
		}
		return result;
	}
	
	private boolean isPalindrome(String str){
		int end = str.length()/2 + 1;
		for(int i=0; i<end; i++){
			if(str.charAt(i) != str.charAt(str.length()-i-1)){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C().goodluck();
			
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
