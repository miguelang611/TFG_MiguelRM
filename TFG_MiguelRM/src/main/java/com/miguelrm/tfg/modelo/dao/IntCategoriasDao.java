package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.ListaCategoriasMensaje;
import com.miguelrm.tfg.modelo.beans.Categoria;

public interface IntCategoriasDao {

	public ListaCategoriasMensaje devuelveCategorias();
	public ListaCategoriasMensaje devuelvePorId(int idCategoria);
	/*ListaCategoriasMensaje devuelveCategoriasConEvActivos();*/
	public String insertarCategoria(Categoria miCategoria);
	public String editarCategoria(Categoria miCategoria);
	public String borrarCategoria(int idCategoria);

}
