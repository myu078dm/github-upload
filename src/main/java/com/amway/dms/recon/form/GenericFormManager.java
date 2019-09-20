package com.amway.dms.recon.form;

import java.util.HashMap;

import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public interface GenericFormManager {
	public Menu createMenu(HashMap<String, GridPane> map, Stage stage);
	
	public void showFormAndView(Stage stage, boolean editMode, Object model);
	
	public Menu createMenu() ;
}
