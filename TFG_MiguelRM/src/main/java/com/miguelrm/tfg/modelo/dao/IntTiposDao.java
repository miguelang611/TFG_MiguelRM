package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.ListaTiposMensaje;
import com.miguelrm.tfg.modelo.beans.Tipo;

public interface IntTiposDao {

	public ListaTiposMensaje devuelveTipos(String forceActivos);
	public ListaTiposMensaje devuelvePorId(int idTipo);
	public String insertarTipo(Tipo miTipo);
	public String editarTipo(Tipo miTipo);
	public String borrarTipo(int idTipo, boolean forceDelete);
	public ListaTiposMensaje devuelveByPalabra(String palabra);
	
}
