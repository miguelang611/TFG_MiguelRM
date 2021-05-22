package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguelrm.tfg.modelo.beans.Evento;

//Extiende de JPARepository
public interface EventosRepository extends JpaRepository<Evento, Integer>{

	
	//Definimos 3 find, aparte de los autogenerados al exteder de JpaRepository:
	public List<Evento> findByEstado(String estado);
	
	public List<Evento> findByDestacado(String destacado);
	
	//Importante destacar que siempre filtraremos eventos destacados no activos
	//(aunque no deberían de estar así nunca en BD)
	public List<Evento> findByDestacadoAndEstado(String destacado, String estado);
	
	//Indicamos manualmente la query que buscamos:
	@Query("select e from Evento e where e.tipo.idTipo = ?1")
	public List<Evento> findByTipo(int idTipo);
	
	/* MÉTODOS NUEVOS */
	public List<Evento> findByNombreContains(String palabra);
	
	public List<Evento> findByDescripcionContains(String palabra);
	
}
