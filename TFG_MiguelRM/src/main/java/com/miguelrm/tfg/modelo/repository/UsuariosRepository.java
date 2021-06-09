package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguelrm.tfg.modelo.beans.Usuario;

//Extiende de JPARepository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{

	
	
	public List<Usuario> findAll();
	
	public List<Usuario> findByEnabled(int enabled);
	
	public Usuario findByEmail(String email);
	
	/*
	@Query("select u from Usuario u where u.rol.idRol = ?1")
	public List<Usuario> findByRol(int idRol);*/
	
	/* MÉTODOS NUEVOS */
	public List<Usuario> findByNombreContains(String palabra);
	
	public List<Usuario> findByEmailContains(String palabra);
	
	public void deleteByEmail(String email);
	
	
}
