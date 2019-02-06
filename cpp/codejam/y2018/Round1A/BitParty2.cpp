/**
 * BitParty.cpp의 solution 1이 "Wrong Answer"로 실패하고, 디버깅 과정에서 문제점 파악을 하지 못해서
 * 2018년 코드잼 Round 1A에서 가장 좋은 점수를 낸 vepifanov 의 코드를 다운로드 받아서 코드를 분석함.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n;
int m;
int r, b, c;
long long cm[1010];
long long cs[1010];
long long cp[1010];

long long can (long long h) {
	vector<long long> now;
	for (int i = 0; i < c; i++)
		if (cp[i] < h)
			now.push_back (min ((h - cp[i]) / cs[i], cm[i]));
	sort (now.begin(), now.end());
	reverse (now.begin(), now.end());
	long long cur = 0;
	for (int i = 0; i < min(r, (int)now.size()); i++) cur += now[i];
//	cout << h << " " << cur << endl;
	return cur;
}

int main () {
	int tt;
	cin >> tt;
	for (int it = 1; it <= tt; it++) {
		cin >> r >> b >> c;
		for (int i = 0; i < c; i++) cin >> cm[i] >> cs[i] >> cp[i];
		long long l = 0, r = 4e18;
		while (r - l > 1) {
			long long s = (l + r) / 2;
			if (can (s) >= b) r = s; else l = s;
		}
		cout.precision (20);
		cout << "Case #" << it << ": " << r;
		cout << endl;
		fprintf (stderr, "%d / %d = %.2f | %.2f\n", it, tt, (double)clock () / CLOCKS_PER_SEC, ((double)clock () / it * tt) / CLOCKS_PER_SEC);
	}
	return 0;
}