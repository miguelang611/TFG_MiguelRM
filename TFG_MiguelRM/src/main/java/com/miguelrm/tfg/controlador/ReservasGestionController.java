package com.miguelrm.tfg.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.Usuario;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntReservasDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;


/* ================================================== CONTROLADOR DE EVENTOS ================================================== 
 * 
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
		model.addAttribute("origen","/gestion/reservas/");

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
		model.addAttribute("origen","/gestion/reservas/");

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
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idReserva, @PathVariable(name = "url") String destino) {
		String mensaje = "";
		boolean isAdmin = true;
		try {
			mensaje = reservasDao.borrarReserva(idReserva, isAdmin, null);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar la reserva";
			e.printStackTrace();
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);

		return "redirect:/gestion/reservas/todas";


	}
	
	@GetMapping("/add/{id}")
	public String editarAdd(Model model, @PathVariable(name = "id") int idreserva,  HttpSession miSesion, RedirectAttributes miRedirAtrib) {

		List<Reserva> listaReservas = reservasDao.devuelvePorId(idreserva).getListaReservas();
		String mensaje = reservasDao.devuelvePorId(idreserva).getMensaje();
		boolean isAdmin = true;
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		Reserva reserva = null;
		if (listaReservas != null) {
			if (listaReservas.size() == 1 && mensaje == null) {
				 reserva = listaReservas.get(0);
				 int cantidad = reserva.getCantidad();
				 System.out.println("CANTIDAD: "+cantidad);
				 cantidad++;
				 System.out.println("CANTIDAD NUEVA: "+cantidad);
				 reserva.setCantidad(cantidad);
				 mensaje = reservasDao.editarReserva(reserva, isAdmin, usuario);
				 System.out.println("CANTIDAD SUPERNUEVA: "+reserva.getCantidad());
								
			}
		}


		miRedirAtrib.addFlashAttribute("mensaje", mensaje);

		System.out.println(reserva);

		return "redirect:/gestion/reservas/todas";

	}
	
	@GetMapping("/minus/{id}")
	public String editarMinus(Model model, @PathVariable(name = "id") int idreserva,  HttpSession miSesion, RedirectAttributes miRedirAtrib) {

		List<Reserva> listaReservas = reservasDao.devuelvePorId(idreserva).getListaReservas();
		String mensaje = reservasDao.devuelvePorId(idreserva).getMensaje();
		boolean isAdmin = true;
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		Reserva reserva = null;
		if (listaReservas != null) {
			if (listaReservas.size() == 1 && mensaje == null) {
				 reserva = listaReservas.get(0);
				 int cantidad = reserva.getCantidad()-1;
				 if(cantidad > 0) {
					 reserva.setCantidad(cantidad);
					 mensaje = reservasDao.editarReserva(reserva, isAdmin, usuario);
				 }else {
					 mensaje = "Error --> No puede dejar una reserva con 0 entradas, si lo desea puede cancelarla";
				 }
								
			}
		}


		miRedirAtrib.addFlashAttribute("mensaje", mensaje);

		System.out.println(reserva);

		return "redirect:/gestion/reservas/todas";

	}

}