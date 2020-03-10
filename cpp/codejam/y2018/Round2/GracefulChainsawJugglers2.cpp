#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <math.h>
#include <list>

using namespace std;

class solution1 {
public:
	int R, B, rbsize;
	pair<int, int> ll[2601];
	bool ss[2601];

	solution1(int r, int b) {
		R = r;
		B = b;
		rbsize = (r+1)*(b+1);
	}
	int reset_ll() {
		fill_n(ll, rbsize, make_pair(0, 0));
		int k = 0;
		for (int i=0; i<=R; i++) {
			for (int j=0; j<=B; j++) {
				if (i==0 && j==0) {
					continue;
				}
				ll[k++] = make_pair(i, j);
			}
		}
		return k;
	}
	int solve() {
		fill_n(ss, rbsize, false);
		
		int k = reset_ll();
		int cnt = 0;
		while (true) {
			int size2 = 0;
			int diff2 = 251001;
			int i2 = -1;
			int rr2, bb2;
			for (int i=0; i<k; i++) {
				if (ss[i]) {
					continue;
				}
				int rr = ll[i].first;
				int bb = ll[i].second;
				if (R >= rr && B >= bb) {
					int size = (R-rr) + (B-bb);
					int diff = abs((R-rr) - (B-bb));
					if (size >= size2 && diff < diff2) {
						size2 = size;
						diff2 = diff;
						i2 = i;
						rr2 = rr;
						bb2 = bb;
					}
				}
			}
			if (i2 == -1) {
				break;
			}
			cnt++;
			ss[i2] = true;
			R -= rr2;
			B -= bb2;
		}
		return cnt;
	}
};

class solution2 {
public:
	int R, B, rbsize;
	pair<int, int> ll[2601];
	char cache[2601][51][51];

	solution2(int r, int b) {
		R = r;
		B = b;
		rbsize = (r+1)*(b+1);
	}
	void reset_cache() {
		for (int i=0; i<rbsize; i++) {
			for (int j=0; j<=R; j++) {
				for (int k=0; k<=B; k++) {
					cache[i][j][k] = -1;
				}
			}
		}
	}
	void reset_ll() {
		fill_n(ll, rbsize, make_pair(0, 0));
		int k = 0;
		for (int i=0; i<=R; i++) {
			for (int j=0; j<=B; j++) {
				ll[k++] = make_pair(i, j);
			}
		}
	}
	int solve() {
		reset_cache();
		reset_ll();
		return _solve((R+1)*(B+1)-1, R, B);
	}
	int _solve(int i, int r, int b) {
		if (i == 0 || (r == 0 && b == 0)) {
			return 0;
		}
		if ((int)cache[i][r][b] >= 0) {
			return (int)cache[i][r][b];
		}
		int result = _solve(i-1, r, b);
		if (r >= ll[i].first && b >= ll[i].second) {
			result = max(result, 1+_solve(i-1, r-ll[i].first, b-ll[i].second));
		}
		cache[i][r][b] = result;
		return result;
	}
};

class solution3 {
public:
	int R, B;

	solution3(int r, int b) {
		R = r;
		B = b;
	}
	int solve() {
		int size = 0;
		for (int bb1=0; bb1<=B; bb1++) {
			for (int rr1=0; rr1<=R; rr1++) {
				if (bb1 == 0 && rr1 == 0) {
					continue;
				}
				int bbsum1 = (rr1+1)*(1+bb1)*bb1/2;
				int rrsum1 = (bb1+1)*(1+rr1)*rr1/2;
				int size1 = (bb1+1)*(rr1+1)-1;
				if (bbsum1 <= B && rrsum1 <= R) {
					size = max(size, size1);
					for (int bb2=0; bb2<=B; bb2++) {
						int rrstart = 0;
						if (bb2 <= bb1) {
							rrstart = rr1+1;
						}
						for (int rr2=rrstart; rr2<=R; rr2++) {
							if ((bb2 == 0 && rr2 == 0) || (bb2 >= bb1 && rr2 >= rr1)) {
								continue;
							}
							int bbsum2 = (rr2+1)*(1+bb2)*bb2/2;
							int rrsum2 = (bb2+1)*(1+rr2)*rr2/2;
							int size2 = (bb2+1)*(rr2+1)-1;
							int bb3 = min(bb1, bb2);
							int rr3 = min(rr1, rr2);
							int bbsum3 = (rr3+1)*(1+bb3)*bb3/2;
							int rrsum3 = (bb3+1)*(1+rr3)*rr3/2;
							int size3 = (bb3+1)*(rr3+1)-1;
							int bbsum = bbsum1 + bbsum2 - bbsum3;
							int rrsum = rrsum1 + rrsum2 - rrsum3;
							if (bbsum <= B && rrsum <= R) {
								int B2 = B - bbsum;
								int R2 = R - rrsum;
								int ex = 0;
								if ((B2 > bb1 || R2 > rr1) && (B2 > bb2 || R2 > rr2)) {
									int B3 = B2;
									int R3 = R2;
									for (int bb4=0; bb4<=B3; bb4++) {
										for (int rr4=0; rr4<=R3; rr4++) {
											if (bb4 == 0 && rr4 == 0) {
												continue;
											}
											if ((bb4 > bb1 || rr4 > rr1) && (bb4 > bb2 || rr4 > rr2) 
													&& (bb4 <= B2 && rr4 <= R2)) {
												B2 -= bb4;
												R2 -= rr4;
												ex++;
											}
										}
									}
								}
								size = max(size, size1+size2-size3+ex);
								if (size1+size2-size3+ex == 20) {
									cout << size1 << " + " << size2 << " - " << size3 << " + " << ex << endl;
								}
							} else {
								break;
							}
						}
					}
				}
			}
		}
		return size;
	}
};

