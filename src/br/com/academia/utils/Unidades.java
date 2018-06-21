package br.com.academia.utils;

/**
 * Contém informações sobre todas as unidades usadas no programa.
 * 
 * @author José do Carmo de Melo Silva
 *
 */


public enum Unidades {
		DISTANCIA("Distância", "Km"), CALORIA("Caloria", "kcal"), ELEVACAO("Elevacão", "m"),
		MINUTO("Minuto", "\'"), SEGUNDO("Segundo", "\""), VELOCIDADE("Velocidade", "Km/h"),
		RITMO("Ritmo", "/Km"), PESO("Peso", "kg"), ALTURA("Altura", "m"), PASSOS("Passos", "Número de Passos");
		
		private String nome;
		private String unidade;
		
		
		private Unidades(String nome, String unidade) {
			this.nome = nome;
			this.unidade = unidade;
		}


		public String getNome() {
			return nome;
		}


		public void setNome(String nome) {
			this.nome = nome;
		}


		public String getUnidade() {
			return unidade;
		}


		public void setUnidade(String unidade) {
			this.unidade = unidade;
		}
		
}