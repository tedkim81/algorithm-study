/**
 * 2020.4.4
 *
 * 숫자로 이루어진 문자열이 있다.
 * 각 숫자는 깊이(depth)를 의미하고, 그 깊이에 맞게 괄호로 묶는 것이 문제.
 * 가장 최적으로(가장 짧게) 묶은 결과가 답이다.
 *
 * (input)
 * T: 테스트케이스의 수
 * S: 숫자로 구성된 문자열. Test set 1은 0과 1, Test set 2는 0부터 9.
 *
 * (output)
 * 각 숫자를 깊이에 맞게 괄호로 묶어준 결과
 * 0 -> 0, 1 -> (1), 2 -> ((2)), 012 -> 0(1(2)), ...
 *
 * (solution 1)
 * S를 앞에서부터 탐색
 * "현재깊이"와 비교하면서 같으면 넘어가고,
 * 현재깊이보다 크면 괄호 열고, 작으면 괄호 닫고.
 *
 * (solution 1 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <sstream>
#include <algorithm>

using namespace std;

string S;

string solve() {
	stringstream ss;
	int curr_depth = 0;
	for (int i=0; i<S.size(); i++) {
		int n = S[i] - '0';
		if (n > curr_depth) {
			int size = n - curr_depth;
			for (int j=0; j<size; j++) {
				ss << "(";
				curr_depth++;
			}
		} else if (n < curr_depth) {
			int size = curr_depth - n;
			for (int j=0; j<size; j++) {
				ss << ")";
				curr_depth--;
			}
		}
		ss << S[i];
	}
	for (int i=0; i<curr_depth; i++) {
		ss << ")";
	}
	return ss.str();
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> S;
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}