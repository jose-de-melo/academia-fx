package br.com.academia.modelo;

public class Atividade implements Comparable<Atividade>{
	private long id;
	private String tipoAtividade;
	private Aluno aluno;
	private Data dataAtividade;
	private Tempo tempo;
	private Hora duracao;
	private double distancia;
	private double caloriasPerdidas;
	private int passosDados;

	public Atividade() { }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Data getData() {
		return dataAtividade;
	}

	public void setData(Data data) {
		this.dataAtividade = data;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}

	public Hora getDuracao() {
		return duracao;
	}

	public void setDuracao(Hora duracao) {
		this.duracao = duracao;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public double getCaloriasPerdidas() {
		return caloriasPerdidas;
	}

	public void setCaloriasPerdidas(double caloriasPerdidas) {
		this.caloriasPerdidas = caloriasPerdidas;
	}

	public int getPassosDados() {
		return passosDados;
	}

	public void setPassosDados(int passosDados) {
		this.passosDados = passosDados;
	}

	@Override
	public String toString() {
		return String.format(tipoAtividade + " -- " + dataAtividade.toString() + " -- " + tempo.toString());
	}

	@Override
	public int compareTo(Atividade atividade) {
		if(atividade.aluno.getEmail().compareTo(aluno.getEmail()) == 0 && 
				atividade.getData().compareTo(dataAtividade) == 0 && 
				atividade.getTempo().toString().compareTo(tempo.toString()) == 0)
			return 0;
		else{
			return 1;
		}
	}
}
