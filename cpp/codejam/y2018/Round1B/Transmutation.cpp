/**
 * 2019.3.16
 *
 * 금속 중에 납을 제일 좋아하는 왕이, 금속을 막 바꿀 수 있는 연금술사에게, 최대한 많은 납을 만들어내라고 요구했다.
 * 총 M 종류의 금속이 있고, 각 금속은 다른 금속 두가지를 1그램씩 써서 1그램을 만들 수 있는 공식을 가지고 있다.
 * 현재 M 종류의 금속이 각각 몇 그램씩 있는지 알고 있고, 이 중 첫번째는 납이다.
 * 연금술사가 마음껏 지지고 볶아서 최선을 다해 납을 만들어 낸다고 할때, 납은 최대 몇그램이 될 수 있나? 
 * #이분법
 *
 * (input)
 * T: 테스트케이스의 수
 * M: 금속의 종류의 수
 * R1 R2 (M행): i번째 행에 대하여, G[i]를 1그램 만들기 위해 G[R1[i]] 1그램과 G[R2[i]] 1그램이 필요하다.
 * G[M] (M개의 자연수): 현재 M종류의 금속이 각각 몇그램씩 있는가. 
 *
 * (output)
 * 납은 최대 몇 그램이 될 수 있나.
 *
 * (solution 1)
 * 납을 1그램씩 차근차근 만들어보자. 납을 만들기 위해 필요한 두가지 금속의 G값을 확인해서 둘다 0보다 크면 G[1]++ 하고,
 * 두 금속은 G[R1[1]]-- , G[R2[1]]-- 한다. (R1,R2가 1부터 시작하므로 배열의 시작도 1로 시작하도록 저장하자.)
 * G[R1[1]] 또는 G[R2[1]]가 0이면 해당 금속을 만들기 위해 필요한 두 금속을 탐색해서 만들 수 있으면 만든다.
 * 이런식으로 반복탐색해서 더이상 납을 만들 수 없을때까지 실행한다.
 * 슈도코드를 대강 작성해 보자.
 * ----------------------------
 * bool can_trans(int gg): // gg는 금속번호. gg를 만들수 있는지 여부 true or false 리턴.
 *   if G[gg] == -1 or G[R1[gg]] == -1 or G[R2[gg]] == -1:  // 무한반복을 막기 위한 장치.
 *     return false
 *   tmp = G[gg]
 *   G[gg] = -1
 *   if G[R1[gg]] == 0:
 *     if can_trans(R1[gg]) == false:
 *       G[gg] = tmp
 *       return false
 *   if G[R2[gg]] == 0:
 *     if can_trans(R2[gg]) == false:
 *       G[gg] = tmp
 *       return false
 *   if G[R1[gg]] == 0 or G[R2[gg]] == 0:
 *     G[gg] = tmp
 *     return false
 *   G[R1[gg]]--
 *   G[R2[gg]]--
 *   G[gg] = tmp + 1
 *   return true
 *
 * while(can_trans(1)) {}
 * print G[1]
 * ----------------------------
 * 위의 알고리즘의 시간 복잡도는, 반복이 최대 G, 탐색 깊이가 M 정도니까, O(G*M).
 * Test set 1과 2는 해결이 될 듯 한데, Test set 3는 G가 10억이라 손을 더 봐야 한다.
 * 위 알고리즘에서 G가 커서 부담되는 이유는 G를 1씩만 증가시키고 있기 때문이니, 
 * can_trans를 만들수 있나없나가 아니라 몇개를 만들 수 있나로 변경하면 문제가 해결될 것으로 보인다.
 * 위 슈도코드를 변형해 보자.
 * ----------------------------
 * int can_trans(int gg): // gg는 금속번호. gg를 몇개 만들수 있는지를 리턴.
 *   if G[gg] == -1 or G[R1[gg]] == -1 or G[R2[gg]] == -1:  // 무한반복을 막기 위한 장치.
 *     return 0
 *   tmp = G[gg]
 *   G[gg] = -1
 *   if G[R1[gg]] == 0:
 *     if can_trans(R1[gg]) == 0:
 *       G[gg] = tmp
 *       return 0
 *   if G[R2[gg]] == 0:
 *     if can_trans(R2[gg]) == 0:
 *       G[gg] = tmp
 *       return 0
 *   // sample의 case2 에서 발견된 문제로, can_trans(R2[gg]) 때문에 
 *   // G[R1[gg]] 가 영향을 받는 경우가 있으므로, 반씩만 사용하도록 한다.
 *   cnt = ceil(min(G[R1[gg]], G[R2[gg]]) / 2.0)
 *   G[R1[gg]] -= cnt
 *   G[R2[gg]] -= cnt
 *   G[gg] = tmp + cnt
 *   return cnt
 *
 * while(can_trans(1) > 0) {}
 * print G[1]
 * ----------------------------
 *
 * (solution 1 result)
 * 실패.. WA(Wrong Answer) 판정을 받았다..
 * 알고리즘 자체의 문제인지 확인해 보기 위해 cnt를 1 또는 0이 되도록 제한하여 테스트해 봤지만 마찬가지였다.
 *
 * (solution 2)
 * Test set 1만 깨자는 생각으로 solution 1의 최초 알고리즘으로 최대한 심플하게 다시 생각해 보자.
 * solution 1 알고리즘은 상단의 종료 조건과 중간에 R1, R2에 대해 can_trans 호출할때 상호 간섭이 일어나는 문제에 있어서
 * 뭔가 찜찜함이 있었다. 여기를 다시 생각해 보자.
 * ----------------------------
 * bool can_trans(int gg): // gg는 금속번호. gg를 만들수 있는지 여부 true or false 리턴.
 *   if G[gg] == -1:  // 무한반복을 막기 위한 장치.
 *     return false
 *   if G[gg] > 0:
 *     G[gg]--
 *     return true
 *   tmp = G[gg]
 *   G[gg] = -1
 *   result = can_trans(R1[gg]) and can_trans(R2[gg])
 *   G[gg] = tmp
 *   return result
 *
 * result = 0
 * while(can_trans(1)) {
 *   result++
 * }
 * print result
 * ----------------------------
 * 뭐야.. 이렇게 바꾸니까 Test set 1과 2를 그냥 통과하고, Test set 3는 당연히 TLE(Time Limit Error)가 발생했다.
 * 좋아 이걸 다시 "gg 몇개 만들 수 있는지" 형태로 변형해 보자.
 * ----------------------------
 * int can_trans(int gg): // gg는 금속번호. gg를 몇개 만들수 있는지 리턴.
 *   if G[gg] == -1:  // 무한반복을 막기 위한 장치.
 *     return 0
 *   if G[gg] > 0:
 *     cnt = ceil(G[gg] / 2.0)
 *     G[gg] -= cnt
 *     return cnt
 *   G[gg] = -1
 *   result = min(can_trans(R1[gg]), can_trans(R2[gg]))
 *   G[gg] = 0
 *   return result
 * ----------------------------
 * 아.. 이렇게 바꾸고 테스트를 해보니 심각한 문제가 하나 있다..
 * gg에 대해 R1과 R2 각각 몇개씩 필요한지 가지고 오는데 그 수가 다를 경우 문제가 된다.
 * R1 2개, R2 3개 만들었으면, gg를 2개 만들 수 있는건데, 그러면 남는 R2 1개는? 얘가 문제인거다..
 * 이래저래 생각해봐도.. 얘는 맞는 전략이 아닌 듯 하다. 다른 방법을 생각해 보자.
 * ......
 * 몇개인지를 구해오는건 양쪽에서 받은게 값이 다를때 그 값을 같게 맞추려고 롤백을 하는게 안된다.
 * 그렇다면 답을 미리 가정하고 이게 되나 안되나를 거꾸로 탐색하면서 검증하는 식으로 하면 어떨까?
 * 납이 aa 그램이라고 가정할때 aa가 가능한지 탐색하는 방법을 생각해보자.
 * G[1]은 이미 있는 납의 양이니까 (aa - G[1])만큼을 추가로 만들기 위해 R1, R2를 탐색해서,
 * 얘들도 원래 있는 만큼은 빼고 남은 양을 가지고 또 그 다음 금속들을 탐색하는 식으로 하면 될 듯.
 * 동일한 금속을 두번 탐색하는 일 없이 탐색해서 탐색이 정상적으로 완료되면 aa 이상의 납을 구할 수 있는거고,
 * 동일한 금속을 두번 탬색하게 되면 aa 이상의 납을 구할 수 없는거다.
 * aa를 포함하는 법위의 최소값과 최대값을 구해서 이분법으로 구현하면 답이 나올듯!
 * ----------------------------
 * bool can_trans(int gg, int aa): // gg는 금속번호. aa는 납의 최종중량 예측치. 납이 aa만큼 될 수 있나를 리턴.
 *   if G[gg] == -1:
 *     return false
 *   if G[gg] >= aa:
 *     G[gg] -= aa
 *     return true
 *   aa -= G[gg]
 *   G[gg] = -1
 *   result = can_trans(R1[gg], aa) and can_trans(R2[gg], aa)
 *   G[gg] = 0
 *   return result
 *
 * hi = G의 총합
 * lo = G[1]
 * while hi-lo > 1:
 *   mid = (hi + lo) / 2
 *   if can_trans(1, mid):
 *     lo = mid
 *   else:
 *     hi = mid
 * if can_trans(1, hi):
 *   return hi
 * return lo
 * ----------------------------
 *
 * (solution 2 result)
 * 실패.. Test set 1과 2는 통과하는데, Test set 3에서 WA(Wrong Answer)가 뜬다. 성공할줄 알았는데..ㅠ.ㅠ
 * ANALYSIS를 보면 뭔가 맞게 접근한 것 같기는 한데, 설명이 자세하게 나와있지 않아서 내가 뭘 놓쳤는지를 못찾겠다.
 * 성공한 사람의 코드를 구해서 살펴보자.
 * Transmutation2.cpp 는 인터넷 검색해서 구한 코드를 실행가능하도록 손을 좀 본 것이다.
 * 읽어보니.. 약간의 차이가 있기는 하지만 알고리즘 자체는 거의 같다.
 * 뭐지..? 한참을 삽질하다가 우연히 발견한 한 문장..
 * int result = solve();
 * 아.. solve()는 long long 을 리턴하는데, int로 받고 있었다.. 이런 #$!@#%!@#%ㄲ!@#%!@
 * 이 부분 수정하고 다시 시도해보니, 성공..
 * Test set 1,2가 통과하는데 3만 통과하지 못한다는게 내심 이상하기는 했었다.
 * long long 타입을 적용해야할 부분이 누락된 곳이 없는지 더욱 세심하게 찾아봤어야 했다..
 */

#include <stdio.h>
#include <iostream>
#include <math.h>

using namespace std;

int M, R1[102], R2[102];
long long G[102];

bool can_trans(int gg, long long aa) {
	if (G[gg] == -1) {
		return false;
	}
	if (G[gg] >= aa) {
		G[gg] -= aa;
		return true;
	}
	aa -= G[gg];
	G[gg] = -1;
	bool result = can_trans(R1[gg], aa) && can_trans(R2[gg], aa);
	G[gg] = 0;
	return result;
}

long long solve() {
	long long G2[102];
	copy(begin(G), end(G), begin(G2));
	long long hi = 0;
	for (int i=0; i<M; i++) {
		hi += G[i+1];
	}
	long long lo = G[1];
	while (hi-lo > 1) {
		long long mid = (hi + lo) / 2;
		copy(begin(G2), end(G2), begin(G));
		if (can_trans(1, mid)) {
			lo = mid;
		} else {
			hi = mid;
		}
	}
	if (can_trans(1, hi)) {
		return hi;
	}
	return lo;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> M;
		for (int j=0; j<M; j++) {
			cin >> R1[j+1] >> R2[j+1];
		}
		for (int j=0; j<M; j++) {
			cin >> G[j+1];
		}
		long long result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}