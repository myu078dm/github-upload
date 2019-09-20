//package com.amway.dms.recon.form;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.amway.dms.recon.model.Profile;
//import com.amway.dms.recon.util.UIService;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//import javafx.scene.control.CheckBox;
//
//public class QueryBuilderManager extends BaseForm {
//	private static final Logger logger = LoggerFactory.getLogger(QueryBuilderManager.class);
//	@Override
//	public void showFormAndView(Stage stage, boolean editMode, Object model) {
//		GridPane view = createView();
//		GridPane form = createForm();
//    	scene.setRoot(generateForm(form, view));
//    	maximize();
//	}
//	
//	public GridPane createForm() {
//		GridPane panel = new GridPane();
//		panel.setAlignment(Pos.TOP_LEFT);
//		panel.setPadding(new Insets(40, 40, 40, 40));
//		panel.setHgap(10);
//	    panel.setVgap(10);
//	    
//	    Button btnRun = UIService.createButton("Run");
//	    btnRun.setOnAction(new EventHandler<ActionEvent>() {
//		    @Override
//		    public void handle(ActionEvent event) {
////		    	readProfileData(panel);
////		    	writeProfile();
//		    }
//		    
//		});
//	    Label lSQL = UIService.createLabel("SQL");
//	    TextArea fSQL = UIService.createTextArea("sql", true);
//
//	    int y=0;
//		panel.add(btnRun, 0, y);
//		y++;
//		panel.add(lSQL, 0, y);
//		panel.add(fSQL, 1, y);
//		y++;
//		loadEnvironment(y, profile, panel);
//	}
//	
//	public GridPane loadEnvironment(int y, Profile profile, GridPane panel) {
//		if(panel!=null) {
//			for(int i=0;i<profile.getList().size();i++) {
//				Label label = UIService.createLabel(profile.getList().get(i).getProperty("db.name"));
//				CheckBox checkbox = UIService.createCheckBox("env" + i);
//				panel.add(label, 0, 1);
//				panel.add(checkbox, 1, 1);
//				y++;
//			}
//		}
//		return panel;
//	}
//}
