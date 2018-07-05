package br.com.academia.controle;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.UsuarioDAO;
import br.com.academia.sgbd.ConnectionFactory;
import br.com.academia.utils.Constantes;

public class LoginController {
	@FXML private PasswordField pfSenha;
    @FXML  private AnchorPane root;
    @FXML  private Button btnLogar, btnCancelar;
    @FXML  private Label lbErros;
    @FXML private TextField tfUser;

    @FXML
    void tratarEventoEnter(KeyEvent event) {
    	limparLabelErro(event);
    	if(event.getCode() == KeyCode.ENTER){
    		if(event.getSource() instanceof PasswordField){
    			logar();
    		}else{
    			pfSenha.requestFocus();
    		}
    	}
    }
    
    @FXML
    void limparLabelErro(Event event) {
    	lbErros.setText("");
    }

    @FXML
    void logar() {
    	if(tfUser.getText().compareTo("") == 0&& pfSenha.getText().compareTo("") == 0){
    		lbErros.setText("Os campos Usuário e Senha não podem estar vazios!");
    	}
    	else{
    		if(new UsuarioDAO().logar(new Usuario(0, tfUser.getText(), pfSenha.getText(), "", false))){
    			((Stage)root.getScene().getWindow()).close();
    			Stage primaryStage = new Stage();
    			BorderPane root;
				try {
					root = (BorderPane)FXMLLoader.load(getClass().getResource("/br/com/academia/view/Menu.fxml"));
					Scene scene = new Scene(root,1220,694);
	    			scene.getStylesheets().add(getClass().getResource("/br/com/academia/view/css/cssMenuPrincipal.css").toExternalForm());
	    			primaryStage.setScene(scene);
	    			primaryStage.setResizable(false);
	    			primaryStage.setTitle(Constantes.NOME_PROGRAMA);
	    			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/com/academia/view/imagens/icone.png")));
	    			primaryStage.initModality(Modality.APPLICATION_MODAL);
	    			primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}else{
        		lbErros.setText("Usuário e/ou senha incorretos!");
        	}
    	}
    	
    }

    @FXML
    void cancelar() {
    	((Stage)root.getScene().getWindow()).close();
    	if(ConnectionFactory.getConnection() != null)
			try {
				ConnectionFactory.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
    
    @FXML
    void onKeyPressed(KeyEvent event){
    	if(event.getCode() == KeyCode.ESCAPE){
    		cancelar();
    	}
    }
    
}