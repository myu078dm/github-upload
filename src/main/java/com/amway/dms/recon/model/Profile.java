package com.amway.dms.recon.model;

import java.util.ArrayList;
import java.util.Properties;
import java.sql.Connection;

public class Profile {
	ArrayList<Connection> connections = new ArrayList<Connection>();
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	ArrayList<Properties> list = new ArrayList<Properties>();
	int maxConfig=0;
	
	public ArrayList<Properties> getList() {
		return list;
	}

	public void setList(ArrayList<Properties> list) {
		this.list = list;
	}

	public int getMaxConfig() {
		return maxConfig;
	}

	public void setMaxConfig(int maxConfig) {
		this.maxConfig = maxConfig;
	}

	public Profile() {
//		= new Properties();
	}
}
