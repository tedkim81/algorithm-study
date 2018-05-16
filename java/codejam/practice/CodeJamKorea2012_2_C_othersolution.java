package com.codejam;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 각 방을 정점으로 표현하고, 서로 맞닿은 방을 간선으로 표현하는 이분 그래프를 만들고, 
 * Hopcroft-Karp algorithm을 이용하여 살균실의 최소값을 구하여 전체 방수에서 빼는 방식이다.
 * TODO: Hopcroft-Karp algorithm 에 대한 구체적인 학습이 필요하다.
 * 
 * TODO: 맞닿은 방들을 간선으로 연결하는 그래프를 만들고, 간선을 모두 제거하기 위해 제거할 정점들의 최소값을 구하는 방식으로 다시 풀어보도록 하자.
 */
public class CodeJamKorea2012_2_C_othersolution implements CodeJamCaseSolver3 {

	MinimumVertexCoverInBipartiteGraph mvc = new Konic(new HopcroftKarp());

	int n, m, k;
	int[][][] a;
	private FourDirection fd;
	
	@Override
	public void input(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		
		a = new int[k][n][m];
		for(int f=0;f<k;f++) {
			for(int x=0;x<n;x++) {
				String line = in.next();
				for(int y=0;y<m;y++) {
					a[f][x][y] = (line.charAt(y) == '#') ? -1 : -2; 
				}
			}
		}
	}
	
	int oddcur = 0;
	int evencur = 0;
	
	boolean trypaint(int f, int x, int y, int v) {
		if(a[f][x][y] == -2) {
			a[f][x][y] = v;
			for(GridPosition gp : fd.nextPositions(new GridPosition(x, y)))
				trypaint(f, gp.x, gp.y, v);
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public String solve() {
		fd = new FourDirection(n, m);
		oddcur = 0;
		evencur = 0;
		for(int f=0;f<k;f++) 
			for(int x=0;x<n;x++) 
				for(int y=0;y<m;y++) 
					if(f%2 == 0) {
						if(trypaint(f, x, y, evencur))
							evencur++;
					} else {
						if(trypaint(f, x, y, oddcur))
							oddcur++;
					}

		ModifiableBipartiteGraph bg = new ModifiableBipartiteGraph(evencur, oddcur);

		for(int f=0;f<k-1;f++) {
			for(int x=0;x<n;x++){
				for(int y=0;y<m;y++) {
					int v1 = a[f][x][y];
					int v2 = a[f+1][x][y];
					if(v1 != -1 && v2 != -1) {
						if(f % 2 == 0) {
							bg.add(v1, v2);
						} else {
							bg.add(v2, v1);
						}
					}
				}
			}
		}
		int result = bg.leftVertexNumber() + bg.rightVertexNumber() - mvc.getMinimumVertexCover(bg);
		return result + "";
	}

	public static void main(String[] args) throws Exception {
		CodeJamSolver3.launch(8, "C-example.in", new CodeJamCaseSolverFactory3() {
			@Override
			public CodeJamCaseSolver3 createSolver() {
				return new CodeJamKorea2012_2_C_othersolution();
			}
		});
	}

}
class FourDirection extends AbstractGridDirection {
	
	public static final int UP = 0;
	public static final int DOWN = 3;
	public static final int LEFT = 2;
	public static final int RIGHT = 1;
	
	public FourDirection(int width, int height) {
		super(width, height, DX, DY);
	}

	static public int leftDirection(int dir) {
		switch(dir) {
		case UP:
			return LEFT;
		case DOWN:
			return RIGHT;
		case LEFT:
			return DOWN;
		case RIGHT:
			return UP;
		}
		return 0;
	}
	
	static public int rightDirection(int dir) {
		switch(dir) {
		case UP:
			return RIGHT;
		case DOWN:
			return LEFT;
		case LEFT:
			return UP;
		case RIGHT:
			return DOWN;
		}
		return 0;
	}
	
	static public int oppositeDirection(int dir) {
		switch(dir) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		}
		return 0;		
	}
	
	private static final int DX[] = {-1,0,0,1};
	private static final int DY[] = {0,1,-1,0};
}
class AbstractGridDirection implements GridDirection {
	
	public AbstractGridDirection(int width, int height, int[] dx, int[] dy) {
		this.width = width;
		this.height = height;
		this.dx = dx;
		this.dy = dy;
	}
	
