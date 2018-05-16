package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 B 장터판
 * NxM 격자 게임판에 K면 다면체가 고정되었거나 그렇지 않은 상태로 있고 게임판을 흔들어서 각 영역에 대하여 연속4인지 연속3인지 연속2인지에 따라 점수를 계산한다고 할때
 * 그 점수의 총합의 기대값을 구하는 문제이다.
 * 2013년 2월 16일 오후 7:32:00
 * 
 * 결국은 엄청난 시간을 쏟아붓고 문제는 풀지도 못했다.
 * 너무 한문제에 오래 매달리지 않기 위해 다음기회로 넘기자.
 * 
 * 문제를 풀기 위해 각 확률간의 관계가 있지 않을까 생각했다.
 * P4=연속4개 이을 확률 이라고 하면, P3 = (1-P4)x(연속3개 이을 확률) 이고, P2 = (1-P3-P4)x(연속2개 이을 확률) 이라고 생각했다.
 * 그리고 이것은 엄청난 착각이었다. 확률의 합,차,곱에 대한 개념을 착각하고 있었다.
 * 
 * 확률의 합은 상황 자체가 동일해야 하며 경우의 수가 변하지 않을 때 성립 가능하다. 
 * 예를 들어, 주사위를 한번 던질때 1이 나오거나 6이 나올 확률은 1/6 + 1/6 = 1/3 이 되는 것이다.
 * 그리고 합할 수 있는 각 경우가 포함관계가 없어야 한다. 1이 나오는 경우와 6이 나오는 경우는 서로를 절대 포함하지 않는다.
 * 확률의 차도 마찬가지다. 그러나 위에서 P3를 (1-P4)에 대한 식으로 만들려고 한 것은 문제가 있다.
 * P3=(1-P4)x(연속3개 이을 확률) 이 성립하려면, (연속3개 이을 확률)이 (연속3개 이을 경우의 수) / (P4에 해당하지 않는 모든 경우의 수) 가 되어야 한다.
 * 다시말해서 (연속3개 이을 확률)을 P4와 상관없이 구하기 위해 식을 만들었는데 결국 그렇지 않기 때문에 문제가 있었다.
 * 
 * 확률의 곱은 서로 독립적인 경우에 대해서만 성립된다.
 * 예를 들어, 주사위 2개를 던져서 각각이 1과 6이 나올 확률은 1/6 * 1/6 이고 또는 주사위 하나를 두번 던져서 처음에 1이 나오고 다음에 6이 나올 확률도 마찬가지다.
 * P3=(1-P4)x(연속3개 이을 확률) 에서, P3는 4개가 이어지지 않고 3개가 이어질 확률이 되어야 한다.
 * 그러나 P4와 P3는 독립적인 상황이 아니라 동일한 상황에서 발생하기 때문에 P4를 이용하여 P3를 구하려고 하는 것은 오류이다.
 * 
 * 위의 문제점들을 확인하고 나서 다시 문제해결을 시도했으나 아직 정답을 구하지는 못했다.
 * 현재 당면한 문제점은, 하나의 방향에 대하여 P4, P3, P2를 구할때 경우의 수가 중복되어 확률이 실제보다 크게 나오는 것이다.
 * (?,?,?,?) 에서 두번째값에 대하여 P3를 구할때 앞에서부터3개와 한칸건너뛴3개가 이어질 확률의 합은 둘간에 중복되는 경우의 수가 존재하기 때문에 실제보다 크다.
 * 
 * 위 문제점들을 충분히 숙고하여 문제 해결방법을 다시 찾고 다시 풀어보도록 하자.
 */
public class CodeJamKorea2012_1_B_todo {
	
	private char[][] map;
	private int N;
	private int M;
	private int K;
	private int S4;
	private int S3;
	private int S2;
	private Rat one = new Rat(1, 1);
	
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
			M = Integer.parseInt(strs[1]);
			K = Integer.parseInt(strs[2]);
			S4 = Integer.parseInt(strs[3]);
			S3 = Integer.parseInt(strs[4]);
			S2 = Integer.parseInt(strs[5]);
			map = new char[N][M];
			for(int ni=0; ni<N; ni++){
				String str = br.readLine();
				System.out.println(str);
				for(int mi=0; mi<M; mi++){
					map[ni][mi] = str.charAt(mi);
				}
			}
			
