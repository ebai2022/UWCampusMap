package marvel;

import graph.Graph;
import java.util.*;

public class MarvelPaths {

    public void buildGraph(){
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData("marvel.csv");
        Graph g = new Graph();
        //add all nodes to the graph
        for (String node : nodesAndLabels.keySet()){
            g.addNode(node);
        }

    }
}
