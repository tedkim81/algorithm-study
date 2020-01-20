/**
 * 2019.4.2
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007765/000000000003e0a8
 *
 * N마리의 개미가 있고, 공중에서 먹이로 유인했더니 
 * 개미들이 서로가 서로를 밟고 올라가 먹이를 따내고자 한다.
 * 개미는 한줄로 올라가고(개미 위로 한마리 아래로 한마리), 
 * 위로는 자신의 무게의 6배까지 버틸 수 있다.
 * 그리고 더 길이가 긴 개미가 아래쪽에 있어야 한다.
 * 그렇다면, N마리의 개미의 무게가 주어지고, 길이 순서대로 줄서있는 상황에서, 
 * 최적의 조합으로 개미탑이 쌓아졌을때, 최대 몇마리가 될 수 있나?
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 개미의 수
 * W[0],...,W[N-1]: 각 개미의 무게. 개미의 몸 길이 오름차순으로 나열됨.
 *
 * (output)
 * 개미탑의 최대 크기(개미 몇마리?)
 *
 * (solution 1)
 * 일단은 심플하게 먼저 생각해보자.
 * 실제로 개미는 아래에서부터 위로 쌓여올라가겠지만, 
 * 반대로 위에서부터 생각하는게 알고리즘 작성하기가 수월하다.
 * 길이가 짧은 개미(먼저 입력받은 개미)부터 선택하거나 하지 않거나를 결정하면서 탐색하고, 
 * 선택된 개미의 수가 가장 많을때를 찾자.
 * 그런데 이 방식은 복잡도가 O(2의 N제곱) 이기 때문에, 
 * N이 100인 Test set 1도 실행할 수가 없다.
 * 그렇다면 두가지 관점에서 더 생각해보자. 
 * 선택을 덜하기(가지치기), 그리고 메모이제이션.
 * - 탐색 중에, 그때까지의 개미의 무게합이 현재 개미 무게의 6배보다 크면 
 *   자연히 걔는 선택할 수가 없다.
 * - Test set 1에 대하여, W 최대값이 1000이므로 
 *   이전 개미의 무게합이 6000을 넘으면 더 탐색할 필요가 없다.
 * - 6 1 1 1 1 1 1 1 의 경우에는 6을 선택하지 않아야 하고, 
 *   6 1 2 의 경우에는 6을 선택해야 한다. 
 * 
 * 아! 한놈씩 선택할지 말지를 판단하는 방식은 아무래도 어려워 보인다. 
 * 그렇다면, 일단 다 선택을 해서 위에서부터 거꾸로 만들어가다가
 * 더 이어갈 수 없을때, 뺄놈 빼고 더할놈 더해서(여기는 좀더 고민 필요) 
 * 다이어트를 시킨 후 다시 이어가도록 하면 어떨까?
 * 특정 시점에 [투입된 개미들],[남은 개미들] 두 집단이 있을때, 
 * 투입된 개미들의 구체적인 구성은 중요하지 않다.
 * 투입된 개미들의 전체 무게가 얼마냐가 중요하다. 
 * 그 전체무게를 wsum이라 할때, 남은 개미 중 다음 개미의 무게가 wsum/6 이상이라면
 * 투입 가능한 것이고, 투입 불가하다면, 
 * "투입된 개미들" 중에서 "다음 개미"보다 무거운 개미를 빼고 "다음 개미"를 투입시킬 수 있다면
 * 전체적으로 "투입된 개미들"은 다이어트를 한 것이 된다. 몇가지 전제해 보자.
 * - "투입된 개미들"에서 임의의 개미들을 제외하는 것이 가능하다.
 * - "다음 개미" 입장에서 "투입된 개미들"의 구성은 중요하지 않고(알 필요도 없고) 무게의 합만 중요하다.
 * - 두마리 이상 빼고 그 이상 추가되는 경우를 신경써야 할까? => 아.. 신경써야 한다..;;
 *
 * 뭔가 문제가 풀릴듯 풀릴듯 풀리지 않아서 ANALYSIS를 컨닝하기로 했다..ㅠ.ㅠ;;
 *
 * (solution 2)
 * ANALYSIS에는 Test set 1에 대한 풀이와 Test set 2에 대한 풀이가 구분되어 있다.
 * 당연히 Test set 2의 풀이는 Test set 1도 커버한다. 
 * Test set 1 풀이가 Test set 2 풀이에 비해 좀더 직관적이라 생각해내기 쉽다.
 * Test set 1 풀이와 Test set 2 풀이 각각에 대하여 코딩을 각각 작성했고, 
 * 설명은 각 코드에 주석으로 작성했다.
 * 둘 모두, 문제를 부분문제화 해서 함수를 재귀적으로 작성하고, 
 * 동적 프로그래밍(DP. 메모이제이션 사용)을 사용하는데,
 * 함수의 역할을 어떻게 정의하느냐가 다르다.
 *
 * (solution 2 result)
 * 성공.. 문제를 제대로 이해하고 DP를 쓸 수 있도록 부분문제화 하는 연습을 더해야 한다.. 
 */

