package com.codejam;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class CodeJamKorea2012_2_B_othersolution implements CodeJamCaseSolver2 {
	
	private int n;
	ArrayList<String> s;
	
	int fa, fb;
	private int a;
	private int b;
	private int u;

	@Override
	public void input(Scanner in) {
		u = in.nextInt();
		n = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		if(u != 1)
			throw new RuntimeException();
		
		s = new ArrayList<String>();
		for(int i=0;i<n;i++)
			s.add(in.next());
		
		if(a == 1)
			fa = in.nextInt(); 
		else
			fa = -1;
		
		if(b == 1)
			fb = in.nextInt();
		else
			fb = -1;
	}
	
	@Override
	public String solve() {
		int[] cp = {-1, -1};
		boolean impossible = false;
		int turn = 0;
		for(String v : s) {
			int step = getstep(v);
			int p = cp[turn];
			if(p >= 1000)
				impossible = true;
			int prep = -1;
			for(int i=0;i<step;i++) {
				boolean fast = isfastpos(p, prep);
				prep = p;
				p = getnext(p, fast);
			}
			int opp = (turn+1)%2;
			boolean cat = false;
			if(cp[opp] == p) {
				cp[opp] = -1;
				cat = true;
			}
			
			cp[turn] = p;						
			if(!(cat || step >= 4))
				turn = opp;
			
//			System.out.println(v + " " + cp[0] + " " + cp[1]);
		}
		
//		System.out.println(fa + " " + fb);
		for(int i=0;i<2;i++)
			if(cp[i] >= 1000)
				cp[i] = -1;
		
		boolean ok = fa==cp[0] && fb == cp[1] && !impossible;
		return ok ? "YES" : "NO";
	}

	private boolean isfastpos(int p, int prep) {
		boolean fast = false;
		if(prep==-1 &&(p == 5 || p == 10 || p  == 22) || p == 22 && prep==26)
			fast = true;
		return fast;
	}

	private int getnext(int p, boolean fast) {
		if(p == 0) {
			p = 1000;
		} if(p==-1) {
			p = 1;
		} else if(p==5) {
			if(fast)
				p = 20;
			else
				p++;
		} else if(p==10) {
			if(fast)
				p = 25;
			else
				p++;
		} else if(p==22) {
			if(fast)
				p = 27;
			else
				p++;					
		} else if(p==24) {
			p = 15; 
		} else if(p == 26){
			p = 22;
		} else if(p==19 || p ==28) {
			p = 0;
		} else {
			p++;
		}
		return p;
	}

	private int getstep(String v) {
		int step = 0;
		if(v.equals("Do"))
			step = 1;
		if(v.equals("Gae"))
			step = 2;
		if(v.equals("Gul"))
			step = 3;
		if(v.equals("Yut"))
			step = 4;
		if(v.equals("Mo"))
			step = 5;
		if(step == 0)
			throw new RuntimeException();
		return step;
	}

	public static void main(String[] args) throws Exception {
		CodeJamSolver2.launch(8, "B-small-practice.in.txt", new CodeJamCaseSolverFactory2() {
			@Override
			public CodeJamCaseSolver2 createSolver() {
				return new CodeJamKorea2012_2_B_othersolution();
			}
		});
	}

}
interface CodeJamCaseSolver2 {
	void input(Scanner in);
	String solve();
}
interface CodeJamCaseSolverFactory2 {
	CodeJamCaseSolver2 createSolver();
}
class CodeJamSolver2 {

	public static void launch(int threadNumber, String inputFileNameOnDesktop, CodeJamCaseSolverFactory2 factory, Integer... caseNumbers) throws Exception {
		if(!inputFileNameOnDesktop.endsWith(".in.txt"))
			throw new RuntimeException("input filename should ends with '.in.txt'");
		String outputFileNameOnDesktop = inputFileNameOnDesktop.substring(0, inputFileNameOnDesktop.length()-7) + ".out.txt";
		String codejamDir = System.getProperty("user.home") + "/codejam";
		String inputFile = codejamDir + "/" + inputFileNameOnDesktop;
		String outputFile = codejamDir + "/" + outputFileNameOnDesktop;
		launch(inputFile, outputFile, threadNumber, factory, caseNumbers);
	}

	private static void launch(String inputFilePath, String outputFilePath, final int threadNumber, final CodeJamCaseSolverFactory2 factory, Integer... caseNumbers) throws Exception {
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
						CodeJamCaseSolver2 caseSolver = factory.createSolver();
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
				if(v < 1 || v > casen)
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
