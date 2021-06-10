package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Usuario;

//Extiende de JPARepository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{

	
	
	public List<Usuario> findAll();
	
	public List<Usuario> findByEnabled(int enabled);
	
	public Usuario findByEmail(String email);
	

	public List<Usuario> findByNombreContains(String palabra);
	
	public List<Usuario> findByEmailContains(String palabra);
	
	public void deleteByEmail(String email);
	
	
}
