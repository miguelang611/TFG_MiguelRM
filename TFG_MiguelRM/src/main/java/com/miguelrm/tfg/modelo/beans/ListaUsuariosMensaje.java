package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaUsuariosMensaje {
	List<Usuario> listaUsuarios;
	String mensaje;
	public ListaUsuariosMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaUsuariosMensaje(List<Usuario> listaUsuarios, String mensaje) {
		super();
		this.listaUsuarios = listaUsuarios;
		this.mensaje = mensaje;
	}
	
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
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
		result = prime * result + ((listaUsuarios == null) ? 0 : listaUsuarios.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaUsuariosMensaje))
			return false;
		ListaUsuariosMensaje other = (ListaUsuariosMensaje) obj;
		if (listaUsuarios == null) {
			if (other.listaUsuarios != null)
				return false;
		} else if (!listaUsuarios.equals(other.listaUsuarios))
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
		return "ListaUsuariosMensaje [listaUsuarios=" + listaUsuarios + ", mensaje=" + mensaje + "]";
	}
	
}