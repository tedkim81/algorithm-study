package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Round 1A 2008 : Problem C. Numbers
 * 2013년 1월 2일 오전 1:13:55
 * (3 + √5)의 n제곱의 소수점 앞 3자리 수를 구하는 문제
 * 
 * 어려운 문제였다. 결국은 문제를 풀지 못하고 해답을 본 후 몇가지 생각하지 못했던 컨셉에 대해 확인 후 문제를 풀 수 있었다.
 * 
 * 처음에는 2000000000 이라는 숫자를 보고 분명 규칙성이 존재할 거라고 확신했다.
 * 일정 범위 단위로 반복되거나, 어느정도 이후로는 같은 수가 반복되거나 할 거라고 생각했다.
 * 그래서 double 타입으로 근사치를 구한후 실제로 제곱 연산을 하여 값을 구하면서 뭔가 규칙성이 있을지 확인해 보려고 했다.
 * 그리고 첫번째 시도에 20번째쯤부터 같은수가 반복되는 것을 보고 문제를 해결했다고 생각했고, 그대로 답안을 작성하여 제출했지만 틀렸다.
 * double 으로 계산후 정수부분의 끝 3자리를 구하는 과정에서 int 로 변경하다가 범위가 초과되어 어느정도 이후로는 계속 같은수만 나왔다.
 * 이때 실제 값을 구하는 방식은 접근방법이 잘못되었다는 것을 알았어야 했으나 또 집착이 시간을 낭비하게 만들었다.
 * 분명 반복되는 부분이 있을거라고 생각하고, long 타입으로 변경후 좀더 결과를 보려고 했던 것이다.
 * 그렇게 시간을 하루 정도 허비하고 나서야 이 접근방법은 아니라는 것을 깨달았다.
 * 그리고 두번째로, 계산된 결과값을 작게 변경하는 방향으로 고민을 했다. 
 * a=이전계산결과, b=뺄값, c=3+√5 라고 할때, (a-b)*c+1000n=ac 를 만족하는 b=(3-√5)*250n 을 이용하여 값을 변경하도록 한 후 결과를 뽑았으나 또 오답이었다.
 * 결과를 변형한 후 첫번째 값은 맞게 나오지만 그 다음 값은 실제 결과와 달랐다. 결국, 계산된 결과가 제대로 바뀌어야한다는 결론을 얻었다.
 * 그리고는 답답한 마음에, 어떻게든 n<=100 정도까지만 결과를 출력시켜보려고 시간을 낭비했다.
 * 그래서 큰단위 숫자로 계산할 수 있는 계산기를 만들어 보려고 생각하다가 다른 방향으로 바꾼다. ( BigDecimal을 잊고 있었다.. )
 * 그러다가 (a+b√5)(3+√5) 를 전개하여 a=3a+5b, b=a+3b 라는 것을 알았으나, 
 * 이러한 방식으로도 결국은 값을 계산해야 하며 그때 수의 범위와 계산횟수 때문에 문제를 해결할 수 없다고 보고 좌절했다. 그리고 해답을 읽었다..
 * 
 * 문제를 해결하는데 있어서 반드시 알았어야 하는 컨셉은 conjugation 과 fast exponentiation 이다. 이 둘을 생각하지 못하면 아무리 오래 붙들고 있어도 해결할 수 없는 문제였다.
 * 문제에 대해 고민할 때 무리수 부분을 없애야 한다는 것을 빨리 눈치챘어야 했다. √5가 무리수라는 것을 빨리 깨달아야 했고, 근사치로 계산하면 n이 충분히 커질 경우 분명 오차가 생긴다는 것을 알았어야 했다.
 * conjugation 을 알았다면, 그리고 3-√5가 1보다 작기 때문에 제곱을 계속하면 점점더 0에 수렴한다는 생각을 할 수 있었다면 첫번째 관문은 통과할 수 있었을 것이다.
 * 그리고 두번째 관문, n이 엄청나게 큰수인 경우 제곱결과에 제곱하는 식의 기하급수적인 방식을 떠올릴 수 있었다면, 
 * 그리고 각 계산결과를 %1000 으로 축소해도 된다는 것을 알았다면 쉽게 문제를 풀 수 있었을 것이다. 
 * 그리고 문제해석을 할때 순열을 이용하여 접근했어야 했다. a(n)=3a(n-1)+5b(n-1) 과 같이 전개하고 상수를 더하는 과정이 없으므로 1000 이상의 수를 빼고 다음으로 전개해도 된다는 것을 알았을 것이다.
 * 
 * 해답의 ( Solution C. [the periodicity of 3 digits] ) 는 나중에 다시 읽어보고 이해를 해보자.
 */
