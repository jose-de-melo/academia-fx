package br.com.academia.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;

public class OperacoesSobreExercicios {

	public static List<Object> getTiposdeExercicios(List<Atividade> exercicios){
		List<Object> tipos = new ArrayList<Object>();

		for (Atividade exercicio : exercicios) {
			if(!tipos.contains(exercicio.getTipoExercicio())){
				tipos.add(exercicio.getTipoExercicio());
			}
		}
		return tipos;
	}

	public static boolean isListOfExerciciosAerobicos(List<Atividade> exercicios){
		for (Atividade exercicio : exercicios) {
			if(exercicio instanceof AtividadeCompleta){
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Atividade> getExerciciosDoTipo(ArrayList<Atividade> exercicios, String tipoExercicio) {
		ArrayList<Atividade> exerciciosDoTipo = new ArrayList<Atividade>();

		for (Atividade exer : exercicios) {
			if(exer.getTipoExercicio().compareToIgnoreCase(tipoExercicio) == 0){
				exerciciosDoTipo.add(exer);
			}
		}
		return exerciciosDoTipo;
	}
}