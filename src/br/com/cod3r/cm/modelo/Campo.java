package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.Excecoes.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado;

	private List<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = (vizinho.linha != linha);
		boolean colunaDiferente = (vizinho.coluna != coluna);
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaX = Math.abs(vizinho.coluna - coluna);
		int deltaY = Math.abs(vizinho.linha - linha);
		int distancia = deltaX + deltaY;

		if (distancia == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (distancia == 2 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			if(areaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}		
	}

	boolean isMinado() {
		return minado;
	}
	void minar() {
		minado = true;
	}
	
	boolean isAberto() {
		return aberto;
	}
    void setAberto(boolean aberto){
		this.aberto = aberto;
		
	}
	boolean isFechado() {
		return !isAberto();
	}
	boolean isMarcado(){
		return marcado;
	}

	boolean areaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto; //Se o !minado = true é pq era nao minado.
		boolean protegido = minado && marcado; //se os dois forem true
		return desvendado || protegido;
	}
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	void reiniciar() {
		aberto = false;
		marcado = false;
		minado = false;
	}

	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	public String toString() {
		if(marcado) {
			return "X";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}
