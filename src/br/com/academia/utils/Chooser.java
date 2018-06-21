package br.com.academia.utils;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Chooser {
	
	public static String salvarArquivo(String nomeSugeridoArquivo, FileNameExtensionFilter filtro, Component janelaPai){
		File novoArquivo = new File(nomeSugeridoArquivo);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Escolha o arquivo desejado ou crie um novo");
		fileChooser.setFileFilter(filtro);
		fileChooser.setSelectedFile(novoArquivo);

		int opcao = fileChooser.showSaveDialog(null);
		if(opcao == JFileChooser.CANCEL_OPTION)
			return null;

		novoArquivo = fileChooser.getSelectedFile();

		return novoArquivo.getAbsolutePath();
	}
	
	public static ArrayList<String> selecionarArquivos(String titulo, Component janelaPai, FileNameExtensionFilter extensaoTxt) {
		JFileChooser selecionaArquivo = new JFileChooser();

		selecionaArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		selecionaArquivo.setDialogTitle(titulo);

		selecionaArquivo.setFileFilter(extensaoTxt);
		
		selecionaArquivo.setMultiSelectionEnabled(true);

		int opcao = selecionaArquivo.showOpenDialog(janelaPai);
		
		if(opcao == JFileChooser.CANCEL_OPTION)
			return null;
		
		File[] files = selecionaArquivo.getSelectedFiles();
		ArrayList<String> nomesArquivos = new ArrayList<String>();
		
		for (File file : files) {
			nomesArquivos.add(file.getPath());
		}

		return nomesArquivos;
	}
}