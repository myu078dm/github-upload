package com.amway.dms.recon.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amway.dms.recon.form.ReconManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class MDMSRecon extends Application {
	private static final Logger logger = LoggerFactory.getLogger(MDMSRecon.class);
	ReconManager fm ;
	
    public static void main(String[] args) {
        launch(args);
    }    
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("MDMS DB Tool");
    	fm = new ReconManager(primaryStage);
    	
//        fm.getSellerFormManager().display("Admin UI");
//        fm.getBsfm().display("Admin UI");
    }
    
     
}
