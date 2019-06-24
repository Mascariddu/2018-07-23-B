package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	public enum Tipo{
		
		AVVISTAMENTO,
		INCREMENTO,
		EMERGENZA,
		FINE_EMERGENZA
		
	}
	
	private Tipo tipo;
	private State stato;
	private LocalDateTime day;
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public State getStato() {
		return stato;
	}
	public void setStato(State stato) {
		this.stato = stato;
	}
	public Evento(Tipo tipo, State stato,LocalDateTime localDateTime) {
		super();
		this.tipo = tipo;
		this.stato = stato;
		this.day = localDateTime;
	}
	public LocalDateTime getDay() {
		return day;
	}
	public void setDay(LocalDateTime day) {
		this.day = day;
	}
	
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.day.compareTo(o.day);
	}
	
}