	public Iterable<GridPosition> nextPositions(GridPosition p) {
		return nextPositions(p.x, p.y);
	}
	
	public Iterable<GridPosition> nextPositions(final int x, final int y) {
		return new Iterable<GridPosition>() {
			public Iterator<GridPosition> iterator() {
				return new AbstractIterator<GridPosition> () {
					int i=0;
					public boolean hasNext() {
						while(i<dx.length) {
							if(canGo(x,y,i))
								return true;
							i++;
						}
						return false;
					}
					public GridPosition next() {
						if(hasNext()) {
							i++;
							return new GridPosition(nextX(x,i-1), nextY(y,i-1));					
						} else
							return null;
					}
				};
			}
		};
	}

	public boolean canGo(int x, int y, int dir) {
		return isValid(nextX(x,dir), nextY(y,dir));
	}

	public boolean isValid(int x, int y) {
		return (0 <= x && x < width && 0<=y && y < height);
	}

	public int directionNumber() {
		return dx.length;
	}

	public int nextX(int x, int dir) {
		return x + dx[dir];		
	}

	public int nextY(int y, int dir) {
		return y + dy[dir];		
	}
	
	private int width;
	private int height;
	private final int[] dx;
	private final int[] dy;

}
abstract class AbstractIterator<T> implements Iterator<T> {

	final public void remove() {
		throw new UnsupportedOperationException();
	}

}
interface GridDirection {
	int directionNumber();
	boolean isValid(int x, int y);
	Iterable<GridPosition> nextPositions(int x, int y);
	boolean canGo(int x, int y, int dir);
	int nextX(int x, int dir);
	int nextY(int y, int dir);
}
class GridPosition {
	public final int x,y;
	public GridPosition(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	public final boolean equals(Object o) {
		if(o==null)
			return false;
		if(o==this)
			return true;
		if(!(o instanceof GridPosition))
			return false;
		GridPosition gp = (GridPosition)o;
		return gp.x==x && gp.y == y; 
	}
	
	public final int hashCode() {
		return x+y;
	}
}
interface CodeJamCaseSolver3 {
	void input(Scanner in);
	String solve();
}
interface CodeJamCaseSolverFactory3 {
	CodeJamCaseSolver3 createSolver();
}
class CodeJamSolver3 {

	public static void launch(int threadNumber, String inputFileNameOnDesktop, CodeJamCaseSolverFactory3 factory, Integer... caseNumbers) throws Exception {
		if(!inputFileNameOnDesktop.endsWith(".in"))
			throw new RuntimeException("input filename should ends with '.in'");
		String outputFileNameOnDesktop = inputFileNameOnDesktop.substring(0, inputFileNameOnDesktop.length()-3) + ".out";
		String desktopDir = System.getProperty("user.home") + "/Desktop";
		String inputFile = desktopDir + "/" + inputFileNameOnDesktop;
		String outputFile = desktopDir + "/" + outputFileNameOnDesktop;
		launch(inputFile, outputFile, threadNumber, factory, caseNumbers);
	}

	private static void launch(String inputFilePath, String outputFilePath, final int threadNumber, final CodeJamCaseSolverFactory3 factory, Integer... caseNumbers) throws Exception {
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
						CodeJamCaseSolver3 caseSolver = factory.createSolver();
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
class ModifiableBipartiteGraph implements BipartiteGraph {
	
	private int rightVertexNumber;
	private List<Integer>[] edge;
	
	@SuppressWarnings("unchecked")
	public ModifiableBipartiteGraph(int leftVertexNumber, int rightVertexNumber) {
		 edge = new List[leftVertexNumber];
		 for(int i=0;i<leftVertexNumber;i++)
			 edge[i] = new ArrayList<Integer>();
		 this.rightVertexNumber = rightVertexNumber;
	}
	
	public void add(int leftVertexIndex, int rightVertexIndex) {
		edge[leftVertexIndex].add(rightVertexIndex);
	}

	public Iterable<Integer> getDirectedVertices(int leftVertex) {
		return edge[leftVertex];
	}

	public int leftVertexNumber() {
		return edge.length;
	}

	public int rightVertexNumber() {
		return rightVertexNumber;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<leftVertexNumber();i++) {
			sb.append(i + " : " );
			for(int v : getDirectedVertices(i))
				sb.append(v + " ");
			sb.append('\n');
		}
		return sb.toString();
	}

}
interface BipartiteGraph {
	int leftVertexNumber();
	int rightVertexNumber();
	Iterable<Integer> getDirectedVertices(int leftVertex);
}
class HopcroftKarp implements MaximumBipartiteMatching {
	
