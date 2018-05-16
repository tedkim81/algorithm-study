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

pair <double, int> a[123456];

int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemD/ProblemD/in", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemD/ProblemD/out", "w", stdout);
    int tt;
    scanf("%d", &tt);
    for (int qq=1;qq<=tt;qq++) {
//        printf("Case #%d: ", qq);
        int n;
        cin >> n;
        for (int i = 0; i < n; i++) {
            cin >> a[i].first;
            a[i].second = 1;
        }
        for (int i = 0; i < n; i++) {
            cin >> a[i + n].first;
            a[i + n].second = -1;
        }
        sort(a, a + n + n);
        int z = 0, cz = 0;
        for (int i = n + n - 1; i >= 0; i--) {
            cz += a[i].second;
            printf("%d ", cz);
            if (cz > z) z = cz;
        }
        int y = 0, bal = 0;
        for (int i = n + n - 1; i >= 0; i--) {
            if (a[i].second == 1) {
                bal++;
            } else {
                if (bal > 0) {
                    bal--;
                    y++;
                }
            }
        }
//        printf("%d %d\n", y, z);
    }
    return 0;
}

//int main() {
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemD/ProblemD/in", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014/ProblemD/ProblemD/out", "w", stdout);
//    
//    int T;
//    scanf("%d", &T);
//    
//    for (int tt=1; tt<=T; tt++) {
//        printf("case #%d: ", tt);
//        
//        int N;
//        scanf("%d", &N);
//        
//        vector<double> ns1, ns2;
//        for (int i=0; i<N; i++) {
//            double n;
//            scanf("%lf", &n);
//            ns1.push_back(n);
//            ns2.push_back(n);
//        }
//        sort(ns1.begin(), ns1.end());
//        sort(ns2.begin(), ns2.end());
//        
//        vector<double> ks1, ks2;
//        for (int i=0; i<N; i++) {
//            double k;
//            scanf("%lf", &k);
//            ks1.push_back(k);
//            ks2.push_back(k);
//        }
//        sort(ks1.begin(), ks1.end());
//        sort(ks2.begin(), ks2.end());
//        
//        int point1 = 0;
//        for (int i=0; i<N; i++) {
//            double n = ns1.front();
//            ns1.erase(ns1.begin());
//            double k = ks1.front();
//            if (n > k) {
//                ks1.erase(ks1.begin());
//                point1++;
//            } else {
//                ks1.pop_back();
//            }
//        }
//        
//        int point2 = 0;
//        for (int i=0; i<N; i++) {
//            double n = ns2.front();
//            ns2.erase(ns2.begin());
//            point2++;
//            for (vector<double>::iterator j=ks2.begin(); j!=ks2.end(); ++j) {
//                if (*j > n) {
//                    ks2.erase(j);
//                    point2--;
//                    break;
//                }
//            }
//        }
//        
//        printf("%d %d\n", point1, point2);
//    }
//    
//    return 0;
//}
