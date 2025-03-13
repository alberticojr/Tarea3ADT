package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ObsListaParadas;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.DB4OService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ValidacionesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ModificarServicioController implements Initializable{
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private DB4OService db4oService;
	
	@FXML
	private Button btnAyuda;
	
	@FXML
	private TextField fieldNombreServicio;
	
	@FXML
	private TextField fieldPrecioServicio;
	
	@FXML
	private ComboBox<String> CBServicios;
	
	@FXML
	private TableView<ObsListaParadas> tableParadas;
	
	@FXML
	private TableColumn <ObsListaParadas, Long> ColumnId;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnNombre;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnRegion;
	
	@FXML
	private TableColumn <ObsListaParadas, String> ColumnResponsable;
	
	private ObservableList<Long> selectedIds = FXCollections.observableArrayList();
	
	private List <Servicio> listaServicios;
	private Long idServicioActual = 11L;
	private Servicio ServicioActual;
	
	
	@FXML
	private void pulsaEditar () {
		String nombreServicio = fieldNombreServicio.getText();
		String precioServicio = fieldPrecioServicio.getText();
		
		
		boolean nombreValido = ValidacionesService.comprobarCredenciales("a", "a", nombreServicio, "a");
		boolean numeroValido = precioServicio.matches("^\\d+\\.\\d{2}$");
		boolean nombreRepetido = false;
		
		List <Servicio> listaServicios = db4oService.listaServicios();
		
		if (listaServicios != null) {
			for (Servicio s : listaServicios) {

				if (s.getNombre().equals(nombreServicio) && !s.getNombre().equals(ServicioActual.getNombre())) {
					nombreRepetido = true;
				}
				
				
			}
		}
			
		if (selectedIds.size() != 0 ) {
			if (nombreValido) {
				if (!nombreRepetido) {
					if (numeroValido) {

						Double Doubleprecio = Double.parseDouble(precioServicio);

						Servicio servicio = new Servicio();
						servicio.setId(db4oService.findServicioLastId());
						servicio.setNombre(nombreServicio);
						servicio.setPrecio(Doubleprecio);

						for (Long idParada : selectedIds) {
							servicio.getIdListaParadas().add(idParada);
						}
						
						db4oService.editarServicio(ServicioActual.getId(), servicio);
						
						CBServicios.getItems().clear();
						cargarListaServicios();
						CBServicios.setValue( listaServicios.get(0).getId()+ " | " +listaServicios.get(0).getNombre() + " | " + listaServicios.get(0).getPrecio());
						
					} else {
						System.out.println("numero no valido");
					}
				} else {
					System.out.println("nombre repetido");
				}
			} else {
				System.out.println("nombre no valido");
			}
		} else {
			System.out.println("No has seleccionado ninguna Parada");
		}
		
	}
	
	@FXML
	private void pulsaCancelar(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.MenuAdministrador);
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
	
	
	
	public void cargarListaServicios() {
		
		for (Servicio s : listaServicios) {

			CBServicios.getItems().add(s.getId() + " | " + s.getNombre() + " | " + s.getPrecio());

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listaServicios = db4oService.listaServicios();
		
		URL linkAyuda = getClass().getResource("/images/iconos/informacion.png");
		Image imgAyuda = new Image(linkAyuda.toString(),30, 30, false, true);
		
		btnAyuda.setGraphic(new ImageView(imgAyuda));
		
		
		List<Parada> listaParadas = paradaService.findAll();

		ObservableList<ObsListaParadas> ObservableListaParadas = FXCollections.observableArrayList();

		for (Parada p : listaParadas) {

			ObsListaParadas paradaTransformada = new ObsListaParadas();
			paradaTransformada.setId(p.getId());
			paradaTransformada.setNombreParada(p.getNombre());
			paradaTransformada.setRegionParada(p.getRegion() + "");
			paradaTransformada.setNombreResponsable(p.getResponsable());

			ObservableListaParadas.add(paradaTransformada);
		
		}
		
		tableParadas.setItems(ObservableListaParadas);
		
		ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreParada"));
		ColumnRegion.setCellValueFactory(new PropertyValueFactory<>("regionParada"));
		ColumnResponsable.setCellValueFactory(new PropertyValueFactory<>("nombreResponsable"));
		
		tableParadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		cargarListaServicios();
		CBServicios.setValue( listaServicios.get(0).getId()+ " | " +listaServicios.get(0).getNombre() + " | " + listaServicios.get(0).getPrecio());
		
		CBServicios.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	String[] servi = newValue.split(" ");
            	idServicioActual = Long.parseLong(servi[0]);
            	ServicioActual = db4oService.buscarServicioPorID(idServicioActual);
            	
            	fieldNombreServicio.setText(ServicioActual.getNombre());
            	fieldPrecioServicio.setText(ServicioActual.getPrecio()+"");
            	
            	tableParadas.getSelectionModel().clearSelection();
            	
            	for (Long idParada: ServicioActual.getIdListaParadas()) {
            		
            		tableParadas.getSelectionModel().select(idParada.intValue()-1);
            		
            	}
            	
                System.out.println(ServicioActual.toString());
            }
        });
		
		tableParadas.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ObsListaParadas>) change -> {
			
			selectedIds.clear();
			
			for (ObsListaParadas paradasSeleccionada : tableParadas.getSelectionModel().getSelectedItems()) {
				selectedIds.add(paradasSeleccionada.getId());
			}
			
			System.out.println("IDs seleccionados: " + selectedIds);
		});
		
		
		
	}
}
