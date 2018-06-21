package br.com.academia.utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Utilitarios {
		public static void alterarEnabledBotoes(boolean estado, JButton... botoes){
			for (JButton botao : botoes) {
				botao.setEnabled(estado);
			}
		}
		
		public static void alterarVisibleBotoes(boolean visible, JButton... botoes){
			for (JButton botao : botoes) {
				botao.setVisible(visible);
			}
		}
		
		public static Icon getIconAjustado(String nomeImagem, int width, int height) {
			ImageIcon icone = new ImageIcon(Recurso.obterLocalizacao(Constantes.CAMINHO_IMAGENS +  nomeImagem));
			RedimensionadorDeImagem.redimensionar(icone, width, height);
			return icone;
		}
}