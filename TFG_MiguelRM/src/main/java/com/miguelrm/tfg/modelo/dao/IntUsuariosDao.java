package com.miguelrm.tfg.modelo.dao;

import com.miguelrm.tfg.modelo.beans.ListaUsuariosMensaje;
import com.miguelrm.tfg.modelo.beans.Usuario;

public interface IntUsuariosDao {

	public ListaUsuariosMensaje devuelveUsuarios();
	public ListaUsuariosMensaje devuelveUsuariosActivos();
	public ListaUsuariosMensaje devuelvePorEmail(String email);
	public String insertarUsuario(Usuario miUsuario);
	public String editarUsuario(Usuario miUsuario);
	public String borrarUsuario(String miEmail, boolean forceDelete);
	public ListaUsuariosMensaje devuelveByPalabra(String palabra);
	
}
