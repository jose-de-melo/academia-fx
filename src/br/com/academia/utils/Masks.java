package br.com.academia.utils;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Funções para se aplicar máscaras aos controles do JavaFX
 * 
 */
public class Masks {

    public static void mascaraNumero(TextField textField){

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    textField.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    textField.setText(oldValue);
                }
            }
        });

    } 

    public static void mascaraEmail(TextField textField){
        textField.setOnKeyTyped((KeyEvent event) -> {
            if("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@".contains(event.getCharacter())==false){
                event.consume();
            }

            if("@".equals(event.getCharacter())&&textField.getText().contains("@")){
                event.consume();
            }

            if("@".equals(event.getCharacter())&&textField.getText().length()==0){
                event.consume();
            }
        });
    }
}