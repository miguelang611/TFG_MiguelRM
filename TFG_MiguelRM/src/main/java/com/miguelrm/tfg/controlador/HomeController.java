package com.miguelrm.tfg.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.Perfile;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.beans.Usuario;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntPerfilesDao;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;
import com.miguelrm.tfg.modelo.dao.IntUsuariosDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;

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
@RequestMapping("/")

public class HomeController {
//public class HomeController implements ErrorController {
	/*
	 * @GetMapping("/error") public String handleError(HttpServletRequest request,
	 * Model model) {
	 * 
	 * int codigo = 000; String mensaje = "Error desconocido";
	 * 
	 * Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	 * 
	 * if (status != null) { Integer statusCode =
	 * Integer.valueOf(status.toString());
	 * 
	 * if (statusCode == HttpStatus.NOT_FOUND.value()) { // handle HTTP 404 Not
	 * Found error codigo = 404; mensaje =
	 * "¡Hemos buscado por todas partes, pero no lo hemos encontrado!";
	 * 
	 * } else if (statusCode == HttpStatus.FORBIDDEN.value()) { // handle HTTP 403
	 * Forbidden error codigo = 403; mensaje =
	 * "¡Vaya! No puedes pasar por aquí... ¡Si crees que deberías, aségurate de estar logeado!"
	 * ;
	 * 
	 * } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) { //
	 * handle HTTP 500 Internal Server error codigo = 500; mensaje =
	 * "¡Vaya! Algo gordo ha fallado. ¡Sentimos no poder mostrarte esta web!";
	 * 
	 * } }
	 * 
	 * model = prepWeb.envia(model);
	 * 
	 * model.addAttribute("codigo",codigo); model.addAttribute("mensaje",mensaje);
	 * 
	 * return "home/error"; }
	 * 
	 * @Override public String getErrorPath() { return "/error"; }
	 */
	// Tenemos eventosDao y tiposDao, ya que haremos uso de ambos,
	// además de anotar el correspondiente Autowired
	@Autowired
	IntEventosDao eventosDao;

	@Autowired
	IntTiposDao tiposDao;

	@Autowired
	IntPreparaServ prepWeb;

	@Autowired
	IntUsuariosDao usuDao;
	
	@Autowired
	IntPerfilesDao perfDao;

	@GetMapping("")
	public String index(Model model) {

		model = prepWeb.envia(model);

		return "home/index";

	}

	@GetMapping("/logout")
	public String logut(HttpServletRequest request) {

		SecurityContextLogoutHandler miLogOut = new SecurityContextLogoutHandler();

		miLogOut.logout(request, null, null);

		return "redirect:/";

	}

	@GetMapping("/login")
	public String toLogin(Model model, String error, String logout) {
		
        if (error != null)
            model.addAttribute("mensaje", "Error -> Su usuario y/o contraseña son incorrectos");

        if (logout != null)
            model.addAttribute("mensaje", "Se ha cerrado la sesión correctamente");


		//model = prepWeb.envia(model);

		return "home/formLogin";

	}

	@PostMapping("/login")
	public String doLogin(Model model, Authentication miAut, HttpSession miSesion) {

		Usuario miUsuario = usuDao.devuelvePorEmail(miAut.getName()).getListaUsuarios().get(0);

		for (GrantedAuthority ele : miAut.getAuthorities())
			System.out.println("ROL : " + ele.getAuthority());

		model.addAttribute("mensaje",miAut.getAuthorities());
		miSesion.setAttribute("usuario", miUsuario);

		return "redirect:/eventos";

	}


	@PostMapping("/registro")
	public String toRegistro(Model model, Usuario usuario, RedirectAttributes miRedirAttrib) {
		
		Usuario userBackup = usuario;
		
		System.out.println(usuario);
		
		usuario.setEnabled(1);
		usuario.setFechaRegistro(new Date());
		usuario.setPassword("{noop}"+usuario.getPassword());
		
		Perfile perfil = perfDao.devuelveByNombre("usuario").get(0);
		
		//lista = perfDao.devuelveByNombre("usuario");
		
		//System.out.println("==========="+lista.get(0).getNombre());
		
		//Perfile perfil = lista.get(0)
		
		usuario.setPerfil(perfil);
		
		String mensaje = usuDao.insertarUsuario(usuario);
		
		System.out.println(usuario);
		
		miRedirAttrib.addFlashAttribute("mensaje",mensaje);
		
		miRedirAttrib.addFlashAttribute("usuario",userBackup);
		
		if(mensaje != null && mensaje.contains("Error") || mensaje.contains("Aviso") ) {
			return "redirect:/registro";
		}else {
			return "redirect:/login";
		}
		

	}

	@GetMapping("/registro")
	public String doRegistro(Model model) {

		model = prepWeb.envia(model);

		return "home/formRegistro";

	}

}
