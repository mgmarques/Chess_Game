package chess;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Piece;
import board.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
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
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
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

	private void validateSourcePosition(Position position) {
		if (!board.therIsAPiece(position)) {
			throw new ChessException("There is no piece on the source position");
		}
		if (currentPlayer !=  ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isTherAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}

	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to the target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public boolean[][] possibleMoves(ChessPosition source) {
		Position position = source.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	private Piece makeMove(Position source, Position target) {

		char piece = board.piece(source).toString().charAt(0);
		Piece play = board.piece(source);
		Piece capturedPiece = board.piece(target);
		// Check if is a Capture from a "en passant" move
		if (piece == 'P' && !board.therIsAPiece(target) && (source.getColumn() != target.getColumn())) {
			Position enPassat = new Position(source.getRow(), target.getColumn());
			capturedPiece = board.removePiece(enPassat);
		}
		else {
			capturedPiece = board.removePiece(target);
		}
		play = board.removePiece(source);
		board.placePiece(play, target);
		// Check if a pawn get promoted
		ChessPiece p = (ChessPiece) board.piece(target);
		Color color = p.getColor();
		int row = target.getRow();
		int col = target.getColumn();
		if (piece == 'P') {
			if ((color == Color.BLACK && row == 7) || (color == Color.WHITE && row == 0)) {
				// Promote Pawn to Queen
				board.removePiece(target);
				board.placePiece(new Queen(board, color), new Position(row, col));
			}
		}
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;

	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		validateSourcePosition(source);
		Position target = targetPosition.toPosition();
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		nextTurn();
		return (ChessPiece) capturedPiece;

	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		Color color = Color.BLACK;

		placeNewPiece('a', 8, new Rook(board, color));
		placeNewPiece('h', 8, new Rook(board, color));
		placeNewPiece('b', 8, new Knight(board, color));
		placeNewPiece('g', 8, new Knight(board, color));
		placeNewPiece('c', 8, new Bishop(board, color));
		placeNewPiece('f', 8, new Bishop(board, color));
		placeNewPiece('d', 8, new Queen(board, color));
		placeNewPiece('e', 8, new King(board, color));
		for (char c = 'a'; c < 'i'; c++) {
			placeNewPiece(c, 7, new Pawn(board, color));
		}

		color = Color.WHITE;
		placeNewPiece('a', 1, new Rook(board, color));
		placeNewPiece('h', 1, new Rook(board, color));
		placeNewPiece('b', 1, new Knight(board, color));
		placeNewPiece('g', 1, new Knight(board, color));
		placeNewPiece('c', 1, new Bishop(board, color));
		placeNewPiece('f', 1, new Bishop(board, color));
		placeNewPiece('d', 1, new Queen(board, color));
		placeNewPiece('e', 1, new King(board, color));
		for (int c = 0; c < board.getColumns(); c++) {
			board.placePiece(new Pawn(board, color), new Position(6, c));
		}
	}
}
