package marvel;

import graph.Graph;
import java.util.*;

public class MarvelPaths {

    public static void Main(String[] args) {

    }

    // do i need to document methods w/ java doc
    // what does my test driver need/not need
    // am i doing bfs correctly
    // how do i test my program in general
    public void buildGraph(){
        Graph g = new Graph();
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData("marvel.csv");
        for (String label : nodesAndLabels.keySet()){
            List<String> children = new ArrayList<>(nodesAndLabels.get(label));
            String parent = children.get(0);
            g.addNode(parent);
            for (int i = 1; i < children.size(); i++){
                String child = children.get(i);
                g.addNode(child);
                g.addEdge(parent, child, label);
                g.addEdge(child, parent, label);
            }
        }
    }

    public List<String> bfsAlg(Graph g, String char1, String char2){
        List<String> shortest = new ArrayList<>();
        String start = char1;
        String end = char2;
        Queue<String> visitNodes = new LinkedList<>();
        Map<String, List<String>> paths = new HashMap<>();
        visitNodes.add(start);
        paths.put(start, new ArrayList<>());
        while (!visitNodes.isEmpty()) {
            String node = visitNodes.remove();
            if (node.equals(end)){
                return paths.get(node);
            }
            List<String> sortChildren = new ArrayList<>(g.listChildren(start).keySet());
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
