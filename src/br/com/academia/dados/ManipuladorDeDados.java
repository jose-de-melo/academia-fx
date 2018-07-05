package br.com.academia.dados;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.academia.arquivos.ArquivoTexto;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.Data;
import br.com.academia.modelo.Hora;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.Tempo;
import br.com.academia.modelo.dao.AtividadeDAO;
import br.com.academia.out.Mensagens;
import br.com.academia.utils.Constantes;
import br.com.academia.utils.ExpressoesRegulares;

/**
 * Classe responsável pela manipulação e importação dos dados vitais para o funcionamento da aplicação
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 * @see ArquivoIndiceExercicio
 */
public class ManipuladorDeDados {
	private ArquivoTexto arqTexto;
	private AtividadeDAO dao;


	/**
	 * Cria uma nova instância <code>ManipuladorDeDados</code>.
	 */
	public ManipuladorDeDados() {
		arqTexto = new ArquivoTexto();
		dao = new AtividadeDAO();
	}

	/**
	 * Importa o conteúdo de um ou mais arquivos texto válidos para a aplicação e o adiciona ao banco de dados do programa.
	 * 
	 * @param pathArquivos {@link List} <{@link String}>  contém o caminho completo de todos os arquivos a serem importados.
	 * @return <code><b>int</b></code> : 0 quando nenhum dado for importado, do contrário, retorna 1.
	 */
	public int importarEGerar(List<String> pathArquivos){
		int verificador = 0;
		ArrayList<Atividade> atividades = new ArrayList<Atividade>();
		List<String> nomesArquivos = new ArrayList<String>();

		int situacaoArquivo = 0;
		Map<String, String> situacaoArquivos = new HashMap<String, String>();
		for (String pathArquivo : pathArquivos) {
			verificador = abrirArquivoTexto(pathArquivo);
			if(verificador == 0)
				break;

			situacaoArquivo = montarEstruturaDeDados(arqTexto.ler(), atividades);
			verificador = 1;

			String nomeArquivo = getNomeArquivo(pathArquivo);
			nomesArquivos.add(nomeArquivo);
			if(situacaoArquivo == Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS){
				situacaoArquivos.put(nomeArquivo, "Não importado (Arquivo inválido, sem dados íntegros ou com todos os dados já importados).");
			}else if(situacaoArquivo == Constantes.ARQUIVO_PARCIALMENTE_INTEGRO){
				situacaoArquivos.put(nomeArquivo, "Parcialmente importado. (Dados inválidos ou repetidos encontrados)");
			}else{
				situacaoArquivos.put(nomeArquivo, "Importado completamente.");
			}

			fecharArquivoTexto();
		}

		exibirRelatorioImportacao(situacaoArquivos, nomesArquivos);

		if(atividades.isEmpty())
			return verificador = 0;

		gerarArquivosDeDados(atividades);

		return verificador;

	}

	/**
	 * Retorna o valor inteiro de uma {@link String} que pode conter pontos.
	 * 
	 * @param quantidade -  {@link String} a se obter o valor inteiro
	 * @return <code><b>int</b></code> : valor inteiro da {@link String}
	 */
	public int getValorEmInteiro(String quantidade) {
		if(quantidade.contains("."))
			quantidade = quantidade.substring(0, quantidade.indexOf(".")) + quantidade.substring(quantidade.indexOf(".") + 1);
		return Integer.parseInt(quantidade);
	}

	/**
	 * Retorna uma lista com todos os tipos de atividades que compõem a lista recebida como parâmetro
	 */
	public static List<Object> getTiposdeAtividades(List<Atividade> atividades){
		List<Object> tipos = new ArrayList<Object>();
		for (Atividade atividade : atividades) {
			if(!tipos.contains(atividade.getTipoAtividade())){
				tipos.add(atividade.getTipoAtividade());
			}
		}
		return tipos;
	}

