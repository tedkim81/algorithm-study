/**
 * 2019.4.6
 *
 * 숫자 N이 있는데, 4를 출력할 수가 없어서, A와 B 두개의 수로 쪼개서 둘다 4가 없으면서,
 * A+B=N이 되도록 A와 B를 구하라.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 적어도 하나 이상의 4가 들어가 있는 수
 *
 * (output)
 * A B
 *
 * (solution 1)
 * N을 string으로 입력받고, 오른쪽에서부터 4를 탐색해서 4가 나오면 그때의 자릿수(0으로 시작, 우측기준)를 p라 할때,
 * (10 ^ p)을 더해나간다.
 *
 * (solution 1 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <string>
#include <math.h>
#include <sstream>

using namespace std;

string N;

string solve() {
	stringstream r1;
	stringstream r2;
	bool start = false;
	for (int i=0; i<N.size(); i++) {
		if (N[i] == '4') {
			r1 << '3';
			start = true;
			r2 << '1';
		} else {
			r1 << N[i];
			if (start) {
				r2 << '0';
			}
		}
	}
	stringstream r;
	r << r1.str() << " " << r2.str();
	return r.str();
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}