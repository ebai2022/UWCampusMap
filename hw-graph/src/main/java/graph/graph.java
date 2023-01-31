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
     * @spec.requires parent, child, label != null, graph must contain both the parent and child, and
     * label must be unique between the parent and child node in graph
     * @spec.effects adds an edge with the label between the parent and the child
     */
    public void addEdge(String parent, String child, String label){
        throw new RuntimeException("method is not yet implemented");
    }

    /**
     * Adds a node to the graph
     *
     * @param data the data that represents the node
     * @spec.requires data != null, graph does not contain a node with contents entirely equal to data
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

}
