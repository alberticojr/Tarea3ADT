package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class LoginNuevoController implements Initializable {
	
	@FXML
	private TextField usufield;
	
	@FXML
	private TextField contrafield;
	
	@FXML
	private Label lblTitulo;
	
	@Autowired
    private CredencialesService credencialesService;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@FXML
    private void login(ActionEvent event) throws IOException{
    	if(credencialesService.authenticate(usufield.getText(), contrafield.getText())){
    		    		
    		stageManager.switchScene(FxmlView.USER);
    		
    	}else{
    		lblTitulo.setText("SUBNORMAL");
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
