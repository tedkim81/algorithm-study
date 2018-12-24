/**
 * 2018.12.18
 *
 * 화장실에 N+2개의 칸이 있는데 양쪽끝은 항상 점유되어 있고 N개의 칸이 비어 있는 상황.
 * K명의 사람이 화장실의 각 칸에 순서대로 들어가는데, 좌우로 최대한 여유있게 들어가고 싶어한다.
 * S번째 칸에 대해서 Ls는 좌측으로 빈칸수, Rs는 우측으로 빈칸수라 할때, 
 * min(Ls,Rs)가 최대인 칸을 선택하는데, 같은 값인 칸이 있으면 max(Ls,Rs)가 큰 값으로 정하고, 
 * 그것도 같으면 가장 왼쪽에 있는 칸을 선택한다.
 * 위의 규칙대로 할때, 마지막 K번째 사람의 max(Ls,Rs)와 min(Ls,Rs)을 구하기.
 *
 * (input)
 * T: 테스트케이스의 수
 * N K: N은 화장실 칸 수, K는 사람 수
 *
 * (output)
 * 마지막 K번째 사람의 max(Ls,Rs)와 min(Ls,Rs)
 *
 * (solution 1)
 * 어떤 구간의 min(Ls,Rs)을 최대화하는 S는 중간지점이다. 
 * 따라서, 구간의 칸의 수가 홀수일때는 정중앙, 짝수일 때는 중앙의 좌측 칸을 선택하면 된다.
 * 처음에는 하나의 구간(0 ~ N-1)으로 시작하지만, 중앙의 칸이 점유되면서 구간도 여러개로 늘어난다.
 * 임의의 구간을 반으로 갈랐을때, 좌측과 우측 구간의 칸의 수가 같은 경우는 그 다음 순서가 좌측이 되고,
 * 좌측과 우측 구간의 칸의 수가 다른 경우는(우측이 한칸 많은 경우), 그 다음 순서가 우측이 된다.
 * 구간들을 너비 우선 탐색 해야한다.
 * Queue에다 시작위치와 끝위치의 페어를 쌓고 K번 반복문 돌면서 큐에 쌓기와 꺼내기를 실행한 후,
 * 마지막의 max와 min을 구한다.
 *
 * (solution 1 result)
 * 아직 submit 하지 않았다. Test set 1과 2는 해결될 듯 한데, 3은 해결이 안될 듯 하다.
 * K가 10의 18제곱인 경우 K번 반복은 할 수가 없으므로, 지수적으로 감소하도록 해야 한다.
 * solution 2는 BathroomStalls2.cpp 에 새로 작성한다.
 */

#include <stdio.h>
#include <iostream>
#include <string>
#include <queue>

using namespace std;

class section {
public:
	section(int s, int e): s(s), e(e) {}
	int s;
	int e;
	int m;
	void divide() {
		m = (e+s) / 2;
	}
	int get_max() {
		return max(m-s, e-m);
	}
	int get_min() {
		return min(m-s, e-m);
	}
	section get_left_section() {
		return section(s, m-1);
	}
	section get_right_section() {
		return section(m+1, e);
	}
	int size() {
		return e - s + 1;
	}
};

string solve(int N, int K) {
	int max, min;
	queue<section> q;
	q.push(section(0, N-1));
	for (int i=0; i<K; i++) {
		section s = q.front();
		q.pop();
		s.divide();
		max = s.get_max();
		min = s.get_min();
		section left_s = s.get_left_section();
		section right_s = s.get_right_section();
		if (left_s.size() == right_s.size()) {
			q.push(left_s);
			q.push(right_s);
		} else {
			q.push(right_s);
			q.push(left_s);
		}
	}
	string result = to_string(max) + " " + to_string(min);
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int N, K;
		cin >> N >> K;
		string result = solve(N, K);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}