package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Credenciales;


@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long>{

	Credenciales findByNombre(String nombre);
}
