package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaEventosMensaje {
	List<Evento> listaEventos;
	String mensaje;
	public ListaEventosMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaEventosMensaje(List<Evento> listaEventos, String mensaje) {
		super();
		this.listaEventos = listaEventos;
		this.mensaje = mensaje;
	}
	
	public List<Evento> getListaEventos() {
		return listaEventos;
	}
	public void setListaEventos(List<Evento> listaEventos) {
		this.listaEventos = listaEventos;
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
		result = prime * result + ((listaEventos == null) ? 0 : listaEventos.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaEventosMensaje))
			return false;
		ListaEventosMensaje other = (ListaEventosMensaje) obj;
		if (listaEventos == null) {
			if (other.listaEventos != null)
				return false;
		} else if (!listaEventos.equals(other.listaEventos))
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
		return "ListaEventosMensaje [listaEventos=" + listaEventos + ", mensaje=" + mensaje + "]";
	}
	
}
