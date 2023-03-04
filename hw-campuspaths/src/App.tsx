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

import React, {Component} from 'react';
import Map from "./Map";
// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import MapLine from "./MapLine";
import RouteEdges from "./RouteEdges";

interface AppState{
    edgeList: MapLine[] // the MapLines that have been parsed from the users' input
}
/**
 * Takes two buildings from the users actions and then draws the shortest path between them
 */
class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            // TODO: store edges in this state
            edgeList: []
        };
    }
    render() {
        return (
            <div>
                <h1 id="app-title">Campus Map!</h1>
                <div>
                    {/* TODO: define props in the Map component and pass them in here */}
                    <Map edges={this.state.edgeList}/>
                </div>
                <RouteEdges
                    onChange={(value) => {
                        // TODO: Modify this onChange callback to store the edges in the state
                        this.setState({edgeList: value});
                    }}
                />
            </div>
        );
    }

}

export default App;