			Rat p = new Rat(0, 1);
			char ki = (""+K).charAt(0);
			for(int ni=0; ni<N; ni++){
				for(int mi=0; mi<M; mi++){
					Rat p2 = new Rat(0, 1);
					if(map[ni][mi] == '?'){
						for(char ci='1'; ci<=ki; ci++){
							p2 = sum(p2, getValue(ni, mi, ci));
						}
						p2.devide(K);
					}
					else{
						p2 = getValue(ni, mi, map[ni][mi]);
					}
					System.out.println("ni:"+ni+", mi:"+mi+", p:"+p2);
					p = sum(p, p2);
				}
			}
			
			String result = ""+p.getDouble();
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	/*
	private Rat getValue(int ni, int mi, char c){
		Rat p4 = getValue(ni, mi, c, 4);
		Rat tmp = getValue(ni, mi, c, 3);
		Rat p3 = multi(diff(one, p4), tmp);
		Rat p2 = multi(diff(one, sum(p3, p4)), getValue(ni, mi, c, 2));
		p4.multi(S4);
		p3.multi(S3);
		p2.multi(S2);
		Rat p = sum(p4, p3);
		p = sum(p, p2);
		System.out.println("-- ni:"+ni+", mi:"+mi+", c:"+c+", p4:"+p4+", p3:"+p3+", p2:"+p2+", result:"+p+", tmp:"+tmp);
		return p;
	}
	
	private Rat getValue(int ni, int mi, char c, int line){
//		System.out.println("getValue ni:"+ni+", mi:"+mi+", c:"+c+", line:"+line);
		Rat pline = new Rat(0, 1);
		Rat p;
		for(int i=line-1; i>=0; i--){ // 가로
			int sm = mi-i; int em = sm+line-1;
			if(sm < 0 || em >= M) continue;
			if((sm-1 >= 0 && map[ni][sm-1] == c) || (em+1 < M && map[ni][em+1] == c)) if(line != 4) continue;
			p = diff(one, pline);
			for(int j=0; j<line; j++){
				int mj = sm+j;
				if(mj == mi) continue;
				if(map[ni][mj] == '?') p.devide(K);
				else if(map[ni][mj] != c){ p.zero(); break; }
			}
			pline = sum(pline, p);
		}
//		System.out.println("A: "+pline);
		for(int i=line-1; i>=0; i--){ // 세로
			int sn = ni-i; int en = sn+line-1;
			if(sn < 0 || en >= N) continue;
			if((sn-1 >= 0 && map[sn-1][mi] == c) || (en+1 < N && map[en+1][mi] == c)) if(line != 4) continue;
			p = diff(one, pline);
			for(int j=0; j<line; j++){
				int nj = sn+j;
				if(nj == ni) continue;
				if(map[nj][mi] == '?') p.devide(K);
				else if(map[nj][mi] != c){ p.zero(); break; }
			}
			pline = sum(pline, p);
		}
//		System.out.println("B: "+pline);
		for(int i=line-1; i>=0; i--){ // 대각1
			int sn = ni-i; int en = sn+line-1;
			int sm = mi-i; int em = sm+line-1;
//			System.out.println("N:"+N+", M:"+M+", sn:"+sn+", en:"+en+", sm:"+sm+", em:"+em);
			if(sn < 0 || en >= N || sm < 0 || em >= M) continue;
			if((sn-1 >= 0 && sm-1 >= 0 && map[sn-1][sm-1] == c) 
					|| (en+1 < N && em+1 < M && map[en+1][em+1] == c)) if(line != 4) continue;
			p = diff(one, pline);
			for(int j=0; j<line; j++){
				int nj = sn+j; int mj = sm+j;
				if(nj == ni) continue;
				if(mj == mi) System.out.println("error!");
				if(map[nj][mj] == '?') p.devide(K);
				else if(map[nj][mj] != c){ p.zero(); break; }
			}
			pline = sum(pline, p);
//			System.out.println("p: "+p+", N:"+N+", M:"+M+", sn:"+sn+", en:"+en+", sm:"+sm+", em:"+em+", pline:"+pline);
		}
//		System.out.println("C: "+pline);
		for(int i=line-1; i>=0; i--){ // 대각2
			int sn = ni-i; int en = sn+line-1;
			int sm = mi+i; int em = sm-line+1;
			if(sn < 0 || en >= N || sm >= M || em < 0) continue;
			if((sn-1 >= 0 && sm+1 < M && map[sn-1][sm+1] == c) 
					|| (en+1 < N && em-1 >= 0 && map[en+1][em-1] == c)) if(line != 4) continue;
			p = diff(one, pline);
			for(int j=0; j<line; j++){
				int nj = sn+j; int mj = sm-j;
				if(nj == ni) continue;
				if(mj == mi) System.out.println("error!");
				if(map[nj][mj] == '?') p.devide(K);
				else if(map[nj][mj] != c){ p.zero(); break; }
			}
			pline = sum(pline, p);
		}
//		System.out.println("D: "+pline);
		return pline;
	}
	*/
	
