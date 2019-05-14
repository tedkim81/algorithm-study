/**
 * 2019.5.4
 *
 * Becca와 Terry가 격자형 게임판에서 게임을 한다.
 * 게임판의 각 셀은 처음에 비어 있거나 방사능 오염지역으로 설정된다.
 * 두 플레이어는 각자의 턴에, 빈 셀에다 H 또는 V를 올려놓는데, H를 놓으면 횡으로 H를 채우고, V를 놓으면 종으로 V를 채운다.
 * 하지만 횡 또는 종으로 채울때 그 영역에 방사능 오염지역이 걸리면 안된다.
 * 그리고 횡 또는 종으로 채울때 H 또는 V가 있으면 거기를 못넘어간다.
 * 각자의 턴에서 H 또는 V를 놓는데, 놓을데가 없으면 지는거다.
 * Becca가 먼저 시작하는데, Becca가 이길 수 있는 경우에 대하여 "첫 수"는 몇가지가 될 수 있나?
 *
 * (input)
 * T: 테스트케이스의 수
 * R C: 격자의 행(R)과 열(C)
 * R행 C열의 셀 정보. (.)은 빈 셀, (#)은 방사능 오염지역.
 *
 * (output)
 * Becca가 이길 경우에 대한 "첫 수"의 수.
 *
 * (solution 1)
 * 일단 Test set 1만 생각하고 문제를 풀어보자.
 * 
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int R, C;
int grid[15][15];
bool radio_horizontal[15];
bool radio_vertical[15];

int solve();

bool check_win(int rr, int cc, bool is_horizontal) {
	if (grid[rr][cc] != 0) {
		return false;
	}
	bool result = true;
	if (is_horizontal) {
		if (radio_horizontal[rr]) {
			return false;
		}
		for (int i=cc; i>=0; i--) {
			grid[rr][i] += 1;
			if (grid[rr][i] > 1) {
				break;
			}
		}
		for (int i=cc+1; i<C; i++) {
			grid[rr][i] += 1;
			if (grid[rr][i] > 1) {
				break;
			}
		}
		if (solve() > 0) {
			result = false;
		}
		for (int i=cc; i>=0; i--) {
			grid[rr][i] -= 1;
			if (grid[rr][i] > 0) {
				break;
			}
		}
		for (int i=cc+1; i<C; i++) {
			grid[rr][i] -= 1;
			if (grid[rr][i] > 0) {
				break;
			}
		}
	} else {
		if (radio_vertical[cc]) {
			return false;
		}
		for (int i=rr; i>=0; i--) {
			grid[i][cc] += 1;
			if (grid[i][cc] > 1) {
				break;
			}
		}
		for (int i=rr+1; i<R; i++) {
			grid[i][cc] += 1;
			if (grid[i][cc] > 1) {
				break;
			}
		}
		if (solve() > 0) {
			result = false;
		}
		for (int i=rr; i>=0; i--) {
			grid[i][cc] -= 1;
			if (grid[i][cc] > 0) {
				break;
			}
		}
		for (int i=rr+1; i<R; i++) {
			grid[i][cc] -= 1;
			if (grid[i][cc] > 0) {
				break;
			}
		}
	}
	return result;
}

int solve() {
	int result = 0;
	for (int i=0; i<R; i++) {
		for (int j=0; j<C; j++) {
			if (check_win(i, j, true)) {
				result++;
			}
			if (check_win(i, j, false)) {
				result++;
			}
		}
	}
	return result;
}

int main() {
	int T;
	cin >> T;
	char ch;
	for (int ii=0; ii<T; ii++) {
		cin >> R >> C;
		for (int i=0; i<R; i++) {
			for (int j=0; j<C; j++) {
				cin >> ch;
				if (ch == '#') {
					grid[i][j] = -1;
				} else {
					grid[i][j] = 0;
				}
			}
		}
		for (int i=0; i<R; i++) {
			bool is_radio = false;
			for (int j=0; j<C; j++) {
				if (grid[i][j] == -1) {
					is_radio = true;
					break;
				}
			}
			radio_horizontal[i] = is_radio;
		}
		for (int i=0; i<C; i++) {
			bool is_radio = false;
			for (int j=0; j<R; j++) {
				if (grid[j][i] == -1) {
					is_radio = true;
					break;
				}
			}
			radio_vertical[i] = is_radio;
		}
		int result = solve();
		cout << "Case #" << ii+1 << ": " << result << endl;
	}
}