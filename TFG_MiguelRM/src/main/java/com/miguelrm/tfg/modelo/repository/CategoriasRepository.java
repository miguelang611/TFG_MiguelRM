package com.miguelrm.tfg.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelrm.tfg.modelo.beans.Categoria;

//Extiende de JPARepository
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
	
}
