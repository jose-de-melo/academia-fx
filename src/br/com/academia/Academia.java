package br.com.academia;

import br.com.academia.utils.Constantes;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Academia extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/Login.fxml"));
			Scene scene = new Scene(root,590,440);
			scene.getStylesheets().add(getClass().getResource("view/css/cssLogin.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(Constantes.NOME_PROGRAMA +" - Login");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
