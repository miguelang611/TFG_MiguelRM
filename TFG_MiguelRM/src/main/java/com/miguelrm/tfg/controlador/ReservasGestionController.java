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

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.dao.IntReservasDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;
import com.miguelrm.tfg.servicios.PreparaServImpl;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;


/* ================================================== CONTROLADOR DE EVENTOS ================================================== 
 * 
 * Se trata del controlador "principal", ya que cuenta con más funciones que su hermano de eventos
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller

//Anotamos que vamos a entrar por /gestion/reservas
@RequestMapping("/gestion/reservas")

public class ReservasGestionController {

	
	//Tenemos reservasDao y eventosDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntReservasDao reservasDao;

	@Autowired
	IntEventosDao eventosDao;
	
	@Autowired
	IntPreparaServ prepWeb;

	 

	
	/*
	 * =================================== MÉTODO VERTODOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/todas")
	public String verTodos(Model model) {

		List<Reserva> listaReservas = reservasDao.devuelveTodos().getListaReservas();
		String mensaje = reservasDao.devuelveTodos().getMensaje();

		model = prepWeb.envia(model);

		if (listaReservas != null) {
			if (listaReservas.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado reservas en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaReservas", listaReservas);
		model.addAttribute("evento", "(todas)");
		model.addAttribute("origen","/todas");

		return"/reservas/gestionReservas";
	}
	

	


	/*
	 * =================================== MÉTODO VERPORTIPO ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método devuelvePorEvento() del reservasDao, pasando el id del evento,
	 * y almacenamos la lista de reservas y el mensaje
	 * 
	 * 2. Si la lista de reservas no es nula y el mensaje es nulo, tenemos conexión a la BD -->
	 * Recuperamos la lista de eventos
	 * 
	 * 3. Si la lista de reservas tiene tamaño 0, y la recuperación de lista de eventos es correcta,
	 * porque el array tiene 1 evento --> el evento existe, pero no hay reservas
	 * 
	 * Si el array está vacío, es que no existe ningún evento con ese id
	 * 
	 * (Si viene con mensaje ya sabemos que habrá fallado la conexión a BD)
	 * 
	 * 4. Agregamos el nombre del evento para indicarlo en el jsp (si lo anterior se ha verificado)
	 * 
	 * 5. Agregamos al model el mensaje, la lista de reservas, y el evento de lista
	 * que estamos mandando, para que se muestre correctamente en el jsp de listaReservas
	 * y el origen, de cara al uso de otras funciones
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/{idEvento}")
	public String verPorEvento(Model model, @PathVariable(name = "idEvento") int idEvento) {

		List<Reserva> listaReservas = reservasDao.devuelveByEvento(idEvento).getListaReservas();
		String mensaje = reservasDao.devuelveByEvento(idEvento).getMensaje();

		model = prepWeb.envia(model);

		String nombreEvento = "";
		Evento evento = null;
		if (listaReservas != null && mensaje == null) {
			//Si la lista no es nul, pero está vacíay el mensaje es nulo, la conexión a la BBDD es correcta,
			//así que vertificaremos si el evento solicitado existe
			
			List<Evento> listaEventos = eventosDao.devuelvePorId(idEvento).getListaEventos();
			String mensajeEvento = eventosDao.devuelvePorId(idEvento).getMensaje();
			
			if(listaReservas.size() == 0) {
				if (listaEventos != null && mensajeEvento == null) {
					System.out.println(listaEventos);
					//El evento existe, simplemente no hay reservas de ese evento
					if (listaEventos.size() == 1) {
						evento = listaEventos.get(0);
						mensaje = "No se han encontrado reservas del evento " + evento.getNombre();
					//El evento no existe, por tanto es imposible que haya reservas de ese evento
					} else {
						mensaje = "No se han encontrado reservas del evento " + idEvento + ", ya que no existe dicho evento";
					}
				}else {
					mensaje = "Error decsconocido de eventos";
				}
			}else {
				nombreEvento = listaEventos.get(0).getNombre();
			}
			
			
		}
		
		if (mensaje == null) {
			model.addAttribute("evento","del evento "+nombreEvento);
		}else {
			model.addAttribute("mensajeError", mensaje);
		}
		

		model.addAttribute("miListaReservas", listaReservas);

		model.addAttribute("mensajeError",mensaje);
		
		model.addAttribute("origen","/"+idEvento);

		
		return"/reservas/gestionReservas";
		
	}
	/*
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Reserva> listaReservas = null;
		String mensaje = "";
		String evento = "";
		if(palabra != null) {
			listaReservas = reservasDao.devuelveByPalabra(palabra).getListaReservas();
			mensaje = reservasDao.devuelveByPalabra(palabra).getMensaje();
			evento = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaReservas", listaReservas);
		model.addAttribute("evento", evento);
		model.addAttribute("origen","/contiene/"+palabra);
		
		return"/reservas/gestionReservas";
	}
		
	
	/////////////////////////////////// BLOQUE 2 --> BOTONES EN LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * =================================== MÉTODOS CANCELAR Y EDITAR ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el id por PathVariable
	 * 
	 * 1. Llamamos al método correspondiente del reservasDao, pasando el id del evento,
	 * y almacenamos la lista de reservas y el mensaje
	 * 
	 * 
	 * 2. Agregamos al model el mensaje, indicamos url de destino (al llegar al jsp, cambiará la url
	 * a mostrar aunque usemos forward), y hacemos forward a reservas (activos, destacadas, etc)
	 * 
	 * ================================================================================================= 
	 */
	

	
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
	 * EXTRA --> En el caso de edición, recuperaremos antes el reserva correspondiente invocando
	 * el método devuelvePorId del reservasDao
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/create/{url}")
	public String goToNuevo(Model model, @PathVariable(name = "url") String origen) {

		
		model = prepWeb.envia(model);
		model.addAttribute("accion", "CREACIÓN");
		model.addAttribute("destino", "/gestion/reservas/procesaCreate/"+origen);

		return "reservas/formReserva";

	}
	
	@GetMapping("/editar/{id}/{url}")
	public String editar(Model model, @PathVariable(name = "id") int idreserva, @PathVariable(name = "url") String origen) {

		List<Reserva> listaReservas = reservasDao.devuelvePorId(idreserva).getListaReservas();
		String mensaje = reservasDao.devuelvePorId(idreserva).getMensaje();
		Reserva reserva = null;
		boolean reservaOK = false;
		if (listaReservas != null) {
			if (listaReservas.size() == 1 && mensaje == null) {
				 reserva = listaReservas.get(0);
				System.out.println(reserva.getEvento());
				Evento evento = reserva.getEvento();
				
				model = prepWeb.envia(model);

				model.addAttribute("reserva", reserva);
				model.addAttribute("evento", evento);
				model.addAttribute("accion", "EDICIÓN");
				model.addAttribute("destino", "/gestion/reservas/procesaEditar/"+origen);
				reservaOK = true;
				
				
			}
		}
/*
		if (reservaOK) {
			model = prepWeb.envia(model);
		}*/

		model.addAttribute("mensaje", mensaje);

		return "reservas/formReserva";

	}
	
	
	/*
	 * ================================ MÉTODOS PROCESANUEVO/PROCESAEDITAR ================================= 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y el objeto reserva
	 * 
	 * (Si es nuevo, lo ponemos activo forzosamente)
	 * 
	 * 2. Invocamos a insertarreserva/editarreserva del reservasDao, guardando el mensaje
	 * 
	 * 3. Almacenamos en el model el mensaje y la futura url de destino
	 * 
	 * 4. Hacemos forward a reservas (activos, destacadas, por X evento)
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/procesaCreate/{url}")
	public String procesarFormulario(RedirectAttributes miRedirAttrib, @PathVariable(name = "url") String destino, Reserva reserva) {

		System.out.println(reserva);

		
		String mensaje = reservasDao.insertarReserva(reserva);

		System.out.println(reserva);
		
		System.out.println(mensaje);

		/////////////////////////////// IMPORTANTE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/reservas/"+destino);

		return "redirect:/gestion/reservas/"+destino;

	}
	

	@GetMapping("/procesaEditar/{url}")
	public String procesarEditarreserva(RedirectAttributes miRedirAttrib,  @PathVariable(name = "url") String destino, Reserva reserva) {
		
		String mensaje = null;

		System.out.println(reserva);

		mensaje = reservasDao.editarReserva(reserva);

		System.out.println(reserva);

		System.out.println(mensaje);

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);
		miRedirAttrib.addFlashAttribute("urlDestino", "/gestion/reservas/"+destino);

		System.out.println(reserva);

		return "redirect:/gestion/reservas/"+destino;

	}


}