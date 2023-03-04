import React, {Component} from 'react';
import MapLine from "./MapLine";

interface EdgeListProps {
    onChange(edges: MapLine[]): void;  // called when a new edge list is ready
    onLoad(buildings: string[]): void;

}

interface EdgeState{
    drawEdges: MapLine[]; // the MapLines that have been parsed from the users' input
    buildingNames: string[];
    startBuilding: string;
    endBuilding: string;
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
            drawEdges: [],
            buildingNames: [],
            startBuilding: "",
            endBuilding: ""
        }
    }

    componentDidMount() {
        this.getBuildings();
    }

    render() {
        let list: any[] = [];
        list.push(<option>Select</option>)
        for(let i = 0; i < this.state.buildingNames.length; i++){
            list.push(<option value={this.state.buildingNames[i]}>{this.state.buildingNames[i]}</option>)
        }
        return (
            <div id="edge-list">
                Enter your start location!
                <br/>
                <select onLoad={(names) => this.getBuildings}
                    placeholder={"choose a building to start!"}
                    value={this.state.startBuilding}
                    onChange={(e) => this.setStart(e.target.value)}
                >
                    {list}
                </select>
                <br/>
                Enter your end location!
                <br/>
                <select onLoad={(names) => this.getBuildings}
                        placeholder={"choose a building to end!"}
                        value={this.state.endBuilding}
                        onChange={(e) => this.setEnd(e.target.value)}
                >
                    {list}
                </select>
                <br/>
                <button onClick={() => {this.getPaths()}}>Draw</button>
                <br/>
                <button onClick={() => this.clearConsole()}>Clear</button>
                <br/>
            </div>
        );
    }

    setStart(e: string): void{
        this.setState({startBuilding: e});
    }

    setEnd(e: string): void{
        this.setState({endBuilding: e});
    }


    /**
     * clears the lines on the screen
     */
    clearConsole(){
        this.setState({drawEdges: []})
        this.props.onChange([])
    }

    getBuildings = async () => {
        try{
            let response = await fetch('http://localhost:4567/buildingNames');
            if (!response.ok){
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let object = (await response.json()) as string[];
            if (object === undefined){
                alert("json parsing failed!");
            }
            let buildings = Array<string>();
            for (let i = 0; i < object.length; i++){
                buildings.push(object[i]);
                console.log(object[i])
            }
            this.setState({buildingNames: buildings})
        } catch(e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };


    /**
     * main function for drawing the lines from given user input
     */
    getPaths = async () => {
        try{
            let response = await fetch('http://localhost:4567/findPath?start=' + this.state.startBuilding + '&end=' + this.state.endBuilding);
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
