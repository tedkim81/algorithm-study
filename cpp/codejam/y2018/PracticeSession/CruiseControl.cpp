/**
 * 2018.12.17
 * 
 * 제목인 크루즈컨트롤은 자동차의 정속주행 기능을 의미한다.
 * 애니가 말을 타고 0에서 D까지 이동하는데, 아래의 제한사항이 있어서 마음대로 못달린다. 
 * 정속으로 목적지까지 멈추지 않고 달리려면 속도를 얼마로 해야할까?
 * 제한사항: N마리의 말이 앞에서 달리고 있는데 모든 말은 서로를 추월하지 못하고, 
 *         빠른 말이 느린 말과 속도를 맞춰서 같이 달릴수만 있다.
 *
 * (input)
 * T: 테스트케이스의 수
 * D N: D는 목적지 위치, N은 말의 수
 * (N줄의) K S: K는 해당 말의 처음 위치, S는 최고(처음) 속도
 *
 * (output)
 * 애니가 정속으로 말을 타면서 다른 말을 추월하지 않기 위한 최고 속도를 출력한다.
 *
 * (solution 1)
 * N마리의 말들이 D지점에 도착하는데 걸리는 시간 중 최대값을 구하고, 그 시간과 거리 D를 이용해 속도를 구한다.
 * 어차피 가장 느리게 도착하는 말을 찾는 것이니, 느린 말 때문에 속도를 줄이게 되는 말들은 무시해도 된다.
 *
 * (solution 1 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <string>
#include <iomanip>

using namespace std;

double solve(int D, int N, int K[], int S[]) {
	double max_hour = 0;
	for (int i=0; i<N; i++) {
		double hour = (D-K[i]) / (double)S[i];
		if (hour > max_hour) {
			max_hour = hour;
		}
	}
	return D / max_hour;
}

int main() {
	cout << setprecision(12);
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int D, N;
		cin >> D >> N;
		int K[N], S[N];
		for (int j=0; j<N; j++) {
			cin >> K[j] >> S[j];
		}
		double result = solve(D, N, K, S);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}