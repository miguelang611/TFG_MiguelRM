package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.Perfile;
import com.miguelrm.tfg.modelo.beans.ListaNoticiasMensaje;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.repository.NoticiasRepository;
import com.miguelrm.tfg.modelo.repository.PerfilesRepository;
import com.miguelrm.tfg.modelo.repository.CategoriasRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class PerfilesDaoImpl implements IntPerfilesDao {
	
	/* RESPECTRO AL ANTERIOR EVENTOSDAOIMPL:
	 * 
	 * Se ha modificado la parte de destacadas, para poder recibir parámetros
	 * 
	 * Se ha mejorado la sección de insertar --> verifica que no haya un noticia con ese id, o que coincidan los datos de dentro
	 * 
	 * Se ha añadido el método de encontrar por palabra --> se divide en 2 métodos del repository, primero busca la palabra en nombres
	 * y luego en subtituloes
	 * 
	 */


	@Autowired
	PerfilesRepository miPerfilesRepo;

	/*
	public Noticia findNoticiaById(int idNoticia) {
		return miPerfilesRepo.findById(idNoticia).orElse(null);
	}

	// Todos los métodos devuelve llaman a métodos contenidos en el repository.
	// Si salta excepción es que falla la conexión a la BD
	@Override
	public ListaNoticiasMensaje devuelvePorId(int idNoticia) {
		List<Noticia> miListaNoticias = new ArrayList<>();
		String mensaje = null;
		Noticia noticia = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el NoticiasRepository,
			// que interpreta a través de JPARepository
			noticia = miPerfilesRepo.findById(idNoticia).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		if (BDconexionOK) {
			if (noticia != null) {
				miListaNoticias.add(noticia);
			} else {
				mensaje = "Error -> Noticia con ID " + idNoticia + " no encontrado";
			}
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias, mensaje);
		return miListaNoticiasMensaje;
	}*/


	@Override
	public List<Perfile> devuelveTodos() {
		List<Perfile> miLista = null;
		String mensaje = null;
		try {
			miLista = miPerfilesRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		
		return miLista;
	}
	
	@Override
	public List<Perfile> devuelveByNombre(String nombre) {
		List<Perfile> miLista = null;
		String mensaje = null;
		try {
			miLista = miPerfilesRepo.findByNombre(nombre);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		
		return miLista;
	}

	

}
