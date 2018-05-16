//
//  backup1.cpp
//  AlgorithmStudy
//
//  Created by 김태우 on 2016. 4. 9..
//  Copyright © 2016년 김태우. All rights reserved.
//

#include <stdio.h>
#include <memory.h>
#include <stdlib.h>
#include <iostream>
#include <sstream>
#include <fstream>
#include <vector>
#include <set>
#include <map>
#include <cassert>
#include <math.h>

using namespace std;

/**
 *
 */
void solve13(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int tt=0; tt<T; tt++) {
        getline(infile, line);
        vector<char> res;
        for (int i=0; i<line.length(); i++) {
            char ch = line.at(i);
            if (res.size() > 0 && ch >= res.at(0)) {
                res.insert(res.begin(), ch);
            } else {
                res.push_back(ch);
            }
        }
        cout << "Case #" << (tt+1) << ": ";
        outfile << "Case #" << (tt+1) << ": ";
        for (vector<char>::iterator it=res.begin(); it!=res.end(); ++it) {
            cout << (*it);
            outfile << (*it);
        }
        cout << endl;
        outfile << endl;
    }
}

int minScalaProduct(int* x, bool* xb, int* y, bool* yb, int n, int remain) {
    if (remain == 0) {
        return 0;
    }
    int res = 987654321;
    for (int i=0; i<n; i++) {
        if (xb[i]) {
            continue;
        }
        xb[i] = true;
        for (int j=0; j<n; j++) {
            if (yb[j]) {
                continue;
            }
            int xx = x[i];
            int yy = y[j];
            yb[j] = true;
            res = min(res, (xx * yy) + minScalaProduct(x, xb, y, yb, n, remain-1));
            //            cout << "xx:" << xx << ", yy:" << yy << ", res:" << res << endl;
            yb[j] = false;
        }
        xb[i] = false;
    }
    return res;
}

/**
 * Round 1A 2008
 * Problem A. Minimum Scalar Product
 * https://code.google.com/codejam/contest/32016/dashboard#s=p0
 * n=8 일때 무한루프에 빠지는데 이유를 모르겠다..
 */
void solve12(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int tt=0; tt<T; tt++) {
        getline(infile, line);
        stringstream ss(line);
        int n;
        ss >> n;
        
        getline(infile, line);
        stringstream ss2(line);
        int x[n];
        for (int i=0; i<n; i++) {
            ss2 >> x[i];
        }
        
        getline(infile, line);
        stringstream ss3(line);
        int y[n];
        for (int i=0; i<n; i++) {
            ss3 >> y[i];
        }
        
        bool xb[n], yb[n];
        memset(xb, false, sizeof(xb));
        memset(yb, false, sizeof(yb));
        int res = minScalaProduct(x, xb, y, yb, n, n);
        cout << "Case #" << (tt+1) << ": " << res << endl;
        outfile << "Case #" << (tt+1) << ": " << res << endl;
    }
}

/**
 * Qualification Round 2016
 * Problem D. Fractiles
 * https://code.google.com/codejam/contest/6254486/dashboard#s=p3
 * Large input
 * 답이 틀렸다고 나오는데, 어디가 틀렸는지 모르겠다.
 */
void solve11(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int tt=0; tt<T; tt++) {
        getline(infile, line);
        stringstream ss(line);
        int K, C, S;
        ss >> K >> C >> S;
        
        if (C*S >= K) {
            vector<unsigned long long> res;
            for (int i=1; i<=K; i+=C) {
                unsigned long long r = i;
                for (int j=0; j<C; j++) {
                    r = ((r - 1) * K) + min(i+j, K);
                }
                res.push_back(r);
            }
            cout << "Case #" << (tt+1) << ":";
            outfile << "Case #" << (tt+1) << ":";
            
            for (vector<unsigned long long>::iterator it=res.begin(); it!=res.end(); ++it) {
                unsigned long long r = *it;
                cout << " " << r;
                outfile << " " << r;
            }
            
            cout << endl;
            outfile << endl;
        } else {
            cout << "Case #" << (tt+1) << ": IMPOSSIBLE" << endl;
            outfile << "Case #" << (tt+1) << ": IMPOSSIBLE" << endl;
        }
    }
}

