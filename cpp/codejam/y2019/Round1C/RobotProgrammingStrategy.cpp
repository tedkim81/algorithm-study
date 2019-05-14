/**
 * 2019.5.4
 *
 * 로봇 가위바위보 토너먼트에 출전했다.
 * 다른 경쟁자들의 RSP 패턴을 다 알고는 있지만 아직 대전이 정해지지 않았기 때문에 누구부터 붙을지 순서는 모른다.
 * 패턴의 길이는 상관없고 패턴의 끝 다음은 패턴의 처음으로 다시 반복하는 방식이다.
 * 토너먼트에서 1등하기 위해 내 로봇의 패턴은 어떻게 정할 수 있을까?
 *
 * (input)
 * T: 테스트케이스의 수
 * A: 경쟁자들의 수 (2의 K제곱 - 1 명)
 * C (A행): 경쟁자들의 RSP 패턴
 *
 * (output)
 * 토너먼트에서 1등할 수 있는 내 로봇의 RSP 패턴. 불가능하면 IMPOSSIBLE
 *
 * (solution 1)
 * Test set 1만 생각하고 문제를 풀어보자. 경쟁자수는 최대 7명, RSP 패턴의 길이는 최대 5.
 * 토너먼트라서 실제로는 모든 경쟁자와 붙는 것은 아니지만, 대전이 정해지지 않았기 때문에 누구랑 붙을지 모르므로 
 * 다 이기는 패턴을 만들어야 한다.
 * 전제조건을 몇가지 깔아보자.
 * 1. 상대의 패턴의 각 선택을 순서대로 비교했을때 각각이 "지지 않아야 한다." 즉, "이기거나 비겨야 한다."
 * 2. 경쟁자들의 첫 선택들에 R, S, P가 모두 있으면 IMPOSSIBLE
 * 3. 경쟁자들의 첫 선택이 모두 같으면 그걸 이기느 선택을 하면 된다.
 * 4. 경쟁자들의 첫 선택이 두가지 이면, (R, S)=>R, (R, P)=>P, (S, P)=>S
 * 5. 위의 2,3,4 를 비긴 경쟁자들에 대해서 반복 적용하여 다 이길때까지 반복한다.
 * 위의 전제조건을 알고리즘으로 그대로 옮기면 될듯. 
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <set>

using namespace std;

int A;
string C[255];

string solve() {
	string result = "";
	set<char> s;
	bool did_win[255] = { false };
	for (int i=0; i<501; i++) {
		s.clear();
		for (int j=0; j<A; j++) {
			if (!did_win[j]) {
				s.insert(C[j][i % C[j].size()]);
			}
		}
		if (s.size() == 1) {
			char choice = *(s.begin());
			if (choice == 'R') {
				result += "P";
			} else if (choice == 'S') {
				result += "R";
			} else {
				result += "S";
			}
			break;
		} else if (s.size() == 2) {
			char choice1 = *(s.begin());
			char choice2 = *(++s.begin());
			char lose_choice;
			if (choice1 == 'P' && choice2 == 'R') {
				result += "P";
				lose_choice = 'R';
			} else if (choice1 == 'P' && choice2 == 'S') {
				result += "S";
				lose_choice = 'P';
			} else {  // R, S
				result += "R";
				lose_choice = 'S';
			}
			for (int j=0; j<A; j++) {
				if (!did_win[j] && C[j][i % C[j].size()] == lose_choice) {
					did_win[j] = true;
				}
			}
		} else {
			result = "IMPOSSIBLE";
			break;
		}
	}
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> A;
		for (int j=0; j<A; j++) {
			cin >> C[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}