package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

/*
 * Curso: Programa��o Orientada a Objetos com Java
 * http://educandoweb.com.br
 * Aluno: Marcelo Gomes Marques
 * Prof. Dr. Nelio Alves
 * Cap�tulo: Projeto Sistema de Jogo de Xadrez
 * Objetivo geral:
 * 		Aplicar os conhecimentos aprendidos at� o momento no curso  
 *      para a constru��o de um projeto.
 *      
 * Reposit�rio no github: https://github.com/mgmarques/Chess_Game
 */
public class ChessGame {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			try {
				UI.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece captured = chessMatch.performChessMovr(source, target);
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}		
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
