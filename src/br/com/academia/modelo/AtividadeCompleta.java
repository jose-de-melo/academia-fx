package br.com.academia.modelo;

import java.util.ArrayList;

public final class AtividadeCompleta extends Atividade {
	private int id_ativade_completa;
	private double velocidadeMedia, velocidadeMaxima;
	private Ritmo ritmoMedio, ritmoMaximo;
	private double menorElevacao, maiorElevacao;
	private ArrayList<Ritmo> ritmosNoExercicio;
	
	public AtividadeCompleta() {
		ritmoMaximo = new Ritmo();
		ritmoMaximo = new Ritmo();
		ritmosNoExercicio = new ArrayList<Ritmo>();
	}

	public int getId_ativade_completa() {
		return id_ativade_completa;
	}

	public void setId_ativade_completa(int id_ativade_completa) {
		this.id_ativade_completa = id_ativade_completa;
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
	
	public void setRitmosNoExercicio(ArrayList<Ritmo> ritmosNoExercicio) {
		this.ritmosNoExercicio = ritmosNoExercicio;
	}

	public ArrayList<Ritmo> getRitmosNoExercicio() {
		return ritmosNoExercicio;
	}
}