package graph;

import java.util.*;

/**
 * graph represents a node that can be connected to other graphs by edges.
 */
public class graph {

    /**
     * Constructs a new graph
     * @spec.effects Constructs a new graph
     */
    public graph(){
        throw new RuntimeException("constructor is not yet implemented");
    }

    /**
     * Adds an edge between the parent and the child
     *
     * @param parent the data that identifies the parent
     * @param child the data that identifies the child
     * @param label the data that represents the edge label
     * @spec.requires parent, child, label != null, and graph must contain both the parent and child
     * @spec.effects adds an edge with the label between the parent and the child
     */
    public void addEdge(String parent, String child, String label){
        throw new RuntimeException("method is not yet implemented");
    }

    /**
     * Adds a node to the graph
     *
     * @param data the data that represents the node
     * @spec.requires data != null, graph does not contain a node contents equal to data
     * @spec.effects adds a node to the graph
     */
    public void addNode(String data){
        throw new RuntimeException("method is not yet implemented");
    }

    /**
     * Gets the children associated with node "parent"
     *
     * @param parent the data that represents the parent
     * @return the list of children associated with the parent
     * @spec.requires parent != null
     */
    public List<String> listChildren(String parent){
        throw new RuntimeException("method is not yet implemented");
    }

    /**
     * Gets the nodes contained within this graph
     *
     * @return the list of nodes contained within this graph
     */
    public List<String> listNodes(){ throw new RuntimeException("method is not yet implemented"); }

    /**
     * Returns a string with information of the number of nodes and edges in this graph
     *
     * @return a String representing the number of nodes and edgesin this graph. Valid formats of strings
     * include: "Graph has 37 nodes and 56 edges", "Graph has no nodes and no edges", "Graph has 1 node
     * and no edges"
     */
    @Override
    public String toString(){ throw new RuntimeException("method is not yet implemented"); }
}
