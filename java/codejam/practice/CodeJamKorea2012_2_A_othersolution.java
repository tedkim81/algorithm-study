package com.codejam;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class CodeJamKorea2012_2_A_othersolution implements CodeJamCaseSolver {
	
	int n;
	int[] a = new int[3];
	int[] b = new int[3];
	long[][] c = new long[3][3];

	@Override
	public void input(Scanner in) {
		n = in.nextInt();
		for(int i=0;i<3;i++)
			a[i] = in.nextInt();
		for(int i=0;i<3;i++)
			b[i] = in.nextInt();
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				c[i][j] = in.nextLong();
	}
	
	Comparator<Pair<Integer,Integer>> comp = new Comparator<Pair<Integer,Integer>>() {
		@Override
		public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
			int comp1 = o1.v1.compareTo(o2.v1);
			if(comp1 != 0)
				return comp1;
			return o1.v2.compareTo(o2.v2);
		}
	};

	
	@Override
	public String solve() {
		List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer,Integer>>();
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				list.add(new Pair<Integer, Integer>(i,j));
		Collections.sort(list, comp);
		
		long maxscore = -1000000000000000000L;
		do {
			int[] ra = new int[3];
			int[] rb = new int[3];
			for(int i=0;i<3;i++) {
				ra[i] = a[i];
				rb[i] = b[i];
			}	
			long score = 0;
			for(Pair<Integer, Integer> p : list) {
				int me = p.v1;
				int you = p.v2;
				int go = Math.min(ra[me], rb[you]);
				ra[me] -= go;
				rb[you] -= go;
				score += c[me][you] * go;
			}
			maxscore = Math.max(maxscore, score);
		} while(NextPermutation.step(list, comp));
		
		return maxscore + "";
	}

	public static void main(String[] args) throws Exception {
		CodeJamSolver.launch(8, "A-small-practice.in.txt", new CodeJamCaseSolverFactory() {
			@Override
			public CodeJamCaseSolver createSolver() {
				return new CodeJamKorea2012_2_A_othersolution();
			}
		});
	}

}
interface CodeJamCaseSolver {
	void input(Scanner in);
	String solve();
}
interface CodeJamCaseSolverFactory {
	CodeJamCaseSolver createSolver();
}
class CodeJamSolver {

	public static void launch(int threadNumber, String inputFileNameOnDesktop, CodeJamCaseSolverFactory factory, Integer... caseNumbers) throws Exception {
		if(!inputFileNameOnDesktop.endsWith(".in.txt"))
			throw new RuntimeException("input filename should ends with '.in.txt'");
		String outputFileNameOnDesktop = inputFileNameOnDesktop.substring(0, inputFileNameOnDesktop.length()-7) + ".out.txt";
		String codejamDir = System.getProperty("user.home") + "/codejam";
		String inputFile = codejamDir + "/" + inputFileNameOnDesktop;
		String outputFile = codejamDir + "/" + outputFileNameOnDesktop;
		launch(inputFile, outputFile, threadNumber, factory, caseNumbers);
	}

