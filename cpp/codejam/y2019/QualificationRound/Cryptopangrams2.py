"""
Cryptopangrams.cpp의 문제에서 Test set 2를 해결하기 위해, Big Integer의 최대공약수를 구해야 했기 때문에 
예외적으로 파이썬 코드를 사용함. 해당 문제의 상세 정보 및 solution 1은 Cryptopangrams.cpp 파일 참조.

(solution 2)
10의 100제곱은 일반적인 방식으로 소인수분해를 할 수 없는 너무 큰 수다. analysis를 읽어보니,
이 문제를 풀기 위해선 중요한 한가지를 깨달아야 한다. 인접해 있는 암호화된 값 두개가 하나의 약수를 공유한다는 것.
A B C를 암호화하면 값이 두개가 나오는데, 그 둘이 모두 B의 암호값을 약수로 갖게 된다.
A B A와 같이 시작하는 경우 암호값이 서로 같게 되는데 이 경우에는 값을 구할 수 없으므로,
인접한 암호값이 서로 다른 부분에서 공약수를 구한 후, 좌우의 인접한 지점의 값을 구해나가는 방식으로 풀어야 한다.
오.. 이렇게 풀면 소수는 미리 구해놓을 필요도 없고 소수인지를 확인할 필요도 없어진다..!
--------------------------------
LL[] # 암호화된 값들
LL2[] # 분리해낸 소수들
for 1 <= i < L:
	if LL[i-1] != LL[i]:
		LL2[i] = gcd(LL[i-1], LL[i])
		break
for i > j >= 0:
	LL2[j] = LL[j] / LL2[j+1]
for i < j <= L:
	LL2[j] = LL[j-1] / LL2[j-1]
ss = sorted(set(LL2))
mm # LL2의 각 값들을 알파벳과 매핑
for aa in LL2:
	result += mm[aa]
--------------------------------

(solution 2 result)
성공!! 그런데 한가지 미스터리한 부분이 있다.
LL2[j-1] = LL[j-1] // LL2[j]  이 부분을  LL2[j-1] = int(LL[j-1] / LL2[j]) 이렇게 하면 
Runtime Error 가 발생한다. //연산자의 사용과 int()의 동작은 같아 보이는데.. 
암튼 이것 때문에 매우 오랜 시간을 또 삽질했다..ㅠ.ㅠ
"""

from math import gcd

def solve(N, L, LL):
	LL2 = []
	for i in range(0,L+1):
		LL2.append(0)
	ii = 0
	for i in range(1, L):
		if LL[i-1] != LL[i]:
			LL2[i] = gcd(LL[i-1], LL[i])
			ii = i
			break
	for j in range(ii, 0, -1):
		LL2[j-1] = LL[j-1] // LL2[j]
	for j in range(ii+1, L+1):
		LL2[j] = LL[j-1] // LL2[j-1]
	ss = sorted(set(LL2))
	mm = {}
	apb = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	i = 0
	for s in ss:
		mm[s] = apb[i]
		i += 1
	result = ""
	for aa in LL2:
		result += mm[aa]
	return result

def main():
	T = int(input())
	for tt in range(0, T):
		tmp = input().split()
		N = int(tmp[0])
		L = int(tmp[1])
		tmp = input().split()
		LL = []
		for aa in tmp:
			LL.append(int(aa))
		result = solve(N, L, LL)
		print("Case #%d: %s" % (tt+1, result))

main()
