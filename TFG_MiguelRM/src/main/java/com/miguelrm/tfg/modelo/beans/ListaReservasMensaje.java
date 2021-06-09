package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaReservasMensaje {
	List<Reserva> listaReservas;
	String mensaje;
	public ListaReservasMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaReservasMensaje(List<Reserva> listaReservas, String mensaje) {
		super();
		this.listaReservas = listaReservas;
		this.mensaje = mensaje;
	}
	
	public List<Reserva> getListaReservas() {
		return listaReservas;
	}
	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaReservas == null) ? 0 : listaReservas.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaReservasMensaje))
			return false;
		ListaReservasMensaje other = (ListaReservasMensaje) obj;
		if (listaReservas == null) {
			if (other.listaReservas != null)
				return false;
		} else if (!listaReservas.equals(other.listaReservas))
			return false;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ListaReservasMensaje [listaReservas=" + listaReservas + ", mensaje=" + mensaje + "]";
	}
	
}
