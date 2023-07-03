package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Album, DefaultWeightedEdge> grafo;
	private ItunesDAO dao;
	private Map<Integer, Album> idMap;
	private List<Adiacenza> adiacenze;
	
	public Model() {
		dao = new ItunesDAO();
		
	}
	
	public void creaGrafo(int tracce) {
		
		adiacenze = new ArrayList<Adiacenza>();
		idMap = new HashMap<Integer, Album>();
		grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.riempiMappa(tracce, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		for (Album a1 : idMap.values()) {
			for (Album a2 : idMap.values()) {
				if (a1.getAlbumId() != a2.getAlbumId()) {
					if ((a1.getTracce() - a2.getTracce()) != 0) {
						if (a1.getTracce() < a2.getTracce()) {
							Graphs.addEdge(this.grafo, a1, a2, Math.abs(a1.getTracce() - a2.getTracce()));
							adiacenze.add(new Adiacenza(a1, a2, Math.abs(a1.getTracce() - a2.getTracce())));
						}
						else {
							Graphs.addEdge(this.grafo, a2, a1, Math.abs(a1.getTracce() - a2.getTracce()));
							adiacenze.add(new Adiacenza(a2, a1, Math.abs(a1.getTracce() - a2.getTracce())));
						}
						
					}
				}
			}
		}
		
		
	}
	
	public List<String> listaAlbum() {
		
		List<String> result = new ArrayList<>();
		for (Album a : idMap.values()) {
			result.add(a.getTitle());
		}
		
		Collections.sort(result);
		return result;
	}
	
	public String componenteConnessa(String s) {
		
		String result = "";
	
		
		for (Album a1 : this.grafo.vertexSet()) {
			int sommaArchiEntranti = 0;
			int sommaArchiUscenti = 0;
				Set<DefaultWeightedEdge> archiEntranti = this.grafo.incomingEdgesOf(a1);
				Set<DefaultWeightedEdge> archiUscenti = this.grafo.outgoingEdgesOf(a1);
				for (Adiacenza a : adiacenze)  {
					DefaultWeightedEdge e1 = this.grafo.getEdge(a.getA1(), a.getA2());
					if (archiEntranti.contains(e1)) {
						sommaArchiEntranti = sommaArchiEntranti + a.getPeso();
						
						}
					if (archiUscenti.contains(e1)) {
						sommaArchiUscenti = sommaArchiUscenti + a.getPeso();
						
					}
					}
				a1.setBilancio((sommaArchiEntranti - sommaArchiUscenti)/2);

		}
		
		for (Album a5 : this.grafo.vertexSet()) {
			if (a5.getTitle().equals(s)) {
		
		List<Album> l = Graphs.successorListOf(this.grafo, a5);
		Collections.sort(l, new Comparator<Album>() {
		    @Override
		    public int compare(Album aaa, Album bbb) {
		        return (int) (aaa.getBilancio() - bbb.getBilancio());
		    }
		});
		Collections.reverse(l);

		for (Album aa : l) {
			
			result = result + aa.getTitle() + ", bilancio = " + aa.getBilancio() + "\n";
		} } }

		return result;

		}
		
		
		

		
	
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
		}
		 public int numeroArchi() {
		return this.grafo.edgeSet().size();
		}


	
}
