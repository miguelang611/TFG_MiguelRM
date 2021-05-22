package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaCategoriasMensaje {
	List<Categoria> listaCategorias;
	String mensaje;
	public ListaCategoriasMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaCategoriasMensaje(List<Categoria> listaCategorias, String mensaje) {
		super();
		this.listaCategorias = listaCategorias;
		this.mensaje = mensaje;
	}
	
	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}
	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
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
		result = prime * result + ((listaCategorias == null) ? 0 : listaCategorias.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaCategoriasMensaje))
			return false;
		ListaCategoriasMensaje other = (ListaCategoriasMensaje) obj;
		if (listaCategorias == null) {
			if (other.listaCategorias != null)
				return false;
		} else if (!listaCategorias.equals(other.listaCategorias))
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
		return "ListaCategoriasMensaje [listaCategorias=" + listaCategorias + ", mensaje=" + mensaje + "]";
	}
	
}