/**
 * 2019.4.6
 *
 * 중앙에 마스터가 하나 있고, N개의 워커가 있으며, 각 워커는 1비트를 처리한다.
 * 마스터가 외부에서 N개의 비트를 받아서 각 비트를 각 워커에서 보내고 동일한 값을 리턴 받아서,
 * 리턴 받은 비트들을 다시 모아 외부에 리턴해 주는데, 고장난 워커가 있으면 
 * 해당 워커에게서는 비트를 리턴 받지 못하기 때문에, 외부에 비트들을 리턴해 줄때도 그 부분은 누락된다.
 * B개의 워커가 고장난걸 알고 있는 상황에서 어떤 애들이 고장난 것인지 확인하기 위해 최대 F번의 테스트를 진행한다.
 * 한번의 테스트에서, N개의 비트를 마스터에 보내서 N-B개의 비트를 리턴받게 되고, 이를 최대 F번 해서 고장난 워커를 찾는것.
 * #interactive
 *
 * (input)
 * T: 테스트케이스의 수
 * N B F: N은 워커의 수(비트의 수), B는 고장난 워커의 수, F는 가능한 테스트 횟수(이보다 적어도 됨)
 * (interactive input and output)
 * (output) N개의 비트. 예, N=5일때 10110. 또는, 답을 아는 상황이면 고장난 워커들의 위치들.
 * (input) N-B개의 비트. 예, B=2일때 010. 또는, 답을 낸 상황에서 맞으면 1, 틀리면 -1. 뭔가 잘못됐어도 -1.
 *
 * (solution 1)
 * 기본적으로는, 비트열을 이등분해서 한쪽만 반전시키고 테스트하여 이등분된 각각에 고장난 워커가 몇개씩 있는지를 찾아나가는 방식.
 * 최초 비트열은 0000011111 과 같은 형식으로 시작하고 이때 만약 00001111이 왔다면, 00000에 대해 00001이 온 것이므로,
 * 앞의 절반에서 1개의 고장이 있고, 뒤의 절반에서 나머지 1개의 고장이 있다고 볼 수 있다.
 * 이런식으로 계속 고장이 있는 부분을 이등분 해나가면 고장난 워커들을 찾을 수 있다.
 * -------------------------------
 * N  // 전체 비트 수
 * B  // 누락된 비트 수
 * F  // 테스트 가능한 횟수
 * bool bb[1024]  // 누락된 비트인지(고장난 워커인지) 여부
 * string bits_send  // 보낸 비트 문자열(크기 N)
 * string bits_recv  // 받은 비트 문자열(크기 N-B)
 * bits_send = "1" N개 문자열
 * 
 * for 0 <= i < F:
 *   
 * -------------------------------
 *
 * (solution 1 result)
 * 실패. WA(Wrong Answer).
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <string>
#include <sstream>
#include <vector>

using namespace std;

int N, B, F;
bool bb[1024];
string bits_send;
string bits_recv;
vector< pair<int, int> > vv;

pair<int, int> get_pair(int s, int e, int cnt) {
	return make_pair(s*10000 + e, cnt);
}

void reverse_bits() {
	bool rvs[1024] = { false };
	for (vector< pair<int, int> >::iterator i = vv.begin(); i != vv.end(); i++) {
		int s = i->first / 10000;
		int e = i->first % 10000;
		int ee = (s+e) / 2;
		int cnt = i->second;
		for (int i=s; i<=ee; i++) {
			rvs[i] = true;
		}
	}
	stringstream ss;
	for (int i=0; i<N; i++) {
		if (rvs[i]) {
			if (bits_send[i] == '0') {
				ss << '1';
			} else {
				ss << '0';
			}
		} else {
			ss << bits_send[i];
		}
	}
	bits_send = ss.str();
}

void update_vvbb() {
	vector< pair<int, int> > vv2;
	int totalcnt = 0;
	for (vector< pair<int, int> >::iterator i = vv.begin(); i != vv.end(); i++) {
		int s = i->first / 10000;
		int e = i->first % 10000;
		int ee = (s+e) / 2;
		int cnt = i->second;
		int diffcnt = 0;
		for (int j=s; j<=ee; j++) {
			if (bits_recv.size()-1 < j-totalcnt || bits_send[j] != bits_recv[j-totalcnt]) {
				diffcnt++;
				totalcnt++;
			}
		}
		totalcnt += cnt-diffcnt;
		if (diffcnt > 0) {
			if (ee-s+1 == diffcnt) {
				for (int j=s; j<=ee; j++) {
					bb[j] = true;
				}
			}
			vv2.push_back(get_pair(s, ee, diffcnt));
		}
		if (cnt-diffcnt > 0) {
			if (e-ee == cnt-diffcnt) {
				for (int j=ee+1; j<=e; j++) {
					bb[j] = true;
				}
			}
			vv2.push_back(get_pair(ee+1, e, cnt-diffcnt));
		}
	}
	vv = vv2;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		
		cin >> N >> B >> F;
		
		for (int j=0; j<1024; j++) {
			bb[j] = false;
		}
		stringstream ss;
		for (int j=0; j<N; j++) {
			ss << "1";
		}
		bits_send = ss.str();

		vv.clear();
		vv.push_back(get_pair(0, N-1, B));
		reverse_bits();

		for (int j=0; j<F; j++) {
			cout << bits_send << endl;
			cin >> bits_recv;

			update_vvbb();

			int bbcnt = 0;
			for (int k=0; k<N; k++) {
				if (bb[k]) {
					bbcnt++;
				}
			}
			if (bbcnt == B) {
				break;
			}

			reverse_bits();
		}
		bool space = false;
		for (int j=0; j<N; j++) {
			if (bb[j]) {
				if (space) {
					cout << " ";
				}
				space = true;
				cout << j;
			}
		}
		cout << endl;

		int result;
		cin >> result;
		if (result == -1) {
			break;
		}
	}
}