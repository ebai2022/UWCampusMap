package marvel;

import graph.Graph;
import java.util.*;

/**
 * MarvelPaths represents
 * NOT AN ADT
 */
public class MarvelPaths {

    public static void Main(String[] args) {

    }

    // do i need to document methods w/ java doc (yes)
    // what does my test driver need/not need (use method pairs, need those 2
    // methods, probably don't need creategraph, put stuff into execute command)
    // am i doing bfs correctly (apparently yes)
    // how do i test my program in general (script test drivers)

    /**
     * Builds a graph from a given file name
     *
     * @param fileName
     * @return
     */
    public Graph buildGraph(String fileName){
        Graph g = new Graph();
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData(fileName);
        for (String label : nodesAndLabels.keySet()){
            List<String> children = new ArrayList<>(nodesAndLabels.get(label));
            String parent = children.get(0);
            g.addNode(parent);
            for (int i = 0; i < children.size(); i++){
                parent = children.get(i);
                for (int j = i+1; j < children.size(); j++){
                    String child = children.get(i);
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
     * @param g
     * @param char1
     * @param char2
     * @return blah blah, null if there is no path from char1 to char2
     */
    public List<String> bfsAlg(Graph g, String char1, String char2){
        List<String> shortest = new ArrayList<>();
        Queue<String> visitNodes = new LinkedList<>();
        Map<String, List<String>> paths = new HashMap<>();
        visitNodes.add(char1);
        paths.put(char1, new ArrayList<>());
        while (!visitNodes.isEmpty()) {
            String node = visitNodes.remove();
            if (node.equals(char2)){
                return paths.get(node);
            }
            // use treemap? treeset? to avoid collections.sort or even the extra loop
            List<String> sortChildren = new ArrayList<>(g.listChildren(char1).keySet());
            Collections.sort(sortChildren);
            for (String child : sortChildren){
                List<String> sortEdges = new ArrayList<>(g.listChildren(node).get(child));
                Collections.sort(sortEdges);
                for (String edge : sortEdges){
                    if (!paths.get(node).contains(edge)){
                        List<String> p = paths.get(node);
                        List<String> pPrime = new ArrayList<>(p);
                        pPrime.add(edge);
                        paths.put(child, pPrime);
                        visitNodes.add(child);
                    }
                }
            }
        }
        return null;
    }
}
