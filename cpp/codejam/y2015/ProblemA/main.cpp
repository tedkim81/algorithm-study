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
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemA/ProblemA/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemA/ProblemA/out", "w", stdout);
    
    int T;
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("case #%d: ", tt);
        
        int Smax;
        scanf("%d", &Smax);
        
        int S[2000];
        memset(S, 0, sizeof(S));
        for (int i=0; i<=Smax; i++) {
            scanf("%1d", &S[i]);
        }
        
        int sum = 0;
        int result = 0;
        for (int i=0; i<=Smax; i++) {
            if (sum < i) {
                result += (i-sum);
                sum = i;
            }
            sum += S[i];
        }
        
        printf("%d\n", result);
    }
    
    return 0;
}
