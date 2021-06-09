package com.miguelrm.tfg.modelo.dao;

import java.util.List;

import com.miguelrm.tfg.modelo.beans.Perfile;

public interface IntPerfilesDao {
	
	public List<Perfile> devuelveTodos();
	public List<Perfile> devuelveByNombre(String nombre);
	
	
}