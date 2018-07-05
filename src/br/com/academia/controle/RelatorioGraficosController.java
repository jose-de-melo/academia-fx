package br.com.academia.controle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import br.com.academia.modelo.AcademiaFXChartFactory;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.Data;
import br.com.academia.modelo.dao.AlunoDAO;
import br.com.academia.modelo.dao.AtividadeDAO;
import br.com.academia.out.Mensagens;
import br.com.academia.utils.Constantes;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RelatorioGraficosController implements Initializable {

	@FXML private DatePicker dataInicio, dataFinal;
	@FXML private ComboBox<Aluno> comboAluno;
	@FXML private ComboBox<String> comboTipo;
	@FXML private RadioButton radioLinha, radioBarra, radioGeral;
	@FXML private BorderPane borderPane;
	@FXML private AnchorPane root;
	@FXML private Button btnGerar;

	private final ToggleGroup group = new ToggleGroup();
	private final List<String> listTipo = new ArrayList<>();

	private static final int GRAFICO_BARRA = 1;
	private static final int GRAFICO_LINHA = 2;
	private static final int GRAFICO_GERAL = 3;


	private AtividadeDAO dao = new AtividadeDAO();
	private AlunoDAO daoAluno = new AlunoDAO();


	private String tipoGraficoSelecionado = "";


	public RelatorioGraficosController() {
		this.borderPane = new BorderPane();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Adiciona os radios ao grupo
		this.radioBarra.setToggleGroup(group);
		this.radioBarra.setSelected(true);
		this.radioLinha.setToggleGroup(group);
		this.radioGeral.setToggleGroup(group);

		radioChange(GRAFICO_BARRA);


		comboAluno.setItems(FXCollections.observableList(daoAluno.listar()));
		comboAluno.setValue(comboAluno.getItems().get(0));


		// Chamado quando um radio é selecionado.
		group.selectedToggleProperty().addListener( (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
			if ( group.getSelectedToggle() == radioBarra ) 
				radioChange(GRAFICO_BARRA);

			else if (group.getSelectedToggle() == radioLinha)
				radioChange(GRAFICO_LINHA);

			else if( group.getSelectedToggle() == radioGeral )
				radioChange(GRAFICO_GERAL);

		});// selectedToggleProperty

	}// initialize



	@FXML
	private void actionGerar() {
		if(validarCampos()) {
			Chart graficoGerado = gerarGrafico();
			borderPane.setCenter(graficoGerado);
		}
	}

	private Chart gerarGrafico() {
		if(tipoGraficoSelecionado.compareTo("L") == 0) {
			return gerarGraficoLinha();
		}else if(tipoGraficoSelecionado.compareTo("B") == 0){
			return gerarGraficoBarra();
		}else{
			return gerarGraficoBarraGeral();
		}
	}


	private Chart gerarGraficoBarraGeral() {
		Data dataInicial = Data.getValueOf(dataInicio.getValue());
		Data dataFim = Data.getValueOf(dataFinal.getValue());

		ArrayList<Atividade> atividades = (ArrayList<Atividade>) dao.exerciciosDoAlunoNoPeriodo(dataInicial, dataFim, comboAluno.getSelectionModel().getSelectedItem().getId());
		atividades.sort(new Comparator<Atividade>() {
			@Override
			public int compare(Atividade at1, Atividade at2) {
				return at1.getData().compareTo(at2.getData());
			}
		});

		if(atividades.isEmpty()){
			exibirMensagemDeAlerta();
			return null;
		}else{
			Chart grafico = null;

			switch (comboTipo.getSelectionModel().getSelectedIndex()) {
			case 0:
				grafico = AcademiaFXChartFactory.createGeneralBarChart(atividades, "P");
				break;
			case 1:
				grafico = AcademiaFXChartFactory.createGeneralBarChart(atividades, "MD");
				break;
			case 2:
				grafico = AcademiaFXChartFactory.createGeneralBarChart(atividades, "D");
				break;
			case 3:
				grafico = AcademiaFXChartFactory.createGeneralBarChart(atividades, "MC");
				break;
			case 4:
				grafico = AcademiaFXChartFactory.createGeneralBarChart(atividades, "C");
				break;
			default:
				break;
			}
			return grafico;
		}
	}


	private Chart gerarGraficoBarra() {

		Data dataInicial = Data.getValueOf(dataInicio.getValue());
		Data dataFim = Data.getValueOf(dataFinal.getValue());

		ArrayList<Atividade> atividades = (ArrayList<Atividade>) dao.exerciciosDoAlunoNoPeriodo(dataInicial, dataFim, comboAluno.getSelectionModel().getSelectedItem().getId());
		atividades.sort(new Comparator<Atividade>() {
			@Override
			public int compare(Atividade at1, Atividade at2) {
				return at1.getData().compareTo(at2.getData());
			}
		});

		if(atividades.isEmpty()){
			exibirMensagemDeAlerta();
			return null;
		}else{
			Chart grafico = null;
			switch (comboTipo.getSelectionModel().getSelectedIndex()) {
			case 0:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "Du");
				break;
			case 1:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "Di");
				break;
			case 2:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "C");
				break;
			case 3:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "P");
				break;
			case 4:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "V");
				break;
			case 5:
				grafico = AcademiaFXChartFactory.createBarChart(atividades, "R");
				break;
			default:
				break;
			}

			return grafico;
		}
	}

	@FXML
	void fechar() {
		((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA);
		((BorderPane) root.getScene().getRoot()).setCenter(null);
	}


	private Chart gerarGraficoLinha() {
		Data dataInicial = Data.getValueOf(dataInicio.getValue());
		Data dataFim = Data.getValueOf(dataFinal.getValue());

		List<Atividade> atividades = dao.atividadesNoPeriodo(dataInicial, dataFim);

		atividades.sort(new Comparator<Atividade>() {

			@Override
			public int compare(Atividade atv1, Atividade atv2) {
				return atv1.getData().compareTo(atv2.getData());
			}

		});

		if(atividades.isEmpty()){
			exibirMensagemDeAlerta();
			return null;
		}else{
			if(comboTipo.getSelectionModel().getSelectedIndex() == 0) {
				return AcademiaFXChartFactory.createLineChart((ArrayList<Atividade>) atividades, 'D');
			}else {
				return AcademiaFXChartFactory.createLineChart((ArrayList<Atividade>) atividades, 'C');
			}
		}
	}


	private void exibirMensagemDeAlerta() {
		Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Relatório de Gráficos", "Erro", "O aluno selecionado não praticou nenhuma\natividade no período especificado!");
	}


	/*
	 * Valida os parametro da consulta
	 */
	@FXML
	private boolean validarCampos() {

		if( group.getSelectedToggle() == null ) { Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Erro", "Erro no campo tipo de gráfico", "Selecione um tipo de gráfico!"); return false; }

		if( dataInicio.getValue() == null || dataFinal.getValue() == null ) { Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Erro", "Erro nos campos Data", "Selecione duas Datas válidas!"); return false; }

		if( this.comboTipo.getValue() == null ) { Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Erro", "Erro no campo tipo de dado do gráfico", "Selecione um tipo de dado a ser exibido no gráfico!"); return false; }

		if(Data.getValueOf(dataInicio.getValue()).compareTo(Data.getValueOf(dataFinal.getValue())) == 1) {  Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Erro", "Erro nos campos Data", "A data inicial não pode ser maior que a final!"); return false; }

		return true;
	}// isValidParametros

	/*
	 * Verifica qual o tipo do modelo do grafico e insere os valores no comboBox
	 *  de acordo com o modelo selecionado.
	 */
	private void radioChange( final int tipo ) {
		switch (tipo) {
		case GRAFICO_BARRA: 
			fillList("Duração", "Distância", "Calorias Perdidas", "Passos", "Velocidade", "Ritmo Médio"); 
			comboAluno.setDisable(false);
			tipoGraficoSelecionado = "B";
			break;
		case GRAFICO_LINHA: 
			fillList("Aluno x Duração", "Aluno x Calorias Perdidas"); 
			comboAluno.setDisable(true);
			tipoGraficoSelecionado = "L";
			break;
		case GRAFICO_GERAL: 
			fillList("Total de Passos", "Distância Média Percorrida", "Distância Total Percorrida", "Média de Calorias Perdidas", "Calorias Perdidas"); 
			comboAluno.setDisable(false);
			tipoGraficoSelecionado = "BGeral";
			break;

		default: break;
		}
		comboTipo.setValue(comboTipo.getItems().get(0));
	}// radioChange

	/*
	 * Preenche a lista como os novos valores passados
	 */
	private void fillList( String... strings ) {
		this.listTipo.clear();

		for (String string : strings) 
			this.listTipo.add(string);

		this.comboTipo.setItems(FXCollections.observableArrayList(listTipo)); 
	}// fillList
}// class DesempenhoController
