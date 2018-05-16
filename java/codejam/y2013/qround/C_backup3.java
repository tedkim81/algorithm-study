package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Qualification Round 2013 : Problem C. Fair and Square
 */
public class C_backup3 {
	
//	private long A,B;
//	private boolean[] checked = new boolean[10000001];
	private BigInteger A,B;
	private TreeSet<Long> palindset;
	private TreeSet<BigInteger> palindsquareset;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		palindset = new TreeSet<Long>();
		for(int i=1; i<=10; i++){
			palindset.addAll(getPalindSet(i));
		}
//		print("palindset size : "+palindset.size());
		
		palindsquareset = new TreeSet<BigInteger>();
		palindsquareset.add(new BigInteger("1"));
		String compstr = "1";
		for(int i=0; i<100; i++) compstr += "0";
		BigInteger comp = new BigInteger(compstr);
		for(long palind : palindset){
			if(palind == 0) continue;
			BigInteger bi = new BigInteger(""+palind);
			if(palindsquareset.contains(bi)) continue;
			
			bi = bi.pow(2);
			while(bi.compareTo(comp) <= 0){
				String bistr = bi.toString();
				if(isPalindrome(bistr)){
					palindsquareset.add(new BigInteger(bistr));
				}
				bi = bi.pow(2);
			}
		}
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/C-large-1.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-1.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			A = new BigInteger(""+sc.nextLong());
			B = new BigInteger(""+sc.nextLong());
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private TreeSet<Long> getPalindSet(int size){
		TreeSet<Long> result = new TreeSet<Long>();
		if(size == 1){
			for(long i=0; i<=9; i++) result.add(i);
			return result;
		}
		if(size == 2){
			for(long i=1; i<=9; i++) result.add(10*i+i);
			return result;
		}
		
		TreeSet<Long> midset = getPalindSet(size-2);
		long mult = (long)Math.pow(10, size-1);
		for(int i=1; i<=9; i++){
			for(long mid : midset){
				result.add(i*mult + mid*10 + i);
			}
		}
		return result;
	}
	
	/*
	private int getResult(){
		int result = 0;
		for(int i=1; i<=10000000; i++){
			if(checked[i]){
				result++;
				continue;
			}
			checked[i] = true;
			
			if(isPalindrome(i)){
				int end = (int)Math.max(A, Math.ceil(Math.sqrt(B)));
				if(i == 1){
					if(i >= A && i <= B) result++;
				}
				else if(i <= end){
					long tmp = (long)i*(long)i;
					while(tmp <= B){
						if(tmp >= A && tmp <= B && isPalindrome(tmp)){
							result++;
							if(tmp < checked.length) checked[(int)tmp] = true;
						}
						if(tmp > tmp*tmp) break;
						tmp *= tmp;
					}
				}
				else{
					break;
				}
			}
		}
		return result;
	}
	
	private boolean isPalindrome(long num){
		String str = ""+num;
		int end = str.length()/2 + 1;
		for(int i=0; i<end; i++){
			if(str.charAt(i) != str.charAt(str.length()-i-1)){
				return false;
			}
		}
		return true;
	}
	*/
	
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
			
			new C_backup3().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	private void test(){
		palindset = new TreeSet<Long>();
		for(int i=1; i<=10; i++){
			palindset.addAll(getPalindSet(i));
		}
//		print("palindset size : "+palindset.size());
		
		palindsquareset = new TreeSet<BigInteger>();
		palindsquareset.add(new BigInteger("1"));
		String compstr = "1";
		for(int i=0; i<100; i++) compstr += "0";
		BigInteger comp = new BigInteger(compstr);
		for(long palind : palindset){
			if(palind == 0) continue;
			BigInteger bi = new BigInteger(""+palind);
			if(palindsquareset.contains(bi)) continue;
			
			bi = bi.pow(2);
			while(bi.compareTo(comp) <= 0){
				String bistr = bi.toString();
				if(isPalindrome(bistr)){
					palindsquareset.add(new BigInteger(bistr));
				}
				bi = bi.pow(2);
			}
		}
//		print("palindsquareset size : "+palindsquareset.size());
		
		int result = 0;
		BigInteger a = new BigInteger("100");
		BigInteger b = new BigInteger("1000");
		for(BigInteger bi : palindsquareset){
			if(bi.compareTo(a) >= 0 && bi.compareTo(b) <= 0) result++;
		}
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
