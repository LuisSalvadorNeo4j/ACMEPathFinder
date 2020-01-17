
package com.acme.pf;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

public class PathFinder{

    @Context
    public GraphDatabaseService db;

    @UserFunction
    @Description("com.acme.pf.traverse(n, ['r1','r2',...], delimiter) - traverse the graph with the given delimiter.")
    public List<Path> traverse(@Name("location") Node location,@Name("rels") List<String> rels,@Name("limit") long limit ) throws IOException
    {
        List<Path> result = getAssets(location,rels,limit).collect(Collectors.toList());

        return result;
    }


    // tag::getAssets[]
    private Stream<Path> getAssets( final Node startNode, final List<String> rels,final long limit )
    {
     //   TraversalDescription td = db.traversalDescription()
     //           .breadthFirst()
     //           .relationships( RelTypes.ENTRANCE)
        //           .relationships( RelTypes.ESCALATOR)
        //            .relationships( RelTypes.SURFACE_LEVEL)
        //           .relationships( RelTypes.RAMP)
        //            .evaluator(Evaluators.toDepth(limit));

        TraversalDescription td = db.traversalDescription()
                   .breadthFirst();

        //TraversalDescription td_aux = td;

        for (Path path : td.evaluator(Evaluators.toDepth((int)limit)).traverse(startNode)) {
            for (String rel:rels)
                td = td.relationships(RelationshipTypes.valueOf(rel));
        }

        td = td.evaluator(Evaluators.toDepth((int)limit));


        return td.traverse( startNode ).stream();
    }
}




