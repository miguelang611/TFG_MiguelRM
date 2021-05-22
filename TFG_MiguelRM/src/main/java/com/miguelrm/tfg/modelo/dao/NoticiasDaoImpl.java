package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.ListaNoticiasMensaje;
import com.miguelrm.tfg.modelo.repository.NoticiasRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class NoticiasDaoImpl implements IntNoticiasDao {

	@Autowired
	NoticiasRepository miNoticiasRepo;

	public Noticia findNoticiaById(int idNoticia) {
		return miNoticiasRepo.findById(idNoticia).orElse(null);
	}
	
	//Todos los métodos devuelve llaman a métodos contenidos en el repository.
	//Si salta excepción es que falla la conexión a la BD
	@Override
	public ListaNoticiasMensaje devuelvePorId(int idNoticia) {
		List<Noticia> miListaNoticias = new ArrayList<>();
		String mensaje = null;
		Noticia noticia = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en la noticiasRepository,
			// que interpreta a través de JPARepository
			noticia = miNoticiasRepo.findById(idNoticia).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		if (BDconexionOK) {
			if(noticia != null) {
				miListaNoticias.add(noticia);
			}else {
				mensaje = "Noticia con ID "+idNoticia+" no encontrada";
			}
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias,mensaje);
		return miListaNoticiasMensaje;
	}
	
	@Override
	public ListaNoticiasMensaje devuelveDestacados() {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {
			//Los noticias destacados tienen que estar activos obligatoriamente
			miListaNoticias = miNoticiasRepo.findByDestacada("s");
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias,mensaje);
		return miListaNoticiasMensaje;
	}
	
	@Override
	public ListaNoticiasMensaje devuelvePorCategoria(int idCategoria) {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {
			miListaNoticias = miNoticiasRepo.findByCategoria(idCategoria);
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias,mensaje);
		return miListaNoticiasMensaje;
	}
	
	@Override
	public ListaNoticiasMensaje devuelveTodos() {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {
			miListaNoticias = miNoticiasRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias,mensaje);
		return miListaNoticiasMensaje;
	}

	
	
	/*
	 * 1. Recuperamos un noticia intermedio por id, para comprobar que exista
	 * 
	 * 2. Ejecutamos el método deleteById del repository
	 * 
	 * 3. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String borrarNoticia(int idNoticia) {
		
		String mensaje = "Error desconocido";
		
		Noticia miNoticiaIntermedio = null;
		
		boolean BDconexionOK = false;
		try {
			miNoticiaIntermedio = miNoticiasRepo.findById(idNoticia).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		boolean noticiaExiste = false;
		if (BDconexionOK) {
			if(miNoticiaIntermedio != null) {
				noticiaExiste = true;
			}else {
				mensaje = "Noticia con ID "+idNoticia+" no encontrada";
			}
		}
		
		if(noticiaExiste) {
			try {
				miNoticiasRepo.deleteById(idNoticia);
				mensaje = "la noticia "+miNoticiaIntermedio.getNombre()+" se ha eliminado correctamente";
			} catch (Exception e) {
				mensaje = "Se ha encontrada la noticia, pero ha habido un error al salvar la eliminación dla noticia"+idNoticia+" en la BBDD";
			}
		
		}
		
		return mensaje;
	}


	//Para insertar simplemente ejecutamos save del objeto que nos llega del controller
	@Override
	public String insertarNoticia(Noticia miNoticia) {
		String insertarRdo = "";
		try {
			miNoticiasRepo.save(miNoticia);
			insertarRdo = "Noticia "+miNoticia.getNombre()+" insertado correctamente";
		} catch (Exception e) {
			insertarRdo = "Error al insertar la noticia. Posible error en BBDD";
			e.printStackTrace();
		}
		return insertarRdo;
	}

	/*
	 * 1. Recuperamos el id dla noticia que viene, si falla es mala señal
	 * 
	 * 2. En ese noticia, leemos que se cumpla que para ser destacado tiene
	 * que estar activo
	 * 
	 * 3. Recuperamos un objeto intermedio por id
	 * 
	 * 4. Hacemos noticiaIntermedio = noticia, es decir, decimos que el sincronizado
	 * con BD tome los valores del modificado
	 * 
	 * 5. Hacemos save de ese objeto
	 * 
	 * 6. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String editarNoticia(Noticia miNoticia) {
		
		String editarRdo = "Error desconocido";
		
		Noticia miNoticiaIntermedio = null;
		
		int codigoNoticia = 0;
		
		boolean noticiaIdCorrecto = false;
		try {
			codigoNoticia = miNoticia.getIdNoticia();
			noticiaIdCorrecto = true;
		}catch(Exception e){
			editarRdo = "Error al recuperar el id dla noticia a modificar";
		}
		
		
		boolean BDconexionOK = false;
		if(noticiaIdCorrecto) {
		try {
			miNoticiaIntermedio = miNoticiasRepo.findById(codigoNoticia).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			editarRdo = "Error de conexión a la BBDD";
		}
		}
		
		boolean noticiaExiste = false;
		if (BDconexionOK) {
			if(miNoticiaIntermedio != null) {
				noticiaExiste = true;
			}else {
				editarRdo = "Noticia con ID "+codigoNoticia+" no encontrada";
			}
		}
		
		
		if(noticiaExiste) {
			try {
				miNoticiaIntermedio = miNoticia;
				miNoticiasRepo.save(miNoticiaIntermedio);
				editarRdo = "la noticia "+miNoticiaIntermedio.getNombre()+" con id "+miNoticiaIntermedio.getIdNoticia()+" se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrada la noticia, pero ha habido un error al salvar la eliminación de la noticia "+codigoNoticia+" en la BBDD";
			}
		}
		
		return editarRdo;
	}


}