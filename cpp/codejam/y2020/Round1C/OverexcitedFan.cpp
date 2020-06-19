/**
 * 2020.5.2
 *
 * 페퍼(누군진 모르겠지만)가 온다고 페퍼의 광팬이 만나서 사진을 찍으려고 한다.
 * 길은 grid 형태로 되어 있고, 광팬과 페퍼의 위치, 
 * 그리고 페퍼의 이동경로가 주어진다고 할때, 광팬이 페퍼를 최대한 빨리 만나려면
 * 얼마나 걸릴지를 구하는 것이 문제.
 * grid의 선을 따라서만 이동가능하고, 한칸 이동하는데 1의 시간이 걸린다.
 * 페퍼의 이동이 끝난 후에는 더이상 이동할 수 없고,
 * 따라서 못만날 수도 있다. 못만나면 IMPOSSIBLE
 *
 * (input)
 * T: 테스트케이스의 수
 * X Y P: X와 Y(광팬 위치 기준 페퍼의 위치.), P(페퍼의 이동경로. N,S,E,W로 구성된 문자열)
 *
 * (output)
 * 광팬이 페퍼를 만날 수 있는 최단시간 또는 IMPOSSIBLE
 *
 * (solution 1)
 * 일단 Test set 1부터 빨리 풀자.
 * 페퍼는 N 또는 S로만 이동하니까 일단 페퍼의 X축 위치까지 이동한후 페퍼가 있는 방향으로 이동하면 된다.
 *
 * (solution 1 result)
 * Test set 1과 2는 성공, 3은 실패. 일단 넘어가고 추후 다시 풀자.
 * 
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int X, Y;
string P;

int solve() {
	int result = 0;
	int x1 = 0;
	int y1 = 0;
	int x2 = X;
	int y2 = Y;
	for (int i=0; i<P.size(); i++) {
		char c = P[i];
		switch(c) {
			case 'N':
				y2++;
				break;
			case 'S':
				y2--;
				break;
			case 'E':
				x2++;
				break;
			case 'W':
				x2--;
				break;
		}
		if (x1 > x2) {
			x1--;
			result++;
		} else if (x1 < x2) {
			x1++;
			result++;
		} else {
			if (y1 > y2) {
				y1--;
				result++;
			} else if (y1 < y2) {
				y1++;
				result++;
			} else {
				result++;
				break;
			}
		}
		if (x1 == x2 && y1 == y2) {
			break;
		}
	}
	if (x1 != x2 || y1 != y2) {
		result = -1;
	}
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> X >> Y >> P;
		int result = solve();
		if (result == -1) {
			cout << "Case #" << i+1 << ": IMPOSSIBLE" << endl;
		} else {
			cout << "Case #" << i+1 << ": " << result << endl;
		}
	}
}