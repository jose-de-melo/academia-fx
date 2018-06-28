package br.com.academia.controle;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.dao.AlunoDAO;

public class GerenciarAtividadesController {

	@FXML CheckBox checkbox;
	@FXML TextField tfVelocidadeME,tfVelocidadeMA, tfRitmoME, tfRitmoMA, tfMEElevacao, tfMAElevacao;
	@FXML Button cadastrarAlterar, excluir, alterar, cancelar, sair, cdRitmos;
	@FXML ComboBox<Aluno> comboBox;
	
	private ArrayList<Ritmo> ritmos;
	
	private List<Aluno> alunos = new AlunoDAO().listar();
	
	@FXML
	private void initialize(){
		acaoCheckBox();
		comboBox.setItems(FXCollections.observableList(alunos));
		if(!alunos.isEmpty())
			comboBox.setValue(alunos.get(0));
	}
	
	@FXML
	void acaoCheckBox(){
		if(checkbox.isSelected()){
			ativarDesativarTextField(false, tfMAElevacao, tfMEElevacao, tfRitmoMA, tfRitmoME, tfVelocidadeMA, tfVelocidadeME);
		}else{
			ativarDesativarTextField(true, tfMAElevacao, tfMEElevacao, tfRitmoMA, tfRitmoME, tfVelocidadeMA, tfVelocidadeME);
		}
	}
	
	@FXML
	void cadastrarRitmos(){
		
		
		
		
	}
	
	
	private void ativarDesativarTextField(Boolean ativar, TextField... campos){
		for (TextField textField : campos) {
			textField.setDisable(ativar);
		}
	}
}
