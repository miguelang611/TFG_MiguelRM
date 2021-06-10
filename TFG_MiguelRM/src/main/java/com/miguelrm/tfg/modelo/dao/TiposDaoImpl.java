package com.miguelrm.tfg.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.ListaTiposMensaje;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.repository.TiposRepository;

//Indicamos que este DaoImpl es el único que implementará UserService de Spring
@Service
public class TiposDaoImpl implements IntTiposDao {
	

	@Autowired
	TiposRepository miTiposRepo;

	
	@Autowired
	IntEventosDao eventosDao;
	
	//Métodos análogos a los anteriores
	@Override
	public ListaTiposMensaje devuelvePorId(int idTipo) {
		List<Tipo> miListaTipos = new ArrayList<>();

		String mensaje = null;
		Tipo tipo = null;
		boolean BDconexionOK = false;
		try {
			// Hemos definido este método en el TiposRepository,
			// que interpreta a través de JPARepository
			tipo = miTiposRepo.findById(idTipo).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		if (BDconexionOK) {
			if(tipo != null) {
				miListaTipos.add(tipo);
			}else {
				mensaje = "Tipo con ID "+idTipo+" no encontrado";
			}
		}
		ListaTiposMensaje miListaTiposMensaje = new ListaTiposMensaje(miListaTipos,mensaje);
		return miListaTiposMensaje;
	}
	
	@Override
	public ListaTiposMensaje devuelveTipos(String forceActivos) {
		
		List<Tipo> miListaTipos = null;
		List<Tipo> miListaNueva = null;
		String mensaje = null;
		try {
			// Hemos definido este método en el TiposRepository,
			// que interpreta a través de JPARepository
			miListaTipos = miTiposRepo.findAll();
			if(forceActivos.equals("activos") && miListaTipos != null && miListaTipos.size()>0) {
				miListaNueva = miTiposRepo.findByNombreContains("");
				for(int i=0; i<miListaTipos.size(); i++) {
					List<Evento> listaEventos = eventosDao.devuelveByTipo(miListaTipos.get(i).getIdTipo()).getListaEventos();
					if(listaEventos != null && listaEventos.size() >= 0 ) {
						boolean containsActivos = false;
						for(int a=0; a<listaEventos.size(); a++) {
							if(listaEventos.get(a).getEstado().equals("activo")) {
								containsActivos = true;
							}
						}
						if(containsActivos) {
							miListaNueva.add(miListaTipos.get(i));
						}
					}
				}
			}else {
				miListaNueva = miListaTipos;
			}
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaTiposMensaje miListaTiposMensaje = new ListaTiposMensaje(miListaNueva,mensaje);
		return miListaTiposMensaje;
	}
	
	
	/* MÉTODO NUEVO */
	@Override
	public ListaTiposMensaje devuelveByPalabra(String palabra) {
		List<Tipo> miListaTipos = null;
		String mensaje = null;
		try {

			List<Tipo> miListaTipoNombre = miTiposRepo.findByNombreContains(palabra);
			if (miListaTipoNombre != null) {
				List<Tipo> miListaTipoDescripcion = miTiposRepo.findByDescripcionContains(palabra);
				// Si no había encontrado nada por nombre, la lista lo que encuentre por
				// descripción
				if (miListaTipoNombre.size() == 0) {
					System.out.println("AAAAAAAAA");
					miListaTipos = miListaTipoDescripcion;

					// PERO, si la lista obtenida por nombre tiene algún dato,
					// leeremos la lista obtenida por descripción
					// y comprobaremos objeto a objeto contra la otra lista
					// Si no lo contiene, añadimos dicho objeto a la liista original
				} else {
					miListaTipos = miListaTipoNombre;
					for (int i = 0; i < miListaTipoDescripcion.size(); i++) {
						Tipo miTipo = miListaTipoDescripcion.get(i);
						if (!miListaTipos.contains(miTipo)) {
							miListaTipos.add(miTipo);
						}
					}
				}

			}
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
			e.printStackTrace();
		}
		ListaTiposMensaje miListaTiposMensaje = new ListaTiposMensaje(miListaTipos, mensaje);
		return miListaTiposMensaje;
	}
	
	@Override
	public String insertarTipo(Tipo miTipo) {
		String insertarRdo = "";
		try {

			String msgCheckID = devuelvePorId(miTipo.getIdTipo()).getMensaje();
			System.out.println(msgCheckID);

			// Hay un tipo con ese ID
			if (msgCheckID == null) {
				insertarRdo = "No se puede insertar un tipo con dicho id, ya que en la BBDD hay otro tipo con dicho ID. ¡Verifíquelo!";
			}

			// El mensaje no viene vacío pero la lista lo está --> no hay tipos con ese id
			if (msgCheckID != null && !msgCheckID.contains("Error")) {
				List<Tipo> miListaTotal = devuelveTipos("").getListaTipos();
				String mensaje = devuelveTipos("").getMensaje();
				// Si no hay error
				if (mensaje == null) {
					// Y el tipo no está en la lista de tipos
					// (compara por debajo, pero excluye tipo, estado, destcado e id)
					if (!miListaTotal.contains(miTipo)) {
						// Si no esá, guardamos
						miTiposRepo.save(miTipo);
						insertarRdo = "El tipo " + miTipo.getNombre() + " se ha insertado correctamente";
					}
					// En caso contrario, no hacemos la inserción
					else {
						insertarRdo = "No se puede insertar un tipo que ya existe. Si lo desea, puede editarlo";
					}
				} else {
					insertarRdo = mensaje;
				}
			}

			if (msgCheckID != null && msgCheckID.contains("Error")) {
				insertarRdo = msgCheckID;
			}
			
		} catch (Exception e) {
			insertarRdo = "Error al insertar el tipo. Verifique que los datos a insertar son correctos";
			e.printStackTrace();
		}
		
		return insertarRdo;
	}

	
	/* El caso de borrar tipo es más complejo que el de borrar evento, puesto que
	 * para eliminar un tipo, deberemos deshacernos antes de todos los eventos asociados
	 * a dicho tipo:
	 */
	@Override
	public String borrarTipo(int idTipo, boolean forceDelete) {
		
		String mensaje = "Error desconocido";
		
		Tipo miTipoIntermedio = null;
		
		boolean BDconexionOK = false;
		try {
			miTipoIntermedio = miTiposRepo.findById(idTipo).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			mensaje = "Error de conexión a la BBDD";
		}
		
		
		boolean tipoExiste = false;
		if (BDconexionOK) {
			if(miTipoIntermedio != null) {
				tipoExiste = true;
			}else {
				mensaje = "Tipo con ID "+idTipo+" no encontrado";
			}
		}
		
		if(!forceDelete) {
			
		}
		List<Evento> miListaEventos = null;		
		boolean readyDeleteEventos = false;
		boolean readyDeleteTipo = false;
		if(tipoExiste) {
			
			miListaEventos = eventosDao.devuelveByTipo(idTipo).getListaEventos();
			String mensajeEv = eventosDao.devuelveByTipo(idTipo).getMensaje();
			
			//Si la lista está en null, ha habido un error, asignamos mensaje 
			if(miListaEventos == null ) {
				
				mensaje = mensajeEv;
			//Si no es nula y
			}else {
				
				//tiene tamaño de 0, sin mensaje de error
				if( miListaEventos.size() == 0 && mensajeEv == null)
					//Estamos listos para eliminar el tipo directamente
					readyDeleteTipo = true;
				
				//tiene tamaño distinto de 0, sin mensaje de error
				//quiere decir que hay eventos asociados a ese tipo
				else if(miListaEventos.size() != 0 && mensajeEv == null) {
					//Estamos listos para eliminar esos eventos, antes de eliminar
					//el tipo
					readyDeleteEventos = true;
					
				//En otro caso, asignamos el mensaje de error
				}else {
					mensaje = mensajeEv;
				}
				
			}
			
		}
		
		boolean deletedEventos = false;
		//Si viene por aquí quiere decir que la lista tiene al menos 1 evento del tipo,
		//que hay que eliminar previamente
		if(readyDeleteEventos) {
			//Si no se ha forzado la eliminación, avisamos de que no es posible realizarlo
			if(!forceDelete) {
				mensaje = "No se puede eliminar el tipo, ya que tiene eventos asociados. Puede forzar su eliminación forzando la eliminación añadiendo /forceDelete a la url";
			}else {
				//Leemos con un for la lista, y pasamos cada id de evento como parámetro
				//a la función de borrarEvento del EventosDaoImpl
					try {
						for(int i=0; i<miListaEventos.size(); i++) {
							eventosDao.borrarEvento(miListaEventos.get(i).getIdEvento());
						}
						//Si no ha fallado, estamos listos para eliminar el tipo
						readyDeleteTipo = true;
						deletedEventos = true;
					}catch(Exception e){
						mensaje = "Se produjo un error al eliminar los eventos asociados al tipo";
					}
			}

		}
		
		//Por último, una vez comprobado que no tenemos eventos asociados,
		//podemos eleminar el tipo por fin por medio del deleteById definido en el repository
		if(readyDeleteTipo) {
			try {
				miTiposRepo.deleteById(idTipo);
				mensaje = "El tipo "+miTipoIntermedio.getNombre()+" se ha eliminado correctamente";
				if(deletedEventos) {
					mensaje += ", además de eliminar los eventos asociados al mismo";
				}
			} catch (Exception e) {
				mensaje = "Se ha encontrado el tipo, y se ha confirmado que ya no quedan eventos asociados, pero ha habido un error al salvar la eliminación del tipo"+idTipo+" en la BBDD";
			}
		}
		
		return mensaje;
		
	}
	
	
	//Método análogo al de editar de eventos, con la salvedad de que no se
	//requieren verificaciones en el evento modificado del estilo
	//destacado vs activo
	@Override
	public String editarTipo(Tipo miTipo) {
		
		String editarRdo = "Error desconocido";
		
		Tipo miTipoIntermedio = null;
		
		int codigoTipo = 0;
		
		boolean tipoIdCorrecto = false;
		try {
			codigoTipo = miTipo.getIdTipo();
			tipoIdCorrecto = true;
		}catch(Exception e){
			editarRdo = "Error al recuperar el id del tipo a modificar";
		}
		

		boolean BDconexionOK = false;
		if(tipoIdCorrecto) {
		try {
			miTipoIntermedio = miTiposRepo.findById(codigoTipo).orElse(null);
			BDconexionOK = true;
		} catch (Exception e) {
			editarRdo = "Error de conexión a la BBDD";
		}
		}
		
		boolean tipoExiste = false;
		if (BDconexionOK) {
			if(miTipoIntermedio != null) {
				tipoExiste = true;
			}else {
				editarRdo = "Tipo con ID "+codigoTipo+" no encontrado";
			}
		}
		
		
		if(tipoExiste) {
			try {
				miTipoIntermedio = miTipo;
				miTiposRepo.save(miTipoIntermedio);
				editarRdo = "El tipo "+miTipoIntermedio.getNombre()+" con id "+miTipoIntermedio.getIdTipo()+" se ha editado correctamente";
			} catch (Exception e) {
				editarRdo = "Se ha encontrado el tipo, pero ha habido un error al salvar la eliminación del tipo "+codigoTipo+" en la BBDD";
			}
		}
		
		return editarRdo;
	}

	
	
	
}
