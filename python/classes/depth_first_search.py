# 깊이 우선 탐색 Depth First Search
class DepthFirstSearch:
    
    def run(self, tree):
        self.tree = tree
        self.visit(0)

    def visit(self, node):
        if node > len(self.tree)-1:
            return
        print(node, end=', ')
        if node == len(self.tree)-1:
            return
        left_child = node * 2 + 1
        self.visit(left_child)
        self.visit(left_child+1)

tree = list(range(20))
DepthFirstSearch().run(tree)