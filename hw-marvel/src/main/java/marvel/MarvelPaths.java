package marvel;

import graph.Graph;
import java.util.*;

/**
 * MarvelPaths represents
 * NOT AN ADT
 */
public class MarvelPaths {

    /**
     * insert comment here
     *
     * @param args do i even need this
     */
    public static void Main(String[] args) {

    }

    // do i need to document methods w/ java doc (yes)
    // what does my test driver need/not need (use method pairs, need those 2)
    // methods, probably don't need creategraph, put stuff into execute command)
    // am i doing bfs correctly (apparently yes)
    // how do i test my program in general (script test drivers)

    /**
     * Builds a graph from a given file name
     *
     * @param fileName the file with data used to build the graph
     * @return a graph that is built from the given file
     */
    public static Graph buildGraph(String fileName){
        Graph g = new Graph();
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData(fileName);
        for (String label : nodesAndLabels.keySet()){
            List<String> children = new ArrayList<>(nodesAndLabels.get(label));
            for (int i = 0; i < children.size(); i++){
                String parent = children.get(i);
                g.addNode(parent);
                for (int j = i+1; j < children.size(); j++){
                    String child = children.get(j);
                    if (i == 0){
                        g.addNode(child);
                    }
                    g.addEdge(parent, child, label);
                    g.addEdge(child, parent, label);
                }
            }
        }
        return g;
    }

    /**
     * Finds the shortest path from a node to another node in the given graph
     *
     * @param g the graph to use to find the shortest path from char1 to char2
     * @param char1 the node to start on
     * @param char2 the character to end on
     *              should I throw an exception if g/char1/char2 are null?
     * @return a list of strings corresponding to the shortest path from char1 to char2. returns
     * null if there is no path from char1 to char2, or if char1 or char2 do not exist in the graph
     */
    public static List<String> findPath(Graph g, String char1, String char2){
        if (!g.containsNode(char1) || !g.containsNode(char2)){
            return null;
        }
        Queue<String> visitNodes = new LinkedList<>();
        Map<String, String> paths = new HashMap<>();
        visitNodes.add(char1);
        paths.put(char1, null);
        while (!visitNodes.isEmpty()) {
            String node = visitNodes.remove();
            if (node.equals(char2)){
                List<String> path = new ArrayList<>();
                while (paths.get(node) != null){
                    List<String> edges = new ArrayList<>(g.listChildren(paths.get(node)).get(node));
                    Collections.sort(edges);
                    path.add(0, node);
                    path.add(0, edges.get(0));
                    node = paths.get(node);
                }
                path.add(0, node);
                return path;
            }
            List<String> sortChildren = new ArrayList<>(g.listChildren(node).keySet());
            Collections.sort(sortChildren);
            for (String child : sortChildren){
                if (!paths.containsKey(child)){
                    paths.put(child, node);
                    visitNodes.add(child);
                }
            }
        }
        return null;
    }
}
