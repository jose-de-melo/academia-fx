package br.com.academia.modelo;

import br.com.academia.utils.Unidades;

/**
 * Classe usada para manipular ritmos dentro de um exercício.
 * 
 * @author José do Carmo de Melo Silva
 */
public class Ritmo implements Comparable<Ritmo>{
	private long id;
	private long idAtividade;
	private double distancia;
	private Hora tempo;
	
	public Ritmo() {
		this(0,new Hora());
	}
	
	public Ritmo(double distancia, Hora minutosGastos) {
		this.distancia = distancia;
		this.tempo = minutosGastos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public Ritmo(String ritmo){
		this();
		String[] ritmoQuebrado = ritmo.split(" ");
		distancia = Double.parseDouble(ritmoQuebrado[0].replace(",", "."));
		
		String tempoGasto = ritmoQuebrado[2];
		tempo.setMinuto(Integer.parseInt(tempoGasto.substring(0, 2)));
		tempo.setSegundo(Integer.parseInt(tempoGasto.substring(3, 5)));
	}
	
	public void setRitmo(String ritmo){
		tempo.setMinuto(Integer.parseInt(ritmo.substring(0, 2)));
		tempo.setSegundo(Integer.parseInt(ritmo.substring(3, 5)));
	}
	
	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public Hora getMinutosGastos() {
		return tempo;
	}

	public void setMinutosGastos(Hora minutosGastos) {
		this.tempo = minutosGastos;
	}

	@Override
	public String toString() {
		return String.format("%1.2f %s: %02d%s%02d%s", distancia, Unidades.DISTANCIA.getUnidade(),tempo.getMinuto(), Unidades.MINUTO.getUnidade(),
							 tempo.getSegundo(), Unidades.SEGUNDO.getUnidade());
	}
	
	public String getTempoRitmo(){
		return String.format("%02d%s%02d%s",tempo.getMinuto(), Unidades.MINUTO.getUnidade(),
				 tempo.getSegundo(), Unidades.SEGUNDO.getUnidade());
	}

	@Override
	public int compareTo(Ritmo ritmo2) {
		Double distanciaRitmo1 = getDistancia(), distanciaRitmo2 = ritmo2.getDistancia();
		
		return distanciaRitmo1.compareTo(distanciaRitmo2);
	}
}