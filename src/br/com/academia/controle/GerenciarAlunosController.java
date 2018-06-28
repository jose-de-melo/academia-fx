package br.com.academia.controle;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Data;
import br.com.academia.modelo.dao.AlunoDAO;
import br.com.academia.out.Mensagens;
import br.com.academia.utils.Constantes;
import br.com.academia.utils.MaskedTextField;
import br.com.academia.utils.Masks;

public class GerenciarAlunosController {
	@FXML TextField nome,  altura, peso, email, sexo, busca;
	@FXML DatePicker data;
	@FXML MaskedTextField cpf, wpp;
	@FXML Button cadastrarAlterar, excluir, alterar, cancelarAlteracao;
	@FXML Label mensagens, lbNome, lbData, lbPeso, lbAltura, lbWpp, lbEmail, lbCPF, lbSexo;
	@FXML AnchorPane root, panePagination;

	private AlunoDAO dao = new AlunoDAO();
	private Aluno alunoSelecionado;

	ObservableList<Aluno> alunos;
	@FXML TableView<Aluno> table  = new TableView<Aluno>();
	@FXML TableColumn<Aluno, String> clNome, clEmail ;

	private Pagination pagination;

	@FXML
	private void initialize(){
		clNome.setStyle("-fx-alingment : CENTER;");
		clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		clEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		Masks.mascaraNumero(altura);
		Masks.mascaraNumero(peso);
		
		alunos = FXCollections.observableList(dao.listar());
		pagination = new Pagination((alunos.size() / linhasPorPagina()+ 1), 0);
		atualizarPaginacao(dao.listar());
		panePagination.getChildren().add(pagination);
	}

	private void atualizarPaginacao(List<Aluno> alunosList){
		alunosList.sort(new Comparator<Aluno>() {
			@Override
			public int compare(Aluno aluno1, Aluno aluno2) {
				return aluno1.getNome().compareTo(aluno2.getNome());
			}
		});

		alunos = FXCollections.observableList(alunosList);
		pagination.setPageFactory(this::createPage);
		pagination.setPrefSize(354, 167);
	}

	private Aluno getAlunoSelecionado(){
		return table.getSelectionModel().getSelectedItem();
	}

	private Node createPage(Integer pageIndex) {
		int fromIndex = pageIndex * linhasPorPagina();
		int toIndex = Math.min(fromIndex +  linhasPorPagina(), alunos.size());
		table.setItems(FXCollections.observableArrayList(alunos.subList(fromIndex, toIndex)));

		return new BorderPane(table);
	}

	private int linhasPorPagina() {
		return 4;
	}

	@FXML
	void getItemTable(MouseEvent event){
		excluir.setDisable(false);
		alterar.setDisable(false);
		alunoSelecionado = getAlunoSelecionado();
		setarDadosAluno(alunoSelecionado);
		limparMensagem();
		limparCampos();
	}

	@FXML
	void cancelarAlteracao(){
		limparCampos();
		cadastrarAlterar.setText("Cadastrar");
		cancelarAlteracao.setDisable(true);
	}

	@FXML
	void alterar(){
		limparMensagem();
		if(alunoSelecionado != null){
			setarValoresAluno(alunoSelecionado);
			cadastrarAlterar.setText("Alterar");
			cancelarAlteracao.setDisable(false);
		}else{
			Mensagens.msgErro(Constantes.NOME_PROGRAMA, "Erro", "Selecione um usuário a ser alterado!");
		}
	}

	private void setarValoresAluno(Aluno aluno){
		nome.setText(aluno.getNome());
		altura.setText(aluno.getAltura().substring(0, aluno.getAltura().lastIndexOf(" ")));
		peso.setText(aluno.getPeso().substring(0, aluno.getPeso().lastIndexOf(" ")));
		data.setValue(LocalDate.of(aluno.getDataNascimento().getAno(), aluno.getDataNascimento().getMes(), aluno.getDataNascimento().getDia()));
		wpp.setText(aluno.getWhattsapp());
		cpf.setText(aluno.getCpf());
		email.setText(aluno.getEmail());
		sexo.setText(aluno.getSexo());
	}
	
