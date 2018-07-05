package br.com.academia.modelo;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class AcademiaFXChartFactory {
	
	
	
	
	
	public static Chart createGeneralBarChart(ArrayList<Atividade> atividades, String tipoDeDado){

		final CategoryAxis eixoX = new CategoryAxis();
		final NumberAxis eixoY = new NumberAxis();
		eixoX.setLabel("Dia");

		final BarChart<String, Number> generalBarChart = new BarChart<String, Number>(eixoX, eixoY);

		final Series<String, Number> series = new Series<>();

		series.setName(atividades.get(0).getAluno().toString());
		List<DadosGrafico> dados = null;
		
		
		if(tipoDeDado.compareTo("D") == 0){
			generalBarChart.setTitle("Distância Percorrida das Atividades X Dia");
			eixoY.setLabel("Km");
			dados = DadosGrafico.valuesToGeneralBarChart(atividades, true, false, false);
		}

		if(tipoDeDado.compareTo("MC") == 0){
			generalBarChart.setTitle("Média de Calorias Perdidas nas Atividades X Dia");
			eixoY.setLabel("Kcal");
			dados = DadosGrafico.valuesToGeneralBarChart(atividades, false, true, false);
		}
		if(tipoDeDado.compareTo("C") == 0){
			generalBarChart.setTitle("Calorias Perdidas nas Atividades X Dia");
			eixoY.setLabel("Kcal");
			dados = DadosGrafico.valuesToGeneralBarChart(atividades, false, true, false);
		}
		if(tipoDeDado.compareTo("P") == 0){
			generalBarChart.setTitle("Passos Dados nas Atividades X Dia");
			eixoY.setLabel("Número de Passos");
			dados = DadosGrafico.valuesToGeneralBarChart(atividades, false, false, true);
		}
		if(tipoDeDado.compareTo("MD") == 0){
			generalBarChart.setTitle("Distância Média Percorrida nas Atividades X Dia");
			eixoY.setLabel("Km");
			dados = DadosGrafico.valuesToGeneralBarChart(atividades, true, false,false);
		}
		
		for (DadosGrafico dado : dados) {
			if(tipoDeDado.compareTo("MD") == 0 ||tipoDeDado.compareTo("MC") == 0 ){
				series.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor.doubleValue() / dado.numeroDeAtividades));
			}
			else{
				series.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
			}
		}
		generalBarChart.getData().add(series);
		return generalBarChart;
	}
	
	
	
	
	
	
	
	

	public static Chart createLineChart(ArrayList<Atividade> atividades, Character tipoDeDado) {
		CategoryAxis dataAxis = new CategoryAxis();
		NumberAxis valorAxis = new NumberAxis();

		List<XYChart.Series<String, Number>> series;

		LineChart<String, Number> lineChart = new LineChart<String, Number>(dataAxis, valorAxis);

		if(tipoDeDado.equals('D')){
			lineChart.setTitle("Aluno X Maior Duração de uma Atividade X Dia");
			series = gerarSeriesAlunoXDuracao(atividades, valorAxis);
		}else{
			lineChart.setTitle("Aluno X Maior Perda de Calorias X Dia");
			series = gerarSeriesAlunoXCalorias(atividades, valorAxis);
		}

		for (XYChart.Series<String, Number> serie : series) {
			lineChart.getData().add(serie);
		}

		return lineChart;
	}

	
	
	
	
	
	
	
	public static Chart createBarChart(ArrayList<Atividade> atividades, String tipoDeDado){

		final CategoryAxis eixoX = new CategoryAxis();
		final NumberAxis eixoY = new NumberAxis();
		eixoX.setLabel("Dia");

		final BarChart<String, Number> barChart = new BarChart<String, Number>(eixoX, eixoY);

		final Series<String, Number> series = new Series<>();

		List<DadosGrafico> dados = null;
		if(tipoDeDado.compareTo("Du") == 0){
			barChart.setTitle("Duração das Atividades X Dia");
			eixoY.setLabel("Minutos");
			dados = DadosGrafico.valuesToBarChart(atividades, true,false, false,false, false, false);
		}

		if(tipoDeDado.compareTo("Di") == 0){
			barChart.setTitle("Distância Percorrida  nas Atividades X Dia");
			eixoY.setLabel("Km");
			dados = DadosGrafico.valuesToBarChart(atividades, false, true,false,false, false, false);
		}
		if(tipoDeDado.compareTo("C") == 0){
			barChart.setTitle("Calorias Perdidas nas Atividades X Dia");
			eixoY.setLabel("Kcal");
			dados = DadosGrafico.valuesToBarChart(atividades, false, false,true,false, false, false);
		}
		if(tipoDeDado.compareTo("P") == 0){
			barChart.setTitle("Passos Dados nas Atividades X Dia");
			eixoY.setLabel("Número de Passos");
			dados = DadosGrafico.valuesToBarChart(atividades, false, false,false,true, false, false);
		}
		if(tipoDeDado.compareTo("V") == 0){
			barChart.setTitle("Velocidade Média nas Atividades X Dia");
			eixoY.setLabel("Km/h");
			dados = DadosGrafico.valuesToBarChart(atividades, false, false,false,false, true, false);
		}

		if(tipoDeDado.compareTo("R") == 0){
			barChart.setTitle("Ritmo Médio nas Atividades X Dia");
			eixoY.setLabel("Minutos/Km");
			dados = DadosGrafico.valuesToBarChart(atividades, false, false,false,false, false, true);
		}

		for (DadosGrafico dado : dados) {
			if(tipoDeDado.compareTo("R")  == 0 || tipoDeDado.compareTo("V")  == 0 )
				series.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor.doubleValue() / dado.numeroDeAtividades));
			else
				series.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
		}
		series.setName(atividades.get(0).getAluno().getNome());

		barChart.getData().add(series);

		return barChart;
	}
	
	
	
	
	
	

	public static List<XYChart.Series<String, Number>> gerarSeriesAlunoXDuracao(ArrayList<Atividade> atividades, NumberAxis valorAxis) {
		valorAxis.setLabel("Duração (em minutos)");

		List<XYChart.Series<String, Number>> series = new ArrayList<XYChart.Series<String, Number>>();

		XYChart.Series<String, Number> serie;
		List<DadosGrafico> dados = DadosGrafico.valuesToLineChart(atividades, false, true);

		int serieJaCriada = 0;
		for (DadosGrafico dado : dados) {
			serieJaCriada = 0;
			for (Series<String,Number> serieList : series) {
				if(serieList.getName().compareTo(dado.getNomeAluno()) ==0){
					serieList.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
					serieJaCriada = 1;
					break;
				}
			}
			if(serieJaCriada == 0){
				serie = new Series<String, Number>();
				serie.setName(dado.getNomeAluno());
				serie.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
				series.add(serie);
			}
		}

		return series;
	}
	
	
	
	
	
	
	
	

	public static List<XYChart.Series<String, Number>> gerarSeriesAlunoXCalorias(ArrayList<Atividade> atividades, NumberAxis valorAxis) {
		valorAxis.setLabel("Calorias (em Kcal)");

		List<XYChart.Series<String, Number>> series = new ArrayList<XYChart.Series<String, Number>>();

		XYChart.Series<String, Number> serie;
		List<DadosGrafico> dados = DadosGrafico.valuesToLineChart(atividades, true, false);

		int serieJaCriada = 0;
		for (DadosGrafico dado : dados) {
			serieJaCriada = 0;
			for (Series<String,Number> serieList : series) {
				if(serieList.getName().compareTo(dado.getNomeAluno()) ==0){
					serieList.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
					serieJaCriada = 1;
					break;
				}
			}
			if(serieJaCriada == 0){
				serie = new Series<String, Number>();
				serie.setName(dado.getNomeAluno());
				serie.getData().add(new XYChart.Data<String, Number>(dado.data, dado.valor));
				series.add(serie);
			}
		}

		return series;
	}
	
	
	
	
	
	
	
	


	public static class DadosGrafico {
		private String data, emailAluno, nomeAluno;
		private Number valor;
		private int numeroDeAtividades;

		public DadosGrafico(String data, String emailAluno, String nomeAluno, Number valor) {
			this.data = data;
			this.emailAluno = emailAluno;
			this.nomeAluno = nomeAluno;
			this.valor = valor;
			this.numeroDeAtividades = 1;
		}

		public int getNumeroDeAtividades() {
			return numeroDeAtividades;
		}

		public void setNumeroDeAtividades(int numeroDeAtividades) {
			this.numeroDeAtividades = numeroDeAtividades;
		}



		public String getNomeAluno() {
			return nomeAluno;
		}

		public void setNomeAluno(String nomeAluno) {
			this.nomeAluno = nomeAluno;
		}


		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getEmailAluno() {
			return emailAluno;
		}
		public void setEmailAluno(String emailAluno) {
			this.emailAluno = emailAluno;
		}
		public Number getValor() {
			return valor;
		}
		public void setValor(Number valor) {
			this.valor = valor;
		}

		public static List<DadosGrafico> valuesToGeneralBarChart(ArrayList<Atividade> atividades, boolean distancia, boolean calorias, boolean passos){
			List<DadosGrafico> dados = new ArrayList<AcademiaFXChartFactory.DadosGrafico>();

			int atividadeJaTemDadosNaData = 0;

			for (Atividade atividade : atividades) {
				atividadeJaTemDadosNaData = 0;
				for (DadosGrafico dado : dados) {
					if(dado.getData().compareTo(atividade.getData().toString()) == 0){
						dado.numeroDeAtividades++;
						atividadeJaTemDadosNaData = 1;
						if(calorias){
							dado.valor = dado.valor.doubleValue() + atividade.getCaloriasPerdidas();
						}else if(passos)
							dado.valor = dado.valor.intValue() + atividade.getPassosDados();
						else if(distancia)
							dado.valor = dado.valor.doubleValue() + atividade.getDistancia();
						break;
					}
				}

				if(atividadeJaTemDadosNaData == 0){
					if(calorias){
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getCaloriasPerdidas()));
					}
					else if(distancia){
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getDistancia()));
					}
					else if(passos){
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getPassosDados()));
					}
				}
			}
			return dados;
		}

		public static List<DadosGrafico> valuesToBarChart(ArrayList<Atividade> atividades, boolean duracao, boolean distancia, boolean calorias, boolean passos, boolean velocidade, boolean ritmo){
			List<DadosGrafico> dados = new ArrayList<AcademiaFXChartFactory.DadosGrafico>();

			int atividadeJaTemDadosNaData = 0;

			for (Atividade atividade : atividades) {
				atividadeJaTemDadosNaData = 0;
				for (DadosGrafico dado : dados) {
					if(dado.getData().compareTo(atividade.getData().toString()) == 0){
						dado.numeroDeAtividades++;
						atividadeJaTemDadosNaData = 1;
						if(calorias){
							dado.valor = dado.valor.doubleValue() + atividade.getCaloriasPerdidas();
						}else if(duracao){
							dado.valor = dado.valor.doubleValue() + atividade.getDuracao().toMinutes();
						}else if(passos)
							dado.valor = dado.valor.intValue() + atividade.getPassosDados();
						else if(distancia)
							dado.valor = dado.valor.doubleValue() + atividade.getDistancia();
						if(atividade instanceof AtividadeCompleta){
							if(velocidade)
								dado.valor = dado.valor.doubleValue() + ((AtividadeCompleta)atividade).getVelocidadeMedia();
							if(ritmo)
								dado.valor = dado.valor.doubleValue() + ((AtividadeCompleta)atividade).getRitmoMedio().getMinutosGastos().toMinutes();
						}						
						break;
					}
				}

				if(atividadeJaTemDadosNaData == 0){
					if(calorias)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getCaloriasPerdidas()));
					else if(duracao)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getDuracao().toMinutes()));
					else if(distancia)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getDistancia()));
					else if(passos)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getPassosDados()));
					if(atividade instanceof AtividadeCompleta){
						if(velocidade)
							dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), ((AtividadeCompleta)atividade).getVelocidadeMedia()));
						if(ritmo)
							dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), ((AtividadeCompleta)atividade).getRitmoMedio().getMinutosGastos().toMinutes()));
					}
				}
			}

			return dados;
		}

		public static List<DadosGrafico> valuesToLineChart(ArrayList<Atividade> atividades, boolean calorias, boolean duracao){
			List<DadosGrafico> dados = new ArrayList<AcademiaFXChartFactory.DadosGrafico>();

			int alunoJaTemDadosNaData = 0;

			for (Atividade atividade : atividades) {
				alunoJaTemDadosNaData = 0;
				for (DadosGrafico dado : dados) {
					if(dado.getEmailAluno().compareTo(atividade.getAluno().getEmail()) == 0 && dado.getData().compareTo(atividade.getData().toString()) == 0){
						alunoJaTemDadosNaData = 1;
						if(calorias){
							if(atividade.getCaloriasPerdidas() > dado.getValor().doubleValue())
								dado.valor = atividade.getCaloriasPerdidas();
						}else if(duracao){
							if(atividade.getDuracao().toMinutes() > dado.valor.doubleValue())
								dado.valor = atividade.getDuracao().toMinutes();
						}
						break;
					}
				}
				if(alunoJaTemDadosNaData == 0){
					if(calorias)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getCaloriasPerdidas()));
					else if(duracao)
						dados.add(new DadosGrafico(atividade.getData().toString(), atividade.getAluno().getEmail(), atividade.getAluno().getNome(), atividade.getDuracao().toMinutes()));
				}
			}
			return dados;
		}

		@Override
		public String toString() {
			return data + " : " + valor;
		}

		
	}
}