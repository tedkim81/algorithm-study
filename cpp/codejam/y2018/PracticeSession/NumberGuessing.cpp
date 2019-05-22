/**
 * 2018.12.11
 * 
 * A < P <= B 인 정수 P를 N번의 시도 안에 맞추기
 * 각 시도에 대하여, 답보다 작은 수였다면 TOO_SMALL, 
 * 큰 수였다면 TOO_BIG, 정답이라면 CORRECT을 응답해준다.
 * #interactive #이분법
 *
 * (input)
 * T: 테스트케이스의 수
 * A B: 최저값(미포함)과 최고값(포함)
 * N: 최대 시도 횟수
 *
 * (interactive input and output)
 * (output) A < P <= B 인 P의 추정값을 출력
 * (input) TOO_SMALL or TOO_BIG or CORRECT, 조건에 맞지 않는 값이었다면 WRONG_ANSWER
 *
 * (solution 1)
 * 이분법 적용. A는 미포함, B는 포함임에 주의하자.
 *
 * (solution 1 result)
 * 성공!!
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
				success = true;
				break;
			} else {
				cerr << "error!! " << result << endl;
				exit(0);
			}
		}
		if (!success) {
			cerr << "error!! cannot guess in time" << endl;
			exit(0);
		}
	}
	return 0;
}