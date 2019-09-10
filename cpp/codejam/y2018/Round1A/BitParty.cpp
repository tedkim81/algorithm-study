/**
 * 2019.2.1
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007883/000000000002fff6
 *
 * 로봇들이 파티를 준비하기 위해 슈퍼마켓에서 비트를 사는 상황이다.
 * (아.. 문제가.. 갈수록 가관이다..;;)
 * 쇼핑중인 로봇의 수는 R, 
 * 살 수 있는 전체 비트의 수는 B, 
 * 결제를 위해 대기중인 캐쉬어의 수는 C
 * 그리고 각 캐쉬어는, 한명이 최대 M개의 비트를 결제처리해 줄 수 있고, 비트 하나하나 스캔하는데 S초가 걸린다.
 * 그리고 결제 및 포장을 하는데 P초가 더 걸린다. 
 * 만약, 어떤 로봇이 N개의 비트를 가지고 왔으면, (S * N) + P 초가 걸리는 것이다.
 * 각 캐쉬어는 각각의 M, S, P 값을 갖는다.
 * R <= C 이고, 한명의 캐쉬어는 로봇 하나만 결제처리해 줄 수 있다.
 * 자, 로봇들이 매우 합리적으로 움직여서 최적의 조합으로 비트들을 나눠들고 최적의 캐쉬어에게 가서 결제를 했다면,
 * 몇 초가 걸릴까? (가능한 최소값이 얼마인가?)
 * #이분법
 *
 * (input)
 * T: 테스트케이스의 수
 * R B C: R(로봇 수), B(비트 수), C(캐쉬어 수)
 * M S P (C행): M(최대 처리 비트 수), S(비트 하나 스캔 시간), P(결제 및 포장 시간)
 *
 * (output)
 * 가능한 최소 결제처리 시간
 *
 * (solution 1)
 * 비트들이 적절하게 분배된 최종상황을 가정해 보면, 
 * 처리시간이 가장 오래걸리는 캐쉬어가 비트를 하나라도 다른 (맡은 비트가 하나라도 있는) 캐쉬어에게 넘길 수 없다.
 * 즉, 비트를 처리해야할 캐쉬어들 사이에 편차가 최소가 되는 경우를 의미한다.
 *
 * 전제조건을 나열해 보면,
 * 1. B개의 비트는, R 이하의 캐쉬어에게 분배된다. (극단적으로 1명의 캐쉬어가 다 처리할 수도 있다.)
 * 2. C는 R보다 크거나 같다. 즉, 캐쉬어가 부족해서 기다리는 경우는 없다.
 *
 * 성능 고려 없는 단순한 알고리즘을 생각해 보면,
 * 1. 비트를 하나씩 할당하는데, 처리시간이 최소가 되게 해주는 캐쉬어에게 할당한다.(할당받은 캐쉬어 수 <= R)
 * 2. 할당후 해당 캐쉬어가 그때까지 받은 비트를 모두 다른 캐쉬어에게 넘겨서 빨라질 수 있는지 탐색후 있다면 넘긴다.
 * 3. 비트가 모두 할당될 때까지 위의 1과 2를 계속 반복후, 마지막 캐쉬어의 처리시간을 구하면 그게 답.
 *
 * 단순 알고리즘으로 Test set 1은 통과할 수 있지만, Test set 2는 통과할 수 없다.
 * B가 최대 10억개, C도 최대 1000개가 될 수 있고, 그러면 10억*1000 번이 넘는 연산이 필요하기 때문.
 *
 * 아이디어!
 * 비트의 수를 x축에, 각 캐쉬어들의 처리시간을 y축에 놓고 그래프를 그려보면,
 * x가 0일때 P로 시작하여 x가 증가함에 따라 y도 증가하는 기울기 S의 1차함수그래프(직선)가 각 캐쉬어마다 하나씩 그려진다.
 * y = S*x + P
 * 단, 여기서 중요한 특징은, 캐쉬어의 직선은 x가 M일때까지만 그려지는 유한한 직선이라는 것이다.
 * 여기에다가 만약 답을 ss라고 가정하고 y=ss 그래프를 그려보면? 
 * y=ss 직선 하단에 들어가는 캐쉬어 직선들의 수가 일을 해야할 캐쉬어의 수가 되고, 
 * y=ss 직선 하단에서, 각 캐쉬어들의 비트 수들의 합이 총 주문되는 비트 수가 된다.
 * 이분법으로 ss에 대하여, 캐쉬어의 수와 비트 수를 이용한 조건에 따라 ss를 찾을 수 있을 듯.
 * 
 * 자, 이제 알고리즘을 다시 생각해보자.
 * 1. ss의 최소값은 P최소값, ss의 최대값은 (B * S최대값 + P최대값).
 * 2. 이분법으로 100번 반복.
 *  2-1. ss 밑에 캐쉬어 직선이 몇개? cc개. 비트수가 몇개? bb개.
 *  2-2. bb < B 이면, cc와 상관없이 ss 위로.
 *  2-3. bb == B && cc <= R 이면, ss가 답. 반복 종료.
 *  2-4. bb == B && cc > R 이면, ss 위로.
 *  2-5. bb > B && cc <= R 이면, ss 아래로.
 *  2-6. bb > B && cc > R 이면, 
 *       y=ss 직선과 만나는 캐쉬어 직선들 중 왼쪽에서 만나는 순서대로 cc-R 개를 제거 후,
 *       bb > B 이면 ss 아래로, bb < B 이면 ss 위로, bb == B 이면 ss 정답.
 *
 * (solution 1 result)
 * 실패! 결과 코드 WA (Wrong Answer)
 * 입출력 값들의 범위가 너무 커서 자체적으로 검증하기가 너무 어렵다 보니 어디가 잘못된 것인지 찾기가 어려웠다.
 * 실패한 테스트 케이스가 무엇인지라도 알려주면 좋으련만, 코드잼은 그렇게 친절하지가 않다.ㅠ.ㅠ
 * 그래서 결국 Round 1A에서 1등한 vepifanov의 코드를 참조하여 차이점을 분석했다.
 * 기본적인 전략은 같은데, 이분법을 통해 답을 구하는 과정에서, 
 * 위의 solution 1이 너무 복잡했고, 그러다보니 오류가 몇군데 있었고,
 * 결정적으로는, hi와 lo가 1 차이가 나는데 y=ss 직선을 up 시켜야 하는 경우에 대한 처리가 미흡했다.
 * hi와 lo가 1 차이 나는 경우, ss=(hi+lo)/2 하면 ss==lo 가 되는데,
 * 이때 y=ss 직선 아래의 비트 수가 B보다 작다면, get_updown()에서 'u'을 리턴하지만, 이미 ss==lo 이므로
 * ss는 변하지 않고 계속 lo와 같게 된다. 이때 답은 hi가 되어야 하는 것이다.
 *
 * (solution 2)
 * 수정된 알고리즘,
 * 1. ss의 최소값(lo)은 P최소값, ss의 최대값(hi)은 (B * S최대값 + P최대값)으로 시작.
 * 2. 이분법으로 100번 반복.
 *  2-1. ss 밑에 캐쉬어들을 비트 많이 처리한 순서대로 정렬.
 *  2-2. min(R, ss 밑의 캐쉬어 직선수) 만큼의 캐쉬어에 대하여 처리한 비트수 계산, bbb
 *  2-3. bbb < B 이면, ss 'u'p.
 *  2-4. bbb >= B 이면, ss 'd'own.
 * 3. hi가 정답
 *
 * (solution 2 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int R, B, C;
int M[1000], S[1000], P[1000];

long long get_bit_num(long long ss, int c_idx) {
	long long bit_num = (ss - P[c_idx]) / S[c_idx];
	return min(bit_num, (long long)M[c_idx]);
}

char get_updown(long long ss) {
	long long bb[C];
	int cc = 0;
	for (int i=0; i<C; i++) {
		bb[i] = get_bit_num(ss, i);
		if (P[i]+S[i] <= ss) {
			cc++;
		}
	}
	sort(bb, bb+C);
	reverse(bb, bb+C);
	long long bbb = 0;
	for (int i=0; i<min(R, cc); i++) {
		bbb += bb[i];
	}
	if (bbb < B) {
		return 'u';
	} else {
		return 'd';
	}
}

string solve() {
	long long p_min = 1000000001;
	long long p_max = 0;
	long long s_max = 0;
	for (int i=0; i<C; i++) {
		if (P[i] < p_min) {
			p_min = P[i];
		}
		if (P[i] > p_max) {
			p_max = P[i];
		}
		if (S[i] > s_max) {
			s_max = S[i];
		}
	}
	long long hi = B * s_max + p_max;
	long long lo = p_min;
	long long ss;
	for (int i=0; i<100; i++) {
		ss = (hi + lo) / 2;
		char updown = get_updown(ss);
		if (updown == 'u') {
			lo = ss;
		} else {
			hi = ss;
		}
		if (hi == lo) {
			break;
		}
	}
	return to_string(hi);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> R >> B >> C;
		for (int j=0; j<C; j++) {
			cin >> M[j] >> S[j] >> P[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
	return 0;
}