public class CodeJam2008_1A_C {
	
	private static void goodluck() throws Exception {
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
		
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		double a1 = 3 + Math.sqrt(5);
		double a2 = (3 - Math.sqrt(5)) * 250;
		for(int i=1; i<=102; i++){
			double b = 1;
			int c = 0;
			for(int j=0; j<i; j++){
				b *= a1;
				c = (int)b % 1000;
				while(b > a2){
					b -= a2;
				}
			}
			String val;
			if(c < 10){
				val = "00"+c;
			}
			else if(c >= 10 && c < 100){
				val = "0"+c;
			}
			else{
				val = ""+c;
			}
			resultMap.put(i, val);
		}
		
		for(int i=0; i<numberOfCases; i++){
			
			long n = Long.parseLong(br.readLine());
			int key = (int)n % 102;
			if(key == 0) key = 102;
			String result = resultMap.get(key);
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void test(){
		double a1 = 3 + Math.sqrt(5);
		double a2 = (3 - Math.sqrt(5)) * 250;
		for(int i=1; i<=10; i++){
			double b = 1;
			int c = 0;
			for(int j=0; j<i; j++){
				while(b > a2){
					b -= a2;
				}
				b *= a1;
				c = (int)b % 1000;
			}
			System.out.println(i+" : "+b);
		}
	}
	
	public static void test2(){
		long d = 1111111111;
		d = d * 10;
		double a = d * 5;
		long b = (long)a;
		long c = 111111111111L * 5;
		System.out.println(b);
	}
	
	public static void test3(){
		double a = 3 + Math.sqrt(5);
		for(int i=1; i<=30; i++){
			double b = 1;
			long c = 0;
			for(int j=0; j<i; j++){
				b *= a;
				c = (long)b % 1000;
			}
			System.out.println(i+" : "+((long)b));
		}
	}
	
	public static void test4(){
		BigDecimal a = BigDecimal.valueOf(3 + Math.sqrt(5));
		BigDecimal d = BigDecimal.valueOf(1000);
		for(int i=2; i<=1000; i++){
			BigDecimal b = BigDecimal.valueOf(1);
			String c = null;
			for(int j=0; j<i; j++){
				b = b.multiply(a);
				c = b.setScale(0, BigDecimal.ROUND_FLOOR).remainder(d).toString();
			}
//			System.out.println(i+" : "+c);
			if("5".equals(c)){
				System.out.println(i+" : "+c);
				break;
			}
		}
	}
	
	private static void goodluck2() throws Exception {
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
		
		for(int i=0; i<numberOfCases; i++){
			String result = getResult(Long.parseLong(br.readLine()));
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static String getResult(long n){
		List<Boolean> list = new ArrayList<Boolean>();
		while(n > 1){
			list.add(n%2 == 1);
			n /= 2;
		}
		int c = 3;
		int d = 1;
		int size = list.size();
		for(int i=size-1; i>=0; i--){
			int tc = c;
			int td = d;
			c = tc*tc + 5*td*td;
			d = 2*tc*td;
			if(list.get(i)){
				tc = c;
				td = d;
				c = 3*tc + 5*td;
				d = tc + 3*td;
			}
			c %= 1000;
			d %= 1000;
		}
		int r = (2*c - 1) % 1000;
		if(r < 10){
			return "00"+r;
		}
		else if(r < 100){
			return "0"+r;
		}
		return ""+r;
	}
	
	public static void test5(){
		double a = 3 + Math.sqrt(5);
		double b = 1;
		for(int i=0; i<200; i++){
			b *= a;
			System.out.println(i+" : "+b+" , "+((int)b%1000));
		}
	}
	
	public static void main(String[] args){
		try{
			
			goodluck2();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
