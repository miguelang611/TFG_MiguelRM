package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.ListaNoticiasMensaje;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.repository.NoticiasRepository;
import com.miguelrm.tfg.modelo.repository.CategoriasRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class NoticiasDaoImpl implements IntNoticiasDao {
	
	@Autowired
	NoticiasRepository miNoticiasRepo;

	@Autowired
	CategoriasRepository miCategoriasRepo;

	public Noticia findNoticiaById(int idNoticia) {
		return miNoticiasRepo.findById(idNoticia).orElse(null);
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
			noticia = miNoticiasRepo.findById(idNoticia).orElse(null);
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
	}


	@Override
	public ListaNoticiasMensaje devuelveByDestacada(String destacada) {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {

				miListaNoticias = miNoticiasRepo.findByDestacada(destacada);


		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias, mensaje);
		return miListaNoticiasMensaje;
	}

	

	@Override
	public ListaNoticiasMensaje devuelveByCategoria(int idCategoria) {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {
			miListaNoticias = miNoticiasRepo.findByCategoria(idCategoria);
			System.out.println(miListaNoticias);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias, mensaje);
		return miListaNoticiasMensaje;
	}

	@Override
	public ListaNoticiasMensaje devuelveTodos() {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {
			miListaNoticias = miNoticiasRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias, mensaje);
		return miListaNoticiasMensaje;
	}

	@Override
	public ListaNoticiasMensaje devuelveByPalabra(String palabra) {
		List<Noticia> miListaNoticias = null;
		String mensaje = null;
		try {

			List<Noticia> miListaEvNombre = miNoticiasRepo.findByNombreContains(palabra);
			if (miListaEvNombre != null) {
				List<Noticia> miListaEvSubtitulo = miNoticiasRepo.findBySubtituloContains(palabra);
				// Si no había encontrado nada por nombre, la lista lo que encuentre por
				// descripción
				if (miListaEvNombre.size() == 0) {
					miListaNoticias = miListaEvSubtitulo;

					// PERO, si la lista obtenida por nombre tiene algún dato,
					// leeremos la lista obtenida por descripción
					// y comprobaremos objeto a objeto contra la otra lista
					// Si no lo contiene, añadimos dicho objeto a la liista original
				} else {
					miListaNoticias = miListaEvNombre;
					for (int i = 0; i < miListaEvSubtitulo.size(); i++) {
						Noticia miNoticia = miListaEvSubtitulo.get(i);
						if (!miListaNoticias.contains(miNoticia)) {
							miListaNoticias.add(miNoticia);
						}
					}
				}

			}
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaNoticiasMensaje miListaNoticiasMensaje = new ListaNoticiasMensaje(miListaNoticias, mensaje);
		return miListaNoticiasMensaje;
	}

	// Para cancelar el noticia, subyace una modificación del mismo
	/*
	 * 1. Recuperamos un noticia intermedio por id
	 * 
	 * 2. En ese noticia intermedio forzamos no
	 * destacada
	 * 
	 * 3. Ejecutamos save de ese noticia intermedio
	 * 
	 * 4. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String modificarNoticia(int idNoticia, String newDestacada) {
		String mensaje = "Error desconocido";

		Noticia miNoticiaIntermedio = null;

		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el NoticiasRepository,
			// que interpreta a través de JPARepository
			miNoticiaIntermedio = miNoticiasRepo.findById(idNoticia).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean noticiaExiste = false;
		if (BDconexionOK) {
			if (miNoticiaIntermedio != null) {
				noticiaExiste = true;
			} else {
				mensaje = "Error -> Noticia con ID " + idNoticia + " no encontrado";
			}
		}

		
		if (noticiaExiste) {
			try {
				miNoticiasRepo.save(miNoticiaIntermedio);
				// Customizamos el mensaje para que diga que ha quedado activo/cancelado y si/no
				// destacada
				if (miNoticiaIntermedio.getDestacada().equals("s")) {
					mensaje = "El noticia " + miNoticiaIntermedio.getNombre() + " ha pasado a estar destacada";
				} else {
					mensaje = "El noticia " + miNoticiaIntermedio.getNombre() + " ha pasado a estar NO destacada";
				}
			} catch (Exception e) {
				mensaje = "Error -> No se ha podido salvar en la BBDD la modificación de del noticia a "
						+ miNoticiaIntermedio.getDestacada();
			}
		}

		return mensaje;

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
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean noticiaExiste = false;
		if (BDconexionOK) {
			if (miNoticiaIntermedio != null) {
				noticiaExiste = true;
			} else {
				mensaje = "Error -> Noticia con ID " + idNoticia + " no encontrado";
			}
		}

		if (noticiaExiste) {
			try {
				miNoticiasRepo.deleteById(idNoticia);
				mensaje = "El noticia " + miNoticiaIntermedio.getNombre() + " se ha eliminado correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el noticia, pero ha habido un error al salvar la eliminación del noticia"
						+ idNoticia + " en la BBDD";
			}

		}

		return mensaje;
	}

	// Borrar por categoria
	// Este método se basa en el borrarCategoria que creamos en la versión web del
	// programa,
	// donde primeros se comprobaba la existencia del categoria
	// luego se recuperaban sus noticias
	// y se eliminaban dichos noticias leyendo la lista
	// sólo que ya no borraremos el categoria

	@Override
	public String borrarNoticiasByCategoria(int idCategoria) {
		String mensaje = "Error desconocido";

		Categoria miCategoriaIntermedio = null;

		boolean BDconexionOK = false;
		try {
			miCategoriaIntermedio = miCategoriasRepo.findById(idCategoria).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean categoriaExiste = false;
		if (BDconexionOK) {
			if (miCategoriaIntermedio != null) {
				categoriaExiste = true;
			} else {
				mensaje = "Error -> Categoria con ID " + idCategoria + " no encontrado";
			}
		}

		List<Noticia> miListaNoticias = null;
		boolean readyDeleteNoticias = false;
		if (categoriaExiste) {

			miListaNoticias = devuelveByCategoria(idCategoria).getListaNoticias();
			String mensajeEv = devuelveByCategoria(idCategoria).getMensaje();

			// Si la lista está en null, ha habido un error, asignamos mensaje
			if (miListaNoticias == null) {

				mensaje = mensajeEv;
				// Si no es nula y
			} else {

				// tiene tamaño de 0, sin mensaje de error
				if (miListaNoticias.size() == 0 && mensajeEv == null) {
					mensaje = "Error -> El categoria " + miCategoriaIntermedio.getNombre()
							+ " existe, pero no había ningún noticia, así que no se ha eliminado nada";
				}
				// Estamos listos para eliminar el categoria directamente
				// tiene tamaño distinto de 0, sin mensaje de error
				// quiere decir que hay noticias asociados a ese categoria
				else if (miListaNoticias.size() != 0 && mensajeEv == null) {
					// Estamos listos para eliminar esos noticias, antes de eliminar
					// el categoria
					readyDeleteNoticias = true;

					// En otro caso, asignamos el mensaje de error
				} else {
					mensaje = mensajeEv;
				}

			}

		}

		// Si viene por aquí quiere decir que la lista tiene al menos 1 noticia del categoria,
		// que hay que eliminar previamente
		if (readyDeleteNoticias) {
			// Leemos con un for la lista, y pasamos cada id de noticia como parámetro
			// a la función de borrarNoticia del NoticiasDaoImpl
			try {
				for (int i = 0; i < miListaNoticias.size(); i++) {
					borrarNoticia(miListaNoticias.get(i).getIdNoticia());
				}
				mensaje = "Se han borrado todos los noticias del categoria " + miCategoriaIntermedio.getNombre()
						+ " correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el categoria y sus noticias, pero se produjo un error al eliminarlos";
			}
		}

		return mensaje;

	}

	// Para insertar simplemente ejecutamos save del objeto que nos llega del
	// controller
	@Override
	public String insertarNoticia(Noticia miNoticia) {
		String insertarRdo = "";
		try {

			String msgCheckID = devuelvePorId(miNoticia.getIdNoticia()).getMensaje();
			System.out.println(msgCheckID);

			// Hay un noticia con ese ID
			if (msgCheckID == null) {
				insertarRdo = "Error -> No se puede insertar un noticia con dicho id, ya que en la BBDD hay otro noticia con dicho ID. ¡Verifíquelo!";
			}

			// El mensaje no viene vacío pero la lista lo está --> no hay noticias con ese id
			if (msgCheckID != null && !msgCheckID.contains("Error") && !msgCheckID.contains("Error -> Noticia con ID 0 no encontrado")) {
				List<Noticia> miListaTotal = devuelveTodos().getListaNoticias();
				String mensaje = devuelveTodos().getMensaje();
				// Si no hay error
				if (mensaje == null) {
					// Y el noticia no está en la lista de noticias
					// (compara por debajo, pero excluye categoria, destcado e id)
					if (!miListaTotal.contains(miNoticia)) {
						// Si no esá, guardamos
						miNoticiasRepo.save(miNoticia);
						insertarRdo = "El noticia " + miNoticia.getNombre() + " se ha insertado correctamente";
					}
					// En caso contrario, no hacemos la inserción
					else {
						insertarRdo = "Error -> No se puede insertar un noticia que ya existe. Si lo desea, puede editarlo";
					}
				} else {
					insertarRdo = mensaje;
				}
			}

			if(msgCheckID != null && msgCheckID.contains("Error -> Noticia con ID 0 no encontrado")) {
				
				miNoticiasRepo.save(miNoticia);
				insertarRdo = "El noticia " + miNoticia.getNombre() + " se ha insertado correctamente";

				
			}
			
		} catch (Exception e) {
			insertarRdo = "Error al insertar el noticia. Verifique que los datos a insertar son correctos";
			e.printStackTrace();
		}
		
		return insertarRdo;
	}

	/*
	 * 1. Recuperamos el id del noticia que viene, si falla es mala señal
	 * 
	 * 2. En ese noticia, leemos que se cumpla que para ser destacada tiene que estar
	 * activo
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
		} catch (Exception e) {
			editarRdo = "Error -> no se pudo recuperar el id del noticia a modificar";
		}

		boolean BDconexionOK = false;
		if (noticiaIdCorrecto) {
			try {
				miNoticiaIntermedio = miNoticiasRepo.findById(codigoNoticia).orElse(null);
				BDconexionOK = true;
			} catch (Exception e) {
				editarRdo = "Error -> fallo de conexión a la BBDD";
			}
		}

		boolean noticiaExiste = false;
		if (BDconexionOK) {
			if (miNoticiaIntermedio != null) {
				noticiaExiste = true;
			} else {
				editarRdo = "Error -> Noticia con ID " + codigoNoticia + " no encontrado";
			}
		}

		if (noticiaExiste) {
			try {
				miNoticiaIntermedio = miNoticia;
				miNoticiasRepo.save(miNoticiaIntermedio);
				editarRdo = "El noticia " + miNoticiaIntermedio.getNombre() + " con id "
						+ miNoticiaIntermedio.getIdNoticia() + " se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrado el noticia, pero ha habido un error al salvar la eliminación del noticia "
						+ codigoNoticia + " en la BBDD";
			}
		}

		return editarRdo;
	}

}
