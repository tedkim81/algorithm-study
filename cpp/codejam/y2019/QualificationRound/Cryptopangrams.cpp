/**
 * 2019.4.6
 *
 * 암호화된 값들이 주어지고 복호화해서 원문 문자열을 구하는 문제
 * 원문은 26개의 알파벳이 적어도 한번 이상씩은 들어간 문자열(pangram)
 * 암호화 방식
 * - N이하의 소수(prime) 26개를 정하고 오름차순 정렬후 A부터 Z에 매핑
 * - 문자열에 대해 0번째 문자부터 시작하여 인접한 두 문자에 해당하는 소수의 곱을 구해서 나열(0번째와 1번째, 1번째와 2번째,..)
 * 위의 방식으로 암호화된 값이 주어졌을때 원문을 구해서 출력하자.
 *
 * (input)
 * T: 테스트케이스의 수
 * N L: N은 소수 구할때 upper limit. L은 암호화된 값의 개수(원문의 길이보다 1 작다).
 * L개의 암호화된 값들
 *
 * (output)
 * 복호화된 원문. 알파벳대문자로 구성된 문자열
 *
 * (solution 1)
 * 암호화된 값 중 첫번째 값에서 소수 두개를 찾아내고, 그 다음 값부터는 이전값에 대한 소수 두개 중 하나로 연이어 찾으면 된다.
 * 소수를 다 찾아내고 나면, 오름차순 정렬해서 알파벳과 매핑하고, 그걸로 원문을 만들어내면 된다.
 * 이 문제의 핵심은 첫번째 값에서 소수 두개를 찾아내는 것. Test set 1은 할만한데, Test set 2는 N의 최대값이 너무 크다.
 * 10의 100제곱이라니.. 이건 에라토스테네스 할아버지의 알고리즘을 사용해도 안될 놈이다..
 * 일단은 Test set 1만 고려하여 풀자.
 * -------------------------------
 * int LL[100]  // L개의 암호화된 값들
 * int LL2[101]  // 분리해낸 소수들
 * set ss // 소수셋
 * map mm // 소수-알파벳 매핑
 * bool pp[10001]  // 소수인지 여부
 * pp 전체를 true로 초기화
 * for 2 <= i <= 100:
 *   if pp[i]:
 *     for i*i <= j <= 10000, j += i:
 *       pp[j] = false
 * int pp0, pp1
 * for 2 <= i <= sqrt(N):
 *   if pp[i]:
 *     if LL[0] % i == 0:
 *       pp1 = LL[0] / i
 *       if pp[pp1]:
 *         pp0 = i
 *         break
 * LL2[0] = pp0, LL2[1] = pp1 으로 놓고 나머지 LL2 구해보기. LL2[i] = LL[i-1] / LL2[i-1]
 * 중간에 실패하면 LL2[0] = pp1, LL2[1] = pp0 로 놓고 나머지 LL2 구하기
 * for 0 <= i <= L:
 *   ss.insert(LL2[i])
 * // ss 오름차순으로 정렬되어 있음
 * char ch = 'A'
 * for ss 탐색 -> i:
 *   mm[i] = ch
 *   ch++
 * string result
 * for 0 <= i <= L:
 *   result += mm[LL2[i]]
 * -------------------------------
 *
 * (solution 1 result)
 * 실패. RE(Runtime Error). 아무리 디버깅해봐도 문제가 없어 보인다. Test set 1은 통과해야 하는데..
 * 이것저것 수정해보면서 7번이나 시도해 봤지만 계속 실패. 아.. y2018의 CubicUFO가 생각난다.. 
 * 걔도 죽어라고 RE 실패 나다가, 한달 정도 후에 같은 코드로 다시 했더니 됐었는데..
 * 암튼 얘도 지금은 일단 포기. 나중에 다시 시도해 보자.
 * ... 짜증이 가라앉았다. 다시 생각해보자. 일단은 Runtime Error가 어디서 나는지 찾아야 한다.
 * 어차피 연습이니까 여기저기 의심가는 부분들을 제거해보면서 RE가 안날때까지 시도해보자.
 * 찾았다! pp0와 pp1을 가지고 LL2[0]와 LL2[1]을 정할때 "LL[1]을 나눌 수 있고 그 몫이 소수인 것"을
 * pp1으로 정하고 넘어갔었는데, 보니까 ABAB.. 이런식으로 초반에 두 알파벳이 번갈아 나오는 경우 BABA.. 와 같은 
 * 경우와 구분할 수 없다는 문제가 있다. 따라서 LL2를 다 구해보고 중간에 실패하면 LL2[0]와 LL2[1]을 바꿔주는
 * 과정이 필요하다.
 * 해당 부분 수정후 다시 시도하니, 드디어 Test case 1은 통과한다..ㅠ.ㅠ 
 * 이제 맘 편하게 analysis을 참고하여 Test case 2를 풀어보자..;;
 *
 * (solution 2)는 Cryptopangrams2.py 참조.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <set>
#include <map>
#include <math.h>
#include <sstream>
#include <string>

using namespace std;

int N;
int L;
int LL[101];
bool pp[10001];

void makeprimes() {
	pp[0] = false;
	pp[1] = false;
	for (int i=2; i<10001; i++) {
		pp[i] = true;
	}
	for (int i=2; i<101; i++) {
		if (pp[i]) {
			for (int j=i*i; j<10001; j+=i) {
				pp[j] = false;
			}
		}
	}
}

string solve() {
	int LL2[101];
	set<int> ss;
	map<int, char> mm;

	int pp0 = 0;
	int pp1 = 0;
	for (int i=2; i<=N; i++) {
		if (pp[i] && (LL[0] % i) == 0) {
			pp1 = (int)(LL[0] / i);
			if (pp1 <= N && pp[pp1]) {
				pp0 = i;
				break;
			}
		}
	}
	bool pass = true;
	LL2[0] = pp0;
	LL2[1] = pp1;
	for (int i=2; i<=L; i++) {
		if (LL[i-1] % LL2[i-1] != 0) {
			pass = false;
			break;
		}
		LL2[i] = (int)(LL[i-1] / LL2[i-1]);
		if (LL2[i] < 2 || LL2[i] > N || pp[LL2[i]] == false) {
			pass = false;
			break;
		}
	}
	if (pass == false) {
		LL2[0] = pp1;
		LL2[1] = pp0;
		for (int i=2; i<=L; i++) {
			LL2[i] = (int)(LL[i-1] / LL2[i-1]);
		}
	}
	for (int i=0; i<=L; i++) {
		ss.insert(LL2[i]);
	}
	char ch = 'A';
	for (set<int>::iterator i = ss.begin(); i != ss.end(); i++) {
		mm[*i] = ch;
		ch++;
	}
	stringstream stst;
	for (int i=0; i<=L; i++) {
		stst << mm[LL2[i]];
	}
	return stst.str();
}

int main() {
	makeprimes();
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N >> L;
		for (int j=0; j<L; j++) {
			cin >> LL[j];
		}
		if (N > 10000) {
			cout << "Case #" << i+1 << ": ONLYFORTESTSETONE" << endl;
			continue;
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
	return 0;
}