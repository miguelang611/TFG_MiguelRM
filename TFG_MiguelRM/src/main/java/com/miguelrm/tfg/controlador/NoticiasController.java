package com.miguelrm.tfg.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.dao.IntNoticiasDao;
import com.miguelrm.tfg.modelo.dao.IntCategoriasDao;


/* ================================================== CONTROLADOR DE NOTICIAS ================================================== 
 * 
 * Se trata del controlador "principal", ya que cuenta con más funciones que su hermano de categorias
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller

//Anotamos que vamos a entrar por /noticias
@RequestMapping("/noticias")

public class NoticiasController {

	
	//Tenemos noticiasDao y categoriasDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntNoticiasDao noticiasDao;

	@Autowired
	IntCategoriasDao categoriasDao;

	/*
	 * ============================= MÉTODO AUXILIAR MANDALISTATIPOS ============================= 
	 * 
	 * Tenemos un método auxiliar que llamaremos desde los métodos principales que se encarga
	 * de devolver la lista con los categorias. Esto es necesario para toda la aplicación:
	 *
	 * En lista: porque en el nav tenemos noticias por categoria
	 * En creación y edición: además de por eso, porque usaremos la lista para un desplegable
	 * 
	 * =========================================================================================== 
	 */

	public Model mandaListaCategorias(Model model) {
		String mensaje = "";
		List<Categoria> listaCategorias = categoriasDao.devuelveCategorias().getListaCategorias();
		String mensajeCategorias = categoriasDao.devuelveCategorias().getMensaje();

		if (listaCategorias != null) {
			if (listaCategorias.size() == 0 && mensajeCategorias == null) {
				mensajeCategorias = "No se han encontrado categorias en la Base de Datos";
			}
		}

		if (mensajeCategorias != null) {
			mensaje = "Error con los categorias de noticias: " + mensajeCategorias;
		}
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("mensajeError",mensaje);
		
		return model;
	}
	 
	/////////////////////////////////////// BLOQUE 1 --> LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * ==================================== MÉTODO VERACTIVOS ===================================== 
	 * 
	 * 0. Entramos por GET, y solo tenemos de entrada el Model
	 * 
	 * 1. Llamamos al método devuelveActivos() del noticiasDao, y almacenamos la lista de noticias
	 * y el mensaje --> tenemos un Beans para devolver ambas cosas.
	 * 
	 * 2. Si la lista no es nula, el mensaje es nulo, y el tamaño es 0 --> la conexión y todo es
	 * correcto, sólo que no hay noticias activos
	 * 
	 * 3. Si el mensaje es nulo, es decir, es todo correcto, llamamos a mandaListaCategorias, para que
	 * nos traiga la lista de categorias en el model y luego poder usarla en el nav
	 * 
	 * 4. Agregamos al model el mensaje, la lista de noticias, el categoria de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaNoticias,
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */

	/*
	@GetMapping("/activos")
	public String verActivos(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveActivos().getListaNoticias();
		String mensaje = noticiasDao.devuelveActivos().getMensaje();
		
		model = mandaListaCategorias(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias activos en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "activos");
		model.addAttribute("origen","/activos");

		return "listaNoticias";

	}*/
	
	/*
	 * =================================== MÉTODO VERTODOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/todos")
	public String verTodos(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveTodos().getListaNoticias();
		String mensaje = noticiasDao.devuelveTodos().getMensaje();

		model = mandaListaCategorias(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "(todos)");
		model.addAttribute("origen","/todos");

		return "listaNoticias";
	}
	
	/*
	 * =================================== MÉTODO VERDESTACADOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	
	@GetMapping("/destacados")
	public String verDestacados(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveDestacados().getListaNoticias();
		String mensaje = noticiasDao.devuelveDestacados().getMensaje();

		model = mandaListaCategorias(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "destacados");
		model.addAttribute("origen","/destacados");


		return "listaNoticias";

	}

	/*
	 * =================================== MÉTODO VERPORTIPO ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método devuelvePorCategoria() del noticiasDao, pasando el id del categoria,
	 * y almacenamos la lista de noticias y el mensaje
	 * 
	 * 2. Si la lista de noticias no es nula y el mensaje es nulo, tenemos conexión a la BD -->
	 * Recuperamos la lista de categorias
	 * 
	 * 3. Si la lista de noticias tiene tamaño 0, y la recuperación de lista de categorias es correcta,
	 * porque el array tiene 1 categoria --> el categoria existe, pero no hay noticias
	 * 
	 * Si el array está vacío, es que no existe ningún categoria con ese id
	 * 
	 * (Si viene con mensaje ya sabemos que habrá fallado la conexión a BD)
	 * 
	 * 4. Agregamos el nombre del categoria para indicarlo en el jsp (si lo anterior se ha verificado)
	 * 
	 * 5. Agregamos al model el mensaje, la lista de noticias, y el categoria de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaNoticias
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/{idCategoria}")
	public String verPorCategoria(Model model, @PathVariable(name = "idCategoria") int idCategoria) {

		List<Noticia> listaNoticias = noticiasDao.devuelvePorCategoria(idCategoria).getListaNoticias();
		String mensaje = noticiasDao.devuelvePorCategoria(idCategoria).getMensaje();

		String nombreCategoria = "";
		Categoria categoria = null;
		if (listaNoticias != null && mensaje == null) {
			//Si la lista no es nul, pero está vacíay el mensaje es nulo, la conexión a la BBDD es correcta,
			//así que vertificaremos si el categoria solicitado existe
			
			List<Categoria> listaCategorias = categoriasDao.devuelvePorId(idCategoria).getListaCategorias();
			String mensajeCategoria = categoriasDao.devuelvePorId(idCategoria).getMensaje();
			
			if(listaNoticias.size() == 0) {
				if (listaCategorias != null && mensajeCategoria == null) {
					System.out.println(listaCategorias);
					//El categoria existe, simplemente no hay noticias de ese categoria
					if (listaCategorias.size() == 1) {
						categoria = listaCategorias.get(0);
						mensaje = "No se han encontrado noticias del categoria " + categoria.getNombre();
					//El categoria no existe, por tanto es imposible que haya noticias de ese categoria
					} else {
						mensaje = "No se han encontrado noticias del categoria " + idCategoria + ", ya que no existe dicho categoria";
					}
				}else {
					mensaje = "Error decsconocido de categorias";
				}
			}else {
				nombreCategoria = listaCategorias.get(0).getNombre();
			}
			
			
		}
		
		if (mensaje == null) {
			model = mandaListaCategorias(model);
			model.addAttribute("categoria","del categoria "+nombreCategoria);
		}else {
			model.addAttribute("mensajeError", mensaje);
		}
		

		model.addAttribute("miListaNoticias", listaNoticias);

		model.addAttribute("mensajeError",mensaje);
		
		model.addAttribute("origen","/"+idCategoria);

		
		return "listaNoticias";
		
	}
		
	
	/////////////////////////////////// BLOQUE 2 --> BOTONES EN LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * =================================== MÉTODOS CANCELAR Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método correspondiente del noticiasDao, pasando el id del categoria,
	 * y almacenamos la lista de noticias y el mensaje
	 * 
	 * 
	 * 2. Agregamos al model el mensaje, indicamos url de destino (al llegar al jsp, cambiará la url
	 * a mostrar aunque usemos forward), y hacemos forward a noticias (activos, destacados, etc)
	 * 
	 * ================================================================================================= 
	 */
	/*
	@GetMapping("/cancelar/{id}/{url}")
	public String cancelar(Model model, @PathVariable(name = "id") int idNoticia, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		try {
			mensaje = noticiasDao.cancelarNoticia(idNoticia);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al cancelar la noticia";
			e.printStackTrace();
		}


		model.addAttribute("mensaje", mensaje);
		model.addAttribute("urlDestino", "/noticias/"+destino);


		return "forward:/noticias/"+destino;

	}*/

	@GetMapping("/eliminar/{id}/{url}")
	public String eliminar(Model model, @PathVariable(name = "id") int idNoticia, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		try {
			mensaje = noticiasDao.borrarNoticia(idNoticia);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar la noticia";
			e.printStackTrace();
		}

		model.addAttribute("mensaje", mensaje);
		model.addAttribute("urlDestino", "/noticias/"+destino);


		return "forward:/noticias/"+destino;


	}
	
	/////////////////////////////////// BLOQUE 3 --> A FORMULARIO  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * =================================== MÉTODOS NUEVO Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y en el caso de editar, y el id por PathVariable
	 * 
	 * 1. Llamamos al método mandaListaCategorias
	 * 
	 * 2. Almacenamos acción, y el destino que procesará el resultado del formulario
	 * 
	 * 3. Mandamos al jsp
	 * 
	 * EXTRA --> En el caso de edición, recuperaremos antes la noticia correspondiente invocando
	 * el método devuelvePorId del noticiasDao
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/create/{url}")
	public String goToNuevo(Model model, @PathVariable(name = "url") String origen) {

		
		model = mandaListaCategorias(model);
		model.addAttribute("accion", "CREACIÓN");
		model.addAttribute("destino", "/noticias/procesaCreate/"+origen);

		return "formNoticia";

	}
	
	@GetMapping("/editar/{id}/{url}")
	public String editar(Model model, @PathVariable(name = "id") int idNoticia, @PathVariable(name = "url") String origen) {

		List<Noticia> listaNoticias = noticiasDao.devuelvePorId(idNoticia).getListaNoticias();
		String mensaje = noticiasDao.devuelvePorId(idNoticia).getMensaje();

		boolean noticiaOK = false;
		if (listaNoticias != null) {
			if (listaNoticias.size() == 1 && mensaje == null) {
				Noticia noticia = listaNoticias.get(0);
				System.out.println(noticia.getCategoria());
				Categoria categoria = noticia.getCategoria();
				model.addAttribute("noticia", noticia);
				model.addAttribute("categoria", categoria);
				model.addAttribute("accion", "EDICIÓN");
				model.addAttribute("destino", "/noticias/procesaEditar/"+origen);
				noticiaOK = true;
			}
		}

		if (noticiaOK) {
			model = mandaListaCategorias(model);
		}

		model.addAttribute("mensaje", mensaje);

		return "formNoticia";

	}
	
	
	/*
	 * ================================ MÉTODOS PROCESANUEVO/PROCESAEDITAR ================================= 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y el objeto Noticia
	 * 
	 * (Si es nuevo, lo ponemos activo forzosamente)
	 * 
	 * 2. Invocamos a insertarNoticia/editarNoticia del noticiasDao, guardando el mensaje
	 * 
	 * 3. Almacenamos en el model el mensaje y la futura url de destino
	 * 
	 * 4. Hacemos forward a noticias (activos, destacados, por X categoria)
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/procesaCreate/{url}")
	public String procesarFormulario(Model model, @PathVariable(name = "url") String destino, Noticia noticia) {

		System.out.println(noticia);

		//noticia.setEstado("activo");

		String mensaje = noticiasDao.insertarNoticia(noticia);

		System.out.println(noticia);
		
		System.out.println(mensaje);

		model.addAttribute("mensaje", mensaje);
		model.addAttribute("urlDestino", "/noticias/"+destino);

		return "forward:/noticias/"+destino;

	}
	

	@GetMapping("/procesaEditar/{url}")
	public String procesarEditarNoticia(Model model,  @PathVariable(name = "url") String destino, Noticia noticia) {
		
		String mensaje = null;

		System.out.println(noticia);

		mensaje = noticiasDao.editarNoticia(noticia);

		System.out.println(noticia);

		System.out.println(mensaje);

		model.addAttribute("mensaje", mensaje);
		model.addAttribute("urlDestino", "/noticias/"+destino);

		System.out.println(noticia);

		return "forward:/noticias/"+destino;

	}





}
