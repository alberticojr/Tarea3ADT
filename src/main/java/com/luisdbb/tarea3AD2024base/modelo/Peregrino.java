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
	
	private String nombre_completo;
	private String nombre;
	private String correo;
	private String nacionalidad;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="peregrino",fetch = FetchType.EAGER)
    private List <PeregrinoParada> peregrinoParada = new ArrayList<>();
	
	@OneToMany(mappedBy="peregrino",cascade= CascadeType.ALL,fetch = FetchType.EAGER)
	private List <Estancia> listaEstancia = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Carnet carnet;
	
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn
	private Credenciales credenciales;
	
		
	//CONSTRUCTORES	
	public Peregrino() {
		super();
		}


	public Peregrino(String nombre_completo, String nombre, String nacionalidad, Credenciales credenciales) {
		super();
		this.nombre_completo = nombre_completo;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.credenciales = credenciales;
	}



	public Peregrino(String nombre, String nacionalidad, String nombre_completo) {
		super();
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.nombre_completo = nombre_completo;
	}
	
	
	
	public Peregrino( String nombre, String nacionalidad, String nombre_completo, String correo) {
		super();
		this.nombre_completo = nombre_completo;
		this.nombre = nombre;
		this.correo = correo;
		this.nacionalidad = nacionalidad;
	}


	public Peregrino(String nombre_completo, String nombre, String nacionalidad, List<PeregrinoParada> peregrinoParada,
			Carnet carnet, Credenciales credenciales) {
		super();
		this.nombre_completo = nombre_completo;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.peregrinoParada = peregrinoParada;
		this.carnet = carnet;
		this.credenciales = credenciales;
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


	public List<PeregrinoParada> getPeregrinoParada() {
		return peregrinoParada;
	}


	public void setPeregrinoParada(List<PeregrinoParada> peregrinoParada) {
		this.peregrinoParada = peregrinoParada;
	}


	public List<Estancia> getListaEstancia() {
		return listaEstancia;
	}


	public void setListaEstancia(List<Estancia> listaEstancia) {
		this.listaEstancia = listaEstancia;
	}


	public Credenciales getCredenciales() {
		return credenciales;
	}


	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}
	
	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	//METODODS INICIALES
	@Override
	public String toString() {
		return "Peregrino [nombre=" + nombre + ", nacionalidad=" + nacionalidad+ "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(carnet, credenciales, id, listaEstancia, nacionalidad, nombre, nombre_completo,
				peregrinoParada);
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
		return Objects.equals(carnet, other.carnet) && Objects.equals(credenciales, other.credenciales)
				&& id == other.id && Objects.equals(listaEstancia, other.listaEstancia)
				&& Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(nombre_completo, other.nombre_completo)
				&& Objects.equals(peregrinoParada, other.peregrinoParada);
	}


	


	
	
}
