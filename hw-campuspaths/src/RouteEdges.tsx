import React, {Component} from 'react';
import MapLine from "./MapLine";

interface EdgeListProps {
    onChange(edges: MapLine[]): void;  // called when a new edge list is ready

}

interface EdgeState{
    drawEdges: MapLine[]; // the MapLines that have been parsed from the users' input
    buildingNames: string[]; // all the short building names on campus map
    startBuilding: string; // the building the start at
    endBuilding: string; // the building to end at
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
 * Drop down menus that allow the user to select two buildings and see the shortest path between them
 * Also contains the buttons that the user will use to draw and clear that path
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

    // instantly loads in the buildings when the page is set
    componentDidMount() {
        this.getBuildings();
    }

    render() {
        // WHAT DOES UNIQUE KEY VALUE PAIRS LOOK LIKE?
        // creates all the options for the selection
        let list: any[] = [];
        list.push(<option>Select</option>)
        for(let i = 0; i < this.state.buildingNames.length; i++){
            list.push(<option value={this.state.buildingNames[i]}>{this.state.buildingNames[i]}</option>)
        }
        return (
            <div id="edge-list">
                Choose your start location!
                <br/>
                <select
                    value={this.state.startBuilding}
                    onChange={(e) => this.setStart(e.target.value)}
                >
                    {list}
                </select>
                <br/>
                Choose your end location!
                <br/>
                <select
                        value={this.state.endBuilding}
                        onChange={(e) => this.setEnd(e.target.value)}
                >
                    {list}
                </select>
                <br/>
                Press Draw to see the shortest path between your two buildings!
                <br/>
                <button onClick={() => {this.getPaths()}}>Draw</button>
                <br/>
                Press Clear to reset the entire application!
                <br/>
                <button onClick={() => this.clearConsole()}>Clear</button>
                <br/>
            </div>
        );
    }

    /**
     * sets the start building's short name
     */
    setStart(e: string): void{
        this.setState({startBuilding: e});
    }

    /**
     * sets the end building's short name
     */
    setEnd(e: string): void{
        this.setState({endBuilding: e});
    }


    /**
     * clears the page to reset everything to their initial states
     */
    clearConsole(){
        this.setState({drawEdges: [], startBuilding: "", endBuilding: ""})
        this.props.onChange([])
    }

    /**
     * gets all the buildings that exist on campus as a list of their short names
     */
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
                //console.log(object[i])
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
            if (this.state.startBuilding === "" || this.state.endBuilding === "" ||
                    this.state.endBuilding === "Select" || this.state.startBuilding === "Select"){
                alert("boxes must be filled!")
                // or I could honestly just clear the lines here or do nothing...hmmm
                // double check that this doesn't royally screw anything up
                this.setState({drawEdges: []})
                this.props.onChange([])
            } else {
                //console.log(this.state.startBuilding)
                //console.log(this.state.endBuilding)
                let response = await fetch('http://localhost:4567/findPath?start=' +
                    this.state.startBuilding + '&end=' + this.state.endBuilding);
                if (!response.ok) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    return;
                }
                let object = (await response.json()) as Path;
                if (object === undefined) {
                    alert("json parsing failed!");
                }
                //console.log(object);
                let arrayOfMapLines = Array<MapLine>()
                for (let i = 0; i < object.path.length; i++) {
                    let numEdges = new MapLine({
                        color: "red",
                        x1: object.path[i].start.x,
                        y1: object.path[i].start.y,
                        x2: object.path[i].end.x,
                        y2: object.path[i].end.y
                    })
                    //console.log(numEdges);
                    arrayOfMapLines.push(numEdges);
                }
                this.setState({drawEdges: arrayOfMapLines})
                this.props.onChange(arrayOfMapLines)
            }
        } catch(e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };


}

export default RouteEdges;
