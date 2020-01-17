package com.acme.pf;

import org.neo4j.graphdb.RelationshipType;

public enum RelationshipTypes implements RelationshipType {

    ENTRANCE,
    ESCALATOR,
    SURFACE_LEVEL,
    RAMP

}