	private Rat getValue(int ni, int mi, char c){
		Rat tmp = new Rat(0, 1);
		Rat p7 = getValue(ni, mi, c, 4, 7); tmp = sum(tmp, p7);
		Rat p6 = diff(getValue(ni, mi, c, 4, 6), tmp); tmp = sum(tmp, p6);
		Rat p5 = diff(getValue(ni, mi, c, 4, 5), tmp); tmp = sum(tmp, p5);
		Rat p4 = diff(getValue(ni, mi, c, 4, 4), tmp); tmp = sum(tmp, p4);
		Rat p3 = diff(getValue(ni, mi, c, 3, 3), tmp); tmp = sum(tmp, p3);
		Rat p2 = diff(getValue(ni, mi, c, 2, 2), tmp); tmp = sum(tmp, p2);
		System.out.print("-- ni:"+ni+", mi:"+mi+", c:"+c+", p4:"+p4+", p3:"+p3+", p2:"+p2);
		p4.multi(S4);
		p3.multi(S3);
		p2.multi(S2);
		Rat p = sum(p4, p3);
		p = sum(p, p2);
		System.out.println(", result:"+p);
		return p;
	}
	
	private Rat getValue(int ni, int mi, char c, int line, int cnt){
//		System.out.println("getValue ni:"+ni+", mi:"+mi+", c:"+c+", line:"+line);
		Rat pline;
		Rat p;
		Rat p1 = new Rat(0, 1);
		for(int i=line-1; i>=0; i--){ // 가로
			int sm = mi-i; int em = sm+cnt-1;
			if(sm < 0 || em >= M) continue;
			p = new Rat(1, 1);
			for(int j=0; j<cnt; j++){
				int mj = sm+j;
				if(mj == mi) continue;
				if(map[ni][mj] == '?') p.devide(K);
				else if(map[ni][mj] != c){ p.zero(); break; }
			}
			p1 = sum(p1, p);
		}
		pline = p1;
		if(pline.isBiggerThanOne()) return new Rat(1, 1);
//		System.out.println("A: "+pline);
		
		Rat p2 = new Rat(0, 1);
		for(int i=line-1; i>=0; i--){ // 세로
			int sn = ni-i; int en = sn+cnt-1;
			if(sn < 0 || en >= N) continue;
			p = new Rat(1, 1);
			for(int j=0; j<cnt; j++){
				int nj = sn+j;
				if(nj == ni) continue;
				if(map[nj][mi] == '?') p.devide(K);
				else if(map[nj][mi] != c){ p.zero(); break; }
			}
			p2 = sum(p2, p);
		}
		pline = sum(pline, multi(diff(one, pline), p2));
		if(pline.isBiggerThanOne()) return new Rat(1, 1);
//		System.out.println("B: "+pline);
		
		Rat p3 = new Rat(0, 1);
		for(int i=line-1; i>=0; i--){ // 대각1
			int sn = ni-i; int en = sn+cnt-1;
			int sm = mi-i; int em = sm+cnt-1;
//			System.out.println("N:"+N+", M:"+M+", sn:"+sn+", en:"+en+", sm:"+sm+", em:"+em);
			if(sn < 0 || en >= N || sm < 0 || em >= M) continue;
			p = new Rat(1, 1);
			for(int j=0; j<cnt; j++){
				int nj = sn+j; int mj = sm+j;
				if(nj == ni) continue;
				if(mj == mi) System.out.println("error!");
				if(map[nj][mj] == '?') p.devide(K);
				else if(map[nj][mj] != c){ p.zero(); break; }
			}
			p3 = sum(p3, p);
//			System.out.println("p: "+p+", N:"+N+", M:"+M+", sn:"+sn+", en:"+en+", sm:"+sm+", em:"+em+", pline:"+pline);
		}
		pline = sum(pline, multi(diff(one, pline), p3));
		if(pline.isBiggerThanOne()) return new Rat(1, 1);
//		System.out.println("C: "+pline);
		
		Rat p4 = new Rat(0, 1);
		for(int i=line-1; i>=0; i--){ // 대각2
			int sn = ni-i; int en = sn+cnt-1;
			int sm = mi+i; int em = sm-cnt+1;
			if(sn < 0 || en >= N || sm >= M || em < 0) continue;
			p = new Rat(1, 1);
			for(int j=0; j<cnt; j++){
				int nj = sn+j; int mj = sm-j;
				if(nj == ni) continue;
				if(mj == mi) System.out.println("error!");
				if(map[nj][mj] == '?') p.devide(K);
				else if(map[nj][mj] != c){ p.zero(); break; }
			}
			p4 = sum(p4, p);
		}
		pline = sum(pline, multi(diff(one, pline), p4));
		if(pline.isBiggerThanOne()) return new Rat(1, 1);
//		System.out.println("D: "+pline);
		
		if(ni==1 && mi==1 && c=='1' && line==3){
			System.out.println("가로: "+p1+", 세로: "+p2+", 대각1: "+p3+", 대각2: "+p4);
		}
		
		return pline;
	}
	
