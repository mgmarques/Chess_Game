package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

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

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		while (true) {
			try {
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null) captured.add(capturedPiece);
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
