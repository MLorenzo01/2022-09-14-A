package it.polito.tdp.itunes.model;

public class Edge {
	Album a1;
	Album a2;
	public Album getA1() {
		return a1;
	}
	public void setA1(Album a1) {
		this.a1 = a1;
	}
	public Album getA2() {
		return a2;
	}
	public void setA2(Album a2) {
		this.a2 = a2;
	}
	public Edge(Album a1, Album a2) {
		this.a1 = a1;
		this.a2 = a2;
	}
	
	
}
