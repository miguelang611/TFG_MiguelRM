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

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;
import com.miguelrm.tfg.servicios.PreparaServImpl;

/* ================================================== CONTROLADOR DE TIPOS ================================================== 
 * 
 * Se trata del controlador "secundario", ya que cuenta con muchas menos funciones que su hermano de eventos
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller
//Anotamos que vamos a entrar por /clientes
@RequestMapping("/gestion/tipos")
public class TiposController {
	
	//El TiposController es análogo al EventosController, siendo mucho más simple
	//no precisando del eventosDao, y teniendo que realizar menos acciones, al trabajar
	//en esencia con los tipos, que son auxiliares permanentemente en EventosController
	@Autowired
	IntTiposDao tiposDao;
	
	@Autowired
	IntEventosDao eventosDao;
	
	@Autowired
	IntPreparaServ prepWeb;

	//Simplemente ejecutar el método prepWeb.envia
	@GetMapping("")
	public String verTodos(Model model) {

		model = prepWeb.envia(model);
		
		return "gestionTipos";
		 
	}
	
	
	//Métodos con funcionamiento análogo al anteriormente descrito en Eventos, pero
	//ejecutando métodos del dao de tipos
	@GetMapping("/eliminar/{id}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name="id") int  idTipo) {
		String mensaje = "";
		try {
			mensaje = tiposDao.borrarTipo(idTipo,false);
		}catch(Exception e){
			mensaje = "Fallo desconocido al eliminar el tipo";
			e.printStackTrace();
		}
				
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino","/gestion/tipos");
		

		return "redirect:/gestion/tipos";
		 
	}
	
	@GetMapping("/create")
	public String goToNuevo(Model model) {
		
		model.addAttribute("accion","CREACIÓN");
		model.addAttribute("destino","/gestion/tipos/procesaCreate");
		
		return "formTipo";	 
		
	}
	
	@PostMapping("/procesaCreate")
	public String procesarFormulario(RedirectAttributes miRedirAttrib, Tipo tipo ) {
		
		System.out.println(tipo);
		
		String mensaje = tiposDao.insertarTipo(tipo);
		
		System.out.println(tipo);
		
		System.out.println(mensaje);
		
		miRedirAttrib.addFlashAttribute("mensaje",mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino","/gestion/tipos");

		
		System.out.println(tipo);
	 
		
	 	return "redirect:/gestion/tipos";
		 	
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model model, @PathVariable(name="id") int  idTipo) {
		
		List<Tipo> listaTipos = tiposDao.devuelvePorId(idTipo).getListaTipos();
		String mensaje = tiposDao.devuelvePorId(idTipo).getMensaje();

		if(listaTipos != null) {
			if( listaTipos.size() == 1 && mensaje == null) {
				Tipo tipo = listaTipos.get(0);
				model.addAttribute("tipo",tipo);
				model.addAttribute("accion","EDICIÓN");
				model.addAttribute("destino","/gestion/tipos/procesaEditar");
			}
		}
		

		model.addAttribute("mensaje", mensaje);

		return "formTipo";	 
		 
	}
	
	@PostMapping("/procesaEditar")
	public String procesarEditarTipo(RedirectAttributes miRedirAttrib, Tipo tipo ) {
		
		
		System.out.println(tipo);
				
		String mensaje = tiposDao.editarTipo(tipo);
		
		System.out.println(tipo);
		
		
		System.out.println(mensaje);
		
		miRedirAttrib.addFlashAttribute("mensaje",mensaje);
		//miRedirAttrib.addFlashAttribute("urlDestino","/gestion/tipos");

		
		System.out.println(tipo);
	 
		
	 	return "redirect:/gestion/tipos";
		 
		
	}
	
	
	
}