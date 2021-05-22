package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaTiposMensaje {
	List<Tipo> listaTipos;
	String mensaje;
	public ListaTiposMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaTiposMensaje(List<Tipo> listaTipos, String mensaje) {
		super();
		this.listaTipos = listaTipos;
		this.mensaje = mensaje;
	}
	
	public List<Tipo> getListaTipos() {
		return listaTipos;
	}
	public void setListaTipos(List<Tipo> listaTipos) {
		this.listaTipos = listaTipos;
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
		result = prime * result + ((listaTipos == null) ? 0 : listaTipos.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaTiposMensaje))
			return false;
		ListaTiposMensaje other = (ListaTiposMensaje) obj;
		if (listaTipos == null) {
			if (other.listaTipos != null)
				return false;
		} else if (!listaTipos.equals(other.listaTipos))
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
		return "ListaTiposMensaje [listaTipos=" + listaTipos + ", mensaje=" + mensaje + "]";
	}
	
}