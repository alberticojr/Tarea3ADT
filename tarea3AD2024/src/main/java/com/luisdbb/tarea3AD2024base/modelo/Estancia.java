package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Estancia")
public class Estancia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	private String nombreParada;
	private Date fecha;
	private boolean vip = false;
	
	@ManyToOne
    @JoinColumn(name="IdPeregrino")
	private Peregrino peregrino;
	
	@ManyToOne
    @JoinColumn(name="IdParada")
	private Parada parada;

	//CONSTRUCTOR
	public Estancia(String nombreParada, Date fecha, boolean vip) {
		super();
		this.nombreParada = nombreParada;
		this.fecha = fecha;
		this.vip = vip;
	}
	
	

	public Estancia(String nombreParada, boolean vip) {
		super();
		this.nombreParada = nombreParada;
		this.vip = vip;
	}


	public Estancia() {
		super();
	}



	//GETTERS Y SETTERS
	public String getNombreParada() {
		return nombreParada;
	}

	public void setNombreParada(String nombreParada) {
		this.nombreParada = nombreParada;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Estancia [id=" + id + ", nombreParada=" + nombreParada + ", fecha=" + fecha + ", vip=" + vip + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, nombreParada, vip);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && Objects.equals(nombreParada, other.nombreParada)
				&& vip == other.vip;
	}
	
}
