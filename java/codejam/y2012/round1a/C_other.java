package gcj.y2012.round1a;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.io.*;
import java.util.*;

public class C_other {
	Scanner sc = new Scanner(System.in);
	
	int N;
	int[] side;
	int[] speed;
	int[] pos;
	
	void read() {
		N = sc.nextInt();
		side = new int[N];
		speed = new int[N];
		pos = new int[N];
		for (int i = 0; i < N; i++) {
			side[i] = sc.next().equals("L") ? 0 : 1;
			speed[i] = sc.nextInt();
			pos[i] = sc.nextInt();
		}
	}
	
	double EPS = 1e-10;
	
	boolean ok(double[] ts, int n) {
		V[] vs = new V[N * n * 2];
		for (int i = 0; i < vs.length; i++) vs[i] = new V();
		for (int i = 0; i < N; i++) {
			// t=0일때의 vs초기값들. 오른쪽에 왼쪽넣고, 왼쪽에 오른쪽 넣고.
			if (side[i] == 0) {
				vs[i * 2 + 1].add(vs[i * 2]);
			} else {
				vs[i * 2].add(vs[i * 2 + 1]);
			}
		}
		double[] ps = new double[N];
		for (int i = 0; i + 1 < n; i++) {
			double t = ts[i];
			for (int j = 0; j < N; j++) ps[j] = pos[j] + speed[j] * t;
			for (int j = 0; j < N; j++) {
				boolean ok = true;
				// 레인과 상관없이 j가 k와 5미터 이내에 있으면 ok=false 
				for (int k = 0; k < N; k++) if (ps[j] + EPS < ps[k] + 5 && ps[k] + EPS < ps[j] + 5) {
					if (j != k) ok = false;
				}
				if (!ok) {
					// j와 5미터 이내에 한개라도 있으면, 아래 내용 각 j에 대하여 하나씩 추가
					vs[i * N * 2 + j * 2].add(vs[(i + 1) * N * 2 + j * 2]);  // 현재시간 j 왼쪽에 다음시간 j 왼쪽 추가
					vs[(i + 1) * N * 2 + j * 2].add(vs[i * N * 2 + j * 2]);  // 다음시간 j 왼쪽에 현재시간 j 왼쪽 추가
					vs[i * N * 2 + j * 2 + 1].add(vs[(i + 1) * N * 2 + j * 2 + 1]);  // 현재시간 j 오른쪽에 다음시간 j 오른쪽 추가
					vs[(i + 1) * N * 2 + j * 2 + 1].add(vs[i * N * 2 + j * 2 + 1]);  // 다음시간 j 오른쪽에 현재시간 j 오른쪽 추가
				}
			}
		}
		for (int i = 0; i < n; i++) {
			double t = ts[i];
			for (int j = 0; j < N; j++) ps[j] = pos[j] + speed[j] * t;
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < j; k++) if (ps[j] + EPS < ps[k] + 5 && ps[k] + EPS < ps[j] + 5) {
					// j가 k와 5미터 이내에 있으면, 각 j,k에 대하여 추가
					vs[i * N * 2 + j * 2].add(vs[i * N * 2 + k * 2 + 1]); // j 왼쪽에 k 오른쪽 추가
					vs[i * N * 2 + j * 2 + 1].add(vs[i * N * 2 + k * 2]); // j 오른쪽에 k 왼쪽 추가
					vs[i * N * 2 + k * 2].add(vs[i * N * 2 + j * 2 + 1]); // k 왼쪽에 j 오른쪽 추가
					vs[i * N * 2 + k * 2 + 1].add(vs[i * N * 2 + j * 2]); // k 오른쪽에 j 왼쪽 추가
				}
			}
		}
		scc(vs);  // strongly connected component 강결합 컴포넌트
		for (int i = 0; i < n; i++) for (int j = 0; j < N; j++) {
			// 모든  시간값, 모든 자동차에 대하여, 왼쪽과 오른쪽이 동일한 scc안에 있으면 막히는 구간이 존재한다고 본다.
			if (vs[i * N * 2 + j * 2].comp == vs[i * N * 2 + j * 2 + 1].comp) return false;
		}
		return true;
	}
	
	void solve() {
		TreeSet<Double> set = new TreeSet<Double>();
		set.add(0.0);
		set.add(1e10);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) if (speed[i] < speed[j]) {
				double t = (double)(pos[i] - pos[j] + 5) / (speed[j] - speed[i]);
				if (t >= 0) set.add(t);
				t = (double)(pos[i] - pos[j] - 5) / (speed[j] - speed[i]);
				if (t >= 0) set.add(t);
			}
		}
		Double[] ds = set.toArray(new Double[0]);
		double[] ts = new double[ds.length * 2 - 1];
		for (int i = 0; i < ds.length; i++) ts[i * 2] = ds[i];
		for (int i = 0; i < ds.length - 1; i++) ts[i * 2 + 1] = (ds[i] + ds[i + 1]) / 2;
		if (ok(ts, ts.length)) System.out.println("Possible");
		else {
			int left = 0, right = ts.length;
			while ((right - left) > 1) {
				int mid = (left + right) / 2;
				if (ok(ts, mid)) left = mid;
				else right = mid;
			}
			System.out.printf("%.10f%n", ts[max(0, left - 1)]);
		}
	}
	
	void run() {
		int caseN = sc.nextInt();
		for (int caseID = 1; caseID <= caseN; caseID++) {
			read();
			System.out.printf("Case #%d: ", caseID);
			solve();
			System.out.flush();
		}
	}
	
	int n;
	V[] us;
	int scc(V[] vs) {
		n = vs.length;
		us = new V[n];
		for (V v : vs) if (!v.visit) dfs(v);
		for (V v : vs) v.visit = false;
		for (V u : us) if (!u.visit) dfsrev(u, n++);
		return n;
	}
	void dfs(V v) {
		v.visit = true;
		for (V u : v.fs) if (!u.visit) dfs(u);
		us[--n] = v;
	}
	void dfsrev(V v, int k) {
		v.visit = true;
		for (V u : v.rs) if (!u.visit) dfsrev(u, k);
		v.comp = k;
	}
	int sccNonRec(V[] vs) {
		int n = vs.length;
		V[] us = new V[n];
		for (V v : vs) if (!v.visit) {
			while (v != null) {
				v.visit = true;
				if (v.p < v.fs.size()) {
					V u = v.fs.get(v.p++);
					if (!u.visit) {
						u.prev = v;
						v = u;
					}
				} else {
					us[--n] = v;
					v = v.prev;
				}
			}
		}
		for (V v : vs) {
			v.visit = false;
			v.p = 0;
			v.prev = null;
		}
		for (V v : us) if (!v.visit) {
			while (v != null) {
				v.visit = true;
				if (v.p < v.rs.size()) {
					V u = v.rs.get(v.p++);
					if (!u.visit) {
						u.prev = v;
						v = u;
					}
				} else {
					v.comp = n;
					v = v.prev;
				}
			}
			n++;
		}
		return n;
	}
	
	class V {
		ArrayList<V> fs = new ArrayList<V>(), rs = new ArrayList<V>();
		int comp, p;
		boolean visit;
		V prev;
		void add(V to) {
			fs.add(to);
			to.rs.add(this);
		}
	}
	
	void debug(Object...os) {
		System.err.println(deepToString(os));
	}
	
	public static void main(String[] args) {
		try {
			String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/C-large-practice.in.txt";
//			String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/test.in.txt";
			System.setIn(new BufferedInputStream(new FileInputStream(path)));
		} catch (Exception e) {
		}
		new C_other().run();
	}
}
