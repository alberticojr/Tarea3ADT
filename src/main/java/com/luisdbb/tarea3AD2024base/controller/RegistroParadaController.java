package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConexiónExistDB;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ExistDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class RegistroParadaController implements Initializable{
	
	
	@FXML
	TextField nombrePaField;
	
	@FXML
	TextField regionPaField;
	
	@FXML
	TextField nombreReField;
	
	@FXML
	TextField contraReField;
	
	@FXML
	private Button btnAyuda;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private CredencialesService credencialeService;
	
	@Autowired
	private ExistDBService existdbService;
	
	@FXML
	private void pulsaCrearParada(ActionEvent event) throws IOException {
		
		String nombreParada = nombrePaField.getText();
		
		String region = regionPaField.getText();
		String nombreResponsable = nombreReField.getText();
		String contraResponsable = contraReField.getText();
		
		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales(nombreResponsable, contraResponsable, nombreParada, region);
		boolean responsableExiste = credencialeService.credencialExiste(nombreResponsable);
		
			if (credencialesCorrectas) {
				
				char regionParada = regionPaField.getText().charAt(0);
				boolean paradaExiste = paradaService.existeParada(nombreParada, regionParada);
				
				if (!paradaExiste) {
					if (!responsableExiste) {
						if (AlertasServices.altConfirmacion()) {
						
						Credenciales cre = new Credenciales(nombreResponsable, contraResponsable, "parada");
						credencialeService.save(cre);

						Parada pa = new Parada(nombreParada, regionParada, nombreResponsable);
						paradaService.save(pa);

						pa.setCredenciales(cre);
						paradaService.save(pa);
						
						AlertasServices.altParadaCreada();
						nombrePaField.setText(null);
						regionPaField.setText(null);
						nombreReField.setText(null);
						contraReField.setText(null);
						}
						else {
							nombrePaField.setText(null);
							regionPaField.setText(null);
							nombreReField.setText(null);
							contraReField.setText(null);
						}
						
					} else {
						AlertasServices.altUsuarioExiste();
						nombreReField.setText(null);
						contraReField.setText(null);
					}
				}
				else {
					AlertasServices.altParadaExiste();
					nombrePaField.setText(null);
					regionPaField.setText(null);
				}
			} else {
					
			}
		
	}
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private void volver(ActionEvent event) throws IOException {
		//stageManager.switchScene(FxmlView.MenuAdministrador);
		try {
			
			existdbService.crearSubColeccion("calvo");
			
			//conexiónExistDB.storeXML();
			//conexiónExistDB.createCollection(nombrePaField.getText());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void pulsaAyuda () {
		try {
			WebView webView = new WebView();
			
			String url = getClass().getResource("/ayuda/MenuPrincipal.html").toExternalForm();
			webView.getEngine().load(url);
			
			Stage helpStage = new Stage();
			helpStage.setTitle("Ayuda");
			
			Scene helpScene = new Scene (webView, 850, 520);
			
			helpStage.setScene(helpScene);
			
			helpStage.initModality(Modality.APPLICATION_MODAL);
			helpStage.setResizable(true);
			helpStage.show();
			
			
		}
		catch (NullPointerException e) {
			System.out.print("No se ha encontrado el HTML");
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
	}

}
