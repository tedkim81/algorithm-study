# Copyright (c) 2018 kamyu. All rights reserved.
#
# Google Code Jam 2018 Round 3 - Name-Preserving Network
# https://codingcompetitions.withgoogle.com/codejam/round/0000000000007707/000000000004ba29
#
# Time:  O(LlogL)
# Space: O(L)
#

import sys
import collections
import random

def random_generate_graph(N):
    array = range(N)*4
    while True:
        random.shuffle(array)
        edges = set()
        for i in xrange(0, len(array), 2):
            if array[i] != array[i+1] and \
              (array[i], array[i+1]) not in edges and \
              (array[i+1], array[i]) not in edges:
                edges.add((array[i], array[i+1]))
            else:
                break
        else:
            break
    return edges

def get_signature(edges):
    MAGIC = 4
    N = len(edges)//2
    G = collections.defaultdict(set)
    for i, j in edges:
        G[i].add(j)
        G[j].add(i)
    signatures = [[0 for _ in xrange(N)] for _ in xrange(2)]
    for level in xrange(MAGIC):
        for i in xrange(N):
            if level == 0:
                counts = collections.defaultdict(int)
                for nei in G[i]:
                    counts[nei] += 1
                    for nei_nei in G[nei]:
                        if nei_nei in G[i]:
                            counts[nei] += 1
                label = counts.values()
            else:
                label = []
                for nei in G[i]:
                    label.append(signatures[(level-1) % 2][nei])
            signatures[level % 2][i] = tuple(sorted(label))
    return signatures[(MAGIC-1) % 2]

def generate_graph(L, U):
    while True:
        edges = random_generate_graph(L)
        signatures = get_signature(edges)
        if len(set(signatures)) == len(edges)//2:
            break
    return edges, signatures

def inv_idx(array):
    result = collections.defaultdict(int)
    for i in xrange(len(array)):
        result[array[i]] = i
    return result

def name_preserving_network():
    L, U = map(int, raw_input().strip().split())
    edges, signatures = generate_graph(L, U)
    print len(edges)//2
    for i, j in edges:
        print i+1, j+1
    sys.stdout.flush()

    permuted_edges = set()
    N = input()
    for _ in xrange(2*N):
        i, j = map(int, raw_input().strip().split())
        permuted_edges.add((i-1, j-1))
    result = []
    inv_sig = inv_idx(get_signature(permuted_edges))
    for i in xrange(len(signatures)):
        result.append(inv_sig[signatures[i]]+1)
    print " ".join(map(str, result))
    sys.stdout.flush()

T = input()
for case in xrange(T):
    name_preserving_network()