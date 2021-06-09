package com.miguelrm.tfg.controlador;

import java.util.List;

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

/* ================================================== CONTROLADOR DE TIPOS ================================================== 
 * 
 * Se trata del controlador "secundario", ya que cuenta con muchas menos funciones que su hermano de noticias
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller
//Anotamos que vamos a entrar por /clientes
@RequestMapping("/gestion/categorias")
public class CategoriasController {
	
	//El CategoriasController es análogo al NoticiasController, siendo mucho más simple
	//no precisando del noticiasDao, y teniendo que realizar menos acciones, al trabajar
	//en esencia con los categorias, que son auxiliares permanentemente en NoticiasController
	@Autowired
	IntCategoriasDao categoriasDao;
	
	@Autowired
	IntNoticiasDao noticiasDao;
	
	@Autowired
	IntPreparaServ prepWeb;


	//Simplemente ejecutar el método prepWeb.envia
	@GetMapping("")
	public String verTodos(Model model) {

		model = prepWeb.envia(model);
		
		return "noticias/gestionCategorias";
		 
	}
	
	
	

	
	//Métodos con funcionamiento análogo al anteriormente descrito en Noticias, pero
	//ejecutando métodos del dao de categorias
	@GetMapping("/eliminar/{id}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name="id") int  idCategoria) {
		String mensaje = "";
		try {
			mensaje = categoriasDao.borrarCategoria(idCategoria,false);
		}catch(Exception e){
			mensaje = "Fallo desconocido al eliminar el categoria";
			e.printStackTrace();
		}
				
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino","/gestion/categorias");
		

		return "redirect:/gestion/categorias";
		 
	}
	
	@GetMapping("/create")
	public String goToNuevo(Model model) {
		
		model.addAttribute("accion","CREACIÓN");
		model.addAttribute("destino","/gestion/categorias/procesaCreate");
		
		return "formCategoria";	 
		
	}
	
	@PostMapping("/procesaCreate")
	public String procesarFormulario(RedirectAttributes miRedirAttrib, Categoria categoria ) {
		
		System.out.println(categoria);
		
		String mensaje = categoriasDao.insertarCategoria(categoria);
		
		System.out.println(categoria);
		
		System.out.println(mensaje);
		
		miRedirAttrib.addFlashAttribute("mensaje",mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino","/gestion/categorias");

		
		System.out.println(categoria);
	 
		
	 	return "redirect:/gestion/categorias";
		 	
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model model, @PathVariable(name="id") int  idCategoria) {
		
		List<Categoria> listaCategorias = categoriasDao.devuelvePorId(idCategoria).getListaCategorias();
		String mensaje = categoriasDao.devuelvePorId(idCategoria).getMensaje();

		if(listaCategorias != null) {
			if( listaCategorias.size() == 1 && mensaje == null) {
				Categoria categoria = listaCategorias.get(0);
				model.addAttribute("categoria",categoria);
				model.addAttribute("accion","EDICIÓN");
				model.addAttribute("destino","/gestion/categorias/procesaEditar");
			}
		}
		

		model.addAttribute("mensaje", mensaje);

		return "noticias/formCategoria";	 
		 
	}
	
	@PostMapping("/procesaEditar")
	public String procesarEditarCategoria(RedirectAttributes miRedirAttrib, Categoria categoria ) {
		
		
		System.out.println(categoria);
				
		String mensaje = categoriasDao.editarCategoria(categoria);
		
		System.out.println(categoria);
		
		
		System.out.println(mensaje);
		
		miRedirAttrib.addFlashAttribute("mensaje",mensaje);
		//miRedirAttrib.addFlashAttribute("urlDestino","/gestion/categorias");

		
		System.out.println(categoria);
	 
		
	 	return "redirect:/gestion/categorias";
		 
		
	}
	
	
	
}

