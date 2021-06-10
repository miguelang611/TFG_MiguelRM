package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.Usuario;
import com.miguelrm.tfg.modelo.beans.ListaReservasMensaje;

public interface IntReservasDao {
	
	public String insertarReserva(Reserva miReserva);
	public String editarReserva(Reserva miReserva, boolean isAdmin, Usuario usuario);
	public String borrarReserva(int idReserva, boolean isAdmin, Usuario usuario);
	public String borrarReservasByEvento(int idEvento);
	public ListaReservasMensaje devuelveTodos();
	public ListaReservasMensaje devuelveByEvento(int idEvento);
	public ListaReservasMensaje devuelveByEmail(String email);
	public ListaReservasMensaje devuelvePorId(int idReserva);
	
	
}