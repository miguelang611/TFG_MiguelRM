package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguelrm.tfg.modelo.beans.Noticia;

//Extiende de JPARepository
public interface NoticiasRepository extends JpaRepository<Noticia, Integer>{
	
	public List<Noticia> findByDestacada(String destacada);
	
	//Indicamos manualmente la query que buscamos:
	@Query("select n from Noticia n where n.categoria.idCategoria = ?1")
	public List<Noticia> findByCategoria(int idCategoria);

}

