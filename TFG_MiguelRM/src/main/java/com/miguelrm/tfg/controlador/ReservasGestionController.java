package com.miguelrm.tfg.controlador;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.Usuario;
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

//Anotamos que vamos a entrar por /cliente/reservas
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
		model.addAttribute("tipo", "Todas las reservas");
		model.addAttribute("origen","/todas");

		return"/reservas/gestionReservas";
	}
	
	@GetMapping("/evento/{idEvento}")
	public String verTodos(Model model, @PathVariable(name = "idEvento") int idEvento) {
		
		
		List<Reserva> listaReservas = reservasDao.devuelveByEvento(idEvento).getListaReservas();
		String mensaje = reservasDao.devuelveByEvento(idEvento).getMensaje();

		String tipo = "Reservas del evento "+idEvento;
		model = prepWeb.envia(model);

		if (listaReservas != null) {
			if (listaReservas.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado reservas en este momento";
			}else {
				tipo = "Reservas del evento '"+listaReservas.get(0).getEvento().getNombre()+"'";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaReservas", listaReservas);
		model.addAttribute("tipo", tipo);
		model.addAttribute("origen","/evento/"+idEvento);

		return"/reservas/gestionReservas";
	}
	
	//NO IMPLEMENTADO EN VISTAS
	@GetMapping("/usuario/{email}")
	public String verTodos(Model model, @PathVariable(name = "email") String email) {
		
		
		List<Reserva> listaReservas = reservasDao.devuelveByEmail(email).getListaReservas();
		String mensaje = reservasDao.devuelveByEmail(email).getMensaje();

		model = prepWeb.envia(model);

		if (listaReservas != null) {
			if (listaReservas.size() == 0 && mensaje == null) {
				mensaje = "No se han encontrado reservas en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaReservas", listaReservas);
		model.addAttribute("tipo", "(email)");
		model.addAttribute("origen","/evento/"+email);

		return"/reservas/gestionReservas";
	}
	
	
	@GetMapping("/eliminar/{id}/{url}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idReserva, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		try {
			mensaje = reservasDao.borrarReserva(idReserva);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar la reserva";
			e.printStackTrace();
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);

		return "redirect:/cliente/reservas/"+destino;


	}

}