/**
 * Code Jam to I/O 2016 for Women
 * Problem A. Cody's Jams
 * https://code.google.com/codejam/contest/8274486/dashboard
 */
void solve10(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        stringstream ss(line);
        int N;
        ss >> N;
        
        getline(infile, line);
        stringstream ss2(line);
        unsigned long long P[N*2];
        for (int b=0; b<N*2; b++) {
            ss2 >> P[b];
        }
        
        unsigned long long res[N];
        int resIdx = N-1;
        bool ckd[N*2];
        memset(ckd, false, sizeof(ckd));
        for (int b=(N*2)-1; b>=0; b--) {
            if (ckd[b] == true) {
                continue;
            }
            if (P[b] % 4 == 0) {
                unsigned long long tmp = P[b] / 4 * 3;
                for (int c=b-1; c>=0; c--) {
                    if (P[c] == tmp && !ckd[c]) {
                        res[resIdx] = P[c];
                        resIdx--;
                        ckd[c] = true;
                        break;
                    }
                }
            }
        }
        
        cout << "Case #" << (a+1) << ":";
        outfile << "Case #" << (a+1) << ":";
        for (int b=0; b<N; b++) {
            cout << " " << res[b];
            outfile << " " << res[b];
        }
        cout << endl;
        outfile << endl;
    }
}

/**
 * Qualification Round 2016
 * Problem D. Fractiles
 * https://code.google.com/codejam/contest/6254486/dashboard#s=p3
 * Small set
 */
void solve9(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        stringstream ss(line);
        int K, C, S;
        ss >> K >> C >> S;
        
        cout << "Case #" << (a+1) << ":";
        outfile << "Case #" << (a+1) << ":";
        
        // small set 에서 K=S 이므로 K만 신경쓰자.
        unsigned long long res;
        for (int b=0; b<K; b++) {
            if (C == 1) {
                res = b + 1;
            } else if (C == 2) {
                res = (K * b) + b + 1;
            } else {
                res = (pow(K, C-1) * b) + (pow(K, C-2) * b) + b + 1;
            }
            cout << " " << res;
            outfile << " " << res;
        }
        cout << endl;
        outfile << endl;
    }
}

int jamcoin(int* jc, int base, int N) {
    int divisor = 0;
    for (int a=2; a<2000; a++) {
        int r = jc[0];
        for (int b=1; b<N; b++) {
            r = ((r * base) + jc[b]) % a;
        }
        if (r == 0) {
            divisor = a;
            break;
        }
    }
    return divisor;
}

/**
 * Qualification Round 2016
 * Problem C. Coin Jam
 * https://code.google.com/codejam/contest/6254486/dashboard#s=p2
 */
void solve8(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        stringstream ss(line);
        int N, J;
        ss >> N >> J;
        
        cout << "Case #" << (a+1) << ":" << endl;
        outfile << "Case #" << (a+1) << ":" << endl;
        
        int jc[N];
        memset(jc, 0, sizeof(jc));
        jc[0] = 1;
        jc[N-1] = 1;
        
        int divisor[11];
        int jj = 0;
        
        while (jj < J) {
            bool success = true;
            for (int base=2; base<=10; base++) {
                divisor[base] = jamcoin(jc, base, N);
                if (divisor[base] == 0) {
                    success = false;
                    break;
                }
            }
            if (success) {
                for (int d=0; d<N; d++) {
                    cout << jc[d];
                    outfile << jc[d];
                }
                for (int base=2; base<=10; base++) {
                    cout << " " << divisor[base];
                    outfile << " " << divisor[base];
                }
                cout << endl;
                outfile << endl;
                jj++;
            }
            
            // 다른 jc로 변경
            for (int b=1; b<N-1; b++) {
                if (jc[b] == 0) {
                    jc[b] = 1;
                    break;
                } else {
                    jc[b] = 0;
                }
            }
        }
    }
}

