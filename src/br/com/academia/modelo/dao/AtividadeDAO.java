package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.Data;
import br.com.academia.modelo.Hora;
import br.com.academia.modelo.Tempo;
import br.com.academia.sgbd.ConnectionFactory;

public class AtividadeDAO {
	private Connection conexao;
	private AtividadeCompletaDAO atividadeCompletaDAO = new AtividadeCompletaDAO();
	private AlunoDAO alunoDAO = new AlunoDAO();

	public AtividadeDAO() {
		conexao = ConnectionFactory.getConnection();
	}

	public void cadastrar(Atividade  atividade){
		Aluno aluno = alunoDAO.buscarPorEmail(atividade.getAluno().getEmail());

		System.out.println(aluno);
		
		if(aluno == null){
			alunoDAO.cadastrar(atividade.getAluno());
			aluno =  alunoDAO.buscarPorEmail(atividade.getAluno().getEmail());
		}
		System.out.println(aluno);
		atividade.setAluno(aluno);

		String sql = "INSERT INTO atividade(id_aluno, data, tempo, tipo, duracao, distancia, calorias, passos)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		if (!verificarSeAAtividadeJaFoiCadastrada(atividade)) {
			try {
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setLong(1, atividade.getAluno().getId());
				ps.setString(2, atividade.getData().toString());
				ps.setString(3, atividade.getTempo().toString());
				ps.setString(4, atividade.getTipoAtividade());
				ps.setString(5, atividade.getDuracao().toString());
				ps.setDouble(6, atividade.getDistancia());
				ps.setDouble(7, atividade.getCaloriasPerdidas());
				ps.setInt(8, atividade.getPassosDados());

				ps.execute();
				ps.close();

				if(atividade instanceof AtividadeCompleta){
					atividade.setId(getUltimaAtividadeCadastrada());
					atividadeCompletaDAO.cadastrar((AtividadeCompleta)atividade);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void alterar(Atividade atividade){
		String sql = "UPDATE atividade  SET tipo=?, duracao=?, distancia=?,  calorias=?, passos=? WHERE  id = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, atividade.getTipoAtividade());
			ps.setString(2, atividade.getDuracao().toString());
			ps.setDouble(3, atividade.getDistancia());
			ps.setDouble(4, atividade.getCaloriasPerdidas());
			ps.setInt(5, atividade.getPassosDados());
			ps.setLong(6, atividade.getAluno().getId());
			ps.setString(7, atividade.getData().toString());
			ps.setString(8, atividade.getTempo().toString());

			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(atividade instanceof AtividadeCompleta){
			atividadeCompletaDAO.alterar((AtividadeCompleta) atividade); 
		}
	}

	public void remover(Atividade atividade){
		String sql = "DELETE FROM atividade WHERE id = ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, atividade.getId());

			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Atividade> listar (){
		ArrayList<Atividade> atividades = new ArrayList<Atividade>();
		String sql = "SELECT * FROM atividade";
		Atividade atividade = null;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				atividade = new Atividade();

				atividade.setAluno(new AlunoDAO().buscarPorID(rs.getInt("id_aluno")));
				atividade.setId(rs.getLong("id"));
				atividade.setData(new Data(rs.getString("data")));
				atividade.setTempo(new Tempo(rs.getString("tempo")));
				atividade.setTipoAtividade(rs.getString("tipo"));
				atividade.setDuracao(new Hora(rs.getString("duracao")));
				atividade.setDistancia(rs.getDouble("distancia"));
				atividade.setCaloriasPerdidas(rs.getDouble("calorias"));
				atividade.setPassosDados(rs.getInt("passos"));

				AtividadeCompleta dadosComplementares = atividadeCompletaDAO.buscarPorIDAtividade(atividade.getId());
				if(dadosComplementares != null){
					atividades.add(getAtividadeCompleta(atividade, dadosComplementares));
				}else{
					atividades.add(atividade);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return atividades;
	}

	private AtividadeCompleta getAtividadeCompleta(Atividade atividade, AtividadeCompleta dadosComplementares) {
		dadosComplementares.setAluno(atividade.getAluno());
		dadosComplementares.setId(atividade.getId());
		dadosComplementares.setData(atividade.getData());
		dadosComplementares.setTempo(atividade.getTempo());
		dadosComplementares.setTipoAtividade(atividade.getTipoAtividade());
		dadosComplementares.setDuracao(atividade.getDuracao());
		dadosComplementares.setDistancia(atividade.getDistancia());
		dadosComplementares.setCaloriasPerdidas(atividade.getCaloriasPerdidas());
		dadosComplementares.setPassosDados(atividade.getPassosDados());

		return dadosComplementares;
	}

	public List<Atividade> exerciciosDoAlunoNoPeriodo(Data dtInicial, Data dtFinal, long idCliente){
		ArrayList<Atividade> atividades = new ArrayList<Atividade>();
		String sql = "SELECT * FROM atividade";
		Atividade atividade = null;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				Data data = new Data(rs.getString("data"));
				if(data.dataDentroDoPeriodo(dtInicial, dtFinal)){
					long id = rs.getLong("id_aluno");
					if(id == idCliente){
						atividade = new Atividade();

						atividade.setAluno(new AlunoDAO().buscarPorID(id ));
						atividade.setId(rs.getLong("id"));
						atividade.setData(data);
						atividade.setTempo(new Tempo(rs.getString("tempo")));
						atividade.setTipoAtividade(rs.getString("tipo"));
						atividade.setDuracao(new Hora(rs.getString("duracao")));
						atividade.setDistancia(rs.getDouble("distancia"));
						atividade.setCaloriasPerdidas(rs.getDouble("calorias"));
						atividade.setPassosDados(rs.getInt("passos"));

						AtividadeCompleta dadosComplementares = atividadeCompletaDAO.buscarPorIDAtividade(atividade.getId());
						if(dadosComplementares != null){
							atividades.add(getAtividadeCompleta(atividade, dadosComplementares));
						}else{
							atividades.add(atividade);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return atividades;
	}

	public long getUltimaAtividadeCadastrada(){
		String sql = "SELECT max(id) AS MAXIMO from atividade";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				return rs.getLong("MAXIMO");
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean verificarSeAAtividadeJaFoiCadastrada(Atividade atividade){
		int jaCadastrada = 0;

		String sql = "SELECT * FROM atividade WHERE data ilike ? AND tempo ilike ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, atividade.getData().toString());
			ps.setString(2, atividade.getTempo().toString());

			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				Aluno aluno = new AlunoDAO().buscarPorID(rs.getLong("id_aluno"));
				if(aluno.getEmail().compareTo(atividade.getAluno().getEmail()) == 0){
					jaCadastrada = 1;
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(jaCadastrada == 0){
			return false;
		}else{
			return true;
		}
	}


	@Override
	protected void finalize() throws Throwable {
		conexao.close();
		atividadeCompletaDAO.finalize();
	}
}