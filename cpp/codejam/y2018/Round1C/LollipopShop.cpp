/**
 * 2019.3.28
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007765/000000000003e068
 *
 * 롤리팝 사탕을 파는 가게가 있고, 
 * N개의 서로 다른 맛을 가진 사탕을 만들었고, 
 * N명의 손님이 오기로 되어 있다. 
 * 각각의 손님은 자기가 먹고 싶은 사탕 중에서 하나를 살 수 있는데, 
 * 사탕은 각 맛별로 하나씩만 만들었기 때문에
 * 누군가 먼저 사갔다면 뒤의 손님은 못사먹는다.
 * 이러한 상황에서 각 손님들에게(먹고 싶은 맛이 둘 이상인 손님들이 중요할 듯) 
 * 적절한 맛의 사탕을 팔아서 총 N명의 손님 중 최대한 많은 손님에게 
 * 사탕을 팔 수 있도록 알고리즘을 짜라는게 이번 문제.
 * 이러한 상황에서 추가적인 조건이 없다면 
 * 그냥 되는대로 손님이 달라는 것중에 랜덤하게 골라주는 것 밖에 안되겠지만,
 * 중요한 조건이 하나 더 있다. 
 * 각 맛들에 대한 사람들의 선호도가 계산되어 있고, 
 * 이번 문제에서도 그 선호도에 따라 확률적으로 요청하는 맛이 분포되어 있다는 것.
 * 이 문제는 #interactive 문제이며, 
 * 요청되는 값을 모두 아는 상태에서 계산된 손님수의 90% 이상의 손님에게 
 * 사탕을 팔 수 있어야 성공으로 간주된다.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 서로 다른 맛의 사탕의 수이자, 손님의 수
 *
 * (interactive input and output)
 * (input) D R[0],...,R[D-1]: D는 해당 손님이 원하는 맛의 수, R은 그 사탕들의 번호
 * (output) R[0]~R[D-1] 중의 하나 또는 -1: 원하는 맛이 남아있으면 그거 선택, 없으면 -1
 *
 * (solution 1)
 * 일단 단순하게 생각해 보자. 더 안팔릴 것 같은 사탕을 먼저 파는게 이득이다.
 * 어차피 지금 손님은 달라고 한 것 중에 하나를 주면 넘어가는거고, 
 * 더 잘팔릴 것 같은 사탕을 남겨놔야 다음 손님들에게 사탕을 팔 확률이 높아지기 때문이다. 
 * 흠.. 이렇게만 보면 너무 문제가 쉬운데.. 이상하지만 일단 츄라이 해보자.
 * 손님들의 요청으로 D와 R값들을 입력받을때마다 
 * RR[200]에 어떤 사탕이 몇번 입력되었는지를 계속 갱신한다.
 * 그리고 각 손님들의 요청에 대해, 
 * RR로 각 사탕들의 출현확률을 계산하여 가장 낮은 사탕을 판매한다.
 * 슈도코드를 작성해 보자.
 * ----------------------------------
 * bool ss[N]  // 이미 팔린 사탕 표시
 * 
 * RR_total = 0
 * for 0 <= i < D:
 *   RR[R[i]]++  // 지금까지 출현한 해당 사탕의 수
 *   RR_total++  // 지금까지 출현한 사탕의 총 수
 *
 * c = -1  // 판매할 사탕 ID 결정
 * c_rate = 2  // 사탕 출현 확률. 최소값 찾기 위해 1보다 큰수로 시작.
 * for 0 <= i < D:
 *   if ss[R[i]]:
 *     continue
 *   c_rate_tmp = RR[R[i]] / RR_total
 *   if c_rate_tmp < c_rate:
 *     c = R[i]
 *     c_rate = c_rate_tmp
 * if c >= 0:
 *   ss[c] = true
 * return c
 * ----------------------------------
 *
 * (solution 1 result)
 * 쳇, 실패.. 어째 너무 쉽다 했다.. 좀더 생각해 보자.
 * 아.. 이것은 해법을 생각하기에는 쉬운 문제가 맞았던 것 같고, 
 * 대신에 구현할때 신경써야할 부분들이 꽤 있는 문제였다.
 * 문제를 다시 열심히 읽어보면서 (Solution도 읽어보면서;;) 
 * 문제가 뭘까를 찾아보니 단순 오해 및 실수가 있었다..ㅠ.ㅠ
 * - 각 사탕별 출현확률이 전체 test case에 모두 같게 적용될 것이라 생각했는데, 
 *   확인해보니 test case 별로 각각 별개였다. 
 *   Solution을 읽다 보니 
 *   "200번이 확률에 있어서는 작은 수이지만 그럼에도 충분히 답이 나올 수 있도록
 *   각 사탕별 출현확률 편차를 뒀다"는 내용이 있었는데, 이걸보고 아차 싶었다. 
 *   이참에 확실히 명심하고 갈 것이, 코드잼에서 각 test case는 서로 의존성이 없다.
 * - 단순한 코딩 실수로 ss와 RR을 제대로 초기화하지 않았었다.. (이러지 말자.. 0TL)
 *
 * 위의 문제들을 수정하여 다시 시도해보니, 깔끔하게 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int main() {
	int T, N, D, R[200];
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		bool ss[200] = { false };
		int RR[200] = { 0 };
		int RR_total = 0;
		for (int j=0; j<N; j++) {
			cin >> D;
			for (int k=0; k<D; k++) {
				cin >> R[k];
			}
			for (int ii=0; ii<D; ii++) {
				RR[R[ii]]++;
				RR_total++;
			}
			int c = -1;
			double c_rate = 2.0;
			if (D > 0) {
				for (int ii=0; ii<D; ii++) {
					if (ss[R[ii]]) {
						continue;
					}
					double c_rate_tmp = RR[R[ii]] / (double)RR_total;
					if (c_rate_tmp < c_rate) {
						c = R[ii];
						c_rate = c_rate_tmp;
					}
				}
				if (c >= 0) {
					ss[c] = true;
				}
			}
			cout << c << endl;
		}
	}
}