/**
 * ANALYSIS의 Test set 1 컨닝하여 알고리즘 작성
 *
 * int solve(int nn, int mm) 함수에서, 
 * nn은 0번째 개미부터 nn번째 개미까지가 이 함수의 후보가 됨을 의미하고, 
 * mm은 후보 개미들 중 대상 개미들의 무게 합의 최대값을 의미한다.
 * 이렇게 nn과 mm이 주어졌을때, 대상 개미들은 최대 몇마리가 될 수 있는가를 return 해야 한다.
 * 그러면, solve(N-1, 충분히 큰값) 을 호출하면, 
 * 답(전체 개미를 후보로 했을때 대상 개미수의 최대값)을 return 해준다.
 * 
 * mm을 "최대값"이라는 모호한 개념으로 지정한 이유는, 
 * 맨 아래 개미의 위에 있는 개미들의 무게합이 맨 아래 개미 무게의 6배 이하가 되어야 한다는 
 * 문제의 조건 때문에, 맨 아래 개미를 제외하면서 부분문제화 할 때 
 * "남은 애들은 이 무게를 넘으면 안된다"는 조건이 생기기 때문이다.
 *
 * 후보 개미들 중 맨 아래 개미가 "정답 집합"에 포함이 될까 안될까를 각각 가정하여 
 * 부분문제를 재귀적으로 호출하고 최대값을 구하는 방식이다.
 * nn은 0~99 이고, mm은 0~100000 이고, 
 * nn*mm은 1000만보다 작은 수이므로 메모이제이션이 가능하다.
 */
// #include <stdio.h>
// #include <iostream>
// #include <algorithm>
// #include <map>
// #include <unordered_map>

// using namespace std;

// int N;
// int W[100000];
// unordered_map<long long, int> cache;

// int solve(int nn, int mm) {
// 	if (mm == 0) {
// 		return 0;
// 	}
// 	if (nn == 0) {
// 		if (W[nn] <= mm) {
// 			return 1;
// 		} else {
// 			return 0;
// 		}
// 	}
// 	long long cache_key = (nn * 1000000) + mm;
// 	if (cache.find(cache_key) != cache.end()) {
// 		return cache[cache_key];
// 	}
// 	int result = solve(nn-1, mm);
// 	if (mm >= W[nn]) {
// 		result = max(result, solve(nn-1, min(mm-W[nn], W[nn]*6))+1);
// 	}
// 	cache[cache_key] = result;
// 	return result;
// }

// int main() {
// 	int T;
// 	cin >> T;
// 	for (int i=0; i<T; i++) {
// 		cin >> N;
// 		for (int j=0; j<N; j++) {
// 			cin >> W[j];
// 		}
// 		cache.clear();
// 		int result = solve(N-1, 100000);
// 		cout << "Case #" << i+1 << ": " << result << endl;
// 	}
// }

/**
 * 결과값(cnt)은 최대 얼마까지 될 수 있을까? => 계산해보니 139
 */
// #include <stdio.h>
// #include <iostream>
// #include <math.h>

// using namespace std;

// int main() {
// 	int cnt = 0;
// 	long long weight = 1;
// 	long long sum = 0;
// 	for (int i=0; i<100000; i++) {
// 		if (sum <= weight*6) {
// 			cnt++;
// 			sum += weight;
// 		} else {
// 			weight = ceil(sum / 6.0);
// 		}
// 		if (weight > 1000000000) {
// 			break;
// 		}
// 	}
// 	cout << "cnt:" << cnt << ", last_weight:" << weight << endl;
// }

/**
 * ANALYSIS의 Test set 2 컨닝하여 알고리즘 작성
 *
 * long long solve(int nn, int mm) 의 정의가 Test set 1 풀이때와는 다르다.
 * mm자리에 무게가 들어가면 Test set 2에서는 너무 큰값이 되어 버리기 때문에 
 * DP를 쓸 수 없기 때문.
 * nn은, 1번째부터 nn번째까지가 후보가 됨을 의미하고, 
 * (0번째가 아니라 1번째부터 시작. mm과 비교할 수 있도록 하기 위해.)
 * mm은, 후보들 중 대상이 몇마리가 되어야 할지를 지정하는 값이고,
 * solve는, 대상 개미들의 무게합의 최소값을 return 한다.
 *
 * Test set 1 풀이와 유사하게, 
 * 맨 아래 개미가 "정답 집합"에 포함이 될까 안될까를 각각 가정하여 
 * 부분문제를 재귀적으로 호출하는 방식인데, 여기서 중요한 차이점은, 
 * solve의 return값이 답이 되는 것이 아니라,
 * mm에 들어갈 수 있는 최대값이 답이 된다는 것이다. 
 * mm에 답보다 큰수가 들어온 경우, 
 * mm마리의 개미로 탑을 쌓는 것이 불가능하므로 solve는 -1을 리턴한다.
 * 즉, 답이 될 수 있는 최대값은 139니까, 
 * mm에 139부터 차례로 시도하여 solve가 -1이 아닌 값을 리턴할때의 
 * mm값이 답이 되는 것이다.
 */
#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int N;
long long W[100000];
long long cache[100001][140];

long long solve(int nn, int mm) {
	if (mm == 0) {
		return 0;
	}
	if (nn == 1) {
		return W[0];
	}
	if (cache[nn][mm] != 0) {
		return cache[nn][mm];
	}
	long long result = -1;
	if (nn > mm) {
		result = solve(nn-1, mm);
	}
	long long tmp = solve(nn-1, mm-1);
	if (tmp >= 0 && tmp <= W[nn-1]*6) {
		if (result == -1 || result > tmp + W[nn-1]) {
			result = tmp + W[nn-1];
		}
	}
	cache[nn][mm] = result;
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int j=0; j<N; j++) {
			cin >> W[j];
		}
		for (int ii=0; ii<100001; ii++) {
			for (int jj=0; jj<140; jj++) {
				cache[ii][jj] = 0;
			}
		}
		int result = min(N, 139);
		while (result > 0) {
			if (solve(N, result) >= 0) {
				break;
			}
			result--;
		}
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}