package com.miguelrm.tfg.modelo.beans;

import java.util.List;

public class ListaNoticiasMensaje {
	List<Noticia> listaNoticias;
	String mensaje;
	public ListaNoticiasMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListaNoticiasMensaje(List<Noticia> listaNoticias, String mensaje) {
		super();
		this.listaNoticias = listaNoticias;
		this.mensaje = mensaje;
	}
	
	public List<Noticia> getListaNoticias() {
		return listaNoticias;
	}
	public void setListaNoticias(List<Noticia> listaNoticias) {
		this.listaNoticias = listaNoticias;
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
		result = prime * result + ((listaNoticias == null) ? 0 : listaNoticias.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ListaNoticiasMensaje))
			return false;
		ListaNoticiasMensaje other = (ListaNoticiasMensaje) obj;
		if (listaNoticias == null) {
			if (other.listaNoticias != null)
				return false;
		} else if (!listaNoticias.equals(other.listaNoticias))
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
		return "ListaNoticiasMensaje [listaNoticias=" + listaNoticias + ", mensaje=" + mensaje + "]";
	}
	
}
