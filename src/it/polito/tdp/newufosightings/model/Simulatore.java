package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.NewUfoSightingsController;
import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Evento.Tipo;

public class Simulatore {
	
	SimpleWeightedGraph<State,DefaultWeightedEdge> grafo;
	PriorityQueue<Evento> queue;
	HashMap<State, Integer> allerte;
	int t1;
	int t2;
	NewUfoSightingsDAO dao;

	public Simulatore() {

		grafo = new SimpleWeightedGraph<State, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		queue = new PriorityQueue<Evento>();
		allerte = new HashMap<State, Integer>();
		dao = new NewUfoSightingsDAO();
		
	}

	public void init(int anno,int t1, int t2, SimpleWeightedGraph<State, DefaultWeightedEdge> grafo2) {
		// TODO Auto-generated method stub
		grafo = grafo2;
		this.t1 = t1;
		this.t2 = t2;
		
		for(State state : grafo.vertexSet()) {
			for(Sighting sighting : dao.loadAllSightings(state,anno)) {
				queue.add(new Evento(Tipo.AVVISTAMENTO,state,sighting.getDatetime()));
				System.out.println("SCHEDULO AVVISTAMENTI");
			}
			state.setAllerta(false);
			state.setDefcon(5);
			allerte.put(state, 0);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		Evento evento;
		
		while((evento = queue.poll()) != null) {
			
			switch(evento.getTipo()) {
			
			case AVVISTAMENTO:
				
				if(!evento.getStato().isAllerta()) {
					
					evento.getStato().setDefcon(evento.getStato().getDefcon()-1);
					System.out.println("DECREMENTATO");
					System.out.println("SCHEDULO INCREMENTO");
					queue.add(new Evento(Tipo.INCREMENTO,evento.getStato(),evento.getDay().plusDays(t1)));
				
				if(evento.getStato().getDefcon() == 1) {
					System.out.println("SCHEDULO EMERGENZA");
					queue.add(new Evento(Tipo.EMERGENZA,evento.getStato(),evento.getDay()));
				}
				} else System.out.println("IN ALLERTA");
				
				break;
				
			case INCREMENTO:
				
				System.out.println("INCREMENTATO");
				if(evento.getStato().getDefcon() < 5)
				evento.getStato().setDefcon(evento.getStato().getDefcon()+1);
				
				break;
				
			case EMERGENZA:
				
				evento.getStato().setAllerta(true);
				System.out.println("EMERGENZA");
				allerte.replace(evento.getStato(), allerte.get(evento.getStato())+1);
				queue.add(new Evento(Tipo.FINE_EMERGENZA,evento.getStato(),evento.getDay().plusDays(t2)));
				
				break;
				
			case FINE_EMERGENZA:
				
				System.out.println("FINE EMERGENZA");
				evento.getStato().setAllerta(false);
				evento.getStato().setDefcon(5);
				
				break;
			
			}
		}
	}

	public HashMap<State, Integer> getMap() {
		// TODO Auto-generated method stub
		return this.allerte;
	}
}
