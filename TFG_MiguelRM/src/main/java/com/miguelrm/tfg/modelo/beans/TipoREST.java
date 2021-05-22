package com.miguelrm.tfg.modelo.beans;

public class TipoREST {
	private int idTipo;
	
	private String nombre;


	private String descripcion;


	public TipoREST() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoREST(int idTipo, String descripcion, String nombre) {
		super();
		this.idTipo = idTipo;
		this.descripcion = descripcion;
		this.nombre = nombre;
	}
	
	public TipoREST (Tipo miTipo) {
		super();
		idTipo = miTipo.getIdTipo();
		nombre = miTipo.getNombre();
		descripcion = miTipo.getDescripcion();
	
	}
	
	

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + idTipo;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TipoREST))
			return false;
		TipoREST other = (TipoREST) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idTipo != other.idTipo)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoREST [idTipo=" + idTipo + ", descripcion=" + descripcion + ", nombre=" + nombre + "]";
	}
	
	
}
