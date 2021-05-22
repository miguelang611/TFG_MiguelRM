package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.ListaCategoriasMensaje;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.repository.CategoriasRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class CategoriasDaoImpl implements IntCategoriasDao {
	


	@Autowired
	CategoriasRepository miCategoriasRepo;

	
	@Autowired
	IntNoticiasDao noticiasDao;
	
	//Métodos análogos a los anteriores
	@Override
	public ListaCategoriasMensaje devuelvePorId(int idCategoria) {
		List<Categoria> miListaCategorias = new ArrayList<>();

		String mensaje = null;
		Categoria categoria = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el CategoriasRepository,
			// que interpreta a través de JPARepository
			categoria = miCategoriasRepo.findById(idCategoria).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		if (BDconexionOK) {
			if(categoria != null) {
				miListaCategorias.add(categoria);
			}else {
				mensaje = "Categoria con ID "+idCategoria+" no encontrado";
			}
		}
		ListaCategoriasMensaje miListaCategoriasMensaje = new ListaCategoriasMensaje(miListaCategorias,mensaje);
		return miListaCategoriasMensaje;
	}
	
	@Override
	public ListaCategoriasMensaje devuelveCategorias() {
		
		List<Categoria> miListaCategorias = null;
		String mensaje = null;
		try {
			// Hemos definido este método en el CategoriasRepository,
			// que interpreta a través de JPARepository
			miListaCategorias = miCategoriasRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaCategoriasMensaje miListaCategoriasMensaje = new ListaCategoriasMensaje(miListaCategorias,mensaje);
		return miListaCategoriasMensaje;
	}

	@Override
	public String insertarCategoria(Categoria miCategoria) {
		String insertarRdo = "Error desconocido";
		try {
			miCategoriasRepo.save(miCategoria);
			insertarRdo = "Categoria "+miCategoria.getNombre()+" insertado correctamente";
		} catch (Exception e) {
			insertarRdo = "Error al insertar el categoria. Posible error en BBDD";
			e.printStackTrace();
		}
		return insertarRdo;
	}

	
	/* El caso de borrar categoria es más complejo que el de borrar noticia, puesto que
	 * para eliminar un categoria, deberemos deshacernos antes de todos los noticias asociados
	 * a dicho categoria:
	 */
	@Override
	public String borrarCategoria(int idCategoria) {
		
		String mensaje = "Error desconocido";
		
		Categoria miCategoriaIntermedio = null;
		
		boolean BDconexionOK = false;
		try {
			miCategoriaIntermedio = miCategoriasRepo.findById(idCategoria).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		
		boolean categoriaExiste = false;
		if (BDconexionOK) {
			if(miCategoriaIntermedio != null) {
				categoriaExiste = true;
			}else {
				mensaje = "Categoria con ID "+idCategoria+" no encontrado";
			}
		}
		
		List<Noticia> miListaNoticias = null;		
		boolean readyDeleteNoticias = false;
		boolean readyDeleteCategoria = false;
		if(categoriaExiste) {
			
			miListaNoticias = noticiasDao.devuelvePorCategoria(idCategoria).getListaNoticias();
			String mensajeEv = noticiasDao.devuelvePorCategoria(idCategoria).getMensaje();
			
			//Si la lista está en null, ha habido un error, asignamos mensaje 
			if(miListaNoticias == null ) {
				
				mensaje = mensajeEv;
			//Si no es nula y
			}else {
				
				//tiene tamaño de 0, sin mensaje de error
				if( miListaNoticias.size() == 0 && mensajeEv == null)
					//Estamos listos para eliminar el categoria directamente
					readyDeleteCategoria = true;
				
				//tiene tamaño distinto de 0, sin mensaje de error
				//quiere decir que hay noticias asociados a ese categoria
				else if(miListaNoticias.size() != 0 && mensajeEv == null) {
					//Estamos listos para eliminar esos noticias, antes de eliminar
					//el categoria
					readyDeleteNoticias = true;
					
				//En otro caso, asignamos el mensaje de error
				}else {
					mensaje = mensajeEv;
				}
				
			}
			
		}
		
		//Si viene por aquí quiere decir que la lista tiene al menos 1 noticia del categoria,
		//que hay que eliminar previamente
		if(readyDeleteNoticias) {
			//Leemos con un for la lista, y pasamos cada id de noticia como parámetro
			//a la función de borrarNoticia del NoticiasDaoImpl
				try {
					for(int i=0; i<miListaNoticias.size(); i++) {
						noticiasDao.borrarNoticia(miListaNoticias.get(i).getIdNoticia());
					}
					//Si no ha fallado, estamos listos para eliminar el categoria
					readyDeleteCategoria = true;
				}catch(Exception e){
					mensaje = "Se produjo un error al eliminar los noticias asociados al categoria";
				}
		}
		
		//Por último, una vez comprobado que no tenemos noticias asociados,
		//podemos eleminar el categoria por fin por medio del deleteById definido en el repository
		if(readyDeleteCategoria) {
			try {
				miCategoriasRepo.deleteById(idCategoria);
				mensaje = "El categoria "+miCategoriaIntermedio.getNombre()+" se ha eliminado correctamente";
			} catch (Exception e) {
				mensaje = "Se ha encontrado el categoria, y se ha confirmado que ya no quedan noticias asociados, pero ha habido un error al salvar la eliminación del categoria"+idCategoria+" en la BBDD";
			}
		}
		
		return mensaje;
		
	}
	
	
	//Método análogo al de editar de noticias, con la salvedad de que no se
	//requieren verificaciones en el noticia modificado del estilo
	//destacado vs activo
	@Override
	public String editarCategoria(Categoria miCategoria) {
		
		String editarRdo = "Error desconocido";
		
		Categoria miCategoriaIntermedio = null;
		
		int codigoCategoria = 0;
		
		boolean categoriaIdCorrecto = false;
		try {
			codigoCategoria = miCategoria.getIdCategoria();
			categoriaIdCorrecto = true;
		}catch(Exception e){
			editarRdo = "Error al recuperar el id del categoria a modificar";
		}
		

		boolean BDconexionOK = false;
		if(categoriaIdCorrecto) {
		try {
			miCategoriaIntermedio = miCategoriasRepo.findById(codigoCategoria).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			editarRdo = "Error de conexión a la BBDD";
		}
		}
		
		boolean categoriaExiste = false;
		if (BDconexionOK) {
			if(miCategoriaIntermedio != null) {
				categoriaExiste = true;
			}else {
				editarRdo = "Categoria con ID "+codigoCategoria+" no encontrado";
			}
		}
		
		
		if(categoriaExiste) {
			try {
				miCategoriaIntermedio = miCategoria;
				miCategoriasRepo.save(miCategoriaIntermedio);
				editarRdo = "El categoria "+miCategoriaIntermedio.getNombre()+" con id "+miCategoriaIntermedio.getIdCategoria()+" se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrado el categoria, pero ha habido un error al salvar la eliminación del categoria "+codigoCategoria+" en la BBDD";
			}
		}
		
		return editarRdo;
	}

	
	
	
}