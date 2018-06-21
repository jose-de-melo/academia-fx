package br.com.academia.modelo;


/**
 * Classe usada para manipulação de horas no formato HH:MM:SS. 
 * 
 * @author José do Carmo de Melo Silva
 *
 */
public class Hora implements Comparable<Hora>{
	private int hora, minuto, segundo;

	public Hora() {
	}

	public Hora(int hora, int minuto, int segundo) {
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}

	public Hora(String horaStr) {
		String[] horaQuebrada = horaStr.split(":");
		
		hora = Integer.parseInt(horaQuebrada[0]);
		minuto = Integer.parseInt(horaQuebrada[1]);
		segundo = Integer.parseInt(horaQuebrada[2]);		
		
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public int getSegundo() {
		return segundo;
	}

	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", hora, minuto, segundo);
	}
	
	public double toMinutes(){
		return (hora * 60) + minuto + (segundo /60);
	}

	@Override
	public int compareTo(Hora hora2) {
		long horaConvertida = (getHora() * 3600) + (getMinuto()*60) + getSegundo(),
				  horaConvertida2 = (hora2.getHora() * 3600 ) + (hora2.getMinuto() * 60) + hora2.getSegundo();
		
		if(horaConvertida > horaConvertida2)
			return 1;
		else if(horaConvertida < horaConvertida2)
			return -1;
		else
			return 0;
	}
}