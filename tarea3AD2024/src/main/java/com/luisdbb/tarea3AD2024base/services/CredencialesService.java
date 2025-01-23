package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;

@Service
public class CredencialesService {
	
	@Autowired
	private CredencialesRepository credencialesRepository;
	
	public Credenciales save(Credenciales entity) {
		return credencialesRepository.save(entity);
	}

	public Credenciales update(Credenciales entity) {
		return credencialesRepository.save(entity);
	}

	public void delete(Credenciales entity) {
		credencialesRepository.delete(entity);
	}

	public void delete(Long id) {
		credencialesRepository.deleteById(id);
	}

	public Credenciales find(Long id) {
		return credencialesRepository.findById(id).get();
	}

	public List<Credenciales> findAll() {
		return credencialesRepository.findAll();
	}
	
	public boolean authenticate(String username, String password) {
		Credenciales credencial = credencialesRepository.findByNombre(username);
		if (credencial == null) {
			return false;
		} else {
			if (password.equals(credencial.getContra()))
				return true;
			else
				return false;
		}
	}
}
