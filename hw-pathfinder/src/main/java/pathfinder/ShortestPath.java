package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import java.util.*;

/**
 * A utility class that contains a method that finds the shortest path between two nodes in a weighted graph
 */
public class ShortestPath {

    // THIS CLASS IS NOT AN ADT

    /**
     * Finds the shortest path from a node to another node in the given graph. Shortest is defined by the path
     * that costs the least, which is determined by the straight line distance between nodes
     *
     * @param g the graph to use to find the shortest path in
     * @param start the node to start on
     * @param end the node to end on
     * @return a path between the start and end nodes. Returns null if there is no path
     * @param <N> the type for the nodes
     * @param <E> the type for the edges. Must be a subtype of number
     */
    public static <N, E extends Number> Path<N> findPath(Graph<N, E> g, N start, N end){
        Queue<Path<N>> active = new PriorityQueue<>(Comparator.comparingDouble(Path::getCost));
        Set<N> finished = new HashSet<>();
        active.add(new Path<>(start));
        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(end)){
                return minPath;
            }
            if (finished.contains(minDest)){
                continue;
            }
            for (N child : g.listChildren(minDest).keySet()){
                if (!finished.contains(child)){
                    for (E edge : g.listChildren(minDest).get(child)){
                        Path<N> newPath = minPath.extend(child, edge.doubleValue());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }
}
