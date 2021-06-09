package com.miguelrm.tfg.controlador;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.dao.IntNoticiasDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;
import com.miguelrm.tfg.servicios.PreparaServImpl;
import com.miguelrm.tfg.modelo.dao.IntCategoriasDao;


/* ================================================== CONTROLADOR DE EVENTOS ================================================== 
 * 
 * Se trata del controlador "principal", ya que cuenta con más funciones que su hermano de categorias
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller

//Anotamos que vamos a entrar por /gestion/noticias
@RequestMapping("/gestion/noticias")

public class NoticiasGestionController {

	
	//Tenemos noticiasDao y categoriasDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntNoticiasDao noticiasDao;

	@Autowired
	IntCategoriasDao categoriasDao;
	
	@Autowired
	IntPreparaServ prepWeb;

	 
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
	 * 3. Si el mensaje es nulo, es decir, es todo correcto, llamamos a prepWeb.envia, para que
	 * nos traiga la lista de categorias en el model y luego poder usarla en el nav
	 * 
	 * 4. Agregamos al model el mensaje, la lista de noticias, el categoria de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaNoticias,
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */


	
	/*
	 * =================================== MÉTODO VERDESTACADOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	
	@GetMapping("/destacadas")
	public String verDestacadas(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveByDestacada("s").getListaNoticias();
		String mensaje = noticiasDao.devuelveByDestacada("s").getMensaje();

		model = prepWeb.envia(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias destacadas en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "destacadas");
		model.addAttribute("origen","/destacadas");


		return"/noticias/gestionNoticias";

	}
	

	
	/*
	 * =================================== MÉTODO VERTODOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/todas")
	public String verTodos(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveTodos().getListaNoticias();
		String mensaje = noticiasDao.devuelveTodos().getMensaje();

		model = prepWeb.envia(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "(todas)");
		model.addAttribute("origen","/todas");

		return"/noticias/gestionNoticias";
	}
	

	
	@GetMapping("/nodestacadas")
	public String verNoDestacadas(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveByDestacada("").getListaNoticias();
		String mensaje = noticiasDao.devuelveByDestacada("").getMensaje();

		model = prepWeb.envia(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias sin destacar en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "no destacadas");
		model.addAttribute("origen","/nodestacadas");


		return"/noticias/gestionNoticias";

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

		List<Noticia> listaNoticias = noticiasDao.devuelveByCategoria(idCategoria).getListaNoticias();
		String mensaje = noticiasDao.devuelveByCategoria(idCategoria).getMensaje();

		model = prepWeb.envia(model);

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
			model.addAttribute("categoria","del categoria "+nombreCategoria);
		}else {
			model.addAttribute("mensajeError", mensaje);
		}
		

		model.addAttribute("miListaNoticias", listaNoticias);

		model.addAttribute("mensajeError",mensaje);
		
		model.addAttribute("origen","/"+idCategoria);

		
		return"/noticias/gestionNoticias";
		
	}
	
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Noticia> listaNoticias = null;
		String mensaje = "";
		String categoria = "";
		if(palabra != null) {
			listaNoticias = noticiasDao.devuelveByPalabra(palabra).getListaNoticias();
			mensaje = noticiasDao.devuelveByPalabra(palabra).getMensaje();
			categoria = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", categoria);
		model.addAttribute("origen","/contiene/"+palabra);
		
		return"/noticias/gestionNoticias";
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
	 * a mostrar aunque usemos forward), y hacemos forward a noticias (activos, destacadas, etc)
	 * 
	 * ================================================================================================= 
	 */
	

	
	@GetMapping("/destacar/{id}/{url}/{destacada}")
	public String destacar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idnoticia, @PathVariable(name = "url") String destino, @PathVariable(name="destacada") String destacada) {
		String mensaje = "";
		
		if(destacada.equals("si") || destacada.equals("no")) {
			if(destacada.equals("si")) {
				destacada = "s";
			}else {
				destacada = "";
			}
			System.out.println(destacada);
			try {
				mensaje = noticiasDao.modificarNoticia(idnoticia, destacada);
			} catch (Exception e) {
				mensaje = "Fallo desconocido al cambiar el destacada del noticia";
				e.printStackTrace();
			}
		}else {
			mensaje = "Sólo se puede destacar un noticia a 'si' o 'no'";
		}
		
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/noticias/"+destino);

		return "redirect:/gestion/noticias/"+destino;

	}

	@GetMapping("/eliminar/{id}/{url}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idnoticia, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		try {
			mensaje = noticiasDao.borrarNoticia(idnoticia);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar el noticia";
			e.printStackTrace();
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/noticias/"+destino);


		return "redirect:/gestion/noticias/"+destino;


	}
	
	/////////////////////////////////// BLOQUE 3 --> A FORMULARIO  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * =================================== MÉTODOS NUEVO Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y en el caso de editar, y el id por PathVariable
	 * 
	 * 1. Llamamos al método prepWeb.envia
	 * 
	 * 2. Almacenamos acción, y el destino que procesará el resultado del formulario
	 * 
	 * 3. Mandamos al jsp
	 * 
	 * EXTRA --> En el caso de edición, recuperaremos antes el noticia correspondiente invocando
	 * el método devuelvePorId del noticiasDao
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/create/{url}")
	public String goToNuevo(Model model, @PathVariable(name = "url") String origen) {

		
		model = prepWeb.envia(model);
		model.addAttribute("accion", "CREACIÓN");
		model.addAttribute("destino", "/gestion/noticias/procesaCreate/"+origen);

		return "noticias/formNoticia";

	}
	
	@GetMapping("/editar/{id}/{url}")
	public String editar(Model model, @PathVariable(name = "id") int idnoticia, @PathVariable(name = "url") String origen) {

		List<Noticia> listaNoticias = noticiasDao.devuelvePorId(idnoticia).getListaNoticias();
		String mensaje = noticiasDao.devuelvePorId(idnoticia).getMensaje();
		Noticia noticia = null;
		boolean noticiaOK = false;
		if (listaNoticias != null) {
			if (listaNoticias.size() == 1 && mensaje == null) {
				 noticia = listaNoticias.get(0);
				System.out.println(noticia.getCategoria());
				Categoria categoria = noticia.getCategoria();
				
				model = prepWeb.envia(model);

				model.addAttribute("noticia", noticia);
				model.addAttribute("categoria", categoria);
				model.addAttribute("accion", "EDICIÓN");
				model.addAttribute("destino", "/gestion/noticias/procesaEditar/"+origen);
				noticiaOK = true;
				
				
			}
		}
/*
		if (noticiaOK) {
			model = prepWeb.envia(model);
		}*/

		model.addAttribute("mensaje", mensaje);

		return "noticias/formNoticia";

	}
	
	
	/*
	 * ================================ MÉTODOS PROCESANUEVO/PROCESAEDITAR ================================= 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y el objeto noticia
	 * 
	 * (Si es nuevo, lo ponemos activo forzosamente)
	 * 
	 * 2. Invocamos a insertarnoticia/editarnoticia del noticiasDao, guardando el mensaje
	 * 
	 * 3. Almacenamos en el model el mensaje y la futura url de destino
	 * 
	 * 4. Hacemos forward a noticias (activos, destacadas, por X categoria)
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/procesaCreate/{url}")
	public String procesarFormulario(RedirectAttributes miRedirAttrib, @PathVariable(name = "url") String destino, Noticia noticia) {

		System.out.println(noticia);

		
		String mensaje = noticiasDao.insertarNoticia(noticia);

		System.out.println(noticia);
		
		System.out.println(mensaje);

		/////////////////////////////// IMPORTANTE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/noticias/"+destino);

		return "redirect:/gestion/noticias/"+destino;

	}
	

	@GetMapping("/procesaEditar/{url}")
	public String procesarEditarnoticia(RedirectAttributes miRedirAttrib,  @PathVariable(name = "url") String destino, Noticia noticia) {
		
		String mensaje = null;

		System.out.println(noticia);

		mensaje = noticiasDao.editarNoticia(noticia);

		System.out.println(noticia);

		System.out.println(mensaje);

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/noticias/"+destino);

		System.out.println(noticia);

		return "redirect:/gestion/noticias/"+destino;

	}





}