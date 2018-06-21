package br.com.academia.modelo;


public class Atividade {
	private int id;
	private String tipoExercicio;
	private Usuario usuario;
	private Data dataExercicio;
	private Tempo tempo;
	private Hora duracao;
	private double distancia;
	private double caloriasPerdidas;
	private int passosDados;

	public Atividade() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoExercicio() {
		return tipoExercicio;
	}

	public void setTipoExercicio(String tipoExercicio) {
		this.tipoExercicio = tipoExercicio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Data getData() {
		return dataExercicio;
	}

	public void setData(Data data) {
		this.dataExercicio = data;
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
		return String.format(tipoExercicio + " -- " + dataExercicio.toString() + " -- " + tempo.toString());
	}
}
