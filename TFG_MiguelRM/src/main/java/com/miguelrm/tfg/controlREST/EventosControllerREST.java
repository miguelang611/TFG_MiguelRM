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

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.EventoREST;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;


/* ================================================== CONTROLADOR REST DE EVENTOS ================================================== 
 * 
 * Se trata del controlador "principal", ya que cuenta con más funciones que su hermano de tipos
 * 
 * Su funcionamiento se basa en el controlador web realizado anteriormente, siendo modificado con ciertas eliminaciones,
 * como las url de origen, o el procesado de la respuesta, que se realiza ahora a través de un nuevo método auxiliar
 * encargado de parsear la respuesta a presentar al usuario
 * 
 * 
/* ============================================================================================================================ 
 */

//Anotamos que es una clase simulada
//de controlador REST
@RestController

//Anotamos que vamos a entrar por /eventos
@RequestMapping("/eventosREST")

public class EventosControllerREST {

	
	//Tenemos eventosDao y tiposDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntEventosDao eventosDao;

	@Autowired
	IntTiposDao tiposDao;

	/*
	 * ==================================== MÉTODO AUX GENERARESPUESTA ===================================== 
	 * 
	 * Recibe la lista de eventos, el mensaje y el tipo de acción pedida --> para mostrar si hemos
	 * pedido eventos activos, destacados, etc
	 * 
	 * Si el mensaje no es nulo ha habido error, sólo devolvemos mensaje
	 * En caso contrario, comprobamos la lista -->
	 * 
	 * Si la lista tiene tamaño 0 es que no hay eventos de ese tipo (pero la conexión a BD es correcta)
	 * Si tiene tamaño 1, es que sólo hay un resultado, con lo que no mostraremos un índice de la lista
	 * Si tiene tamaño superior, con un for leemos la lista, y añadimos un índice a cada evento devuelto
	 * (NO coincide con el id, sólo indica el orden de la lista personalizada)
	 * 
	 * =========================================================================================== 
	 */
	private String generaRespuesta(List<Evento> listaEventos, String mensaje, String tipoPedido) {
		
		if(mensaje != null) {
			return mensaje;
		}else {
			String rdo = "";
			if(listaEventos.size() == 0) {
				rdo = "Advertencia: No se han encontrado eventos "+tipoPedido+" en este momento";
			}else {
				rdo = "========================================== LISTA DE EVENTOS "+tipoPedido.toUpperCase()+" ========================================== \n\n\n";
				if(listaEventos.size() == 1) {
					rdo+= listaEventos.get(0).toString()+"\n\n";
				}else {
					for(int i = 0; i < listaEventos.size(); i++) {
						int indice = i;
						indice = indice+1;
						
						rdo+= indice+". "+listaEventos.get(i).toString()+"\n\n";
					}
				}
				rdo+="\n\n========================================== FIN ==========================================";
			}

			
			return rdo;
		}
		
	}
	 
	/////////////////////////////////////// BLOQUE 1 --> LISTA  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/*
	 * ==================================== MÉTODO VERPORESTADO ===================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model y el estado que queremos (activo/cancelado)
	 * 
	 * 1. Ajustamos la variable estado y tipo a mandar acordemente.
	 * 
	 * 2. Recogemos la lista de eventos y el mensaje
	 * 
	 * 3. Llamamos al generaRespuesta, pasándole como argumentos la lista, el mensaje y el tipo
	 * 
	 * =========================================================================================== 
	 */

	@GetMapping("/estado/{estado}")
	public String verPorEstado(Model model, @PathVariable(name="estado") String estado) {


		List<Evento> listaEventos = null;
		String mensaje = "";
		String tipo = "";
		if(estado.equals("activos") || estado.equals("cancelados")) {
			if(estado.equals("activos")) {
				estado = "activo";
				tipo = "activos";
			}else {
				estado = "cancelado";
				tipo = "cancelados";
			}
			System.out.println(estado);
			listaEventos = eventosDao.devuelveByEstado(estado).getListaEventos();
			mensaje = eventosDao.devuelveByEstado(estado).getMensaje();

		}else {
			mensaje = "Acción no soportada --> indique con 'activos' o 'cancelados' si quiere eventos activos o no activos";
		}

		return generaRespuesta(listaEventos,mensaje, tipo);
		

	}
	
