package br.com.academia.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Data;

public class AlunoDAO {
	private Connection conexao;

	public AlunoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public void cadastrar(Aluno aluno){
		String sql = "INSERT INTO aluno(nome, sexo, altura, peso, datanascimento, email, cpf, whattsapp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			
			st.setString(1, aluno.getNome());
			st.setString(2, aluno.getSexo());
			st.setString(3, aluno.getAltura());
			st.setString(4, aluno.getPeso());
			st.setString(5, aluno.getDataNascimento().toString());
			st.setString(6, aluno.getEmail());
			st.setString(7, aluno.getCpf());
			st.setString(8, aluno.getWhattsapp());
			
			st.execute();
			st.close();
		} catch (SQLException e) {
			System.err.println("Erro ao cadastrar um aluno!");
			e.printStackTrace();
		}
	}
	
	public void alterar(Aluno aluno){
		String sql = "UPDATE aluno SET nome=?, sexo=?, altura=?, peso=?, datanascimento=?, email=?,  cpf=?, whattsapp=? WHERE id = ?";
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			
			st.setString(1, aluno.getNome());
			st.setString(2, aluno.getSexo());
			st.setString(3, aluno.getAltura());
			st.setString(4, aluno.getPeso());
			st.setString(5, aluno.getDataNascimento().toString());
			st.setString(6, aluno.getEmail());
			st.setString(7, aluno.getCpf());
			st.setString(8, aluno.getWhattsapp());
			st.setLong(9, aluno.getId());
			
			st.execute();
			st.close();
		} catch (SQLException e) {
			System.err.println("Erro ao alterar um aluno!");
			e.printStackTrace();
		}
	}
	
	public Aluno buscarPorNome(String nome){
		String sql = "SELECT * FROM aluno WHERE nome ilike = ?";
		Aluno aluno = null;
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			 
			if(rs.next()){
				aluno = new Aluno();
				
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setDataNascimento(new Data(rs.getString("datanascimento")));
				aluno.setAltura(rs.getString("altura"));
				aluno.setPeso(rs.getString("peso"));
				aluno.setEmail(rs.getString("email"));
				aluno.setSexo(rs.getString("sexo"));
				aluno.setWhattsapp(rs.getString("whattsapp"));
				aluno.setCpf(rs.getString("cpf"));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Erro ao buscar um aluno!");
			e.printStackTrace();
		}
		return aluno;
	}
	
	public void remover(Aluno aluno){
		String sql = "DELETE FROM aluno WHERE id = ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, aluno.getId());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			System.err.println("Erro ao excluir um aluno!");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Aluno> listar(){
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		String sql = "SELECT * FROM aluno";
		Aluno aluno = null;
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				aluno = new Aluno();
				
				aluno.setId(rs.getLong("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setDataNascimento(new Data(rs.getString("datanascimento")));
				aluno.setAltura(rs.getString("altura"));
				aluno.setPeso(rs.getString("peso"));
				aluno.setEmail(rs.getString("email"));
				aluno.setSexo(rs.getString("sexo"));
				aluno.setWhattsapp(rs.getString("whattsapp"));
				aluno.setCpf(rs.getString("cpf"));
				
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar os alunos!");
			e.printStackTrace();
		}
		
		return alunos;
	}

}