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

public class EventosPublicController {

	
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
	 * de devolver la lista con los tipos. 
	 * 
	 * Al ser el public controller, filtraremos los tipos por aquellos que tienen eventos activos,
	 * el resto serán eliminados, aunque existan en la BD
	 * 
	 * =========================================================================================== 
	 */

	public Model mandaListaTipos(Model model) {
		if(1==1) {
			String mensaje = "";
			List<Tipo> listaTipos = tiposDao.devuelveTipos("").getListaTipos();
			String mensajeTipos = tiposDao.devuelveTipos("").getMensaje();

			if (listaTipos != null) {
				if (listaTipos.size() == 0 && mensajeTipos == null) {
					mensajeTipos = "No se han encontrado tipos en la Base de Datos";
				}
			}

			if (mensajeTipos != null) {
				mensaje = "Error con los tipos de eventos: " + mensajeTipos;
			}
			System.out.println(listaTipos);
			model.addAttribute("listaTiposFull", listaTipos);
			model.addAttribute("mensajeError",mensaje);
		}
		
		if(2==2) {
			List<Tipo> listaTipos = tiposDao.devuelveTipos("activos").getListaTipos();
			String mensajeTipos = tiposDao.devuelveTipos("activos").getMensaje();
			List<Tipo> nuevaLista = listaTipos;
			if (listaTipos != null) {
				if (listaTipos.size() == 0 && mensajeTipos == null) {
					mensajeTipos = "No se han encontrado tipos en la Base de Datos";
				}else {
					for(int i = 0; i < listaTipos.size(); i++) {
						List<Evento> lista = listaLimpia(eventosDao.devuelveByTipo(i).getListaEventos());
						if(lista == null || lista.size() == 0) {
							
						}else {
							if(!nuevaLista.contains(listaTipos.get(i))){
								nuevaLista.add(listaTipos.get(i));
							}
						}
					}
				}
			}

			
			model.addAttribute("listaTipos", nuevaLista);
		}
		
		
		return model;
	}
	
	
	public List<Evento> listaLimpia (List<Evento> miListaEventos){
		List<Evento> miListaNueva = eventosDao.devuelveByPalabra("").getListaEventos();
		try {
			// Hemos definido este método en el TiposRepository,
			// que interpreta a través de JPARepository
			if(miListaEventos != null && miListaEventos.size() >= 0 ) {
						for(int a=0; a<miListaEventos.size(); a++) {
							if(miListaEventos.get(a).getEstado().equals("activo")) {
								miListaNueva.add(miListaEventos.get(a));
							}
						}

					
				}
		}catch(Exception e) {
			
		}
		
		return miListaNueva;
		
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

	
	@GetMapping("/")
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

		return "buyEventos";

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


		return "buyEventos";

	}
	
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Evento> nuevaLista = null;
		String mensaje = "";
		String tipo = "";
		if(palabra != null) {
			List<Evento> listaEventos = eventosDao.devuelveByPalabra(palabra).getListaEventos();
			mensaje = eventosDao.devuelveByPalabra(palabra).getMensaje();
			nuevaLista = listaLimpia(listaEventos);
			if(nuevaLista == null || nuevaLista.size() == 0 ) {
				mensaje = "No hay eventos que contengan la palabra "+palabra;
			}
			tipo = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		
		model.addAttribute("mensajeError", mensaje);
		
		
		model.addAttribute("miListaEventos", nuevaLista);
		model.addAttribute("tipo", tipo);
		model.addAttribute("origen","/contiene/"+palabra);
		
		return "buyEventos";
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
	 * 4. Una vez realizadas estas comprobaciones específicas, podemos invocar al método auxiliar
	 * utilizado anteriormente
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("/tipo/{idTipo}")
	public String verPorTipo(Model model, @PathVariable(name = "idTipo") int idTipo) {

		List<Evento> listaEventos = eventosDao.devuelveByTipo(idTipo).getListaEventos();
		List<Evento> nuevaLista = listaLimpia(listaEventos);
		String mensaje = eventosDao.devuelveByTipo(idTipo).getMensaje();		
		
		model = mandaListaTipos(model);

		String nombreTipo = "";
		Tipo tipo = null;
		if (nuevaLista != null && mensaje == null) {
			//Si la lista no es nul, pero está vacíay el mensaje es nulo, la conexión a la BBDD es correcta,
			//así que vertificaremos si el tipo solicitado existe
			
			List<Tipo> listaTipos = tiposDao.devuelvePorId(idTipo).getListaTipos();
			String mensajeTipo = tiposDao.devuelvePorId(idTipo).getMensaje();
			
			if(nuevaLista.size() == 0) {
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
		
		
		model.addAttribute("miListaEventos", nuevaLista);
		model.addAttribute("mensajeError", mensaje);
		model.addAttribute("origen","/tipo/{"+idTipo+"}");
		
		return "buyEventos";
		
	}
	
	@GetMapping("/detalle/{id}")
	public String verPorId(Model model, @PathVariable(name = "id") int idEvento) {

		List<Evento> listaEventos = eventosDao.devuelvePorId(idEvento).getListaEventos();
		String mensaje = eventosDao.devuelvePorId(idEvento).getMensaje();
		System.out.println(listaEventos);
		System.out.println(mensaje);
		model = mandaListaTipos(model);
		
		List<Evento> listaDestacados = eventosDao.devuelveByDestacado("s").getListaEventos();
		for(int i=listaDestacados.size() - 1; i > 1; i--) {

			listaDestacados.remove(i);
			
		}
		model.addAttribute("listaEventos", listaDestacados);
		
		Evento miEvento = null;
		if (mensaje == null) {
			listaEventos = listaLimpia(listaEventos);
			if(listaEventos.size() >0) {
				miEvento = listaEventos.get(0);

			}else {
				mensaje = "Error --> No se ha encontrado ningún evento activo con ID "+idEvento;
			}
			System.out.println(miEvento);

		}
		
		model.addAttribute("mensajeError", mensaje);
		
		model.addAttribute("evento", miEvento);


		return "detalleEvento";

	}
	

	



}
