package marvel.junitTests;

import graph.Graph;
import org.junit.*;
import marvel.MarvelPaths;
import static org.junit.Assert.*;
import java.util.*;

public class MarvelPathsTest {
    Graph g = new Graph();

    @Before
    public void constructGraph(){
        g.addNode("Perkins-the-Magical-Singing-Instructor");
        g.addNode("Grossman-the-Youngest-of-them-all");
        g.addNode("Ernst-the-Bicycling-Wizard");
        g.addNode("Notkin-of-the-Superhuman-Beard");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard", "CSE331"); // 1
        g.addEdge("Notkin-of-the-Superhuman-Beard","Ernst-the-Bicycling-Wizard",  "CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Perkins-the-Magical-Singing-Instructor", "CSE331"); // 2
        g.addEdge("Perkins-the-Magical-Singing-Instructor", "Ernst-the-Bicycling-Wizard", "CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Grossman-the-Youngest-of-them-all", "CSE331"); // 3
        g.addEdge("Grossman-the-Youngest-of-them-all", "Ernst-the-Bicycling-Wizard", "CSE331");
        g.addEdge("Notkin-of-the-Superhuman-Beard","Perkins-the-Magical-Singing-Instructor",  "CSE331"); // 4
        g.addEdge("Perkins-the-Magical-Singing-Instructor",  "Notkin-of-the-Superhuman-Beard","CSE331");
        g.addEdge("Notkin-of-the-Superhuman-Beard","Grossman-the-Youngest-of-them-all",  "CSE331"); // 5
        g.addEdge("Grossman-the-Youngest-of-them-all",  "Notkin-of-the-Superhuman-Beard","CSE331");
        g.addEdge("Perkins-the-Magical-Singing-Instructor","Grossman-the-Youngest-of-them-all",  "CSE331"); // 6
        g.addEdge("Grossman-the-Youngest-of-them-all",  "Perkins-the-Magical-Singing-Instructor","CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard", "CSE403");
        g.addEdge("Notkin-of-the-Superhuman-Beard", "Ernst-the-Bicycling-Wizard", "CSE403");
    }

    @Test
    public void sameNodes(){
        Graph f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        for (String parent : g.listNodes()) {
            assertTrue(f.containsNode(parent));
        }
    }

    @Test
    public void sameChildren(){
        Graph f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        for (String parent : g.listNodes()) {
            assertTrue(f.containsNode(parent));
            for (String children : g.listChildren(parent).keySet()) {
                assertTrue(f.listChildren(parent).containsKey(children));
            }
        }
    }

    @Test
    public void sameEdges(){
        Graph f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        for (String parent : g.listNodes()){
            assertTrue(f.containsNode(parent));
            for (String children : g.listChildren(parent).keySet()){
                assertTrue(f.listChildren(parent).containsKey(children));
                for (String edge : g.listChildren(parent).get(children)){
                    assertTrue(f.listChildren(parent).get(children).contains(edge));
                }
            }
        }
    }

    @Test
    public void testFindPathSingle(){
        Graph f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        List<String> path = MarvelPaths.findPath(f, "Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard");
        List<String> expected = new ArrayList<>();
        expected.add("Ernst-the-Bicycling-Wizard");
        expected.add("CSE331");
        expected.add("Notkin-of-the-Superhuman-Beard");
        assertEquals(expected, MarvelPaths.findPath(f, "Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard"));
    }

    @Test
    public void testFindPathStrangers(){
        Graph f = new Graph();
        f.addNode("Ethan");
        f.addNode("Elaine");
        f.addNode("Yufei");
        f.addNode("Andrew");
        f.addEdge("Ethan", "Elaine", "CS190B");
        f.addEdge("Elaine", "Yufei", "Roommates");
        f.addEdge("Yufei", "Andrew", "Poplar");
        f.addEdge("Ethan", "Andrew", "Elaine");
        List<String> e = new ArrayList<>();
        e.add("Ethan");
        e.add("Elaine");
        e.add("Andrew");
        assertEquals(e, MarvelPaths.findPath(f, "Ethan", "Andrew"));
        assertEquals(null, MarvelPaths.findPath(f, "Yufei", "Ethan"));
    }
}
