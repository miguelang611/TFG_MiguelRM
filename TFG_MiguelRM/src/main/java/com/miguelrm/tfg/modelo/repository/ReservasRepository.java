package com.miguelrm.tfg.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguelrm.tfg.modelo.beans.Reserva;

public interface ReservasRepository extends JpaRepository<Reserva, Integer>{

	@Query("select r from Reserva r where r.evento.idEvento = ?1")
	public List<Reserva> findByIdEvento(int idEvento);
	
	@Query("select r from Reserva r where r.usuario.email = ?1")
	public List<Reserva> findByEmail(String email);

}