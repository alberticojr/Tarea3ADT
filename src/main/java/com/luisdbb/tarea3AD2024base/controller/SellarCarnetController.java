package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConexionObjectDB;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.services.AlertasServices;
import com.luisdbb.tarea3AD2024base.services.DB4OService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ObjectDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class SellarCarnetController implements Initializable{
	
	//SERVICIOS	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
    private PeregrinoService peregrinoService;
	
	@Autowired
    private ParadaService paradaService;
	
	@Autowired
    private PeregrinoParadaService peregrinoParadaService;
	
	@Autowired
    private EstanciaService estanciaService;
	
	@Autowired
	private DB4OService db4oService;
	
	@Autowired
	private ObjectDBService objectDBService;
	
	
	//OBJETOS BASICOS
	@FXML
	private ComboBox<String> coBoxPeregrino;
	
	@FXML
	private CheckBox chBoxEstancia;
	
	@FXML
	private CheckBox chBoxVip;
	
	@FXML
	private Button btnSellar;
	
	//OBJETOS DE CONTRATAR SERVICIOS
	@FXML
	private CheckBox chBoxServicio;
	
	@FXML
	private ComboBox<String> coBoxTipoPago;
	
	@FXML
	private TextArea txtAreaExtra;
	
	//tabla servicios
	@FXML
	private TableView<Servicio> tableServicios;
	
	@FXML
	private TableColumn<Servicio, String> columnNombre;
	
	@FXML
	private TableColumn <Servicio, Double> columnPrecio;
	
	
	//OBJETOS DE ENVIO A CASA
	@FXML
	private TextField fieldDireccion;
	
	@FXML
	private TextField fieldLocalidad;
	
	@FXML
	private TextField fieldPeso;
	
	@FXML
	private TextField fieldVolumenX;
	
	@FXML
	private TextField fieldVolumenY;
	
	@FXML
	private TextField fieldVolumenZ;
	
	@FXML
	private CheckBox cheBoxUrgente;
	
	
	
	@FXML
	private Button btnAyuda;
	
	//VARIABLES
	Parada parada;
	private List <Servicio> listaServicios;
	private ObservableList<Long> selectedIds = FXCollections.observableArrayList();
	private double precioTotal = 0.0;
	boolean EnvioACasa = false;
	
	@FXML
	private void pulsaSellar () {
		
		List <PeregrinoParada> listaPP = peregrinoParadaService.listaParadasPorFecha(Date.valueOf(LocalDate.now()));
		
		//SELLAR CON ESTANCIA - VIP - SERVICIO - ENVIO A CASA
		if (coBoxPeregrino != null && chBoxEstancia.isSelected() && chBoxVip.isSelected() && chBoxServicio.isSelected() && EnvioACasa) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			Estancia estancia = new Estancia(parada.getNombre(),Date.valueOf(LocalDate.now()), true, peregrino, parada);
			
			peregrino.getPeregrinoParada().add(PP);
			peregrino.getListaEstancia().add(estancia);
			
			double distActual = peregrino.getCarnet().getDistancia();
			int vipstActual = peregrino.getCarnet().getNvips();
			
			peregrino.getCarnet().setDistancia(distActual+10.0);
			peregrino.getCarnet().setNvips(vipstActual+1);
			
			
			if (selectedIds.size() == 0) {
				System.out.println("No has escogido ningun servicio");
			} else {
				peregrinoService.save(peregrino);

				crearConjuntoContratado(estanciaService.findLastId());
				crearEnvioACasa();
			}
			
			}
			else {
				AlertasServices.altSellarInvalido();
			}
		}
		
		//SELLAR CON ESTANCIA - SERVICIO - ENVIO A CASA
		else if (coBoxPeregrino != null && chBoxEstancia.isSelected() && chBoxServicio.isSelected() && EnvioACasa) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			Estancia estancia = new Estancia(parada.getNombre(),Date.valueOf(LocalDate.now()), false, peregrino, parada);
			
			
			peregrino.getPeregrinoParada().add(PP);
			peregrino.getListaEstancia().add(estancia);
			
			double distActual = peregrino.getCarnet().getDistancia();
			peregrino.getCarnet().setDistancia(distActual+10.0);
			
			if (selectedIds.size() == 0) {
				System.out.println("No has escogido ningun servicio");
			} else {
				peregrinoService.save(peregrino);

				crearConjuntoContratado(estanciaService.findLastId());
				crearEnvioACasa();
			}
			
			}
			else {
				AlertasServices.altSellarInvalido();
			}

		}
		//SELLAR CON ESTANCIA - SERVICIO
		else if (coBoxPeregrino != null && chBoxEstancia.isSelected() && chBoxServicio.isSelected()) {

			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];

			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);

			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();

			for (PeregrinoParada PP : listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId()
						&& PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}

			if (!selloRepetido) {
				PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
				Estancia estancia = new Estancia(parada.getNombre(), Date.valueOf(LocalDate.now()), false, peregrino,
						parada);

				peregrino.getPeregrinoParada().add(PP);
				peregrino.getListaEstancia().add(estancia);

				double distActual = peregrino.getCarnet().getDistancia();
				peregrino.getCarnet().setDistancia(distActual + 10.0);

				if (selectedIds.size() == 0) {
					System.out.println("No has escogido ningun servicio");
				} else {
					peregrinoService.save(peregrino);

					crearConjuntoContratado(estanciaService.findLastId());
				}

			} else {
				AlertasServices.altSellarInvalido();
			}

		}
		
		//SELLAR CON ESTANCIA - VIP
		else if (coBoxPeregrino != null && chBoxEstancia.isSelected() && chBoxVip.isSelected()) {

			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];

			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);

			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();

			for (PeregrinoParada PP : listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId()
						&& PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}

			if (!selloRepetido) {
				PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
				Estancia estancia = new Estancia(parada.getNombre(), Date.valueOf(LocalDate.now()), true, peregrino,
						parada);

				peregrino.getPeregrinoParada().add(PP);
				peregrino.getListaEstancia().add(estancia);

				double distActual = peregrino.getCarnet().getDistancia();
				peregrino.getCarnet().setDistancia(distActual + 10.0);

				peregrinoService.save(peregrino);
				

			} else {
				AlertasServices.altSellarInvalido();
			}

		}
		
		//SELLAR CON ESTANCIA
		else if (coBoxPeregrino != null && chBoxEstancia.isSelected()) {

			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];

			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);

			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();

			for (PeregrinoParada PP : listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId()
						&& PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}

			if (!selloRepetido) {
				PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
				Estancia estancia = new Estancia(parada.getNombre(), Date.valueOf(LocalDate.now()), false, peregrino,
						parada);

				peregrino.getPeregrinoParada().add(PP);
				peregrino.getListaEstancia().add(estancia);

				double distActual = peregrino.getCarnet().getDistancia();
				peregrino.getCarnet().setDistancia(distActual + 10.0);

				peregrinoService.save(peregrino);
				

			} else {
				AlertasServices.altSellarInvalido();
			}

		}
		//SOLO SELLADO DEL PEREGRINO
		else if (coBoxPeregrino != null) {
			
			String[] datosPeregrino = coBoxPeregrino.getValue().split(" ");
			String nombrePeregrino = datosPeregrino[2];
			
			Peregrino peregrino = peregrinoService.findByNombre(nombrePeregrino);
			
			boolean selloRepetido = false;
			LocalDate fechaActual = LocalDate.now();
			
			for (PeregrinoParada PP: listaPP) {
				LocalDate fechaPP = PP.getFecha().toLocalDate();
				if (fechaPP.isEqual(fechaActual) && PP.getPeregrino().getId() == peregrino.getId() && PP.getParada().getId() == parada.getId()) {
					selloRepetido = true;
				}
			}
			
			if (!selloRepetido) {
			PeregrinoParada PP = new PeregrinoParada(peregrino, parada, Date.valueOf(LocalDate.now()));
			peregrino.getPeregrinoParada().add(PP);
			
			peregrinoService.save(peregrino);
			
			AlertasServices.altSelladoCorrecto();
			
			}
			else {
				AlertasServices.altSellarInvalido();
			}
		}
		else {
			AlertasServices.altNoPeregrinos();
		}
	}
	
	
	public void crearConjuntoContratado (long idEstancia) {
		String[] metodoPago = coBoxTipoPago.getValue().split(" ");
		System.out.println(metodoPago[0]);
		
		String extra = txtAreaExtra.getText();
		
		long id = db4oService.findConjuntoContratadoLastId();
		
		ConjuntoContratado conjuntoContratado = new ConjuntoContratado();
		conjuntoContratado.setId(id);
		conjuntoContratado.setModoPago(metodoPago[0].charAt(0));
		conjuntoContratado.setExtra(extra);
		
		List <Long> ids = new ArrayList <>();
		ids.addAll(selectedIds);
		
		
		conjuntoContratado.setIdListaServicios(ids);
		conjuntoContratado.setIdEstancia(idEstancia);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		conjuntoContratado.setPrecioTotal(Double.parseDouble(df.format(precioTotal).replace(",", ".")));
		
		db4oService.crearConjuntoContratado(conjuntoContratado);
		System.out.println(conjuntoContratado.toString());
		
	}
	
	EntityManager conexionOBD = ConexionObjectDB.getInstance();
	
	public void crearEnvioACasa () {
		
		String direccion = fieldDireccion.getText();
		String localidad = fieldLocalidad.getText();
		boolean urgente = cheBoxUrgente.isSelected();
		
		
		boolean pesoValido =  fieldPeso.getText().matches("^\\d+\\.\\d{2}$");
		boolean volumenXvalido = fieldVolumenX.getText().matches("^\\d+$");
		boolean volumenYvalido = fieldVolumenY.getText().matches("^\\d+$");
		boolean volumenZvalido = fieldVolumenZ.getText().matches("^\\d+$");
		
		if (pesoValido && volumenXvalido && volumenYvalido && volumenZvalido) {

			double doublePeso = Double.parseDouble(fieldPeso.getText());
			DecimalFormat df = new DecimalFormat("#.##");
			double peso = Double.parseDouble(df.format(doublePeso).replace(",", "."));

			int volumenX = Integer.parseInt(fieldVolumenX.getText());
			int volumenY = Integer.parseInt(fieldVolumenY.getText());
			int volumenZ = Integer.parseInt(fieldVolumenZ.getText());
			int[] volumen = new int[] { volumenX, volumenY, volumenZ };

			if (direccion.length() != 0 && localidad.length() != 0) {
				
				boolean localidadValida = localidad.matches("^[a-zA-Z\\s]+$");
				
				if (localidadValida) {
					
					EnvioACasa envio = new EnvioACasa();
					envio.setPeso(peso);
					envio.setVolumen(volumen);
					envio.setUrgente(urgente);
					envio.setIdParada(parada.getId());

					Direccion direccionG = new Direccion(direccion, localidad);
					envio.setDireccion(direccionG);

					objectDBService.crearEnvioACasa(envio);
					
				}
				else {
					System.out.println("La localidad solo puede contener letras y espacios");
				}
				
			}
			else {
				System.out.println("Direccion o localidad vacios");
			}

		} else {
			System.out.println("numeros no validos");
		}
		
		
		
		
	}
	
	
	@FXML
	private void pulsaCancelar() {
		stageManager.switchScene(FxmlView.MenuResponsable);
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
		
		listaServicios = db4oService.listaServicios();
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		parada = paradaService.findByResponsable(LoginNuevoController.sesion.getNombre());
		
		
		
		List<Peregrino> listaPeregrinos = peregrinoService.findAll();

		if (!listaPeregrinos.isEmpty()) {
			Peregrino peregrinoDefault = listaPeregrinos.get(0);
			coBoxPeregrino.setValue(peregrinoDefault.getId() + " | " + peregrinoDefault.getNombre() + " | " + peregrinoDefault.getNacionalidad());
			
			for (Peregrino p : listaPeregrinos) {
				coBoxPeregrino.getItems().add(p.getId() + " | " + p.getNombre() + " | " + p.getNacionalidad());
			}
			
		} else {
			AlertasServices.altNoPeregrinos();
			btnSellar.setDisable(true);
		}
		
		chBoxEstancia.selectedProperty().addListener((observable, oldValue, newValue) -> {
			
			chBoxVip.setDisable(!newValue);
		    chBoxServicio.setDisable(!newValue);
			
	        if (!newValue) {
	        	chBoxVip.setSelected(false);
	        	chBoxServicio.setSelected(false);
	        	}
	    });
		
		chBoxServicio.selectedProperty().addListener((observable, oldValue, newValue) -> {
			
			coBoxTipoPago.setDisable(!newValue);
	        txtAreaExtra.setDisable(!newValue);
	        tableServicios.setDisable(!newValue);
	        
	        if (!newValue) {
	        	EnvioACasa = false;
	        	fieldDireccion.setText("");
				fieldLocalidad.setText("");
				fieldPeso.setText("");
				fieldVolumenX.setText("");
				fieldVolumenY.setText("");
				fieldVolumenZ.setText("");
				cheBoxUrgente.setSelected(false);
				
				desactivacionComponentesEnvio(true);
	        }
	        
	    });
		
		
		coBoxTipoPago.getItems().addAll("E - Efectivo", "T - Tarjeta", "B - Bizum");
		coBoxTipoPago.setValue("E - Efectivo");

		activacionComponentes(true);
		
		ObservableList<Servicio> ObservableListaServicios = FXCollections.observableArrayList();
		
		for (Servicio s : listaServicios) {
			
			for (Long idParadaServicio: s.getIdListaParadas()) {
				if (idParadaServicio == parada.getId()) {
					ObservableListaServicios.add(s);
				}
			}

			
		
		}
		
		tableServicios.setItems(ObservableListaServicios);
		
		columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		
		tableServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		tableServicios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Servicio>) change -> {

			selectedIds.clear();
			precioTotal = 0.0;
			
			desactivacionComponentesEnvio(true);

			for (Servicio servicioSeleccionado : tableServicios.getSelectionModel().getSelectedItems()) {
				selectedIds.add(servicioSeleccionado.getId());
				precioTotal += servicioSeleccionado.getPrecio();

				if (servicioSeleccionado.getNombre().equals("Envio a casa")) {
					desactivacionComponentesEnvio(false);
					EnvioACasa = true;
				}

			}
			if (!EnvioACasa) {
				fieldDireccion.setText("");
				fieldLocalidad.setText("");
				fieldPeso.setText("");
				fieldVolumenX.setText("");
				fieldVolumenY.setText("");
				fieldVolumenZ.setText("");
				cheBoxUrgente.setSelected(false);
			}

			DecimalFormat df = new DecimalFormat("#.##");

			System.out.println(
					"IDs seleccionados: " + selectedIds + "| total: " + df.format(precioTotal).replace(",", "."));
		});
		
		
	}
	
	public void activacionComponentes (boolean activacion) {
		chBoxVip.setDisable(activacion);
	    chBoxServicio.setDisable(activacion);
        coBoxTipoPago.setDisable(activacion);
        txtAreaExtra.setDisable(activacion);
        tableServicios.setDisable(activacion);
        fieldDireccion.setDisable(activacion);
        fieldLocalidad.setDisable(activacion);
        fieldPeso.setDisable(activacion);
        fieldVolumenX.setDisable(activacion);
        fieldVolumenY.setDisable(activacion);
        fieldVolumenZ.setDisable(activacion);
        cheBoxUrgente.setDisable(activacion);
	}
	
	public void desactivacionComponentesEnvio (boolean activacion) {
        fieldDireccion.setDisable(activacion);
        fieldLocalidad.setDisable(activacion);
        fieldPeso.setDisable(activacion);
        fieldVolumenX.setDisable(activacion);
        fieldVolumenY.setDisable(activacion);
        fieldVolumenZ.setDisable(activacion);
        cheBoxUrgente.setDisable(activacion);
	}
	

}
