package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 B 일조량
 * N채의 건물에 대하여 최소 H시간 이상 햇볕을 받는 영역의 비율을 구하는 문제
 * 2013년 3월 13일 오전 1:17:19
 * 
 * 문제이해 46분, 해결방법 3시간, 코딩 2시간. 엄청난 시간을 소비했지만 또 실패다.
 * 각 건물에 대하여 높이를 Y부터 0까지 0.01 감소하면서 햇볕을 받을 수 있는 각을 계산하여 일정각도 미만이 되면 해당 높이를 구하도록 했고,
 * 그 높이들을 모두 더하여 전체높이에 대한 비율을 구하도록 했다.
 * 첫번째 시도에서 실패한 이유를 찾아보니 기울기와 관련된 오류와 좌우측 벽을 찾는 과정에서의 오류를 발견할 수 있었다.
 * 기울기를 절대값으로 사용하려고 했었는데 부호를 갖는 상태 그대로 사용하고 크기비교 과정의 문제점을 수정했다.
 * 좌우측 벽을 찾을때 y=0일때에 대해서만 고려했었는데 y가 변함에 따라 벽이 바뀔 수 있다는 것을 깨달았다.
 * 기울기와 좌우벽 문제를 해결한 후에도 정답을 구할 수 없어서 더 확인을 해보니 계산과정에서 자료구조의 표현영역을 벗어나는 오류가 있었다.
 * A,B,C 가 이미 10^7 을 넘는 숫자가 되다보니 근의 공식을 계산하는 과정에서 문제가 생긴 것이다.
 * 그래서 일단 실행시간 무시하고 답이라도 구해보고자 BigDecimal로 모두 치환했으나.. 그래도 정답이 도출되지 않았다.
 * 일일이 확인하여 문제점을 찾아보는데에는 시간이 너무 소요될 것 같아 일단 skip 하기로 했다.
 * 
 * 내가 생각한 방법은 직관적이지 않았다. tanc = bsinx / (a + bcosx) 라는 공식을 도출했고, 이를 만족하는 x를 찾고자 했으나
 * 특정 상황에 대한 그림을 그려놓고 만든 식이라서 상황에 따라 +/- 가 바뀌어야 하는 부분들이 있었다.
 * 그냥 직관적으로 직선과 원이 만나는 점을 구하고 그 점과 중심간에 이루는 각을 구하는 식으로 하는 것이 훨씬 직관적이다.
 * 문제를 너무 어렵게 생각했다..ㅠ.ㅠ
 * 
 * TODO: 추후 다시 풀어보자.
 */
public class CodeJamKorea2012_F_B_todo {
	
