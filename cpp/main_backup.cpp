//
//  main.cpp
//  AlgorithmStudy
//
//  Created by 김태우 on 2015. 12. 30..
//  Copyright © 2015년 김태우. All rights reserved.
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

void solve(ifstream& infile, ofstream& outfile);

int main(int argc, const char * argv[]) {
    string line;
//    ifstream infile ("/Users/ted/Downloads/A-small-attempt0.in.txt");
//    ifstream infile ("/Users/ted/Downloads/A-large.in.txt");
    ifstream infile ("/Users/ted/Downloads/test.in.txt");
    if (infile.is_open()) {
        ofstream outfile;
        outfile.open("/Users/ted/Downloads/out.txt");
        solve(infile, outfile);
        outfile.close();
        infile.close();
    } else {
        cout << "Unable to open file";
    }
    return 0;
}

int N;
int arr[100][50];

int argusResult(vector<int> idxs, bool* chk) {
    // TODO: chk가 false인 애들로 컬럼을 체크하여 맞으면 해당 컬럼번호, 틀리면 -1 리턴.
    bool checked[N];
    memset(checked, false, sizeof(checked));
    for (int i=0; i<(2*N)-1; i++) {
        if (chk[i]) {
            continue;
        }
        bool res = false;  // chk가 false인 애와 일치하는 컬럼이 있나?
        for (int j=0; j<N; j++) {
            bool res2 = true;  // chk가 false인 애와 현재 컬럼이 일치하나?
            for (int k=0; k<N; k++) {
                if (arr[k][j] != arr[i][k]) {
                    res2 = false;
                    break;
                }
            }
            if (res2) {
                res = true;
                checked[j] = true;
                break;
            }
        }
        if (!res) {
            return -1;
        }
    }
    for (int i=0; i<N; i++) {
        if (!checked[i]) {
            return i;
        }
    }
    return -1;
}

int argusMake(vector<int> idxs, int idx) {
    // idxs의 적당한 위치에 idx 추가. 성공하면 추가된 위치, 아니면 -1 리턴.
    for (vector<int>::iterator it=idxs.begin(); it!=idxs.end(); ++it) {
        bool res = true;
        for (int i=0; i<N; i++) {
            if (arr[*it][i] <= arr[idx][i]) {
                res = false;
                break;
            }
        }
        if (res) {
            idxs.insert(idxs.begin()+(*it), idx);
            return (*it);
        }
    }
    int last = idxs.back();
    bool res = true;
    for (int i=0; i<N; i++) {
        if (arr[last][i] >= arr[idx][i]) {
            res = false;
            break;
        }
    }
    if (res) {
        idxs.push_back(idx);
        return (int)idxs.size()-1;
    } else {
        return -1;
    }
}

int argus(vector<int> idxs, bool* chk) {
    if (idxs.size() == N) {
        return argusResult(idxs, chk);
    }
    if (idxs.size() == 0) {
        idxs.push_back(0);
        chk[0] = true;
        return argus(idxs, chk);
    }
    int res = -1;
    for (int i=0; i<(2*N)-1; i++) {
        if (chk[i]) {
            continue;
        }
        int addedIdx = argusMake(idxs, i);
        if (addedIdx >= 0) {
            chk[i] = true;
            int tmp = argus(idxs, chk);
            idxs.erase(idxs.begin()+addedIdx);
            chk[i] = false;
            return tmp;
        }
    }
    return res;
}

/**
 *
 */
void solve(ifstream& infile, ofstream& outfile){
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int tt=0; tt<T; tt++) {
        getline(infile, line);
        stringstream ss(line);
        ss >> N;
        
        for (int i=0; i<(2*N)-1; i++) {
            getline(infile, line);
            stringstream ss(line);
            for (int j=0; j<N; j++) {
                ss >> arr[i][j];
            }
        }
        
        vector<int> idxs;
        bool chk[(2*N)-1];
        memset(chk, false, sizeof(chk));
        int res = argus(idxs, chk);
        assert(res >= 0);
        
        cout << "Case #" << (tt+1) << ":";
        outfile << "Case #" << (tt+1) << ":";
        for (int i=0; i<N; i++) {
            cout << " " << arr[i][res];
            outfile << " " << arr[i][res];
        }
        cout << endl;
        outfile << endl;
    }
}