package com.miguelrm.tfg.controlREST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.beans.TipoREST;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;

/* ================================================== CONTROLADOR DE TIPOS ================================================== 
 * 
 * Se trata del controlador "secundario", ya que cuenta con muchas menos funciones que su hermano de eventos
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@RestController
//Anotamos que vamos a entrar por /clientes
@RequestMapping("/tiposREST")
public class TiposControllerREST {

	// El TiposController es análogo al EventosController, siendo mucho más simple
	// no precisando del eventosDao, y teniendo que realizar menos acciones, al
	// trabajar
	// en esencia con los tipos, que son auxiliares permanentemente en
	// EventosController
	@Autowired
	IntTiposDao tiposDao;

	private String generaRespuesta(List<Tipo> listaTipos, String mensaje, String tipoPedido) {
		
		if(mensaje != null) {
			return mensaje;
		}else {
			String rdo = "";
			if(listaTipos.size() == 0) {
				rdo = "Advertencia: No se han encontrado tipos "+tipoPedido+" en este momento";
			}else {
				rdo = "========================================== LISTA DE TIPOS "+tipoPedido.toUpperCase()+" ========================================== \n\n\n";
				if(listaTipos.size() == 1) {
					rdo+= listaTipos.get(0).toString()+"\n\n";
				}else {
					for(int i = 0; i < listaTipos.size(); i++) {
						int indice = i;
						indice = indice+1;
						
						rdo+= indice+". "+listaTipos.get(i).toString()+"\n\n";
					}
				}
				rdo+="========================================== FIN ==========================================";
			}

			
			return rdo;
		}
		
	}
	
	@GetMapping("")
	public String verTodos(Model model) {

		List<Tipo> listaTipos = tiposDao.devuelveTipos().getListaTipos();
		String mensajeTipos = tiposDao.devuelveTipos().getMensaje();
		
		
		return generaRespuesta(listaTipos,mensajeTipos,"");
	}
	
	/*
	 * =================================== MÉTODO VERPORPALABRA =================================== 
	 * 
	 * Procedimiento análogo a los anteriores, sólo que invocando otro método del tiposDao,
	 * recibiendo palabra a buscar, e invocando al método correspondiente
	 * 
	 * =========================================================================================== 
	 */

	
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Tipo> listaTipos = null;
		String mensaje = "";
		String tipo = "";
		if(palabra != null) {
			listaTipos = tiposDao.devuelveByPalabra(palabra).getListaTipos();
			mensaje = tiposDao.devuelveByPalabra(palabra).getMensaje();
			tipo = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		return generaRespuesta(listaTipos,mensaje,tipo);
		
	}
	
	@GetMapping(value="/{id}")
	public TipoREST devuelveJSON (Model model, @PathVariable(name = "id") int idTipo) {

		List<Tipo> listaTipos = tiposDao.devuelvePorId(idTipo).getListaTipos();
		//String mensaje = tiposDao.devuelvePorId(idTipo).getMensaje();

		Tipo miTipoFull = listaTipos.get(0);
		miTipoFull.setEventos(null);
		
		System.out.println(miTipoFull);
		
		TipoREST miTipo = new TipoREST(miTipoFull);
		
		return miTipo;


	}
	
	@GetMapping("/{id}/texto")
	public String devuelve(Model model, @PathVariable(name = "id") int idTipo) {

		List<Tipo> listaTipos = tiposDao.devuelvePorId(idTipo).getListaTipos();
		String mensaje = tiposDao.devuelvePorId(idTipo).getMensaje();

		return generaRespuesta(listaTipos,mensaje,"");


	}
	

	@PostMapping("")
	public String procesaAlta(@RequestBody Tipo tipo) {

		System.out.println(tipo);
		
		String mensaje = tiposDao.insertarTipo(tipo);

		System.out.println(tipo);

		System.out.println(mensaje);

		System.out.println(tipo);

		return mensaje;

	}
	
	@PutMapping("")
	public String procesaEditar(Model model, @RequestBody Tipo tipo) {

		System.out.println(tipo);

		String mensaje = tiposDao.editarTipo(tipo);

		System.out.println(tipo);

		System.out.println(mensaje);
		
		return mensaje;

	}

	// Métodos con funcionamiento análogo al anteriormente descrito en Eventos, pero
	// ejecutando métodos del dao de tipos
	@DeleteMapping(value={"/{id}","/{id}/{forceDelete}"})
	public String eliminar(Model model, @PathVariable(name = "id") int idTipo, @PathVariable(name="forceDelete", required = false) String forceDelete) {
		String mensaje = "";
		boolean doForceDelete;
		try {
			if(forceDelete == null) {
				doForceDelete = false;
				mensaje = tiposDao.borrarTipo(idTipo,doForceDelete);
			}else if(forceDelete.equals("forceDelete")){
				doForceDelete = true;
				mensaje = tiposDao.borrarTipo(idTipo,doForceDelete);
			}else {
				mensaje = "Opción no soportada. Si quiere forzar eliminación de eventos haga /idTipo/forceDelete";
			}
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar el tipo";
			e.printStackTrace();
		}

		return mensaje;

	}



}