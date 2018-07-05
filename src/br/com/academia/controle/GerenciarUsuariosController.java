package br.com.academia.controle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.UsuarioDAO;
import br.com.academia.out.Mensagens;
import br.com.academia.utils.Constantes;

public class GerenciarUsuariosController {
	@FXML private AnchorPane root;
    @FXML private ToggleGroup rbGroup;
    @FXML private TableColumn<Usuario, String> colSenha;
    @FXML  private TextField tfSenha;
    @FXML private TableColumn<Usuario, String> colUser;
    @FXML private Button cdAlterar;
    @FXML private TextField tfUser;
    @FXML  private Button btnExcluir, btnCancelar;
    @FXML private RadioButton rbComum,rbAdm;
    @FXML  private TableView<Usuario> table;
    @FXML private TableColumn<Usuario, String> colPapel;
    @FXML private Label mensagens;

    private Usuario usuarioSelecionado = null;
    private UsuarioDAO dao = new UsuarioDAO();
    
    @FXML
    private void initialize() {
    	colUser.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    	colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
    	colPapel.setCellValueFactory(new PropertyValueFactory<>("papel"));
    	
    	atualizaTable();
    }
    
    
    private void atualizaTable() {
    	table.setItems(FXCollections.observableList(dao.listar()));
	}


	@FXML
    void cadastrarAlterar(ActionEvent event) {
		if(verificaCampos()){
			if(cdAlterar.getText().equals("Cadastrar")){
				dao.cadastrarUsuario(new Usuario(0L, tfUser.getText(), tfSenha.getText(), (rbAdm.isSelected()) ? "A": "C", false));
				limparCampos();
				mensagens.setTextFill(Paint.valueOf("GREEN"));
				mensagens.setText("Usuário cadastrado!");
			}else{
				usuarioSelecionado.setSenha(tfSenha.getText());
				usuarioSelecionado.setUsuario(tfUser.getText());
				usuarioSelecionado.setPapel((rbAdm.isSelected()) ? "A": "C");
				dao.alterar(usuarioSelecionado);
				usuarioSelecionado = null;
				limparCampos();
				cdAlterar.setText("Cadastrar");
				mensagens.setTextFill(Paint.valueOf("GREEN"));
				mensagens.setText("Usuário alterado!");
			}
			atualizaTable();
		}else{
			mensagens.setTextFill(Paint.valueOf("RED"));
			mensagens.setText("Forneça dados válidos e escolha o papel do novo usuário!");
		}
    }
	
	@FXML
	private void limparMensagem(){
		mensagens.setText("");
	}
    
    
    private boolean verificaCampos() {
    	if(tfUser.getText().equals("") || tfSenha.getText().equals("") || (!rbAdm.isSelected() && !rbComum.isSelected())){
    		return false;
    	}
		return true;
	}


	@FXML
    void actionTable() {
    	usuarioSelecionado = table.getSelectionModel().getSelectedItem();
    	if(usuarioSelecionado != null) {
    		btnExcluir.setDisable(false);
    		cdAlterar.setText("Alterar");
    		btnCancelar.setDisable(false);
    		carregarCampos(usuarioSelecionado);
    	}
    }

    @FXML
    void fechar(ActionEvent event) {
    	((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA);
		((BorderPane) root.getScene().getRoot()).setCenter(null);
    }

    @FXML
    void excluir(ActionEvent event) {
    	if(usuarioSelecionado != null && Mensagens.msgConfirma(Constantes.NOME_PROGRAMA + " : Gerenciar Usuários", "Confirmação", "Confirmar a exclusão do usuário selecionado ?")) {
    		if(usuarioSelecionado.isLogado()){
    			mensagens.setTextFill(Paint.valueOf("RED"));
    			mensagens.setText("O usuário não pode ser removido por ser o usuário que está logado na aplicação!");
    		}else{
    			dao.remover(usuarioSelecionado);
        		mensagens.setTextFill(Paint.valueOf("RED"));
    			mensagens.setText("Usuário removido!");
        		limparCampos();
        		btnExcluir.setDisable(true);
        		cdAlterar.setText("Cadastrar");
        		atualizaTable();
    		}
    	}
    }
    
    private void carregarCampos(Usuario usuario) {
    	tfUser.setText(usuario.getUsuario());
		tfSenha.setText(usuario.getSenha());
		if(usuario.getPapel().equals("A")) {
			rbAdm.setSelected(true);
			rbComum.setSelected(false);
		}else {
			rbAdm.setSelected(false);
			rbComum.setSelected(true);
		}
    }


	private void limparCampos() {	
		tfSenha.clear();
		tfUser.clear();
		rbAdm.setSelected(false);
		rbComum.setSelected(false);
	}
	
	@FXML
	void cancelar(){
		limparCampos();
		btnCancelar.setDisable(true);
		cdAlterar.setText("Cadastrar");
		btnExcluir.setDisable(true);
	}
}