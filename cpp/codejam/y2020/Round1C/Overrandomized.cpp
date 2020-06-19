/**
 * 2020.5.2
 *
 * 알파벳대문자 10자로 구성된 문자열 D가 있고, 얘가 뭔지 알아내는게 문제.
 * 숫자 M과 문자 R이 10000개 주어지는데,
 * 1 <= N <= M 인 N을 랜덤하게 정해서, 그걸 D를 이용해서 인코딩해서 리턴한다고 한다.
 * 인코딩은, N을 구성하는 숫자를 D에서의 위치값으로 이용해 해당 위치에 있는 문자로 치환하는 방식.
 *
 * (input)
 * T: 테스트케이스의 수
 * U: M의 자리수
 * (10000행의) M R: M(U자리 십진수), R(랜덤하게 선택된 수를 인코딩한 문자열)
 *
 * (output)
 * 문자열 D
 *
 * (solution 1)
 * - 문자 10개가 뭔지는 알 수 있다. 그 순서를 정하는게 문제.
 * - 모든 경우의 수는, 10! == 3628800
 * - 각 경우에 대해 맞는지 확인하는게 10000번. 모든 경우 체크는 불가.
 *
 * - 10000개 탐색하면서 출현한 문자들을 문자별로 min, max 설정
 * - 0번째부터 가능한 문자들을 깊이우선탐색으로 탐색
 *
 * (solution 1 result)
 * Test set 1은 통과, 2는 실패. 일단 넘어가고 추후 다시 풀자.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <map>
#include <set>
#include <sstream>
#include <fstream>

using namespace std;

int U;
int M[10000];
string R[10000];
map<char, pair<int, int> > m;

int get_digits(int a) {
	int result = 1;
	int aa = 10;
	for (int i=0; i<20; i++) {
		if (a < aa) {
			break;
		}
		aa *= 10;
		result++;
	}
	return result;
}

int get_first_digit(int a, int digits) {
	int result = a;
	for (int i=0; i<digits-1; i++) {
		result /= 10;
	}
	return result;
}

string find_d(string s) {
	int pos = s.size();
	if (pos == 10) {
		return s;
	}
	set<char> ch_set;
	int diff = 9999;
	for (map<char, pair<int, int> >::iterator i = m.begin(); i != m.end(); i++) {
		if (pos >= i->second.first && pos <= i->second.second) {
			if (i->second.second - i->second.first < diff) {
				ch_set.clear();
				ch_set.insert(i->first);
				diff = i->second.second - i->second.first;
			} else if (i->second.second - i->second.first == diff) {
				ch_set.insert(i->first);
			}
		}
	}
	string result;
	for (set<char>::iterator i = ch_set.begin(); i != ch_set.end(); i++) {
		string tmp = find_d(s + (*i));
		if (tmp.size() > result.size()) {
			result = tmp;
		}
	}
	return result;
}

string solve() {
	m.clear();
	for (int i=0; i<10000; i++) {
		int digits = get_digits(M[i]);
		int rsize = R[i].size();
		for (int j=0; j<rsize; j++) {
			char cc = R[i][j];
			if (!m.count(cc)) {
				m[cc] = make_pair(0, 9);
			}
		}
		if (digits == rsize) {
			int first_digit = get_first_digit(M[i], digits);
			char cc = R[i][0];
			if (digits > 1) {
				m[cc].first = 1;
			}
			m[cc].second = min(m[cc].second, first_digit);
		} else {
			
		}
	}
	string result = find_d(string());
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> U;
		for (int j=0; j<10000; j++) {
			cin >> M[j] >> R[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}

// int main() {
//     ifstream infile ("input.txt");
//     if (infile.is_open()) {
//         string line;
//     	getline(infile, line);
//     	int T = stoi(line);
//     	for (int i=0; i<T; i++) {
//     		getline(infile, line);
//     		U = stoi(line);
//     		for (int j=0; j<10000; j++) {
//     			getline(infile, line);
//     			stringstream ss(line);
//     			string line2;
//     			getline(ss, line2, ' ');
//     			M[j] = stoi(line2);
//     			getline(ss, line2, ' ');
//     			R[j] = line2;
//     		}
//     		string result = solve();
//     		cout << "Case #" << i+1 << ": " << result << endl;
//     	}
//         infile.close();
//     } else {
//         cout << "Unable to open file";
//     }
// }