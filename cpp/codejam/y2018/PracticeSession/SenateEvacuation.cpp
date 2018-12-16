/**
 * 2018.12.12
 * 
 * N개의 정당이 있고, 각 정당의 이름은 순서대로 A,B,C,.. 
 * 방안에 A정당 P1명, B정당 P2명, C정당 P3명,.. 이렇게 있다.
 * 불이 나서 대피해야하는데, 한번에 1명 또는 2명이 빠져 나갈 수 있고, 
 * 그 와중에도 날치기 의결을 할 수 있으므로, 어떤 순간에도 방안에는 어느 한 정당이 과반수가 되지 않아야 한다.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 정당의 수
 * P1 P2 P3 .. Pn : 각 정당의 의원 수
 *
 * (output)
 * 조건을 어기지 않는 선에서 의원들이 빠져 나가는 방법
 * 예) A 2명, B 2명 있는 경우, AB BA 순서로 빠져나가면 A 또는 B가 어떤 순간에도 과반수가 되지 않는다.
 *
 * (solution 1)
 * 직관적으로, 가장 의원수가 많은 정당에서 먼저 나가주면 된다.
 * 가장 많은 정당에서 2명 나가거나, 가장 많은 정당에서 1명과 그 다음 정당에서 1명이 나가거나, 가장 많은 정당에서 1명만 나가는 경우 중 하나 선택.
 * 나가는 횟수가 최소가 되게 하라는 조건은 없으므로, 그냥 가장 많은 정당에서 1명씩 나가게 하면 될 듯.
 * 단, 마지막에 한명이 남는 상황이 나오면 안되므로 2명 남았을때는 2명 다 나가게 하자.
 *
 * (solution 1 result)
 * 실패! 1명만 나가면 안되는 케이스가 존재한다. A 2, B 2 있을때, A 1명만 빠지만 B가 과반수 초과가 된다.
 *
 * (solution 2)
 * 최다수 정당에서 2명 나가거나, or 최다수 정당에서 1명과 그다음 정당에서 1명 나가거나, or 최다수 정당에서 1명 나가거나,
 * 세가지 케이스 중에 나간 후 과반수 초과 존재 여부를 체크하여 가능한 경우 다음 상태를 재귀적으로 진행하도록 작성하자.
 *
 * (solution 2 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <string>

using namespace std;

string solve(int N, int P[], int remains) {
	if (remains <= 2) {
		string result = "";
		for (int i=0; i<N; i++) {
			if (P[i] > 0) {
				result += 'A' + i;
			}
		}
		return result;
	}
	int first = 0;
	int second = 0;
	int max = -1;
	for (int i=0; i<N; i++) {
		if (P[i] >= max) {
			max = P[i];
			second = first;
			first = i;
		}
	}
	string thisturn = "";
	P[first] -= 1;
	remains -= 1;
	thisturn += 'A' + first;
	if (P[first]*2 > remains) {
		P[first] -= 1;
		remains -= 1;
		thisturn += 'A' + first;
	} else if (P[second]*2 > remains) {
		P[second] -= 1;
		remains -= 1;
		thisturn += 'A' + second;
	}
	return thisturn + " " + solve(N, P, remains);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int N;
		cin >> N;
		int P[N];
		int remains = 0;
		for (int j=0; j<N; j++) {
			cin >> P[j];
			remains += P[j];
		}
		string result = solve(N, P, remains);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}