package com.miguelrm.tfg.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.Usuario;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntReservasDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;


/* ================================================== CONTROLADOR DE RESERVAS ================================================== 
 * 
 * Similar a la versión de gestión. FILTRA POR FECHAS LAS RESERVAS
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller

//Anotamos que vamos a entrar por /cliente/reservas
@RequestMapping("/cliente/reservas")

public class ReservasPublicController {

	
	//Tenemos reservasDao y eventosDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntReservasDao reservasDao;

	@Autowired
	IntEventosDao eventosDao;
	
	@Autowired
	IntPreparaServ prepWeb;

	 
	
	@GetMapping("/todas")
	public String verTodos(Model model, HttpSession miSesion) {
		
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		
		String email = usuario.getEmail();

		List<Reserva> listaReservas = reservasDao.devuelveByEmail(email).getListaReservas();
		String mensaje = reservasDao.devuelveByEmail(email).getMensaje();

		List<Reserva> listaLimpia = new ArrayList<Reserva>();
		
		model = prepWeb.envia(model);

		if (listaReservas != null) {
			
			if (listaReservas.size() > 0) {
				Date fecha = new Date();
				for(int i=0; i<listaReservas.size(); i++) {
					
					if(fecha.compareTo(listaReservas.get(i).getEvento().getFechaInicio()) < 0)   {
						listaLimpia.add(listaReservas.get(i));
					}
					
				}
			}
			
			if (listaLimpia == null || listaLimpia.size() == 0 && mensaje == null) {
				 
				mensaje = "No se han encontrado reservas en este momento";
			}
		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("miListaReservas", listaLimpia);
		model.addAttribute("tipo", "Mis Reservas");
		model.addAttribute("origen","/cliente/reservas/");

		return"/reservas/gestionReservas";
	}
	
	@PostMapping("/do/{idEvento}")
	public String doReserva(Model model, @PathVariable(name="idEvento") int idEvento, int cantidad, HttpSession miSesion, RedirectAttributes miRedirAtrib) {
		
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		
		Evento evento = eventosDao.devuelvePorId(idEvento).getListaEventos().get(0);
				
		Reserva reserva = new Reserva(cantidad, "Hecha por cliente logeado previamente", usuario, evento);
		
		System.out.println(reserva);
		
		String mensaje = reservasDao.insertarReserva(reserva);
		
		System.out.println(reserva);

		
		miRedirAtrib.addFlashAttribute("mensaje", mensaje);
		
		return"redirect:/cliente/reservas/todas";
	}
	
	@PostMapping("/doDirecto/{idEvento}")
	public String doDirecto(Model model, @PathVariable(name="idEvento") int idEvento, HttpSession miSesion, RedirectAttributes miRedirAtrib) {
		
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");
		
		Evento evento = eventosDao.devuelvePorId(idEvento).getListaEventos().get(0);
				
		Reserva reserva = new Reserva(1, "Hecha por cliente sin logeo previo", usuario, evento);
		
		System.out.println(reserva);
		
		String mensaje = reservasDao.insertarReserva(reserva);
		
		System.out.println(reserva);

		
		miRedirAtrib.addFlashAttribute("mensaje", mensaje);
		
		return"redirect:/cliente/reservas/todas";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(RedirectAttributes miRedirAttrib, @PathVariable(name = "id") int idReserva, HttpSession miSesion) {
		String mensaje = "";
		boolean isAdmin = false;
		Usuario usuario = (Usuario) miSesion.getAttribute("usuario");

		try {
			mensaje = reservasDao.borrarReserva(idReserva, isAdmin, usuario);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar la reserva";
			e.printStackTrace();
		}

		miRedirAttrib.addFlashAttribute("mensaje", mensaje);

		return "redirect:/cliente/reservas/todas";


	}
	

	
	@GetMapping("/add/{id}")
	public String editarAdd(Model model, @PathVariable(name = "id") int idreserva,  HttpSession miSesion, RedirectAttributes miRedirAtrib) {

		List<Reserva> listaReservas = reservasDao.devuelvePorId(idreserva).getListaReservas();
		String mensaje = reservasDao.devuelvePorId(idreserva).getMensaje();
		boolean isAdmin = false;
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

		return "redirect:/cliente/reservas/todas";

	}
	
	@GetMapping("/minus/{id}")
	public String editarMinus(Model model, @PathVariable(name = "id") int idreserva,  HttpSession miSesion, RedirectAttributes miRedirAtrib) {

		List<Reserva> listaReservas = reservasDao.devuelvePorId(idreserva).getListaReservas();
		String mensaje = reservasDao.devuelvePorId(idreserva).getMensaje();
		boolean isAdmin = false;
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
					 mensaje = "No puede dejar una reserva con 0 entradas, si lo desea puede cancelarla";
				 }

								
			}
		}


		miRedirAtrib.addFlashAttribute("mensaje", mensaje);

		System.out.println(reserva);

		return "redirect:/cliente/reservas/todas";

	}
	



}