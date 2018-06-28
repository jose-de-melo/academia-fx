package br.com.academia.modelo;

public class Usuario {
		private long id;
		private String usuario, senha, papel;
		private boolean logado;
		
		public Usuario(long id, String usuario, String senha, String papel, boolean logado) {
			this.id = id;
			this.usuario = usuario;
			this.senha = senha;
			this.papel = papel;
			this.logado = logado;
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getSenha() {
			return senha;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public String getPapel() {
			return papel;
		}
		public void setPapel(String papel) {
			this.papel = papel;
		}

		public boolean isLogado() {
			return logado;
		}

		public void setLogado(boolean logado) {
			this.logado = logado;
		}

		@Override
		public String toString() {
			return String.format("%d : %s : %s : %s : %s", id, usuario, senha, papel, logado);
		}
}