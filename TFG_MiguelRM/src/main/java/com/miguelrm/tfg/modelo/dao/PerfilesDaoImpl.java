package com.miguelrm.tfg.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Perfile;
import com.miguelrm.tfg.modelo.repository.PerfilesRepository;

@Service
public class PerfilesDaoImpl implements IntPerfilesDao {


	@Autowired
	PerfilesRepository miPerfilesRepo;

	@Override
	public List<Perfile> devuelveTodos() {
		List<Perfile> miLista = null;
		try {
			miLista = miPerfilesRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return miLista;
	}
	
	@Override
	public List<Perfile> devuelveByNombre(String nombre) {
		List<Perfile> miLista = null;
		try {
			miLista = miPerfilesRepo.findByNombre(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return miLista;
	}

	

}
