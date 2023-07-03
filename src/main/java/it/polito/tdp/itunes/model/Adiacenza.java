package it.polito.tdp.itunes.model;

public class Adiacenza {
	
	private Album a1;
	private Album a2;
	private int peso;
	
	
	
	public Adiacenza(Album a1, Album a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
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
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
