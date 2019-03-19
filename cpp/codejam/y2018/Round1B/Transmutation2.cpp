/**
 * Transmutation.cpp이 Test set 3를 통과하지 못해서, 성공한 코드를 찾아서 참고용으로 확인함.
 * http://kmjp.hatenablog.jp/entry/2018/05/02/1100
 */

#include <stdio.h>
#include <iostream>
#include <math.h>

using namespace std;

int M;
int R[101][2];
long long G[101];
long long A[101];
long long lp[101];

int dfs(int cur, long long need) {
	if(lp[cur]) return 0;
	
	if(A[cur]>=need) {
		A[cur]-=need;
		return 1;
	}
	
	need-=A[cur];
	A[cur]=0;
	lp[cur]=1;
	
	if(dfs(R[cur][0],need) && dfs(R[cur][1],need)) {
		lp[cur]=0;
		return 1;
	}
	return 0;
	
}


int ok(long long v) {
	int i;
	for (int i=0; i<M; i++) {
		A[i]=G[i];
	}
	fill(lp, lp+101, 0);
	
	return dfs(0,v);
}

void solve(int TC) {
	int i,j,k,l,r,x,y; string s;
	
	cin>>M;
	for (i=0; i<M; i++) {
		cin>>R[i][0]>>R[i][1];
		R[i][0]--;
		R[i][1]--;
	}
	for (i=0; i<M; i++) cin>>G[i];
	
	long long ret=0;
	for(int i=40;i>=0;i--) if(ok(ret+(1LL<<i))) ret+=1LL<<i;
	
	cout << "Case #" << TC << ": " << ret << endl;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		solve(i+1);
	}
}