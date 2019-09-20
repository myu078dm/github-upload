package com.amway.dms.recon.form;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amway.dms.recon.model.Profile;
import com.amway.dms.recon.util.CaesarUtil;
import com.amway.dms.recon.util.UIService;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ProfileFormManager extends BaseForm {
	private static final Logger logger = LoggerFactory.getLogger(ProfileFormManager.class);
	Profile profile;
	
	EventHandler newEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
        	showFormAndView(getMainStage(), false, null);
        }
	};
	
	@Override
	public void showFormAndView(Stage stage, boolean editMode, Object model) {
		GridPane view = createView();
		GridPane form = createForm();
    	scene.setRoot(generateForm(form, view));
    	maximize();

	}

	public GridPane createView() {
		return new GridPane();
	}
	
	public Profile getProfile() {
		return this.profile;
	}
	public GridPane createForm() {
		GridPane panel = new GridPane();
		panel.setAlignment(Pos.TOP_LEFT);
		panel.setPadding(new Insets(40, 40, 40, 40));
		panel.setHgap(10);
	    panel.setVgap(10);	    

		profile = UIService.loadExistingProfile();
//
//		TabPane tabPane = new TabPane();
		
		int y = 0;
		Button btnSave = UIService.createButton("Save");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	readProfileData(panel);
		    	UIService.writeProfile(getProfile());
		    	showFormAndView(getMainStage(), false, null);
		    }
		    
		});
		
		Button btnTab = UIService.createButton("Add Connection");
		btnTab.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	addTab((TabPane) scene.lookup("#tabpane"), profile);
		    	maximize();
		    }
		    
		});
		
		panel.add(btnSave, 0, y);
		panel.add(btnTab, 2, y);
		y++;
