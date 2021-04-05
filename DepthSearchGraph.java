import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;
    public Vertex(int val)
    {
        Value = val;
        Hit = false;
    }

    public int getValue() {
        return Value;
    }

    public void visit() {
        Hit = true;
    }

    public boolean isHit() {
        return Hit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return Value == vertex.Value;
    }

    @Override
    public String toString() {
        return "Vertex {" +
                "Value = " + Value +
                ", Hit = " + Hit +
                '}';
    }
}


class SimpleGraph {
    Vertex[] vertex;
    int[][] m_adjacency;
    int max_vertex;
    private int vertexSize;

    public SimpleGraph(int size) {
        max_vertex = size;
        m_adjacency = new int[size][size];
        vertex = new Vertex[size];
    }

    public void AddVertex(int value) {
        if (getVertexSize() == max_vertex) {
            return;
        }
        vertex[getVertexSize()] = new Vertex(value);
        vertexSizeIncrement();
    }

    public void AddVertex(Vertex vertex) {
        if (getVertexSize() != max_vertex) {
            this.vertex[getVertexSize()] = vertex;
            vertexSizeIncrement();
        }
    }

    // здесь и далее, параметры v -- индекс вершины
    // в списке  vertex
    public void RemoveVertex(int v) {
        // ваш код удаления вершины со всеми её рёбрами
        if (v < 0 || v >= getVertexSize()) {
            return;
        }
        vertex[v] = null;
        for (int i = 0; i < getVertexSize(); i++) {
            m_adjacency[v][i] = 0;
        }
        for (int i = 0; i < getVertexSize(); i++) {
            m_adjacency[i][v] = 0;
        }
        vertexSizeDecrement();
    }

    private boolean checkCorrectVertex(int v1, int v2) {
        if (v1 < 0 || v1 >= getMax_vertex() || v2 < 0 || v2 >= getMax_vertex()) {
            return false;
        }
        return true;
    }

    public boolean IsEdge(int v1, int v2) {
        if (!checkCorrectVertex(v1, v2)) {
            return false;
        }
        // true если есть ребро между вершинами v1 и v2
        if (m_adjacency[v1][v2] == 1) {
            return true;
        }
        return false;
    }

    public void AddEdge(int v1, int v2) {
        if (!checkCorrectVertex(v1, v2)) {
            return;
        }
        // добавление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2) {
        // удаление ребра между вершинами v1 и v2
        if (!checkCorrectVertex(v1, v2)) {
            return;
        }
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    private void vertexSizeIncrement() {
        vertexSize++;
    }

    private void vertexSizeDecrement() {
        vertexSize--;
    }

    public int getMax_vertex() {
        return max_vertex;
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public ArrayList<Integer> EvenTrees() {
        ArrayList<Integer> list = new ArrayList<>();
        if (getVertexSize() % 2.0 == 0) {
            deleteEdge(list, 0);
        }
        return list;
    }

    private List deleteEdge(List listEdgeDelete, int vertexPresent) {
        for (int i = vertexPresent + 1; i < getVertexSize(); i++) {
            if (m_adjacency[i][vertexPresent] == 1) {
                if (counterVertex(i) % 2 == 0) {
                    listEdgeDelete.add(vertex[vertexPresent].getValue());
                    listEdgeDelete.add(vertex[i].getValue());
                }
                deleteEdge(listEdgeDelete, i);
            }
        }
        return listEdgeDelete;
    }

    private int counterVertex(int vertex) {
        int counter = 1;
        for (int i = vertex + 1; i < getVertexSize(); i++) {
            if (m_adjacency[i][vertex] == 1) {
                counter += counterVertex(i);
            }
        }
        return counter;
    }

    private void searchWay(Stack<Integer> way, int indexFrom, int indexTo) {
        int indexNoVisit = -1;
        if (way.empty() || indexFrom != way.peek()) {
            way.push(indexFrom);
        }
        vertex[indexFrom].visit();
        for (int i = 0; i < getVertexSize(); i++) {
            if (m_adjacency[i][indexFrom] == 1) {
                if (i == indexTo) {
                    way.push(i);
                    return;
                }
                if (!vertex[i].isHit()) {
                    indexNoVisit = i;
                }
            }
        }
        if (indexNoVisit != -1) {
            searchWay(way, indexNoVisit, indexTo);
        } else {
            way.pop();
            if (way.empty()) {
                return;
            } else {
                searchWay(way, way.peek(), indexTo);
            }
        }
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo) {
        ArrayList<Vertex> listResult = new ArrayList<>();
        Stack<Integer> way = new Stack<>();
        searchWay(way, VFrom, VTo);
        for (int index : way) {
            listResult.add(vertex[index]);
        }
            // Узлы задаются позициями в списке vertex.
            // Возвращается список узлов -- путь из VFrom в VTo.
            // Список пустой, если пути нету
            return listResult;
    }
}