	/*
	 * =================================== MÉTODO VERDESTACADOS =================================== 
	 * 
	 * Procedimiento análogo al anterior, sólo que invocando otro método del eventosDao y 
	 * con otros parámetros para el if
	 * 
	 * =========================================================================================== 
	 */
	
	
	
	@GetMapping("/destacados/{destacado}")
	public String verDestacados(Model model, @PathVariable(name="destacado") String destacado) {


		List<Evento> listaEventos = null;
		String mensaje = "";
		String tipo = "";
		if(destacado.equals("si") || destacado.equals("no")) {
			if(destacado.equals("si")) {
				destacado = "s";
				tipo = "destacados";
			}else {
				destacado = "";
				tipo = "no destacados";
			}
			System.out.println(destacado);
			listaEventos = eventosDao.devuelveByDestacado(destacado).getListaEventos();
			mensaje = eventosDao.devuelveByDestacado(destacado).getMensaje();

		}else {
			mensaje = "Acción no soportada --> indique con 'si' o 'no' si quiere eventos destacados o no destacados";
		}

		return generaRespuesta(listaEventos,mensaje, tipo);
		

	}

	/*
	 * =================================== MÉTODO VERTODOS =================================== 
	 * 
	 * Procedimiento análogo a los anteriores, sólo que invocando otro método del eventosDao,
	 * y sin recibir parámetro de estado/destacado
	 * 
	 * =========================================================================================== 
	 */
	
	@GetMapping("")
	public String verTodos(Model model) {

		List<Evento> listaEventos = eventosDao.devuelveTodos().getListaEventos();
		String mensaje = eventosDao.devuelveTodos().getMensaje();

		String tipo = ""; 
		
		String rdo = generaRespuesta(listaEventos,mensaje, tipo);
		
		return rdo;
	}
	
