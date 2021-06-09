package com.miguelrm.tfg.servicios;

import java.util.List;

import org.springframework.ui.Model;

import com.miguelrm.tfg.modelo.beans.Evento;

public interface IntPreparaServ {
	
	public Model envia (Model model);
	
	public List<Evento> listaLimpia (List<Evento> miListaEventos);

}
