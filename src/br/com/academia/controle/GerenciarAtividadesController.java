package br.com.academia.controle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.com.academia.dados.ManipuladorDeDados;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Atividade;
import br.com.academia.modelo.AtividadeCompleta;
import br.com.academia.modelo.Data;
import br.com.academia.modelo.Hora;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.Tempo;
import br.com.academia.modelo.dao.AlunoDAO;
import br.com.academia.modelo.dao.AtividadeDAO;
import br.com.academia.out.Mensagens;
import br.com.academia.utils.Constantes;
import br.com.academia.utils.MaskedTextField;
import br.com.academia.utils.Masks;

public class GerenciarAtividadesController {

	@FXML CheckBox checkbox;
	@FXML MaskedTextField tfTempo, tfDuracao;
	@FXML TextField tfTipo, tfDistancia, tfCalorias, tfPassos, tfVelocidadeME,tfVelocidadeMA, tfMEElevacao, tfMAElevacao, tfRitmoME, tfRitmoMA;
	@FXML Button cadastrarAlterar, excluir, alterar, cancelar, sair, cdRitmos, limparFiltro;
	@FXML ComboBox<Aluno> boxAlunos, boxAlunosFiltro;
	@FXML ComboBox<Object> boxTipos;
	@FXML AnchorPane root, paneAtividades;
	@FXML TableView<Atividade> table;
	@FXML TableColumn<Atividade, String> colTipo, colDataTempo, colAluno;
	@FXML DatePicker dataInicio, dataFim, dataAtividade;
	@FXML Label mensagensData, labelMensagemCampos;

	private ArrayList<Ritmo> ritmos;

	AtividadeDAO dao = new AtividadeDAO();

	private List<Aluno> alunos = new AlunoDAO().listar();
	private List<Atividade> atividades = dao.listar();
	private Atividade atividadeSelecionada = null;


	private Pagination pagination;

	@FXML
	private void initialize(){
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoAtividade"));
		colDataTempo.setCellValueFactory(new PropertyValueFactory<>("dataTempo"));
		colAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
		
		Masks.mascaraNumero(tfCalorias);
		Masks.mascaraNumero(tfVelocidadeMA);
		Masks.mascaraNumero(tfVelocidadeME);
		Masks.mascaraNumero(tfDistancia);
		Masks.mascaraNumero(tfMAElevacao);
		Masks.mascaraNumero(tfMEElevacao);
		Masks.mascaraNumero(tfPassos);

		acaoCheckBox();
		boxTipos.setItems(FXCollections.observableList(ManipuladorDeDados.getTiposdeAtividades(atividades)));
		boxAlunos.setItems(FXCollections.observableList(alunos));
		boxAlunosFiltro.setItems(FXCollections.observableList(alunos));
		if(!alunos.isEmpty())
			boxAlunos.setValue(alunos.get(0));

		pagination = new Pagination((atividades.size() / linhasPorPagina() + 1), 0);
		atualizarPaginacao(atividades);
		paneAtividades.getChildren().add(pagination);
	}	



	private void atualizarPaginacao(List<Atividade> atividadesList){
		if(atividadesList == null)
			atividadesList = dao.listar();
		
		atividadesList.sort(new Comparator<Atividade>() {
			@Override
			public int compare(Atividade atv1, Atividade atv2) {
				return atv1.getData().compareTo(atv2.getData());
			}
		});

		table.getItems().clear();
		atividades = FXCollections.observableList(atividadesList);
		pagination.setPageFactory(this::createPage);
		pagination.setPrefSize(630, 162.1);
		table.refresh();
	}

	private Node createPage(Integer pageIndex) {
		int fromIndex = pageIndex * linhasPorPagina();
		int toIndex = Math.min(fromIndex +  linhasPorPagina(), atividades.size());

		if(fromIndex > toIndex)
			fromIndex = 0;

		table.setItems(FXCollections.observableArrayList(atividades.subList(fromIndex, toIndex)));
		return new BorderPane(table);
	}



	private int linhasPorPagina() {
		return 4;
	}



	@FXML
	void acaoCheckBox(){
		if(checkbox.isSelected()){
			ativarDesativarTextField(false, tfMAElevacao, tfMEElevacao, tfRitmoMA, tfRitmoME, tfVelocidadeMA, tfVelocidadeME);
			cdRitmos.setDisable(false);
		}else{
			ativarDesativarTextField(true, tfMAElevacao, tfMEElevacao, tfRitmoMA, tfRitmoME, tfVelocidadeMA, tfVelocidadeME);
			cdRitmos.setDisable(true);
		}
	}


