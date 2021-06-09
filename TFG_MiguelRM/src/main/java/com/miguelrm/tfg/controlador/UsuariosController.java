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

import com.miguelrm.tfg.modelo.beans.Noticia;
import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.dao.IntNoticiasDao;
import com.miguelrm.tfg.servicios.IntPreparaServ;
import com.miguelrm.tfg.servicios.PreparaServImpl;
import com.miguelrm.tfg.modelo.dao.IntCategoriasDao;

/* ================================================== CONTROLADOR DE TIPOS ================================================== 
 * 
 * Se trata del controlador "secundario", ya que cuenta con muchas menos funciones que su hermano de noticias
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

