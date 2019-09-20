package com.amway.dms.recon.model;

public class ProfileDetails implements Comparable {

	private String dbName;
	private String dbUsername;
	private String dbURL;
	private String dbStatus;
	private String sqlResult;
	
	public ProfileDetails(String dbName, String dbUsername, String dbURL, String dbStatus, String sqlResult) {
		this.dbName = dbName;
		this.dbUsername = dbUsername;
		this.dbURL = dbURL;
		this.dbStatus = dbStatus;
		this.sqlResult = sqlResult;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	public String getDbURL() {
		return dbURL;
	}
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
	}

	public String getSqlResult() {
		return sqlResult;
	}

	public void setSqlResult(String sqlResult) {
		this.sqlResult = sqlResult;
	}

	@Override
	public int compareTo(Object o) {	
		ProfileDetails obj2 = (ProfileDetails) o;
		return getDbName().compareTo(obj2.getDbName());
	}
	
}
