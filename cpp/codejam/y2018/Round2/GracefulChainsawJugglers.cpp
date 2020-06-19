/**
 * 2020.2.28
 *
 * Code jam page url:
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007706/00000000000459f3
 *
 * Github page url:
 * https://github.com/tedkim81/algorithm-study/blob/master/cpp/codejam/y2018/Round2/GracefulChainsawJugglers.cpp
 *
 * 전기톱으로 저글링을 하는 쇼가 있다. 
 * 전기톱은 빨간색이 R개, 파란색이 B개 있고, 저글러는 충분히 많이 있는 상황.
 * 쇼에 나서는 저글러는 1개 이상의 전기톱을 가지고 저글링을 해야 한다.
 * 관객들은, 저글러가 최대한 많이 나올수록 좋지만,
 * 같은 쇼를 하는 저글러가 2명 이상이면 안된다고 한다.
 * R과 B가 주어질때 저글러는 최대 몇명이 될 수 있나?
 *
 * (input)
 * T: 테스트케이스의 수
 * R B: R은 빨간색 전기톱의 수, B는 파란색 전기톱의 수
 *
 * (output)
 * R+B개를 쇼에 참가하는 저글러들에게 분배한다고 할때, 저글러는 최대 몇명?
 *
 * (solution 1)
 * 단순하게 생각해보자. 
 * 저글러 한명씩 차근차근 전기톱을 나눠주는데 가능한한 최소한으로 나눠준다.
 * 1개씩 나눠주고 1개씩 안되면 2개씩, 2개씩 안되면 3개씩..
 * (R1),(B1),(R1,B1),(R2),(B2),(R1,B2),(R2,B1),..
 * 순서는 어떻게 정할까? 선택의 기로에 서게되면 R과 B가 균형을 맞추는 쪽으로 선택한다.
 * (직감적으로 R과 B가 균형을 이룰때 경우의 수가 늘어난다고 생각했고,
 * 증명까지는 아니지만, 몇가지 경우를 시뮬레이션 해봤다.)
 * 그리고, 1개짜리 조합, 2개짜리 조합, 3개짜리 조합,..., (R+B)개짜리 조합들을
 * 리스트에 미리 담아두고, 합이 작은 애들부터 되는대로 빼내는 식으로 구현하자.
 * 슈도코드를 작성해 보자.
 * ---------------------------------------
 * int R // 빨간색 전기톱의 수
 * int B // 파란색 전기톱의 수
 * pair ll[251001] // 모든 (R,B)조합의 리스트
 * bool ss[251001] // ll에 대해, 선택된 저글러인지 여부
 * int cnt = 0 // 저글러의 수
 *
 * int k = 0
 * for 0 <= i <= R:
 *   for 0 <= j <= B:
 *     if i == 0 and j == 0:
 *       continue
 *     ll[k++] = (i,j)
 * 
 * while(true):
 *   int size2 = 0 // size의 최대값
 *   int diff2 = 251001 // diff의 최소값
 *   int i2 = -1 // 선택된 i
 *   int rr2, bb2
 *   for 0 <= i < ll.size:
 *     if ss[i]:
 *       continue
 *     rr = ll[i].first
 *     bb = ll[i].second
 *     if R >= rr and B >= bb:
 *       int size = (R-rr) + (B-bb) // R과 B의 남은 수
 *       int diff = abs((R-rr) - (B-bb)) // 남은 R과 B의 차이
 *       if size >= size2 and diff < diff2:
 *         i2 = i
 *         size2 = size
 *         diff2 = diff
 *         rr2 = rr
 *         bb2 = bb
 *   if i2 == -1:
 *     break
 *   cnt++
 *   ss[i2] = true
 *   R -= rr2
 *   B -= bb2
 * return cnt
 * ---------------------------------------
 *
 * (solution 1 result)
 * 실패! WA(Wrong Answer)..
 * 뭐가 잘못된 건지 한참을 헤맨 끝에, 
 * R과 B가 균형을 이룰때 경우의 수가 늘어날 것이라고 가정했던 것이 잘못됐다는 것을 알았다.
 * 역시.. 직감으로 문제 풀면 안된다는 것을 새삼 깨닫게 된다..ㅠ.ㅠ
 * R=3, B=3 일때, 위의 알고리즘으로는 3이 나오는데, 4가 나와야 한다.
 * (1,0), (0,1) 하고 나면 (2,2) 남고, 여기서 위의 알고리즘으로는 (1,1)을 선택하게 되는데,
 * (2,0)을 선택해야, (0,2)까지 해서 깔끔하게 4개가 되는 것이다.
 * 다시 생각해보자.
 *
 * (solution 2)
 * 다른 문제 풀 때와 비슷한 방식으로 접근하니 Test set 1 까지는 풀어낼 방법이 있었다.
 * (Ri,Bi) 집합에서 순서대로 하나씩 선택할지 말지를 결정하면서 탐색하고,
 * 남은 집합을 표현하는 값과 남은 R과 B를 메모이제이션해서 탐색을 줄여주는 방식이다.
 * GracefulChainsawJugglers2.cpp의 Solution2 참고.
 * 하지만 Test set 2에서는 R과 B가 최대 500이고, 메모이제이션 해야할 크기가
 * 500^4 가 되기 때문에 동일한 알고리즘으로는 풀 수가 없다.
 * 이래저래 생각해보다가 시간이 너무 지체되어 ANALYSIS를 참고하기로 했다.
 * 그런데 ANALYSIS도 말이 참 어렵다.. 무슨 말인지 이해하는데 한참 걸렸다;;
 *
 * ANALYSIS 정리
 *
 * 0<=B<=B최대값, 0<=R<=R최대값 을 만족하는 모든 (bb,rr) 집합을 만들고,
 * (bb,rr)의 집합을 차례대로 탐색하면서 i번째의 (bi,ri)가 정답집합에 포함될 경우와 
 * 그렇지 않은 경우를 재귀적으로 탐색하면서 결과집합의 최대값을 구한다.
 * 이때, i,bb,rr 값에 대해 메모이제이션을 하면 그 크기가 ((B+1)^2)*((R+1)^2) 이므로
 * Test set 1은 해결 가능하지만 Test set 2는 불가능하다. 
 * (위의 solution2도 이와 같은 방식이다.)
 *
 * Test set 2를 해결하기 위해서는, Test case가 달라도(즉, B와 R이 계속 바뀌어도) 
 * 같은 메모이제이션 테이블을 사용할 수 있음을 알아차려야 한다고 ANALYSIS에서 설명한다.
 * 하지만 이후 내용을 보면, 메모이제이션은 (i,bb,rr)이 아니라 (bb,rr)에 대해 저장하고,
 * 정답집합의 특징도 다시 정리해야 문제를 해결할 수 있기 때문에, 위의 해결방식에서
 * 바로 "Test case가 달라도 같은 메모이제이션 테이블을 사용할 수 있음을 알아차리기"는 
 * 어렵지 않나 싶다.
 *
 * Test set 2를 해결하기 위해서는, 아래와 같은 가정을 이끌어내어
 * (bb,rr)에 대한 메모이제이션 테이블을 미리 만들어두고 모든 Test case에 적용할 수 있음을
 * 발견해내야 한다.
 * B=2,R=2일때의 정답집합은 B=1,R=1일때의 정답집합을 포함한다.
 * 즉, B=b,R=r일때의 정답집합은 B=b-a, R=r-b (a>=0, b>=0)일때의 정답집합을 포함한다.
 *
 * 이제 좀더 구체적으로 과정을 정리해보자.
 * 
 * 정답집합이 minimal weak b,r valid set에 포함됨을 증명한다.
 * 그러면 집합에 (bb,rr)이 있다고 가정할 경우
 * 0<=i<=bb, 0<=j<=rr 인 i,j에 대해 (bb-i, rr-j)가 모두 있음을 전제할 수 있다.
 * 
 * - b,r valid set: left 합이 B, right 합이 R 인 (bb,rr)의 집합
 * - weak b,r valid set: left 합이 B 이하, right 합이 R 이하인 (bb,rr)의 집합
 *
 * weak b,r valid set 사이즈 제일 큰거 크기 X
 * b,r valid set 사이즈 제일 큰거 크기 Y
 * b,r valid sets 는 weak... 에 포함되니까 X >= Y
 * weak... 사이즈 제일 큰거의 제일 큰 (bb,rr)를 빼고 (bb+db, rr+dr)를 
 * 넣을 수 있으니까 X > Y 인 경우는 없다. 따라서 X == Y
 *
 * - minimal weak...은, weak...에서 임의의 (bb,rr)을 골랐다고 가정했을때
 *   (bb,rr)이 있으면 (bb-1,rr)도 있고 (bb,rr-1)도 꼭 있음이 보장되도록 
 *   빈틈 없이 만든 집합
 * 
 * minimal weak... 사이즈 제일 큰거의 크기와 weak... 사이즈 제일 큰거의 크기는 같다.
 * weak...은 항상 같은 크기의 minimal weak...을 만들 수 있고,
 * 반대로 minimal weak...은 어차피 weak...집합의 모든 집합 안에 포함되니까
 * 제일 큰거의 크기는 같다.
 *
 * 결국 위에서 하고 싶은 말은, 
 * b,r valid set 제일 큰거의 크기 
 * == weak... 제일 큰거의 크기 
 * == minimal... 제일 큰거의 크기
 * 이므로, minimal... 제일 큰거의 크기를 구하면 된다는 것이다.
 *
 * minimal... 제일 큰거를 구해놨다고 가정하고, 거기에 (bb,rr)가 있다면,
 * (bb-1,rr),(bb,rr-1)... 이 있는 것이고, (bb,rr) 밑으로 부분집합의 크기가
 * (bb+1)*(rr+1)-1이라는 것을 알 수 있다.( (0,0)은 제외 )
 * 그리고 그 집합의 bb쪽 애들 합은 정리하면, (rr+1)*(1+bb)*bb/2
 * rr쪽은, (bb+1)*(1+rr)*rr/2
 * 즉, (rr+1)*(1+bb)*bb/2 <= B and (bb+1)*(1+rr)*rr/2 <= R 을 만족해야
 * (bb,rr)은 그 집합에 포함될 수 있다는 조건을 탐색 가지치기를 할때 이용해야 한다.
 *
 * 그리고 마지막으로, 문제를 풀기 위해서는 중요하고 기본적인 규칙이 하나 더 필요하다.
 * 정답 집합에 포함된 임의의 (bb,rr)에 대하여, f(B,R) = f(B-bb,R-rr) + 1
 *
 * 결론적으로 정리해보면,
 * - 모든 B,R에 대한 정답배열 brmap[501][501]을 미리 만들어 둔다.
 *   brmap[B][R]은 B,R에 대한 집합의 최대크기를 의미한다.
 * - (rr+1)*(1+bb)*bb/2 <= B and (bb+1)*(1+rr)*rr/2 <= R
 *   만족하는 (bb,rr)만 취급한다.
 * - 정답 집합의 (bb,rr)에 대하여, f(B,R) = f(B-bb,R-rr) + 1
 * - (bb,rr)을 탐색하면서, brmap을 점진적으로 갱신한다.
 *
 * 이제 슈도코드를 작성해 보자.
 * solution1과의 통일성을 위해 순서는 pair는 (R,B)로 한다.
 * ---------------------------------------
 * int R,B
 * int brmap[501][501]
 * // (rr,bb)를 탐색하면서 그때까지 탐색된 애들에 대해 brmap을 갱신한다.
 * for 0 <= rr <= R:
 *   if (1+rr)*rr/2 > R:
 *     break
 *   for 0 <= bb <= B:
 *     if rr == 0 and bb == 0:
 *       continue
 *     if (bb+1)*(1+rr)*rr/2 > R or (rr+1)*(1+bb)*bb/2 > B:
 *       break
 *     // brmap 갱신할때 서로 영향을 주지 않도록 (rr2,bb2)는 큰쪽부터 탐색한다.
 *     for R >= rr2 >= rr:
 *       for B >= bb2 >= bb:
 *         brmap[rr2][bb2] = max(brmap[rr2][bb2], brmap[rr2-rr][bb2-bb]+1)
 * ---------------------------------------
 *
 * (solution 2 result)
 * 성공.. 참 오래 걸렸다.. ㅠ.ㅠ
 * brmap을 미리 만들어 둔다는 것과, brmap을 만들때, (rr,bb)를 순서대로 탐색하면서
 * brmap을 "점진적으로 갱신"해야 한다는 것을 발견할 수 있었어야 했다.
 * 그 외의 다른 조건들은 결국 brmap을 만들때 탐색시 가지치기 용도라 볼 수 있겠다.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int rbmap[501][501];

void solve() {
	int R = 500;
	int B = 500;
	for (int rr=0; rr<=R; rr++) {
		for (int bb=0; bb<=B; bb++) {
			rbmap[rr][bb] = 0;
		}
	}
	for (int rr=0; rr<=R; rr++) {
		if ((1+rr)*rr/2 > R) {
			break;
		}
		for (int bb=0; bb<=B; bb++) {
			if (rr == 0 && bb == 0) {
				continue;
			}
			if ((bb+1)*(1+rr)*rr/2 > R || (rr+1)*(1+bb)*bb/2 > B) {
				break;
			}
			for (int rr2=R; rr2>=rr; rr2--) {
				for (int bb2=B; bb2>=bb; bb2--) {
					rbmap[rr2][bb2] = max(rbmap[rr2][bb2], rbmap[rr2-rr][bb2-bb]+1);
				}
			}
		}
	}
}

int main() {
	int T, R, B;
	cin >> T;
	solve();
	for (int i=0; i<T; i++) {
		cin >> R >> B;
		int result = rbmap[R][B];
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}