	@FXML
	private void exibirAtividade(MouseEvent event) {
		if(atividadeSelecionada != null) {
			ExibirAtividadeController exibir = new ExibirAtividadeController(atividadeSelecionada);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/academia/view/ExibirAtividade.fxml"));
			loader.setController(exibir);
			
			Stage stage = new Stage();
			
			try {
				Scene scene = new Scene(loader.load());
				scene.getStylesheets().add(getClass().getResource("/br/com/academia/view/css/cssMenuPrincipal.css").toExternalForm());
				stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(atividadeSelecionada instanceof AtividadeCompleta) {
				stage.setWidth(643);
				stage.setHeight(709);
			}else {
				stage.setWidth(643);
				stage.setHeight(335);
			}
			
			stage.setTitle(Constantes.NOME_PROGRAMA + " - Dados da Atividade");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/br/com/academia/view/imagens/icone.png")));
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();
		}
	}
	

	@FXML
	void cadastrarRitmos(ActionEvent event){
		Stage stage = new Stage();

		if(ritmos == null)
			ritmos = new ArrayList<Ritmo>();

		CadastrarRitmoController cadastro = new CadastrarRitmoController(ritmos);

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/br/com/academia/view/CadastrarRitmos.fxml"));
		fxmlloader.setController(cadastro);

		try {
			Scene scene = new Scene(fxmlloader.load());
			scene.getStylesheets().add(getClass().getResource("/br/com/academia/view/css/cssMenuPrincipal.css").toExternalForm());
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.setTitle(Constantes.NOME_PROGRAMA + " - Cadastro de Ritmos para a Atividade");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/br/com/academia/view/imagens/icone.png")));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.show();
	}


	@FXML
	void limparMensagens(){
		mensagensData.setText("");
		labelMensagemCampos.setText("");
	}


	@FXML
	void limparFiltros (ActionEvent e){
		atualizarPaginacao(dao.listar());
		limparFiltro.setDisable(true);
		boxAlunosFiltro.setValue(null);
		boxTipos.setValue(null);
	}

	@FXML
	void sair() {
		((Stage) root.getScene().getWindow()).setTitle(Constantes.NOME_PROGRAMA);
		((BorderPane) root.getScene().getRoot()).setCenter(null);
	}

	private void ativarDesativarTextField(Boolean ativar, TextField... campos){
		for (TextField textField : campos) {
			textField.setDisable(ativar);
		}
	}

	@FXML
	void filtrar(ActionEvent event){
		Data dtInicio, dtFim;
		if(dataInicio.getValue() != null && dataFim.getValue() != null){
			dtInicio = Data.getValueOf(dataInicio.getValue());
			dtFim = Data.getValueOf(dataFim.getValue());

			if(dtInicio.compareTo(dtFim) != 1){
				if(event.getSource() == boxAlunosFiltro) {
					removerAtividadesForaDoPeriodo(dtInicio, dtFim);
					limparMensagens();
				}else if(event.getSource() == boxTipos){
					atualizarPaginacao(dao.atividadesDoTipoNoPeriodo(dtInicio, dtFim, (String) boxTipos.getSelectionModel().getSelectedItem()));
					limparMensagens();
				}
				limparFiltro.setDisable(false);
			}else{
				mensagensData.setTextFill(Paint.valueOf("RED"));
				mensagensData.setText("A data inicial tem que ser menor que a data final!");
			}
		}else{
			mensagensData.setTextFill(Paint.valueOf("RED"));
			mensagensData.setText("Selecione a data de início e fim do período!");
		}
	}

	private void removerAtividadesForaDoPeriodo(Data dataInicio, Data dataFim){
		Aluno aluno = boxAlunosFiltro.getSelectionModel().getSelectedItem();
		if(aluno != null) {
			atualizarPaginacao((dao.exerciciosDoAlunoNoPeriodo(dataInicio, dataFim, aluno.getId())));
		}
	}
	
	@FXML
	void cadastrarAlterar() {
		if(verificarCampos()) {
			if(cadastrarAlterar.getText().equals("Cadastrar")){
				dao.cadastrar(getAtividadeDosCampos());
				labelMensagemCampos.setTextFill(Paint.valueOf("GREEN"));
				labelMensagemCampos.setText("Atividade cadastrada!");
			}else if(cadastrarAlterar.getText().equals("Alterar")){
				Atividade atividade = getAtividadeDosCampos();
				atividade.setId(atividadeSelecionada.getId());
				dao.alterar(atividade);
				labelMensagemCampos.setTextFill(Paint.valueOf("GREEN"));
				labelMensagemCampos.setText("Atividade alterada!");
				cadastrarAlterar.setText("Cadastrar");
				checkbox.setDisable(false);
				cdRitmos.setText("Cadastrar Ritmos");
			}
			atualizarPaginacao(null);
			limparCampos();
		}else {
			labelMensagemCampos.setTextFill(Paint.valueOf("RED"));
			labelMensagemCampos.setText("Preencha todos o campos!");
		}
	}
	
	@FXML
	void cancelar() {
		atividadeSelecionada = null;
		limparCampos();
		cancelar.setDisable(true);
		excluir.setDisable(true);
		alterar.setDisable(true);
		checkbox.setDisable(false);
		acaoCheckBox();
		checkbox.setDisable(false);
	}
	
	
	@FXML
	void actionTable(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY) {
			if(event.getClickCount() == 2) {
				exibirAtividade(event);
			}else if(event.getClickCount() == 1) {
				limparCampos();
				limparMensagens();
				checkbox.setDisable(false);
				if(table.getSelectionModel().getSelectedItem() != null){
					atividadeSelecionada = table.getSelectionModel().getSelectedItem();
					alterar.setDisable(false);
					excluir.setDisable(false);
				}
			}
		}
	}
	
	@FXML
	void excluir() {
		if(atividadeSelecionada != null){
			if(Mensagens.msgConfirma(Constantes.NOME_PROGRAMA + " - Controlar Atividades", "Confirmar", "Confirma a exclusão da atividade selecionada?")){
				dao.remover(atividadeSelecionada);
				labelMensagemCampos.setTextFill(Paint.valueOf("RED"));
				labelMensagemCampos.setText("Atividade removida!");
				atualizarPaginacao(null);
			}
		}else{
			Mensagens.msgErro(Constantes.NOME_PROGRAMA + " - Gerenciar Atividades", "ERRO", "Selecione uma atividade a ser excluída!");
		}
	}
	
	@FXML
	void alterar() {
		if(atividadeSelecionada != null){
			preencherCampos(atividadeSelecionada);
			alterar.setDisable(true);
			excluir.setDisable(true);
			cadastrarAlterar.setText("Alterar");
			checkbox.setDisable(true);
			cdRitmos.setText("Alterar Ritmos");
			cancelar.setDisable(false);
		}
		else{
			Mensagens.msgErro(Constantes.NOME_PROGRAMA + " - Gerenciar Atividades", "ERRO", "Selecione uma atividade a ser alterada!");
		}
	}
	
	private boolean verificarCampos() {
		if(boxAlunos.getValue() == null || dataAtividade.getValue() == null ||!verificarTextField()) {
			return false;
		}
		return true;
	}

	private boolean verificarTextField() {
		if(tfTempo.getText().equals("") || tfTipo.getText().equals("") || tfDuracao.getText().equals("") || tfDistancia.getText().equals("") || tfCalorias.getText().equals("") ||  tfPassos.getText().equals("")) {
			return false;
		}else {
			if(checkbox.isSelected()) {
				if(tfVelocidadeMA.getText().equals("") || tfVelocidadeME.getText().equals("") || tfMAElevacao.getText().equals("") || tfMEElevacao.getText().equals("") || tfRitmoMA.getText().equals("") || tfRitmoME.getText().equals("")) {
					return false;
				}
			}
			return true;
		}
	}
	
	private Atividade getAtividadeDosCampos() {
		if(checkbox.isSelected()) {
			return preencheAtividade(true);
		}else {
			return preencheAtividade(false);
		}
	}

	private Atividade preencheAtividade(boolean completa) {
		if(completa) {
			 AtividadeCompleta atividade = new AtividadeCompleta();
			
			Ritmo rMax = new Ritmo();
			rMax.setRitmo(tfRitmoMA.getText());
			Ritmo rMed = new Ritmo();
			rMed.setRitmo(tfRitmoME.getText());
			
			atividade.setAluno(boxAlunos.getValue());
			atividade.setData(Data.getValueOf(dataAtividade.getValue()));
			atividade.setCaloriasPerdidas(Double.valueOf(tfCalorias.getText()));
			atividade.setDistancia(Double.valueOf(tfDistancia.getText()));
			atividade.setDuracao(new Hora(tfDuracao.getText()));
			atividade.setMaiorElevacao(Double.valueOf(tfMAElevacao.getText()));
			atividade.setMenorElevacao(Double.valueOf(tfMEElevacao.getText()));
			atividade.setPassosDados(Integer.valueOf(tfPassos.getText()));
			atividade.setRitmoMaximo(rMax);
			atividade.setRitmoMedio(rMed);
			atividade.setVelocidadeMaxima(Double.valueOf(tfVelocidadeMA.getText()));
			atividade.setVelocidadeMedia(Double.valueOf(tfVelocidadeME.getText()));
			atividade.setRitmosNaAtividade(ritmos);
			atividade.setTipoAtividade(tfTipo.getText());
			atividade.setTempo(new Tempo(tfTempo.getText()));
			
			return atividade;
			
		}else {
			Atividade atividade = new Atividade();
			
			atividade.setAluno(boxAlunos.getValue());
			atividade.setData(Data.getValueOf(dataAtividade.getValue()));
			atividade.setCaloriasPerdidas(Double.valueOf(tfCalorias.getText()));
			atividade.setDistancia(Double.valueOf(tfDistancia.getText()));
			atividade.setDuracao(new Hora(tfDuracao.getText()));
			atividade.setTipoAtividade(tfTipo.getText());
			atividade.setTempo(new Tempo(tfTempo.getText()));
			atividade.setPassosDados(Integer.valueOf(tfPassos.getText()));
			
			return atividade;
		}
	}
	
	
	private void limparCampos(){
		boxAlunos.setValue(boxAlunos.getItems().get(0));
		dataAtividade.setValue(null);
		tfTempo.clear();
		tfCalorias.clear();
		tfPassos.clear();
		tfDistancia.clear();
		tfDuracao.clear();
		tfTipo.clear();
		if(checkbox.isSelected()){
			checkbox.setSelected(false);
			tfVelocidadeMA.clear();
			tfVelocidadeME.clear();
			tfMAElevacao.clear();
			tfMEElevacao.clear();
			tfRitmoMA.clear();
			tfRitmoME.clear();
			ritmos = null;
		}
	}
	
	private void preencherCampos(Atividade atividade){
		limparCampos();
		for (Aluno aluno : boxAlunos.getItems()) {
			if(aluno.getEmail().compareTo(atividade.getAluno().getEmail()) == 0) {
				boxAlunos.setValue(aluno);
				break;
			}
		}
		
		dataAtividade.setValue(LocalDate.of(atividade.getData().getAno(), atividade.getData().getMes(), atividade.getData().getDia()));
		tfTempo.setText(atividade.getTempo().toString());
		tfCalorias.setText(String.valueOf(atividade.getCaloriasPerdidas()));
		tfPassos.setText(String.valueOf(atividade.getPassosDados()));
		tfDistancia.setText(String.valueOf(atividade.getDistancia()));
		tfDuracao.setText(atividade.getDuracao().toString());
		tfTipo.setText(atividade.getTipoAtividade());
		if(atividade instanceof AtividadeCompleta){
			checkbox.setSelected(true);
			tfVelocidadeMA.setText(String.valueOf(((AtividadeCompleta)atividade).getVelocidadeMaxima()));
			tfVelocidadeME.setText(String.valueOf(((AtividadeCompleta)atividade).getVelocidadeMedia()));
			tfMAElevacao.setText(String.valueOf(((AtividadeCompleta)atividade).getMaiorElevacao()));
			tfMEElevacao.setText(String.valueOf(((AtividadeCompleta)atividade).getMenorElevacao()));
			tfRitmoMA.setText(((AtividadeCompleta)atividade).getRitmoMaximo().getTempoRitmo());
			tfRitmoME.setText(((AtividadeCompleta)atividade).getRitmoMedio().getTempoRitmo());
			ritmos = ((AtividadeCompleta)atividade).getRitmosNaAtividade() ;
		}
		acaoCheckBox();
	}
}