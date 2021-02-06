/**
 * 2021.1.28
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007707/000000000004ba29
 *
 * 어떤 연구협회에서 나에게 네트워크 디자인을 요청했다.
 * 아래 4개의 조건을 만족하는 네트워크를 디자인해야 한다.
 * 1. L과 U를 주면 L<=N<=U를 만족하는 N을 선택하여 N개의 컴퓨터로 구성한다.
 * 2. 각 컴퓨터는 반드시 다른 4개의 컴퓨터와 연결되어야 한다.
 * 3. 모든 컴퓨터는 서로 직간접적으로 연결되어 있어야 한다.
 * 4. 시스템이 reboot되면 컴퓨터들의 ID가 랜덤하게 바뀌는데, 어떻게 바뀌었는지 알 수 있어야 한다.
 * 연구협회에서는 내가 만든 네트워크 디자인을 검증하기 위한 프로세스를 만들었고,
 * 아래의 interactive input and output 의 방식이다.
 *
 * (input)
 * T: 테스트케이스의 수
 *
 * (interactive input and output)
 * (input) L U : L은 lower limit, U는 upper limit
 * (output) N : L <= N <= U 만족하는 N 선택
 * (output) 2N행의 C1 C2 : 2N개의 link
 * (input) N : output으로 보냈던 그 N
 * (input) 2N행의 C3 C4 : 랜덤하게 컴퓨터 번호가 바뀐 상황에서, 2N개의 link
 * (output) N개의 컴퓨터 번호. D1 D2 D3 라면, 1->D1, 2->D2, 3->D3 의미
 *
 * (solution 1)
 * [문제 해결 방식 설명. 실패하면 solution 2를 생각하여 다시 시도]
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

string solve() {
	return "";
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}