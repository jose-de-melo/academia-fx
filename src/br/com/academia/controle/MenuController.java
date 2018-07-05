package br.com.academia.controle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import br.com.academia.utils.Constantes;

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
	void telaGraficos() {
		AnchorPane telaGraficos;
		try {
			telaGraficos = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/academia/view/RelatorioGraficos.fxml"));
			root.setCenter(telaGraficos);
			((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA + " - Relatório de Gráficos");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void telaRelatorioEstatisticas() {
		AnchorPane telaGraficos;
		try {
			telaGraficos = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/academia/view/Relatorio.fxml"));
			root.setCenter(telaGraficos);
			((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA + " - Relatório de Estatísticas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void gerenciarAlunos(){
		AnchorPane telaGerenciarAlunos;
		try {
			telaGerenciarAlunos = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/com/academia/view/GerenciarAlunos.fxml"));
			root.setCenter(telaGerenciarAlunos);
			((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA + " - Controle de Alunos");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void gerenciarAtividades(){
		AnchorPane telaGerenciarAtividades;
		try {
			telaGerenciarAtividades = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/com/academia/view/GerenciarAtividades.fxml"));
			((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA + " - Controle de Atividades");
			root.setCenter(telaGerenciarAtividades);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void gerenciarUsuarios() {
		AnchorPane telaGerenciarAtividades;
		try {
			telaGerenciarAtividades = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/com/academia/view/GerenciarUsuarios.fxml"));
			((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA + " - Controle de Usuários");
			root.setCenter(telaGerenciarAtividades);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}