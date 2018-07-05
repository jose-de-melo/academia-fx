package br.com.academia.controle;

import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.academia.dados.ManipuladorDeDados;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.dao.AlunoDAO;
import br.com.academia.modelo.dao.AtividadeDAO;
import br.com.academia.utils.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RelatorioController {

	
	
	@FXML ComboBox<Aluno> boxAluno;
	@FXML Label lbDuracao, lbDistancia, lbCalorias, lbPassos, lbVelocidade;
	@FXML AnchorPane root;
	
	AlunoDAO aDAO = new AlunoDAO();
	AtividadeDAO atDAO = new AtividadeDAO();
	
	@FXML
	private void initialize() {
		boxAluno.setItems(FXCollections.observableList(aDAO.listar()));
		boxAluno.setValue(boxAluno.getItems().get(0));
		actionAluno();
	}
	
	
	@FXML
	void fechar() {
		((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA);
		((BorderPane) root.getScene().getRoot()).setCenter(null);
	}
	
	@FXML
	void actionAluno() {
		if(boxAluno.getValue() != null) {
			List<Atividade> atividadesDoAluno = atDAO.atividadesDoAluno(boxAluno.getValue().getId());
			
			
			atividadesDoAluno.sort(new Comparator<Atividade>() {
				@Override
				public int compare(Atividade atv1, Atividade atv2) {
					return (-1) * Double.compare(atv1.getDuracao().toMinutes(), atv2.getDuracao().toMinutes());
				}
			});
			definirTextoLabel(lbDuracao, atividadesDoAluno.get(0).getDuracao() + " : " + atividadesDoAluno.get(0).getData());
			
			
			
			atividadesDoAluno.sort(new Comparator<Atividade>() {
				@Override
				public int compare(Atividade atv1, Atividade atv2) {
					return (-1) * Double.compare(atv1.getDistancia(), atv2.getDistancia());
				}
			});
			definirTextoLabel(lbDistancia, String.format("%1.2f", atividadesDoAluno.get(0).getDistancia()) + " : " + atividadesDoAluno.get(0).getData());
			
			
			atividadesDoAluno.sort(new Comparator<Atividade>() {
				@Override
				public int compare(Atividade atv1, Atividade atv2) {
					return (-1) * Double.compare(atv1.getCaloriasPerdidas(), atv2.getCaloriasPerdidas());
				}
			});
			definirTextoLabel(lbCalorias, String.format("%1.2f", atividadesDoAluno.get(0).getCaloriasPerdidas()) + " : " + atividadesDoAluno.get(0).getData());
			
			
			atividadesDoAluno.sort(new Comparator<Atividade>() {
				@Override
				public int compare(Atividade atv1, Atividade atv2) {
					return (-1) * Integer.compare(atv1.getPassosDados(), atv2.getPassosDados());
				}
			});
			definirTextoLabel(lbPassos, atividadesDoAluno.get(0).getPassosDados()+ " : " + atividadesDoAluno.get(0).getData());
			
			if(ManipuladorDeDados.isListOfAtividadeCompleta(atividadesDoAluno)) {
				List<AtividadeCompleta> atividadesCompletas = new ArrayList<AtividadeCompleta>();
				
				for (Atividade atividade : atividadesDoAluno) {
					if(atividade instanceof AtividadeCompleta) {
						atividadesCompletas.add((AtividadeCompleta)atividade);
					}
				}
				
				atividadesCompletas.sort(new Comparator<AtividadeCompleta>() {

					@Override
					public int compare(AtividadeCompleta atv1, AtividadeCompleta atv2) {
						return (-1) * Double.compare(atv1.getVelocidadeMaxima(), atv2.getVelocidadeMaxima());
					}
				});
				
				definirTextoLabel(lbVelocidade, String.format("%1.2f", atividadesCompletas.get(0).getVelocidadeMaxima()) +  " : " + atividadesDoAluno.get(0).getData());
			}else {
				definirTextoLabel(lbVelocidade, " ------");
			}
		}
	}
	
	private void definirTextoLabel(Label lb, String texto) {
		lb.setText(texto);
	}
	
}