void flip(string& str, int endidx) {
    for (int a=0; a<=endidx/2; a++) {
        if (a == endidx-a) {
            if (str[a] == '+') {
                str[a] = '-';
            } else {
                str[a] = '+';
            }
        } else {
            char left = str[a];
            char right = str[endidx-a];
            if (left == '+') {
                str[endidx-a] = '-';
            } else {
                str[endidx-a] = '+';
            }
            if (right == '+') {
                str[a] = '-';
            } else {
                str[a] = '+';
            }
        }
    }
}

/**
 * Qualification Round 2016
 * Problem B. Revenge of the Pancakes
 * https://code.google.com/codejam/contest/6254486/dashboard#s=p1
 */
void solve7(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        int res = 0;
        int end = (int)line.length() - 1;
        while (res < 1000) {
            while (end >= 0 && line.at(end) == '+') { // 우측에 +는 제외
                end--;
            }
            if (end < 0) {
                break;
            }
            // 남은영역(우측에 -)에서,
            if (line.at(0) == '-') {
                // 좌측에 -면, 전체 flip
                flip(line, end);
            } else {
                // 좌측에 +면, 우측에 연속 +수가 최대인 상태까지 탐색후, flip
                int maxcnt = 0;
                int maxidx = 0;
                int cnt = 0;
                for (int b=0; b<=end; b++) {
                    if (line.at(b) == '+') {
                        cnt++;
                        if (cnt > maxcnt) {
                            maxcnt = cnt;
                            maxidx = b;
                        }
                    } else {
                        cnt = 0;
                    }
                }
                flip(line, maxidx);
            }
            res++;
        }
        assert(res < 1000);
        cout << "Case #" << (a+1) << ": " << res << endl;
        outfile << "Case #" << (a+1) << ": " << res << endl;
    }
}

/**
 * Qualification Round 2016
 * Problem A. Counting Sheep
 * https://code.google.com/codejam/contest/6254486/dashboard
 */
void solve6(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        stringstream ss(line);
        int N;
        ss >> N;
        int nn = 0;
        string str;
        bool ckd[10];
        int ckdn = 0;
        memset(ckd, false, sizeof(ckd));
        
        for (int b=0; b<100; b++) {
            nn += N;
            str = to_string(nn);
            for (int c=0; c<str.length(); c++) {
                // check and fill
                int m = str.at(c) - '0';
                if (!ckd[m]) {
                    ckd[m] = true;
                    ckdn++;
                }
            }
            // if full, break
            if (ckdn == 10) {
                break;
            }
        }
        if (nn == 0) {
            cout << "Case #" << (a+1) << ": INSOMNIA" << endl;
            outfile << "Case #" << (a+1) << ": INSOMNIA" << endl;
        } else {
            cout << "Case #" << (a+1) << ": " << nn << endl;
            outfile << "Case #" << (a+1) << ": " << nn << endl;
        }
    }
}

/**
 * Qualification Round 2009
 * Problem B. Watersheds
 * https://code.google.com/codejam/contest/dashboard?c=90101#s=p1
 */
void solve5(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int a=0; a<T; a++) {
        getline(infile, line);
        stringstream ss(line);
        int H, W;
        ss >> H >> W;
        char res[H][W];
        memset(res, ' ', sizeof(res));
        int alti[H][W];
        for (int b=0; b<H; b++) {
            getline(infile, line);
            stringstream ss(line);
            for (int c=0; c<W; c++) {
                ss >> alti[b][c];
            }
        }
        cout << "Case #" << (a+1) << ":" << endl;
        outfile << "Case #" << (a+1) << ":" << endl;
        int dy[4] = {-1,0,0,1};
        int dx[4] = {0,-1,1,0};
        char ch = 'a'-1;
        for (int h1=0; h1<H; h1++) {
            for (int w1=0; w1<W; w1++) {
                int mh = h1;
                int mw = w1;
                int min = alti[h1][w1];
                for (int d=0; d<4; d++) {
                    int h2 = h1 + dy[d];
                    int w2 = w1 + dx[d];
                    if (h2 >= 0 && h2 < H && w2 >= 0 && w2 < W && alti[h2][w2] < min) {
                        min = alti[h2][w2];
                        mh = h2;
                        mw = w2;
                    }
                }
                if (res[h1][w1] == ' ') {
                    if (res[mh][mw] == ' ') {
                        ch = ch + 1;
                        res[h1][w1] = ch;
                        res[mh][mw] = ch;
                    } else {
                        res[h1][w1] = res[mh][mw];
                    }
                } else if (res[mh][mw] == ' ') {
                    res[mh][mw] = res[h1][w1];
                }
                cout << res[h1][w1];
                outfile << res[h1][w1];
                if (w1 < W-1) {
                    cout << " ";
                    outfile << " ";
                }
            }
            cout << endl;
            outfile << endl;
        }
    }
}

