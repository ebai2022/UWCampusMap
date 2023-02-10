package marvel;

import com.sun.tools.javac.Main;
import graph.Graph;
import java.util.*;

public class MarvelPaths {

    public static void Main(String[] args){

    }
    public void buildGraph(){
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData("marvel.csv");
        Graph g = new Graph();
        for (String label : nodesAndLabels.keySet()){
            for (String node : nodesAndLabels.get(label)){
                g.addNode(node);
            }
        }
        for (String node : g.listNodes()){
            for (String label : nodesAndLabels.keySet()){
                for (String child : nodesAndLabels.get(label)){
                    if (!node.equals(child)){
                        g.addEdge(node, child, label);
                        g.addEdge(child, node, label);
                    }
                }
            }
        }
    }

    public List<String> bfsAlg(String char1, String char2){
        List<String> shortest = new ArrayList<>();
        return shortest;
    }
}
