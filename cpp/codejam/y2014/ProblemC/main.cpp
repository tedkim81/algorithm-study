#include <stdio.h>
#include <vector>
#include <iterator>
#include <algorithm>
#include <set>
#include <string>
#include <iostream>
#include <list>
#include <map>
#include <deque>
#include <stack>
#include <bitset>
#include <functional>
#include <numeric>
#include <utility>
#include <sstream>
#include <iomanip>

using namespace std;

char ans[55][55];
int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemC/ProblemC/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemC/ProblemC/out2", "w", stdout);
    
    int cas, r, c, m, pos, v;
    scanf("%d", &cas);
    for (int ii=0; ii<cas; ii++) {
        scanf("%d%d%d", &r, &c, &m);
        memset(ans, 0, sizeof(ans));
        pos = 1;
        if (r == 1) {
            for(int i=0;i<m;i++)ans[0][i]='*';
            for(int i=m;i<c;i++)ans[0][i]='.';
            ans[0][c-1] = 'c';
        } else if (c==1) {
            for(int i=0;i<m;i++)ans[i][0]='*';
            for(int i=m;i<r;i++)ans[i][0]='.';
            ans[r-1][0] = 'c';
        } else if (m == r * c - 1) {
            for(int i=0; i<r; i++) for(int j=0; j<c; j++)ans[i][j] = i==0&&j==0?'c':'*';
        } else if (r == 2) {
            if (r*c-m == 2 || m%2!=0) pos = 0;
            else{
                for(int i=0; i<r; i++) for(int j=0; j<c; j++)ans[i][j] = j<m/2?'*':'.';
                ans[0][c-1] = 'c';
            }
        } else if (c == 2) {
            if (r*c-m == 2 || m%2!=0) pos = 0;
            else {
                for(int i=0; i<r; i++) for(int j=0; j<c; j++)ans[i][j] = i<m/2?'*':'.';
                ans[r-1][0] = 'c';
            }
        } else {
            v = r*c - m;
            if (v == 2 || v == 3 || v == 5 || v == 7) pos = 0;
            else{
                if (v / c <= 2) {
                    for(int i=0; i<r; i++) for(int j=0; j<c; j++)
                        ans[i][j] = i<3 && j*3+i<v?'.':'*';
                    if (v%3 == 1) {ans[1][v/3] = '.'; ans[2][v/3-1] = '*';}
                    ans[0][0] = 'c';
                } else {
                    for(int i=0; i<r; i++) for(int j=0; j<c; j++)
                        ans[i][j] = i*c+j<v?'.':'*';
                    if (v % c == 1) {
                        ans[v/c][1] = '.'; ans[v/c-1][c-1] = '*';
                    }
                    ans[0][0] = 'c';
                }
            }
        }
        if (pos) {
            printf("Case #%d:\n", ii+1);
            for(int i=0; i<r; i++) puts(ans[i]);
        } else {
            printf("Case #%d:\nImpossible\n", ii+1);
            
        }
    }
    return 0;
}

// 내 코드
//bool play(int r, int c, int remain);
//
//int R, C, M;
//char board[51][51];
//bool checked[51][51];
//
//int main() {
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemC/ProblemC/in", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemC/ProblemC/out", "w", stdout);
//    
//    int T;
//    scanf("%d", &T);
//    
//    for (int tt=1; tt<=T; tt++) {
//        printf("case #%d:\n", tt);
//        scanf("%d %d %d", &R, &C, &M);
//        memset(board, 0, sizeof(board));
//        memset(checked, 0, sizeof(checked));
//
//        for (int i=0; i<R; i++) {
//            for (int j=0; j<C; j++) {
//                board[i][j] = '*';
//            }
//        }
//        int remain = R*C;
//        bool success = false;
//        for (int i=0; i<R; i++) {
//            for (int j=0; j<C; j++) {
//                if (play(i, j, remain)) {
//                    success = true;
//                    board[i][j] = 'c';
//                    break;
//                }
//            }
//            if (success) {
//                break;
//            }
//        }
//        if (success) {
//            for (int i=0; i<R; i++) {
//                for (int j=0; j<C; j++) {
//                    printf("%c", board[i][j]);
//                }
//                printf("\n");
//            }
//        } else {
//            printf("Impossible\n");
//        }
//    }
//    
//    return 0;
//}
//
//bool play(int r, int c, int remain) {
//    checked[r][c] = true;
//    char tmpmap[3][3];
//    memset(tmpmap, 0, sizeof(tmpmap));
//    for (int dr=-1; dr<=1; dr++) {
//        for (int dc=-1; dc<=1; dc++) {
//            int nr = r+dr;
//            int nc = c+dc;
//            if (nr>=0 && nr<R && nc>=0 && nc<C) {
//                if (board[nr][nc] == '*') {
//                    remain--;
//                }
//                tmpmap[dr+1][dc+1] = board[nr][nc];
//                board[nr][nc] = '.';
//            }
//        }
//    }
//    if (remain == M) {
//        return true;
//    } else if (remain > M) {
//        for (int dr=-1; dr<=1; dr++) {
//            for (int dc=-1; dc<=1; dc++) {
//                int nr = r+dr;
//                int nc = c+dc;
//                if (nr>=0 && nr<R && nc>=0 && nc<C
//                    && !checked[nr][nc] && play(nr, nc, remain)) {
//                
//                    return true;
//                }
//            }
//        }
//    }
//    for (int dr=-1; dr<=1; dr++) {
//        for (int dc=-1; dc<=1; dc++) {
//            int nr = r+dr;
//            int nc = c+dc;
//            if (nr>=0 && nr<R && nc>=0 && nc<C) {
//                board[nr][nc] = tmpmap[dr+1][dc+1];
//            }
//        }
//    }
//    checked[r][c] = false;
//    return false;
//}
