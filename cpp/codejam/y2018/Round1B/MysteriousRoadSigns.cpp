/**
 * 2019.3.11
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007764/000000000003675b
 *
 * 서쪽에서 동쪽으로 일직선으로 쭉 뻗은 길이 있고, 길 중간중간에 표지판들이 있다.
 * 표지판들의 위치는 서쪽 시작점 기준으로 D1,D2,... 이다.
 * 표지판들은 양면에 숫자가 적혀 있는데, 
 * 한면은 서쪽방향을 보고 있고(서쪽에서 동쪽으로 이동할때 보이는 방향, A1,A2,...),
 * 반대편은 당연히 동쪽방향을 보고 있다(B1,B2,...).
 * 이 숫자가 뭘까 생각을 해보니, 
 * A는 그 위치에서 동쪽으로(진행방향) A만큼 떨어진 곳에 마을이 있고,
 * B는 그 위치에서 서쪽으로(반대방향) B만큼 떨어진 곳에 마을이 있을 것이라는 
 * 가설을 세웠단다. (누가? 문제 만든 친구가)
 * 가설을 검증하기 위해, 전체 표지판 집합에서 아래 조건을 만족하는 부분집합을 구해보려는데, 
 * 그 크기가 가장 클 경우 얼마인지, 그리고 그 최대 크기의 부분 집합은 몇개가 되는지를 구해보자.
 * 조건은,
 * - 부분집합은 연속된 표지판의 집합이다.(1,2,3,4,5 에서 2,3,4는 연속이지만 2,3,5는 연속이 아니다.)
 * - D+A=M, D-B=N. M지점과 N지점에 마을이 있음을 가리키는 표지판들의 (연속으로 나열된) 집합인데,
 *   A와 B 중 하나 이상만 맞는 값이면 된단다. 
 *   (둘다 맞는 경우만 인정하도록 하면 문제가 너무 쉬위질테니까..ㅎㅎ)
 * - M과 N은 양수던 음수던 상관없고, 서로 같은 값이어도 상관 없단다.
 *
 * (input)
 * T: 테스트케이스의 수
 * S: 표지판의 수
 * D A B (S행): 
 *   D는 서쪽 시작점 기준 표지판까지의 거리, 
 *   A는 표지판에서 서쪽 방향 바라보는 쪽에 적힌 숫자,
 *   B는 A의 반대편에 적힌 숫자
 *
 * (output)
 * [최대크기 부분집합의 크기] [해당 크기 부분집합이 몇개 나오나]
 *
 * (solution 1)
 * 표지판 자체는 S로 표현하자. S1은 D1 위치에 있고, A1과 B1 숫자가 적혀 있다.
 * 구하고자 하는 것은, 연속된 최대크기의 표지판 부분집합이다.
 * 그 부분집합을 안다고 치고, S1부터 순서대로 생각해 보자. 
 * S1은 그 부분집합에 포함이 되어 있을 수도 있고, 아닐 수도 있다.
 * S1을 포함 했다치고, S2부터 시작하는 부분집합의 크기를 구해보자.
 * S1을 포함 안했다치고, S2에서 SS 구간에서 가장 큰 부분집합 크기를 구해보자.
 * 그리고, 위 둘 중 큰놈이 winner가 된다. 
 * 최대크기 부분집합의 수는, 
 * 위의 탐색을 하면서 제일 큰놈 나왔을때 이전 큰놈과 같다면 +1, 더 크다면 리셋해서 다시 1.
 * 이런 식의 알고리즘이면 될 듯 하다. 이제 슈도코드 시~작!
 * ----------------------
 * max_size = 0
 * max_cnt = 0
 * int solve(int pos):
 *   if pos가 마지막 위치보다 크면?
 *     return 0
 *   S 포함 했다치고, l1 = get_length(pos)
 *   S 포함 안했다치고, l2 = solve(pos+1)
 *   m = max(l1, l2)
 *   if m == max_size:
 *     max_cnt++
 *   elif m > max_size:
 *     max_size = m
 *     max_cnt = 1
 *   return m
 * --------- or -----------
 * max_size = 0
 * max_cnt = 0
 * for 0 <= i < S:
 *   l = get_length(i)
 *   if l == max_size:
 *     max_cnt++
 *   elif l > max_size:
 *     max_size = l
 *     max_cnt = 1
 * ------------------------
 * int get_length(int pos):
 *   l = 0
 *   for pos <= i < S:
 *     if Si가 이전까지의 부분집합에 들어갈 수 있나?:
 *       l++
 *     else:
 *       break
 *   return l
 * ------------------------
 * solve function은 재귀보다 반복이 나은듯.
 * 암튼 여기서 문제는, 시간복잡도가 O(S^2)가 되어 S가 10만인 경우 실행할 수 없다는 것이다.
 * 따라서, get_length에 메모이제이션을 적용해야한다.
 * 일단, "Si가 이전까지의 부분집합에 들어갈 수 있나?" 부분을 구현해보자.
 * ...(한참을 삽질한 후)...
 * 메모이제이션을 어떻게 적용해야할지 방법을 찾지 못했다.. 
 * 부분문제화 하고 재귀적으로 함수를 호출하면서, 
 * 메모이제이션을 하려면 함수가 인자로 받은 값을 key로 하여 저장할 수 있어야 하는데,
 * "Si가 이전까지의 부분집합에 들어갈 수 있나?"를 구하려면 
 * 이전까지 만들어진 부분집합에 대한 정보가 있어야 하고, 
 * 이게 key로 쓰기에는 너무 복잡한 것이었던 것이었다.. 
 * 이번에도 너무 많은 시간을 사용했으므로, ANALYSIS를 컨닝하기로 결정..ㅠ.ㅠ
 * ANALYSIS를 읽어보니, 두가지 해법을 제시하고 있다.
 * 
 * 하나는 분할정복 알고리즘.
 * 중심의 S 하나를 기준으로 중심의 S를 포함하지 않고 
 * 그 좌측과 우측을 분할하여 재귀적으로 정복하는 방식이다.
 * 최대크기 부분집합은 좌측영역 안에 있을 수도 있고, 우측영역 안에 있을 수도 있고, 
 * 아니면 중심S를 포함하여 중심에 걸쳐져 있을 수 있다.
 * 따라서, 좌측과 우측 분할영역은 재귀적으로 탐색하고, 
 * 중심S가 포함된 부분집합을 찾아서 셋 중에 제일 큰게 winner가 되는 방식!
 * 분할정복의 시간복잡도는 O(S log S) 이므로 S가 10만인 경우도 실행 가능하다.
 * 좌우 탐색은 재귀호출이고, 이제 중심S를 포함한 부분집합 만드는 로직만 생각하면 된다.
 * 중심S에 대한 M이 맞는 값이라고 가정하면, 동쪽과 서쪽방향으로 M이 불일치할때까지 탐색하고, 
 * 불일치하는 동쪽의 S와 서쪽의 S에 대한 N값을 N1, N2라고 하면, 
 * (M, N1), (M, N2)에 대한 부분집합 중 큰걸 고르면 된다.
 * 그리고 중심S에 대해 N이 맞는 값이라고 가정하고 유사하게 한번더 찾아서 제일 큰 걸 찾아내면 된다.
 * 
 * ANALYSIS가 제시한 두번째 해법은 시간복잡도가 O(S) 이다.
 * (ANALYSIS에 나와 있는 그림 참고). 
 * 모든 S에 대해 M과 N을 미리 계산하여 저장해두고 얘들을 탐색한다.
 * (이는 분할정복에도 적용 가능)
 * i번째 S를 탐색중이라고 할때, 
 * M-candidate은 M이 맞다고 할때 N과 start와 xstart가 나오고,
 * N-candidate은 N이 맞다고 할때 M과 start와 xstart가 나온다. 
 * 그러면 xstart-start+1 이 부분집합의 크기가 되고, 
 * M-candidate과 N-candidate 두쪽중 큰걸 고르면 되는 방식이다.
 * 여기에서, S들을 탐색하면서 값들을 연결하고, start와 xstart를 갱신하는 방식을 이해하는 것이 중요한데,
 * 글로 써놓기가 좀 애매하다.. 
 * 간단하게 요약하면, 
 * M가 N 값들을 체인처럼 연결하고 적절히 끊어가면서 가장 긴 체인을 남기는 방식이라 할 수 있다.
 * M-candidate은 M값 기준으로 이전의 두 체인(M-candidate체인과 N-candidate체인) 중 하나를 선택하는데,
 * 같은 체인(M-candidate 체인)의 M값과 같으면 걔랑 연결하고, 
 * 그렇지 않으면 반대 체인(N-candidate 체인)과 연결한다.
 * N-candidate도 유사한 방식으로 이어간다. 
 * 그리고 체인을 연결할때 만약 M을 연결하는데 연결되는 체인에 다른 M이 붙어 있으면
 * 걔 밑으로는 다 잘라버린다.
 * 
 * 위의 두개의 해법 중 분할정복 알고리즘을 이용하여 문제를 풀어보자.
 * 분할정복 알고리즘을 사용하는 것은 수학문제를 풀때 일반적인 공식을 이용하여 푸는 느낌이고, 
 * 두번째 해법은 해당 문제에 특화되어 특별하게 발견된 느낌이 든다.
 * 문제 출제자는 분할정복 알고리즘을 생각하고 문제를 냈는데, 
 * 누군가가 "이렇게 하면 더 빨라요!"라고 해법을 제시했고,
 * 보아하니 맞네 싶어서 ANALYSIS에 추가로 실어준 듯한 느낌적인 느낌.
 * 암튼 현재는 알고리즘을 학습하고자 함이니 분할정복 알고리즘을 사용하여 정규적인 방식으로 풀어보자.
 * 자, 그럼 다시 슈도코드를 작성해 보자.
 * ------------------------------
 * int max_set_size = 0
 * int max_set_cnt = 0
 * int get_size(mid_idx, start, end, m, n):
 *   size = 1
 *   for mid_idx+1 <= i <= end:
 *     if D[i]+A[i] == m or D[i]-B[i] == n:
 *       size++
 *     else:
 *       break
 *   for mid_idx-1 >= i >= start:
 *     if D[i]+A[i] == m or D[i]-B[i] == n:
 *       size++
 *     else:
 *       break
 *   return size
 * 
 * int solve(start, end):
 *   if start == end:
 *     return 1
 *   if start+1 == end:
 *     return 2
 *   mid_idx = (start+end) / 2
 *   left_size = solve(start, mid_idx-1)
 *   right_size = solve(mid_idx+1, end)
 *   m = D[mid_idx] + A[mid_idx]
 *   for mid_idx+1 <= i <= end:
 *     if m != D[i] + A[i]:
 *       n1 = D[i] - B[i]
 *       break
 *   for mid_idx-1 >= i >= start:
 *     if m != D[i] + A[i]:
 *       n2 = D[i] - B[i]
 *       break
 *   n = D[mid_idx] - B[mid_idx]
 *   for mid_idx+1 <= i <= end:
 *     if n != D[i] - B[i]:
 *       m1 = D[i] + A[i]
 *       break
 *   for mid_idx-1 >= i >= start:
 *     if n != D[i] - B[i]:
 *       m2 = D[i] + A[i]
 *       break
 *   mid_size = max(
 *     get_size(mid_idx, start, end, m, n1), get_size(mid_idx, start, end, m, n2)
 *     , get_size(mid_idx, start, end, m1, n), get_size(mid_idx, start, end, m2, n)
 *   )
 *   max_size = max(left_size, right_size, mid_size)
 *   max_set_size와 max_set_cnt 업데이트
 *   return max_size
 * ---------------------------------
 *
 * (solution 1 result)
 * 성공!! ANALYSIS 컨닝한 주제에 그래도 코드를 서밋하고 결과를 기다릴때는 나름 쫄렸고, 
 * correct 판정 받았을때는 주먹을 불끈 쥐었다..;;
 * 이번에도 한가지 반성을 한 것이 있다. 문제가 한방에 풀리지 않는다 싶을때는 일단 Test set 1에 대해서만이라도
 * 해결할 수 있는 알고리즘을 완성해 보는 것이 중요하다는 것이다. 
 * 이번 문제에서는 무식하게라도 부분집합을 만드는 로직을 먼저 생각해 봤어야 했다. 이 부분을 "되겠거니"하고 냅두고
 * 메모이제이션을 생각해보다가 시간만 엄청나게 버린 것이다.
 * 명심하자!!
 */

