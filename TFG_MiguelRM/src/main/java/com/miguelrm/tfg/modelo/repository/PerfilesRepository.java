package com.miguelrm.tfg.modelo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Perfile;

public interface PerfilesRepository extends JpaRepository<Perfile, Integer> {
	
	public List<Perfile> findByNombre(String nombre);
	
}
