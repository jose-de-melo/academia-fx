package br.com.academia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.academia.arquivos.ArquivoTexto;

/**
 * Classe usada para validar as linhas com os dados importados de um {@link ArquivoTexto}.
 * 
 * @author José do Carmo de Melo Silva
 * @see ArquivoTexto
 * @see Matcher
 * @see Pattern
 */
public class ExpressoesRegulares {
	/**
	 * <code>Regex</code> usada para validar o tipo do exercício.
	 */
	public static final String TIPO_EXERCICIO = "Exercício: [A-Za-z-záàâãéèêíïóôõöúçñ]{1,}";
	/**
	 * <code>Regex</code> usada para validar o nome do usuário que praticou o exercício.
	 */
	public static final String NOME = "Nome: [A-Za-z-záàâãéèêíïóôõöúçñ]{1,}";
	/**
	 * <code>Regex</code> usada para validar o sexo do usuário que praticou o exercício.
	 */
	public static final String SEXO = "Sexo: [A-Za-z]{1,}";
	/**
	 * <code>Regex</code> usada para validar a altura do usuário que praticou o exercício.
	 */
	public static final String ALTURA = "Altura: [0-9]{1,}[,\\.]{1,1}[0-9]{1,} [A-Za-z]{1,}";
	/**
	 * <code>Regex</code> usada para validar o peso do usuário que praticou o exercício.
	 */
	public static final String PESO = "Peso: [0-9]{1,}[,\\.]{1,1}[0-9]{1,} [A-Za-z]{1,}";
	/**
	 * <code>Regex</code> usada para validar a data de nascimento do usuário que praticou o exercício e a data em que o exercício foi praticado.
	 */
	public static final String DATA = "Data[\\sA-Za-záàâãéèêíïóôõöúçñ]{0,}: [0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}";
	/**
	 * <code>Regex</code> usada para validar o email do usuário que praticou o exercício.
	 */
	public static final String EMAIL = "E-mail: [A-Za-z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-/\\=\\?\\^\\_\\`\\.\\{\\|\\}\\~]+@[A-Za-z]+\\.[A-Za-z]+";
	/**
	 * <code>Regex</code> usada para validar o tempo em que o exercício foi praticado.
	 */
	public static final String TEMPO = "Tempo: [012]{1}[0-9]{1}:[012345]{1}[0-9]{1} - [012]{1}[0-9]{1}:[012345]{1}[0-9]{1}";
	/**
	 * <code>Regex</code> usada para validar a duração gasta na prática do exercício.
	 */
	public static final String DURACAO = "Duração: [012]{1}[0-9]{1}:[012345]{1}[0-9]{1}:[012345]{1}[0-9]{1}";
	/**
	 * <code>Regex</code> usada para validar a distância percorrida na prática do exercício.
	 */
	public static final String DISTANCIA = "Distância: [0-9]{1,}[,\\.]{1,1}[0-9]{1,} [A-Za-z]{1,}";
	/**
	 * <code>Regex</code> usada para validar as calorias perdidas na prática do exercício.
	 */
	public static final String CALORIA = "Calorias perdidas: [0-9]{1,}[,\\.]{0,1}[0-9]{0,} [Kk][Cc][Aa][Ll]";
	/**
	 * <code>Regex</code> usada para validar o número de passos dados na prática do exercício.
	 */
	public static final String PASSOS = "Passos: [0-9]{1,}[,\\.]{0,1}[0-9]{0,}"; 
	/**
	 * <code>Regex</code> usada para validar as velocidades média e máxima alcançadas na prática do exercício.
	 */
	public static final String VELOCIDADES = "Velocidade[\\sA-Za-záàâãéèêíïóôõöúçñ]{0,}:\\s[0-9]{1,}[,\\.]{0,1}[0-9]{1,} [Kk][Mm]/[Hh]";
	/**
	 * <code>Regex</code> usada para validar os ritmos médio e máximo alcançados na prática do exercício.
	 */
	public static final String RITMOS_MEDIO_MAXIMO = "Ritmo[\\sA-Za-záàâãéèêíïóôõöúçñ]{0,}: [012345]{1}[0-9]{1}\\'[012345]{1}[0-9]{1}\" /[Kk][Mm]";
	/**
	 * <code>Regex</code> usada para validar a maior e a menor elevação alcançadas na prática do exercício.
	 */
	public static final String ELEVACAO = "[\\sA-Za-záàâãéèêíïóôõöúçñ]{0,}[Ee]levação: [0-9]{1,}[,\\.]{0,1}[0-9]{0,} [Mm]";
	/**
	 * <code>Regex</code> usada para validar um ritmo no exercício.
	 */
	public static final String RITMO_COMPLETO = "[0-9]{1,} [Kk][Mm]: [012345]{1}[0-9]{1}\\'[012345]{1}[0-9]{1}\"";
	
	/**
	 * <code>Regex</code> usada para validar um número de WhattsApp.
	 */
	public static final String WHATTSAPP = "Whatts[Aa]pp: \\([0-9]{2,3}\\) [0-9]{4,5}-[0-9]{4,4}";
	
	/**
	 * <code>Regex</code> usada para validar CPF.
	 */
	public static final String CPF = "C[Pp][Ff]: [0-9]{3,3}\\.[0-9]{3,3}\\.[0-9]{3,3}-[0-9]{2,2}";
	
	/**
	 * Verifica se a <code>string</code> está de acordo com o <code>regex</code>.
	 * 
	 * @param texto - <code>string</code> a ser verificada
	 * @param expressaoRegular - <code>regex</code> usada para validar a <code>string</code> recebida.
	 * @return <code><b>true</b></code> : <code>string</code> de acordo com a <code>regex</code> recebida; <code><b>false</b></code>
	 * <code>string</code> não bate com a <code>regex</code>
	 */
	public static boolean verificarTexto(String texto, String expressaoRegular){
		Pattern pattern = Pattern.compile(expressaoRegular);
		Matcher matcher = pattern.matcher(texto);
		
		return matcher.find();
	}
	
}