#include <stdio.h>
#include <iostream>
#include <math.h>
#include <set>

using namespace std;

int S;
int D[100001];
int A[100001];
int B[100001];
int max_set_size;
set<int> max_set_set;

void update_max_set_info(int size, int set_start) {
	if (size > max_set_size) {
		max_set_size = size;
		max_set_set.clear();
		max_set_set.insert(set_start);
	} else if (size == max_set_size) {
		max_set_set.insert(set_start);
	}
}

int get_size(int mid_idx, int start, int end, int m, int n) {
	int size = 1;
	for (int i=mid_idx+1; i<=end; i++) {
		if (D[i]+A[i] == m || D[i]-B[i] == n) {
			size++;
		} else {
			break;
		}
	}
	int set_start = mid_idx;
	for (int i=mid_idx-1; i>=start; i--) {
		if (D[i]+A[i] == m || D[i]-B[i] == n) {
			size++;
			set_start--;
		} else {
			break;
		}
	}
	update_max_set_info(size, set_start);
	return size;
}

int solve(int start, int end) {
	if (start == end) {
		update_max_set_info(1, start);
		return 1;
	}
	if (start+1 == end) {
		update_max_set_info(2, start);
		return 2;
	}
	int mid_idx = (start+end) / 2;
	int left_size = solve(start, mid_idx-1);
	int right_size = solve(mid_idx+1, end);
	int m = D[mid_idx] + A[mid_idx];
	int i, n1, n2, m1, m2;
	for (i=mid_idx+1; i<=end; i++) {
		if (m != D[i] + A[i]) {
			n1 = D[i] - B[i];
			break;
		}
	}
	for (i=mid_idx-1; i>=start; i--) {
		if (m != D[i] + A[i]) {
			n2 = D[i] - B[i];
			break;
		}
	}
	int n = D[mid_idx] - B[mid_idx];
	for (i=mid_idx+1; i<=end; i++) {
		if (n != D[i] - B[i]) {
			m1 = D[i] + A[i];
			break;
		}
	}
	for (i=mid_idx-1; i>=start; i--) {
		if (n != D[i] - B[i]) {
			m2 = D[i] + A[i];
			break;
		}
	}
	int mid_size = max(
		max(get_size(mid_idx, start, end, m, n1), get_size(mid_idx, start, end, m, n2)),
		max(get_size(mid_idx, start, end, m1, n), get_size(mid_idx, start, end, m2, n))
	);
	return max(max(left_size, right_size), mid_size);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> S;
		for (int j=0; j<S; j++) {
			cin >> D[j] >> A[j] >> B[j];
		}
		max_set_size = 0;
		max_set_set.clear();
		int result = solve(0, S-1);
		cout << "Case #" << i+1 << ": " << max_set_size << " " << max_set_set.size() << endl;
	}
}