	private void setarDadosAluno(Aluno aluno){
		lbNome.setText(aluno.getNome());
		lbAltura.setText(aluno.getAltura());
		lbPeso.setText(aluno.getPeso());
		lbData.setText(aluno.getDataNascimento().toString());
		lbWpp.setText(aluno.getWhattsapp());
		lbCPF.setText(aluno.getCpf());
		lbEmail.setText(aluno.getEmail());
		lbSexo.setText(aluno.getSexo());
	}

	@FXML
	void cadastrarAlterar(){
		if(cadastrarAlterar.getText().compareTo("Alterar") == 0){
			if(verificarCampos(data, nome, cpf, email, peso, altura, wpp, sexo)){
				Aluno aluno = alunoSelecionado;

				aluno.setAltura(altura.getText() + " m");
				aluno.setPeso(peso.getText()+ " Kg");
				aluno.setNome(nome.getText());
				aluno.setDataNascimento(Data.getValueOf(data.getValue()));
				aluno.setCpf(cpf.getText());
				aluno.setEmail(email.getText());
				aluno.setWhattsapp(wpp.getText());
				aluno.setSexo(sexo.getText());
				dao.alterar(aluno);
				mensagens.setTextFill(Paint.valueOf("GREEN"));
				mensagens.setText("Aluno alterado com sucesso!");
				cancelarAlteracao();
				cadastrarAlterar.setText("Cadastrar");
				alunoSelecionado = null;
				limparCampos();
				limparLabels();
				excluir.setDisable(false);
				alterar.setDisable(false);
				atualizarPaginacao(dao.listar());
			}
		}else{
			if(verificarCampos(data, nome, cpf, email, peso, altura, wpp, sexo)){
				Aluno aluno = new Aluno();

				aluno.setAltura(altura.getText() + " m");
				aluno.setPeso(peso.getText()+ " Kg");
				aluno.setNome(nome.getText());
				aluno.setDataNascimento(Data.getValueOf(data.getValue()));
				aluno.setCpf(cpf.getText());
				aluno.setEmail(email.getText());
				aluno.setWhattsapp(wpp.getText());
				aluno.setSexo(sexo.getText());
				dao.cadastrar(aluno);
				mensagens.setTextFill(Paint.valueOf("GREEN"));
				mensagens.setText("Aluno cadastrado com sucesso!");
				limparCampos();
				limparLabels();
				excluir.setDisable(false);
				alterar.setDisable(false);
				atualizarPaginacao(dao.listar());
			}
		}
	}

	@FXML
	void excluir(){
		if(alunoSelecionado != null){
			if(Mensagens.msgConfirma(Constantes.NOME_PROGRAMA, "Confirmar", "Deseja excluir o aluno selecionado ?")){
				dao.remover(alunoSelecionado);
				excluir.setDisable(false);
				alterar.setDisable(false);
				atualizarPaginacao(dao.listar());
				limparCampos();
				limparLabels();
			}
		}else{
			Mensagens.msgInfo(Constantes.NOME_PROGRAMA, "Erro", "Escolha um usuário primeiro!");
		}
	}

	@FXML
	void sair(){
		root.setVisible(false);
	}
	
	@FXML
	void onKeyPressed(){
		limparLabels();
		atualizarPaginacao(dao.listarPorTexto(busca.getText()));
	}

	private boolean verificarCampos(DatePicker date, TextField... labels){
		boolean bDadosInvalidos = false;
		for (TextField textField : labels) {
			if(textField.getText().equals("")){
				bDadosInvalidos = true;
			}
		}

		if(date.getValue() == null){
			bDadosInvalidos = true;
		}

		if(bDadosInvalidos){
			mensagens.setTextFill(Paint.valueOf("RED"));
			mensagens.setText("Todos os dados precisam estar preenchidos!");
			return false;
		}else{
			return true;
		}
	}
	@FXML
	void limparMensagem(){
		mensagens.setText("");
	}

	private void limparCampos(){
		nome.setText("");
		cpf.setText("");
		sexo.setText("");
		wpp.setText("");
		altura.setText("");
		peso.setText("");
		email.setText("");
		data.setValue(null);
	}
	
	private void limparLabels(){
		lbNome.setText("");
		lbCPF.setText("");
		lbSexo.setText("");
		lbWpp.setText("");
		lbAltura.setText("");
		lbPeso.setText("");
		lbEmail.setText("");
		lbData.setText("");
	}
}