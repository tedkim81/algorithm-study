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

int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemD/ProblemD/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemD/ProblemD/out", "w", stdout);
    
    int T;
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("case #%d: ", tt);
        
        int X, R, C;
        scanf("%d %d %d", &X, &R, &C);
        
        bool winRichard = false;
        
//        if (X < 7) {
            if (X > max(R, C)) {
                winRichard = true;
            } else {
                int x1 = X / 2;
                int x2 = X - x1;
                if (min(x1, x2)+1 > min(R, C)) {
                    winRichard = true;
                }
            }
//        } else {
//            winRichard = true;
//        }
        
        if (winRichard) {
            printf("RICHARD\n");
        } else {
            printf("GABRIEL\n");
        }
    }
    
    return 0;
}
