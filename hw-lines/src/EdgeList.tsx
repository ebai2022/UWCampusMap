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

interface EdgeState{
    textInfo: string; // the text that the user has entered
    drawEdges: MapLine[]; // the MapLines that have been parsed from the users' input
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

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => this.setState({textInfo: e.target.value})}
                    value={this.state.textInfo}
                /> <br/>
                <button onClick={() => {this.drawLines()}}>Draw</button>
                <button onClick={() => this.clearConsole()}>Clear</button>
            </div>
        );
    }

    /**
     * clears both the content in the box and the lines on the screen
     */
    clearConsole(){
        this.setState({textInfo: "", drawEdges: []})
        this.props.onChange([])
    }

    /**
     * main function for drawing the lines from given user input
     */
    drawLines(){
        let arrayOfLines = this.state.textInfo.trim().split('\n')
        let arrayOfMapLines = Array<MapLine>()
        for (let i = 0; i < arrayOfLines.length; i++){
            let edge = this.parseString(arrayOfLines[i])
            if (edge !== undefined){
                arrayOfMapLines.push(edge)
            }
        }
        this.setState({drawEdges: arrayOfMapLines})
        this.props.onChange(arrayOfMapLines)
    }

    /**
     * helper method for drawLines. Handles the parsing of each line and responds to bad user input.
     * input should be in the form "x1 y1 x2 y2 color"
     *
     * @param s the string to parse
     */
    parseString = (s: string): MapLine | undefined=> {
        let stringEdges = s.trim().split(" ");
        // checking input length
        if (stringEdges.length !== 5){
            alert("bad input! Input must be in the form of: x1 y1 x2 y2 color")
        } else{
            let firstX = parseFloat(stringEdges[0])
            let firstY = parseFloat(stringEdges[1])
            let secondX = parseFloat(stringEdges[2])
            let secondY = parseFloat(stringEdges[3])
            // can check if these are numbers (isNaN function)
            if (isNaN(firstX) || isNaN(firstY) || isNaN(secondX) || isNaN(secondY)){
                alert("bad input! all the coordinates (x1 y1 x2 y2) must be numbers!")
            }
            // check whether the coordinates are 0<=x<=4000
            if (firstX < 0 || firstX > 4000 || firstY < 0 || firstY > 4000 || secondX < 0 || secondX > 4000 || secondY < 0 || secondY > 4000){
                alert("bad input! all coordinates must be between 0 and 4000 (inclusive)")
            } else{
                let numEdges = new MapLine({
                    color: stringEdges[4],
                    x1: firstX,
                    y1: firstY,
                    x2: secondX,
                    y2: secondY
                })
                return numEdges;
            }
        }
        // returns undefined if the input explodes
        return undefined;
    }
}

export default EdgeList;
