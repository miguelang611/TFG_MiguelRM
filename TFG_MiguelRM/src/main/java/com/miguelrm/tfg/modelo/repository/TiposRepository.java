package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Tipo;

//Extiende de JPARepository
public interface TiposRepository extends JpaRepository<Tipo, Integer> {
	
	/*MÉTODOS NUEVOS*/
	
	public List<Tipo> findByNombreContains(String palabra);
	
	public List<Tipo> findByDescripcionContains(String palabra);
	
}
