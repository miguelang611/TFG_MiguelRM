package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Tipo;

public interface TiposRepository extends JpaRepository<Tipo, Integer> {
	
	
	public List<Tipo> findByNombreContains(String palabra);
	
	public List<Tipo> findByDescripcionContains(String palabra);
	
}
