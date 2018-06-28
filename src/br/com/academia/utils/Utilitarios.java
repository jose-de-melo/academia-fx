package br.com.academia.utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Utilitarios {
		public static Icon getIconAjustado(String nomeImagem, int width, int height) {
			ImageIcon icone = new ImageIcon(Recurso.obterLocalizacao(Constantes.CAMINHO_IMAGENS +  nomeImagem));
			RedimensionadorDeImagem.redimensionar(icone, width, height);
			return icone;
		}
}