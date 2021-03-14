# 다익스트라(Dijkstra) 알고리즘.
# 시작점 정해져 있고, 음의 가중치 없는 경우, 최단경로 찾기.

import math, heapq

class Dijkstra:
    def __init__(self, graph, origin):
        distances = {node:math.inf for node in graph.keys()}
        distances[origin] = 0
        queue = []
        heapq.heappush(queue, (0, origin))

        while queue:
            distance_from_origin, node = heapq.heappop(queue)

            # 보통 위키에 작성되어 있는 슈도코드에서는, 
            # queue에 이미 전체 노드를 담아두고, pop 후에 우선순위큐를 재정렬한다.
            # 하지만 본 구현에서는, queue에 담긴 노드의 가중치를 변경하는 것이 까다롭기 때문에,
            # 보통의 너비우선탐색에서처럼 첫 노드만 queue에 담은후 반복문 안에서 자식 노드들을 담는다.
            # 이때 같은 노드들이 여러번 담길 수 있기 때문에 아래의 조건문을 통해 중복 노드들을 skip하도록 한다.
            if distance_from_origin > distances[node]:
                continue

            for next_node, weight in graph[node].items():
                distance_to_next_node = distance_from_origin + weight
                if distance_to_next_node < distances[next_node]:
                    distances[next_node] = distance_to_next_node
                    heapq.heappush(queue, (distance_to_next_node, next_node))

        self.distances = distances

    def get(self, destination):
        return self.distances[destination]

graph = {
    'A': {'B':10, 'C':30, 'D':15},
    'B': {'E':20},
    'C': {'F':5},
    'D': {'C':5, 'F':20},
    'E': {'F':20},
    'F': {'D':20}
}
dijkstra = Dijkstra(graph, 'A')

print('B', dijkstra.get('B'))
print('C', dijkstra.get('C'))
print('D', dijkstra.get('D'))
print('E', dijkstra.get('E'))
print('F', dijkstra.get('F'))