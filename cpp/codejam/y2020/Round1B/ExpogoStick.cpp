/**
 * 2020.4.20
 *
 * 콩콩이(점프스틱)를 타고 (0,0)에서 (X,Y)로 최소한의 점프로 이동해야 한다.
 * 첫번째 점프에 1칸, 두번째 점프에 2칸, 세번째 점프에 4칸,.. 이동한다.(2의 제곱)
 * 각각의 점프를 어느방향으로 할지 N(북쪽),S(남쪽),E(동쪽),W(서쪽)로 구성된
 * 점프계획 문자열을 구하는 것이 문제이다.
 * 불가능하다면 IMPOSSIBLE
 *
 * (input)
 * T: 테스트케이스의 수
 * X Y: 목적지의 위치 좌표
 *
 * (output)
 * 점프계획 문자열. N,S,E,W로 구성된 문자열.
 *
 * (solution 1)
 * 몇가지 되는대로 정리를 해보자.
 * - 2의 제곱 중에 2의 0제곱만 홀수고 나머지는 다 짝수다.
 *   X,Y 중 하나는 홀수고 하나는 짝수여야 한다. 그렇지 않으면 IMPOSSIBLE
 * - 어떤 지점을 넘어갔다가 다시 돌아올 수는 없다.
 *   탐색할때 목표지점을 넘어가는 경우는 거기서 탐색을 끝낼 수 있다.
 * 위 내용 참고해서, 실행시간 무시하고 전략을 짜보면,
 * - 각 점프에 대해 N,S,E,W 각 방향으로 탐색(첫점프는 홀수인 좌표축에 대해서만)
 * - 목표지점 넘어갔으면 실패로 탐색종료. 목표지점이면 성공으로 탐색종료.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <string>
#include <sstream>
#include <vector>

using namespace std;

int X, Y;
int cx, cy;
vector<char> vv;

bool trace(char direct, int jump) {
	if (jump == 1) {
		cx = 0;
		cy = 0;
		vv.clear();
	}
	if (direct == 'N') cy += jump;
	else if (direct == 'S') cy -= jump;
	else if (direct == 'E') cx += jump;
	else cx -= jump;

	if (cx == X && cy == Y) {
		vv.push_back(direct);
		return true;
	}
	if (abs(cx) > abs(X) || abs(cy) > abs(Y)) {
		return false;
	}
	jump *= 2;
	vector<char> dd;
	if (X != 0 && cx != X) {
		if (X > cx) {
			dd.push_back('E');
			dd.push_back('W');
		} else {
			dd.push_back('W');
			dd.push_back('E');
		}
	}
	if (Y != 0 && cy != Y) {
		if (Y > cy) {
			dd.push_back('N');
			dd.push_back('S');
		} else {
			dd.push_back('S');
			dd.push_back('N');
		}
	}
	int cx2 = cx;
	int cy2 = cy;
	for (int i=0; i<dd.size(); i++) {
		if (trace(dd[i], jump)) {
			vv.push_back(direct);
			return true;
		} else {
			cx = cx2;
			cy = cy2;
		}
	}
	return false;
}

string get_ss() {
	stringstream ss;
	for (int i=vv.size()-1; i>=0; i--) {
		ss << vv[i];
	}
	return ss.str();
}

string solve() {
	int xx = abs(X) % 2;
	int yy = abs(Y) % 2;
	if (xx+yy != 1) {
		return "IMPOSSIBLE";
	}
	vector<char> dd;
	if (xx == 1) {
		if (X > 0) {
			dd.push_back('E');
			dd.push_back('W');
		} else if (X < 0) {
			dd.push_back('W');
			dd.push_back('E');
		}
	} else {
		if (Y > 0) {
			dd.push_back('N');
			dd.push_back('S');
		} else if (Y < 0) {
			dd.push_back('S');
			dd.push_back('N');
		}
	}
	for (int i=0; i<dd.size(); i++) {
		if (trace(dd[i], 1)) {
			return get_ss();
		}
	}
	return "IMPOSSIBLE";
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> X >> Y;
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}