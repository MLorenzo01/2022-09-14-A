package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	ItunesDAO dao;
	SimpleGraph<Album, DefaultEdge> graph;
	Set<Album> insieme;
	private ArrayList<Album> best;
	
	public Model() {
		this.dao = new ItunesDAO();
	}
	
	public String creaGrafo(int durata) {
		graph = new SimpleGraph<>(DefaultEdge.class);
		Graphs.addAllVertices(graph, dao.getAlbums(durata));
		
		ArrayList<Edge> lista = dao.getCoppie();
		
		for(Edge e: lista) {
			Graphs.addEdgeWithVertices(graph, e.getA1(), e.getA2());
		}
		return ("il grafo ha " + graph.vertexSet().size() + " vertici e " + graph.edgeSet().size() + " archi\n");
		
	}
	public Set<Album> getVertexSet(){
		return graph.vertexSet();
	}
	
	public String getComponenteConnessa(Album a) {
		ConnectivityInspector<Album, DefaultEdge> inspect = new ConnectivityInspector<Album, DefaultEdge>(graph);
		insieme = inspect.connectedSetOf(a);
		double durata = 0;
		for(Album a1: insieme) {
			durata += a1.getDurata();
		}
		System.out.println(insieme);
		return "\nL'album " + a.getTitle() + " ha la componente connessa di " + insieme.size() +  " vertici e di durata " + (durata/(60*1000));
		
	}
	
	public ArrayList<Album> trovaMaggiore(int DTot, Album a){
		this.best = new ArrayList<>();
		double ms = DTot*60*1000 - a.getDurata();
		ArrayList<Album> parziale = new ArrayList<>();
		int sec = 0;
		cerca(parziale, sec, ms);
		best.add(a);
		return best;
	}

	private void cerca(ArrayList<Album> parziale, int sec, double ms) {
		if(parziale.size() > best.size()) {
			best = new ArrayList<>(parziale);
		}
		for(Album t: insieme) {
			if(!parziale.contains(t) && (sec+t.getDurata()) < ms) {
				parziale.add(t);
				sec += t.getDurata();
				cerca(parziale, sec, ms);
				parziale.remove(t);
				sec -=t.getDurata();
			}
		}
	}
}