	/*
	 * =================================== MÉTODO VERPORPALABRA =================================== 
	 * 
	 * Procedimiento análogo a los anteriores, sólo que invocando otro método del eventosDao,
	 * recibiendo palabra a buscar, e invocando al método correspondiente
	 * 
	 * =========================================================================================== 
	 */

	
	@GetMapping("/contiene/{palabra}")
	public String verPorPalabra(Model model, @PathVariable (name="palabra") String palabra) {
		
		List<Evento> listaEventos = null;
		String mensaje = "";
		String tipo = "";
		if(palabra != null) {
			listaEventos = eventosDao.devuelveByPalabra(palabra).getListaEventos();
			mensaje = eventosDao.devuelveByPalabra(palabra).getMensaje();
			tipo = "que contienen '"+palabra+"'";
		}else {
			mensaje = "Error: Indique una palabra correcta";
		}

		return generaRespuesta(listaEventos,mensaje, tipo);
		
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
		String mensaje = eventosDao.devuelveByTipo(idTipo).getMensaje();

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
					mensaje = "Error desconocido de tipos";
				}
			}else {
				nombreTipo = listaTipos.get(0).getNombre();
			}
			
			
		}
		
		String tipoPedido = "del tipo "+nombreTipo;
		
		String rdo = generaRespuesta(listaEventos, mensaje, tipoPedido);
		
		return rdo;
		
	}
	
	
	/*
	 * =================================== MÉTODO DEVUELVEPORID TEXTO ==================================== 
	 * 
	 * Invoca al método devuelvePorId del eventosDao, y almacenamos la lista de eventos
	 * y el mensaje que devuelve, para mandarlo al generaRespuesta, y que devuelva
	 * el String de respuesta
	 * 
	 * 
	 * ============================================================================================== 
	 */
	
	@GetMapping("/{id}/texto")
	public String devuelvePorIdTexto(Model model, @PathVariable(name = "id") int idEvento) {

		List<Evento> listaEventos = eventosDao.devuelvePorId(idEvento).getListaEventos();
		String mensaje = eventosDao.devuelvePorId(idEvento).getMensaje();
		
		return generaRespuesta(listaEventos, mensaje, "");

		
	}
	
	
	/*
	 * =================================== MÉTODO DEVUELVEPORID ==================================== 
	 * 
	 * 1. Invoca al método devuelvePorId del eventosDao
	 * 
	 * 2. Coge la lista y recupera la posición 0: el evento full
	 * 
	 * 3. Creamos un objeto EventoREST --> la diferencia es que dentro tiene un TipoREST,
	 * donde ya no hay una lista de eventos --> el json va limpio
	 * 
	 * 4. Devolvemos dicho objeto eventoREST
	 * 
	 * ============================================================================================== 
	 */

	
	@GetMapping("/{id}")
	public EventoREST devuelvePorId(Model model, @PathVariable(name = "id") int idEvento) {

		List<Evento> listaEventos = eventosDao.devuelvePorId(idEvento).getListaEventos();
		//String mensaje = eventosDao.devuelvePorId(idEvento).getMensaje();
		
		Evento miEventoFull = listaEventos.get(0);
		EventoREST miEvento = new EventoREST(miEventoFull);
		
		return miEvento;

	}
	
		
	
	/////////////////////////////////// BLOQUE 2 --> ALTA, EDICIÓN, ESTADO, DESTACAR, ELIMINAR  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * =================================== MÉTODOS ESTADO Y DESTACADO ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y por PathVariable el id y el estado/destacado
	 * 
	 * 1. Llamamos al modificarEvento, pasándole id, estado y destacado:
	 * 		Irá a null estado/destacado según el método concreto
	 * 		(Por debajo comprueba que no intentamos destacar un evento cancelado)
	 *
	 * 2. Recogemos el mensaje y lo devolvemos
	 * 
	 * ================================================================================================= 
	 */
	
	@GetMapping("/estado/{id}/{estado}")
	public String changeEstado(Model model, @PathVariable(name = "id") int idEvento, @PathVariable(name="estado") String estado) {
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

		return mensaje;
		
	}

	@GetMapping("/destacar/{id}/{destacado}")
	public String changeDestacado(Model model, @PathVariable(name = "id") int idEvento, @PathVariable(name="destacado") String destacado) {
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

		return mensaje;
		
	}
	
	/*
	 * =================================== MÉTODOS ELIMINAR POR ID Y POR TIPO ==================================== 
	 * 
	 * 0. Entramos por GET, y tenemos de entrada el Model, y por PathVariable el id
	 * 
	 * 1. Llamamos al borrarEvento / borrarEventosByTipo según corresponda
	 * 
	 * 2. Recogemos el mensaje y lo devolvemos
	 * 
	 * ================================================================================================= 
	 */
	
	
	@DeleteMapping("/{id}")
	public String eliminarPorId(Model model, @PathVariable(name = "id") int idEvento) {
		String mensaje = "";
		try {
			mensaje = eventosDao.borrarEvento(idEvento);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar el evento";
			e.printStackTrace();
		}

		return mensaje;

	}
	
	@DeleteMapping("/tipo/{id}")
	public String eliminarPorTipo(Model model, @PathVariable(name = "id") int idTipo) {
		String mensaje = "";
		try {
			mensaje = eventosDao.borrarEventosByTipo(idTipo);
		} catch (Exception e) {
			mensaje = "Fallo desconocido al eliminar los eventos del tipo "+idTipo;
			e.printStackTrace();
		}

		return mensaje;


	}

	
	/*
	 * ================================ MÉTODOS PROCESAALTA/PROCESAEDITAR ================================= 
	 * 
	 * 0. Recibimos por POST, y tenemos de entrada el Model, y el objeto Evento
	 * 
	 * (Si es nuevo, lo ponemos activo forzosamente)
	 * 
	 * 2. Invocamos a insertarEvento/editarEvento del eventosDao, guardando el mensaje
	 * 
	 * 3. Devolvemos el mensaje, confirmando el alta/edicción, o informando la causa
	 * del error
	 * 
	 * ================================================================================================= 
	 */
	
	
	@PostMapping("")
	public String procesarAltaEvento(@RequestBody Evento evento) {

		System.out.println(evento.getNombre());

		evento.setEstado("activo");

		String mensaje = eventosDao.insertarEvento(evento);
		
		return mensaje;

	}
	
	
	@PutMapping("")
	public String procesarEditarEvento(Model model, @RequestBody Evento evento) {
		
		String mensaje = null;

		System.out.println(evento);

		mensaje = eventosDao.editarEvento(evento);
		
		return mensaje;

	}

}
