package com.miguelrm.tfg.modelo.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EventoREST {

	private int idEvento;

	private int aforoMaximo;

	private String descripcion;

	private String destacado;

	private String direccion;

	private int duracion;

	private String estado;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	private int minimoAsistencia;

	private String nombre;

	private BigDecimal precio;

	private TipoREST tipo;

	public EventoREST() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventoREST(int idEvento, int aforoMaximo, String descripcion, String destacado, String direccion,
			int duracion, String estado, Date fechaInicio, int minimoAsistencia, String nombre, BigDecimal precio,
			TipoREST tipo) {
		super();
		this.idEvento = idEvento;
		this.aforoMaximo = aforoMaximo;
		this.descripcion = descripcion;
		this.destacado = destacado;
		this.direccion = direccion;
		this.duracion = duracion;
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.minimoAsistencia = minimoAsistencia;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
	}

	/*
	 * Asignamos las variables del evento normal al de tipo rest,
	 * y creamos un objeto nuevo de tipo, pasándole como parámetro
	 * el tipo que está dentro del evento "normal"
	 */
	public EventoREST(Evento miEvento) {
		super();
		idEvento = miEvento.getIdEvento();
		aforoMaximo = miEvento.getAforoMaximo();
		descripcion = miEvento.getDescripcion();
		destacado = miEvento.getDestacado();
		direccion = miEvento.getDireccion();
		duracion = miEvento.getDuracion();
		estado = miEvento.getEstado();
		fechaInicio = miEvento.getFechaInicio();
		minimoAsistencia = miEvento.getMinimoAsistencia();
		nombre = miEvento.getNombre();
		precio = miEvento.getPrecio();
		tipo = new TipoREST(miEvento.getTipo());
	}
	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getAforoMaximo() {
		return aforoMaximo;
	}

	public void setAforoMaximo(int aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDestacado() {
		return destacado;
	}

	public void setDestacado(String destacado) {
		this.destacado = destacado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getMinimoAsistencia() {
		return minimoAsistencia;
	}

	public void setMinimoAsistencia(int minimoAsistencia) {
		this.minimoAsistencia = minimoAsistencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public TipoREST getTipo() {
		return tipo;
	}

	public void setTipo(TipoREST tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aforoMaximo;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((destacado == null) ? 0 : destacado.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + duracion;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + idEvento;
		result = prime * result + minimoAsistencia;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventoREST))
			return false;
		EventoREST other = (EventoREST) obj;
		if (aforoMaximo != other.aforoMaximo)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (destacado == null) {
			if (other.destacado != null)
				return false;
		} else if (!destacado.equals(other.destacado))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (duracion != other.duracion)
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (idEvento != other.idEvento)
			return false;
		if (minimoAsistencia != other.minimoAsistencia)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventoREST [idEvento=" + idEvento + ", aforoMaximo=" + aforoMaximo + ", descripcion=" + descripcion
				+ ", destacado=" + destacado + ", direccion=" + direccion + ", duracion=" + duracion + ", estado="
				+ estado + ", fechaInicio=" + fechaInicio + ", minimoAsistencia=" + minimoAsistencia + ", nombre="
				+ nombre + ", precio=" + precio + ", tipo=" + tipo + "]";
	}
	
	
	
}
