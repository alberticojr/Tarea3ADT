package com.luisdbb.tarea3AD2024base.repositorios;


import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import com.luisdbb.tarea3AD2024base.modelo.ConexiónExistDB;


@Repository
public class ExistDBRepository {

	Collection coleccionPrincipal = ConexiónExistDB.getInstance();

	public void crearSubColeccion(String collectionName) throws Exception {

		// Obtener la colección principal (base de datos) para crear subcolecciones
		Collection col = coleccionPrincipal;

		try {

			if (col == null) {
				throw new Exception("No se pudo conectar a la base de datos");
			}

			CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			mgtService.createCollection(collectionName);
			System.out.println("subConexion Creada");

		} catch (XMLDBException e) {
			e.printStackTrace();
			throw new Exception("Error al crear la colección", e);
		}

	}
	
	public Collection getSubColeccion(String nombreParada) {
		
		ConexiónExistDB.inicializarDriver();
		

			String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas/"+nombreParada;
			String usuario = "admin";
			String contra = "admin";

			Collection col = null;
			try {
				col = DatabaseManager.getCollection(uri, usuario, contra);
				
				
				if (col == null) {
					System.out.println("no se encontro la coleccion");
				}
				else { System.out.println("conectado a la subColeccion"); }
			}
			catch (XMLDBException e) {e.printStackTrace();}
			
			return col;

		}

	}

