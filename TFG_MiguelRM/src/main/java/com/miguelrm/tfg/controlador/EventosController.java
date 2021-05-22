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

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;


/* ================================================== CONTROLADOR DE EVENTOS ================================================== 
 * 
 * Se trata del controlador "principal", ya que cuenta con más funciones que su hermano de tipos
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller

//Anotamos que vamos a entrar por /eventos
@RequestMapping("/eventos")

public class EventosController {

	
	//Tenemos eventosDao y tiposDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntEventosDao eventosDao;

	@Autowired
	IntTiposDao tiposDao;

	/*
	 * ============================= MÉTODO AUXILIAR MANDALISTATIPOS ============================= 
	 * 
	 * Tenemos un método auxiliar que llamaremos desde los métodos principales que se encarga
	 * de devolver la lista con los tipos. Esto es necesario para toda la aplicación:
	 *
	 * En lista: porque en el nav tenemos eventos por tipo
	 * En creación y edición: además de por eso, porque usaremos la lista para un desplegable
	 * 
	 * =========================================================================================== 
	 */

	public Model mandaListaTipos(Model model) {
		String mensaje = "";
		List<Tipo> listaTipos = tiposDao.devuelveTipos().getListaTipos();
		String mensajeTipos = tiposDao.devuelveTipos().getMensaje();

		if (listaTipos != null) {
			if (listaTipos.size() == 0 && mensajeTipos == null) {
				mensajeTipos = "No se han encontrado tipos en la Base de Datos";
			}
		}

		if (mensajeTipos != null) {
			mensaje = "Error con los tipos de eventos: " + mensajeTipos;
		}
		model.addAttribute("listaTipos", listaTipos);
		model.addAttribute("mensajeError",mensaje);
		
		return model;
	}
	 
	/////////////////////////////////////// BLOQUE 1 --> LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * ==================================== MÉTODO VERACTIVOS ===================================== 
	 * 
	 * 0. Entramos por GET, y solo tenemos de entrada el Model
	 * 
	 * 1. Llamamos al método devuelveActivos() del eventosDao, y almacenamos la lista de eventos
	 * y el mensaje --> tenemos un Beans para devolver ambas cosas.
	 * 
	 * 2. Si la lista no es nula, el mensaje es nulo, y el tamaño es 0 --> la conexión y todo es
	 * correcto, sólo que no hay eventos activos
	 * 
	 * 3. Si el mensaje es nulo, es decir, es todo correcto, llamamos a mandaListaTipos, para que
	 * nos traiga la lista de tipos en el model y luego poder usarla en el nav
	 * 
	 * 4. Agregamos al model el mensaje, la lista de eventos, el tipo de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaEventos,
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */

	
	@GetMapping("/activos")
	public String verActivos(Model model) {

		List<Evento> listaEventos = eventosDao.devuelveByEstado("activo").getListaEventos();
		String mensaje = eventosDao.devuelveByEstado("activo").getMensaje();
		
		model = mandaListaTipos(model);

		if (listaEventos != null) {
			if (listaEventos.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado eventos activos en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaEventos", listaEventos);
		model.addAttribute("tipo", "activos");
		model.addAttribute("origen","/activos");

		return "listaEventos";

	}
	
	@GetMapping("/cancelados")
	public String verCancelados(Model model) {

		List<Evento> listaEventos = eventosDao.devuelveByEstado("cancelado").getListaEventos();
		String mensaje = eventosDao.devuelveByEstado("cancelado").getMensaje();
		
		model = mandaListaTipos(model);

		if (listaEventos != null) {
			if (listaEventos.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado eventos cancelados en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaEventos", listaEventos);
		model.addAttribute("tipo", "cancelados");
		model.addAttribute("origen","/cancelados");

		return "listaEventos";

	}
	
	/*
	 * =================================== MÉTODO VERTODOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/todos")
	public String verTodos(Model model) {

		List<Evento> listaEventos = eventosDao.devuelveTodos().getListaEventos();
		String mensaje = eventosDao.devuelveTodos().getMensaje();

		model = mandaListaTipos(model);

		if (listaEventos != null) {
			if (listaEventos.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado eventos en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaEventos", listaEventos);
		model.addAttribute("tipo", "(todos)");
		model.addAttribute("origen","/todos");

		return "listaEventos";
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

		List<Evento> listaEventos = eventosDao.devuelveByDestacado("s").getListaEventos();
		String mensaje = eventosDao.devuelveByDestacado("s").getMensaje();

		model = mandaListaTipos(model);

		if (listaEventos != null) {
			if (listaEventos.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado eventos destacados en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaEventos", listaEventos);
		model.addAttribute("tipo", "destacados");
		model.addAttribute("origen","/destacados");


		return "listaEventos";

	}
	
	@GetMapping("/nodestacados")
	public String verNoDestacados(Model model) {

		List<Evento> listaEventos = eventosDao.devuelveByDestacado("").getListaEventos();
		String mensaje = eventosDao.devuelveByDestacado("").getMensaje();

		model = mandaListaTipos(model);

		if (listaEventos != null) {
			if (listaEventos.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado eventos sin destacar en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaEventos", listaEventos);
		model.addAttribute("tipo", "no destacados");
		model.addAttribute("origen","/nodestacados");


		return "listaEventos";

	}

	/*
	 * =================================== MÉTODO VERPORTIPO ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método devuelvePorTipo() del eventosDao, pasando el id del tipo,
	 * y almacenamos la lista de eventos y el mensaje
	 * 
	 * 2. Si la lista de eventos no es nula y el mensaje es nulo, tenemos conexión a la BD -->
	 * Recuperamos la lista de tipos
	 * 
	 * 3. Si la lista de eventos tiene tamaño 0, y la recuperación de lista de tipos es correcta,
	 * porque el array tiene 1 tipo --> el tipo existe, pero no hay eventos
	 * 
	 * Si el array está vacío, es que no existe ningún tipo con ese id
	 * 
	 * (Si viene con mensaje ya sabemos que habrá fallado la conexión a BD)
	 * 
	 * 4. Agregamos el nombre del tipo para indicarlo en el jsp (si lo anterior se ha verificado)
	 * 
	 * 5. Agregamos al model el mensaje, la lista de eventos, y el tipo de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaEventos
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/{idTipo}")
	public String verPorTipo(Model model, @PathVariable(name = "idTipo") int idTipo) {

		List<Evento> listaEventos = eventosDao.devuelveByTipo(idTipo).getListaEventos();
		String mensaje = eventosDao.devuelveByTipo(idTipo).getMensaje();

		model = mandaListaTipos(model);

		String nombreTipo = "";
		Tipo tipo = null;
		if (listaEventos != null && mensaje == null) {
			//Si la lista no es nul, pero está vacíay el mensaje es nulo, la conexión a la BBDD es correcta,
			//así que vertificaremos si el tipo solicitado existe
			
			List<Tipo> listaTipos = tiposDao.devuelvePorId(idTipo).getListaTipos();
			String mensajeTipo = tiposDao.devuelvePorId(idTipo).getMensaje();
			
			if(listaEventos.size() == 0) {
				if (listaTipos != null && mensajeTipo == null) {
					System.out.println(listaTipos);
					//El tipo existe, simplemente no hay eventos de ese tipo
					if (listaTipos.size() == 1) {
						tipo = listaTipos.get(0);
						mensaje = "No se han encontrado eventos del tipo " + tipo.getNombre();
					//El tipo no existe, por tanto es imposible que haya eventos de ese tipo
					} else {
						mensaje = "No se han encontrado eventos del tipo " + idTipo + ", ya que no existe dicho tipo";
					}
				}else {
					mensaje = "Error decsconocido de tipos";
				}
			}else {
				nombreTipo = listaTipos.get(0).getNombre();
			}
			
			
		}
		
		if (mensaje == null) {
			model.addAttribute("tipo","del tipo "+nombreTipo);
		}else {
			model.addAttribute("mensajeError", mensaje);
		}
		

		model.addAttribute("miListaEventos", listaEventos);

		model.addAttribute("mensajeError",mensaje);
		
		model.addAttribute("origen","/"+idTipo);

		
		return "listaEventos";
		
	}
		
	
	/////////////////////////////////// BLOQUE 2 --> BOTONES EN LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * =================================== MÉTODOS CANCELAR Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método correspondiente del eventosDao, pasando el id del tipo,
	 * y almacenamos la lista de eventos y el mensaje
	 * 
	 * 
	 * 2. Agregamos al model el mensaje, indicamos url de destino (al llegar al jsp, cambiará la url
	 * a mostrar aunque usemos forward), y hacemos forward a eventos (activos, destacados, etc)
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/estado/{id}/{url}/{estado}")
	public String cancelar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idEvento, @PathVariable(name = "url") String destino, @PathVariable(name="estado") String estado ) {
		String mensaje = "";
		if(estado.equals("activo") || estado.equals("cancelado")) {
			try {
				mensaje = eventosDao.modificarEvento(idEvento, estado, null);
			} catch (Exception e) {
				mensaje = "Fallo desconocido al estado del evento";
				e.printStackTrace();
			}
		}else {
			mensaje = "Sólo puede modificar el estado de un evento a 'activo' o a 'cancelado'";
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/eventos/"+destino);


		return "redirect:/eventos/"+destino;

	}
	
	@GetMapping("/destacar/{id}/{url}/{destacado}")
	public String destacar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idEvento, @PathVariable(name = "url") String destino, @PathVariable(name="destacado") String destacado) {
		String mensaje = "";
		
		if(destacado.equals("si") || destacado.equals("no")) {
			if(destacado.equals("si")) {
				destacado = "s";
			}else {
				destacado = "";
			}
			System.out.println(destacado);
			try {
				mensaje = eventosDao.modificarEvento(idEvento, null, destacado);
			} catch (Exception e) {
				mensaje = "Fallo desconocido al cambiar el destacado del evento";
				e.printStackTrace();
			}
		}else {
			mensaje = "Sólo se puede destacar un evento a 'si' o 'no'";
		}
		
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/eventos/"+destino);

		return "redirect:/eventos/"+destino;

	}

	@GetMapping("/eliminar/{id}/{url}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idEvento, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		try {
			mensaje = eventosDao.borrarEvento(idEvento);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar el evento";
			e.printStackTrace();
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/eventos/"+destino);


		return "redirect:/eventos/"+destino;


	}
	
	/////////////////////////////////// BLOQUE 3 --> A FORMULARIO  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * =================================== MÉTODOS NUEVO Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y en el caso de editar, y el id por PathVariable
	 * 
	 * 1. Llamamos al método mandaListaTipos
	 * 
	 * 2. Almacenamos acción, y el destino que procesará el resultado del formulario
	 * 
	 * 3. Mandamos al jsp
	 * 
	 * EXTRA --> En el caso de edición, recuperaremos antes el evento correspondiente invocando
	 * el método devuelvePorId del eventosDao
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/create/{url}")
	public String goToNuevo(Model model, @PathVariable(name = "url") String origen) {

		
		model = mandaListaTipos(model);
		model.addAttribute("accion", "CREACIÓN");
		model.addAttribute("destino", "/eventos/procesaCreate/"+origen);

		return "formEvento";

	}
	
	@GetMapping("/editar/{id}/{url}")
	public String editar(Model model, @PathVariable(name = "id") int idEvento, @PathVariable(name = "url") String origen) {

		List<Evento> listaEventos = eventosDao.devuelvePorId(idEvento).getListaEventos();
		String mensaje = eventosDao.devuelvePorId(idEvento).getMensaje();

		boolean eventoOK = false;
		if (listaEventos != null) {
			if (listaEventos.size() == 1 && mensaje == null) {
				Evento evento = listaEventos.get(0);
				System.out.println(evento.getTipo());
				Tipo tipo = evento.getTipo();
				
				model = mandaListaTipos(model);

				model.addAttribute("evento", evento);
				model.addAttribute("tipo", tipo);
				model.addAttribute("accion", "EDICIÓN");
				model.addAttribute("destino", "/eventos/procesaEditar/"+origen);
				eventoOK = true;
				
				
			}
		}
/*
		if (eventoOK) {
			model = mandaListaTipos(model);
		}*/

		model.addAttribute("mensaje", mensaje);

		return "formEvento";

	}
	
	
	/*
	 * ================================ MÉTODOS PROCESANUEVO/PROCESAEDITAR ================================= 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y el objeto Evento
	 * 
	 * (Si es nuevo, lo ponemos activo forzosamente)
	 * 
	 * 2. Invocamos a insertarEvento/editarEvento del eventosDao, guardando el mensaje
	 * 
	 * 3. Almacenamos en el model el mensaje y la futura url de destino
	 * 
	 * 4. Hacemos forward a eventos (activos, destacados, por X tipo)
	 * 
	 * ================================================================================================= 
	 */
	
	@PostMapping("/procesaCreate/{url}")
	public String procesarFormulario(RedirectAttributes miRedirAttrib, @PathVariable(name = "url") String destino, Evento evento) {

		System.out.println(evento);

		evento.setEstado("activo");

		String mensaje = eventosDao.insertarEvento(evento);

		System.out.println(evento);
		
		System.out.println(mensaje);

		/////////////////////////////// IMPORTANTE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/eventos/"+destino);

		return "redirect:/eventos/"+destino;

	}
	

	@GetMapping("/procesaEditar/{url}")
	public String procesarEditarEvento(RedirectAttributes miRedirAttrib,  @PathVariable(name = "url") String destino, Evento evento) {
		
		String mensaje = null;

		System.out.println(evento);

		mensaje = eventosDao.editarEvento(evento);

		System.out.println(evento);

		System.out.println(mensaje);

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/eventos/"+destino);

		System.out.println(evento);

		return "redirect:/eventos/"+destino;

	}





}
