package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvioACasa extends Servicio{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private double peso;
	private int[] volumen = new int [3];
	private boolean urgente;
	private Direccion direccion;
	private Long idParada;
	
	//CONSTRUCTORES
	

	public EnvioACasa() {
		super();
	}

	public EnvioACasa(double peso, int[] volumen, boolean urgente, Direccion direccion) {
		super();
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
	}
	

	public EnvioACasa(long id, double peso, int[] volumen, boolean urgente, Direccion direccion, Long idParada) {
		super();
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
		this.idParada = idParada;
	}

	//GETTERS Y SETTERS
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(double peso) {
		this.peso = peso;
	}


	public int[] getVolumen() {
		return volumen;
	}


	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}


	public boolean isUrgente() {
		return urgente;
	}


	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + ", direccion=" + direccion + "]";
	}
	
	
	
	
}
