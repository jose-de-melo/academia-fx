package br.com.academia.modelo;

/**
 * Classe usada para manipular o tempo gasto em um exercício.
 * 
 * @author José do Carmo de Melo Silva
 *
 */
public class Tempo {
	private Hora horaInicio, horaTermino;

	public Tempo() {
		horaTermino = new Hora();
		horaInicio = new Hora();
	}

	public Tempo(Hora tempoInicial, Hora tempoFinal) {
		this.horaInicio = tempoInicial;
		this.horaTermino = tempoFinal;
	}

	public Tempo(String tempo) {
		this();
		String[] tempoQuebrado = tempo.split(" - ");
		horaInicio.setMinuto(Integer.parseInt(tempoQuebrado[0].substring(0, tempoQuebrado[0].indexOf(":"))));
		horaInicio.setSegundo(Integer.parseInt(tempoQuebrado[0].substring(tempoQuebrado[0].indexOf(":") +1)));
		
		horaTermino.setMinuto(Integer.parseInt(tempoQuebrado[1].substring(0, tempoQuebrado[1].indexOf(":"))));
		horaTermino.setSegundo(Integer.parseInt(tempoQuebrado[1].substring(tempoQuebrado[1].indexOf(":") +1)));
		
	}

	public Hora getTempoInicial() {
		return horaInicio;
	}

	public void setTempoInicial(Hora tempoInicial) {
		this.horaInicio = tempoInicial;
	}

	public Hora getTempoFinal() {
		return horaTermino;
	}

	public void setTempoFinal(Hora tempoFinal) {
		this.horaTermino = tempoFinal;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d - %02d:%02d",horaInicio.getMinuto(), horaInicio.getSegundo(), horaTermino.getMinuto(), horaTermino.getSegundo());
	}
}