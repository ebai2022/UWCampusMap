import React, {Component} from 'react';
import MapLine from "./MapLine";

interface EdgeListProps {
    onChange(edges: MapLine[]): void;  // called when a new edge list is ready

}

interface EdgeState{
    drawEdges: MapLine[]; // the MapLines that have been parsed from the users' input
    buildingFullNames: string[]; // all the full building names on campus map
    buildingShortNames: string[]; // all the short building names on campus map
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
    y: number; // the y coordinate of the point
}


/**
 * Drop down menus that allow the user to select two buildings and see the shortest path between them.
 * Also contains the buttons that the user will use to draw and clear that path
 */
class RouteEdges extends Component<EdgeListProps, EdgeState> {

    constructor(props: any) {
        super(props);
        this.state = {
            drawEdges: [],
            buildingFullNames: [],
            buildingShortNames: [],
            startBuilding: "",
            endBuilding: ""
        }
    }

    // instantly loads in the buildings when the page is set
    componentDidMount() {
        this.getBuildings();
    }

    render() {
        // creates all the options for the selection
        let list: any[] = [];
        list.push(<option key={0}> Select</option>)
        for(let i = 0; i < this.state.buildingFullNames.length; i++){
            // displayed string on the dropdown menu has both the short name and long name
            list.push(<option key={i+1} value={this.state.buildingShortNames[i]}>
                {this.state.buildingFullNames[i]}</option>)
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
     * gets all the buildings that exist on campus with both their short and long names
     */
    getBuildings = async () => {
        try{
            let response = await fetch('http://localhost:4567/buildingNames');
            if (!response.ok){
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
                // return if something goes wrong
            }
            let object = (await response.json()) as string[];
            if (object === undefined){
                alert("json parsing failed!");
                return;
                // return if something goes wrong
            }
            let buildingFull = Array<string>();
            let buildingShort = Array<string>();
            Object.entries(object).forEach(entry => {
                let key:string = entry[0];
                let value:string = entry[1];
                buildingFull.push(key + " (" + value + ")")
                buildingShort.push(key);
            })
            this.setState({buildingFullNames: buildingFull, buildingShortNames: buildingShort})
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
                this.setState({drawEdges: []})
                this.props.onChange([])
            } else {
                let response = await fetch('http://localhost:4567/findPath?start=' +
                    this.state.startBuilding + '&end=' + this.state.endBuilding);
                if (!response.ok) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    return;
                    // return if something goes wrong
                }
                let object = (await response.json()) as Path;
                if (object === undefined) {
                    alert("json parsing failed!");
                    return;
                    // return if something goes wrong
                }
                let arrayOfMapLines = Array<MapLine>()
                // creating all the lines to be drawn
                for (let i = 0; i < object.path.length; i++) {
                    let numEdges = new MapLine({
                        color: "red",
                        x1: object.path[i].start.x,
                        y1: object.path[i].start.y,
                        x2: object.path[i].end.x,
                        y2: object.path[i].end.y
                    })
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
