package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.Ritmo;
import br.com.academia.sgbd.ConnectionFactory;

public class AtividadeCompletaDAO {

	private Connection conexao;
	private RitmoDAO auxDAO = new RitmoDAO();

	public AtividadeCompletaDAO() {
		conexao = ConnectionFactory.getConnection();
	}

	public void cadastrar(AtividadeCompleta atividade){
		String sql = "INSERT INTO atividadecompleta(id_atividade, velocidade_media, velocidade_maxima, menor_elevacao, maior_elevacao, ritmo_medio, ritmo_maximo) VALUES (?, ?, ?, ?,  ?, ?, ?) ";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setLong(1, atividade.getId());
			ps.setDouble(2, atividade.getVelocidadeMedia());
			ps.setDouble(3, atividade.getVelocidadeMaxima());
			ps.setDouble(4, atividade.getMenorElevacao());
			ps.setDouble(5, atividade.getMaiorElevacao());
			ps.setString(6, atividade.getRitmoMedio().getTempoRitmo());
			ps.setString(7, atividade.getRitmoMaximo().getTempoRitmo());

			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		atividade.setId_atividade_completa(getIDUltimaAtividadeCompleta());
		for (Ritmo ritmo : atividade.getRitmosNaAtividade()) {
			ritmo.setIdAtividade(atividade.getId());
			auxDAO.cadastrar(ritmo);
		}
	}


	public void alterar(AtividadeCompleta atividade){
		String sql = "UPDATE atividadecompleta SET velocidade_media = ?, velocidade_maxima = ?, menor_elevacao = ?, maior_elevacao = ?, ritmo_medio = ?, ritmo_maximo = ? WHERE id_atividade_completa = ? ";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setDouble(1, atividade.getVelocidadeMedia());
			ps.setDouble(2, atividade.getVelocidadeMaxima());
			ps.setDouble(3, atividade.getMenorElevacao());
			ps.setDouble(4, atividade.getMaiorElevacao());
			ps.setString(5, atividade.getRitmoMedio().getTempoRitmo());
			ps.setString(6, atividade.getRitmoMaximo().getTempoRitmo());
			ps.setLong(7, atividade.getId_ativade_completa());

			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public AtividadeCompleta buscarPorIDAtividade(Long idAtividade){
		AtividadeCompleta atividade = null;

		String sql = "SELECT * FROM atividadecompleta where id_atividade = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, idAtividade);

			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				atividade = new AtividadeCompleta();

				atividade.setId(rs.getLong("id_atividade"));
				atividade.setId_atividade_completa(rs.getLong("id_atividade_completa"));
				atividade.setVelocidadeMaxima(rs.getDouble("velocidade_maxima"));
				atividade.setVelocidadeMedia(rs.getDouble("velocidade_media"));
				Ritmo ritmoMedio = new Ritmo();
				ritmoMedio.setRitmo(rs.getString("ritmo_medio"));
				atividade.setRitmoMedio(ritmoMedio);
				Ritmo ritmoMaximo = new Ritmo();
				ritmoMaximo.setRitmo(rs.getString("ritmo_maximo"));
				atividade.setRitmoMaximo(ritmoMaximo);
				atividade.setMaiorElevacao(rs.getDouble("maior_elevacao"));
				atividade.setMenorElevacao(rs.getDouble("menor_elevacao"));
				atividade.setRitmosNaAtividade(auxDAO.listarPorIDAtividade(atividade.getId()));
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return atividade;
	}

	private long getIDUltimaAtividadeCompleta(){
		String sql = "SELECT max(id_atividade_completa) AS MAXIMO from atividadecompleta";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getLong("MAXIMO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
