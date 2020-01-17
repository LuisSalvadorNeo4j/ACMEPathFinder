package com.acme.pf;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.BranchState;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.PathEvaluator;

import java.util.List;

public class PathFinderEvaluator implements PathEvaluator<PathFinderState> {

    private final Node destination;
    private MutableDouble shortestSoFar;
    private final List<Node> exclusionList;

    public PathFinderEvaluator(Node destination, MutableDouble shortestSoFar, List<Node> exclusionList) {
        this.destination = destination;
        this.shortestSoFar = shortestSoFar;
        this.exclusionList = exclusionList;
    }

    @Override
    public Evaluation evaluate(Path path, BranchState<PathFinderState> state) {
        if(path.length() == 0){
            return Evaluation.EXCLUDE_AND_CONTINUE;
        }

        if(path.length() > 100){
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        if ( state.getState().getCost() >= (Double) shortestSoFar.getValue() ) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }
        if (path.endNode().equals(destination)){
            //System.out.println("path length: " + path.length() + " cost: " + state.getState().getCost());

            shortestSoFar.setValue(state.getState().getCost());

            return Evaluation.INCLUDE_AND_PRUNE;
        }

        if (exclusionList != null){
            if(exclusionList.contains(path.endNode())){
                return Evaluation.EXCLUDE_AND_PRUNE;
            }
        }

        if (path.endNode().hasLabel(Labels.Point)){
            if(path.endNode().hasLabel(Labels.Escalator)){
                return Evaluation.EXCLUDE_AND_CONTINUE;
            } else {
                return Evaluation.EXCLUDE_AND_PRUNE;
            }
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

    @Override
    public Evaluation evaluate(Path path) {
        return null;
    }
}
