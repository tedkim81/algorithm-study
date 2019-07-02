/**
 * 2018.12.28
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/00000000000000cb/00000000000079cb
 *
 * 어떤 알고리즘 연구소에서 버블소트에 영감을 받아 색다른(그리고 바보같은) 
 * 소팅 알고리즘을 개발했다. 앞에서부터 3개씩을 확인하여 가장 왼쪽값과 
 * 가장 오른쪽값을 비교해서 왼쪽값이 더 크면 자리를 바꾸는 방식이다.
 * 숫자의 개수가 N이고 위치를 i라고 하면, i는 0부터 N-3까지 탐색하면서 
 * i번째 값이 i+2번째 값보다 크면 바꾸는 것이다.
 * 그렇게 한바퀴 돌고 끝나는게 아니라, 
 * 한바퀴 돌때 값의 자리바꿈이 한번도 일어나지 않았으면 종료하고, 그렇지 않으면 계속 돌린다.
 * 그런데, 이 알고리즘에는 문제가 있음이 밝혀졌고, 디버깅 해봐야 하는 상황이 됐다.
 * 정렬해야할 숫자가 주어졌을때, 정렬이 가능한지, 불가능하다면 정렬한 결과의 어디가 잘못됐는지를 찾아보자.
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 정렬할 숫자의 개수
 * V: 정렬할 숫자들. 띄어쓰기로 구분됨.
 *
 * (output)
 * 정렬결과가 성공이면 OK, 실패면 뒷수보다 큰 숫자의 위치.
 *
 * (solution 1)
 * 짝수번째에 있는 숫자들(0번째 포함)과 홀수번째에 있는 숫자들은 서로 바뀔 수 없다.
 * 따라서 짝수번째에 있는 숫자들을 따로 정렬하고, 홀수번째에 있는 숫자들도 따로 정렬한 후, (C++ 라이브러리를 믿는다..)
 * 인접한 홀수번째 숫자와 짝수번째 숫자 중에서, 먼저 나열된 숫자인데 다음 숫자보다 큰 경우가 있다면,
 * 걔가 답이다!
 * 여기서 주의해야할 점 몇가지,
 * - V에는 최대 10자리 숫자가 10만개까지 올 수 있다. 1.1MB. cin으로 읽을때 문제 없는지 먼저 테스트해 보자.
 * - 짝수번째와 홀수번째를 구분했을때 각각의 총 개수가 다를 수 있다.(1개 차이 날 수 있다.)
 *
 * (solution 1 result)
 * 성공!! 
 * 처음에 algorithm을 include 안했다가 컴파일 에러로 실패했었다. 근데 로컬에서 테스트할때는 왜 동작했을까..?
 */

#include <stdio.h>
#include <iostream>
#include <string>
#include <math.h>
#include <algorithm>

using namespace std;

string solve(int N, int V[]) {
	int size_odd = N / 2;
	int size_even = N - size_odd;
	int v_odd[size_odd], v_even[size_even];
	for (int i=0; i<N; i++) {
		if (i%2 == 0) {
			v_even[i/2] = V[i];
		} else {
			v_odd[i/2] = V[i];
		}
	}
	sort(v_even, v_even+size_even);
	sort(v_odd, v_odd+size_odd);
	int wrong_idx = -1;
	for (int i=0; i<size_odd; i++) {
		if (v_even[i] > v_odd[i]) {
			wrong_idx = i*2;
			break;
		}
		if (i+1 < size_even && v_odd[i] > v_even[i+1]) {
			wrong_idx = i*2 + 1;
			break;
		}
	}
	if (wrong_idx == -1) {
		return "OK";
	}
	return to_string(wrong_idx);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int N;
		cin >> N;
		int V[N];
		for (int i=0; i<N; i++) {
			cin >> V[i];
		}
		string result = solve(N, V);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}