	// O(V*root(E))
	
	public MaximumBipartiteMatchingResult getResult(BipartiteGraph graph) {
		int n = graph.leftVertexNumber();
		int m = graph.rightVertexNumber();
		final int[] m1 = new int[n];
		int[] m2 = new int[m];
		IntArrayFiller.fill(m1, -1);
		IntArrayFiller.fill(m2, -1);
		
		int[] d1 = new int[n];
		int[] pre1 = new int[n];
		int[] pre2 = new int[m];
		boolean[] f2 = new boolean[m];
		boolean[] visit1 = new boolean[n];
		boolean[] visit2 = new boolean[m];
		
		while(true) {
			IntArrayFiller.fill(d1, -1);
			IntArrayFiller.fill(pre1, -1);
			IntArrayFiller.fill(pre2, -1);

			Queue<Integer> q = new DynamicArrayQueue<Integer>();
			for(int i=0;i<n;i++)
				if(m1[i] == -1) {
					q.enque(i);
					d1[i] = 0;
				}
			
			BooleanArrayFiller.fill(f2, false);
			int lastFoundDepth = -1;
					
			while(!q.isEmpty()) {
				int v1 = q.deque();
				
				if(lastFoundDepth != -1 && d1[v1] > lastFoundDepth)
					break;
				
				for(int v2 : graph.getDirectedVertices(v1) )
					if(pre2[v2] == -1) {
						pre2[v2] = v1;
						if(m2[v2] == -1) {
							lastFoundDepth = d1[v1];
							f2[v2] = true;
						} else if(pre1[m2[v2]] == -1) {
							q.enque(m2[v2]);
							pre1[m2[v2]] = v2;
							d1[m2[v2]] = d1[v1]+1;
						}
					}
				
			}

			if(lastFoundDepth == -1)
				break;

			BooleanArrayFiller.fill(visit1, false);
			BooleanArrayFiller.fill(visit2, false);
			for(int i=0;i<m;i++)
				if(f2[i]) {
					int v2 = i; 
					visit2[v2] = true;
					boolean ok = true;
					while(ok) {
						int v1 = pre2[v2];
						if(visit1[v1])
							ok = false;
						visit1[v1] = true;
						if(pre1[v1] == -1)
							break;
						v2 = pre1[v1];
						if(visit2[v2])
							ok = false;
						visit2[v2] = true;
					}
					if(ok) {
						v2 = i;
						while(v2 != -1) {
							m2[v2] = pre2[v2];
							m1[pre2[v2]] = v2;
							v2 = pre1[pre2[v2]];
						}
					}
				}
			}

		int tot = 0;
		for(int i=0;i<n;i++)
			if(m1[i] != -1)
				tot++;
		
		final int ftot = tot;
		return new MaximumBipartiteMatchingResult() {
			public int getMatchedVertex(int from) {
				return m1[from];
			}
			public int getMaxMatchCount() {
				return ftot;
			}
			public boolean hasMatch(int from) {
				return m1[from] != -1;
			}			
		};
	}
}
class DynamicArrayQueue<T> implements Queue<T> {

	private final DynamicArray<T> a;
	private int next = 0;

	public DynamicArrayQueue(int initCapacity) {
		a = new DynamicArray<T>(initCapacity);
	}
	
	public DynamicArrayQueue() {
		a = new DynamicArray<T>();
	}
	
	public T deque() {
		next ++;
		return a.get(next-1);
	}

	public void enque(T v) {
		a.addToLast(v);
	}

	public boolean isEmpty() {
		return next == a.size();
	}
	
}
class DynamicArray<T> extends AbstractReadableArray<T>  implements StaticArray<T> {
	
	private T[] a;
	private int asize;
	
	
	@SuppressWarnings("unchecked")
	private void init(int cap) {
		asize = 0;
		a = (T[])new Object[Math.max(1, cap)];		
	}
	
	public DynamicArray() {
		init(1);
	}
	
	public DynamicArray(int initialCapacity) {
		init(initialCapacity);
	}
	
	public DynamicArray(Container<T> itemsToAdd) {
		init(itemsToAdd.size());
		addToLastAll(itemsToAdd);
	}
	
	public T get(int index) {
		return a[index];
	}	
	
