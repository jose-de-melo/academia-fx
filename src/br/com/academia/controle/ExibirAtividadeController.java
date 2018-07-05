package br.com.academia.controle;

import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.Ritmo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExibirAtividadeController {
	private Atividade atividade;

	public ExibirAtividadeController(Atividade atividade) {
		this.atividade = atividade;
	}
	
	
	@FXML TableView<Ritmo> tableRitmos;
	@FXML Label lbAluno, lbTipo, lbData, lbPassos, lbDuracao, lbTempo, lbCalorias, lbDistancia, lbVelME, lbVelMA, lbMAElevacao, lbMEElevacao, lbRitmoME, lbRitmoMA;
	@FXML TableColumn<Ritmo, String> colKm, colTempo;
	
	@FXML
	private void initialize() {
		colKm.setCellValueFactory(new PropertyValueFactory<>("distancia"));
		colTempo.setCellValueFactory(new PropertyValueFactory<>("tempoRitmo"));
		
		if(atividade != null) {
			preencherCampos();
		}
	}

	private void preencherCampos() {
		lbAluno.setText(atividade.getAluno().getNome());
		lbData.setText(atividade.getData().toString());
		lbPassos.setText(String.valueOf(atividade.getPassosDados()));
		lbTipo.setText(atividade.getTipoAtividade());
		lbDuracao.setText(atividade.getDuracao().toString());
		lbTempo.setText(atividade.getTempo().toString());
		lbCalorias.setText(String.valueOf(atividade.getCaloriasPerdidas()));
		lbDistancia.setText(String.valueOf(atividade.getDistancia()));
		if(atividade instanceof AtividadeCompleta) {
			AtividadeCompleta at = (AtividadeCompleta) atividade;
			lbVelMA.setText(String.valueOf(at.getVelocidadeMaxima()));
			lbVelME.setText(String.valueOf(at.getVelocidadeMedia()));
			lbMAElevacao.setText(String.valueOf(at.getMaiorElevacao()));
			lbMEElevacao.setText(String.valueOf(at.getMenorElevacao()));
			lbRitmoMA.setText(at.getRitmoMaximo().getTempoRitmo());
			lbRitmoME.setText(at.getRitmoMedio().getTempoRitmo());
			tableRitmos.setItems(FXCollections.observableList(at.getRitmosNaAtividade()));
		}
	}
}