/**
 * Qualification Round Africa 2010
 * Problem C. T9 Spelling
 * https://code.google.com/codejam/contest/351101/dashboard#s=p2
 */
void solve4(ifstream& infile, ofstream& outfile){
    map<char,string> map;
    map.insert(pair<char,string>('a',"2"));  map.insert(pair<char,string>('b',"22"));  map.insert(pair<char,string>('c',"222"));
    map.insert(pair<char,string>('d',"3"));  map.insert(pair<char,string>('e',"33"));  map.insert(pair<char,string>('f',"333"));
    map.insert(pair<char,string>('g',"4"));  map.insert(pair<char,string>('h',"44"));  map.insert(pair<char,string>('i',"444"));
    map.insert(pair<char,string>('j',"5"));  map.insert(pair<char,string>('k',"55"));  map.insert(pair<char,string>('l',"555"));
    map.insert(pair<char,string>('m',"6"));  map.insert(pair<char,string>('n',"66"));  map.insert(pair<char,string>('o',"666"));
    map.insert(pair<char,string>('p',"7"));  map.insert(pair<char,string>('q',"77"));  map.insert(pair<char,string>('r',"777"));
    map.insert(pair<char,string>('s',"7777"));
    map.insert(pair<char,string>('t',"8"));  map.insert(pair<char,string>('u',"88"));  map.insert(pair<char,string>('v',"888"));
    map.insert(pair<char,string>('w',"9"));  map.insert(pair<char,string>('x',"99"));  map.insert(pair<char,string>('y',"999"));
    map.insert(pair<char,string>('z',"9999"));
    map.insert(pair<char,string>('A',"2"));  map.insert(pair<char,string>('B',"22"));  map.insert(pair<char,string>('C',"222"));
    map.insert(pair<char,string>('D',"3"));  map.insert(pair<char,string>('E',"33"));  map.insert(pair<char,string>('F',"333"));
    map.insert(pair<char,string>('G',"4"));  map.insert(pair<char,string>('H',"44"));  map.insert(pair<char,string>('I',"444"));
    map.insert(pair<char,string>('J',"5"));  map.insert(pair<char,string>('K',"55"));  map.insert(pair<char,string>('L',"555"));
    map.insert(pair<char,string>('M',"6"));  map.insert(pair<char,string>('N',"66"));  map.insert(pair<char,string>('O',"666"));
    map.insert(pair<char,string>('P',"7"));  map.insert(pair<char,string>('Q',"77"));  map.insert(pair<char,string>('R',"777"));
    map.insert(pair<char,string>('S',"7777"));
    map.insert(pair<char,string>('T',"8"));  map.insert(pair<char,string>('U',"88"));  map.insert(pair<char,string>('V',"888"));
    map.insert(pair<char,string>('W',"9"));  map.insert(pair<char,string>('X',"99"));  map.insert(pair<char,string>('Y',"999"));
    map.insert(pair<char,string>('Z',"9999"));
    map.insert(pair<char,string>(' ',"0"));
    
    string line;
    getline(infile, line);
    int N = atoi(line.c_str());
    
    for (int i=0; i<N; i++) {
        getline(infile, line);
        string result = map[line.at(0)];
        for (int j=1; j<line.length(); j++) {
            if (result.back() == map[line.at(j)].front()) {
                result.push_back(' ');
            }
            result += map[line.at(j)];
        }
        outfile << "Case #" << (i+1) << ": " << result << endl;
        cout << "Case #" << (i+1) << ": " << result << endl;
    }
}

