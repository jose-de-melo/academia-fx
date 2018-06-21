package br.com.academia.modelo;

import java.util.Calendar;
import java.util.Date;


/**
 * Classe para manipular datas.
 * 
 * @author José do Carmo de Melo Silva
 *
 */
public class Data implements Comparable<Data>{
	private int dia, mes, ano;

	public Data() {
	}

	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}

	public Data(String data) {
		String[] dataQuebrada = data.split("/");

		dia = Integer.parseInt(dataQuebrada[0]);
		mes = Integer.parseInt(dataQuebrada[1]);
		ano = Integer.parseInt(dataQuebrada[2]);
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	@Override
	public String toString() {
		return String.format("%02d/%02d/%04d", dia, mes, ano);
	}

	public boolean dataDentroDoPeriodo(Data dataInicial, Data dataFinal){
		if((compareTo(dataInicial) == 1|| compareTo(dataInicial) == 0)  && (compareTo(dataFinal) == -1 || compareTo(dataFinal) == 0)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Data transformaDateEmData(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return new Data(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	@Override
	public int compareTo(Data data) {
		long d1, d2;

		d1 = ((getDia()) + (getMes() * 30) + (getAno() * 365));
		d2 = ((data.getDia()) + (data.getMes() * 30) + (data.getAno() * 365));

		if (d1 > d2)
			return 1;
		else if (d1 < d2)
			return -1;
		else
			return 0;
	}
}