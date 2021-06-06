package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Categoria;

//Extiende de JPARepository
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
	/*MÉTODOS NUEVOS*/
	
	public List<Categoria> findByNombreContains(String palabra);
	
	public List<Categoria> findByDescripcionContains(String palabra);
	
}
