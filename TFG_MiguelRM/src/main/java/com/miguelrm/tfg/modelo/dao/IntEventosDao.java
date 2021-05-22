package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.ListaEventosMensaje;

public interface IntEventosDao {
	
	public ListaEventosMensaje devuelveActivos();
	public String insertarEvento(Evento miEvento);
	public String editarEvento(Evento miEvento);
	public String borrarEvento(int idEvento);
	public String borrarEventosByTipo(int idTipo);
	public String modificarEvento(int idEvento, String estado, String destacado);
	public ListaEventosMensaje devuelveByDestacado(String destacado);
	public ListaEventosMensaje devuelveByEstado(String estado);
	public ListaEventosMensaje devuelveByTipo(int idTipo);
	public ListaEventosMensaje devuelveByPalabra(String palabra);
	public ListaEventosMensaje devuelveTodos();
	public ListaEventosMensaje devuelvePorId(int idEvento);
	
	
}