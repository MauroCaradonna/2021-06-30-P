package it.polito.tdp.genes.model;

import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private GenesDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new GenesDao();
	}

	public Graph<String, DefaultWeightedEdge> creaGrafo(){
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getAllLocalization());
		Map<CoppiaLocazioni, Set<Adiacenza>>adiacenze = this.dao.getAllAdiacenze();
		for(CoppiaLocazioni coppia : adiacenze.keySet()) {
			Graphs.addEdge(this.grafo, coppia.getLi(), coppia.getL2(), adiacenze.get(coppia).size());
		}
		return this.grafo;
	}
	
	public String getStatistiche(String localization) {
		String result = "Adiacenti a : " + localization + "\n";
		for(DefaultWeightedEdge edge : this.grafo.edgesOf(localization)) {
			if(this.grafo.getEdgeSource(edge).equals(localization))
				result += this.grafo.getEdgeTarget(edge) + "   " + (int)(grafo.getEdgeWeight(edge)) +"\n";
			else
				result += this.grafo.getEdgeSource(edge) + "   " + (int)(grafo.getEdgeWeight(edge)) +"\n";
		}
//		Set<String> connessi = Graphs.neighborSetOf(this.grafo, localization);
//		for(String connesso : connessi) {
//			DefaultWeightedEdge edge = this.grafo.getEdge(localization, connesso);
//			result += connesso+ (int)(grafo.getEdgeWeight(edge)) +"\n";
//		}
		return result;
	}
}