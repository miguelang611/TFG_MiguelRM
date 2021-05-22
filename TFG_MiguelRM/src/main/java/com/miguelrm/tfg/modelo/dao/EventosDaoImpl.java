package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.ListaEventosMensaje;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.repository.EventosRepository;
import com.miguelrm.tfg.modelo.repository.TiposRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class EventosDaoImpl implements IntEventosDao {
	
	/* RESPECTRO AL ANTERIOR EVENTOSDAOIMPL:
	 * 
	 * Se ha modificado la parte de estado y destacados, para poder recibir parámetros
	 * 
	 * Se ha mejorado la sección de insertar --> verifica que no haya un evento con ese id, o que coincidan los datos de dentro
	 * 
	 * Se ha añadido el método de encontrar por palabra --> se divide en 2 métodos del repository, primero busca la palabra en nombres
	 * y luego en descripciones
	 * 
	 */

	@Autowired
	EventosRepository miEventosRepo;

	@Autowired
	TiposRepository miTiposRepo;

	public Evento findEventoById(int idEvento) {
		return miEventosRepo.findById(idEvento).orElse(null);
	}

	// Todos los métodos devuelve llaman a métodos contenidos en el repository.
	// Si salta excepción es que falla la conexión a la BD
	@Override
	public ListaEventosMensaje devuelvePorId(int idEvento) {
		List<Evento> miListaEventos = new ArrayList<>();
		String mensaje = null;
		Evento evento = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el EventosRepository,
			// que interpreta a través de JPARepository
			evento = miEventosRepo.findById(idEvento).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		if (BDconexionOK) {
			if (evento != null) {
				miListaEventos.add(evento);
			} else {
				mensaje = "Error -> Evento con ID " + idEvento + " no encontrado";
			}
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveActivos() {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {
			// Hemos definido este método en el EventosRepository,
			// que interpreta a través de JPARepository
			miListaEventos = miEventosRepo.findByEstado("activo");
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveByDestacado(String destacado) {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {
			if (destacado.equals("s")) {
				// Los eventos destacados tienen que estar activos obligatoriamente
				miListaEventos = miEventosRepo.findByDestacadoAndEstado(destacado, "activo");
			} else {
				// Pero los no destacados no tienen por qué:
				miListaEventos = miEventosRepo.findByDestacado(destacado);
			}

		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveByEstado(String estado) {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {

			miListaEventos = miEventosRepo.findByEstado(estado);

		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveByTipo(int idTipo) {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {
			miListaEventos = miEventosRepo.findByTipo(idTipo);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveTodos() {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {
			miListaEventos = miEventosRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	@Override
	public ListaEventosMensaje devuelveByPalabra(String palabra) {
		List<Evento> miListaEventos = null;
		String mensaje = null;
		try {

			List<Evento> miListaEvNombre = miEventosRepo.findByNombreContains(palabra);
			if (miListaEvNombre != null) {
				List<Evento> miListaEvDescripcion = miEventosRepo.findByDescripcionContains(palabra);
				// Si no había encontrado nada por nombre, la lista lo que encuentre por
				// descripción
				if (miListaEvNombre.size() == 0) {
					System.out.println("AAAAAAAAA");
					miListaEventos = miListaEvDescripcion;

					// PERO, si la lista obtenida por nombre tiene algún dato,
					// leeremos la lista obtenida por descripción
					// y comprobaremos objeto a objeto contra la otra lista
					// Si no lo contiene, añadimos dicho objeto a la liista original
				} else {
					miListaEventos = miListaEvNombre;
					for (int i = 0; i < miListaEvDescripcion.size(); i++) {
						Evento miEvento = miListaEvDescripcion.get(i);
						if (!miListaEventos.contains(miEvento)) {
							miListaEventos.add(miEvento);
						}
					}
				}

			}
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaEventosMensaje miListaEventosMensaje = new ListaEventosMensaje(miListaEventos, mensaje);
		return miListaEventosMensaje;
	}

	// Para cancelar el evento, subyace una modificación del mismo
	/*
	 * 1. Recuperamos un evento intermedio por id
	 * 
	 * 2. En ese evento intermedio, cambiamos el estado a cancelado y forzamos no
	 * destacado
	 * 
	 * 3. Ejecutamos save de ese evento intermedio
	 * 
	 * 4. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String modificarEvento(int idEvento, String newEstado, String newDestacado) {
		String mensaje = "Error desconocido";

		Evento miEventoIntermedio = null;

		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el EventosRepository,
			// que interpreta a través de JPARepository
			miEventoIntermedio = miEventosRepo.findById(idEvento).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		String oldEstado = "";
		boolean eventoExiste = false;
		if (BDconexionOK) {
			if (miEventoIntermedio != null) {
				eventoExiste = true;
				oldEstado = miEventoIntermedio.getEstado();
			} else {
				mensaje = "Error -> Evento con ID " + idEvento + " no encontrado";
			}
		}

		boolean changeEstado = false;
		if (eventoExiste) {
			try {
				// Si viene sin newDestacado es que sólo quiere cambiar estado
				if (newDestacado == null && newEstado != null) {
					System.out.println("AAAAAAAA");
					// Si es cancelado, forzamos que pase a NO destacado
					if (newEstado.equals("cancelado")) {
						System.out.println("EEEEEEEEE");
						miEventoIntermedio.setDestacado("");
						miEventoIntermedio.setEstado(newEstado);
						// Si no, simplemente grabamos el nuevo estado
					} else {
						miEventoIntermedio.setEstado(newEstado);
					}
					changeEstado = true;

					// Y viceversa
				} else if (newDestacado != null && newEstado == null) {

					// Si el evento está cancelado no podemos destacarlo, mandamos mensaje
					if (oldEstado.equals("cancelado") && newDestacado.equals("s")) {
						mensaje = "Error -> No se puede destacar un evento cancelado";
						// Si está activo no hay problema, pasamos a sí destacado y avanzamos al
						// siguiente paso
					} else {
						miEventoIntermedio.setDestacado(newDestacado);
						changeEstado = true;
					}
				} else {
					mensaje = "Error -> Acción no permitida";
				}

			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el evento " + idEvento + ", pero no se ha podido cambiar su estado";
			}
		}

		if (changeEstado) {
			try {
				miEventosRepo.save(miEventoIntermedio);
				// Customizamos el mensaje para que diga que ha quedado activo/cancelado y si/no
				// destacado
				if (miEventoIntermedio.getDestacado().equals("s")) {
					mensaje = "El evento " + miEventoIntermedio.getNombre() + " ha pasado a estar "
							+ miEventoIntermedio.getEstado() + " y destacado";
				} else {
					mensaje = "El evento " + miEventoIntermedio.getNombre() + " ha pasado a estar "
							+ miEventoIntermedio.getEstado() + " y NO destacado";
				}
			} catch (Exception e) {
				mensaje = "Error -> No se ha podido salvar en la BBDD la modificación de del evento a "
						+ miEventoIntermedio.getEstado() + " y " + miEventoIntermedio.getDestacado();
			}
		}

		return mensaje;

	}

	/*
	 * 1. Recuperamos un evento intermedio por id, para comprobar que exista
	 * 
	 * 2. Ejecutamos el método deleteById del repository
	 * 
	 * 3. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String borrarEvento(int idEvento) {

		String mensaje = "Error desconocido";

		Evento miEventoIntermedio = null;

		boolean BDconexionOK = false;
		try {
			miEventoIntermedio = miEventosRepo.findById(idEvento).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean eventoExiste = false;
		if (BDconexionOK) {
			if (miEventoIntermedio != null) {
				eventoExiste = true;
			} else {
				mensaje = "Error -> Evento con ID " + idEvento + " no encontrado";
			}
		}

		if (eventoExiste) {
			try {
				miEventosRepo.deleteById(idEvento);
				mensaje = "El evento " + miEventoIntermedio.getNombre() + " se ha eliminado correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el evento, pero ha habido un error al salvar la eliminación del evento"
						+ idEvento + " en la BBDD";
			}

		}

		return mensaje;
	}

	// Borrar por tipo
	// Este método se basa en el borrarTipo que creamos en la versión web del
	// programa,
	// donde primeros se comprobaba la existencia del tipo
	// luego se recuperaban sus eventos
	// y se eliminaban dichos eventos leyendo la lista
	// sólo que ya no borraremos el tipo

	@Override
	public String borrarEventosByTipo(int idTipo) {
		String mensaje = "Error desconocido";

		Tipo miTipoIntermedio = null;

		boolean BDconexionOK = false;
		try {
			miTipoIntermedio = miTiposRepo.findById(idTipo).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean tipoExiste = false;
		if (BDconexionOK) {
			if (miTipoIntermedio != null) {
				tipoExiste = true;
			} else {
				mensaje = "Error -> Tipo con ID " + idTipo + " no encontrado";
			}
		}

		List<Evento> miListaEventos = null;
		boolean readyDeleteEventos = false;
		if (tipoExiste) {

			miListaEventos = devuelveByTipo(idTipo).getListaEventos();
			String mensajeEv = devuelveByTipo(idTipo).getMensaje();

			// Si la lista está en null, ha habido un error, asignamos mensaje
			if (miListaEventos == null) {

				mensaje = mensajeEv;
				// Si no es nula y
			} else {

				// tiene tamaño de 0, sin mensaje de error
				if (miListaEventos.size() == 0 && mensajeEv == null) {
					mensaje = "Error -> El tipo " + miTipoIntermedio.getNombre()
							+ " existe, pero no había ningún evento, así que no se ha eliminado nada";
				}
				// Estamos listos para eliminar el tipo directamente
				// tiene tamaño distinto de 0, sin mensaje de error
				// quiere decir que hay eventos asociados a ese tipo
				else if (miListaEventos.size() != 0 && mensajeEv == null) {
					// Estamos listos para eliminar esos eventos, antes de eliminar
					// el tipo
					readyDeleteEventos = true;

					// En otro caso, asignamos el mensaje de error
				} else {
					mensaje = mensajeEv;
				}

			}

		}

		// Si viene por aquí quiere decir que la lista tiene al menos 1 evento del tipo,
		// que hay que eliminar previamente
		if (readyDeleteEventos) {
			// Leemos con un for la lista, y pasamos cada id de evento como parámetro
			// a la función de borrarEvento del EventosDaoImpl
			try {
				for (int i = 0; i < miListaEventos.size(); i++) {
					borrarEvento(miListaEventos.get(i).getIdEvento());
				}
				mensaje = "Se han borrado todos los eventos del tipo " + miTipoIntermedio.getNombre()
						+ " correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el tipo y sus eventos, pero se produjo un error al eliminarlos";
			}
		}

		return mensaje;

	}

	// Para insertar simplemente ejecutamos save del objeto que nos llega del
	// controller
	@Override
	public String insertarEvento(Evento miEvento) {
		String insertarRdo = "";
		try {

			String msgCheckID = devuelvePorId(miEvento.getIdEvento()).getMensaje();
			System.out.println(msgCheckID);

			// Hay un evento con ese ID
			if (msgCheckID == null) {
				insertarRdo = "Error -> No se puede insertar un evento con dicho id, ya que en la BBDD hay otro evento con dicho ID. ¡Verifíquelo!";
			}

			// El mensaje no viene vacío pero la lista lo está --> no hay eventos con ese id
			if (msgCheckID != null && !msgCheckID.contains("Error")) {
				List<Evento> miListaTotal = devuelveTodos().getListaEventos();
				String mensaje = devuelveTodos().getMensaje();
				// Si no hay error
				if (mensaje == null) {
					// Y el evento no está en la lista de eventos
					// (compara por debajo, pero excluye tipo, estado, destcado e id)
					if (!miListaTotal.contains(miEvento)) {
						// Si no esá, guardamos
						miEventosRepo.save(miEvento);
						insertarRdo = "El evento " + miEvento.getNombre() + " se ha insertado correctamente";
					}
					// En caso contrario, no hacemos la inserción
					else {
						insertarRdo = "Error -> No se puede insertar un evento que ya existe. Si lo desea, puede editarlo";
					}
				} else {
					insertarRdo = mensaje;
				}
			}

			if (msgCheckID != null && msgCheckID.contains("Error")) {
				insertarRdo = msgCheckID;
			}
			
		} catch (Exception e) {
			insertarRdo = "Error al insertar el evento. Verifique que los datos a insertar son correctos";
			e.printStackTrace();
		}
		
		return insertarRdo;
	}

	/*
	 * 1. Recuperamos el id del evento que viene, si falla es mala señal
	 * 
	 * 2. En ese evento, leemos que se cumpla que para ser destacado tiene que estar
	 * activo
	 * 
	 * 3. Recuperamos un objeto intermedio por id
	 * 
	 * 4. Hacemos eventoIntermedio = evento, es decir, decimos que el sincronizado
	 * con BD tome los valores del modificado
	 * 
	 * 5. Hacemos save de ese objeto
	 * 
	 * 6. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String editarEvento(Evento miEvento) {

		String editarRdo = "Error desconocido";

		Evento miEventoIntermedio = null;

		int codigoEvento = 0;

		boolean eventoIdCorrecto = false;
		try {
			codigoEvento = miEvento.getIdEvento();
			eventoIdCorrecto = true;
		} catch (Exception e) {
			editarRdo = "Error -> no se pudo recuperar el id del evento a modificar";
		}

		boolean checkActivoIfDestacado = false;
		if (eventoIdCorrecto) {

			if (miEvento.getEstado().equals("cancelado") && miEvento.getDestacado().equals("s")) {

				editarRdo = "Error -> no se puede destacar un evento cancelado";
			} else {
				checkActivoIfDestacado = true;
			}
		}

		boolean BDconexionOK = false;
		if (checkActivoIfDestacado) {
			try {
				miEventoIntermedio = miEventosRepo.findById(codigoEvento).orElse(null);
				BDconexionOK = true;
			} catch (Exception e) {
				editarRdo = "Error -> fallo de conexión a la BBDD";
			}
		}

		boolean eventoExiste = false;
		if (BDconexionOK) {
			if (miEventoIntermedio != null) {
				eventoExiste = true;
			} else {
				editarRdo = "Error -> Evento con ID " + codigoEvento + " no encontrado";
			}
		}

		if (eventoExiste) {
			try {
				miEventoIntermedio = miEvento;
				miEventosRepo.save(miEventoIntermedio);
				editarRdo = "El evento " + miEventoIntermedio.getNombre() + " con id "
						+ miEventoIntermedio.getIdEvento() + " se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrado el evento, pero ha habido un error al salvar la eliminación del evento "
						+ codigoEvento + " en la BBDD";
			}
		}

		return editarRdo;
	}

}
