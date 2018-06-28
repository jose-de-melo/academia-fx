package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.academia.modelo.Usuario;
import br.com.academia.sgbd.ConnectionFactory;

public class UsuarioDAO {
	private Connection conexao;

	public UsuarioDAO() {
		this.conexao = ConnectionFactory.getConnection();
	}
	
	public void cadastrarUsuario(Usuario usuario){
		String sql = "INSERT INTO usuario(usuario, senha, papel, logado)";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getPapel());
			ps.setBoolean(4, usuario.isLogado());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterar(Usuario usuario){
		String sql = "UPDATE usuario SET usuario=?, senha=?, papel=?, logado=? WHERE id = ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getPapel());
			ps.setBoolean(4, usuario.isLogado());
			ps.setLong(5, usuario.getId());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getUsuarioLogado(){
		String sql = "SELECT * FROM usuario where logado = ?";
		Usuario usuario = null;
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setBoolean(1, true);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				usuario = new Usuario(rs.getLong("id"), rs.getString("usuario"), rs.getString("senha"), rs.getString("papel"), rs.getBoolean("logado"));
			}
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public ArrayList<Usuario> listar(){
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String sql = "SELECT * from usuario";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				usuarios.add(new Usuario(rs.getLong("id"), rs.getString("usuario"), rs.getString("senha"), rs.getString("papel"), rs.getBoolean("logado")));
			}
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	public Usuario consultaPorNome(String nome){
		String sql = "SELECT * FROM usuario where usuario ilike ? ";
		Usuario usuario = null;
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				usuario = new Usuario(rs.getLong("id"), rs.getString("usuario"), rs.getString("senha"), rs.getString("papel"), rs.getBoolean("logado"));
			}
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public void remover(Usuario usuario){
		String sql = "DELECT FROM usuario WHERE id = ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, usuario.getId());
			
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean logar(Usuario usuario){
		String sql = "SELECT * FROM usuario where usuario like ? AND senha ilike ?";
		Usuario userLido= null;
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getSenha());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userLido = new Usuario(rs.getLong("id"), rs.getString("usuario"), rs.getString("senha"), rs.getString("papel"), rs.getBoolean("logado"));
				// Realiza o login
				userLido.setLogado(true);
				alterar(userLido);
			}
			
			ps.execute();
			ps.close();
			
			if(userLido != null){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		conexao.close();
	}
	
}
