package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ExistDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class RegistroPeregrinoController implements Initializable{
	
	
	
	@FXML
	private TextField nombreField;
	
	@FXML
	private TextField usuField;
	
	@FXML
	private TextField contraField;
	
	@FXML
	private TextField conf_Contra;
	
	@FXML
	private TextField correo;
	
	@FXML
	private ChoiceBox <String> chbParada;
	
	@FXML
	private ChoiceBox <String> chbPaises;
	
	@FXML
	private Button btnCrear;
	
	//DECLARACION DE SERVICIOS 
	@Autowired
	private PeregrinoService peregrinoService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private CredencialesService credencialeService;
	
	@Autowired
	private ExistDBService existdbService;
	
	
	@FXML
	private void PulsaCrearPeregrino() {
		String nombreCompleto = nombreField.getText();
		String nombreUsuario = usuField.getText();
		String contraUsuario = contraField.getText();
		String contraRep = conf_Contra.getText();
		String correoE = correo.getText();
		String datosParada = chbParada.getValue();
		String nacionalidad = chbPaises.getValue();

		boolean credencialesCorrectas = ValidacionesService.comprobarCredenciales(nombreUsuario, contraUsuario,
				nombreCompleto, "a");

		String[] nombreYRegion = datosParada.split(" ");
		String nombreParada = nombreYRegion[0];
		char regionParada = nombreYRegion[2].charAt(0);

		Parada paradaP = paradaService.findByNameAndRegion(nombreParada, regionParada);

		boolean peregrinoExiste = peregrinoService.existePeregrino(nombreUsuario);
		boolean correoValido = ValidacionesService.validacionCorreo(correoE);

		if (!peregrinoExiste) {

			if (correoValido && correoE != null) {

				if (contraUsuario.equals(contraRep)) {

					if (credencialesCorrectas) {

						if (AlertasServices.altConfirmacion()) {

							Credenciales c = new Credenciales(nombreUsuario, contraUsuario, "peregrino");
							credencialeService.save(c);

							Peregrino p = new Peregrino(nombreUsuario, nacionalidad, nombreCompleto, correoE);
							peregrinoService.save(p);

							p.setCredenciales(c);
							peregrinoService.save(p);

							Date fecha = Date.valueOf(LocalDate.now());

							PeregrinoParada pp = new PeregrinoParada(p, paradaP, fecha);
							p.getPeregrinoParada().add(pp);

							Carnet carnet = new Carnet(fecha, 10.0, 0);

							carnet.setParadaInicial(paradaP);
							carnet.setPeregrino(p);
							p.setCarnet(carnet);

							peregrinoService.save(p);

							AlertasServices.altPeregrinoCreado();
							nombreField.setText(null);
							usuField.setText(null);
							contraField.setText(null);
							conf_Contra.setText(null);
							correo.setText(null);
							
						} else {
							nombreField.setText(null);
							usuField.setText(null);
							contraField.setText(null);
						}

					} else {
						nombreField.setText(null);
						usuField.setText(null);
						contraField.setText(null);
					}
				} else {
					AlertasServices.altContrasNoCoinciden();
					contraField.setText(null);
					conf_Contra.setText(null);
				}

			} else {
				AlertasServices.altCorreoIncorrecto();
				correo.setText(null);
			}
		} else {
			AlertasServices.altUsuarioExiste();
			nombreField.setText(null);
			usuField.setText(null);
			contraField.setText(null);
		}

	}
	
	private void persirtirXML() {

		Collection col = existdbService.getSubColeccion("gigia");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("Peregrino");
			doc.appendChild(root);

			Element nombre = doc.createElement("Nombre");
			nombre.appendChild(doc.createTextNode("Alicia"));
			root.appendChild(nombre);

			Element edad = doc.createElement("Edad");
			edad.appendChild(doc.createTextNode("25"));
			root.appendChild(edad);

			// Guardar en eXistDB
			XMLResource res = (XMLResource) col.createResource("peregrino.xml", "XMLResource");
			res.setContentAsDOM(doc);

			col.storeResource(res);
			System.out.println("XML almacenado correctamente en eXistDB.");

		} catch (ParserConfigurationException | XMLDBException e) {
			e.printStackTrace();
		}

	}
	
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		
		persirtirXML();
		
//		LoginNuevoController.sesion.setNombre("invitado");
//		LoginNuevoController.sesion.setPerfil("invitado");
//		
//		stageManager.switchScene(FxmlView.LOGIN);
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
	
	@FXML
	private Button btnAyuda;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		ValidacionesService.almacenarEnMap();
		HashMap <String, String> map = ValidacionesService.diccionarioPaises;
		
		for (String pais : map.values()) {
			chbPaises.getItems().add(pais);
			
		}
		
		List <Parada> listaParadas = paradaService.findAll();
		
		if (!listaParadas.isEmpty()) {
			Parada pDefault = listaParadas.get(0);
			chbParada.setValue(pDefault.getNombre()+" - "+pDefault.getRegion());
			
			for(Parada parada: listaParadas) {
				chbParada.getItems().add(parada.getNombre()+" - "+parada.getRegion());
			}
		}
		else {
			AlertasServices.altNoParadas();
			btnCrear.setDisable(true);
		}
		
		
		
		chbPaises.setValue(map.get("ES"));
		
		
	}
	
	
}
