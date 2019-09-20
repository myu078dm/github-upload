package com.amway.dms.recon.util;

import com.amway.dms.recon.form.ReconManager;
import com.amway.dms.recon.form.custom.ComboBoxHolder;
import com.amway.dms.recon.model.Profile;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.BlendMode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.control.CheckBox;
public class UIService {
final static Logger logger = LoggerFactory.getLogger(UIService.class);
	
	public static Label createHeader(String text) {
		Label lbl = new Label(text);
		lbl.setFont(Constants.FONT.HEADER_FONT);
		lbl.setUnderline(true);
		return lbl;
	}
	
	public static Label createHeader(String id, String text) {
		Label lbl = new Label(text);
		lbl.setId(id);
		lbl.setFont(Constants.FONT.HEADER_FONT);
		lbl.setUnderline(true);
		return lbl;
	}
	
	public static Label createLabel(String text) {
		Label lbl = new Label(text);
		lbl.setId(text);
		lbl.setFont(Constants.FONT.STANDARD_FONT);
		return lbl;
	}
	
	public static Label createLabel(String id, String text) {
		Label lbl = new Label(text);
		lbl.setId(id);
		lbl.setFont(Constants.FONT.STANDARD_FONT);
		return lbl;
	}
	
	public static TextField createNumericTextField(String id) {
		TextField tf = new TextField();
		tf.setFont(Constants.FONT.STANDARD_FONT);
		tf.setId(id);
		UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                
                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

//        TextField tfDouble = new TextField();
        tf.setTextFormatter(new TextFormatter<>(filter));
        
		return tf;
	}
	
	public static Profile loadExistingProfile() {
		Profile profile = new Profile();
		try (InputStream input = new FileInputStream("profile.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            if(prop.containsKey("profile.max")) {
            profile.setMaxConfig(Integer.valueOf(prop.getProperty("profile.max")));
            System.out.println(">>>" + profile.getMaxConfig());
            for(int i=0;i<profile.getMaxConfig();i++) {
            	Properties dbProfile = new Properties();
            	String connName = prop.getProperty("db" + i + ".name");
            	System.out.println(">>" + connName);
            	
            	String userName = prop.getProperty("db" + i + ".uname");
            	System.out.println(">>" + userName);
            	String pass = prop.getProperty("db" + i + ".pass");
            	System.out.println(">>" + pass);
            	pass = CaesarUtil.decrypt(pass, 911);
            	String url = prop.getProperty("db" + i + ".url");
            	System.out.println(">>" + url);
            	dbProfile.put("db.name", connName);
            	dbProfile.put("db.uname", userName);
            	dbProfile.put("db.pass", pass);
            	dbProfile.put("db.url", url);
            	profile.getList().add(dbProfile);
            	System.out.println("LIST>>" + profile.getList().size());
            }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		System.out.println("MAX CONFIG" + profile.getMaxConfig());
		profile.setMaxConfig(profile.getList().size());
		return profile;
	}
	
	public static void writeProfile(Profile profile) {
		int maxConfig = profile.getMaxConfig();
		
		try (OutputStream output = new FileOutputStream("profile.properties")) {
			Properties prop = new Properties();
			ArrayList<Properties> list = profile.getList();
			
			for(int i=0;i<maxConfig;i++) {
				System.out.println("Loop" + i);
				prop.setProperty("db" + i + ".name", list.get(i).getProperty("db.name"));
				prop.setProperty("db" + i + ".uname", list.get(i).getProperty("db.uname"));
				prop.setProperty("db" + i + ".pass", CaesarUtil.encrypt(list.get(i).getProperty("db.pass"),911));
				prop.setProperty("db" + i + ".url", list.get(i).getProperty("db.url"));
			}
            // save properties to project root folder
			prop.setProperty("profile.max", String.valueOf(maxConfig));
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
	}
	
	public static CheckBox createCheckBox(String id) {
		CheckBox check = new CheckBox();
		check.setId(id);
		check.setFont(Constants.FONT.STANDARD_FONT);
		return check;
	}
	public static TextArea createTextArea(String id, Boolean editable) {
		TextArea ta = new TextArea();
		ta.setFont(Constants.FONT.STANDARD_FONT);
		ta.setId(id);
		ta.setEditable(editable);
//		ta.setBlendMode(BlendMode.OVERLAY);
		return ta;
	}
	public static TextField createTextField(String id) {
		TextField tf = new TextField();
		tf.setFont(Constants.FONT.STANDARD_FONT);
		tf.setId(id);
		return tf;
	}
	
	public static PasswordField createPasswordField(String id) {
		PasswordField tf = new PasswordField();
		tf.setPromptText("Your Password");
		tf.setFont(Constants.FONT.STANDARD_FONT);
		tf.setId(id);
		return tf;
	}
	
	public static Button createButton(String text) {
		Button btn = new Button(text);
		btn.setFont(Constants.FONT.STANDARD_FONT);
		return btn;
	}
	
	public static Button createButton(String id, String text) {
		Button btn = new Button(text);
		btn.setId(id);
		btn.setFont(Constants.FONT.STANDARD_FONT);
		return btn;
	}
	
	public static ComboBox createComboBox(ObservableList<ComboBoxHolder> options, String id) {
		ComboBox cb = new ComboBox(options);
		cb.setId(id);
		cb.setConverter(new StringConverter<ComboBoxHolder>() {
			@Override
		    public String toString(ComboBoxHolder object) {
				if(object!=null) {
					return object.getName();
				}
				return "";
		    }

		    @Override
		    public ComboBoxHolder fromString(String string) {
		    	return (ComboBoxHolder) cb.getItems().stream().filter(ap -> 
	            ((ComboBoxHolder) ap).getName().equals(string)).findFirst().orElse(null);
		
		    }
		});
		return cb;
	}

}
