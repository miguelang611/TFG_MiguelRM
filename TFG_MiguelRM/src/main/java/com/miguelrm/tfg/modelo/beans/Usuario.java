package com.miguelrm.tfg.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EMAIL")
	private String email;

	private String direccion;

	private int enabled;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;

	private String nombre;

	private String password;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinTable(
			name="usuario_perfiles"
			, joinColumns={
				@JoinColumn(name="EMAIL")
				}
			, inverseJoinColumns={
				@JoinColumn(name="ID_PERFIL")
				}
			)
	private Perfile perfil;
	
	
	public Usuario() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getEnabled() {
		return this.enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfile getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfile perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaRegistro == null) {
			if (other.fechaRegistro != null)
				return false;
		} else if (!fechaRegistro.equals(other.fechaRegistro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", direccion=" + direccion + ", enabled=" + enabled + ", fechaRegistro="
				+ fechaRegistro + ", nombre=" + nombre + ", password=" + password + ", perfil=" + perfil + "]";
	}

	
	

}