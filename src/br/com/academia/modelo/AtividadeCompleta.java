package br.com.academia.modelo;

import java.util.List;

public final class AtividadeCompleta extends Atividade {
	private long id_atividade_completa;
	private double velocidadeMedia, velocidadeMaxima;
	private Ritmo ritmoMedio, ritmoMaximo;
	private double menorElevacao, maiorElevacao;
	private List<Ritmo> ritmosNoExercicio;
	
	public AtividadeCompleta() {
	}

	public long getId_ativade_completa() {
		return id_atividade_completa;
	}

	public void setId_atividade_completa(long id_ativade_completa) {
		this.id_atividade_completa = id_ativade_completa;
	}

	public double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public double getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	public void setVelocidadeMaxima(double velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
	}

	public Ritmo getRitmoMedio() {
		return ritmoMedio;
	}

	public void setRitmoMedio(Ritmo ritmoMedio) {
		this.ritmoMedio = ritmoMedio;
	}

	public Ritmo getRitmoMaximo() {
		return ritmoMaximo;
	}

	public void setRitmoMaximo(Ritmo ritmoMaximo) {
		this.ritmoMaximo = ritmoMaximo;
	}

	public double getMenorElevacao() {
		return menorElevacao;
	}

	public void setMenorElevacao(double menorElevacao) {
		this.menorElevacao = menorElevacao;
	}

	public double getMaiorElevacao() {
		return maiorElevacao;
	}

	public void setMaiorElevacao(double maiorElevacao) {
		this.maiorElevacao = maiorElevacao;
	}
	
	public void setRitmosNoExercicio(List<Ritmo> ritmosNoExercicio) {
		this.ritmosNoExercicio = ritmosNoExercicio;
	}

	public List<Ritmo> getRitmosNoExercicio() {
		return ritmosNoExercicio;
	}
}