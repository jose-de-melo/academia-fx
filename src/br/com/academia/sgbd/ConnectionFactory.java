package br.com.academia.sgbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost/academia", "postgres", "aluno");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



}