	private int N,R,H;
	private int[] X,Y;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			N = Integer.parseInt(strs[0]);
			R = Integer.parseInt(strs[1]);
			H = Integer.parseInt(strs[2]);
			TreeSet<XY> set = new TreeSet<XY>();
			int j;
			for(j=1; j<=N; j++){
				strs = br.readLine().split(" ");
				set.add(new XY(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
			}
			Iterator<XY> it = set.iterator();
			X = new int[N+2];  X[0] = -R;  X[N+1] = R;
			Y = new int[N+2];  Y[0] = Y[N+1] = 0;
			j = 1;
			while(it.hasNext()){
				XY xy = it.next();
				X[j] = xy.x;
				Y[j] = xy.y;
				j++;
			}
			
			double sum = 0;
			double tsum = 0;
			for(j=1; j<=N; j++){
				sum += getLightArea(j);
				tsum += Y[j];
			}
			
			String result = ""+String.format("%.7f", (sum/(double)tsum));
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private double getLightArea(int idx){
		double limit = (H / (double)12) * Math.PI;
		for(double y=Y[idx]; y>=0; y -= 0.01){
			double degree = getDegree(idx, y);
//			print("y:"+y+", limit:"+limit+" , degree:"+degree);
			if((int)(degree*10000) < (int)(limit*10000)){
				return Y[idx] - y;
			}
		}
		return Y[idx];
	}
	
	/*
	private double getDegree(int idx, double y){
		int left = 0;
		double slope = 999999;
		for(int i=0; i<idx; i++){
			double tmpSlope = (Y[i]-y) / (double)(X[i]-X[idx]);
			if(tmpSlope < slope){
				slope = tmpSlope;
				left = i;
			}
		}
		int right = N+1;
		slope = -999999;
		for(int i=idx+1; i<N+2; i++){
			double tmpSlope = (Y[i]-y) / (double)(X[i]-X[idx]);
//			print("i:"+i+", tmpSlope:"+tmpSlope+", slope:"+slope);
			if(tmpSlope > slope){
				slope = tmpSlope;
				right = i;
			}
		}
		
//		print("idx:"+idx+", y:"+y+", left:"+left+", right:"+right);
		double tanc = (Y[left]-y) / (double)(X[left] - X[idx]);
		double a,A,B,C,cos;
		double b = R;
		double leftDegree = 0;
		if(tanc != 0){
			a = (y / tanc) - X[idx];
//			print("left a:"+a);
			if(a <= 0 || (int)(a*10000) > (int)(b*10000)){
				A = (b*b*tanc*tanc) + (b*b);
				B = (-2)*a*b*tanc*tanc;
				C = (a*a*tanc*tanc) - (b*b);
				cos = (-B + Math.sqrt((B*B) - (4*A*C))) / (2*A);
				leftDegree = Math.acos(cos);
				if(cos < 0 || cos > 1){
					print("left error R:"+R+", leftX:"+X[left]+", leftY:"+Y[left]+", X:"+X[idx]+", Y:"+y+", tanc:"+tanc+", cos:"+cos+", A:"+A+", B:"+B+", C:"+C+", a:"+a);
				}
			}
		}
		else{  // tanc == 0
			leftDegree = Math.asin(y / b);
		}
//		print("left tanc:"+tanc+" , degree:"+leftDegree);
		
		tanc = (Y[right]-y) / (double)(X[right] - X[idx]);
		double rightDegree = 0;
		if(tanc != 0){
			a = (y / tanc) - X[idx];
//			print("right a:"+a);
			if(a >= 0 || (int)((-a)*10000) > (int)(b*10000)){
				A = (b*b*tanc*tanc) + (b*b);
				B = 2*a*b*tanc*tanc;
				C = (a*a*tanc*tanc) - (b*b);
				cos = (-B + Math.sqrt((B*B) - (4*A*C))) / (2*A);
				rightDegree = Math.acos(cos);
			}
		}
		else{  // tanc == 0
			rightDegree = Math.asin(y / b);
		}
//		print("right tanc:"+tanc+" , degree:"+rightDegree);
		
		return Math.PI - leftDegree - rightDegree;
	}
	*/
	
	private double getDegree(int idx, double y){
		int left = 0;
		double slope = 999999;
		for(int i=0; i<idx; i++){
			double tmpSlope = (Y[i]-y) / (double)(X[i]-X[idx]);
			if(tmpSlope < slope){
				slope = tmpSlope;
				left = i;
			}
		}
		int right = N+1;
		slope = -999999;
		for(int i=idx+1; i<N+2; i++){
			double tmpSlope = (Y[i]-y) / (double)(X[i]-X[idx]);
//			print("i:"+i+", tmpSlope:"+tmpSlope+", slope:"+slope);
			if(tmpSlope > slope){
				slope = tmpSlope;
				right = i;
			}
		}
		
//		print("idx:"+idx+", y:"+y+", left:"+left+", right:"+right);
		BigDecimal bigy = new BigDecimal(y);
		BigDecimal tanc = new BigDecimal((Y[left]-y) / (double)(X[left] - X[idx])).setScale(5, BigDecimal.ROUND_DOWN);
		BigDecimal tancxtanc = tanc.pow(2);
		BigDecimal a,A,B,C,cos;
		BigDecimal b = new BigDecimal(R);
		BigDecimal bxb = new BigDecimal(R*R);
		BigDecimal zero = new BigDecimal(0);
		BigDecimal _one = new BigDecimal(-1);
		BigDecimal one = new BigDecimal(1);
		double leftDegree = 0;
		if(tanc.compareTo(zero) != 0){
			a = bigy.divide(tanc, 5, BigDecimal.ROUND_DOWN).subtract(new BigDecimal(X[idx]));
//			print("left a:"+a);
			if(a.compareTo(zero) <= 0 || (int)(a.doubleValue()*10000) > (int)(b.doubleValue()*10000)){
				A = bxb.multiply(tancxtanc).add(bxb).setScale(5, BigDecimal.ROUND_DOWN);
				B = a.multiply(b).multiply(tancxtanc).multiply(new BigDecimal(-2)).setScale(5, BigDecimal.ROUND_DOWN);
				C = a.pow(2).multiply(tancxtanc).subtract(bxb).setScale(5, BigDecimal.ROUND_DOWN);
				
				double det = Math.sqrt(B.pow(2).subtract(A.multiply(C).multiply(new BigDecimal(4))).doubleValue());
				cos = new BigDecimal(det).subtract(B).divide(A.multiply(new BigDecimal(2)), 5, BigDecimal.ROUND_DOWN);
				if(cos.compareTo(_one) < 0 || cos.compareTo(one) > 0)
					cos = new BigDecimal((-1.0)*Math.sqrt(B.pow(2).subtract(A.multiply(C).multiply(new BigDecimal(4))).doubleValue())).subtract(B).divide(A.multiply(new BigDecimal(2)), 5, BigDecimal.ROUND_DOWN);
				leftDegree = Math.acos(cos.doubleValue());
				if(cos.compareTo(_one) < 0 || cos.compareTo(one) > 0){
					print("left error R:"+R+", leftX:"+X[left]+", leftY:"+Y[left]+", X:"+X[idx]+", Y:"+y+", tanc:"+tanc+", cos:"+cos+", A:"+A+", B:"+B+", C:"+C+", a:"+a);
				}
			}
		}
		else{  // tanc == 0
			leftDegree = Math.asin(y / b.doubleValue());
		}
//		print("left tanc:"+tanc+" , degree:"+leftDegree);
		
		tanc = new BigDecimal((Y[right]-y) / (double)(X[right] - X[idx])).setScale(5, BigDecimal.ROUND_DOWN);
		tancxtanc = tanc.pow(2);
		double rightDegree = 0;
		if(tanc.compareTo(zero) != 0){
			a = bigy.divide(tanc, 5, BigDecimal.ROUND_DOWN).subtract(new BigDecimal(X[idx]));
//			print("right a:"+a);
			if(a.compareTo(zero) >= 0 || (int)(a.doubleValue()*(-10000)) > (int)(b.doubleValue()*10000)){
				A = bxb.multiply(tancxtanc).add(bxb).setScale(5, BigDecimal.ROUND_DOWN);
				B = a.multiply(b).multiply(tancxtanc).multiply(new BigDecimal(2)).setScale(5, BigDecimal.ROUND_DOWN);
				C = a.pow(2).multiply(tancxtanc).subtract(bxb).setScale(5, BigDecimal.ROUND_DOWN);

				BigDecimal tmp = B.pow(2).subtract(A.multiply(C).multiply(new BigDecimal(4)));
				BigDecimal det = new BigDecimal(Math.sqrt(tmp.doubleValue()));
				cos = det.subtract(B).divide(A.multiply(new BigDecimal(2)), 5, BigDecimal.ROUND_DOWN);
				if(cos.compareTo(_one) < 0 || cos.compareTo(one) > 0)
					cos = new BigDecimal((-1.0)*Math.sqrt(B.pow(2).subtract(A.multiply(C).multiply(new BigDecimal(4))).doubleValue())).subtract(B).divide(A.multiply(new BigDecimal(2)), 5, BigDecimal.ROUND_DOWN);
				rightDegree = Math.acos(cos.doubleValue());
				if(cos.compareTo(_one) < 0 || cos.compareTo(one) > 0){
					print("right error R:"+R+", rightX:"+X[right]+", rightY:"+Y[right]+", X:"+X[idx]+", Y:"+y+", tanc:"+tanc+", cos:"+cos+", A:"+A+", B:"+B+", C:"+C+", a:"+a);
				}
			}
		}
		else{  // tanc == 0
			rightDegree = Math.asin(y / b.doubleValue());
		}
//		print("right tanc:"+tanc+" , degree:"+rightDegree);
		
		return Math.PI - leftDegree - rightDegree;
	}
	
	private class XY implements Comparable<XY>{
		private int x;
		private int y;
		public XY(int x, int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(XY o) {
			return this.x - o.x;
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_F_B_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void test(){
//		N = 2;  R = 100;  H = 7;
//		X = new int[N+2];  X[0] = -R;  X[N+1] = R;
//		Y = new int[N+2];  Y[0] = Y[N+1] = 0;
//		X[1] = -50;  X[2] = 0;
//		Y[1] = 25;  Y[2] = 75;
		
//		N = 1;  R = 100;  H = 12;
//		X = new int[N+2];  X[0] = -R;  X[N+1] = R;
//		Y = new int[N+2];  Y[0] = Y[N+1] = 0;
//		X[1] = 0;
//		Y[1] = 50;
		
		N = 2;  R = 703;  H = 0;
		X = new int[N+2];  X[0] = -R;  X[N+1] = R;
		Y = new int[N+2];  Y[0] = Y[N+1] = 0;
		X[1] = 483;  X[2] = 502;
		Y[1] = 306;  Y[2] = 154;
		
		double result = getDegree(2, 154);
//		double result = getLightArea(1);
		print("result: "+result);
	}
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			System.out.println("exit: "+log);
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
}
