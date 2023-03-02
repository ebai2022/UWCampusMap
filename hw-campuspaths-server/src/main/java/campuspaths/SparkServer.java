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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import java.util.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        CampusMap map = new CampusMap("campus_buildings.csv", "campus_paths.csv");
        Spark.get("/buildingNames", new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception {
                List<String> buildingShortNames = new ArrayList<>(map.buildingNames().keySet());
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(buildingShortNames);
                return jsonResponse;
            }
        });
        Spark.get("/findPath", new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // don't need error handling because we will have a dropdown box and exactly what input we are giving
                //http://localhost:4567/findPath?start=CSE&end=KNE calls findPath with start building being CSE and end being KNE
                String startBuilding = request.queryParams("start");
                String endBuilding = request.queryParams("end");
                Path<Point> shortestPath = map.findShortestPath(startBuilding, endBuilding);
                //if (startBuilding == null || endBuilding == null || !map.shortNameExists(startBuilding) || !map.shortNameExists(endBuilding)){
                //    Spark.halt(400, "hwhooooops");
                //}
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(shortestPath);
                return jsonResponse;
            }
        });
    }

}
