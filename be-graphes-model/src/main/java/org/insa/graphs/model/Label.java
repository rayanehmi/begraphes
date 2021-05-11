package org.insa.graphs.model;

public class Label {
	
	public Node currentNode,father;
	public boolean marked;
	public double cost;

	public Label(int id, Node currentNode, double cost, Node father) {
		this.currentNode = currentNode;
		boolean marked = false; //True when minimal cost of this node is definitively known by the algorithm
		this.father = father;

	}
	
	public void getCost() {
		return ;
	}
	
}
