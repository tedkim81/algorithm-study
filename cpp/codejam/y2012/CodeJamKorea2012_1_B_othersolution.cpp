#include <stdio.h>
#include <string.h>
#include <math.h>
#include <vector>
#include <string>
#include <queue>
#include <stack>
#include <set>
#include <map>
using namespace std;

int T,C,N,M,K,S2,S3,S4;
char B[111][111];
int dx[8] = {0,1,1,1,0,-1,-1,-1};
int dy[8] = {1,1,0,-1,-1,-1,0,1};
double ans = 0, gat[4][4], all[4];

int main()
{
	freopen ("input.txt","r",stdin);
	freopen ("output.txt","w",stdout);

	int i,j,k,l,d,x,y;

	scanf ("%d",&T); while (T--){
		scanf ("%d %d %d %d %d %d",&N,&M,&K,&S4,&S3,&S2); ans = 0;
		for (i=0;i<N;i++) scanf ("%s",B[i]);

		for (k=1;k<=K;k++){
			for (i=0;i<N;i++) for (j=0;j<M;j++){
				if (B[i][j] == (k + '0') || B[i][j] == '?'){
					vector<double> P[8]; double p;

					for (d=0;d<8;d++){
						P[d].clear();
						x = i; y = j; p = 1; P[d].push_back(1);
						for (l=1;l<4;l++){
							x += dx[d]; y += dy[d];
							if (x < 0 || y < 0 || x >= N || y >= M ||
								!(B[x][y] == (k + '0') || B[x][y] == '?')){
								P[d].push_back(0); break;
							}
							if (B[x][y] == '?'){
								P[d][l-1] *= (K - 1.) / K;
								p *= 1. / K;
							}
							else P[d][l-1] = 0;
							P[d].push_back(p);
						}
					}

					for (d=0;d<8;d++){
						while (P[d].size() <= 4) P[d].push_back(0);
					}

					double point = 0;
					for (d=0;d<4;d++) for (l=0;l<4;l++) gat[d][l] = 0;
					for (d=0;d<4;d++){
						for (x=0;x<4;x++) for (y=0;y<4;y++){
							int c = x + y + 1;
							if (c >= 4) gat[d][3] += P[d][x] * P[d+4][y];
							if (c == 3) gat[d][2] += P[d][x] * P[d+4][y];
							if (c == 2) gat[d][1] += P[d][x] * P[d+4][y];
							if (c <= 1) gat[d][0] += P[d][x] * P[d+4][y];
						}
					}
					for (l=0;l<4;l++) all[l] = 1;
					for (d=0;d<4;d++){
						all[2] = all[2] * (gat[d][0] + gat[d][1] + gat[d][2]);
						all[1] = all[1] * (gat[d][1] + gat[d][0]);
						all[0] = all[0] * gat[d][0];
					}
					all[3] -= all[2];
					all[2] -= all[1];
					all[1] -= all[0];

					if (B[i][j] == '?') p = 1. / K;
					else p = 1;

					point = all[1] * S2 + all[2] * S3 + all[3] * S4;
					ans += point * p;
				}
			}
		}

		printf ("Case #%d: %.7lf\n",++C,ans);
	}

	return 0;
}