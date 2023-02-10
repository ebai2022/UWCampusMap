package graph.junitTests;

import graph.Graph;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;
public final class GraphTest {

    private Graph notInGraph = new Graph();
    private Graph inGraph = new Graph();
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // testing whether the containsNode method works

    // checks that nodes that are not in the graph return false for contains node
    public void testNoNode(){
        notInGraph.addNode("Bob");
        assertFalse(notInGraph.containsNode("Bobby"));
        assertFalse(notInGraph.containsNode("Ella"));
    }

    // checks that nodes that are in the graph return true for contains node
    public void testExistingNode(){
        inGraph.addNode("Kevin");
        inGraph.addNode("Sophia");
        assertTrue(inGraph.containsNode("Kevin"));
        assertTrue(inGraph.containsNode("Sophia"));
    }
}
