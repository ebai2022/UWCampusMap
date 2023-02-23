/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import graph.Graph;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * This represents a campus with buildings that may be connected to each other through a number of
 * straight line paths
 */
public class CampusMap implements ModelAPI {

    // AF(this): For a campus map, let the short names of all the buildings be "campusBuildings.keySet()".
    // Each name is associated with its respective building, so the building for i would be "campusBuildings.get(i)".
    // Let all the "parent" node points contained on campus be "campusGraph.keySet()", the
    // "children" node points from that node be "campusGraph.get(i)" if i is a parent, and all the edges
    // between the "parent" node and the "child" node be "campusGraph.get(i).values()".
    //
    // Representation invariant for every campus map:
    // "campusBuilding" cannot be null and cannot have null keys (short names of buildings) and cannot have
    // null values (CampusBuildings) &&
    // "campusGraph" cannot be null

    private final boolean expensiveChecks = false;

    // the relation between the short name of the building to its CampusBuilding properties
    private Map<String, CampusBuilding> campusBuildings;

    // the graph for all the points
    private Graph<Point,Double> campusGraph;

    /**
     * Creates a campus map with buildings and paths using the given files
     *
     * @param buildingFile the file that contains all the building data
     * @param pathFile the file that contains all the path data
     */
    public CampusMap(String buildingFile, String pathFile){
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings(buildingFile);
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths(pathFile);
        campusGraph = new Graph<>();
        campusBuildings = new HashMap<>();
        //making life easier by having O(1) reference to short names
        for (CampusBuilding building : buildings){
            campusBuildings.put(building.getShortName(), building);
        }
        for (CampusPath path : paths){
            Point startNode = new Point(path.getX1(), path.getY1());
            Point endNode = new Point(path.getX2(), path.getY2());
            //brute forcing no checks, relying on graph implementation
            campusGraph.addNode(startNode);
            campusGraph.addNode(endNode);
            //edges are one directional
            campusGraph.addEdge(startNode, endNode, path.getDistance());
        }
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert(campusBuildings != null);
        assert(campusGraph != null);
        if(expensiveChecks){
            for (String name : campusBuildings.keySet()){
                assert(name != null);
                assert(campusBuildings.get(name) != null);
            }
        }
    }

    /**
     * Checks if the short name of the building exists in this campus map
     *
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        return campusBuildings.containsKey(shortName);
    }

    /**
     * Gets the long name of a building from its short name
     *
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        if (!campusBuildings.containsKey(shortName)){
            throw new IllegalArgumentException();
        }
        checkRep();
        return campusBuildings.get(shortName).getLongName();
    }

    /**
     * Gets all the building names (short and long) in the campus map
     *
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        Map<String, String> allNames = new HashMap<>();
        for (String shortName : campusBuildings.keySet()){
            // node names are unique so we don't have to worry about erasing names
            allNames.put(shortName, campusBuildings.get(shortName).getLongName());
        }
        checkRep();
        return allNames;
    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        if (startShortName == null || endShortName == null || !campusBuildings.containsKey(startShortName)
                || !campusBuildings.containsKey(endShortName)){
            throw new IllegalArgumentException();
        }
        Point startNode = new Point(campusBuildings.get(startShortName).getX(),
                campusBuildings.get(startShortName).getY());
        Point endNode = new Point(campusBuildings.get(endShortName).getX(),
                campusBuildings.get(endShortName).getY());
        checkRep();
        return ShortestPath.findPath(campusGraph, startNode, endNode);
    }
}
