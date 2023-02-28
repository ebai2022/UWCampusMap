/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
  // TODO: Define the props of this component. You will want to pass down edges
    edges: MapLine[] // the MapLines that will be drawn
}

interface MapState {}

/**
 * Draws the edges that have been obtained from the user onto the map
 */
class Map extends Component<MapProps, MapState> {

  render() {
      // creating an array of all the MapLines for rendering
      const arrayOfEdges: JSX.Element[] = [];
      let current = this.props.edges;
      for (let i = 0; i < current.length; i++){
          let lineColor = current[i].props.color;
          let firstX = current[i].props.x1;
          let firstY = current[i].props.y1;
          let secondX = current[i].props.x2;
          let secondY = current[i].props.y2;
          arrayOfEdges.push(<MapLine key={i} color={lineColor} x1={firstX} y1={firstY} x2={secondX} y2={secondY}/>)
      }
    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {
            // TODO: Render map lines here using the MapLine component. E.g.
            //<MapLine key={0} color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>
            // will draw a red line from the point 1000,1000 to 2000,2000 on the
            // map
              arrayOfEdges
          }
        </MapContainer>
      </div>
    );
  }
}

export default Map;
