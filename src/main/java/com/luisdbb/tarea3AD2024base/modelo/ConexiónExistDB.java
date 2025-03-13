package com.luisdbb.tarea3AD2024base.modelo;

import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

public class ConexiónExistDB {

	// URI de conexión
	private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas";
	private static String usuario = "admin";
	private static String contra = "admin";
	
	// Driver de la base de datos
	private static String driver = "org.exist.xmldb.DatabaseImpl";

	// Método para obtener la colección de la base de datos
	public static Collection getInstance() {

		// Obtener la colección
		Collection col = null;

		try {

			// Inicializa el driver de la base de datos
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(database);

			try {
				col = DatabaseManager.getCollection(URI, usuario, contra);
				System.out.println("conectado correctamente");

				// si la coleccion no existe la crea
				if (col == null) {

					Collection raiz = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db",
							usuario, contra);

					CollectionManagementService mgtService = (CollectionManagementService) raiz
							.getService("CollectionManagementService", "1.0");
					mgtService.createCollection(URI.substring(URI.lastIndexOf("/") + 1));

					col = DatabaseManager.getCollection(URI, usuario, contra);
					System.out.println("Conexion creada");
				}

			} catch (XMLDBException e) {
				e.printStackTrace();
				//throw new Exception("Error al obtener la colección", e);
			}

		} catch (XMLDBException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		return col;

	}
	
	public static void inicializarDriver () {
		try {
		
		Class<?> cl = Class.forName(driver);
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
		
		} catch (XMLDBException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		
	}
	
	public static void storeXML () throws Exception {
		
		Class<?> cl = Class.forName(driver);
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
		
		Collection col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/Paradas/gigia", usuario, contra);
		
		
		XMLResource res = null;
		
		
		if (col == null)
			System.out.println("la coleccion esta vacia");
		else
			System.out.println("no esta vacia");
		
		File f = new File("src\\main\\resources\\exportaciones\\alicia_peregrino.xml");
		String xmlContent = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
		
		try {
			
			res = (XMLResource) col.createResource(f.getName(), "XMLResource");
			res.setContent(xmlContent);
			
			System.out.print("storing document " + res.getId() + "...");
			
			col.storeResource(res);
			System.out.println("ok.");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	// Método para cerrar la colección de la base de datos
	public static void closeCollection(Collection col) {
		if (col != null) {
			try {
				col.close();
			} catch (XMLDBException e) {
				e.printStackTrace();
			}
		}
	}
}
