package com.amway.dms.recon.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amway.dms.recon.model.Profile;
import com.amway.dms.recon.ui.*;
import com.amway.dms.recon.util.UIService;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ComparatorFormManager extends BaseForm {
	private static final Logger logger = LoggerFactory.getLogger(ProfileFormManager.class);
	ObservableList<ComboBoxHolder> dbOptions = 
		    FXCollections.observableArrayList(
		    );
	
	EventHandler compareMenuEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
        	showFormAndView(getMainStage(), false, null);
        }
	};
	
	@Override
	public void showFormAndView(Stage stage, boolean editMode, Object model) {
		GridPane form = createForm();
		System.out.println("scene" + scene);
    	scene.setRoot(generateForm(form, new GridPane()));
    	maximize();
	}
	
	public GridPane createForm() {
//		ComboBox cb = UIService.createComboBox(dbOptions, "DB_LIST");
		
		GridPane panel = new GridPane();
		panel.setAlignment(Pos.TOP_LEFT);
		panel.setPadding(new Insets(40, 40, 40, 40));
		panel.setHgap(10);
	    panel.setVgap(10);
	    
		Profile profile = UIService.loadExistingProfile();
		for(int i=0;i<profile.getConnections().size();i++) {
			
		}
		return panel;
	}
}
