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

public class NoticiasPublicController {

	
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
	 * de devolver la lista con los categorias. 
	 * 
	 * Al ser el public controller, filtraremos los categorias por aquellos que tienen noticias activos,
	 * el resto serán eliminados, aunque existan en la BD
	 * 
	 * =========================================================================================== 
	 */

	public Model mandaListaCategorias(Model model) {
		
		String mensaje = "";
		List<Categoria> listaCategorias = categoriasDao.devuelveCategorias("").getListaCategorias();
		String mensajeCategorias = categoriasDao.devuelveCategorias("").getMensaje();

		if (listaCategorias != null) {
			if (listaCategorias.size() == 0 && mensajeCategorias == null) {
				mensajeCategorias = "No se han encontrado categorias en la Base de Datos";
			}
		}

		if (mensajeCategorias != null) {
			mensaje = "Error con los categorias de noticias: " + mensajeCategorias;
		}
		System.out.println(listaCategorias);
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

	
	@GetMapping("")
	public String verActivos(Model model) {

		List<Noticia> listaNoticias = noticiasDao.devuelveTodos().getListaNoticias();
		String mensaje = noticiasDao.devuelveTodos().getMensaje();
		
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

		return "noticias/buyNoticias";

	}
	
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

		model = mandaListaCategorias(model);

		if (listaNoticias != null) {
			if (listaNoticias.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado noticias destacadas en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", "destacadas");
		model.addAttribute("origen","/destacadas");


		return "noticias/buyNoticias";

	}
	
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Noticia> listaNoticias = null;
		String mensaje = "";
		String categoria = "";
		if(palabra != null) {
			listaNoticias = noticiasDao.devuelveByPalabra(palabra).getListaNoticias();
			mensaje = noticiasDao.devuelveByPalabra(palabra).getMensaje();
			if(listaNoticias == null || listaNoticias.size() == 0 ) {
				mensaje = "No hay noticias que contengan la palabra "+palabra;
			}
			categoria = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		
		model.addAttribute("mensajeError", mensaje);
		
		
		model.addAttribute("miListaNoticias", listaNoticias);
		model.addAttribute("categoria", categoria);
		model.addAttribute("origen","/contiene/"+palabra);
		
		return "noticias/buyNoticias";
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
	 * 4. Una vez realizadas estas comprobaciones específicas, podemos invocar al método auxiliar
	 * utilizado anteriormente
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/categoria/{idCategoria}")
	public String verPorCategoria(Model model, @PathVariable(name = "idCategoria") int idCategoria) {

		List<Noticia> listaNoticias = noticiasDao.devuelveByCategoria(idCategoria).getListaNoticias();
		String mensaje = noticiasDao.devuelveByCategoria(idCategoria).getMensaje();		
		
		model = mandaListaCategorias(model);

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
		model.addAttribute("mensajeError", mensaje);
		model.addAttribute("origen","/categoria/{"+idCategoria+"}");
		
		return "noticias/buyNoticias";
		
	}
	
	@GetMapping("/detalle/{id}")
	public String verPorId(Model model, @PathVariable(name = "id") int idNoticia) {

		List<Noticia> listaNoticias = noticiasDao.devuelvePorId(idNoticia).getListaNoticias();
		String mensaje = noticiasDao.devuelvePorId(idNoticia).getMensaje();
		System.out.println(listaNoticias);
		System.out.println(mensaje);
		model = mandaListaCategorias(model);
		
		List<Noticia> listaDestacadas = noticiasDao.devuelveByDestacada("s").getListaNoticias();
		for(int i=listaDestacadas.size() - 1; i > 1; i--) {

			listaDestacadas.remove(i);
			
		}
		model.addAttribute("listaNoticias", listaDestacadas);
		
		Noticia miNoticia = null;
		if (mensaje == null) {
			if(listaNoticias.size() >0) {
				miNoticia = listaNoticias.get(0);

			}else {
				mensaje = "Error --> No se ha encontrado ningún noticia activo con ID "+idNoticia;
			}
			System.out.println(miNoticia);

		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("noticia", miNoticia);


		return "detalleNoticia";

	}
	

	



}