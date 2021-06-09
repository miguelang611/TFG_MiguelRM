package com.miguelrm.tfg.modelo.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the noticias database table.
 * 
 */
@Entity
@Table(name="noticias")
@NamedQuery(name="Noticia.findAll", query="SELECT n FROM Noticia n")
public class Noticia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_NOTICIA")
	private int idNoticia;

	private String destacada;

	private String detalle;

	private String nombre;

	private String subtitulo;
	
	private String img;
	
	@Temporal(TemporalType.DATE)
	//Indicando esta etiqueta Spring será capaz de entender la fecha que proviene
	//del tipo date en el input de HTML5
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="EMAIL")
	private Usuario usuario;

	//uni-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA")
	private Categoria categoria;

	public Noticia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Noticia(int idNoticia, String destacada, String detalle, String nombre, String subtitulo, String img,
			Date fecha, Usuario usuario, Categoria categoria) {
		super();
		this.idNoticia = idNoticia;
		this.destacada = destacada;
		this.detalle = detalle;
		this.nombre = nombre;
		this.subtitulo = subtitulo;
		this.img = img;
		this.fecha = fecha;
		this.usuario = usuario;
		this.categoria = categoria;
	}

	public int getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(int idNoticia) {
		this.idNoticia = idNoticia;
	}

	public String getDestacada() {
		return destacada;
	}

	public void setDestacada(String destacada) {
		this.destacada = destacada;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + idNoticia;
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Noticia))
			return false;
		Noticia other = (Noticia) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idNoticia != other.idNoticia)
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
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
		return "Noticia [idNoticia=" + idNoticia + ", destacada=" + destacada + ", detalle=" + detalle + ", nombre="
				+ nombre + ", subtitulo=" + subtitulo + ", img=" + img + ", fecha=" + fecha + ", usuario=" + usuario
				+ ", categoria=" + categoria + "]";
	}

	
	
	
	
}