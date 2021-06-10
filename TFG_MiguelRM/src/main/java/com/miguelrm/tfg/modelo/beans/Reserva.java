package com.miguelrm.tfg.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the reservas database table.
 * 
 */
@Entity
@Table(name="reservas")
@NamedQuery(name="Reserva.findAll", query="SELECT r FROM Reserva r")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_RESERVA")
	private int idReserva;

	private int cantidad;

	private String observaciones;

	@Column(name="PRECIO_VENTA")
	private BigDecimal precioVenta;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="EMAIL")
	private Usuario usuario;

	//uni-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	public Reserva() {
	}

	
	
	public Reserva(int idReserva, int cantidad, String observaciones, BigDecimal precioVenta, Usuario usuario,
			Evento evento) {
		super();
		this.idReserva = idReserva;
		this.cantidad = cantidad;
		this.observaciones = observaciones;
		this.precioVenta = precioVenta;
		this.usuario = usuario;
		this.evento = evento;
	}
	
	public Reserva(int cantidad, String observaciones, Usuario usuario, Evento evento) {
		super();
		this.cantidad = cantidad;
		this.observaciones = observaciones;
		this.precioVenta = evento.getPrecio();
		this.usuario = usuario;
		this.evento = evento;
	}



	public int getIdReserva() {
		return this.idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getPrecioVenta() {
		return this.precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
		result = prime * result + idReserva;
		result = prime * result + ((precioVenta == null) ? 0 : precioVenta.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Reserva))
			return false;
		Reserva other = (Reserva) obj;
		if (cantidad != other.cantidad)
			return false;
		if (evento == null) {
			if (other.evento != null)
				return false;
		} else if (!evento.equals(other.evento))
			return false;
		if (idReserva != other.idReserva)
			return false;
		if (precioVenta == null) {
			if (other.precioVenta != null)
				return false;
		} else if (!precioVenta.equals(other.precioVenta))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", cantidad=" + cantidad + ", observaciones=" + observaciones
				+ ", precioVenta=" + precioVenta + ", usuario=" + usuario + ", evento=" + evento + "]";
	}

	
}