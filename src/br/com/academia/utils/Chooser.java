package br.com.academia.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class Chooser {
	public static ArrayList<String> selecionarArquivos(String titulo, Window janelaPai, ExtensionFilter filtroExtensao) {
		FileChooser selecionaArquivo = new FileChooser();

		selecionaArquivo.setTitle(titulo);
		selecionaArquivo.setInitialDirectory(new File(System.getProperty("user.home")));
		selecionaArquivo.getExtensionFilters().add(filtroExtensao);

		List<File> files = selecionaArquivo.showOpenMultipleDialog(janelaPai);
		
		if(files == null)
			return null;
		
		ArrayList<String> nomesArquivos = new ArrayList<String>();
		
		for (File file : files) {
			nomesArquivos.add(file.getPath());
		}
		

		return nomesArquivos;
	}
}