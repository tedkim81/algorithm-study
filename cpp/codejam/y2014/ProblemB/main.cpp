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
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/out", "w", stdout);
    
    int T;
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("Case #%d: ", tt);
        
        double C, F, X;
        scanf("%lf", &C);
        scanf("%lf", &F);
        scanf("%lf", &X);
        
        double s1=0, s2=0, a=0;
        double result = 100000;
        while (true) {
            if (a > 0) {
                s1 += (C / (2+(F*(a-1))));
            }
            s2 = s1 + (X / (2+(F*a)));
            if (result <= s2) {
                break;
            }
            a++;
            result = s2;
        }
        
        printf("%.7lf\n", result);
    }
    
    return 0;
}

/*
int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemB/ProblemB/out2", "w", stdout);
    int tt;
    scanf("%d", &tt);
    for (int qq=1;qq<=tt;qq++) {
        printf("Case #%d: ", qq);
        double c, f, x;
        cin >> c >> f >> x;
        double spent = 0, ans = 1e30;
        int km = -1;
        double rate = 2.0;
        for (int farms = 0; farms <= 1000000; farms++) {
            double current = spent + x / rate;
            if (current < ans) {
                ans = current;
                km = farms;
            }
            spent += c / rate;
            rate += f;
        }
        printf("%.7lf\n", ans);
        cerr << "at " << km << endl;
    }
    return 0;
}
*/