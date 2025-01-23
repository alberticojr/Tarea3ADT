package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "credenciales")
public class Credenciales {
	
	//COLUMNAS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String nombre;
	private String contra;
	private Perfil perfil;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Peregrino peregrino;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Parada parada;
	
	
	
	//CONSTRUCTOR
	public Credenciales() {
		super();
	}
	
	//GETTERS Y SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	
	

}
