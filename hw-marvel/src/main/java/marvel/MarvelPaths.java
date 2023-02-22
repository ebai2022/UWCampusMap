package marvel;

import graph.Graph;
import java.util.*;
import java.util.Scanner;

/**
 * MarvelPaths represents the main window for building a graph and finding the shortest path between
 * characters. It contains the main() function that starts the marvel program
 */
public class MarvelPaths {

    /**
     * Main method to interact with the client
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("hola! I can find the shortest path between any two characters in a file. I will give you the shortest path in the form of name1 edge name2 etc. Everything is case sensitive! Try me :)");
        System.out.println("Please enter a full csv file name and two character names all separated by spaces to " +
                "find the shortest distance from the first character to the second, or use 'quit' to stop");
        String s = input.nextLine();
        while (!s.equals("quit")){
            String[] arg = s.split(" ");
            if (arg.length != 3){
                System.out.println("bad input! make sure your input looks like: file.csv name1 name2");
            } else {
                try{
                    Graph<String, String> g = buildGraph(arg[0]);
                    if (!g.containsNode(arg[1]) && !g.containsNode(arg[2])){
                        System.out.println("Graph does not contain " + arg[1]);
                        System.out.println("Graph does not contain " + arg[2]);
                    }
                    else if (!g.containsNode(arg[1])){
                        System.out.println("Graph does not contain " + arg[1]);
                    }
                    else if (!g.containsNode(arg[2])){
                        System.out.println("Graph does not contain " + arg[2]);
                    } else{
                        List<String> shortest = findPath(g, arg[1], arg[2]);
                        if (shortest == null){
                            System.out.println("no path found!");
                        } else{
                            System.out.println("The shortest path from " + arg[1] + " to " + arg[2] + " is: ");
                            System.out.println(shortest);
                        }
                    }
                } catch (IllegalArgumentException e){
                    System.out.println("invalid file name!");
                }
            }
            System.out.println();
            System.out.println("Please enter a full csv file name and two character names all separated by spaces to " +
                    "find the shortest distance from the first character to the second, or use 'quit' to stop");
            s = input.nextLine();
        }
        System.out.println("baibai");
    }

    /**
     * Builds a graph from a given file name using data from that file. The nodes
     * represent character names while the edges represent comic book names
     *
     * @param fileName the file with data used to build the graph
     * @spec.requires filename is a valid file in the resources/data folder.
     * @return a graph that is built from the given file
     */
    public static Graph<String, String> buildGraph(String fileName){
        Graph<String, String> g = new Graph<>();
        Map<String, Set<String>> nodesAndLabels = MarvelParser.parseData(fileName);
        for (String label : nodesAndLabels.keySet()){
            List<String> children = new ArrayList<>(nodesAndLabels.get(label));
            for (int i = 0; i < children.size(); i++){
                String parent = children.get(i);
                g.addNode(parent);
                //starting from i+1 to make sure we don't connect a character to themselves
                for (int j = i+1; j < children.size(); j++){
                    String child = children.get(j);
                    //making sure we only add the children in each book on the first rotation to not have duplicate operations
                    if (i == 0){
                        g.addNode(child);
                    }
                    //adding edges both ways
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
     * @spec.requires the given graph is not null
     * @return a list of strings corresponding to the shortest path from char1 to char2 in the
     * form of nodeA, edge, nodeB... where edge connects from nodeA to node B. Returns null if
     * there is no path from char1 to char2, or if char1 or char2 do not exist in the graph
     */
    public static List<String> findPath(Graph<String, String> g, String char1, String char2){
        if (!g.containsNode(char1) || !g.containsNode(char2)){
            return null;
        }
        Queue<String> visitedNodes = new LinkedList<>();
        Map<String, String> paths = new HashMap<>();
        visitedNodes.add(char1);
        paths.put(char1, null);
        while (!visitedNodes.isEmpty()) {
            String node = visitedNodes.remove();
            if (node.equals(char2)){
                return compileEdgesAndNodes(g, paths, node);
            }
            addSortedNodes(g, node, paths, visitedNodes);
        }
        return null;
    }

    // used to add nodes to the queue that need to be visited
    // used to add already visited nodes with the node visited as the key and the node it came from as the value
    private static void addSortedNodes(Graph<String, String> g, String node, Map<String, String> paths, Queue<String> visitedNodes){
        List<String> sortChildren = new ArrayList<>(g.listChildren(node).keySet());
        //sorting for the lexicographically least path
        Collections.sort(sortChildren);
        for (String child : sortChildren){
            //checking if we have visited the node
            if (!paths.containsKey(child)){
                paths.put(child, node);
                visitedNodes.add(child);
            }
        }
    }

    // used to find all the edges connecting the nodes and put them in a list in the form of node edge node etc.
    private static List<String> compileEdgesAndNodes(Graph<String, String> g, Map<String, String> paths, String node){
        List<String> path = new ArrayList<>();
        while (paths.get(node) != null){
            List<String> edges = new ArrayList<>(g.listChildren(paths.get(node)).get(node));
            //sorting for the lexicographically least path
            Collections.sort(edges);
            //adding to the start to get the right order from start node ----> end node
            path.add(0, node);
            path.add(0, edges.get(0));
            node = paths.get(node);
        }
        path.add(0, node);
        return path;
    }
}