	public void set(int index, T value) {
		a[index] = value;		
	}
	
	public int size() {
		return asize;
	}
	
	public void clear() {
		asize = 0;
	}
	
	@SuppressWarnings("unchecked")
	public void reserve(int size) {
		if(a.length < size) {
			T[] ta = (T[])new Object[size];
			for(int i=0;i<a.length;i++)
				ta[i] = a[i];
			a = ta;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addToLast(T value) {
		if(a.length == asize) {
			T[] ta = (T[])new Object[asize*2];
			for(int i=0;i<asize;i++)
				ta[i] = a[i];
			a =ta;
		}
		a[asize++] = value;						
	}
	
	public void addToLastAll(Iterable<T> values) {
		for(T v : values)
			addToLast(v);
	}
	
	public T removeLast() {
		T r = last();
		a[--asize] = null;
		return r;
	}
	
}
interface Container<T> extends Iterable<T> {
	int size();
	boolean isEmpty();
}
abstract class AbstractReadableArray<T> extends AbstractContainer<T> implements ReadableArray<T> {
	
	final public boolean isEmpty() {
		return size() == 0;
	}
	
	final public T last() {
		return get(size()-1);
	}
	
	final public T first() {
		return get(0);
	}	
	
	final public Iterator<T> iterator() {
		return new AbstractIterator<T>() {
			int p = 0;
			public boolean hasNext() {
				return p < size();
			}
			public T next() {
				return get(p++);
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	final public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ReadableArray))
			return false;
		ReadableArray<T> o = (ReadableArray<T>) obj;
		if(size() != o.size())
			return false;
		for(int i=0;i<size();i++)
			if(!get(i).equals(o.get(i)))
				return false;		
		return true;
	}
	
	final public int hashCode() {
		int r = 0;
		for(int i=0;i<size();i++)
			r += get(i).hashCode();
		return r;
	}
	
	
		
}
abstract class AbstractContainer<T> implements Container<T> {

	final public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('(');
		boolean first = true;
		for(T v : this) {
			if(first)
				first = false;
			else
				sb.append(',');
			sb.append(v);
		}
		sb.append(')');
		return sb.toString();
	}
}
interface ReadableArray<T> extends Container<T> {
	T get(int index);
	T last();
	T first();
}
interface StaticArray<T> extends ReadableArray<T>{
	void set(int index, T value);	
}
interface Queue<T> {
	void enque(T v);
	T deque();
	boolean isEmpty();
}
class BooleanArrayFiller {
	
	static public void fill(boolean[] a, final boolean v) {
		for(int i=0;i<a.length;i++)
			a[i] = v;
	}
	
	static public void fill(boolean[][] a, boolean v) {
		for(int i=0;i<a.length;i++)
			fill(a[i], v);
	}

	static public void fill(boolean[][][] a, boolean v) {
		for(int i=0;i<a.length;i++)
			fill(a[i], v);
	}

	static public void fill(boolean[][][][] a, boolean v) {
		for(int i=0;i<a.length;i++)
			fill(a[i], v);
	}

}
class IntArrayFiller {
	
	static public void fill(int[] a, int v) {
		for(int i=0;i<a.length;i++)
			a[i] = v;
	}
	static public void fill(int[][] a, int v) {
		for(int[] sub : a)
			fill(sub, v);
	}
	static public void fill(int[][][] a, int v) {
		for(int[][] sub : a)
			fill(sub, v);
	}
	static public void fill(int[][][][] a, int v) {
		for(int[][][] sub : a)
			fill(sub, v);
	}
}
interface MaximumBipartiteMatching {
	
	// tco03-semi4-1050 RookAttack ? ???? ?????.
	MaximumBipartiteMatchingResult getResult(BipartiteGraph graph);
}
interface MaximumBipartiteMatchingResult {
	int getMaxMatchCount();
	boolean hasMatch(int from);
	int getMatchedVertex(int from);
}
class Konic implements MinimumVertexCoverInBipartiteGraph {
	
	// srm303-1-500

	private MaximumBipartiteMatching mbm;
	
	public Konic(MaximumBipartiteMatching mbm) {
		this.mbm = mbm;
	}
	
	public int getMinimumVertexCover(BipartiteGraph bg) {
		return mbm.getResult(bg).getMaxMatchCount();
	}
}
interface MinimumVertexCoverInBipartiteGraph {

	int getMinimumVertexCover(BipartiteGraph bg);

}
