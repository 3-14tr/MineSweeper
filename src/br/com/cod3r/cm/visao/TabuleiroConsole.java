package br.com.cod3r.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.Excecoes.ExplosaoException;
import br.com.cod3r.cm.Excecoes.SairException;
import br.com.cod3r.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() { //Loop para iterar várias partidas.

		try {
			boolean continuar = true; //Continuar com outra partida
			while(continuar) {
				
				cicloDoJogo(); //Se cair numa ExplosaoException ele fecha e o método é dado como finalizado.
				
				System.out.println("Outra partida? (S/n) ");
				if("n".equalsIgnoreCase(entrada.nextLine())) {
					continuar = false;
					System.out.println("\nAté a próxima!");
				}else {
					tabuleiro.reiniciar();
				}
			}
			
		} catch (SairException e) {
			System.out.println("Tchau");
		}finally {
			entrada.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancado()) { //iterar sobre todos campos do tabuleiro.
				 System.out.println(tabuleiro);
				 
				 String digitado = capturarValorDigitado("Digite(x,y): ");
				 
				Iterator<Integer> xy = Arrays
						.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim()))
						.iterator(); //Tratamento da entrada.(Formatação)
				
				digitado = capturarValorDigitado("1- ABRIR ||| 2 - MARCAR/DESMARCAR: ");
				if("1".equalsIgnoreCase(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				}else if("2".equalsIgnoreCase(digitado)) {
					tabuleiro.alterarMarcacao(xy.next(), xy.next());
				}
			} //Objetivo é alcançado.
			System.out.println("You win!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("GAME-OVER!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
	
	
}
