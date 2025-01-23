package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Peregrino")
public class Peregrino {

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String nombre;
	private String nacionalidad;
	private String nombre_completo;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="peregrino")
    private List <PeregrinoParada> peregrinoParada = new ArrayList<>();
	
	@OneToMany(mappedBy="peregrino",cascade= CascadeType.ALL)
	private List <Estancia> listaEstancia = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Carnet carnet;
	
		
	//CONSTRUCTORES	
	public Peregrino() {
		super();
		}

	

	public Peregrino(String nombre, String nacionalidad, String nombre_completo) {
		super();
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.nombre_completo = nombre_completo;
	}
	



	//GETTERS Y SETTERS
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	
	public String getNombre_completo() {
		return nombre_completo;
	}



	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}


	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Carnet getCarnet() {
		return carnet;
	}



	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}



	//METODODS INICIALES
	@Override
	public String toString() {
		return "Peregrino [nombre=" + nombre + ", nacionalidad=" + nacionalidad+ "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(nacionalidad, nombre);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peregrino other = (Peregrino) obj;
		return  Objects.equals(nacionalidad, other.nacionalidad)
				&& Objects.equals(nombre, other.nombre);
	}
	
}
