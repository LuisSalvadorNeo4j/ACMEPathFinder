package com.acme.pf;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.BranchState;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PathFinderExpander implements PathExpander<PathFinderState>{
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;
    private final double requiredCapacity;

    public PathFinderExpander(ZonedDateTime startTime, ZonedDateTime endTime , double requiredCapacity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.requiredCapacity = requiredCapacity;
    }


    @Override
    public Iterable<Relationship> expand(Path path, BranchState<PathFinderState> state) {

        //System.out.println(path);
        if (path.endNode().hasLabel(Labels.Gatelines)){
            return expandFromGatelines(path, state);
        }

        if (path.endNode().hasLabel(Labels.Escalator) && path.lastRelationship().isType(RelationshipTypes.RAMP)){
            return path.endNode().getRelationships(RelationshipTypes.RAMP, Direction.OUTGOING );
        }



        return Collections.EMPTY_LIST;
    }

    private Iterable<Relationship> expandFromGatelines(Path path, BranchState<PathFinderState> state){
        PathFinderState pathFinderState = state.getState().copy();

        // Increment the cost in the path state
        // TODO: Define cost calculation that takes into account the entire algorithm
        Node directionalChannel = path.endNode();

        //TODO put back the latency
        //if ( directionalChannel.hasProperty( Properties.latency ) ) {
        pathFinderState.incrementCost(10.0d);
        //}

        state.setState( pathFinderState );
        return path.endNode().getRelationships(RelationshipTypes.RAMP, Direction.OUTGOING );
    }

    @Override
    public PathExpander<PathFinderState> reverse() {
        return null;
    }
}
