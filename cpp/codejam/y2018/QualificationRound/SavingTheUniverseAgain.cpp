/**
 * 2018.12.24
 *
 * 외계인 로봇이 공격을 계획하고 있는데, SCCSCS.. 처럼 공격 스케줄을 짰다.
 * S는 shooting으로 공격하여 데미지를 입히는 것인데, 데미지는 1로 시작하고, C는 데미지를 2배씩 올린다.
 * 이를 방어해야 하는데, 최대 D의 데미지까지 견딜 수 있다.
 * 공격 스케줄을 해킹해서 데미지를 D 이내로 줄이고자 하는데, 스케줄의 인접한 두 글자를 바꿔치기하는 방식이다.
 * 해킹은 최소 몇번 해야할까?
 *
 * (input)
 * T: 테스트케이스의 수
 * D P: D는 견딜 수 있는 최대 데미지이고, P는 "S"와 "C"로 구성된 공격스케줄이다.
 *
 * (output)
 * 최소 몇번 해킹해야하는가 이고, 불가능하면 IMPOSSIBLE
 *
 * (solution 1)
 * 가능한한 뒤에 있는 C가 최대한 뒤로 더 이동하도록 해야한다.
 * 현재 공격스케줄대로 했을때 데미지값을 구하고, D와의 차이(dd)를 계산한 후,
 * C를 뒤에서부터 하나씩 탐색하면서, 한칸씩 뒤로 보내면서 dd를 0이하가 될때까지 감소시킨다.
 * 그러면 C를 뒤로 보낸 횟수가 결과값이 된다. 더이상 C를 옮길 수 없는데 dd가 0보다 크다면 IMPOSSIBLE.
 *
 * (solution 1 result)
 * 첫번째 submit에서 실패했다. 예제 테스트는 모두 통과했고, 쉬운 문제라 자신이 있었는데..
 * 처음에는 몇가지 랜덤하게 테스트를 해보고 검산을 해보는 식으로 문제를 찾아봤는데, 문제가 발견되지 않았다.
 * 그러다가 소스코드를 보는데, cnt를 구하는 반복문 안에서 
 * if (dd <= 0) break; 이렇게 있어야 할 아이가 if (dd < 0) break; 이렇게 되어 있었던 것;;
 * 이런 버그는 소스코드를 다시 처음부터 읽어보면서 알고리즘을 검토해야 오히려 찾기가 쉽다.
 * 버그 고치고 나니 두번째 submit에서는 당연히 통과.
 */

#include <stdio.h>
#include <iostream>
#include <string>
#include <math.h>

using namespace std;

string solve(int D, string P) {
	int dd = 0;
	int cur = 1;
	int cqty = 0;
	for (string::size_type i = 0; i < P.size(); i++) {
		if (P[i] == 'C') {
			cur *= 2;
			if (i < P.size()-1) cqty++;
		} else {
			dd += cur;
		}
	}
	dd -= D;
	if (dd <= 0) return "0";

	int cnt = 0;
	for (string::size_type ii = P.size()-1; ii > 0; ii--) {
		int i = ii - 1;
		if (P[i] == 'C') {
			cqty--;
			for (string::size_type j = i; j < P.size()-1; j++) {
				if (P[j+1] == 'C') break;
				P[j] = 'S';
				P[j+1] = 'C';
				dd -= (int)pow(2, cqty);
				cnt++;
				if (dd <= 0) break;
			}
		}
		if (dd <= 0) break;
	}
	// cout << P << " , " << dd << " , " << cnt << endl;
	if (dd > 0) return "IMPOSSIBLE";
	else return to_string(cnt);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int D;
		string P;
		cin >> D >> P;
		string result = solve(D, P);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}
