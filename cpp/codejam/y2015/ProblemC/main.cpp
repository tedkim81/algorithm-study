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

char opijk(char c1, char c2);
char getijk(int pos);

int T;
long L, X;
char ijks[11000];

int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemC/ProblemC/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemC/ProblemC/out", "w", stdout);
    
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("case #%d: ", tt);
        
        scanf("%ld %ld", &L, &X);
        long xx = X % 4;
        
        memset(ijks, 0, sizeof(ijks));
        scanf("%s", ijks);
        
        char ii = '+';
        char jj = '+';
        char kk = '+';
        char ll = '+';
        for (int l=0; l<L; l++) {
            ll = opijk(ll, ijks[l]);
        }
        long totalsize = L*X;
        bool result = false;
        for (int i=0; i<4*L; i++) {
            if (i >= totalsize) {
                break;
            }
            ii = opijk(ii, getijk(i));
            if (ii == 'i') {
                for (int j=i+1; j<i+1+(4*L); j++) {
                    if (j >= totalsize) {
                        break;
                    }
                    jj = opijk(jj, getijk(j));
                    if (jj == 'j') {
                        for (int k=(j%L)+1; k<L; k++) {
                            kk = opijk(kk, getijk(k));
                        }
                        int a = (j/L) + 1;
                        while (a > xx) {
                            xx += 4;
                        }
                        if (xx > X) {
                            break;
                        }
                        char kk2 = '+';
                        for (int b=0; b<xx-a; b++) {
                            kk2 = opijk(kk2, ll);
                        }
                        kk = opijk(kk, kk2);
                        if (kk == 'k') {
                            result = true;
                            break;
                        }
                    }
                }
            }
            if (result) {
                break;
            }
        }
        
        if (result) {
            printf("YES\n");
        } else {
            printf("NO\n");
        }
    }
    
    return 0;
}

// 1=+, -1=-, -i=I, -j=J, -k=K
char opijk(char c1, char c2) {
    if (c1 == '+') {
        return c2;
    } else if (c2 == '+') {
        return c1;
    } else if (c1 == '-') {
        if (c2 == 'i') return 'I';
        else if (c2 == 'j') return 'J';
        else if (c2 == 'k') return 'K';
        else if (c2 == 'I') return 'i';
        else if (c2 == 'J') return 'j';
        else if (c2 == 'K') return 'k';
        else return '+';
    } else if (c1 == 'i') {
        if (c2 == 'i') return '-';
        else if (c2 == 'j') return 'k';
        else if (c2 == 'k') return 'J';
        else if (c2 == 'I') return '+';
        else if (c2 == 'J') return 'K';
        else if (c2 == 'K') return 'j';
        else return 'I';
    } else if (c1 == 'j') {
        if (c2 == 'i') return 'K';
        else if (c2 == 'j') return '-';
        else if (c2 == 'k') return 'i';
        else if (c2 == 'I') return 'k';
        else if (c2 == 'J') return '+';
        else if (c2 == 'K') return 'I';
        else return 'J';
    } else if (c1 == 'k') {
        if (c2 == 'i') return 'j';
        else if (c2 == 'j') return 'I';
        else if (c2 == 'k') return '-';
        else if (c2 == 'I') return 'J';
        else if (c2 == 'J') return 'i';
        else if (c2 == 'K') return '+';
        else return 'K';
    } else if (c1 == 'I') {
        if (c2 == 'i') return '+';
        else if (c2 == 'j') return 'K';
        else if (c2 == 'k') return 'j';
        else if (c2 == 'I') return '-';
        else if (c2 == 'J') return 'k';
        else if (c2 == 'K') return 'J';
        else return 'i';
    } else if (c1 == 'J') {
        if (c2 == 'i') return 'k';
        else if (c2 == 'j') return '+';
        else if (c2 == 'k') return 'I';
        else if (c2 == 'I') return 'K';
        else if (c2 == 'J') return '-';
        else if (c2 == 'K') return 'i';
        else return 'j';
    } else {
        if (c2 == 'i') return 'J';
        else if (c2 == 'j') return 'i';
        else if (c2 == 'k') return '+';
        else if (c2 == 'I') return 'j';
        else if (c2 == 'J') return 'I';
        else if (c2 == 'K') return '-';
        else return 'k';
    }
}

char getijk(int pos) {
    pos = pos % L;
    return ijks[pos];
}
