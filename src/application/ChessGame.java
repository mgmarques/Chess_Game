package application;

import chess.ChessMatch;

/*
 * Curso: Programação Orientada a Objetos com Java
 * http://educandoweb.com.br
 * Aluno: Marcelo Gomes Marques
 * Prof. Dr. Nelio Alves
 * Capítulo: Projeto Sistema de Jogo de Xadrez
 * Objetivo geral:
 * 		Aplicar os conhecimentos aprendidos até o momento no curso  
 *      para a construção de um projeto.
 *      
 * Repositório no github: https://github.com/mgmarques/Chess_Game
 */
public class ChessGame {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());

	}

}
