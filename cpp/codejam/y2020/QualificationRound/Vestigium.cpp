/**
 * 2020.4.4
 *
 * 정사각 행렬이 주어지고, 
 * 거기에서 좌상-우하로 이어지는 대각선 경로로 탐색한 값의 합과,
 * 반복되는 값이 있는 행의 수와 반복되는 값이 있는 열의 수를 
 * 출력하는 문제이다.
 * (라틴스퀘어는 행과 열에 값이 반복되지 않는 행렬을 의미한다.)
 *
 * (input)
 * T: 테스트케이스의 수
 * N: 행렬의 크기(NxN)
 * N행 N열이고 1<=M<=N 인 M값들
 *
 * (output)
 * k r c
 * k: 대각선 경로 값의 합
 * r: 중복된 값이 있는 행의 수
 * c: 중복된 값이 있는 열의 수
 *
 * (solution 1)
 * 문제의 조건 그대로 탐색하자.
 * 대각선 경로 합 구하고, 행과 열 각각 탐색하면서 중복값 있는지 구하자.
 * 중복값 있는지는, set을 이용해서 구하자.
 *
 * (solution 1 result)
 * 성공!!
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <set>

using namespace std;

int N;
int M[100][100];
int k, r, c;

void solve() {
	k = 0;
	for (int i=0; i<N; i++) {
		k += M[i][i];
	}
	r = 0;
	c = 0;
	set<int> rset;
	set<int> cset;
	for (int i=0; i<N; i++) {
		rset.clear();
		cset.clear();
		for (int j=0; j<N; j++) {
			rset.insert(M[i][j]);
			cset.insert(M[j][i]);
		}
		if (rset.size() != N) {
			r++;
		}
		if (cset.size() != N) {
			c++;
		}
	}
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N;
		for (int ii=0; ii<N; ii++) {
			for (int jj=0; jj<N; jj++) {
				cin >> M[ii][jj];
			}
		}
		solve();
		cout << "Case #" << i+1 << ": " << k << " " << r << " " << c << endl;
	}
}