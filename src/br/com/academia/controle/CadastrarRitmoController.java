package br.com.academia.controle;

import java.util.ArrayList;

import br.com.academia.modelo.Hora;
import br.com.academia.modelo.Ritmo;
import br.com.academia.utils.Constantes;
import br.com.academia.utils.Masks;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CadastrarRitmoController {
	private ArrayList<Ritmo> ritmos;

	public CadastrarRitmoController(ArrayList<Ritmo> ritmos) {
		this.ritmos = ritmos;
	}

	public ArrayList<Ritmo> getRitmos() {
		return ritmos;
	}

	@FXML AnchorPane root;
	@FXML TableView<Ritmo> table;
	@FXML TableColumn<Ritmo, String> colQuilometragem, colMinutosGastos;
	@FXML Button btnAlterar, btnCadastrar, btnExcluir, btnConcluir;
	@FXML TextField tfQuilometragem, tfMinutos, tfSegundos;
	@FXML Label mensagem;

	private Ritmo ritmoSelecionado;


	@FXML
	private void initialize() {
		Masks.mascaraNumero(tfQuilometragem);
		Masks.mascaraNumero(tfMinutos);
		Masks.mascaraNumero(tfSegundos);
		
		colQuilometragem.setCellValueFactory(new PropertyValueFactory<>("distancia"));
		colMinutosGastos.setCellValueFactory(new PropertyValueFactory<>("tempoRitmo"));
		atualizarTabela();
	}

	@FXML
	void alterar() {
		if(ritmoSelecionado != null) {
			carregarCampos(ritmoSelecionado);
			btnCadastrar.setText("Alterar");
			btnAlterar.setDisable(true);
		}
	}

	private void carregarCampos(Ritmo ritmo) {
		tfQuilometragem.setText(String.valueOf(ritmo.getDistancia()));
		tfMinutos.setText(String.valueOf(ritmo.getMinutosGastos().getMinuto()));
		tfSegundos.setText(String.valueOf(ritmo.getMinutosGastos().getSegundo()));
	}

	private void limparCampos() {
		tfQuilometragem.setText("");
		tfMinutos.setText("");
		tfSegundos.setText("");
		btnExcluir.setDisable(true);
		btnAlterar.setDisable(true);
	}

	@FXML
	void cadastrar() {
		if(btnCadastrar.getText().equals("Cadastrar")) {
			if(verificarCampos()) {
				Ritmo ritmo = new Ritmo();
				Hora hora = new Hora();

				hora.setMinuto(Integer.valueOf(tfMinutos.getText()));
				hora.setSegundo(Integer.valueOf(tfSegundos.getText()));
				hora.setHora(0);

				ritmo.setMinutosGastos(hora);
				ritmo.setDistancia(Double.valueOf(tfQuilometragem.getText()));

				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Cadastrado!");
				ritmos.add(ritmo);
				atualizarTabela();
				limparCampos();
			}else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Forneça valores válidos para os todos os campos!");
			}
		}
		else {
			if(verificarCampos()) {
				Hora hora = new Hora();
				hora.setMinuto(Integer.valueOf(tfMinutos.getText()));
				hora.setSegundo(Integer.valueOf(tfSegundos.getText()));
				hora.setHora(0);

				ritmos.get(table.getSelectionModel().getSelectedIndex()).setMinutosGastos(hora);
				ritmos.get(table.getSelectionModel().getSelectedIndex()).setDistancia(Double.valueOf(tfQuilometragem.getText()));
				limparCampos();
				table.refresh();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Ritmo alterado!");
				btnCadastrar.setText("Cadastrar");
			}else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Forneça valores válidos para os todos os campos!");
			}
		}
	}

	private boolean verificarCampos() {
		if(tfQuilometragem.getText().equals("") || tfQuilometragem.getText().equals("") || tfQuilometragem.getText().equals("")) {
			return false;
		}else {
			return true;
		}
	}

	private void atualizarTabela() {
		if(ritmos.isEmpty()) {
			btnConcluir.setDisable(true); 
		}else {
			btnConcluir.setDisable(false);
		}
		table.setItems(FXCollections.observableList(ritmos));
	}

	@FXML
	void excluir() {
		if(ritmoSelecionado != null) {
			ritmos.remove(ritmoSelecionado);
			mensagem.setText("Ritmo removido!");
			table.refresh();
			if(ritmos.size() == 0)
				btnConcluir.setDisable(true);
			
			btnExcluir.setDisable(true);
			btnAlterar.setDisable(true);
		}
	}

	@FXML
	void concluirCadastro() {
		((Stage) root.getScene().getWindow()).close();
		((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA);
	}


	@FXML
	void tableAction() {
		ritmoSelecionado = table.getSelectionModel().getSelectedItem();
		btnAlterar.setDisable(false);
		btnExcluir.setDisable(false);
	}


	@FXML
	void limparMensagem() {
		mensagem.setText("");
	}

}
