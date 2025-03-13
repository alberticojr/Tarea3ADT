package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class MenuResponsableController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private Label lblId;
	
	@FXML
	private Label lblNombreUsu;
	
	@FXML
	private Label lblNombrePar;
	
	@FXML
	private Label lblRegion;
	
	@FXML
	private Button btnAyuda;
	
	@Autowired
	private ParadaService paradaService;
	
	Parada par;
	
	@FXML
	private void pulsaExportar (ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ExportarDatosParada);
	}
	
	@FXML
	private void pulsaSellar (ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SellarCarnet);
	}
	
	@FXML
	private void PulsaVerLista (ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ListarEnvios);
	}
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		
		LoginNuevoController.sesion.setNombre("invitado");
		LoginNuevoController.sesion.setPerfil("invitado");
		
		stageManager.switchScene(FxmlView.LOGIN);
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
		
		par = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		lblId.setText(par.getId()+"");
		lblNombreUsu.setText(par.getResponsable());
		lblNombrePar.setText(par.getNombre());
		lblRegion.setText(par.getRegion()+"");
	}
}
