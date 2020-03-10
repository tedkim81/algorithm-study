/**
 * 2020.1.19
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007706/00000000000459f2
 *
 * 격자(grid) 구조 판의 윗부분에서 구슬을 떨어뜨려 아랫부분까지 보내는 장난감이 있다.
 * 페이지 중간에 보여주는 그림이 직관적으로 잘 표현되어 있다.
 * 게임판 맨 위에는 구슬을 떨어뜨릴 수 있는 C개의 칸(columns)이 있고, 
 * 내부의 각각의 칸은 / 모양 또는 \ 모양의 방향전환용 경사로가 있거나, 또는 없다.
 * / 는 해당 칸에 구슬이 왔을때 왼쪽아래칸으로 보내는 것이고,
 * \ 는 오른쪽아래칸으로 보내고, 없으면 그냥 아래칸으로 보낸다.
 * \/ 이렇게 되면 구조적으로 구슬을 아래로 내려보낼 수 없으므로 안되고,
 * 좌측, 우측, 바닥 경계쪽은 / 또는 \ 가 있으면 안된다.
 * 내부 구조는 보이지 않게 가려져 있어서 위아래로 몇 칸인지(rows) 알 수 없다.
 * 이제 게임 시작. 
 * C개의 칸에 각각 1개씩 전체 C개의 구슬을 떨어뜨리고 나서,
 * 바닥에 각각 몇개의 구슬들이 모였는지를 확인했다고 할때,
 * 게임판의 내부는 위아래로 몇 칸인지와 그 구조는 어떠할지를 추측해 보는 것이 문제.
 *
 * (input)
 * T: 테스트케이스의 수
 * C: 게임판 좌우로 몇칸인지(column 수)
 * B[0],...,B[C-1]: 게임판 바닥 각각의 칸에 모인 구슬의 수
 *
 * (output)
 * 구조를 추측한 후, 위아래로 몇칸인지(row 수)와 그 구조를 출력.
 * 각 칸(cell)은 / 또는 \ 또는 . 로 표현.
 *
 * (solution 1)
 * 몇가지 전제를 나열해 보자.
 * - \/ 이러한 구조가 허용되지 않으니 구슬이 내려오면서 서로 교차할 수 없다.
 * - 좍측끝과 우측끝의 구슬은 항상 바로 아래쪽 바닥으로 내려온다.
 * - 아래칸 입장에서 구슬은 바로 위 또는 그 좌우, 이렇게 3칸 중 하나에서 온다.
 * 위의 전제들과 문제의 그림을 뚫어지게 보다보니 한가지 직관을 얻었다.
 * - 내부 구조를 몰라도 바닥에서의 결과만으로 구슬들을 짝 지을 수 있다.
 * 구슬들을 왼쪽부터, 바닥에서의 수만큼을 채우면서 짝을 지어주면 되는 것이다.
 * 이렇게 맨 위에서 이미 구슬들이 각각 자기들이 어디로 갈지 아는 상황이면,
 * 내려가면서 그쪽으로 보내주는 방향으로 / \ . 을 만들어 주면 된다.
 * 슈도코드부터 작성해 보자.
 * --------------------------------------------
 * int C // 게임판 컬럼 수
 * int B[C] // 바닥에서의 구슬 수
 * int A[C] // 맨 위에서 출발전 각 구슬들에 대한 바닥에서의 목적지 위치
 * for 0 <= i < C:
 *   for 0 <= j < C:
 *     if B[j] > 0:
 *       A[i] = j
 *       B[j] -= 1
 *       break
 * int row = 0 // 현재 행
 * int aa[C] // 현재 행이 row일때 구슬들의 위치
 * int bb[100][C] // / 또는 \ 또는 . 로 구성된 내부 구조
 * for 0 <= row < 100:
 *   if aa[0] != A[0] or aa[C-1] != A[C-1]:
 *     return "IMPOSSIBLE"
 *   for 0 <= i < C:
 *     bb[row][i] = "."
 *   bool no_change = true
 *   for 0 <= i < C:
 *     if aa[i] > A[i]:
 *       bb[row][aa[i]] = "/"
 *       aa[i] -= 1
 *       no_change = false
 *     elif aa[i] < A[i]:
 *       bb[row][aa[i]] = "\"
 *       aa[i] += 1
 *       no_change = false
 *   if no_change:
 *     break
 * return row and bb
 * --------------------------------------------
 *
 * (solution 1 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int C;
int B[100];
char bb[100][100];

int solve() {
	int A[C];
	for (int i=0; i<C; i++) {
		for (int j=0; j<C; j++) {
			if (B[j] > 0) {
				A[i] = j;
				B[j] -= 1;
				break;
			}
		}
	}
	int aa[C];
	for (int i=0; i<C; i++) {
		aa[i] = i;
	}
	int row = 0;
	for (; row<100; row++) {
		if (aa[0] != A[0] || aa[C-1] != A[C-1]) {
			return -1;
		}
		bool no_change = true;
		for (int i=0; i<C; i++) {
			bb[row][i] = '.';
		}
		for (int i=0; i<C; i++) {
			if (aa[i] > A[i]) {
				bb[row][aa[i]] = '/';
				aa[i] -= 1;
				no_change = false;
			} else if (aa[i] < A[i]) {
				bb[row][aa[i]] = '\\';
				aa[i] += 1;
				no_change = false;
			}
		}
		if (no_change) {
			break;
		}
	}
	return row;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> C;
		for (int j=0; j<C; j++) {
			cin >> B[j];
		}
		int result = solve();
		if (result < 0) {
			cout << "Case #" << i+1 << ": IMPOSSIBLE" << endl;
		} else {
			cout << "Case #" << i+1 << ": " << (result+1) << endl;
			for (int j=0; j<=result; j++) {
				for (int k=0; k<C; k++) {
					cout << bb[j][k];
				}
				cout << endl;
			}
		}
	}
}