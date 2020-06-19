/**
 * 2020.4.4
 *
 * Cameron과 Jamie는 3살짜리 애를 키우는 중이다.
 * 할일이 N개가 있고, 각각은 시작시간과 종료시간이 정해져 있다.
 * 각각의 일을 둘이서 분담해야 하는데,
 * 한 사람이 시간 겹치는 일을 동시에 할 수는 없다.
 * 어떻게 분담하면 될지를 구하는 것이 문제.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 일의 수
 * (N행의) S E: 시작시간과 종료시간. 0시에서 몇분 지났는지로 표현.
 *
 * (output)
 * N개의 일에 대해 순서대로 C 또는 J로 표시.
 * (C 또는 J로 표현된 크기 N의 문자열)
 *
 * (solution 1)
 * 어차피 사람은 두사람 밖에 없으니, 일을 최대한 한사람에게 몰고
 * 나머지를 다른 사람에게 다 주는데, 그게 불가능하면 IMPOSSIBLE.
 * S[] 배열에 시작시간들 담고, E[] 배열에 종료시간들 담는다.
 * CJ[] 배열에는 C 또는 J를 차근차근 담으면 된다.
 *
 * (solution 1 result)
 * 실패.. Wrong Answer..
 * 순서와 상관이 없을줄 알았는데, 생각해보니 상관이 있다.
 * 1 3
 * 5 7
 * 2 5
 * 4 7
 * 위의 경우, 분배 가능한 상황이지만 solution 1 알고리즘대로
 * 나열된 순서대로 시도하면 IMPOSSIBLE이 된다..ㅠ.ㅠ
 *
 * (solution 2)
 * solution 1의 알고리즘을 대체로 유지하되,
 * 시작시간이 빠른 순서대로 진행시키자.
 *
 * (solution 2 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <sstream>
#include <algorithm>
#include <vector>

using namespace std;

int N;
int S[1000];
int E[1000];
char CJ[1000];

bool comp(pair<int, int> p1, pair<int, int> p2) {
	return p1.second < p2.second;
}

string solve() {
	for (int i=0; i<1000; i++) {
		CJ[i] = '.';
	}
	vector<pair<int,int> > v;
	for (int i=0; i<N; i++) {
		v.push_back(make_pair(i, S[i]));
	}
	sort(v.begin(), v.end(), comp);
	vector<int> vv;
	for (int i=0; i<N; i++) {
		vv.push_back(v[i].first);
	}
	for (int i=0; i<N; i++) {
		int ii = vv[i];
		bool ccan = true;
		bool jcan = true;
		for (int j=0; j<i; j++) {
			int jj = vv[j];
			if (CJ[jj] == 'C') {
				if (!(S[ii] >= E[jj] || E[ii] <= S[jj])) {
					ccan = false;
				}
			} else {
				if (!(S[ii] >= E[jj] || E[ii] <= S[jj])) {
					jcan = false;
				}
			}
		}
		if (ccan) {
			CJ[ii] = 'C';
		} else if (jcan) {
			CJ[ii] = 'J';
		} else {
			return "IMPOSSIBLE";
		}
	}
	stringstream ss;
	for (int i=0; i<N; i++) {
		ss << CJ[i];
	}
	return ss.str();
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int j=0; j<N; j++) {
			cin >> S[j] >> E[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}