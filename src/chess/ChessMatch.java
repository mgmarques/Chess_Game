package chess;

import board.Board;
import board.Position;
import chess.enums.Color;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; 
		for (int r = 0; r < board.getRows(); r++) {
			for (int c = 0; c < board.getColumns(); c++) {
				mat[r][c] = (ChessPiece) board.piece(r, c);
			}
		}
		return mat;
	}
	
	private void initialSetup() {
		Color color = Color.BLACK;
		
		board.placePiece(new Rook(board, color), new Position(0, 0));
		board.placePiece(new Rook(board, color), new Position(0, 7));
		board.placePiece(new Knight(board, color), new Position(0, 1));
		board.placePiece(new Knight(board, color), new Position(0, 6));
		board.placePiece(new Bishop(board, color), new Position(0, 2));
		board.placePiece(new Bishop(board, color), new Position(0, 5));
		board.placePiece(new Queen(board, color), new Position(0, 3));
		board.placePiece(new King(board, color), new Position(0, 4));
		for (int c = 0; c < board.getColumns(); c++) {
			board.placePiece(new Pawn(board, color), new Position(1, c));
		}

		color = Color.WHITE;
		board.placePiece(new Rook(board, color), new Position(7, 0));
		board.placePiece(new Rook(board, color), new Position(7, 7));
		board.placePiece(new Knight(board, color), new Position(7, 1));
		board.placePiece(new Knight(board, color), new Position(7, 6));
		board.placePiece(new Bishop(board, color), new Position(7, 2));
		board.placePiece(new Bishop(board, color), new Position(7, 5));
		board.placePiece(new Queen(board, color), new Position(7, 3));
		board.placePiece(new King(board, color), new Position(7, 4));
		for (int c = 0; c < board.getColumns(); c++) {
			board.placePiece(new Pawn(board, color), new Position(6, c));
		}
	}
}