	private class Rat {
		private long nume;
		private long deno;
		
		public Rat(long nume, long deno){
			this.nume = nume;
			this.deno = deno;
			reduct();
		}
		
		public void devide(int k){
			this.deno *= k;
			reduct();
		}
		
		public void multi(int k){
			this.nume *= k;
			reduct();
		}
		
		public void zero(){
			this.nume = 0;
		}
		
		public boolean isOne(){
			return this.nume==this.deno;
		}
		
		public boolean isBiggerThanOne(){
			return this.nume > this.deno;
		}
		
		public double getDouble(){
			return this.nume / (double)this.deno;
		}
		
		public void reduct(){
			if(K == 0) return;
			while(this.nume%K==0 && this.deno%K==0){
				this.nume /= K;
				this.deno /= K;
			}
		}

		@Override
		public String toString() {
			return "("+this.nume+" / "+this.deno+")";
		}
		
	}
	
	private Rat sum(Rat a, Rat b){
		if(a == null) return b;
		if(b == null) return a;
		if(a.deno > b.deno){
			if(a.deno % b.deno == 0){
				long ratio = a.deno / b.deno;
				return new Rat(a.nume+(b.nume*ratio), a.deno);
			}
			else{
				return new Rat((a.nume*b.deno)+(b.nume*a.deno), a.deno*b.deno);
			}
		}
		else{
			if(b.deno % a.deno == 0){
				long ratio = b.deno / a.deno;
				return new Rat(b.nume+(a.nume*ratio), b.deno);
			}
			else{
				return new Rat((a.nume*b.deno)+(b.nume*a.deno), a.deno*b.deno);
			}
		}
	}
	
	private Rat diff(Rat a, Rat b){
		if(a == null) return b;
		if(b == null) return a;
		if(a.deno > b.deno){
			if(a.deno % b.deno == 0){
				long ratio = a.deno / b.deno;
				return new Rat(a.nume-(b.nume*ratio), a.deno);
			}
			else{
				return new Rat((a.nume*b.deno)-(b.nume*a.deno), a.deno*b.deno);
			}
		}
		else{
			if(b.deno % a.deno == 0){
				long ratio = b.deno / a.deno;
				return new Rat((a.nume*ratio)-b.nume, b.deno);
			}
			else{
				return new Rat((a.nume*b.deno)-(b.nume*a.deno), a.deno*b.deno);
			}
		}
	}
	
	private Rat multi(Rat a, Rat b){
		return new Rat(a.nume*b.nume, a.deno*b.deno);
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_B_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
