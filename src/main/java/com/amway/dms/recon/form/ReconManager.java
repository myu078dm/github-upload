package com.amway.dms.recon.form;

import org.slf4j.Logger;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import org.slf4j.LoggerFactory;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
public class ReconManager {
	private static final Logger logger = LoggerFactory.getLogger(ReconManager.class);
	
	ProfileFormManager pfm = new ProfileFormManager();
	DataSourceFormManager dsfm = new DataSourceFormManager();
//	QueryBuilderManager qbfm = new QueryBuilderManager();
	
	public ReconManager(Stage stage){
		Scene scene = new Scene(createMenuBar(), 0, 0);
//		scene = new Scene(new BorderPane(), 0, 0);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		pfm.setMainStage(stage);
		pfm.setMenuBar(createMenuBar());
		pfm.setScene(scene);
//		pfm.showFormAndView(stage, true, null);
		pfm.refresh();	
		
		dsfm.setMainStage(stage);
		dsfm.setMenuBar(createMenuBar());
		dsfm.setScene(scene);		
		dsfm.refresh();
		
	}

	public BorderPane createMenuBar(){
    	MenuBar menuBar = new MenuBar();
        
    	Menu menu1 =  pfm.createMenu();
    	Menu menu2 = dsfm.createMenu();

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        return root;
	}

}
