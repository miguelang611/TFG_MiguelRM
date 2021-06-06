package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.ListaNoticiasMensaje;

public interface IntNoticiasDao {
	
	public String insertarNoticia(Noticia miNoticia);
	public String editarNoticia(Noticia miNoticia);
	public String borrarNoticia(int idNoticia);
	public String borrarNoticiasByCategoria(int idCategoria);
	public String modificarNoticia(int idNoticia, String destacada);
	public ListaNoticiasMensaje devuelveByDestacada(String destacada);
	public ListaNoticiasMensaje devuelveByCategoria(int idCategoria);
	public ListaNoticiasMensaje devuelveByPalabra(String palabra);
	public ListaNoticiasMensaje devuelveTodos();
	public ListaNoticiasMensaje devuelvePorId(int idNoticia);
	
	
}