package org.insa.graphs.algorithm.utils;

public class Label {
	
	public Node currentNode,father;
	
	public Label(Node currentNode, double cost, Node father) {
		this.currentNode = currentNode;
		boolean marque = false; //True when minimal cost of this node is definitively known by the algorithm
		this.father = father;

	}
	
	public void getCost() {
		return;
	}
	
}
