package br.com.academia.out;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Mensagens {
	protected static boolean confirmaOuNão;
	
	public static void msgInfo(String titulo, String cabecalho, String mensagem){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setWidth(400);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
	
	public static void msgErro(String titulo, String cabecalho, String mensagem){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
	
	public static boolean msgConfirma(String titulo, String cabecalho, String mensagem){
		Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        
        dialogoExe.setTitle(titulo);
        dialogoExe.setHeaderText(cabecalho);
        dialogoExe.setContentText(mensagem);
        
        dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
        dialogoExe.showAndWait().ifPresent(b -> {
            if (b == btnSim) {
            	confirmaOuNão = true;
            } else {
               confirmaOuNão =  false;
            }
        });
		return confirmaOuNão;
	}
}