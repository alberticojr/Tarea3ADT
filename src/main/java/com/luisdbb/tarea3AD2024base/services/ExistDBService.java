package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Collection;

import com.luisdbb.tarea3AD2024base.repositorios.ExistDBRepository;

@Service
public class ExistDBService {
	
	@Autowired
	private ExistDBRepository existdbRepository;
	
	public void crearSubColeccion (String nombreParada) {
		try {
			existdbRepository.crearSubColeccion(nombreParada);
		} catch (Exception e) {}
	}
	
	public Collection getSubColeccion (String nombreParada) {
		
		return existdbRepository.getSubColeccion(nombreParada);
	}

}
