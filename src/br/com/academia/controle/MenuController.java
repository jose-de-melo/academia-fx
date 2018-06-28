package br.com.academia.controle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import br.com.academia.Academia;
import br.com.academia.dados.ManipuladorDeDados;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.UsuarioDAO;
import br.com.academia.utils.Chooser;

public class MenuController {
	UsuarioDAO dao = new UsuarioDAO();
	Usuario usuarioLogado = dao.getUsuarioLogado();
	
	@FXML BorderPane root;
	@FXML MenuItem mIUsuarios;

	@FXML
	private void initialize(){
		if(usuarioLogado.getPapel().compareTo("A") == 0){
			mIUsuarios.setDisable(false);
		}else{
			mIUsuarios.setDisable(true);
		}
	}

	@FXML
	void importarDados(){
		ArrayList<String> nomesArquivos = Chooser.selecionarArquivos("Escolha o(s) arquivo(s) a ser(em) importado(s)", root.getScene().getWindow(),
				new ExtensionFilter("Arquivos  TXT","*.txt"));

		if(nomesArquivos != null)
			new ManipuladorDeDados().importarEGerar(nomesArquivos);
	}

	@FXML
	void logout(ActionEvent event){
		usuarioLogado.setLogado(false);
		dao.alterar(usuarioLogado);
		((Stage)root.getScene().getWindow()).close();
		new Academia().start(new Stage());
	}
	
	@FXML
	void gerenciarAlunos(){
		AnchorPane telaGerenciarAlunos;
		try {
			telaGerenciarAlunos = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/GerenciarAlunos.fxml"));
			Scene scene = new Scene(telaGerenciarAlunos,801,562);
			scene.getStylesheets().add(getClass().getResource("../view/css/application.css").toExternalForm());
			root.setCenter(telaGerenciarAlunos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void gerenciarAtividades(){
		AnchorPane telaGerenciarAtividades;
		try {
			telaGerenciarAtividades = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/GerenciarAtividades.fxml"));
			Scene scene = new Scene(telaGerenciarAtividades,801,562);
			scene.getStylesheets().add(getClass().getResource("../view/css/application.css").toExternalForm());
			root.setCenter(telaGerenciarAtividades);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}