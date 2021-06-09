package com.miguelrm.tfg.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.miguelrm.tfg.modelo.beans.Categoria;
import com.miguelrm.tfg.modelo.beans.Evento;
import com.miguelrm.tfg.modelo.beans.Tipo;
import com.miguelrm.tfg.modelo.dao.IntCategoriasDao;
import com.miguelrm.tfg.modelo.dao.IntEventosDao;
import com.miguelrm.tfg.modelo.dao.IntNoticiasDao;
import com.miguelrm.tfg.modelo.dao.IntTiposDao;

@Service("preparaWeb")

public class PreparaServImpl implements IntPreparaServ{

	
	//Tenemos eventosDao y tiposDao, ya que haremos uso de ambos,
	//además de anotar el correspondiente Autowired
	@Autowired
	IntEventosDao eventosDao;

	@Autowired
	IntTiposDao tiposDao;
	
	@Autowired
	IntNoticiasDao noticiasDao;

	@Autowired
	IntCategoriasDao categoriasDao;
	

	/*
	 * ============================= CLASE ENVIA ============================= 
	 * 
	 * Tenemos un clase que envía las listas de tipos y categorías actualizadas en todo momento,
	 * filtrando además por tipos con eventos solo activos (versión pública) y todos (versión
	 * para admin)
	 * 
	 * 
	 * =========================================================================================== 
	 */

	@Override
	public Model envia (Model model){
		
		model = enviaListaTiposReducida(model);
		
		model = enviaListaTiposFull(model);
		
		model = enviaListaCategorias(model);
		
		return model;
			
	}
		
	@Override
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
	

	private Model enviaListaTiposReducida(Model model) {
		
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
		model.addAttribute("listaTiposFull", listaTipos);
		model.addAttribute("mensajeErrorGenerico",mensaje);
		
		return model;
		
	}
	
	private Model enviaListaTiposFull(Model model) {
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
		model.addAttribute("mensajeError",mensajeTipos);
		
		return model;

	}
	
	private Model enviaListaCategorias(Model model) {
		
		String mensaje = "";
		List<Categoria> listaCategorias = categoriasDao.devuelveCategorias("").getListaCategorias();
		String mensajeCategorias = categoriasDao.devuelveCategorias("").getMensaje();

		if (listaCategorias != null) {
			if (listaCategorias.size() == 0 && mensajeCategorias == null) {
				mensajeCategorias = "No se han encontrado categorias en la Base de Datos";
			}
		}

		if (mensajeCategorias != null) {
			mensaje = "Error con los categorias de noticias: " + mensajeCategorias;
		}
		
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("mensajeErrorGenerico",mensaje);
			
		return model;
		
	}
	 

		

}
