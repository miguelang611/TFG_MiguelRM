package com.miguelrm.tfg.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miguelrm.tfg.servicios.IntPreparaServ;

/* ================================================== CONTROLADOR DE USUARIOS ================================================== 
 * 
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador
@Controller
//Anotamos que vamos a entrar por /clientes
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	IntPreparaServ prepWeb;
	
	@GetMapping("/registro")
	public String toRegistro(Model model) {

		model = prepWeb.envia(model);

		return "home/formRegistro";

	}

	@PostMapping("/registro")
	public String doRegistro(Model model) {

		model = prepWeb.envia(model);

		return "redirect:/";

	}
	
	
}

