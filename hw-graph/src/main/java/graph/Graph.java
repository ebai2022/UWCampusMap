package graph;

import java.util.*;

/**
 * Graph represents a mutable collection of nodes and their respective edges that connect them
 */
public class Graph {

    private Map<String, Map<String, Set<String>>> nodes;

    private final boolean ExpensiveCheck = true;

    // Abstraction function: For a graph g, let the "parent" nodes of g be "nodes.keySet()". The "children" nodes of a
    // "parent" i would be "nodes.get(i).keySet()". The "edge label" j from a child node would be "nodes.get(i).get(j),
    // where all the edge labels associated with the child node would be "nodes.get(i).values()".
    //
    // Representation invariant for every Graph g:
    // g != null &&
    // "parent" nodes cannot be null &&
    // "child" nodes cannot be null, have to also be contained in "parent" nodes, and have to have at least one edge &&
    // "edge labels" cannot be null

    /**
     * Constructs a new Graph
     *
     * @spec.requires Graph does not already exist
     * @spec.effects constructs a new graph that is empty (with no nodes and edges)
     */
    public Graph(){
        nodes = new HashMap<>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert(this != null);

        if (ExpensiveCheck){
            // check no nulls
            for (String parent : nodes.keySet()){
                assert(parent != null) : "null parent";
                for (String child : nodes.get(parent).keySet()){
                    assert (child != null) : "null child";
                    for (String edge : nodes.get(parent).get(child)){
                        assert (edge != null) : "null edge";
                    }
                }
            }
            // check if all children are also a parent node
            for (String parent : nodes.keySet()){
                for (String child : nodes.get(parent).keySet()){
                    assert(nodes.containsKey(child) == true) : "missing parent node";
                }
            }
            // check if all children have at least one edge
            for (String parent : nodes.keySet()){
                for (String child : nodes.get(parent).keySet()){
                    assert(nodes.get(parent).get(child) != null) : "no edges connecting parent and child";
                }
            }
        }
    }

    /**
     * Adds an edge between the parent and the child
     *
     * @param parent the data that identifies the parent
     * @param child the data that identifies the child
     * @param label the data that represents the edge label
     * @spec.modifies this
     * @spec.requires parent, child, label != null
     * @spec.effects adds an edge with the label between the parent and the child nodes. If the parent or child does not
     * exist in the graph, or the label is not unique, nothing is added
     */
    public void addEdge(String parent, String child, String label){
        checkRep();
        if (nodes.containsKey(parent) && nodes.containsKey(child)){
            if (!nodes.get(parent).containsKey(child)){
                nodes.get(parent).put(child, new HashSet<>());
            }
            nodes.get(parent).get(child).add(label);
        }
        checkRep();
    }

    /**
     * Adds a node to the graph
     *
     * @param data the data that represents the node
     * @spec.modifies this
     * @spec.requires data != null
     * @spec.effects adds a node to the graph. If the node is not unique, the node is not added
     */
    public void addNode(String data){
        checkRep();
        if (!nodes.containsKey(data) && data != null){
            nodes.put(data, new HashMap<>());
        }
    }

    /**
     * Gets the children associated with node "parent" and their respective edges
     *
     * @param parent the data that represents the parent
     * @return the map of children associated with the parent and their respective edges
     * @spec.requires parent != null, graph contains parent
     */
    public Map<String, Set<String>> listChildren(String parent){
        checkRep();
        if (!nodes.containsKey(parent)){
            // throw?
        }
        // does this have rep exposure
        return nodes.get(parent);
    }

    /**
     * Gets the nodes contained within the graph
     *
     * @return the list of nodes contained within this graph
     */
    public Set<String> listNodes(){
        checkRep();
        // does this have rep exposure
        return nodes.keySet();
    }

    //add a contains node method?

}
