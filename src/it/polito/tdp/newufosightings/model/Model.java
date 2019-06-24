package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	NewUfoSightingsDAO dao;
	List<State> vertex;
	HashMap<String, State> idMap;
	SimpleWeightedGraph<State, DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	HashMap<State, Integer> allerteMap;
	
	public Model() {
		
		dao = new NewUfoSightingsDAO();
		idMap = new HashMap<String, State>();
		vertex = dao.loadAllStates(idMap);
		allerteMap = new HashMap<State, Integer>();
	}

	public void creaGrafo(int anno, int gg) {
		// TODO Auto-generated method stub
		grafo = new SimpleWeightedGraph<State, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, vertex);
		adiacenze = new ArrayList<Adiacenza>(dao.getArchi());
		System.out.println(adiacenze.size());
		
		for(Adiacenza a : adiacenze) {
			
			State source = idMap.get(a.getState1());
			State target = idMap.get(a.getState2());
			double peso = dao.getPeso(gg,anno,a.getState1(),a.getState2());
			
			Graphs.addEdge(grafo, source, target, peso);
			System.out.println("AGGIUNGO ARCO");
		}
		
		System.out.println("#vertici: "+grafo.vertexSet().size());
		System.out.println("#archi: "+grafo.edgeSet().size());
	}

	public List<String> getPesi() {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		
		for(State state : grafo.vertexSet()) {
			int tot = 0;
			
			for(State state2 : Graphs.neighborListOf(grafo, state)) {
				
				DefaultWeightedEdge edge = grafo.getEdge(state, state2);
				tot += grafo.getEdgeWeight(edge);
				
			}
			
			state.setPeso(tot);
			System.out.println(state.getPeso());
			result.add(state.toString()+" con peso: "+tot);
		}
		
		return result;
	}

	public void simula(int anno,int t1, int t2) {
		// TODO Auto-generated method stub
		
		Simulatore sim = new Simulatore();
		
		sim.init(anno,t1,t2,this.grafo);
		sim.run();
		this.allerteMap = sim.getMap();
		
	}

	public List<String> getAllerte() {
		// TODO Auto-generated method stub
		List<String> allerte = new ArrayList<String>();
		
		for(State state : allerteMap.keySet())
			allerte.add(state.toString()+" con : "+allerteMap.get(state)+" allerte!");
		
		return allerte;
	}

}
