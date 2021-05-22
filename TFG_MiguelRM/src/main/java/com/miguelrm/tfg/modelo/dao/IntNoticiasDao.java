package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.ListaNoticiasMensaje;

public interface IntNoticiasDao {
	
	/*public ListaNoticiasMensaje devuelveActivos();*/
	public String insertarNoticia(Noticia miNoticia);
	public String editarNoticia(Noticia miNoticia);
	public String borrarNoticia(int idNoticia);
	/*public String cancelarNoticia(int idNoticia);*/
	public ListaNoticiasMensaje devuelveDestacados();
	public ListaNoticiasMensaje devuelvePorCategoria(int idCategoria);
	public ListaNoticiasMensaje devuelveTodos();
	public ListaNoticiasMensaje devuelvePorId(int idNoticia);
	
	
}