package com.amway.dms.recon.form;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amway.dms.recon.model.Profile;
import com.amway.dms.recon.model.ProfileDetails;
import com.amway.dms.recon.util.ConnectionManager;
import com.amway.dms.recon.util.UIService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import oracle.sql.TIMESTAMP;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;

public class DataSourceFormManager extends BaseForm {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceFormManager.class);
	private String globalQuery = "SELECT 1 FROM DUAL";
	private boolean compareBtnClicked = false;
	private Profile profile = null;
	private List<ProfileDetails> globalProfileDetailsList = null;
	private List<String> columnNameList = null;
	private List<String> dataList = null;
	
	public DataSourceFormManager(){}
	
	public DataSourceFormManager(Scene scene) {
		setScene(scene);
	}
	
	EventHandler newEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
        	showFormAndView(getMainStage(), false, null);
        }
	};
	
	EventHandler compareMenuEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
        	showCompareFormAndView(getMainStage(), false, null);
        }
	};
	
	@Override
	public void showFormAndView(Stage stage, boolean editMode, Object model) {
		GridPane form = createForm();
    	scene.setRoot(generateForm(form, new GridPane()));
    	maximize();
	}
	
	public void showCompareFormAndView(Stage stage, boolean editMode, Object model) {
		GridPane form = createCompareForm();
    	scene.setRoot(generateForm(form, new GridPane()));
    	maximize();
	}
	
	@Override
	public Menu createMenu() {
		Menu menuSetting = new Menu("Data Source");
		MenuItem menuTestConn = new MenuItem("Test Connection");
		MenuItem menuCompare = new MenuItem("Compare Query");
		menuSetting.getItems().add(menuTestConn);
		menuSetting.getItems().add(menuCompare);
		menuTestConn.setOnAction(newEvent);
		menuCompare.setOnAction(compareMenuEvent);
		return menuSetting;
	}
	
	private GridPane createForm() {
		GridPane panel = new GridPane();
		panel.setAlignment(Pos.TOP_LEFT);
		panel.setPadding(new Insets(40, 40, 40, 40));
		panel.setHgap(10);
	    panel.setVgap(10);	    
	    
	    TableView<ProfileDetails> tableView = new TableView<>();
	    TableColumn<ProfileDetails, String> column1 = new TableColumn<>("Connection name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("dbName"));
	    TableColumn<ProfileDetails, String> column2 = new TableColumn<>("Status");
	    column2.setCellValueFactory(new PropertyValueFactory<>("dbStatus"));
	    tableView.getColumns().addAll(column1, column2);
	    tableView.setItems(FXCollections.observableArrayList(getTableData(null)));
	    
	    panel.add(tableView, 0, 0);
	    return panel;
	}

	private GridPane createCompareForm() {
		GridPane panel = new GridPane();
		panel.setAlignment(Pos.TOP_LEFT);
		panel.setPadding(new Insets(40, 40, 40, 40));
		panel.setHgap(10);
	    panel.setVgap(10);	
	    
	    Label enterQueryLbl = new Label("Enter your query here : ");
	    TextArea queryInput = new TextArea(globalQuery);
	    queryInput.setMaxWidth(800);
	    queryInput.setMinWidth(800);
		Button btnCompare = UIService.createButton("Compare");
		ProgressIndicator progressInd = new ProgressIndicator(compareBtnClicked?1:0);
		btnCompare.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	panel.add(progressInd, 0, 2);
		    	globalQuery = queryInput.getText();
		    	compareBtnClicked = true;
		    	Task<Object> task = new Task<Object>() {
					@Override
					protected Object call() throws Exception {
						if(profile==null)
							profile = UIService.loadExistingProfile();
						columnNameList = new ArrayList<String>();
						dataList = new ArrayList<String>();
						int i = 1;
						int increment = 100/profile.getList().size();
						for(Properties property : profile.getList()) {
							executeQuery2(property, queryInput.getText());
							updateProgress(increment*i, 100);
							i++;
						}
						updateProgress(100, 100);
						return null;
					}
		    	};
		    	progressInd.progressProperty().bind(task.progressProperty());
		    	task.setOnSucceeded(evt->{showCompareFormAndView(getMainStage(), false, null);});
		    	new Thread(task).start();
		    }
		});
		Button btnClear = UIService.createButton("Clear");
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	globalQuery = "SELECT 1 FROM DUAL";
		    	compareBtnClicked = false;
		    	showCompareFormAndView(getMainStage(), false, null);
		    }
		});
		
	    panel.add(enterQueryLbl, 0, 0);
	    panel.add(queryInput, 0, 1);
	    panel.add(btnCompare, 1, 1);
	    panel.add(btnClear, 2, 1);
	    
	    if(compareBtnClicked && columnNameList!=null && dataList!=null) {
	    	TableView tableView = new TableView<>();
	    	tableView.setMaxWidth(800);
		    columnNameList.forEach(p->{
		    	TableColumn col = new TableColumn<>(p);
		    	final int j = columnNameList.indexOf(p);
		    	col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                }   
			    });	
		    	tableView.getColumns().add(col);
		    });
		    ObservableList<ObservableList> data = FXCollections.observableArrayList();
		    dataList.forEach(p->{
		    	ObservableList<String> row = FXCollections.observableArrayList();
		    	String[] dataArray = p.split(",");
		    	for(int j=0; j<dataArray.length; j++) {
		    		row.add(dataArray[j]);
		    	}
		    	if(dataArray.length!=columnNameList.size()) {
		    		int y = dataArray.length;
		    		while(y++!=columnNameList.size()) {
		    			row.add("");
		    		}
		    	}
		    	data.add(row);
		    });
		    tableView.setItems(data);
		    panel.add(tableView, 0, 3);
	    }
	    
	    return panel;
	}
	
	private ObservableList<ProfileDetails> getTableData(String query) {
		if(query==null || query.isEmpty()) {
			query = "SELECT 1 FROM DUAL";
		}
		query = query.trim();
		if(query.endsWith(";")) {
			query = query.substring(0, query.length()-1);
		}
		String[] columns = findColumnNumber(query);
		if(profile==null)
			profile = UIService.loadExistingProfile();
	    List<ProfileDetails> profileDetailList = new ArrayList<ProfileDetails>();
	    Connection conn = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    Object data = null;
	    try {
		    for(Properties property : profile.getList()) {
		    	rs = null;
		    	data = null; 
				try {
					conn = ConnectionManager.getDBConnection(property.getProperty("db.url"), property.getProperty("db.uname"), property.getProperty("db.pass"));
					if(conn!=null) {
						statement = conn.createStatement();
						rs = statement.executeQuery(query);
						if(rs.next()) {
							if(columns.length==1)
								data = rs.getObject(1);
							else {
								int i = 0;
								data = "";
								while(i<columns.length) {
									if(i==(columns.length-1)) 
										data = data + columns[i].trim()+": "+getDBObject(rs.getObject(i+1));
									else 
										data = data + columns[i].trim()+": "+getDBObject(rs.getObject(i+1))+", ";
									i++;
								}
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				profileDetailList.add(new ProfileDetails(property.getProperty("db.name"), property.getProperty("db.uname"), property.getProperty("db.url"), rs!=null?"Active":"Inactive", getDBObject(data)));
		    }
	    } finally {
			try {
				if(rs!=null)
					rs.close();						
				if(statement!=null)
					statement.close();					
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    Collections.sort(profileDetailList);
	    return FXCollections.observableArrayList(profileDetailList);
	}
	
	private ProfileDetails executeQuery(Properties property, String query) {
		if(query==null || query.isEmpty()) {
			query = "SELECT 1 FROM DUAL";
		}
		query = query.trim();
		if(query.endsWith(";")) {
			query = query.substring(0, query.length()-1);
		}
		String[] columns = findColumnNumber(query);
		Connection conn = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    Object data = null;
		try {
			conn = ConnectionManager.getDBConnection(property.getProperty("db.url"), property.getProperty("db.uname"), property.getProperty("db.pass"));
			if(conn!=null) {
				statement = conn.createStatement();
				rs = statement.executeQuery(query);
				if(rs.next()) {
					if(columns.length==1) {
						data = rs.getObject(1);
					} else {
						int i = 0;
						data = "";
						while(i<columns.length) {
							if(i==(columns.length-1)) 
								data = data + columns[i].trim()+": "+getDBObject(rs.getObject(i+1));
							else 
								data = data + columns[i].trim()+": "+getDBObject(rs.getObject(i+1))+", ";
							i++;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return new ProfileDetails(property.getProperty("db.name"), property.getProperty("db.uname"), property.getProperty("db.url"), rs!=null?"Active":"Inactive", getDBObject(data));
	}
	
	private ProfileDetails executeQuery2(Properties property, String query) {
		if(query==null || query.isEmpty()) {
			query = "SELECT 1 FROM DUAL";
		}
		query = query.trim();
		if(query.endsWith(";")) {
			query = query.substring(0, query.length()-1);
		}
		StringBuffer dataStrB = new StringBuffer();
		Connection conn = null;
	    Statement statement = null;
	    ResultSet rs = null;
		try {
			conn = ConnectionManager.getDBConnection(property.getProperty("db.url"), property.getProperty("db.uname"), property.getProperty("db.pass"));
			if(conn!=null) {
				statement = conn.createStatement();
				rs = statement.executeQuery(query);
				if(columnNameList.size()==0) {
					columnNameList.add("CONN_NAME");
					for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
						columnNameList.add(rs.getMetaData().getColumnName(i+1));
					}
				}
				dataStrB.append(property.getProperty("db.name")+",");
				if(rs.next()) {
					for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
						dataStrB.append(getDBObject(rs.getObject(i+1))+",");
					}
				}
				dataList.add(dataStrB.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return new ProfileDetails(property.getProperty("db.name"), property.getProperty("db.uname"), property.getProperty("db.url"), rs!=null?"Active":"Inactive", "test");
	}
	
	private String getDBObject(Object obj) {
		if(obj==null) {
			return "";
		} else if(obj instanceof TIMESTAMP){
			TIMESTAMP temp = (TIMESTAMP) obj;
			return temp.stringValue();
		}else {
			return obj.toString();
		}
	}
	
	private String[] findColumnNumber(String query) {
		String temp = query.toUpperCase();
		temp = temp.substring(temp.indexOf("SELECT ")+7,temp.indexOf("FROM"));
		return temp.split(",");
	}
	
}