	public static boolean isListOfAtividadeCompleta(List<Atividade> atividades){
		for (Atividade atividade : atividades) {
			if(atividade instanceof AtividadeCompleta){
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Atividade> getAtividadesDoTipo(List<Atividade> list, String tipoAtividade) {
		ArrayList<Atividade> atividadesDoTipo = new ArrayList<Atividade>();

		for (Atividade atv : list) {
			if(atv.getTipoAtividade().compareToIgnoreCase(tipoAtividade) == 0){
				atividadesDoTipo.add(atv);
			}
		}
		return atividadesDoTipo;
	}

	private String getNomeArquivo(String pathArquivo) {
		return pathArquivo.substring(pathArquivo.lastIndexOf("\\")+1);
	}

	private void exibirRelatorioImportacao(Map<String, String> situacaoArquivos, List<String> nomesArquivos) {
		String mensagem = "Atenção: A seguir os nomes dos arquivos selecionados e suas respectivas situações:\n\n";
		
		for (int indice = 0; indice < nomesArquivos.size(); indice++) {
			mensagem += nomesArquivos.get(indice) + " : " + situacaoArquivos.get(nomesArquivos.get(indice)) + "\n\n";
		}
		Mensagens.msgInfo(Constantes.NOME_PROGRAMA + " : Importar Dados", "Informações ", mensagem);
		
	}

	private void gerarArquivosDeDados(ArrayList<Atividade> atividades) {
		for (Atividade atividade : atividades) {
			dao.cadastrar(atividade);
		}
	}

	private int montarEstruturaDeDados(ArrayList<String> dados, List<Atividade> exercicios) {
		boolean atividadeComDadosInvalidos = false, atividadeCompleta = false, arquivoComDadosInvalidosOuRepetidos = false;

		String tipoAtividade = "", nomeAluno= "" , dataDeNascimento = "00/00/00" , email= ""  , sexo= "" ,altura= ""   ,peso= ""  , cpf = "", whattsapp = "", 
				dataAtividade= "01/01/1999"  , tempo= "00:00 - 00:01"  , duracao = "00:01:00" , ritmoMedio= "1 Km: 01'00\""  , ritmoMaximo= "1 Km: 01'00\""  ;

		double velocidadeMedia = 1 , velocidadeMaxima= 1  , caloriasPerdidas = 1 , distancia = 1 ;
		int numeroDePassos= 1  , menorElevacao= 1  , maiorElevacao= 1 ;

		Ritmo ritmoMedioObj =new Ritmo(ritmoMedio) , ritmoMaximoObj = new Ritmo(ritmoMaximo);
		ArrayList<Atividade> atividadesDoArquivo = new ArrayList<Atividade>();
		ArrayList<Ritmo> ritmos = null;

		for (int indice = 0; indice < dados.size(); indice++) {

			String linha = dados.get(indice);

			if(atividadeComDadosInvalidos && !arquivoComDadosInvalidosOuRepetidos)
				arquivoComDadosInvalidosOuRepetidos = atividadeComDadosInvalidos;

			if(indice == 0 && !linha.contains("Exercício:"))
				return Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS;

			if(linha.contains("Usuário") || linha.contains("Detalhes do exercício"))
				continue;

			if(indice == dados.size() -1 ){
				if(linha.contains("Passos:")){
					if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PASSOS)){
						String quantidade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n")).replace(",", ".");
						numeroDePassos = getValorEmInteiro(quantidade);
					}else{
						atividadeComDadosInvalidos = true;
					}
				}else{
					if(linha.contains("Km:") || linha.contains("KM:") || linha.contains("kM:")){
						if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMO_COMPLETO)){
							Ritmo ritmoObj = new Ritmo(linha);
							ritmos.add(ritmoObj);
						}else{
							atividadeComDadosInvalidos = true;
						}
					}
				}
			}

			if((linha.contains("Exercício:") || indice == dados.size() - 1) && !atividadeComDadosInvalidos){
				if(indice != 0){
					if(atividadeCompleta){
						if(dados.size() != 0){
							AtividadeCompleta atividade = new AtividadeCompleta();

							atividade.setTipoAtividade(tipoAtividade);
							atividade.setAluno(new Aluno(nomeAluno, cpf, whattsapp, sexo, altura, peso, email, new Data(dataDeNascimento)));
							atividade.setData(new Data(dataAtividade));
							atividade.setTempo(new Tempo(tempo));
							atividade.setDuracao(new Hora(duracao));
							atividade.setDistancia(distancia);
							atividade.setCaloriasPerdidas(caloriasPerdidas);
							atividade.setPassosDados(numeroDePassos);
							atividade.setVelocidadeMedia(velocidadeMedia);
							atividade.setVelocidadeMaxima(velocidadeMaxima);
							atividade.setRitmoMedio(ritmoMedioObj);
							atividade.setRitmoMaximo(ritmoMaximoObj);
							atividade.setMenorElevacao(menorElevacao);
							atividade.setMaiorElevacao(maiorElevacao);
							atividade.setRitmosNaAtividade(ritmos);

							if(!dao.verificarSeAAtividadeJaFoiCadastrada(atividade) && !atividadeJaEstaNaListaRecebida(exercicios, atividade)){
								atividadesDoArquivo.add(atividade);
							}else{
								arquivoComDadosInvalidosOuRepetidos = true;
							}
						}
					}else{
						if(dados.size() != 0){
							Atividade atividade = new Atividade();

							atividade.setTipoAtividade(tipoAtividade);
							atividade.setAluno(new Aluno(nomeAluno, cpf, whattsapp, sexo, altura, peso, email, new Data(dataDeNascimento)));
							atividade.setData(new Data(dataAtividade));
							atividade.setTempo(new Tempo(tempo));
							atividade.setDuracao(new Hora(duracao));
							atividade.setDistancia(distancia);
							atividade.setCaloriasPerdidas(caloriasPerdidas);
							atividade.setPassosDados(numeroDePassos);

							if(!dao.verificarSeAAtividadeJaFoiCadastrada(atividade) && !atividadeJaEstaNaListaRecebida(exercicios, atividade)){
								atividadesDoArquivo.add(atividade);
							}else{
								arquivoComDadosInvalidosOuRepetidos = true;
							}
						}
					}
					atividadeCompleta = false;
				}
			}

			if(linha.contains("Exercício:")){
				atividadeComDadosInvalidos = false;
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.TIPO_EXERCICIO)){
					tipoAtividade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Nome:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.NOME)){
					nomeAluno = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Sexo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.SEXO)){
					sexo = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("CPF:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.CPF)){
					cpf = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					System.out.println("Não validou CPF!");
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Altura:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ALTURA)){
					altura = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Peso:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PESO)){
					peso = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Data de nascimento:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DATA)){
					dataDeNascimento = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("E-mail:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.EMAIL)){
					email = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("WhattsApp:") || linha.contains("Whattsapp:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.WHATTSAPP)){
					whattsapp = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}


			if(linha.contains("Data:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DATA)){
					dataAtividade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Tempo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.TEMPO)){
					tempo = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Duração:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DURACAO)){
					duracao = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Distância")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DISTANCIA)){
					distancia = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")-1).replace(",", "."));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Calorias perdidas:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.CALORIA)){
					caloriasPerdidas = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Passos:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PASSOS)){
					String quantidade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n")).replace(",", ".");
					numeroDePassos = getValorEmInteiro(quantidade);
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Velocidade média:")){
				if(ExpressoesRegulares.verificarTexto(linha.replace("\n", ""), ExpressoesRegulares.VELOCIDADES)){
					atividadeCompleta = true;
					velocidadeMedia = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Velocidade máxima:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.VELOCIDADES)){
					velocidadeMaxima = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Ritmo médio:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMOS_MEDIO_MAXIMO)){
					ritmoMedio = "0 Km:" + linha.substring(linha.indexOf(":")+1, linha.lastIndexOf(" "));
					ritmoMedioObj = new Ritmo(ritmoMedio);
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Ritmo máximo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMOS_MEDIO_MAXIMO)){
					ritmoMaximo = "0 Km:" + linha.substring(linha.indexOf(":")+1, linha.lastIndexOf(" "));
					ritmoMaximoObj = new Ritmo(ritmoMaximo);
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Menor elevação:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ELEVACAO)){
					String valorElevacao = linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", ".");
					menorElevacao = getValorEmInteiro(valorElevacao);
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Maior elevação:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ELEVACAO)){
					String quantidade = linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", ".");
					maiorElevacao = getValorEmInteiro(quantidade);
					continue;
				}else{
					atividadeComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("-- Ritmo --")){
				ritmos = new ArrayList<Ritmo>();
				continue;
			}

			if(linha.contains("Km:") || linha.contains("KM:") || linha.contains("kM:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMO_COMPLETO)){
					Ritmo ritmoObj = new Ritmo(linha);
					ritmos.add(ritmoObj);
					if(indice != dados.size() - 1)
						continue;
				}else{
					atividadeComDadosInvalidos = true;
				}
			}
		}

		if(atividadesDoArquivo.isEmpty()){
			return Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS;
		}

		exercicios.addAll(atividadesDoArquivo);

		if(arquivoComDadosInvalidosOuRepetidos){
			return Constantes.ARQUIVO_PARCIALMENTE_INTEGRO;
		}

		return Constantes.ARQUIVO_INTEGRO;

	}

	private void fecharArquivoTexto() {
		try {
			arqTexto.fechar();
		} catch (IOException e) {
			Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Importar Dados", "Erro ao fechar um arquivo", "Não foi possível fechar o arquivo de texto!");
		}
	}

	private int abrirArquivoTexto(String nomeArquivo){
		try {
			arqTexto.abrir(nomeArquivo);
			return 1;
		} catch (FileNotFoundException e) {
			Mensagens.msgErro(Constantes.NOME_PROGRAMA + " : Importar Dados", "Erro ao abrir um arquivo", "Não foi possível abrir o arquivo " + nomeArquivo);
			return 0;
		}
	}
	
	private boolean atividadeJaEstaNaListaRecebida(List<Atividade> atividades, Atividade atividade) {
		for (Atividade atv : atividades) {
			if(atv.compareTo(atividade) == 0){
				return true;
			}
		}
		return false;
	}
}