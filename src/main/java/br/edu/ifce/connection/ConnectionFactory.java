package br.edu.ifce.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://127.9.255.2:3306/swappersws";
	public static final String USER = "adminkFPgmM4";
	public static final String KEY = "HkEA1a5fqZlQ";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			con = DriverManager.getConnection(URL, USER, KEY);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return con;
	}

}
