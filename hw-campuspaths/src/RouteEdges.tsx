import React, {Component} from 'react';
import MapLine from "./MapLine";

interface EdgeListProps {
    onChange(edges: MapLine[]): void;  // called when a new edge list is ready
}

interface EdgeState{
    textInfo: string;// way to store the drop-down menu information?
    drawEdges: MapLine[]; // the MapLines that have been parsed from the users' input
}


interface Path{
    start: Point; // the start point
    path: [Segment]; // the array of segments that represent the path
    cost: number; // the cost from the start point to the end point
}

interface Segment{
    start: Point; // the start point
    end: Point; // the end point
    cost: number; // the cost from the start point to the end point
}

interface Point {
    x: number; // the x coordinate of the point
    y: number; // the x coordinate of the point
}


/**
 * CHANGE THIS A text field that allows the user to enter the list of edges.
 * AND THIS Also contains the buttons that the user will use to interact with the app.
 */
// do I need to extend path/segment/point?
class RouteEdges extends Component<EdgeListProps, EdgeState> {

    constructor(props: any) {
        super(props);
        this.state = {
            textInfo: "",
            drawEdges: [],
        }
    }
    /*
    componentDidMount() {
        this.testJsonInput();
    }
    */
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    // change this to a non text area?
                    rows={5}
                    cols={30}
                    onChange={(e) => this.setState({textInfo: e.target.value})}
                    value={this.state.textInfo}
                /> <br/>
                <button onClick={() => {this.testJsonInput()}}>Draw</button>
                <button onClick={() => this.clearConsole()}>Clear</button>

            </div>
        );
    }

    /**
     * clears the lines on the screen
     */
    clearConsole(){
        this.setState({drawEdges: []})
        this.props.onChange([])
    }

    /**
     * main function for drawing the lines from given user input
     */
    testJsonInput = async () => {
        try{
            let arr = this.state.textInfo.split(' ');
            let i = arr[0];
            let j = arr[1];
            let response = await fetch('http://localhost:4567/findPath?start=' + i + '&end=' + j);
            if (!response.ok){
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let object = (await response.json()) as Path;
            if (object === undefined){
                alert("json parsing failed!");
            }
            console.log(object);
            let arrayOfMapLines = Array<MapLine>()
            for (let i = 0; i < object.path.length; i++){
                let numEdges = new MapLine({
                    color: "red",
                    x1: object.path[i].start.x,
                    y1: object.path[i].start.y,
                    x2: object.path[i].end.x,
                    y2: object.path[i].end.y
                })
                console.log(numEdges);
                arrayOfMapLines.push(numEdges);
            }
            this.setState({drawEdges: arrayOfMapLines})
            this.props.onChange(arrayOfMapLines)

        } catch(e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

}

export default RouteEdges;
