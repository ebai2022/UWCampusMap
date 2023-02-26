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
import MapLine from "./MapLine";

interface EdgeListProps {
    onChange(edges: MapLine[]): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}
// FEAT ELIJAH: DEFINE A STATE and make it hold the value variable. USE A CONSTRUCTOR
interface EdgeState{
    textInfo: string;
    drawEdges: MapLine[];
    //what is a JSX.Element[] ? make this an array of map lines
}
/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeState> {

    constructor(props: any) {
        super(props);
        this.state = {
            textInfo: "delete this and type stuff here!",
            drawEdges: []
        }
    }

    //maybe this should return a MapLine?
    parseString = (s: string): MapLine => {
        let stringEdges = s.split(" ");
        // check whether the coordinates are 0<=x<=4000
        let numEdges = new MapLine({
            color: stringEdges[0],
            x1: stringEdges[1],
            y1: stringEdges[2],
            x2: stringEdges[3],
            y2: stringEdges[4],
        })
        return numEdges;
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => {this.changeName(e)}}
                    value={this.state.textInfo}
                /> <br/>
                <button onClick={(f) => {this.drawLines(f)}}>Draw</button>
                <button onClick={(g) => {this.clearConsole(g)}}>Clear</button>
            </div>
        );
    }

    changeName(e: React.ChangeEvent<HTMLTextAreaElement>) {
        this.setState({textInfo: e.target.value});
    }

    //FIGURE THIS OUT - store in an array of maplines?
    drawLines(f: React.MouseEvent<HTMLButtonElement>){
        let arrayOfLines = f.currentTarget.value.split('\n');
        let arrayOfMapLines = Array<MapLine>();
        for (let i = 0; i < arrayOfLines.length; i++){
            arrayOfMapLines.push(this.parseString(arrayOfLines[i]));
        }
        this.setState({drawEdges: arrayOfMapLines})
    }

    clearConsole(g: React.MouseEvent<HTMLButtonElement>){
        this.setState({textInfo: ""})
    }
}

export default EdgeList;
