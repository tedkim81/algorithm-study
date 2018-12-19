/**
 * 2018.12.18
 *
 * 화장실에 N+2개의 칸이 있는데 양쪽끝은 항상 점유되어 있고 N개의 칸이 비어 있는 상황.
 * K명의 사람이 화장실의 각 칸에 순서대로 들어가는데, 좌우로 최대한 여유있게 들어가고 싶어한다.
 * S번째 칸에 대해서 Ls는 좌측으로 빈칸수, Rs는 우측으로 빈칸수라 할때, 
 * min(Ls,Rs)가 최대인 칸을 선택하는데, 같은 값인 칸이 있으면 max(Ls,Rs)가 큰 값으로 정하고, 
 * 그것도 같으면 가장 왼쪽에 있는 칸을 선택한다.
 * 위의 규칙대로 할때, 마지막 K번째 사람의 max(Ls,Rs)와 min(Ls,Rs)을 구하기.
 *
 * (input)
 * T: 테스트케이스의 수
 * N K: N은 화장실 칸 수, K는 사람 수
 *
 * (output)
 * 마지막 K번째 사람의 max(Ls,Rs)와 min(Ls,Rs)
 *
 * (solution 1은 BathroomStalls.cpp 에 구현되어 있고, solution 2는 완전히 다른 접근이라 따로 작성함.)
 *
 * (solution 2)
 * 문제에서 요구하는 것은 K번째 사람이 칸을 선택했을때 max와 min이 얼마냐이다.
 * 즉, 어디인지는 중요하지 않다는 것이다! 속았다..
 * 그리고 어차피 계속 반씩 가르기 게임이므로 K번째에서의 구간의 크기는 쉽게 계산할 수 있다. (단지 어디인지만 찾기 어려울뿐)
 * K에서 1을 빼고, 2를 빼고, 4를 빼고, 8을 빼고,.. K가 0 이하가 될때까지 반복한다면, 
 * 10의 18제곱일때 대략 60번 정도만 반복하면 된다.(헐..)
 * 반복이 실행되면서 구간의 크기를 줄이면 되는데, 현재의 구간크기를 l이라고 한다면, 
 * 다음 구간크기는 floor((l-1)/2)와 ceil((l-1)/2) 이 된다.
 * a=1로 시작하여 K -= a; a *= 2; 가 반복 실행될텐데, 이제 중요한 건 a > K 가 되는 마지막 순간.
 * 마지막 단계에 구간의 크기는 어떤 값 x이거나 x+1이다. (x+1의 구간의 갯수 >= K) 여부를 알 수 있어야 한다.
 * 반복문 내에서, 구간 쪼개기후 쪼개진 구간의 크기를 l1과 l2로, 그리고 l1과 l2의 갯수를 l1_cnt와 l2_cnt로 계속 구하자. 
 *
 * (solution 2 result)
 * 성공!! 하지만.. solve 함수를 작성하고 디버깅 하는데에 너무 많은 시간이 걸렸다.(2~3시간 정도?)
 * 위의 solution 2를 작성해 놓고, solve 함수를 처음 코딩하는데에도 상당한 시간이 걸렸고, 
 * 샘플 케이스에 대한 답이 맞지 않아서 디버깅 하는데에도 상당한 시간이 걸렸다.
 * 설계가 모호한 상태에서, "코딩하면서 풀리겠지"라는 생각으로 너무 성급하게 코딩을 시작한게 문제였었던 것 같다.
 * 앞으로는 무조건 손코딩을 먼저 하고 코딩을 시작하자.
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <string>
#include <queue>
#include <algorithm>

using namespace std;

string solve(unsigned long long N, unsigned long long K) {
	long long a = 1;
	long long l1 = N;
	long long l2 = 0;
	long long l1_cnt = 1;
	long long l2_cnt = 0;
	while (K > a) {
		bool l1_is_even = (l1 % 2 == 0);
		bool l2_is_even = (l2 % 2 == 0);
		long long l2_cnt_addend = 0;
		l1 = (l1 - 1) / 2;
		if (l1_is_even) {
			l2_cnt_addend = l1_cnt;
		} else {
			l1_cnt *= 2;
		}
		l2 = l1 + 1;
		if (a > 1) {
			if (l2_is_even) {
				l1_cnt += l2_cnt;
			} else {
				l2_cnt = l2_cnt * 2 + l2_cnt_addend;
			}
		} else {
			l2_cnt = l2_cnt_addend;
		}
		K -= a;
		a *= 2;
		// cout << l1 << "," << l2 << "," << l1_cnt << "," << l2_cnt << endl;
	}
	long long max, min, l;
	if (l2_cnt >= K) {
		l = l2;
	} else {
		l = l1;
	}
	min = (l - 1) / 2;
	if (l % 2 == 0) {
		max = min + 1;
	} else {
		max = min;
	}
	string result = to_string(max) + " " + to_string(min);
	return result;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		long long N, K;
		cin >> N >> K;
		string result = solve(N, K);
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}