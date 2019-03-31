/**
 * A < P <= B 인 정수 P를 N번의 시도 안에 맞추기
 * #interactive
 *
 * (input)
 * T: 테스트케이스의 수
 * A B: 최저값(미포함)과 최고값(포함)
 * N: 최대 시도 횟수
 *
 * (output)
 * 각 테스트케이스에서 최대 N번 A < P <= B 인 P의 추정값을 출력하자.
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <string>

using namespace std;

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int A, B, N;
		cin >> A >> B >> N;
		int lo, hi, P;
		lo = A;
		hi = B;
		bool success = false;
		for (int j=0; j<N; j++) {
			P = ceil((lo + hi) / 2.0);
			cout << P << endl;
			string result;
			cin >> result;
			if (result == "TOO_SMALL") {
				lo = P;
			} else if (result == "TOO_BIG") {
				hi = P - 1;
			} else if (result == "CORRECT") {
				// 성공!
				success = true;
				break;
			} else {
				// 에러..
				cerr << "error!! " << result << endl;
				exit(0);
			}
		}
		if (!success) {
			// 에러..
			cerr << "error!! cannot guess in time" << endl;
			exit(0);
		}
	}
	return 0;
}