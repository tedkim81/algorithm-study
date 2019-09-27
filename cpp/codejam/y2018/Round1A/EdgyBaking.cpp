/**
 * 2019.2.9
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007883/000000000002fff7
 *
 * 제과사(과자 만드는 사람) 아저씨가 쿠키를 만드는데, 
 * 쿠키의 가장자리가 어느 정도 되느냐에 따라 쿠키 맛이 달라질 수 있기 때문에,
 * 현재 쿠키 반죽들을 오븐에 넣기 전에 가장자리의 총 길이가 원하는만큼이 되도록 
 * 만들고 싶단다.(과하다 과해..)
 * 원하는 가장자리 총 길이를 P라고 할때, 작업(?) 후 쿠키 가장자리의 총 길이가 P 이하이면서 가장 가까운 값이 되어야 한다.
 * 그리고 쿠키를 자를때는 정확히 2등분을 하기 위해 절단선이 쿠키의 중심을 지나야 한다.
 * 쿠키를 자르지 않아도 된다면 굳이 안잘라도 된다.
 * 그렇게 쿠키에 작업을 하고 나서 쿠키들의 가장자리 총 길이가 얼마가 되는지를 구하는 것이 문제.
 *
 * (input)
 * T: 테스트케이스의 수
 * N P: N은 쿠키의 수, P는 재과사가 원하는 쿠키들 가장자리의 총 길이
 * W H (N행): 각 쿠키들의 가로길이(Width)와 세로길이(Height)
 *
 * (output)
 * 쿠키들 자르기 작업 후의 가장자리 총 길이 (P와 같거나 가장 가까운 값)
 *
 * (solution 1)
 * 쿠키를 자르면 자른 단면의 길이의 2배 만큼이 쿠키 가장자리 총 길이에 더해진다.
 * l = 자르기 작업 전의 쿠키들의 가장자리 총 길이
 * P-l 만큼을, 자르기 작업을 통해 채우면 된다.
 * 쿠키를 크기 순으로 정렬하고, 큰 넘부터 자르기 작업 여부를 정하고 어떻게 자를지를 정한다.
 * 하나씩 자르면서 남은 길이 pp를 계산한다.(pp=P-l 에서 시작)
 * 쿠키 각각에 대해서, 잘랐을때 단면이 가장 짧을 때의 길이(d1)와 가장 길 때의 길이(d2)를 구하고,
 * if d1 <= pp <= d2, then P가 정답. pp = 0 실행 후 break.
 * if pp < d1, then 다음 작은 쿠키로 시도.
 * if pp > d2, then pp -= d2 실행 후 다음 작은 쿠키로 시도.
 * 모든 쿠키 시도후 P-pp 가 정답.
 *
 * (solution 1 result)
 * 실패! Wrong Answer 란다..
 * 생각해보니, solution 1 대로라면, 잘린 쿠키들 중 마지막 쿠키(잘린 것 중 제일 작은거)를 제외하고는 
 * 모두 대각선으로(d2로) 잘린게 된다.
 * 그런데 그렇지 않은 경우, 즉 잘린애들이 다 어중간하게 잘리는 경우가 있을 수 있으려나..? 생각해보니, 있다!! 
 * 쿠키 큰 놈 하나, 작은 놈 하나가 있을때 큰 놈을 대각선으로 자르고 나니 남은 pp가 작은 놈 d1보다 작다면? 
 * 작은 놈을 d1으로 놓고 큰 놈을 d1과 d2 사이로 절묘하게 잘라야겠지..
 * 어쩐지 배점이 높은데 왜이렇게 쉽나 했다..
 * 암튼 solution 1의 문제점은 확인되었다. 다시 생각해보자.
 *
 * (solution 2)
 * 만약, 크기가 다른 쿠키 A,B,C를 모두 자른다고 할때, 
 * 쿠키를 잘라서 추가되는 가장자리 길이의 최소값은 (A,B,C 짧은면 길이 합)이고, 
 * 최대값은 (A,B,C 대각선 길이 합)이 된다. 
 * 그리고, 저 최소값과 최대값 사이의 모든 값은 A,B,C를 적당히 잘라서 만들어낼 수 있다. (이게 중요한 부분이었다!) 
 * 따라서, 전체 쿠키들 중 어떤 쿠키들을 자를지 정하면 절단작업 후 pp에 가장 가까운 수는 쉽게 구할 수 있는 것이다.
 * (최소값 <= pp <= 최대값이면, 답은 P가 되고, pp > 최대값이면, 답은 P-(pp-최대값)이 된다.)
 * 자, 이제 어떤 쿠키들을 자를지만 정하면 되는데, 
 * 기본적으로는 각 쿠키마다 자를지 말지를 가정하여 깊이우선탐색을 하고, 최선의 값을 찾으면 된다.
 * 그러나, 쿠키가 100개인 경우 2의 100제곱만큼을 탐색해야 되는데 이는 너무 큰 수이므로, 
 * 동적프로그래밍 또는 가지치기를 통해 탐색을 줄여야 한다.
 * 슈도코드를 간단하게 써보자.
 * double solve(구간 시작값 r_min, 구간 크기 r_size, 쿠키인덱스 pos)
 *   pos가 마지막 쿠키인덱스보다 크면? return max(0, pp-(r_min+r_size))
 *   r_min <= pp <= r_min+r_size 인 경우? return 0
 *   메모이제이션. 가능한가?
 *   단면 길이 최소값 c_min = min(w, h)
 *   단면 길이 최대값 c_max = sqrt(w*w + h*h)
 *   pos 쿠키 잘랐다 치고, a = solve(r_min+c_min, r_size+(c_max-c_min), pos+1)
 *   a가 0이면? return 0
 *   pos 쿠키 안잘랐다 치고, b = solve(r_min, r_size, pos+1)
 *   return min(a, b)
 * 아.. 메모이제이션이 문제다. r_min, r_size, pos의 조합의 수가 너무 크다. 
 * r_min의 범위는 [0, 25000], r_size의 범위도 [0, 25000], pos의 범위는 [0, 100]. 25000*25000*100=625억
 * 시간을 너무 지체했다. 문제 풀이 컨닝하고 solution 3를 다시 작성하자.
 *
 * (solution 3)
 * 어떤 쿠키들을 잘라야 할지는 알 필요가 없고, 최선의 값이 무엇인지만 구하면 된다.
 * 그리고 쿠키를 잘라서 추가되는 길이에 대한 "구간"을 만들어서 
 * pp를 포함하거나 가장 가까운 구간을 찾아서 답을 구하는 방식은 맞는 방식이다.
 * 다만 어떤 쿠키를 자를지에 대해 모든 경우를 탐색하는 것은, 직관적이기는 하지만 실행 불가능하다.
 * 그래서, 모든 쿠키를 탐색하고 각 쿠키를 자를 경우와 자르지 않을 경우를 고려하되, 
 * 경우의 수가 기하급수적으로 증가하지 않는 방법을 찾아야 한다.
 * 쿠키를 하나씩 탐색하면서 자를 경우와 자르지 않을 경우에 대해 "구간들"을 만들어서, 가능한 구간들의 집합을 구한다면?
 * 그 집합안에 있는 구간 내의 숫자는, 
 * 쿠키들을 잘라서(어떤 쿠키들을 잘랐는지는 알 수 없지만) 만들어 낼 수 있는 숫자라는 걸 보장할 수 있다.
 * 그렇다면 pp를 포함하는 구간을 찾거나 구간 최대값이 pp에 제일 가까이 있는 구간을 찾아서 답을 구할 수 있다.
 * 쿠키들이 3개 있고, 각각 잘랐을 때 단면 길이 (최소값, 최대값)이 (1,2), (2,3), (3,4)라고 가정해 보자.
 * 구간은 [0,0]으로 시작. 1번 쿠키 안자를수도 있고 자를 수도 있으므로 구간은 [0,0] or [1,2]
 * 2번 쿠키 확인 후 구간은 [0,0] or [1,2] or [2,3] or [3,5]. 합칠 수 있는거 합치면, [0,0] or [1,5]
 * 3번 쿠키 확인 후 구간은 [0,0] or [1,5] or [3,4] or [4,9]. 합칠 수 있는거 합치면, [0,0] or [1,9]
 * 따라서 최종 구간은 [0,0] or [1,9]
 * 이 방식이 가능한 이유는, 발생 가능한 모든 구간들은 결국 [0, 50000] 구간 안에 있고, 겹치는 애들은 merge 되므로,
 * 쪼개져 있는 구간들은 아무리 많아도 50000을 넘을 수 없기 때문.
 * 슈도코드 작성해 보면,
 * q = 구간들을 담을 큐. 구간은 pair 자료형에 담자.
 * q.push([0,0])
 * for 쿠키 하나씩 탐색
 *   c_min = min(w, h), c_max = sqrt(w*w + h*h)
 *   q2 = q를 온전히 탐색하기 위해 새로 생기는 구간들은 q2에 담았다가 나중에 q로 바꾸는 방식으로 하자.
 *   while q 탐색. r = q.pop()
 *     r2 = [r.0+c_min, r.1+c_max]
 *     if r과 r2 merge 가능? then, r2=merge(r,r2). q2에 r2와 merge할 수 있는 구간들 merge시킨후 q2에 추가
 *     else, r과 r2 모두 q2에 merge할 수 있는 구간들 merge시킨후 q2에 추가
 *   q = q2
 *
 * (solution 3 result)
 * 됐다! 성공! 얘도 참 오래걸렸다..ㅠ.ㅠ 이래 가지고 어느 세월에 다 풀지..
 * 이번 문제에서 헤맨 이유는, 일단 첫째가 처음에 문제 풀이 방식을 잘못 생각해서 solution 1을 실패한 거고,
 * 그리고 나서 문제를 제대로 파악한 후 생각한 solution 2에서 메모이제이션을 할 방법을 생각해내지 못했기 때문이다.
 * 그래서 답지를 봤더니 거기에는 solution 3에 해당하는 아이디어를 설명해 주고 있었다.
 * solution 2를 기반으로 메모이제이션 방식을 더 생각해보고 싶지만, 너무 여기에만 매달리면 너무 비효율적일 듯 하니
 * 추후 다시 생각해보는게 좋을 듯 하다. 다른 여러 문제를 풀고 돌아와서 다시 이 문제를 보면 쉬울 수도 있으니 ㅎㅎ
 * 암튼 이 문제를 풀기 위해서는 solution 2 기반으로 메모이제이션 방법을 잘 생각해 냈거나, 아니면
 * solution 3에서의 구간들 집합을 merge 해가면서 만들어내는 아이디어를 생각해 냈어야 했다.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <math.h>
#include <queue>

using namespace std;

int N;
double P;
int W[100], H[100];

bool can_merge(pair<double, double> p1, pair<double, double> p2) {
	return !(max(p1.first, p1.second) < min(p2.first, p2.second) || max(p2.first, p2.second) < min(p1.first, p1.second));
}

pair<double, double> do_merge(pair<double, double> p1, pair<double, double> p2) {
	return make_pair(min(p1.first, p2.first), max(p1.second, p2.second));
}

queue< pair<double, double> > queue_with_merge(queue< pair<double, double> > q, pair<double, double> p) {
	queue< pair<double, double> > q2;
	while (!q.empty()) {
		pair<double, double> r = q.front();
		q.pop();
		if (can_merge(p, r)) {
			p = do_merge(p, r);
		} else {
			q2.push(r);
		}
	}
	q2.push(p);
	return q2;
}

double solve() {
	queue< pair<double, double> > q;
	q.push(make_pair(0, 0));
	int l = 0;
	for (int i=0; i<N; i++) {
		l += (W[i] + H[i]) * 2;
		double c_min = min(W[i], H[i]) * 2;
		double c_max = sqrt(W[i]*W[i] + H[i]*H[i]) * 2;
		queue< pair<double, double> > q2;
		while (!q.empty()) {
			pair<double, double> r = q.front();
			q.pop();
			pair<double, double> r2 = make_pair(r.first+c_min, r.second+c_max);
			if (can_merge(r, r2)) {
				q2 = queue_with_merge(q2, do_merge(r, r2));
			} else {
				q2 = queue_with_merge(q2, r);
				q2 = queue_with_merge(q2, r2);
			}
		}
		q = q2;
	}
	double pp = P - l;
	double result = 987654321;
	double tmp;
	while (!q.empty()) {
		pair<double, double> r = q.front();
		q.pop();
		if (pp >= r.first) {
			if (pp <= r.second) {
				result = 0;
				break;
			} else {
				result = min(result, pp-r.second);
			}
		}
	}
	return P - result;
}

int main() {
	int T;
	cin >> T;
	cout.precision(10);
	for (int i=0; i<T; i++) {
		cin >> N >> P;
		for (int j=0; j<N; j++) {
			cin >> W[j] >> H[j];
		}
		double result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}