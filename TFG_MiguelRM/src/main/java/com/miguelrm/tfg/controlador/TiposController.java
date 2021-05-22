package com.miguelrm.tfg.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;

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
@RequestMapping("/tipos")
public class TiposController {
	
	//El TiposController es análogo al EventosController, siendo mucho más simple
	//no precisando del eventosDao, y teniendo que realizar menos acciones, al trabajar
	//en esencia con los tipos, que son auxiliares permanentemente en EventosController
	@Autowired
	IntTiposDao tiposDao;

	//Simplemente ejecutar el método mandaListaTipos
	@GetMapping("/todos")
	public String verTodos(Model model) {

		model = mandaListaTipos(model);
		
		return "listaTipos";
		 
	}
	
	//Metodo ya visto
	public Model mandaListaTipos(Model model) {
		String mensaje = "";
		
		List<Tipo> listaTipos = tiposDao.devuelveTipos().getListaTipos();
		String mensajeTipos = tiposDao.devuelveTipos().getMensaje();

		if (listaTipos != null) {
			if (listaTipos.size() == 0 && mensajeTipos == null) {
				mensajeTipos = "No se han encontrado tipos en la Base de Datos";
			}
		}


		model.addAttribute("listaTipos", listaTipos);
		model.addAttribute("mensajeError",mensaje);
		
		return model;
		
	}
	
	
	//Métodos con funcionamiento análogo al anteriormente descrito en Eventos, pero
	//ejecutando métodos del dao de tipos
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable(name="id") int  idTipo) {
		String mensaje = "";
		try {
			mensaje = tiposDao.borrarTipo(idTipo);
		}catch(Exception e){
			mensaje = "Fallo desconocido al eliminar el tipo";
			e.printStackTrace();
		}
				
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("urlDestino","/tipos/todos");
		

		return "forward:/tipos/todos";
		 
	}
	
	@GetMapping("/create")
	public String goToNuevo(Model model) {
		
		model.addAttribute("accion","CREACIÓN");
		model.addAttribute("destino","/tipos/procesaCreate");
		
		return "formTipo";	 
		
	}
	
	@GetMapping("/procesaCreate")
	public String procesarFormulario(Model model, Tipo tipo ) {
		
		System.out.println(tipo);
		
		String mensaje = tiposDao.insertarTipo(tipo);
		
		System.out.println(tipo);
		
		System.out.println(mensaje);
		
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("urlDestino","/tipos/todos");

		
		System.out.println(tipo);
	 
		
	 	return "forward:/tipos/todos";
		 	
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
				model.addAttribute("destino","/tipos/procesaEditar");
			}
		}
		

		model.addAttribute("mensaje", mensaje);

		return "formTipo";	 
		 
	}
	
	@GetMapping("/procesaEditar")
	public String procesarEditarTipo(Model model, Tipo tipo ) {
		
		
		System.out.println(tipo);
				
		String mensaje = tiposDao.editarTipo(tipo);
		
		System.out.println(tipo);
		
		
		System.out.println(mensaje);
		
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("urlDestino","/tipos/todos");

		
		System.out.println(tipo);
	 
		
	 	return "forward:/tipos/todos";
		 
		
	}
	
	
	
}