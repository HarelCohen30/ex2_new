package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class DWGraph_DS implements directed_weighted_graph {

	private HashMap<Integer, node_data> vertices;
	//private HashMap<Integer, node_data> src_vertices;
	//private HashMap<Integer, node_data> dest_vertices;
	public Collection<node_data> GraphVertices;
	//public Collection<node_data> GraphSrcVertices;
	//public Collection<node_data> GraphDestVertices;
	public Collection<edge_data> edges;
	int nodeCounter = 0;
	int edgeCounter=0;
	int mc = 0;

	//empty constructor
	public DWGraph_DS() {
		vertices = new HashMap<Integer, node_data>();
		//src_vertices = new HashMap<Integer, node_data>();
		//dest_vertices = new HashMap<Integer, node_data>();
		GraphVertices = new ArrayList<node_data>();
		//GraphSrcVertices = new ArrayList<node_data>();
		//GraphDestVertices = new ArrayList<node_data>();
		edges=new ArrayList<edge_data>();
	}

	//constructor for the copy function creating a deep copy
	public DWGraph_DS(Collection<node_data> NewGraphVertices, Collection<edge_data> edges) {
		this.GraphVertices = NewGraphVertices;
		//this.GraphDestVertices = NewGraphDestVertices;
		this.vertices = new HashMap<Integer, node_data>();
		//this.src_vertices = new HashMap<Integer, node_data>();
		//this.dest_vertices = new HashMap<Integer, node_data>();
		this.edges=edges;
		for (node_data verts : GraphVertices) {
			addNode(verts);
		}
		for (node_data verts : GraphVertices)
		{
			verts.setInfo(verts.getInfo());
			verts.setTag(verts.getTag());
			Collection<node_data> col = ((NodeData) verts).getNi();
			Iterator<node_data> ite = col.iterator();
			while(ite.hasNext()) {
				node_data N=ite.next();
				connect(verts.getKey(),N.getKey(),N.getWeight());
			}
		}
	}


	public node_data getNode(int key) {
		if (vertices.get(key) != null)
			return vertices.get(key);
		else
			return null;
	}

	public edge_data getEdge(int src, int dest) {
		if(hasEdge(src,dest))
		{
			//edge_data edge = new EdgeData();
			Iterator <edge_data> iter = edges.iterator();
			while (iter.hasNext())
			{
				edge_data e = iter.next();
				if (e.getDest() == dest && e.getSrc()==src)
					return e;
			}
			return null;
		}
		else
			return null;
	}

	public void addNode(node_data n) {
		boolean ForCountNodes = false;
		if (!vertices.containsValue(n) && n != null)
		{
			vertices.put(n.getKey(), n);
			ForCountNodes = true;
		}
		if (ForCountNodes)//the node added to graph
			nodeCounter++;
	}

	public void addNode(int key)
	{
		addNode(new NodeData(key));
	}

	public void connect(int src, int dest, double w) {
		if(vertices.containsKey(src) &&  src!=dest ){
			if(!hasEdge(src, dest)) {
				((NodeData) vertices.get(src)).addNi(vertices.get(dest));
				((NodeData) vertices.get(dest)).addDestNi(vertices.get(src));
				edgeCounter++;
				mc++;
				edge_data edge = new EdgeData(src,dest,w);
				edges.add(edge);
			}
			else {
				edge_data edge = getEdge(src, dest);
				edges.remove(edge);
				((EdgeData) edge).setWeight(w);
				edges.add(edge);
			}
		}
	}

	public Collection <node_data> getV() {
		if (vertices != null)
		{
			return vertices.values();
		}
		return null;
	}

	public Collection <edge_data> getE(int node_id) {
		NodeData src = (NodeData) vertices.get(node_id);
		Collection<edge_data> coll = new  ArrayList<edge_data>();
		Iterator<node_data> iter = src.getNi().iterator();
		while (iter.hasNext())
		{
			node_data b = iter.next();
			edge_data edge = getEdge(src.getKey(),b.getKey());
			coll.add(edge);
		}
		return coll;
	}

	//Maurice, Please check this method again/////////////////////////////
	public node_data removeNode(int key){
		if (vertices.containsKey(key))
		{
			NodeData nodeToRemove = (NodeData) vertices.get(key);
			Collection<node_data> neighborsOfDel = nodeToRemove.getNi();
			for (node_data neighbor : neighborsOfDel)
			{
				NodeData Ni = (NodeData) neighbor;
				Ni.removeNode(nodeToRemove);
				edgeCounter--;
			}

			Collection<node_data> neighborsOfDel2 = nodeToRemove.getDestNi();
			for (node_data neighbor : neighborsOfDel2)
			{
				NodeData Ni = (NodeData) neighbor;
				Ni.removeNode(nodeToRemove);
				edgeCounter--;
			}
			vertices.remove(key);
			neighborsOfDel.clear();
			neighborsOfDel2.clear();
			mc++;
			return nodeToRemove;
		}
		else return null;

	}
	/*public Collection<node_data> getSrcVertices(){
		if(vertices!=null)
		{
			return vertices.values();
		}
		return null;
	}*/
	/*public Collection<node_data> getDestVertices(){
		if(dest_vertices!=null)
		{


			return dest_vertices.values();
		}
		return null;
	}*/

	public edge_data removeEdge(int src, int dest) {
		if (src != dest && getNode(src) != null && getNode(dest) != null && hasEdge(src, dest)) {
			((NodeData) vertices.get(src)).removeNode(getNode(dest));
			((NodeData) vertices.get(dest)).removeDestNode(getNode(src));
			edge_data edge = getEdge(src,dest);
			edges.remove(edge);
			edgeCounter--;
			mc++;
		}
		return null;
	}

	public int nodeSize() {
		return nodeCounter;
	}

	public int edgeSize() {
		return edgeCounter;
	}

	public int getMC() {
		return mc;
	}

	public boolean hasEdge(int src, int dest) {
		if (vertices.get(src) == null || vertices.get(dest) == null || src == dest) return false;
		if (((NodeData) vertices.get(src)).hasNi(dest))
			return true;
		return false;
	}

	public Collection<edge_data> getEdges()
	{
		return edges;
	}

}