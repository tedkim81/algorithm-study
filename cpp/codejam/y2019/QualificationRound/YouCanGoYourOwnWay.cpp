/**
 * 2019.4.6
 *
 * 정사각 격자형 공간이 있고, 북서쪽끝(좌측상단)에서 남동쪽끝(우측하단)으로 이동해야 한다.
 * 남쪽 한칸씩 혹은 동쪽 한칸씩 이동 가능하다.
 * 그런데, 이미 앞사람이 이동한 경로가 주어질때, 그사람이 지났던 길은 지나고 싶지 않다고 한다.
 * 앞사람이 갔던 곳(지점)을 가는 것은 괜찮지만, 갔던 길(경로)을 다시 걷는 것은 안되는 것이다.
 * 이러한 조건을 만족하는 이동경로를 구하자.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 격자형 공간의 크기. N x N
 * P: 앞사람의 이동경로. S(남쪽한칸)와 E(동쪽한칸)로 만들어진 2N-2 크기의 문자열
 *
 * (output)
 * 나의 이동경로. S와 E로 만들어진 문자열
 *
 * (solution 1)
 * 앞사람의 이동경로를 구조화하는 방법
 * int SS[100000], EE[100000]이 있고, P[i]가 'S'면 SS[i+1]=SS[i]+1 하고, 
 * 'E'면 EE[i+1]=EE[i]+1 한다.
 * 이제 (0,0)부터 시작해서 S로 갈지 E로 갈지를 결정하면서 이분탐색을 하는데,
 * 현재 위치가 (s,e)일 경우, SS[s+e]와 EE[s+e]를 확인하여 앞사람이 같은 위치를 자났다면,
 * 앞사람의 선택은 P[s+e]를 선택하지 않으면서 탐색하면 된다.
 * -------------------------------
 * string findpath(int s, int e):
 *   if s == N-1 and e == N-1:
 *     return ''
 *   if s > N-1 or e > N-1:
 *     return 'X'
 *   
 *   // 여기서 메모이제이션!!
 *   long long se = s + (e * (long long)100000)
 *   if mm[se]가 있으면:
 *     return mm[se]
 * 
 *   if SS[s+e] == s and EE[s+e] == e:
 *     if P[s+e] == 'S':
 *       pp = findpath(s, e+1)
 *       if pp == 'X':
 *         return 'X'
 *       mm[se] = 'E' + pp
 *       return 'E' + pp
 *     else:
 *       pp = findpath(s+1, e)
 *       if pp == 'X':
 *         return 'X'
 *       mm[se] = 'S' + pp
 *       return mm[se]
 *   else:
 *     pp = findpath(s+1, e)
 *     if pp != 'X':
 *       mm[se] = 'S' + pp
 *       return mm[se]
 *     pp = findpath(s, e+1)
 *     if pp != 'X':
 *       mm[se] = 'E' + pp
 *       return mm[se]
 *     return 'X'
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <map>

using namespace std;

int N;
string P;
int SS[100000], EE[100000];
map<long long, string> mm;

string findpath(int s, int e) {
	if (s == N-1 && e == N-1) {
		return "";
	}
	if (s > N-1 || e > N-1) {
		return "X";
	}
	long long se = s + (e * (long long)100000);
	if (mm.find(se) != mm.end()) {
		return mm[se];
	}
	string pp;
	if (SS[s+e] == s && EE[s+e] == e) {
		if (P[s+e] == 'S') {
			pp = findpath(s, e+1);
			if (pp == "X") {
				mm[se] = "X";
				return "X";
			}
			pp = "E" + pp;
			mm[se] = pp;
			return pp;
		} else {
			pp = findpath(s+1, e);
			if (pp == "X") {
				mm[se] = "X";
				return "X";
			}
			pp = "S" + pp;
			mm[se] = pp;
			return pp;
		}
	} else {
		pp = findpath(s+1, e);
		if (pp != "X") {
			pp = "S" + pp;
			mm[se] = pp;
			return pp;
		}
		pp = findpath(s, e+1);
		if (pp != "X") {
			pp = "E" + pp;
			mm[se] = pp;
			return pp;
		}
		mm[se] = "X";
		return "X";
	}
}

string solve() {
	SS[0] = 0;
	EE[0] = 0;
	for (int i=0; i<P.size(); i++) {
		if (P[i] == 'S') {
			SS[i+1] = SS[i] + 1;
			EE[i+1] = EE[i];
		} else {
			SS[i+1] = SS[i];
			EE[i+1] = EE[i] + 1;
		}
	}
	mm.clear();
	return findpath(0,0);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		cin >> P;
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}