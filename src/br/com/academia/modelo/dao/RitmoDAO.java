package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.academia.modelo.Ritmo;
import br.com.academia.sgbd.ConnectionFactory;

public class RitmoDAO {
	private Connection conexao;

	public RitmoDAO() {
		conexao = ConnectionFactory.getConnection();
	}
	
	public void cadastrar(Ritmo ritmo){
		String sql = "INSERT INTO ritmo(id_atividade, quilometragem, tempo) VALUES (?, ?, ?) ";
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1,ritmo.getIdAtividade() );
			ps.setDouble(2, ritmo.getDistancia());
			ps.setString(3, ritmo.getTempoRitmo());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterar(Ritmo ritmo){
		String sql = "UPDATE ritmo SET quilometragem=?, tempo=?  WHERE id = ?";
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setDouble(1, ritmo.getDistancia());
			ps.setString(2, ritmo.getTempoRitmo());
			ps.setLong(3, ritmo.getId());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Ritmo> listarPorIDAtividade(long idAtividade){
		ArrayList<Ritmo> ritmos = new ArrayList<Ritmo>();
		String sql = "SELECT * FROM ritmo where id_atividade = ?";
		
		String textoRitmo;
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, idAtividade);

			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				// Exemplo: 1 Km: 09'15"
				textoRitmo = rs.getDouble("quilometragem")+ " Km: " + rs.getString("tempo");
				ritmos.add(new Ritmo(textoRitmo));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ritmos;
	}
	
	@Override
	protected void finalize() throws Throwable {
		conexao.close();
	}
}