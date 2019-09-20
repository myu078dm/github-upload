package com.amway.dms.recon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	public static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	public static Connection getDBConnection(String connUrl, String userName,
			String password) {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(connUrl, userName,
					password);
			return dbConnection;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
		}

		return dbConnection;

	}
}
