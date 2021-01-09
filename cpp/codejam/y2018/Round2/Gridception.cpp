/**
 * 2020.4.2
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007706/00000000000459f4
 *
 * Gridception = grid + ception(인셉션의 그 "셉션")
 * 다른 사람의 꿈을 훔쳐볼 수 있는 도둑이 있다..;;
 * 꿈의 형상은, 격자형(바둑판 모양)이고 흰색(W) 또는 검은색(B)으로 이뤄져 있다.
 * 그리고 첫 형상에서, 꿈이 한단계 진행되면 형상이 가로 세로가 각각 2배씩 커지는데,
 * W => WW
 *      WW
 * B => BB
 *      BB
 * 로 변한다.
 * 첫 형상에서 어떤 패턴을 정하고, 첫 형상을 10의 100제곱만큼(그냥 무지하게 많이) 
 * 변화시킨 후, 해당 패턴이 변화된 꿈 안에 존재하도록 하는 패턴의 최대크기를 
 * 구하는 것이 문제다.
 * 패턴은 아무거나 다 되는건 아니고, 가장자리가 서로 붙어있는 관계만 인정된다고 한다.
 * W
 *  W
 * 이런건 안된다.
 *
 * (input)
 * T: 테스트케이스의 수
 * R C: 행(R)과 열(C)의 수
 * R행 C열이고 W 또는 B로 이루어진 격자형 문자열
 *
 * (output)
 * 주어진 첫 형상에서 문제의 조건을 만족하는 최대 패턴의 크기
 *
 * (solution 1)
 * 몇가지 정리된 생각을 나열해보자.
 * - 많은 변형을 거치고 나면, W가 B를(또는 B가 W를) 감싸는 경우는 존재할 수 없다.
 * - W와 B가 상하 또는 좌우로 갈리거나, ㄱ자 모양으로 구분될 수 있다.
 * - 한가지 색만 있는 경우는 무조건 가능하다.
 * 위 내용들을 가지고 전략을 짜보자.
 * - W만 남겨놓고 B를 탐색하면서 가능한지 확인. 그리고 반대로도 확인.
 * - "가능한지"는 패턴의 형태가 |자 또는 ㄱ자 모양으로 구분이 되는지 
 *   또는 한가지 색으로만 구성되었는지로 판단한다.
 * 슈도코드(?)를 작성해 보자.
 * --------------------------------------------
 * char BW[20][20]  // 꿈의 첫 형상. B or W
 * bool ptn[20][20]  // 패턴
 * ptn을 false로 초기화
 * B인 애들 전부에 대하여 ptn에 true
 * ptn에 false인 애들 탐색하면서 |자 또는 ㄱ자 모양이 되면 true
 * result = ptn에서 true의 수
 * ptn을 false로 초기화
 * W인 애들 전부에 대하여 ptn에 true
 * ptn에 false인 애들 탐색하면서 |자 또는 ㄱ자 모양이 되면 true
 * result = max(result, ptn에서 true의 수)
 * return result
 * --------------------------------------------
 *
 * (solution 1 result)
 * 코드 작성 중에 문제를 발견했다.
 * 초반에 정리했던 내용 중 "한가지 색만 있는 경우는 무조건 가능하다."
 * 이걸 보고 B 또는 W 한쪽이 다 있고 나머지가 일부 채우는 것만 생각했는데,
 * 다시 생각해보니, ㄱ자 모양 확인할때 걸리는 애들은 빼버릴 수 있다.
 * B W W B B B
 * W W W B B B
 * B B B B B B
 * B B B B B B
 * 이때 (1,2)의 W가 ㄱ자 모양이 되는지를 보면,
 * (0,0)의 B 때문에 안될 수도 있지만, B를 빼버리면 될 수도 있는 것이다.
 * 자.. 다시 생각해보자.(어쩐지 너무 쉽게 풀린다 했다..)
 *
 * (solution 2)
 * |자 구분 or ㄱ자 구분 or 한가지 색
 * 위의 조건을 만족하는 패턴을 찾는 문제는, 
 * "사분면으로 구분된" 패턴을 찾는 문제로 바꿀 수 있다.
 * 주어진 grid를 사분면으로 구분하고, 각 분면에는 같은 색만 있을 수 있도록 할때,
 * 가능한 패턴의 최대 크기를 구하는 문제가 된다.
 * - 가능한 사분면 구분을 각각 탐색하면서,
 * - 각 분면의 유일색이 B or W가 되는 모든 경우의 수(16가지)를 탐색하면서,
 *   해당하는 색만 남긴 후, 가장자리가 서로 붙은 경우만 남기는 작업을 한다.
 * - 가장자리가 서로 붙은(연결된) 경우만 남기는 작업은,
 *   grid를 탐색할때 가장자리 4개에 인접한 cell을 재귀적으로 탐색.
 *   뭉쳐있는 group이 둘 이상이 될 수도 있으므로, 그들 중 최대크기를 찾아야 한다.
 * 슈도코드를 작성해 보자.
 * --------------------------------------------
 * char BW[20][20]  // 꿈의 첫 형상. B or W
 * bool ptn[20][20]  // 패턴
 * bool visited[20][20]  // 연결된 패턴 찾을때 방문한 cell인지 여부
 * char quad[16][4]  // 각 분면의 유일색이 B or W가 되는 모든 경우
 * quad에 값 넣기. 16개니까 그냥 직접 넣어도 될듯.
 * int max_size = 0
 * for 0 <= r <= R:
 *   for 0 <= c <= C:
 *     for q in quad:
 *       (r,c)를 사분면 중심으로 하고 q의 색만 남도록 ptn 구성
 *       for 0 <= rr < R:
 *         for 0 <= cc < C:
 *           ptn_size = get_connected_size(rr, cc)
 *           max_size = max(max_size, ptn_size)
 * return max_size
 *
 * get_connected_size(rr, cc):
 *   if not ptn[rr2][cc2] or visited[rr][cc]:
 *     return 0
 *   result = 1
 *   int d[4][2] = [[-1,0],[1,0],[0,-1],[0,1]]
 *   for dd in d:
 *     rr2 = rr + dd[0]
 *     cc2 = cc + dd[1]
 *     if 0 <= rr2 < R and 0 <= cc2 < C
 *         and ptn[rr2][cc2] and not visited[rr2][cc2]:
 *       result += get_connected_size(rr2, cc2)
 *   return result
 * --------------------------------------------
 *
 * (solution 2 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int R, C;
char BW[20][20];
bool ptn[20][20];
bool visited[20][20];

void make_ptn(int r, int c, char* q) {
	for (int rr=0; rr<R; rr++) {
		for (int cc=0; cc<C; cc++) {
			visited[rr][cc] = false;
			if (rr < r) {
				if (cc < c) {
					ptn[rr][cc] = BW[rr][cc] == q[0];
				} else {
					ptn[rr][cc] = BW[rr][cc] == q[1];
				}
			} else {
				if (cc < c) {
					ptn[rr][cc] = BW[rr][cc] == q[2];
				} else {
					ptn[rr][cc] = BW[rr][cc] == q[3];
				}
			}
		}
	}
}

int get_connected_size(int r, int c) {
	if (!ptn[r][c] || visited[r][c]) {
		return 0;
	}
	visited[r][c] = true;
	int result = 1;
	int d[4][2] = {{-1,0},{1,0},{0,-1},{0,1}};
	for (int i=0; i<4; i++) {
		int rr = r + d[i][0];
		int cc = c + d[i][1];
		if (rr >= 0 && rr < R && cc >= 0 && cc < C && ptn[rr][cc] && !visited[rr][cc]) {
			result += get_connected_size(rr, cc);
		}
	}
	return result;
}

int solve() {
	for (int r=0; r<R; r++) {
		for (int c=0; c<C; c++) {
			ptn[r][c] = false;
			visited[r][c] = false;
		}
	}
	char quad[16][4] = {
		{'B','B','B','B'}, {'B','B','B','W'}, {'B','B','W','B'}, {'B','B','W','W'}, 
		{'B','W','B','B'}, {'B','W','B','W'}, {'B','W','W','B'}, {'B','W','W','W'}, 
		{'W','B','B','B'}, {'W','B','B','W'}, {'W','B','W','B'}, {'W','B','W','W'}, 
		{'W','W','B','B'}, {'W','W','B','W'}, {'W','W','W','B'}, {'W','W','W','W'}
	};
	bool quad_able[16] = {false};
	for (int r=0; r<R; r++) {
		for (int c=0; c<C; c++) {
			if (BW[r][c] == 'B') quad_able[0] = true;
			if (BW[r][c] == 'W') quad_able[15] = true;
			if (r+1 < R) {
				if (BW[r][c] == 'B' && BW[r+1][c] == 'W') quad_able[3] = true;
				if (BW[r][c] == 'W' && BW[r+1][c] == 'B') quad_able[12] = true;
			}
			if (c+1 < C) {
				if (BW[r][c] == 'B' && BW[r][c+1] == 'W') quad_able[5] = true;
				if (BW[r][c] == 'W' && BW[r][c+1] == 'B') quad_able[10] = true;
			}
			if (r+1 < R && c+1 < C) {
				for (int i=0; i<16; i++) {
					char* q = quad[i];
					if (BW[r][c] == q[0] && BW[r][c+1] == q[1] && BW[r+1][c] == q[2] && BW[r+1][c+1] == q[3]) 
						quad_able[i] = true;
				}
			}
		}
	}
	int max_size = 0;
	for (int r=0; r<=R; r++) {
		for (int c=0; c<=C; c++) {
			for (int i=0; i<16; i++) {
				if (!quad_able[i]) continue;
				make_ptn(r, c, quad[i]);
				for (int rr=0; rr<R; rr++) {
					for (int cc=0; cc<C; cc++) {
						max_size = max(max_size, get_connected_size(rr, cc));
					}
				}
			}
		}
	}
	return max_size;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> R >> C;
		for (int r=0; r<R; r++) {
			for (int c=0; c<C; c++) {
				cin >> BW[r][c];
			}
		}
		int result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}