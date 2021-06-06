package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.ListaCategoriasMensaje;
import com.miguelrm.tfg.modelo.beans.Categoria;

public interface IntCategoriasDao {

	public ListaCategoriasMensaje devuelveCategorias(String forceActivos);
	public ListaCategoriasMensaje devuelvePorId(int idCategoria);
	public String insertarCategoria(Categoria miCategoria);
	public String editarCategoria(Categoria miCategoria);
	public String borrarCategoria(int idCategoria, boolean forceDelete);
	public ListaCategoriasMensaje devuelveByPalabra(String palabra);
	
}