/**
 * Qualification Round 2009
 * Problem A. Alien Language
 * https://code.google.com/codejam/contest/90101/dashboard
 */
void solve3(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int L, D, N;
    ss >> L >> D >> N;
    
    vector<string> wordsOrigin;
    for (int i=0; i<D; i++) {
        getline(infile, line);
        wordsOrigin.push_back(line);
    }
    
    vector<string> wordsCopy;
    wordsCopy.resize(wordsOrigin.size());
    
    for (int i=0; i<N; i++) {
        getline(infile, line);
        vector<set<char>> vs;
        bool inner = false;
        for (int j=0; j<line.size(); j++) {
            char ch = line.at(j);
            if (ch == '(') {
                inner = true;
                vs.push_back(set<char>());
            } else if (ch == ')') {
                inner = false;
            } else if (inner) {
                vs.back().insert(ch);
            } else {
                set<char> s;
                s.insert(ch);
                vs.push_back(s);
            }
        }
        if (vs.size() != L) {
            cout << "error!!" << endl;
            return;
        }
        copy(wordsOrigin.begin(), wordsOrigin.end(), wordsCopy.begin());
        for (int j=0; j<L; j++) {
            for (vector<string>::iterator it=wordsCopy.begin(); it != wordsCopy.end(); ++it) {
                string word = *it;
                set<char> *set = &vs.at(j);  // 포인터로 안하면 굉장히 느려진다. 아마도 객체를 생성하는데 시간이 많이 걸리는듯.
                if (word != "" && (*set).find(word.at(j)) == (*set).end()) {
                    *it = "";
                }
            }
        }
        int result = 0;
        for (vector<string>::iterator it=wordsCopy.begin(); it != wordsCopy.end(); ++it) {
            if (*it != "") {
                result++;
            }
        }
        outfile << "Case #" << (i+1) << ": " << result << endl;
        cout << "Case #" << (i+1) << ": " << result << endl;
    }
}

/**
 * Qualification Round Africa 2010
 * Problem B. Reverse Words
 * https://code.google.com/codejam/contest/351101/dashboard#s=p1
 */
void solve2(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    int N = atoi(line.c_str());
    for (int a=0; a<N; a++) {
        getline(infile, line);
        stringstream ss(line);
        string s;
        vector<string> tmp;
        while (ss >> s){
            tmp.push_back(s);
        }
        outfile << "Case #" << (a+1) << ": ";
        cout << "Case #" << (a+1) << ": ";
        for (int b=(int)tmp.size()-1; b>=0; b--){
            outfile << tmp[b] << " ";
            cout << tmp[b] << " ";
        }
        outfile << endl;
        cout << endl;
    }
}

/**
 * Qualification Round Africa 2010
 * Problem A. Store Credit
 * https://code.google.com/codejam/contest/351101/dashboard#s=p0
 */
void solve1(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    int N = atoi(line.c_str());
    int r1[N];
    int r2[N];
    int C, I;
    for (int a=0; a<N; a++) {
        getline(infile, line);
        C = atoi(line.c_str());
        
        getline(infile, line);
        I = atoi(line.c_str());
        
        int P[I];
        getline(infile, line);
        istringstream iss2(line);
        for (int b=0; b<I; b++) {
            iss2 >> P[b];
        }
        
        int PP = 0;
        for (int c=0; c<I-1; c++) {
            for (int d=c+1; d<I; d++) {
                if (P[c]+P[d] <= C && P[c]+P[d] > PP) {
                    PP = P[c]+P[d];
                    r1[a] = c+1;
                    r2[a] = d+1;
                }
            }
        }
    }
    for (int a=0; a<N; a++) {
        outfile << "Case #" << (a+1) << ": " << r1[a] << " " << r2[a] << endl;
        cout << "Case #" << (a+1) << ": " << r1[a] << " " << r2[a] << endl;
    }
}
