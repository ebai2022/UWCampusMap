package graph;

import java.util.*;

/**
 * Graph represents a mutable collection of nodes and their respective edges that connect them
 */
public class Graph {

    /**
     * Constructs a new Graph
     *
     * @spec.requires Graph does not already exist
     * @spec.effects constructs a new graph that is empty (with no nodes and edges)
     */
    public Graph(){
        throw new RuntimeException("constructor is not yet implemented");
    }

    /**
     * Adds an edge between the parent and the child
     *
     * @param parent the data that identifies the parent
     * @param child the data that identifies the child
     * @param label the data that represents the edge label
     * @spec.requires parent, child, label != null, graph must contain both the parent and child, and the
     * label must be unique between the parent and child node in graph
     * @spec.effects adds an edge with the label between the parent and the child nodes
     */
    public void addEdge(String parent, String child, String label){
        throw new RuntimeException("addEdge is not yet implemented");
    }

    /**
     * Adds a node to the graph
     *
     * @param data the data that represents the node
     * @spec.requires data != null, graph does not contain a node with contents entirely equal to data
     * @spec.effects adds a node to the graph
     */
    public void addNode(String data){
        throw new RuntimeException("addNode is not yet implemented");
    }

    /**
     * Gets the children associated with node "parent" in alphabetical order by node name and
     * secondarily by edge label
     *
     * @param parent the data that represents the parent
     * @return the list of children associated with the parent
     * @spec.requires parent != null, graph contains parent
     */
    public List<String> listChildren(String parent){
        throw new RuntimeException("listChildren is not yet implemented");
    }

    /**
     * Gets the nodes contained within the graph in alphabetical order by node name
     *
     * @return the list of nodes contained within this graph
     */
    public List<String> listNodes(){ throw new RuntimeException("listNode is not yet implemented"); }

}