class solution4 {
public:
	int R, B, rbsize;
	pair<int, int> ll[251001];
	list< pair<int, int> > li;

	solution4(int r, int b) {
		R = r;
		B = b;
		rbsize = (r+1)*(b+1);
		reset_ll();
	}
	void reset_ll() {
		fill_n(ll, rbsize, make_pair(0, 0));
		int k = 0;
		for (int i=0; i<=R; i++) {
			for (int j=0; j<=B; j++) {
				ll[k++] = make_pair(i, j);
			}
		}
	}
	int solve() {
		return _solve((R+1)*(B+1)-1, R, B);
	}
	int _solve(int i, int r, int b) {
		if (i == 0 || (r == 0 && b == 0)) {
			return 0;
		}
		int rr,bb;
		for (; i>=0; i--) {
			rr = ll[i].first;
			bb = ll[i].second;
			if (floor((bb+1)*rr*(rr+1)/2) <= r && floor((rr+1)*bb*(bb+1)/2) <= b) {
				break;
			}
		}
		bool aa = false;
		for (list< pair<int,int> >::iterator ii = li.begin(); ii != li.end(); ii++) {
			int rr2 = (*ii).first;
			int bb2 = (*ii).second;
			if (rr2 >= rr && bb2 >= bb) {
				aa = true;
				break;
			}
		}
		if (aa) {
			return 1+_solve(i-1, r-rr, b-bb);
		}
		int result1 = _solve(i-1, r, b);
		li.push_back(make_pair(rr, bb));
		int result2 = 1+_solve(i-1, r-rr, b-bb);
		li.pop_back();
		return max(result1, result2);
	}
};

class solution5 {
public:
	int rbmap[501][501];
	solution5() {
		int R = 500;
		int B = 500;
		for (int rr=0; rr<=R; rr++) {
			for (int bb=0; bb<=B; bb++) {
				rbmap[rr][bb] = 0;
			}
		}
		for (int rr=0; rr<=R; rr++) {
			if ((1+rr)*rr/2 > R) {
				break;
			}
			for (int bb=0; bb<=B; bb++) {
				if (rr == 0 && bb == 0) {
					continue;
				}
				if ((bb+1)*(1+rr)*rr/2 > R || (rr+1)*(1+bb)*bb/2 > B) {
					break;
				}
				for (int rr2=R; rr2>=rr; rr2--) {
					for (int bb2=B; bb2>=bb; bb2--) {
						rbmap[rr2][bb2] = max(rbmap[rr2][bb2], rbmap[rr2-rr][bb2-bb]+1);
					}
				}
			}
		}
	}
	int solve(int R, int B) {
		return rbmap[R][B];
	}
};

int main() {
	solution5 sol = solution5();
	// int r = sol.solve(500,500);
	// cout << r << endl;
	for (int i=0; i<=50; i++) {
		for (int j=0; j<=50; j++) {
			int r1 = solution2(i,j).solve();
			int r2 = sol.solve(i,j);
			if (r1 != r2) {
				cout << "R:" << i << ", B:" << j << ", results: " << r1 << ", " << r2 << endl;
			}
		}
	}
}
