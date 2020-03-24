/**
 * 2020.3.11
 *
 * 어떤 안무가가 자기 커리어 N년 기념일을 맞이하여 댄스파티를 연다.
 * N x N 격자 모양의 무대에서 한칸에 한명씩 댄서가 춤을 출 예정인데
 * 댄서들을 위한 옷도 N가지 색상으로 준비했다. 소재도 울 또는 면 두가지다.
 * 댄서들은 모두 개성을 중시하는 사람들이라, 자신과 같은 행과 열에 
 * 같은 옷(같은 색 && 같은 소재)을 입은 사람이 있는걸 싫어한단다.
 * 자 이제 댄서들이 무대에 섰다. 입은 옷들을 보고 댄서들이 모두 행복할 수 있도록
 * 최소한의 댄서들의 옷을 갈아입히려 할때 몇명의 댄서의 옷을 갈아입혀야 할까?
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 격자 크기. 옷의 색상 수.
 * N행 N열의 수(-N<=C<=N, C!=0): 
 *   격자 무대에 서있는 댄서들이 입고 있는 옷의 색상
 *   음수면 울, 양수면 면
 *
 * (output)
 * 임의의 댄서에 대해, 댄서와 동일한 행과 열에 각각 서로 다른 옷을 입고 있도록 하기 위해
 * 몇명의 댄서의 옷을 갈아입혀야 할지에 대한 최소값
 *
 * (solution 1)
 * 노트에 5x5 격자모양을 그리고 같은옷이라 불행한 댄서들을 임의로 정해서 표시해 봤다.
 * 최초 같은 옷을 입고 있던 댄서들을 불행한 댄서라 정의한다.
 * 그리고 몇가지 생각해 볼 문제를 찾았다.
 * - 불행한 댄서들 중 일부만 옷을 갈아입으면 될까?
 *   불행하지 않았던 댄서들 중에서 옷을 갈아입는 일이 있을 수 있나..?
 * - 옷을 갈아입힐지 말지만 정하고, 무엇으로 갈아입힐지는 무시해도 될 듯.
 *   옷의 종류는 N*2, 행과 열 칸의 수는 N*2-1. 옷의 종류가 충분하다.
 * 아래 가설이 맞다면 위의 가설도 맞다. 일단은 맞다고 가정하고 이어나가 보자.
 * - 불행한 댄서들을 먼저 다 찾는다.
 * - 좌측상단부터 차례대로 탐색하면서 갈아입혀야 하는지를 확인하고,
 *   갈아입힐지 말지 각각에 대해 탐색한다.
 * 그렇다면 실행시간이, 불행한 댄서의 수를 A라 할때 O(2^A)가 되어
 * test set 1은 풀 수 있지만, test set 2는 풀 수 없게 된다.
 * - N x N 배열에, 각각의 댄서 입장에서 같은 옷을 입고 있는 댄서의 수를 기록한다.
 * - 그 수가 가장 큰 댄서들부터 한명씩 옷을 갈아입히고 위의 배열을 갱신한다.
 * - 배열의 모든 값이 0이 될때까지 반복하면 끝.
 * - 문제는, 그 수가 같을때 누구부터 갈아입힐지 순서가 결과에 영향을 미칠 것인가인데,
 *   일단은 미치지 않는다고 가정하고 문제를 풀어보자.
 * 이제 슈도코드를 작성해 보자.
 * -------------------------------------------
 * int N
 * int C[100][100] // 댄서들이 현재 입고 있는 옷들
 * int D[100][100] // D[i][j]는 (i,j) 기준으로 i행 j열에 같은 옷의 수
 * int dd = 0 // D[][]의 최대값
 * int ee = 0 // 옷을 갈아입힌 횟수
 * 
 * for 0 <= i < N:
 *   for 0 <= j < N:
 *     for 0 <= k < N:
 *       if C[i][k] == C[i][j]:
 *         D[i][j]++
 *       if C[k][j] == C[i][j]:
 *         D[i][j]++
 *     dd = max(dd, D[i][j])
 *
 * while dd > 0:
 *   for 0 <= i < N:
 *     for 0 <= j < N:
 *       if D[i][j] == dd:
 *         D[i][j] = 0
 *         ee++
 *         for 0 <= k < N:
 *           if D[i][k] > 0:
 *             D[i][k]--
 *           if D[k][j] > 0:
 *             D[k][j]--
 *   dd--
 * return ee
 * -------------------------------------------
 *
 * (solution 1 result)
 * 쳇.. 실패.. Wrong Answer.
 * 아.. 한방에 될 것 같은 느낌이었는데 아쉽다.
 * N=3이고 싹다 같은 옷을 입은 경우, 답은 6이 되어야 하는데 7이 나온다.
 * D[][]의 가장 큰 수부터 갈아입히면 될 것이라 생각했지만, 아니었다.
 * 다시 생각해보자.
 *
 * (solution 2)
 * 일단 test set 1만 생각하면, 위의 알고리즘을 조금만 변형하면 된다.
 * D의 모든 값이 0이 될때까지 탐색하는건 같지만,
 * 가장 큰 수부터 차근차근 없애 나가는 것이 아니라,
 * 없앨지 말지의 모든 경우를 탐색하여 최소값을 구하면 된다.
 * 하지만 test set 2는 그렇게 해결할 수가 없다.
 * ...
 * 한참의 고민 끝에 몇가지를 발견했다.
 * - 옷을 갈아입지 않아도 되는 댄서들의 최대값을 구하는 방향으로 풀자.
 * - 여러 옷이 섞여있는 상태가 아니라, 각각의 옷에 대해 따로 풀자.
 * 예를 들어, 무대에 1번옷을 입은 댄서가 있다면,
 * 1번옷 입은 댄서들만 있다고 가정하고, 각각의 댄서에 대하여 
 * 자신이 포함된 행과 열에 다른 댄서가 없도록 만들어 주면 되는 것이다.
 * 그렇게 될 수 있는 여러 경우 중에 댄서의 수가 가장 많을때의 수를 구하면 되고,
 * 2번옷, 3번옷,... 모든 옷에 대해 각각 그 최대값을 구해서 모두 더한 값이
 * "옷을 갈아입지 않아도 되는 댄서들의 최대값"이 되는 것이다.
 * 그리고 이 문제는, "이분그래프에서의 최대매칭 구하기" 문제의 응용판임을 알 수 있다.
 * 1번옷에 대해 이분그래프를 만들어보면,
 * 모든 행 번호를 좌측 정점으로, 모든 열 번호를 우측 정점으로 셋팅하고,
 * 1번옷 입은 모든 댄서에 대하여, 위치가 (row,col)이라면, 
 * 좌측의 row 정점과 우측의 col 정점을 간선으로 이어주면 이분그래프 완성.
 * 이 그래프에서 하나의 정점에 2개 이상의 간선이 있으면 
 * 이건 옷을 갈아입어야 하는 댄서들이 있음을 의미하는 것이다.
 * 그래서 정점들이 간선을 하나씩만 갖는 "최대매칭"을 구하면,
 * 그게 옷을 갈아입지 않아도 되는 1번옷 댄서의 수의 최대값이 되는 것이다.
 * 슈도코드 생략. BipartiteGraph 클래스 작성하고 바로 문제를 풀어보자.
 *
 * (solution 2 result)
 * 성공!!
 * known 알고리즘을 문제에 적절히 활용하는 능력.. 
 * 이건 그저 훈련(?)만이 답이라는 생각이 든다..
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <vector>
#include <set>
#include <map>

using namespace std;

int N;
int C[100][100];

class BipartiteGraph {
public:
	vector<set<int> > graph;
	map<int,int> matched;
	set<int> visited;

	BipartiteGraph(vector<set<int> > g) {
		graph = g;
	}

	bool can_match(int i) {
		set<int> s = graph[i];
		for (set<int>::iterator it = s.begin(); it != s.end(); it++) {
			if (visited.count(*it) > 0) {
				continue;
			}
			visited.insert(*it);
			if (matched.count(*it) == 0 || can_match(matched[*it])) {
				matched[*it] = i;
				return true;
			}
		}
		return false;
	}

	int maximum_match_cnt() {
		matched.clear();
		int match_cnt = 0;
		for (int i=0; i<graph.size(); i++) {
			visited.clear();
			if (can_match(i)) {
				match_cnt++;
			}
		}
		return match_cnt;
	}
};

int solve() {
	map<int, vector<set<int> > > graph_map;
	for (int i=0; i<N; i++) {
		for (int j=0; j<N; j++) {
			if (graph_map.count(C[i][j]) == 0) {
				vector<set<int> > v;
				for (int k=0; k<N; k++) {
					set<int> s;
					v.push_back(s);
				}
				graph_map[C[i][j]] = v;
			}
			graph_map[C[i][j]][i].insert(j);
		}
	}
	int match_cnt = 0;
	for (map<int, vector<set<int> > >::iterator i = graph_map.begin(); i != graph_map.end(); i++) {
		match_cnt += BipartiteGraph(i->second).maximum_match_cnt();
	}
	return N*N - match_cnt;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int j=0; j<N; j++) {
			for (int k=0; k<N; k++) {
				cin >> C[j][k];
			}
		}
		int result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}