package br.com.academia.sgbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static Connection conexao;
	public static Connection getConnection() {
		if(conexao == null) {
			try {
				conexao = DriverManager.getConnection("jdbc:postgresql://localhost/academia", "postgres", "aluno");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}



}
