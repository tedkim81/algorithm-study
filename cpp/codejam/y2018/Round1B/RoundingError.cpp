/**
 * 2019.2.27
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007764/0000000000036601
 *
 * 어떤 프로그래밍 언어가 최고인지 가려내기 위해서, 
 * N명의 사람들에게 가장 좋아하는 언어 하나씩을 말하도록 한다.
 * 설문을 마치고 나면, 어떤 언어 몇명, 또 어떤 언어 몇명 이런 식으로 정리가 될텐데, 
 * 이걸 언어별로 백분율로 기록하려고 한다.
 * 그런데, 백분율로 기록할때 소수점 첫째자리에서 반올림을 하여 자연수로 기록하려고 한단다.
 * (얘는 통계쪽이랑 안맞는듯..)
 * 이러면 당연히 각 언어별 백분율값들을 다 더했을때 100이 되지 않는 경우가 발생할 수 있을 것이다.
 * 현재 설문이 진행중인 상황, 몇명이 아직 설문에 응답을 안한 상태에서, 
 * 지금까지 각 언어별로 몇명이 좋다고 했는지를 알고 있다.
 * 나머지 사람들의 최애 언어를 모두 응답 받을때, 
 * 선택 받은 모든 언어의 백분율값을 모두 합한 값이 가질 수 있는 최대값은 얼마인가?
 * 즉, 원래는 100이 되어야 하는게 반올림 하면서 발생한 오차로 인해 최대 얼마가 될 수 있는가를 구해야 한다. 
 *
 * (input)
 * T: 테스트케이스의 수
 * N L: N은 설문에 참여하는 사람수, L은 현재까지 선택받은 언어의 수
 * L개의 정수(C[L]): 각 언어별로 현재까지 몇명의 선택을 받았는가
 *
 * (output)
 * 각 언어별 반올림한 백분율값들의 합의 최대값
 *
 * (solution 1)
 * 아직 설문에 응답하지 않은 사람 수, nn = N - (C[L]의 합)
 * nn을 잘 분산시켜서 반올림 이득을 최대한 많이 이끌어 내야 한다.
 * 이미 선택을 받은 L개의 언어, 또는 아직 선택받지 않은 언어에, 
 * 최소한의 선택을 추가하여 소수점 아래가 0.5 이상이 되도록 해야 한다.
 * L개의 언어를 탐색하여 각각에 얼마를 더해야 반올림이 되는지를 기록하고, 
 * 선택받지 않았던 경우에는 얼마를 더해야 반올림이 되는지 구한다.
 * 더해야 하는 수가 작은 것부터 순서대로 더하면서 nn 만큼을 더한다.
 * 자, 이제 슈도코드를 작성해 보자.
 * --------------------------
 * nn = N - (C[L]의 합)
 * cc[L] = 0 // 반올림 이득 얻기 위해 더해야 하는 수
 * for 0 <= i < L 탐색:
 *   cc[i] = [ (C[i] + x) / N * 100 이 반올림 이득을 얻게 하기 위한 x 최소값. 0 <= x <= nn+1 ]
 * ccc = [ x / N * 100 이 반올림 이득을 얻게 하기 위한 x 최소값. 0 <= x <= nn+1 ]
 * dd[L] = cc[L]을 오름차순으로 정렬하는 키값
 * for 0 <= i < L 탐색:
 *   if cc[dd[i]] > ccc or cc[dd[i]] > nn:
 *     break
 *   C[dd[i]] += cc[dd[i]]
 *   nn -= cc[dd[i]]
 * result = 0
 * for 0 <= i < L 탐색:
 *   result += round(C[i] / N * 100)
 * while nn > 0:
 *   result += round(min(ccc, nn) / N * 100)
 *   nn -= ccc
 * --------------------------
 * (코딩하는 과정에서 슈도코드와 살짝 달라진 부분이 있지만, 생각할 당시를 기록하기 위해 그냥 둠.)
 *
 * (solution 1 result)
 * 실패!! WA(Wrong Answer) 발생.. 
 * 문제에 있는 Sample에 대해서는 맞는 값들이 나오는데.. 어떤 입력일때 잘못된 것인지 알지 못하니 참 어렵다..
 * 코드를 확인하다보니 잘못 코딩한 부분이 있었다. 
 * x를 nn+1까지 반복했던 이유가 정렬시 뒤로 가게 해서 별도로 제외처리 시키기 위함이었는데,
 * 코딩 하다가 그걸 깜박하고, for loop 안에서 break 하기 전에 값을 세팅하도록 잘못 코딩했었다.
 * (이런 실수는 하지 말자 진짜..ㅠ.ㅠ)
 * 이 버그를 수정하고 다시 시도하니, Test set 1과 2는 성공하고, 
 * Test set 3는 TLE(Time Limit Exceeded)로 실패한다. 그래도 절반의 성공!
 * Test set 3에서 N은 최대 10만이고, 나의 알고리즘은 O(N^2)이고, 
 * 이는 100억정도로 너무 큰 값이라 시간내에 실행할 수 없었던 것이다.
 * 자, 알고리즘을 개선해 보자.
 *
 * (solution 2)
 * solution 1에서 이중 for loop 부분을 개선하여 반복을 줄이자.
 * C[i]가 엄청 큰 수일때 x가 0부터 시작하면 굉장히 비효율적이니, x의 초기값을 계산하고 시작하자.
 * C[i] 백분율값의 소수점 이하 부분값과 0.5의 차이만큼을 메꿀 수 있는 x값을 구하면 된다.
 * ---------------------
 * tmp = 1.0 / N * 100
 * aa = tmp - floor(tmp)
 * tmp = C[i] / N * 100
 * bb = 0.5 - (tmp - floor(tmp))
 * if bb > 0:
 *   x = floor(bb / aa)
 * else:
 *   x = 0
 * ---------------------
 *
 * (solution 2 result)
 * 성공!! 코딩하다가 아예 이중루프 구조가 되지 않도록 욕심 부렸다가 아까운 시간만 날렸다..;;
 * 알고리즘 문제를 풀때, 반복을 줄이는 것도 중요하지만 복잡하지 않게 하는게 더 중요하다는걸 새삼 느꼈다.
 * 어차피 100억번 도는게 문제지, 만번 도는거나 2만번 도는거는 큰 차이가 없는 것이다.
 * 2만번 도는게 코드가 훨씬 가독성이 좋고 심플하다면 이게 더 좋은 알고리즘일 수 있다.
 * 명심하자.
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <map>

using namespace std;

int N, L;
int C[100001];

int solve() {
	int nn = N;
	for (int i=0; i<L; i++) {
		nn -= C[i];
	}
	double tmp = 1.0 / N * 100;
	double aa = tmp - floor(tmp);
	if (aa == 0) {
		return 100;
	}
	multimap<int, int> cc;
	int x;
	for (int i=0; i<L; i++) {
		tmp = (double)C[i] / N * 100;
		double bb = 0.5 - (tmp - floor(tmp));
		if (bb > 0) {
			x = floor(bb / aa);
		} else {
			x = 0;
		}
		for (; x<=nn+1; x++) {
			double tmp = ((double)C[i] + x) / N * 100;
			if (floor(tmp) < round(tmp)) {
				break;
			}
		}
		cc.insert(make_pair(x, i));
	}
	for (x=0; x<=nn+1; x++) {
		double tmp = (double)x / N * 100;
		if (floor(tmp) < round(tmp)) {
			break;
		}
	}
	int ccc = x;
	
	for (multimap<int, int>::iterator i = cc.begin(); i != cc.end(); i++) {
		if (i->first > ccc || i->first > nn) {
			break;
		}
		C[i->second] += i->first;
		nn -= i->first;
	}
	int result = 0;
	for (int i=0; i<L; i++) {
		result += round((double)C[i] / N * 100);
	}
	if (ccc <= nn) {
		while (nn > 0) {
			result += round((double)min(ccc, nn) / N * 100);
			nn -= ccc;
		}
	} else {
		result += round((double)nn / N * 100);
	}
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N >> L;
		for (int j=0; j<L; j++) {
			cin >> C[j];
		}
		int result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}