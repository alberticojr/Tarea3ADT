package com.luisdbb.tarea3AD2024base.services;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertasServices {
	
	public static void altNombreConEspacioONumero() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre no Valido");
        alert.setHeaderText("Nombre no Valido");
        alert.setContentText("NO se pueden escribir ni espacios, ni numeros en el nombre.");
        alert.showAndWait();
    }
	
	public static void altNombreVacio() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre no Valido");
        alert.setHeaderText("Nombre no Valido");
        alert.setContentText("NO se puede dejar el nombre en blanco");
        alert.showAndWait();
    }
	
	public static void altContraConEspacio() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Contraseña no Valida");
        alert.setHeaderText("Contraseña no Valida");
        alert.setContentText("NO se pueden escribir espacios en la contrasenia.");
        alert.showAndWait();
    }
	
	public static void altContraVacia() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Contraseña no Valida");
        alert.setHeaderText("Contraseña no Valida");
        alert.setContentText("NO se puede dejar la contrasenia en blanco.");
        alert.showAndWait();
    }
	
	public static void altUsuarioExiste() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Usuario existente");
        alert.setHeaderText("Usuario existente");
        alert.setContentText("El usuario introducido ya existe");
        alert.showAndWait();
    }
	
	public static void altParadaExiste() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Parada existente");
        alert.setHeaderText("Parada existente");
        alert.setContentText("La parada introducida ya existe");
        alert.showAndWait();
    }
	
	public static void altRegionVacia() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Region incorrecta");
        alert.setHeaderText("Region incorrecta");
        alert.setContentText("La region introducida es incorrecta o vacia");
        alert.showAndWait();
    }
	
	public static void altNombreCompleto() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre completo incorrecto");
        alert.setHeaderText("Nombre completo incorrecto");
        alert.setContentText("El nombre completo es incorrecto o esta vacio");
        alert.showAndWait();
    }
	public static void altNombreCompletoConNumeros() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nombre completo incorrecto");
        alert.setHeaderText("Nombre completo incorrecto");
        alert.setContentText("No se pueden introducir numeros en el nombre completo");
        alert.showAndWait();
    }
	
	public static void altNoParadas() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No hay paradas disponibles");
        alert.setHeaderText("No hay paradas disponibles");
        alert.setContentText("No hay paradas disponibles en el sistema para registrarse");
        alert.showAndWait();
    }
	
	public static void altNoPeregrinos() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No hay peregrinos disponibles");
        alert.setHeaderText("No hay peregrinos disponibles");
        alert.setContentText("No hay peregrinos disponibles en el sistema para sellar");
        alert.showAndWait();
    }
	public static void altSellarInvalido() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Sellado invalido");
        alert.setHeaderText("Ya ha sellado en esta parada");
        alert.setContentText("No se puede sellar un carnet dos veces el mismo dia");
        alert.showAndWait();
    }
	public static void altPeregrinoCreado() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Peregrino creado");
        alert.setHeaderText("Peregrino creado");
        alert.setContentText("El peregrino se ha creado correctamente");
        alert.showAndWait();
    }
	
	public static void altParadaCreada() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Parada creada");
        alert.setHeaderText("Parada creada");
        alert.setContentText("La parada se ha creado correctamente");
        alert.showAndWait();
    }
	public static void altExportacionCorrecta() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exportación completada");
        alert.setHeaderText("Exportación completada");
        alert.setContentText("El carnet del peregrino se ha exportado correctamente");
        alert.showAndWait();
    }
	public static void altSelladoCorrecto() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sellado correcto");
        alert.setHeaderText("sellado correcto");
        alert.setContentText("Se ha sellado el carnet del peregrino correctamente");
        alert.showAndWait();
    }
	
	public static void altFechasAlReves() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Fechas incorrectas");
        alert.setHeaderText("Fechas incorrectas");
        alert.setContentText("Has introducido las fechas en orden incorrecto");
        alert.showAndWait();
    }
	
	public static void altFechasIguales() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Fechas iguales");
        alert.setHeaderText("Fechas iguales");
        alert.setContentText("Has introducido la misma fecha");
        alert.showAndWait();
    }
	public static void altUsuarioNoElegido() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Selecciona un usuario");
        alert.setHeaderText("Selecciona un usuario");
        alert.setContentText("No has seleccionado ningun usuario");
        alert.showAndWait();
    }
	
	public static void altCorreoIncorrecto() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Correo invalido");
        alert.setHeaderText("Correo invalido");
        alert.setContentText("El correo introducido es invalido");
        alert.showAndWait();
    }
	
	public static void altContrasNoCoinciden() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Cotraseñas invalidas");
        alert.setHeaderText("Contraseñas invalidas");
        alert.setContentText("Las contraseñas introducidas no coniciden");
        alert.showAndWait();
    }
	public static void altPeregrinoEditado() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edicion correcta");
        alert.setHeaderText("Edicion correcta");
        alert.setContentText("El peregrino se ha editado correctamente");
        alert.showAndWait();
    }
	
	public static boolean altConfirmacion() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar datos");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("¿Esta satisfecho con los datos introducidos?");

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

}
