package br.com.academia.utils;

import javax.swing.ImageIcon;

public class RedimensionadorDeImagem {
	/**
	 * Redimensiona uma imagem de acordo com os parâmetros passados.
	 * 
	 * @param receber icone, largura e altura desejeda
	 */
	public static void redimensionar(ImageIcon img, int xLargura, int yAltura){
		img.setImage(img.getImage().getScaledInstance(xLargura, yAltura, 100));
	}
}