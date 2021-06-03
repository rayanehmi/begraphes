package org.insa.graphs.model;

public class Label implements Comparable <Label>{
	
	public Node currentNode;
	public Arc father;
	public boolean marked = false;
	public double cost;
	
	public double GetCost (Label label) {
		return label.cost;
	}
	
	public int compareTo(Label label) {
		if (this.cost <= label.cost) {
			return 1;
		}
		else {
			return 0;
		}
	}
}