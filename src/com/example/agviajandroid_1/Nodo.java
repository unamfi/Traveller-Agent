package com.example.agviajandroid_1;

public class Nodo {

	//private int costo;
	private String nombre;
	private float costo; //costo variable dependiendo de ubicacion
	
	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String id) {
		this.nombre = id;
	}

	public Nodo(/*int costo,*/String id){
		//this.costo = costo;
		this.nombre = id;
	}

}
