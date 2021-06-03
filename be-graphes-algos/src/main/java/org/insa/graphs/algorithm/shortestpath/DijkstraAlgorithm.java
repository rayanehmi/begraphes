package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @SuppressWarnings("null")
	@Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        
        // data initialization
    	Graph graph = data.getGraph();
    	Label[] labelList;
    	labelList = new Label[graph.size()];
        Label min;
        Label nextLabel;
        Node minNode;
        Node nextNode;
    	double arcCost;
    	BinaryHeap<Label> heap = new BinaryHeap<Label>();
    	
    	// origin label initialization
    	Node origin = data.getOrigin();
    	Label Label_origin = new Label();
    	Label_origin.currentNode = origin;
    	Label_origin.cost = 0;
    	
    	// label list initialization (node-label linking)
    	for(Node n : graph.getNodes()) {
    		
    		Label l = new Label();
    		l.cost = Double.POSITIVE_INFINITY;
    		l.currentNode = n;
    		labelList[n.getId()] = l;
    		
    	}
    	
    	// supersede origin label
    	int var = origin.getId();
    	labelList[var] = Label_origin;
    	
    	// insert origin label in the heap
    	heap.insert(Label_origin);
    	
    	// keep going until heap is empty
    	while ((labelList[data.getDestination().getId()].marked == false)) {
    		
    		// find minimal cost label
    		min = heap.findMin();
    		minNode = min.currentNode;
    		//System.out.print("minimalNode ID" + minNode.getId() + " cost " + min.cost);
            //System.out.print("\n");
    		min.marked = true;
    		heap.remove(min);
    		
    		// Look into its successors
    		
    		for (Arc arc : minNode.getSuccessors()) {
    			
    			// Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
                
                //System.out.print(minNode.getSuccessors().size());
                //System.out.print("\n");
    			
    			nextNode = arc.getDestination();
    			
    			//System.out.print("successor id" + nextNode.getId() + " cost " + labelList[nextNode.getId()].cost);
                //System.out.print("\n");
    			
    			arcCost = data.getCost(arc);
    			
    			nextLabel = labelList[nextNode.getId()];
    			try {
    				heap.remove(nextLabel);
    			}catch(ElementNotFoundException ignored){
    				
    			}
    			
    			// If we find a shorter path, replace cost 
    			
    			
    			if (nextLabel.marked == false) {
    				

        			if (min.cost + arcCost < nextLabel.cost) {
        				
        				notifyNodeReached(arc.getDestination());
        				
        				nextLabel.cost = min.cost + arcCost;
        				nextLabel.father = arc;
        				
        			}
        			//System.out.print("successor ID " + nextNode.getId() + " cost update " + labelList[nextNode.getId()].cost);
                    //System.out.print("\n");
    				heap.insert(nextLabel);
    			}
    		}
    		//min.marked = true;
    		//heap.remove(min);	
    	}
    	
    	ShortestPathSolution solution;
		// Destination has no predecessor, the solution is infeasible...
        if (labelList[data.getDestination().getId()].cost == Double.POSITIVE_INFINITY) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        else {
            // node list initialization
            notifyDestinationReached(data.getDestination());

            ArrayList<Node> pathNodes = new ArrayList<>();
            pathNodes.add(data.getDestination());
            Node node = data.getDestination();
            
            //travel back to origin using fathers
            while (!node.equals(data.getOrigin())) {
                Node fatherNode = (labelList[node.getId()].father).getOrigin();
                pathNodes.add(fatherNode);
                node = fatherNode;
            }
            Collections.reverse(pathNodes);

            // final path creation
            
            Path solutionPath;
            if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
                solutionPath = Path.createShortestPathFromNodes(graph, pathNodes);
            } else {
                solutionPath = Path.createFastestPathFromNodes(graph, pathNodes);
            }
            solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
        }
    	
        return solution;
        
    }


}