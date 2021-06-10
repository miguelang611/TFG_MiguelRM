package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.ListaReservasMensaje;
import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.repository.ReservasRepository;
import com.miguelrm.tfg.modelo.repository.EventosRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class ReservasDaoImpl implements IntReservasDao {
	
	@Autowired
	ReservasRepository miReservasRepo;

	@Autowired
	EventosRepository miEventosRepo;

	public Reserva findReservaById(int idReserva) {
		return miReservasRepo.findById(idReserva).orElse(null);
	}

	// Todos los métodos devuelve llaman a métodos contenidos en el repository.
	// Si salta excepción es que falla la conexión a la BD
	@Override
	public ListaReservasMensaje devuelvePorId(int idReserva) {
		List<Reserva> miListaReservas = new ArrayList<>();
		String mensaje = null;
		Reserva reserva = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el ReservasRepository,
			// que interpreta a través de JPARepository
			reserva = miReservasRepo.findById(idReserva).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		if (BDconexionOK) {
			if (reserva != null) {
				miListaReservas.add(reserva);
			} else {
				mensaje = "Error -> Reserva con ID " + idReserva + " no encontrado";
			}
		}
		ListaReservasMensaje miListaReservasMensaje = new ListaReservasMensaje(miListaReservas, mensaje);
		return miListaReservasMensaje;
	}


	
	@Override
	public ListaReservasMensaje devuelveByEvento(int idEvento) {
		List<Reserva> miListaReservas = null;
		String mensaje = null;
		try {
			miListaReservas = miReservasRepo.findByIdEvento(idEvento);
			System.out.println(miListaReservas);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaReservasMensaje miListaReservasMensaje = new ListaReservasMensaje(miListaReservas, mensaje);
		return miListaReservasMensaje;
	}
	
	@Override
	public ListaReservasMensaje devuelveByEmail(String email) {
		List<Reserva> miListaReservas = null;
		String mensaje = null;
		try {
			miListaReservas = miReservasRepo.findByEmail(email);
			System.out.println(miListaReservas);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaReservasMensaje miListaReservasMensaje = new ListaReservasMensaje(miListaReservas, mensaje);
		return miListaReservasMensaje;
	}

	@Override
	public ListaReservasMensaje devuelveTodos() {
		List<Reserva> miListaReservas = null;
		String mensaje = null;
		try {
			miListaReservas = miReservasRepo.findAll();
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaReservasMensaje miListaReservasMensaje = new ListaReservasMensaje(miListaReservas, mensaje);
		return miListaReservasMensaje;
	}

	/*
	@Override
	public ListaReservasMensaje devuelveByPalabra(String palabra) {
		List<Reserva> miListaReservas = null;
		String mensaje = null;
		try {

			List<Reserva> miListaEvNombre = miReservasRepo.findByNombreContains(palabra);
			if (miListaEvNombre != null) {
				List<Reserva> miListaEvDescripcion = miReservasRepo.findByDescripcionContains(palabra);
				// Si no había encontrado nada por nombre, la lista lo que encuentre por
				// descripción
				if (miListaEvNombre.size() == 0) {
					miListaReservas = miListaEvDescripcion;

					// PERO, si la lista obtenida por nombre tiene algún dato,
					// leeremos la lista obtenida por descripción
					// y comprobaremos objeto a objeto contra la otra lista
					// Si no lo contiene, añadimos dicho objeto a la liista original
				} else {
					miListaReservas = miListaEvNombre;
					for (int i = 0; i < miListaEvDescripcion.size(); i++) {
						Reserva miReserva = miListaEvDescripcion.get(i);
						if (!miListaReservas.contains(miReserva)) {
							miListaReservas.add(miReserva);
						}
					}
				}

			}
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaReservasMensaje miListaReservasMensaje = new ListaReservasMensaje(miListaReservas, mensaje);
		return miListaReservasMensaje;
	}

	// Para cancelar la reserva, subyace una modificación del mismo
	/*
	 * 1. Recuperamos una reserva intermedio por id
	 * 
	 * 2. En ese reserva intermedio, cambiamos el estado a cancelado y forzamos no
	 * destacado
	 * 
	 * 3. Ejecutamos save de ese reserva intermedio
	 * 
	 * 4. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */


	/*
	 * 1. Recuperamos un reserva intermedio por id, para comprobar que exista
	 * 
	 * 2. Ejecutamos el método deleteById del repository
	 * 
	 * 3. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String borrarReserva(int idReserva) {

		String mensaje = "Error desconocido";

		Reserva miReservaIntermedio = null;

		boolean BDconexionOK = false;
		try {
			miReservaIntermedio = miReservasRepo.findById(idReserva).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
		}

		boolean reservaExiste = false;
		if (BDconexionOK) {
			if (miReservaIntermedio != null) {
				reservaExiste = true;
			} else {
				mensaje = "Error -> Reserva con ID " + idReserva + " no encontrado";
			}
		}

		if (reservaExiste) {
			try {
				miReservasRepo.deleteById(idReserva);
				mensaje = "La reserva de " + miReservaIntermedio.getEvento().getNombre() + " se ha eliminado correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el reserva, pero ha habido un error al salvar la eliminación del reserva"
						+ idReserva + " en la BBDD";
			}

		}

		return mensaje;
	}

	// Borrar por evento
	// Este método se basa en el borrarEvento que creamos en la versión web del
	// programa,
	// donde primeros se comprobaba la existencia del evento
	// luego se recuperaban sus reservas
	// y se eliminaban dichos reservas leyendo la lista
	// sólo que ya no borraremos el evento

	@Override
	public String borrarReservasByEvento(int idEvento) {
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

		List<Reserva> miListaReservas = null;
		boolean readyDeleteReservas = false;
		if (eventoExiste) {

			miListaReservas = devuelveByEvento(idEvento).getListaReservas();
			String mensajeEv = devuelveByEvento(idEvento).getMensaje();

			// Si la lista está en null, ha habido un error, asignamos mensaje
			if (miListaReservas == null) {

				mensaje = mensajeEv;
				// Si no es nula y
			} else {

				// tiene tamaño de 0, sin mensaje de error
				if (miListaReservas.size() == 0 && mensajeEv == null) {
					mensaje = "Error -> El evento " + miEventoIntermedio.getNombre()
							+ " existe, pero no había ningún reserva, así que no se ha eliminado nada";
				}
				// Estamos listos para eliminar el evento directamente
				// tiene tamaño distinto de 0, sin mensaje de error
				// quiere decir que hay reservas asociados a ese evento
				else if (miListaReservas.size() != 0 && mensajeEv == null) {
					// Estamos listos para eliminar esos reservas, antes de eliminar
					// el evento
					readyDeleteReservas = true;

					// En otro caso, asignamos el mensaje de error
				} else {
					mensaje = mensajeEv;
				}

			}

		}

		// Si viene por aquí quiere decir que la lista tiene al menos 1 reserva del evento,
		// que hay que eliminar previamente
		if (readyDeleteReservas) {
			// Leemos con un for la lista, y pasamos cada id de reserva como parámetro
			// a la función de borrarReserva del ReservasDaoImpl
			try {
				for (int i = 0; i < miListaReservas.size(); i++) {
					borrarReserva(miListaReservas.get(i).getIdReserva());
				}
				mensaje = "Se han borrado todos las reservas del evento " + miEventoIntermedio.getNombre()
						+ " correctamente";
			} catch (Exception e) {
				mensaje = "Error -> Se ha encontrado el evento y sus reservas, pero se produjo un error al eliminarlos";
			}
		}

		return mensaje;

	}

	// Para insertar simplemente ejecutamos save del objeto que nos llega del
	// controller
	@Override
	public String insertarReserva(Reserva miReserva) {
		String insertarRdo = "";
		try {

			String msgCheckID = devuelvePorId(miReserva.getIdReserva()).getMensaje();
			System.out.println(msgCheckID);
			
			List<Reserva> lista = null;
			List<Reserva> listaResEvento = null;
			String rdo2 = "";

			// Hay un reserva con ese ID
			if (msgCheckID == null) {
				insertarRdo = "Error -> No se puede insertar un reserva con dicho id, ya que en la BBDD hay otro reserva con dicho ID. ¡Verifíquelo!";
			}
			
			boolean isNotRepeated = true;
			if(msgCheckID != null && msgCheckID.contains("Error -> Reserva con ID 0 no encontrado")) {
				
				lista = devuelveByEmail(miReserva.getUsuario().getEmail()).getListaReservas();
				
				
				if(lista != null && lista.size() > 0) {
					
					for(int i = 0; i < lista.size(); i++) {
						if(lista.get(i).getEvento().equals(miReserva.getEvento())) {
							
							insertarRdo = "Ya dispone de una reserva de este evento. Si lo desea puede aumentar la cantidad desde su panel de usuario";
							isNotRepeated = false;
						}
					}
				}
								
			}

			boolean allOK = false;
			if(isNotRepeated) {
					
				System.out.println("TEST");
				listaResEvento = devuelveByEvento(miReserva.getEvento().getIdEvento()).getListaReservas();
				
				if (listaResEvento != null) {
					System.out.println("NNNNNNNNNNNN");

					int cantidadRes = 0;
					if(listaResEvento.size() > 0) {
						for(int i = 0; i < listaResEvento.size(); i++) {
							
							cantidadRes += listaResEvento.get(i).getCantidad();
								
						}
						
					}

					int totalInicial = cantidadRes + miReserva.getCantidad();
					int aforoMaximo = miReserva.getEvento().getAforoMaximo();
					if(totalInicial > aforoMaximo) {
						
						System.out.println("POPPPPPPPPPPP");

						
						int cantidadSiReservable = aforoMaximo - cantidadRes;
						if(cantidadSiReservable > 0) {
							miReserva.setCantidad(cantidadSiReservable);
							allOK = true;
							rdo2 = "\n\rNOTA: Sólo quedaban "+cantidadSiReservable+" plazas disponibles, de modo que su reserva se ha reducido a dicha cantidad";
							
						}else {
							System.out.println("QQQQQQQQQQQQQQQQQ");

							insertarRdo = "Error --> No se puede realizar la reserva al superarse el aforo máximo";
						}
						
					}else {
						allOK = true;
					}
				}else {
					allOK = true;
				}
				
			}
			
			
			if(allOK) {
				try {
					miReservasRepo.save(miReserva);
					insertarRdo = "La reserva de " + miReserva.getEvento().getNombre()+ " se ha insertado correctamente";
					insertarRdo += rdo2;
				}catch(Exception e) {
					insertarRdo = "Error --> no se pudo realizar reserva contra BBDD";
				}

			}
			
		
			
		} catch (Exception e) {
			insertarRdo = "Error al insertar el reserva. Verifique que los datos a insertar son correctos";
			e.printStackTrace();
		}
		
		return insertarRdo;
	}

	/*
	 * 1. Recuperamos el id del reserva que viene, si falla es mala señal
	 * 
	 * 2. En ese reserva, leemos que se cumpla que para ser destacada tiene que estar
	 * activo
	 * 
	 * 3. Recuperamos un objeto intermedio por id
	 * 
	 * 4. Hacemos reservaIntermedio = reserva, es decir, decimos que el sincronizado
	 * con BD tome los valores del modificado
	 * 
	 * 5. Hacemos save de ese objeto
	 * 
	 * 6. Devolvemos mensaje, confirmando éxito o indicando causa del error
	 * 
	 */
	@Override
	public String editarReserva(Reserva miReserva) {

		String editarRdo = "Error desconocido";

		Reserva miReservaIntermedio = null;

		int codigoReserva = 0;

		boolean reservaIdCorrecto = false;
		try {
			codigoReserva = miReserva.getIdReserva();
			reservaIdCorrecto = true;
		} catch (Exception e) {
			editarRdo = "Error -> no se pudo recuperar el id del reserva a modificar";
		}

		boolean BDconexionOK = false;
		if (reservaIdCorrecto) {
			try {
				miReservaIntermedio = miReservasRepo.findById(codigoReserva).orElse(null);
				BDconexionOK = true;
			} catch (Exception e) {
				editarRdo = "Error -> fallo de conexión a la BBDD";
			}
		}

		boolean reservaExiste = false;
		if (BDconexionOK) {
			if (miReservaIntermedio != null) {
				reservaExiste = true;
			} else {
				editarRdo = "Error -> Reserva con ID " + codigoReserva + " no encontrado";
			}
		}

		if (reservaExiste) {
			try {
				miReservaIntermedio = miReserva;
				miReservasRepo.save(miReservaIntermedio);
				editarRdo = "La reserva de " + miReservaIntermedio.getEvento().getNombre()+ " con id "
						+ miReservaIntermedio.getIdReserva() + " se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrado el reserva, pero ha habido un error al salvar la eliminación del reserva "
						+ codigoReserva + " en la BBDD";
			}
		}

		return editarRdo;
	}


}
