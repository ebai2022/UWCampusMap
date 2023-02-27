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

    //how do i communicate this stuff back to the app whatever and then to map???
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
    parseString = (s: string): MapLine | undefined=> {
        let stringEdges = s.trim().split(" ");
        if (stringEdges.length != 5){
            // CHECK IF COLOR IS FIRST OR LAST
            alert("bad input! Input must be in the form of: color x1 x2 y1 y2")
        } else{
            // can check if these are numbers (isNaN function)
            let firstX = parseFloat(stringEdges[1])
            let firstY = parseFloat(stringEdges[2])
            let secondX = parseFloat(stringEdges[3])
            let secondY = parseFloat(stringEdges[4])
            // check whether the coordinates are 0<=x<=4000
            if (firstX < 0 || firstX > 4000){
                alert("bad input! all coordinates must be between 0 and 4000 (inclusive)")
            } else{
                let numEdges = new MapLine({
                    color: stringEdges[0],
                    x1: firstX,
                    y1: firstY,
                    x2: secondX,
                    y2: secondY,
                })
                return numEdges;
            }
        }
        // check if this screws something up with null pointers
        return undefined;
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
                <button onClick={() => this.setState({textInfo: "", drawEdges: []})}>Clear</button>
            </div>
        );
    }

    //changeName(e: React.ChangeEvent<HTMLTextAreaElement>) {
    //    this.setState({textInfo: e.target.value});
    //}
    //FIGURE THIS OUT - store in an array of maplines?
    drawLines(){
        // TODO: validate user input
        // should this take f or should it take textInfo?
        let arrayOfLines = this.state.textInfo.split('\n')
        //let arrayOfLines = f.currentTarget.value.split('\n');
        let arrayOfMapLines = Array<MapLine>()
        for (let i = 0; i < arrayOfLines.length; i++){
            let m = this.parseString(arrayOfLines[i])
            if (m != undefined){
                arrayOfMapLines.push(m)
            }
        }
        this.setState({drawEdges: arrayOfMapLines})
    }
    //clearConsole(g: React.MouseEvent<HTMLButtonElement>){
        // make this clear the lines, not just the box
    //    this.setState({textInfo: "", drawEdges: []})
    //}
}

export default EdgeList;
