package com.amway.dms.recon.form;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class BaseForm implements GenericFormManager{
private static final Logger logger = LoggerFactory.getLogger(BaseForm.class);
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Double dwidth = screenSize.getWidth();
    Double dheight = screenSize.getHeight();
    protected int width = dwidth.intValue();
    protected int height = dheight.intValue();
    
    Stage mainStage ;
    protected Scene scene;
    BorderPane menuBar;
    
    public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public BorderPane getMenuBar() {
		return this.menuBar;
	}

	public void setMenuBar(BorderPane menuBar) {
		this.menuBar = menuBar;
	}

	
	public void refresh() {
		mainStage.setScene(scene);
		this.maximize();
		mainStage.show();
	}

	@Override
	public Menu createMenu(HashMap<String, GridPane> map, Stage stage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showFormAndView(Stage stage, boolean editMode, Object model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu createMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	public BorderPane generateForm(GridPane content, GridPane content2){
		if(menuBar!=null) {
			BorderPane menu = menuBar;
			menu.setLeft(content);
			menu.setRight(content2);
			return menu;
		}else {
			BorderPane menu = new BorderPane();
			menu.setLeft(content);
			menu.setRight(content2);
			return menu;
		}
	}
	
	public void maximize() {
		Screen screen = Screen.getPrimary();
    	Rectangle2D bounds = screen.getVisualBounds();
    	mainStage.setX(bounds.getMinX());
    	mainStage.setY(bounds.getMinY());
    	mainStage.setWidth(bounds.getWidth());
    	mainStage.setHeight(bounds.getHeight());
	}

}
