package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import java.util.*;

/**
 * Finds the shortest path between two nodes in a weighted graph
 */
public class ShortestPath {

    // THIS CLASS IS NOT AN ADT

    // do we need to construct our own graph? can i assume it has points & distances? for campusmap LATER
    // queue takes PATHS
    // what does finished take? how to keep track
    // map is constructed with nodes as points and children as other point in the graph through a straight line path (meaning
    // the edges are the straight line distances between every point) all the stuff in paths.csv
    // change to N and E instead of K and V
    // testDriver will iterate through path by going through segments - each segment has a cost, beginning and end node
    // comparator class for paths (cost)
    // do we need to define our own equals to tell if two nodes are equal? - points already has this
    // do i need to comment <N> type stuff, yes (for methods and for classes)
    // comparing double -- Comparator.comparingDouble
    // contains
    // set of nodes for finished

    /**
     *
     * @param g the
     * @param node1 the
     * @param node2 the
     * @return null if there is no path, or
     * @param <N> the
     * @param <E> the
     */
    public static <N, E extends Number> Path<N> findPath(Graph<N, E> g, N node1, N node2){
        N start = node1;
        N destination = node2;
        Queue<Path<N>> active = new PriorityQueue<>(Comparator.comparingDouble(Path::getCost));
        Set<N> finished = new HashSet<>();
        active.add(new Path<>(start));
        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(destination)){
                return minPath;
            }
            if (finished.contains(minDest)){
                continue;
            }
            for (N child : g.listChildren(minDest).keySet()){
                if (!finished.contains(child)){
                    for (E edge : g.listChildren(minDest).get(child)){
                        // does this screw things up??
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
