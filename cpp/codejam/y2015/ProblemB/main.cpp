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
#include <math.h>

using namespace std;

//typedef long long LL;
//
//int main() {
//	freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/in2", "r", stdin);
////	freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/out2", "w", stdout);
//	int T;
//	cin >> T;
//	for(int t = 1; t <= T; t++){
//		cout << "Case #" << t << ": ";
//		int n;
//		cin >> n;
//		int stuff[n];
//		for(int i = 0; i < n; i++) cin >> stuff[i];
//		int answer = 1000;
//		for(int i = 1; i <= 1000; i++){
//			int cur = i;
//			for(int j = 0; j < n; j++){
//				cur += (stuff[j]-1)/i;
//			}
//			answer = min(answer, cur);
//		}
//		cout << answer << endl;
//	}
//	exit(0);
//}

int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/in2", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/out", "w", stdout);
    
    int T;
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("Case #%d: ", tt);
        
        int D;
        scanf("%d", &D);
        
        int P[2000];
        memset(P, 0, sizeof(P));
        for (int i=0; i<D; i++) {
            scanf("%d", &P[i]);
        }
        
        int result = 0;
        
        while (true) {
            sort(P, P+D, greater<int>());
            int j = 0;
            for (int i=0; i<D; i++) {
                if (P[0] == P[i]) {
                    j = i;
                } else {
                    break;
                }
            }
            int a = j + 1;
            int b = P[0] / 2;
            int c = max(P[0]-b, P[a]) + a;
            if (a >= b || P[0] < c) {
                break;
            } else {
                result += a;
                for (int k=0; k<=j; k++) {
                    P[k] -= b;
                    P[D+k] = b;
                    D++;
                }
            }
        }
        
        result += *max_element(P, P+D);
        
        printf("%d\n", result);
    }
    
    return 0;
}
