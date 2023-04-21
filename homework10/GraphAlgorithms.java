import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Panya Bhinder 
 * @userid pbhinder3
 * @GTID 903690652
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot insert null data or vertex that does not exist.");
        } else {
            Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
            Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            List<Vertex<T>> list = new ArrayList<Vertex<T>>();
            q.add(start);
            list.add(start);
            visitedSet.add(start);
            while (!q.isEmpty() && (visitedSet.size() != graph.getVertices().size())) {
                Vertex<T> v = q.remove();
                
                for (VertexDistance<T> w : adjList.get(v)) {
                    if (!visitedSet.contains(w.getVertex())) {
                        visitedSet.add(w.getVertex());
                        list.add(w.getVertex());
                        q.add(w.getVertex());
                    }
                }
            }
            return list;
        }
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot insert null data or vertex that does not exist.");
        } else {
            List<Vertex<T>> trav = new ArrayList<Vertex<T>>();
            Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            dfsRecursive(visited, start, trav, adjList);
            return trav;
        }
    }

    /**
     * Recursive helper method for dfs.
     * @param <T> The generic typing of the data.
     * @param visited Set of visited verticies.
     * @param curr The current vertex.
     * @param list List of of verticies in visited order.
     * @param adjList The adjacency list of the passed in graph.
     */
    private static <T> void dfsRecursive(Set<Vertex<T>> visited, Vertex<T> curr, 
                                            List<Vertex<T>> list, Map<Vertex<T>, 
                                            List<VertexDistance<T>>> adjList) {
        visited.add(curr);
        list.add(curr);
        for (VertexDistance<T> w : adjList.get(curr)) {
            if (!visited.contains(w.getVertex())) {
                dfsRecursive(visited, w.getVertex(), list, adjList);
            }
        }

    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot insert null data or vertex that does not exist.");
        } else {
            Map<Vertex<T>, Integer> returnMap = new HashMap<Vertex<T>, Integer>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
            Queue<VertexDistance<T>> q = new PriorityQueue<VertexDistance<T>>();
            for (Vertex<T> v : adjList.keySet()) {
                if (v.equals(start)) {
                    returnMap.put(v, 0);
                } else {
                    returnMap.put(v, Integer.MAX_VALUE);
                }
            }
            q.add(new VertexDistance(start, 0));

            while (visited.size() < adjList.size() && !q.isEmpty()) {
                VertexDistance<T> vd = q.remove();
                if (!visited.contains(vd.getVertex())) {
                    visited.add(vd.getVertex());
                    for (VertexDistance<T> v : adjList.get(vd.getVertex())) {
                        int newDist = vd.getDistance() + v.getDistance();
                        if (!visited.contains(v.getVertex()) && returnMap.get(v.getVertex()) > newDist) {
                            returnMap.put(v.getVertex(), newDist);
                            q.add(new VertexDistance<T>(v.getVertex(), newDist));
                        }
                    }
                }
            }
            return returnMap;
        }
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Cannot insert null data or vertex that does not exist.");
        } else {
            Set<Edge<T>> returnSet = new HashSet<Edge<T>>();
            Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
            Queue<Edge<T>> q = new PriorityQueue<Edge<T>>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            
            for (VertexDistance<T> v : adjList.get(start)) {
                q.add(new Edge<T>(start, v.getVertex(), v.getDistance()));
            }
            visited.add(start);

            while (!q.isEmpty() && visited.size() != graph.getVertices().size()) {
                Edge<T> curr = q.remove();
                if (!visited.contains(curr.getV())) {
                    returnSet.add(new Edge<>(curr.getU(), curr.getV(), curr.getWeight()));
                    returnSet.add(new Edge<>(curr.getV(), curr.getU(), curr.getWeight()));
                    visited.add(curr.getV());

                    for (VertexDistance<T> v : adjList.get(curr.getV())) {
                        if (!visited.contains(v.getVertex())) {
                            q.add(new Edge<T>(curr.getV(), v.getVertex(), v.getDistance()));
                        }
                    }
                }
            }

            if (visited.size() < adjList.size() - 1) {
                return null;
            }
            return returnSet;

        }
    }
}