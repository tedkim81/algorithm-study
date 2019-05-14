/**
 * 2019.4.29
 *
 * 맨하탄의 유명한 크레페 가게를 찾아가는 중인데, 그 위치를 몰라서 이제 찾아야 한다.
 * 거기서 여행중인 모든 사람이 거기를 향하고 있다고 가정하고, 그 중 P명의 사람의 현재 위치와 향하는 방향을 알고 있다.
 * 그 정보를 가지고 위치를 찾을거고, 후보지가 여럿 된다면 그 중 가장 왼쪽(X 작은 값)을 고르고 
 * 그래도 여럿이면 그 중 가장 아래쪽(Y 작은 값)을 고르기로 한다.
 *
 * (input)
 * T: 테스트케이스의 수
 * P Q: P는 내가 위치 알고 있는 사람들의 수, Q는 격자의 최대 크기.
 * X Y D (P행): X와 Y는 좌표값. X는 맨왼쪽이 0, Y는 맨아래쪽이 0. D는 이동방향. N(북), S(남), E(동), W(서)
 *
 * (output)
 * 예측되는 크레페 가게의 위치 X Y 값
 *
 * (solution 1)
 * P개의 좌표를 순차적으로 확인하면서, 가능한 구간을 점점 좁혀나가자.
 * 구간은 8개의 값으로 정의하자. 좌상-(x1,y1), 우상-(x2,y2), 좌하-(x3,y3), 우하-(x4,y4)
 * 위의 방식으로는 sample의 세번째 케이스를 해결하지 못한다. 
 * 문제를 다시 확인해보니 모든 사람이 아닌 "대부분"의 사람이 가게를 향한다고 하니, 일부는 제외될 수 있는 것이다.
 * 그렇다면 이분법과 비슷한 느낌으로 좌측하단의 점에서 시작하여 조건을 만족하지 않는다면 북 또는 동쪽으로 후보위치를 이동시키는 
 * 방식으로 답을 찾도록 하자.
 * 북쪽 또는 동쪽 중에 어느 위치로 이동시킬 것인가가 이 문제의 핵심이 될 듯.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <queue>

using namespace std;

int P, Q;
int X[500], Y[500];
char D[500];

class area {
public:
	int x1; int y1;
	int x2; int y2;
	int pp;
	void init(int x1, int y1, int x2, int y2, int pp) {
		this->x1 = x1;
		this->y1 = y1;
		this->x2 = x2;
		this->y2 = y2;
		this->pp = pp;
	}
	bool available() {
		if (this->x1 >= 0 && this->x1 <= this->x2 && this->x2 <= Q
			&& this->y1 >= 0 && this->y1 <= this->y2 && this->y2 <= Q) {
			return true;
		}
		return false;
	}
	bool can_merge(area aa) {
		if (this->pp != aa.pp) {
			return false;
		}
		if (this->x1 == aa.x1 && this->x2 == aa.x2) {
			if ((this->y2 >= aa.y1 && this->y2 <= aa.y2) || (aa.y2 >= this->y1 && aa.y2 <= this->y2)) {
				return true;
			}
		}
		if (this->y1 == aa.y1 && this->y2 == aa.y2) {
			if ((this->x2 >= aa.x1 && this->x2 <= aa.x2) || (aa.x2 >= this->x1 && aa.x2 <= this->x2)) {
				return true;
			}
		}
		return false;
	}
	void merge(area aa) {
		if (this->x1 == aa.x1 && this->x2 == aa.x2) {
			if ((this->y2 >= aa.y1 && this->y2 <= aa.y2) || (aa.y2 >= this->y1 && aa.y2 <= this->y2)) {
				this->y1 = min(this->y1, aa.y1);
				this->y2 = max(this->y2, aa.y2);
			}
		}
		if (this->y1 == aa.y1 && this->y2 == aa.y2) {
			if ((this->x2 >= aa.x1 && this->x2 <= aa.x2) || (aa.x2 >= this->x1 && aa.x2 <= this->x2)) {
				this->x1 = min(this->x1, aa.x1);
				this->x2 = max(this->x2, aa.x2);
			}
		}
	}
};

string solve() {
	queue<area> q;
	queue<area> q2;
	area aa;
	aa.init(0, 0, Q, Q, 0);
	q.push(aa);
	int max_pp = 0;
	for (int i=0; i<P; i++) {
		while (!q2.empty()) {
			q2.pop();
		}
		while (!q.empty()) {
			area qaa = q.front();
			q.pop();
			area qaa1, qaa2;
			if (D[i] == 'N') {
				qaa1.init(qaa.x1, qaa.y1, qaa.x2, Y[i], qaa.pp);
				qaa2.init(qaa.x1, Y[i]+1, qaa.x2, qaa.y2, qaa.pp+1);
			} else if (D[i] == 'S') {
				qaa1.init(qaa.x1, qaa.y1, qaa.x2, Y[i]-1, qaa.pp+1);
				qaa2.init(qaa.x1, Y[i], qaa.x2, qaa.y2, qaa.pp);
			} else if (D[i] == 'E') {
				qaa1.init(qaa.x1, qaa.y1, X[i], qaa.y2, qaa.pp);
				qaa2.init(X[i]+1, qaa.y1, qaa.x2, qaa.y2, qaa.pp+1);
			} else {
				qaa1.init(qaa.x1, qaa.y1, X[i]-1, qaa.y2, qaa.pp+1);
				qaa2.init(X[i], qaa.y1, qaa.x2, qaa.y2, qaa.pp);
			}
			if (qaa1.available()) {
				bool did_merge = false;
				for (int j=0; j<q2.size(); j++) {
					area qaa_tmp = q2.front();
					q2.pop();
					if (qaa_tmp.can_merge(qaa1)) {
						qaa_tmp.merge(qaa1);
						did_merge = true;
						break;
					}
					q2.push(qaa_tmp);
				}
				if (!did_merge) q2.push(qaa1);
				if (qaa1.pp > max_pp) max_pp = qaa1.pp;
			}
			if (qaa2.available()) {
				bool did_merge = false;
				for (int j=0; j<q2.size(); j++) {
					area qaa_tmp = q2.front();
					q2.pop();
					if (qaa_tmp.can_merge(qaa2)) {
						qaa_tmp.merge(qaa2);
						did_merge = true;
						break;
					}
					q2.push(qaa_tmp);
				}
				if (!did_merge) q2.push(qaa2);
				if (qaa2.pp > max_pp) max_pp = qaa2.pp;
			}
		}
		q = q2;
	}
	int xx = Q;
	int yy = Q;
	while (!q.empty()) {
		area qaa = q.front();
		q.pop();
		if (qaa.pp < max_pp) {
			continue;
		}
		if (qaa.x1 < xx || (qaa.x1 == xx && qaa.y1 < yy)) {
			xx = qaa.x1;
			yy = qaa.y1;
		}
	}
	return to_string(xx)+" "+to_string(yy);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> P >> Q;
		for (int j=0; j<P; j++) {
			cin >> X[j] >> Y[j] >> D[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}