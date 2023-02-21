package marvel.junitTests;

import graph.Graph;
import org.junit.*;
import marvel.MarvelPaths;
import static org.junit.Assert.*;
import java.util.*;

public class MarvelPathsTest {

    Graph<String, String> f = new Graph<>();
    Graph<String, String> g = new Graph<>();

    @Before
    public void constructGraphForBuildGraph(){
        g.addNode("Perkins-the-Magical-Singing-Instructor");
        g.addNode("Grossman-the-Youngest-of-them-all");
        g.addNode("Ernst-the-Bicycling-Wizard");
        g.addNode("Notkin-of-the-Superhuman-Beard");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard", "CSE331");
        g.addEdge("Notkin-of-the-Superhuman-Beard","Ernst-the-Bicycling-Wizard",  "CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Perkins-the-Magical-Singing-Instructor", "CSE331");
        g.addEdge("Perkins-the-Magical-Singing-Instructor", "Ernst-the-Bicycling-Wizard", "CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Grossman-the-Youngest-of-them-all", "CSE331");
        g.addEdge("Grossman-the-Youngest-of-them-all", "Ernst-the-Bicycling-Wizard", "CSE331");
        g.addEdge("Notkin-of-the-Superhuman-Beard","Perkins-the-Magical-Singing-Instructor",  "CSE331");
        g.addEdge("Perkins-the-Magical-Singing-Instructor",  "Notkin-of-the-Superhuman-Beard","CSE331");
        g.addEdge("Notkin-of-the-Superhuman-Beard","Grossman-the-Youngest-of-them-all",  "CSE331");
        g.addEdge("Grossman-the-Youngest-of-them-all",  "Notkin-of-the-Superhuman-Beard","CSE331");
        g.addEdge("Perkins-the-Magical-Singing-Instructor","Grossman-the-Youngest-of-them-all",  "CSE331");
        g.addEdge("Grossman-the-Youngest-of-them-all",  "Perkins-the-Magical-Singing-Instructor","CSE331");
        g.addEdge("Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard", "CSE403");
        g.addEdge("Notkin-of-the-Superhuman-Beard", "Ernst-the-Bicycling-Wizard", "CSE403");
    }

    @Before
    public void constructGraphStrangersForBFS(){
        f.addNode("Ethan");
        f.addNode("Elaine");
        f.addNode("Yufei");
        f.addNode("Andrew");
        f.addEdge("Ethan", "Elaine", "CS190B");
        f.addEdge("Elaine", "Yufei", "Roommates");
        f.addEdge("Yufei", "Andrew", "Poplar");
        f.addEdge("Ethan", "Andrew", "Elaine");
    }

    @Test
    public void sameNodesInGraph(){
        Graph<String, String> f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        for (String parent : g.listNodes()) {
            assertTrue(f.containsNode(parent));
        }
    }

    @Test
    public void sameChildrenInGraph(){
        Graph<String, String> f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        for (String parent : g.listNodes()) {
            assertTrue(f.containsNode(parent));
            for (String children : g.listChildren(parent).keySet()) {
                assertTrue(f.listChildren(parent).containsKey(children));
            }
        }
    }

    @Test
    public void sameEdgesInGraph(){
        Graph<String, String> f = MarvelPaths.buildGraph("staffSuperheroes.csv");
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
        Graph<String, String> f = MarvelPaths.buildGraph("staffSuperheroes.csv");
        List<String> expected = new ArrayList<>();
        expected.add("Ernst-the-Bicycling-Wizard");
        expected.add("CSE331");
        expected.add("Notkin-of-the-Superhuman-Beard");
        assertEquals(expected, MarvelPaths.findPath(f, "Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard"));
    }

    @Test
    public void testFindPathMultiplePaths(){
        List<String> e = new ArrayList<>();
        e.add("Ethan");
        e.add("Elaine");
        e.add("Andrew");
        assertEquals(e, MarvelPaths.findPath(f, "Ethan", "Andrew"));
        assertEquals(null, MarvelPaths.findPath(f, "Yufei", "Ethan"));
    }

    @Test
    public void testFindPathNoPath(){
        assertEquals(null, MarvelPaths.findPath(f, "Yufei", "Ethan"));
    }

    @Test
    public void testFindPathToSelf(){
        List<String> e = new ArrayList<>();
        e.add("Ethan");
        assertEquals(e, MarvelPaths.findPath(f, "Ethan", "Ethan"));
    }
}