//		System.out.println("PROFILE MAX CONFIG=" + profile.getMaxConfig());
				
		if(profile.getMaxConfig()==0) {
			Label lConn = UIService.createLabel("Connection Name");
			TextField fConn = UIService.createTextField("db0_name");
			Label lUname = UIService.createLabel("User Name");
			TextField fUname = UIService.createTextField("db0_uname");
			Label lPass = UIService.createLabel("Password");
			PasswordField fPass = UIService.createPasswordField("db0_pass");
			Label lUrl = UIService.createLabel("URL");
			TextArea fUrl = UIService.createTextArea("db0_url", true);
			panel.add(lConn, 0 , y);
			panel.add(fConn, 1 , y);
			y++;
			panel.add(lUname, 0 , y);
			panel.add(fUname, 1 , y);
			y++;
			panel.add(lPass, 0 , y);
			panel.add(fPass, 1 , y);
			y++;
			panel.add(lUrl, 0 , y);
			panel.add(fUrl, 1 , y);

		}else {
			loadProfileData(y, panel,profile);
		}
		
		
	   
		
		
		return panel;
	}
	
	public void addTab(TabPane pane, Profile profile) {
		System.out.println("ADDING TAB");
		GridPane inner = new GridPane();
		int z=0;
		Tab tab = new Tab();
		int i = profile.getMaxConfig();
		Label lConn = UIService.createLabel("Connection Name");
		TextField fConn = UIService.createTextField("db" + i + "_name");
		
		Label lUname = UIService.createLabel("User Name");
		TextField fUname = UIService.createTextField("db" + i + "_uname");
		
		Label lPass = UIService.createLabel("Password");
		PasswordField fPass = UIService.createPasswordField("db" + i + "_pass");
		
		Label lUrl = UIService.createLabel("URL");
		TextArea fUrl = UIService.createTextArea("db" + i + "_url", true);
		
		inner.add(lConn, 0 , z);
		inner.add(fConn, 1 , z);
		z++;
		inner.add(lUname, 0 , z);
		inner.add(fUname, 1 , z);
		z++;
		inner.add(lPass, 0 , z);
		inner.add(fPass, 1 , z);
		z++;
		inner.add(lUrl, 0 , z);
		inner.add(fUrl, 1 , z);
		tab.setContent(inner);
		tab.setText(String.valueOf(i));
		tab.setId(String.valueOf(i));
		System.out.println(">>>" + String.valueOf(i));
		pane.getTabs().add(tab);
		
	}
	public void loadProfileData(int y, GridPane panel, Profile profile) {
		TabPane pane = new TabPane();
		pane.setId("tabpane");
		for(int i=0;i<profile.getMaxConfig();i++) {
			GridPane inner = new GridPane();
			int z=0;
			Tab tab = new Tab();
			Label lConn = UIService.createLabel("Connection Name");
			TextField fConn = UIService.createTextField("db" + i + "_name");
			fConn.setText(profile.getList().get(i).getProperty("db.name"));
			Label lUname = UIService.createLabel("User Name");
			TextField fUname = UIService.createTextField("db" + i + "_uname");
			fUname.setText(profile.getList().get(i).getProperty("db.uname"));
			Label lPass = UIService.createLabel("Password");
			PasswordField fPass = UIService.createPasswordField("db" + i + "_pass");
			fPass.setText(profile.getList().get(i).getProperty("db.pass"));
			Label lUrl = UIService.createLabel("URL");
			TextArea fUrl = UIService.createTextArea("db" + i + "_url", true);
			fUrl.setText(profile.getList().get(i).getProperty("db.url"));
			inner.add(lConn, 0 , z);
			inner.add(fConn, 1 , z);
			z++;
			inner.add(lUname, 0 , z);
			inner.add(fUname, 1 , z);
			z++;
			inner.add(lPass, 0 , z);
			inner.add(fPass, 1 , z);
			z++;
			inner.add(lUrl, 0 , z);
			inner.add(fUrl, 1 , z);
			tab.setContent(inner);
			tab.setText(fConn.getText());
			tab.setId(String.valueOf(i));
			pane.getTabs().add(tab);
		}
		panel.add(pane, 0, y);
	}
	
	
	
	
	@Override
	public Menu createMenu() {
		Menu menuSetting = new Menu("DB Profile");
		MenuItem menuSettingAdd = new MenuItem("Update DB Profile");
		menuSetting.getItems().add(menuSettingAdd);
		menuSetting.setOnAction(newEvent);
		return menuSetting;
	}
	
	public void readProfileData(Pane pane){

		if(profile.getList()==null || profile.getList().size()==0) {
			Properties newProperties = new Properties();
			profile.getList().add(new Properties());
			logger.info("No properties found..adding new");
			System.out.println("No properties found..adding new");
		}
		
		if(pane!=null) {
			TabPane tb = (TabPane) scene.lookup("#tabpane");
			for (Tab tab : tb.getTabs()) {
				System.out.println("TRAVERSE TAB " + tab.getId());
				GridPane innerPane = (GridPane)tab.getContent();
			for (Node node : innerPane.getChildren()) {
		    if (node instanceof TextField) {
		    	TextField textFieldData = (TextField) node;
		    	String idTmp = textFieldData.getId();
		    	int profileNo = Integer.valueOf(idTmp.substring(2,3));
		    	if(profileNo >= profile.getList().size()) {
		    		Properties newProperties = new Properties();
		    		profile.getList().add(new Properties());
		    	}
		    	String[] segments = idTmp.split("_");
		    	String type = segments[1];
		    	switch(type){
		    		case "name" :
//		    			System.out.println("Found name");
		    			profile.getList().get(profileNo).setProperty("db.name", textFieldData.getText());
		    			break;
		    		case "uname" :
//		    			System.out.println("Found uname");
		    			profile.getList().get(profileNo).setProperty("db.uname", textFieldData.getText());
		    			break;
		    		case "pass" :
//		    			System.out.println("Found pass" + textFieldData.getText());
		    			profile.getList().get(profileNo).setProperty("db.pass", textFieldData.getText());
		    			break;
		    		default:break;
		    	}
		    
		    }else if(node instanceof TextArea) {
		    	TextArea textFieldData = (TextArea) node;
		    	String idTmp = textFieldData.getId();
		    	System.out.println("ID found =" + idTmp);
		    	int profileNo = Integer.valueOf(idTmp.substring(2,3));
		    	if(profileNo >= profile.getList().size()) {
		    		Properties newProperties = new Properties();
		    		profile.getList().add(new Properties());
		    	}
		    	String[] segments = idTmp.split("_");
		    	String type = segments[1];
		    	switch(type){
		    		case "url" :
		    			profile.getList().get(profileNo).setProperty("db.url", textFieldData.getText());
		    			break;
		    		default:break;
		    	}
		    }
			}
			}
		}
		profile.setMaxConfig(profile.getList().size());
	}


}
