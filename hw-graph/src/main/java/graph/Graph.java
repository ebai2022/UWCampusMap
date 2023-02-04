package graph;

import java.util.*;

/**
 * Graph represents a mutable collection of nodes and their respective edges that connect them
 */
public class Graph {

    private Map<String, Map<String, Set<String>>> nodes;

    // Abstraction function: what does each thing represent in graph

    // Representation invariant:
    /**
     * Constructs a new Graph
     *
     * @spec.requires Graph does not already exist
     * @spec.effects constructs a new graph that is empty (with no nodes and edges)
     */
    public Graph(){
        nodes = new HashMap<>();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert(this != null);
        boolean expensive = true;

        if (expensive){
            //check duplicate nodes

            //check duplicate edges
        }

        //check no nulls?
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
        checkRep();
        nodes.get(parent).get(child).add(label);
        checkRep();
    }

    /**
     * Adds a node to the graph
     *
     * @param data the data that represents the node
     * @spec.requires data != null, graph does not contain a node with contents entirely equal to data
     * @spec.effects adds a node to the graph
     */
    public void addNode(String data){
        checkRep();
        //check if data already exists?
        if (nodes.containsKey(data)){
            //throw anything?
        } else{
            nodes.put(data, new HashMap<>());
        }
    }

    /**
     * Gets the children associated with node "parent"
     *
     * @param parent the data that represents the parent
     * @return the list of children associated with the parent
     * @spec.requires parent != null, graph contains parent
     */
    public Map<String, Set<String>> listChildren(String parent){
        checkRep();
        Map<String, Set<String>> children = nodes.get(parent);
        checkRep();
        return children;
    }

    /**
     * Gets the nodes contained within the graph
     *
     * @return the list of nodes contained within this graph
     */
    public Set<String> listNodes(){
        checkRep();
        return nodes.keySet();
    }

    //add a contains node method?

}
