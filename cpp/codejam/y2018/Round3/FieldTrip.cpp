/**
 * 2020.4.26
 *
 * 1명의 교사와 N-1명의 아이들이 grid 형태의 충분히 넓은 잔디밭으로 견학을 갔다. (총 N명)
 * 교사 및 아이들은 각각 grid의 어떤 cell에 있고, 한 cell에 2명 이상이 있을 수 있다.
 * 집에 갈 시간이 됐고, 버스 타고 집에 가려면 교사 및 아이들이 한 cell에 모여야 한다.
 * 한번의 turn에 모든 사람이 한칸씩 이동할 수 있는데, 현재 자신의 cell에 인접한 
 * 8개의 cell 중 하나로 이동하거나, 아니면 그냥 안 움직여도 된다.
 * 한번의 turn에서, 사람들은 한명씩 움직이는데 i번째 사람은 i-1번째 사람과 
 * 가까워지는 방향으로 이동해야 한다.
 * 하나의 cell에 모일때까지 최소 몇번의 turn이 필요할까?
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 교사 및 아이들의 수
 * (N행의) R C: N명 각각의 위치. R(row), C(column)
 *
 * (output)
 * 최소 turn의 수
 *
 * (solution 1)
 * 문제에 대해 몇가지 정리해보자.
 * - row와 col은 서로 의존성이 없다. 따로 계산하고 max 값을 선택하면 된다.
 * - 좌우 끝에 있는 사람들의 위치를 이용해 목적지를 구할 수 있다.
 * - i-1번째와 i번째가 최소거리를 유지해야한다는 조건은, 같은 목적지로 이동한다면
 *   무시해도 되는 조건이다.
 * 위의 정리를 이용해 Test set 1을 풀어보자.
 * - min R과 max R의 중간지점을 구하고, 현재위치에서 중간지점까지의 거리를 구한다.
 * - min C와 max C의 중간지점을 구하고, 현재위치에서 중간지점까지의 거리를 구한다.
 * - 위 두값의 max가 답
 * 슈도코드를 작성해보자.
 * -------------------------------------------------
 * int N
 * long R[9], C[9]
 * min_r = min(R)
 * max_r = max(R)
 * avg_r = (min_r + max_r) / 2
 * dist_r = max((avg_r - min_r), (max_r - avg_r))
 * min_c = min(C)
 * max_c = max(C)
 * avg_c = (min_c + max_c) / 2
 * dist_c = max((avg_c - min_c), (max_c - avg_c))
 * return max(dist_r, dist_c)
 * -------------------------------------------------
 *
 * (solution 1 result)
 * Test set 1과 2 모두 성공..
 * 뭐지? 이건 뭔가.. 음.. 다른 문제들에 비해 너무 쉽게 통과한 느낌이다..;;
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int N;
long R[10001], C[10001];

long solve() {
	long min_r = *min_element(R, R+N);
	long max_r = *max_element(R, R+N);
	long avg_r = (min_r + max_r) / 2;
	long dist_r = max((avg_r - min_r), (max_r - avg_r));
	long min_c = *min_element(C, C+N);
	long max_c = *max_element(C, C+N);
	long avg_c = (min_c + max_c) / 2;
	long dist_c = max((avg_c - min_c), (max_c - avg_c));
	return max(dist_r, dist_c);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int j=0; j<N; j++) {
			cin >> R[j] >> C[j];
		}
		long result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}