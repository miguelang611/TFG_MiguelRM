
package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Reserva;
import com.miguelrm.tfg.modelo.beans.ListaUsuariosMensaje;
import com.miguelrm.tfg.modelo.beans.Usuario;
import com.miguelrm.tfg.modelo.repository.UsuariosRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class UsuariosDaoImpl implements IntUsuariosDao {
	


	@Autowired
	UsuariosRepository miUsuariosRepo;

	
	@Autowired
	IntReservasDao reservasDao;
	
	//Métodos análogos a los anteriores
	@Override
	public ListaUsuariosMensaje devuelvePorEmail(String miEmail) {
		List<Usuario> miListaUsuarios = new ArrayList<>();

		String mensaje = null;
		Usuario usuario = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el UsuariosRepository,
			// que interpreta a través de JPARepository
			usuario = miUsuariosRepo.findByEmail(miEmail);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();

		}
		
		if (BDconexionOK) {
			if(usuario != null) {
				miListaUsuarios.add(usuario);
			}else {
				mensaje = "Aviso --> Usuario con Email "+miEmail+" no encontrado";
			}
		}
		ListaUsuariosMensaje miListaUsuariosMensaje = new ListaUsuariosMensaje(miListaUsuarios,mensaje);
		return miListaUsuariosMensaje;
	}
	
	@Override
	public ListaUsuariosMensaje devuelveUsuariosActivos() {
		List<Usuario> miListaUsuarios = null;
		String mensaje = null;
		try {
			// Hemos definido este método en el UsuariosRepository,
			// que interpreta a través de JPARepository
			miListaUsuarios = miUsuariosRepo.findByEnabled(1);
		} catch (Exception e) {
			mensaje = "Error -> fallo de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaUsuariosMensaje miListaUsuariosMensaje = new ListaUsuariosMensaje(miListaUsuarios, mensaje);
		return miListaUsuariosMensaje;
	}
	
	@Override
	public ListaUsuariosMensaje devuelveUsuarios() {
		
		List<Usuario> miListaUsuarios = null;
		List<Usuario> miListaNueva = null;
		String mensaje = null;
		try {
			miListaUsuarios = miUsuariosRepo.findAll();
			miListaNueva = miListaUsuarios;

		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaUsuariosMensaje miListaUsuariosMensaje = new ListaUsuariosMensaje(miListaNueva,mensaje);
		return miListaUsuariosMensaje;
	}
	
	
	/* MÉTODO NUEVO */
	@Override
	public ListaUsuariosMensaje devuelveByPalabra(String palabra) {
		List<Usuario> miListaUsuarios = null;
		String mensaje = null;
		try {

			List<Usuario> miListaUsuarioEmail = miUsuariosRepo.findByEmailContains(palabra);
			if (miListaUsuarioEmail != null) {
				List<Usuario> miListaUsuarioNombre = miUsuariosRepo.findByNombreContains(palabra);
				// Si no había encontrado nada por nombre, la lista lo que encuentre por
				// descripción
				if (miListaUsuarioEmail.size() == 0) {
					miListaUsuarios = miListaUsuarioNombre;

					// PERO, si la lista obtenida por nombre tiene algún dato,
					// leeremos la lista obtenida por descripción
					// y comprobaremos objeto a objeto contra la otra lista
					// Si no lo contiene, añadimos dicho objeto a la liista original
				} else {
					miListaUsuarios = miListaUsuarioEmail;
					for (int i = 0; i < miListaUsuarioNombre.size(); i++) {
						Usuario miUsuario = miListaUsuarioNombre.get(i);
						if (!miListaUsuarios.contains(miUsuario)) {
							miListaUsuarios.add(miUsuario);
						}
					}
				}

			}
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaUsuariosMensaje miListaUsuariosMensaje = new ListaUsuariosMensaje(miListaUsuarios, mensaje);
		return miListaUsuariosMensaje;
	}
	
	@Override
	public String insertarUsuario(Usuario miUsuario) {
		String insertarRdo = "";
		try {

			String msgCheckEmail = devuelvePorEmail(miUsuario.getEmail()).getMensaje();
			System.out.println("BBBBBB"+msgCheckEmail);

			// Hay un usuario con ese Email
			if (msgCheckEmail == null) {
				insertarRdo = "Aviso --> No se puede insertar un usuario con dicho email, ya que en la BBDD hay otro usuario con dicho Email. ¡Verifíquelo!";
			}

			// El mensaje no viene vacío pero la lista lo está --> no hay usuarios con ese email
			if (msgCheckEmail != null && !msgCheckEmail.contains("Error")) {
				List<Usuario> miListaTotal = devuelveUsuarios().getListaUsuarios();
				String mensaje = devuelveUsuarios().getMensaje();
				// Si no hay error
				if (mensaje == null) {
					// Y el usuario no está en la lista de usuarios
					// (compara por debajo, pero excluye usuario, estado, destcado e email)
					if (!miListaTotal.contains(miUsuario)) {
						// Si no esá, guardamos
						miUsuariosRepo.save(miUsuario);
						insertarRdo = "El usuario " + miUsuario.getEmail() + " se ha insertado correctamente";
					}
					// En caso contrario, no hacemos la inserción
					else {
						insertarRdo = "Aviso --> No se puede insertar un usuario que ya existe. Si lo desea, puede editarlo";
					}
				} else {
					insertarRdo = mensaje;
				}
			}

			if (msgCheckEmail != null && msgCheckEmail.contains("Error")) {
				insertarRdo = msgCheckEmail;
			}
			
		} catch (Exception e) {
			System.out.println(miUsuario);
			insertarRdo = "Aviso --> Verifique que los datos a insertar son correctos";
			e.printStackTrace();
		}
		
		return insertarRdo;
	}

	
	/* El caso de borrar usuario es más complejo que el de borrar Reserva o noticia puesto que
	 * para eliminar un usuario, deberemos deshacernos antes de todos los Reservas asociados
	 * a dicho usuario:
	 */
	@Override
	public String borrarUsuario(String miEmail, boolean forceDelete) {
		
		String mensaje = "Error desconocido";
		
		Usuario miUsuarioIntermedio = null;
		
		boolean BDconexionOK = false;
		try {
			miUsuarioIntermedio = miUsuariosRepo.findByEmail(miEmail);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		
		boolean usuarioExiste = false;
		if (BDconexionOK) {
			if(miUsuarioIntermedio != null) {
				usuarioExiste = true;
			}else {
				mensaje = "Aviso --> Usuario con Email "+miEmail+" no encontrado";
			}
		}
		
		if(!forceDelete) {
			
		}
		List<Reserva> miListaReservas = null;		
		boolean readyDeleteReservas = false;
		boolean readyDeleteUsuario = false;
		if(usuarioExiste) {
			
			miListaReservas = reservasDao.devuelveByEmail(miEmail).getListaReservas();
			String mensajeEv = reservasDao.devuelveByEmail(miEmail).getMensaje();
			
			//Si la lista está en null, ha habido un error, asignamos mensaje 
			if(miListaReservas == null ) {
				
				mensaje = mensajeEv;
			//Si no es nula y
			}else {
				
				//tiene tamaño de 0, sin mensaje de error
				if( miListaReservas.size() == 0 && mensajeEv == null)
					//Estamos listos para eliminar el usuario directamente
					readyDeleteUsuario = true;
				
				//tiene tamaño distinto de 0, sin mensaje de error
				//quiere decir que hay Reservas asociados a ese usuario
				else if(miListaReservas.size() != 0 && mensajeEv == null) {
					//Estamos listos para eliminar esos Reservas, antes de eliminar
					//el usuario
					readyDeleteReservas = true;
					
				//En otro caso, asignamos el mensaje de error
				}else {
					mensaje = mensajeEv;
				}
				
			}
			
		}
		
		boolean deletedReservas = false;
		//Si viene por aquí quiere decir que la lista tiene al menos 1 Reserva del usuario,
		//que hay que eliminar previamente
		if(readyDeleteReservas) {
			//Si no se ha forzado la eliminación, avisamos de que no es posible realizarlo
			if(!forceDelete) {
				mensaje = "No se puede eliminar el usuario, ya que tiene Reservas asociados. Puede forzar su eliminación forzando la eliminación añadiendo /forceDelete a la url";
			}else {
				//Leemos con un for la lista, y pasamos cada email de Reserva como parámetro
				//a la función de borrarReserva del ReservasDaoImpl
					try {
						for(int i=0; i<miListaReservas.size(); i++) {
							reservasDao.borrarReserva(miListaReservas.get(i).getIdReserva());
						}
						//Si no ha fallado, estamos listos para eliminar el usuario
						readyDeleteUsuario = true;
						deletedReservas = true;
					}catch(Exception e){
						mensaje = "Se produjo un error al eliminar los Reservas asociados al usuario";
					}
			}

		}
		
		//Por último, una vez comprobado que no tenemos Reservas asociados,
		//podemos eleminar el usuario por fin por medio del deleteByEmail definido en el repository
		if(readyDeleteUsuario) {
			try {
				miUsuariosRepo.deleteByEmail(miEmail);
				mensaje = "El usuario "+miUsuarioIntermedio.getEmail()+" se ha eliminado correctamente";
				if(deletedReservas) {
					mensaje += ", además de eliminar los Reservas asociados al mismo";
				}
			} catch (Exception e) {
				mensaje = "Aviso --> Se ha encontrado el usuario, y se ha confirmado que ya no quedan Reservas asociados, pero ha habido un error al salvar la eliminación del usuario"+miEmail+" en la BBDD";
			}
		}
		
		return mensaje;
		
	}
	
	
	//Método análogo al de editar de Reservas, con la salvedad de que no se
	//requieren verificaciones en el Reserva modificado del estilo
	//destacado vs activo
	@Override
	public String editarUsuario(Usuario miUsuario) {
		
		String editarRdo = "Error desconocido";
		
		Usuario miUsuarioIntermedio = null;
		
		String email = "";
		
		boolean usuarioIdCorrecto = false;
		try {
			email = miUsuario.getEmail();
			usuarioIdCorrecto = true;
		}catch(Exception e){
			editarRdo = "Error al recuperar el email del usuario a modificar";
		}
		

		boolean BDconexionOK = false;
		if(usuarioIdCorrecto) {
		try {
			miUsuarioIntermedio = miUsuariosRepo.findByEmail(email);
			BDconexionOK = true;
		} catch (Exception e) {
			editarRdo = "Error de conexión a la BBDD";
		}
		}
		
		boolean usuarioExiste = false;
		if (BDconexionOK) {
			if(miUsuarioIntermedio != null) {
				usuarioExiste = true;
			}else {
				editarRdo = "Aviso --> Usuario con Email "+email+" no encontrado";
			}
		}
		
		
		if(usuarioExiste) {
			try {
				miUsuarioIntermedio = miUsuario;
				miUsuariosRepo.save(miUsuarioIntermedio);
				editarRdo = "El usuario "+miUsuarioIntermedio.getEmail()+" con email "+miUsuarioIntermedio.getEmail()+" se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Aviso --> Se ha encontrado el usuario, pero ha habido un error al salvar la eliminación del usuario "+email+" en la BBDD";
			}
		}
		
		return editarRdo;
	}

	
	
	
	
}