	private static void launch(String inputFilePath, String outputFilePath, final int threadNumber, final CodeJamCaseSolverFactory factory, Integer... caseNumbers) throws Exception {
		final Object lock = new Object();
		final long startTime = System.currentTimeMillis();
		final Scanner in = new Scanner(new File(inputFilePath));
		final int casen = in.nextInt();
		final int[] nextIndex = {0}; 
		final String[] results = new String[casen];
		final Set<Integer> caseNumberSet = createCaseNumberSet(casen, caseNumbers);
		ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
		for(int i=0;i<casen;i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					int casei;
					String result = null;
					{
						CodeJamCaseSolver caseSolver = factory.createSolver();
						synchronized(lock) {
							caseSolver.input(in);
							casei = nextIndex[0]++;
						}
						if(caseNumberSet.contains(casei+1))
							result = caseSolver.solve();
					}
					if(result != null) {
						synchronized(lock) {
							results[casei] = result;
							int solved = countNotNull(results);
							long currentTime = System.currentTimeMillis();
							long duration = currentTime - startTime;
							long seconds = (long)Math.round((double)duration * caseNumberSet.size() / solved / 1000);
							System.out.printf("%.03fs : %d/%d solved (estimated : %dm %ds)\n", (double)duration / 1000, solved,  caseNumberSet.size(), seconds / 60, seconds % 60);
						}
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		PrintStream ps = new PrintStream(outputFilePath);
		printLine();
		for(int i=0;i<casen;i++) {
			if(results[i] != null) {
				String line = String.format("Case #%d: %s", i+1, results[i]);
				System.out.println(line);
				ps.println(line);
			}
		}
		ps.close();
		printLine();
		System.out.println("Output is written to " + outputFilePath);
	}

	private static Set<Integer> createCaseNumberSet(int casen, Integer... caseNumbers) {
		final Set<Integer> set = new TreeSet<Integer>();
		if(caseNumbers.length == 0) {
			for(int i=1;i<=casen;i++)
				set.add(i);
		} else {
			for(int v : caseNumbers) {
				if(v < 1 || v >= casen)
					throw new RuntimeException("invalid index : " + v);
				set.add(v);
			}
		}
		return set;
	}

	private static void printLine() {
		for(int i=0;i<100;i++)
			System.out.print("-");
		System.out.println();
	}

	private static int countNotNull(final String[] results) {
		int solved = 0;
		for(String s : results)
			if(s != null)
				solved++;
		return solved;
	}

}
class Pair<T1,T2> {
	
	public final T1 v1;
	public final T2 v2;
	
	public Pair(T1 v1, T2 v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		Pair<T1,T2> t = (Pair<T1,T2>)obj;
		return v1.equals(t.v1) && v2.equals(t.v2);
	}
	
	@Override
	public int hashCode() {
		return PairHashFunction.hash(v1.hashCode(), v2.hashCode());
	}
}
class PairHashFunction {
	
	// from http://www.concentric.net/~ttwang/tech/inthash.htm

	public static int hash(int h1, int h2) {
		return hash6432shift((((long) h1) << 32) | h2);
	}

	private static int hash6432shift(long key) {
		key = (~key) + (key << 18); // key = (key << 18) - key - 1;
		key = key ^ (key >>> 31);
		key = key * 21; // key = (key + (key << 2)) + (key << 4);
		key = key ^ (key >>> 11);
		key = key + (key << 6);
		key = key ^ (key >>> 22);
		return (int) key;
	}

}
class NextPermutation {
	
	public static <T> boolean step(List<T> a, Comparator<T> comp) {
		int p = findLastIncresingPosition(a, comp, -1);
		if(p != -1) {
			ListPartReverser.reverse(a, p+1, a.size()-1);
			ListSwapper.swap(a, p, findFirstBiggerPosition(a, p+1, a.get(p), comp));
			return true;
		} else {
			return false;
		}
	}

	private static <T> int findLastIncresingPosition(List<T> a, Comparator<T> comp, int def) {
		for(int i=a.size()-2;i>=0;i--)
			if(comp.compare(a.get(i), a.get(i+1)) < 0)
				return i;
		return def;
	}
	
	private static <T> int findFirstBiggerPosition(List<T> a, int startp, T target, Comparator<T> comp) {
		for(int i=startp;i<a.size();i++)
			if(comp.compare(a.get(i), target) > 0)
				return i;
		throw new RuntimeException();
	}

}
class ListPartReverser {

	static public <T> void reverse(List<T> a, int start, int end) {
		for(int i=0;i<(end-start+1)/2;i++)
			ListSwapper.swap(a, start+i, end-i); 
	}

}
class ListSwapper {
	
	public static <T> void swap(List<T> a, int p1, int p2) {
		T t = a.get(p1);
		a.set(p1, a.get(p2));
		a.set(p2, t);
	}
	
}
