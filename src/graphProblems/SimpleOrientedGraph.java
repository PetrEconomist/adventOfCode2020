package graphProblems;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Describes graph and provides functions on the graph
 * Graph edges have their values.
 * Tree type - only one predecessor, can be multiple successors
 * @author Petr
 *
 */
public class SimpleOrientedGraph {
	
	private HashSet<Edge> edges;
	
	public SimpleOrientedGraph(){
		edges = new HashSet<Edge>();
	}
	
	/**
	 * Adds edge into the graph
	 * @param fromVertex
	 * @param toVertex
	 * @param value
	 */
	public void addEdge(String fromVertex, String toVertex, double value) {
		Edge edge = new Edge(fromVertex, toVertex, value);
		if(edges.contains(edge)) {
			//case if attempt to add already existing edge
			throw new IllegalArgumentException("Edge already exists");
		}else {
			edges.add(edge);
		}
	}
	
	public void addEdge(String fromVertex, String toVertex, Integer value) {
		addEdge(fromVertex, toVertex, (double) value);
	}
	
	/**
	 * Gets all leaves of the given vertex
	 * @param vertex of which we find leaves
	 * @return leaves of the vertex
	 */
	public HashSet<String> getLeaves(String vertex){
		//output
		HashSet<String> leaves = new HashSet<String>();
		//get all siblings of the vertex
		HashSet<String> siblings = getSuccessors(vertex);
		//loop over sibling, if leaves, add to output; if not, get its leaves
		for(String sibling : siblings) {
			if(isLeaf(sibling)) {
				leaves.add(sibling);
			}else {
				leaves.addAll(getLeaves(sibling));
			}
		}
		return leaves;
	}
	
	public HashSet<String> getPredecessorsAll(String vertex){
		//initiate set for output
		HashSet<String> output = new HashSet<String>();
		//find direct predecessor
		HashSet<String> predecessors = getPredecessorsDirect(vertex);
		//add predecessors to output
		output.addAll(predecessors);
		for(String predecessor : predecessors) {
			//vertex is not root, find its predecessors
			output.addAll(getPredecessorsAll(predecessor));
		}
		return output;
	}
	
	public HashSet<String> getPredecessorsDirect(String vertex){
		if(vertex.isEmpty()) return null;
		//find direct predecessoring edge
		HashSet<String> predecessors = new HashSet<String>();
		for(Edge e : edges) {
			if(e.getToVertex().equals(vertex)) {
				predecessors.add(e.getFromVertex());
			}
		}
		return predecessors;
	}
	
	//not sure how adjacent vertices in ordered tree are named
	public HashSet<String> getSuccessors(String vertex){
		//Initiate set for output
		HashSet<String> output = new HashSet<String>();
		//loop over graph's edges
		for(Edge e : edges) {
			//if edge's from vertex equals vertex
			if(e.getFromVertex().equals(vertex)) {
				//store vertex "to" (sibling)
				output.add(e.getToVertex());
			}
		}
		return output;
	}
	
	private boolean isLeaf(String vertex) {
		//loop over edges if vertex is "parent" vertex
		for(Edge e: edges) {
			if(e.getFromVertex().equals(vertex)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	//iterate further
	private LinkedList<Edge> getSuccessorEdgesAll(String vertex, double volume){
		//Initiate set for output
		LinkedList<Edge> output = new LinkedList<Edge>();
		//get inner vertices
		LinkedList<Edge> successors = getSuccessorEdgesDirect(vertex);
		//loop over edges and find 
		for(Edge e : successors) {
			//multiply successors with current volume of the vertex
			Edge multipliedEdge = new Edge(vertex, e.toVertex, e.getEdgeValue()*volume);
			//add found edge (multiplied)
			output.add(multipliedEdge);
			//find its successors
			output.addAll(getSuccessorEdgesAll(multipliedEdge.getToVertex(), multipliedEdge.getEdgeValue()));
		}
		return output;
	}
	
	public LinkedList<String> getSuccessorEdgesAllAsString(String vertex, double volume){
		LinkedList<String> output = new LinkedList<String>();
		LinkedList<Edge> outputList = getSuccessorEdgesAll(vertex, volume);
		for(Edge e : outputList){
			output.add(e.toVertex + ";" + e.getEdgeValue());
		}
		return output;
	}
		
	/**
	 * Gets direct edges with successors
	 * @param vertex of which successors are to be found
	 * @return edges with successors
	 */
	private LinkedList<Edge> getSuccessorEdgesDirect(String vertex){
		//Initiate set for output
		LinkedList<Edge> output = new LinkedList<Edge>();
		//iterate over edges and find edges with successors
		for(Edge e : edges) {
			if(e.getFromVertex().equals(vertex)) {
				output.add(e);
			}
		}
		return output;
	}

	private class Edge{
		private String fromVertex;
		private String toVertex;
		private Double edgeValue;
		
		Edge(String fromVertex, String toVertex, Double value){
			this.fromVertex = fromVertex;
			this.toVertex = toVertex;
			this.edgeValue = value;
		}
		
		String getFromVertex() {
			return fromVertex;
		}
		
		String getToVertex() {
			return toVertex;
		}
		
		double getEdgeValue() {
			return edgeValue;
		}
		
		void setEdgeValue(double value) {
			this.edgeValue = value;
		}
		
		
		/**
		 *  Overriding equals() to compare two Edge objects 
		 */
		@Override
		public boolean equals(Object o) {
			
			// If the object is compared with itself then return true   
	        if (o == this) { 
	            return true; 
	        } 
	  
	        /* Check if o is an instance of Complex or not 
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof Edge)) { 
	            return false; 
	        } 
	          
	        // typecast o to Complex so that we can compare data members  
	        Edge e = (Edge) o; 
	          
	        // Compare the data members and return accordingly 
			return(this.getFromVertex().equals(e.getFromVertex()) &&
				this.getToVertex().equals(e.getToVertex())
			); 
		}
		
	}
	
}
