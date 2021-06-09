package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguelrm.tfg.modelo.beans.Reserva;

//Extiende de JPARepository
public interface ReservasRepository extends JpaRepository<Reserva, Integer>{

	@Query("select r from Reserva r where r.evento.idEvento = ?1")
	public List<Reserva> findByIdEvento(int idEvento);
	
	@Query("select r from Reserva r where r.usuario.email = ?1")
	public List<Reserva> findByEmail(String email);
	/*
	//Definimos 3 find, aparte de los autogenerados al exteder de JpaRepository:
	public List<Reserva> findByEstado(String estado);
	
	public List<Reserva> findByDestacado(String destacado);
	
	//Importante destacar que siempre filtraremos eventos destacados no activos
	//(aunque no deberían de estar así nunca en BD)
	public List<Reserva> findByDestacadoAndEstado(String destacado, String estado);
	
	//Indicamos manualmente la query que buscamos:
	@Query("select e from Reserva e where e.tipo.idEvento = ?1")
	public List<Reserva> findByEvento(int idEvento);
	
	/* MÉTODOS NUEVOS 
	public List<Reserva> findByNombreContains(String palabra);
	
	public List<Reserva> findByDescripcionContains(String palabra);
	*/
}