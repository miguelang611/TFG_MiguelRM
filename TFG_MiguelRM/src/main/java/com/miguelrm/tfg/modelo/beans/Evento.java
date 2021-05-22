package com.miguelrm.tfg.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the eventos database table.
 * 
 */
@Entity
@Table(name="eventos")
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_EVENTO")
	private int idEvento;

	@Column(name="AFORO_MAXIMO")
	private int aforoMaximo;

	private String descripcion;

	private String destacado;

	private String direccion;

	private int duracion;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	//Indicando esta etiqueta Spring será capaz de entender la fecha que proviene
	//del tipo date en el input de HTML5
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	@Column(name="MINIMO_ASISTENCIA")
	private int minimoAsistencia;

	private String nombre;

	private BigDecimal precio;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="ID_TIPO")
	private Tipo tipo;

	public Evento(int idEvento, int aforoMaximo, String descripcion, String destacado, String direccion, int duracion,
			String estado, Date fechaInicio, int minimoAsistencia, String nombre, BigDecimal precio, Tipo tipo) {
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


	public Evento() {
	}

	public int getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getAforoMaximo() {
		return this.aforoMaximo;
	}

	public void setAforoMaximo(int aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDestacado() {
		return this.destacado;
	}

	public void setDestacado(String destacado) {
		this.destacado = destacado;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getDuracion() {
		return this.duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getMinimoAsistencia() {
		return this.minimoAsistencia;
	}

	public void setMinimoAsistencia(int minimoAsistencia) {
		this.minimoAsistencia = minimoAsistencia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aforoMaximo;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + duracion;
		result = prime * result + minimoAsistencia;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Evento))
			return false;
		Evento other = (Evento) obj;
		if (aforoMaximo != other.aforoMaximo)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (duracion != other.duracion)
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
		return true;
	}


	@Override
	public String toString() {
		
		String mensInicial = "";
		if(destacado.equals("s")) {
			mensInicial = "Evento DESTACADO ->";
		}else {
			mensInicial = "Evento normal -> ";
		}
		
		String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado  
	    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto 
	    String miFecha = (objSDF.format(fechaInicio)); // El formato de fecha se aplica a la fecha almacenada
	        
		return mensInicial +" ID: " + idEvento + " || Nombre: " + nombre + " || Descripción: " + descripcion + " || Precio: " + precio + "€"+
		
				"\n\n Detalles extras -> "+
				"Tipo: " + tipo.getNombre() + " || Aforo máximo: " + aforoMaximo +  " Personas"+
				" || Dirección: " + direccion + " || Duración: " + duracion + " horas || Estado: " + estado +
				" || Fehca de inicio: " + miFecha + " || Mínimo de asistencia: " + minimoAsistencia +" personas \n\n\n";
	}
	
}