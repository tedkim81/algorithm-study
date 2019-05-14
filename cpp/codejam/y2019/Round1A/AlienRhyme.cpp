/**
 * 2019.4.13
 *
 * 외계인의 "시"라고 추정되는 텍스트가 발견되었고, 각 단어는 랩처럼 라임이 있는데,
 * 단어의 중간의 한 알파벳을 시작으로 그 단어 끝까지를 액센트접미사라 하고, 두 단어가 같은 액센트접미사를 가지면
 * 라임이 있다고 판단할 수 있다.
 * 그런데 주어진 단어들의 엑센트접미사가 어디인지를 알 수 없는 상황이란다.
 * 이 상황에서, 단어들 중 몇개(0개 포함)를 제외하고 2개씩 짝지어서 나열하면 각 쌍들은 라임이 있고,
 * 각 라임은 그 전체 텍스트 안에서 유일하게 한다고 할때, 그게 최대 몇 단어가 될 수 있나를 구하는 문제
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 단어의 개수
 * W[N]: 단어들
 *
 * (output)
 * 라임이 있는 쌍의 나열로 만들었을때 최대 단어 수
 *
 * (solution 1)
 * 라임은 어차피 단어 뒷부분이고, 라임이 뭐냐를 찾는게 아니라 같은지 여부가 중요한 것이므로,
 * 단어를 입력받아 저장할때 reverse 해서 W[N]에 저장하고 sort를 하자.
 * 그리고, 더 긴 라임을 공유하는 쌍을 선택하는게 유리하다고 가정하고 문제를 sort된 W를 위에서부터 순차적으로 탐색하여 쌍을 찾자.
 * 쌍을 찾았다면 그 라임을 저장하고 다음 쌍에서는 그 라임이 없는 쌍으로 찾는 방식으로 하자.
 * 여기서 한가지 애매한 것은 액센트접미사가 AB인 것이 4개가 있다고 할때, AB인거 2개와 B인거 2개를 따로 인정해 주는가인데,
 * 이건 그냥 된다고 가정하고 코드 작성후 테스트해 보자.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <set>

using namespace std;

int N;
string W[1000];
bool mkd[1000];

int get_j(int i) {
	if (mkd[i]) {
		return 0;
	}
	string w1 = W[i];
	string w2;
	bool bb = false;
	for (int ii=i+1; ii<N; ii++) {
		if (!mkd[ii]) {
			w2 = W[ii];
			bb = true;
			break;
		}
	}
	if (!bb) {
		return 0;
	}
	int l = min(w1.size(), w2.size());
	int j = 0;
	for (int k=0; k<l; k++) {
		if (w1[k] == w2[k]) {
			j++;
		} else {
			break;
		}
	}
	return j;
}

int solve() {
	for (int i=0; i<N; i++) {
		reverse(W[i].begin(), W[i].end());
	}
	sort(W, W+N);
	set<string> ss;
	for (int i=0; i<1000; i++) {
		mkd[i] = false;
	}
	for (int k=0; k<50; k++) {
		int maxlen = 0;
		for (int i=0; i<N-1; i++) {
			int j = get_j(i);
			if (j > maxlen) {
				maxlen = j;
			}
		}
		if (maxlen == 0) {
			break;
		}
		for (int i=0; i<N-1; i++) {
			int j = get_j(i);
			if (j > 0 && j == maxlen) {
				string ww = W[i].substr(0, j);
				if (ss.find(ww) == ss.end()) {
					ss.insert(ww);
					mkd[i] = true;
					for (int ii=i+1; ii<N; ii++) {
						if (!mkd[ii]) {
							mkd[ii] = true;
							break;
						}
					}
				}
			}
		}
	}
	int cnt = 0;
	for (int i=0; i<N; i++) {
		if (mkd[i]) {
			cnt++;
		}
	}
	return cnt;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int j=0; j<N; j++) {
			cin >> W[j];
		}
		int result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}