package org.insa.graphs.algorithm.shortestpath;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        /*
        for each vertex V in G          //initialization; initial path set to infinite
	        path[V] <- infinite
	        previous[V] <- NULL
	        If V != S, add V to Priority Queue PQueue
	    path [S] <- 0
     
	    while PQueue IS NOT EMPTY
	        U <- Extract MIN from PQueue
	        for each unvisited adjacent_node  V of U
	            tempDistance <- path [U] + edge_weight(U, V)
	            if tempDistance < path [V]
	                path [V] <- tempDistance
	                previous[V] <- U
	    return path[], previous[]
	   	*/
        
        return solution;
    }

}
