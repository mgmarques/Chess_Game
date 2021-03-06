package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public void setPromoted(ChessPiece promoted) {
		this.promoted = promoted;
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
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
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
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("Q")) return new Queen(board, color);
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("R")) return new Rook(board, color);
		return new Knight(board, color);
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (type == null) {
			throw new IllegalStateException("Ther is no piece to be promoted");
		}
		if (!type.equals("Q") && !type.equals("B") && !type.equals("R") && !type.equals("N")) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		Position position = promoted.getChessPosition().toPosition();
		Piece piece = board.removePiece(position);
		piecesOnTheBoard.remove(piece);
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, position);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}

	private Piece makeMove(Position source, Position target) {

		promoted = null;
		char piece = board.piece(source).toString().charAt(0);
		ChessPiece play = (ChessPiece) board.piece(source);
		Piece capturedPiece = board.piece(target);
		// Check if is a Capture from a "en passant" move
		if (piece == 'P' && !board.therIsAPiece(target) && (source.getColumn() != target.getColumn())) {
			Position enPassat = new Position(source.getRow(), target.getColumn());
			capturedPiece = board.removePiece(enPassat);
		} else {
			capturedPiece = board.removePiece(target);
		}
		play = (ChessPiece) board.removePiece(source);
		board.placePiece(play, target);
		play.increaseMoveCount();

		// Check if a pawn get promoted
		ChessPiece p = (ChessPiece) board.piece(target);
		Color color = p.getColor();
		int row = target.getRow();
		if (piece == 'P') {
			if ((color == Color.BLACK && row == 7) || (color == Color.WHITE && row == 0)) {
				// Promote Pawn to Queen
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// Special Move King side Rook
		if (piece == 'K' && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);			
			rook.increaseMoveCount();
		}
		
		// Special Move Queen side Rook
		if (piece == 'K' && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);			
			rook.increaseMoveCount();
		}
		
		return capturedPiece;

	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece original = (ChessPiece) board.removePiece(target);
		original.decreaseMoveCount();
		board.placePiece(original, source);

		if (capturedPiece != null) {
			if (original instanceof Pawn && capturedPiece == enPassantVulnerable) {
				Position enPassat = new Position(source.getRow(), target.getColumn());
				board.placePiece(capturedPiece, enPassat);
				enPassantVulnerable = (ChessPiece) capturedPiece;			
			} else {
			board.placePiece(capturedPiece, target);
			}
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// Special Move King side Rook
		if (original instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);			
			rook.decreaseMoveCount();
		}
		
		// Special Move Queen side Rook
		if (original instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);			
			rook.decreaseMoveCount();
		}

	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		validateSourcePosition(source);
		Position target = targetPosition.toPosition();
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put your King on check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		

		check = (testCheck(opponet(currentPlayer))) ? true : false;

		if (testCheckMate(opponet(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		
		//Special Move En Passant: Check pawn vulnerability
		if (movedPiece instanceof Pawn && Math.abs(target.getRow() - source.getRow()) ==  2) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}

	private Color opponet(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> piecesOfSameColor = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece piece : piecesOfSameColor) {
			if (piece instanceof King)
				return (ChessPiece) piece;
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponetPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponet(color))
				.collect(Collectors.toList());
		for (Piece opponet : opponetPieces) {
			boolean[][] opponetPossibleMoves = opponet.possibleMoves();
			if (opponetPossibleMoves[kingPosition.getRow()][kingPosition.getColumn()] == true) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> currentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : currentPieces) {
			boolean[][] mat = p.possibleMoves();
			for (int r = 0; r < board.getRows(); r++) {
				for (int c = 0; c < board.getColumns(); c++) {
					if (mat[r][c]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(r, c);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck)
							return false;
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		/* Test Checkmate	
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('b', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 8, new King(board, Color.BLACK, this));
			
		// Test Special Move RookCateling	
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));

		*/
		
		Color color = Color.BLACK;

		placeNewPiece('a', 8, new Rook(board, color));
		placeNewPiece('h', 8, new Rook(board, color));
		placeNewPiece('b', 8, new Knight(board, color));
		placeNewPiece('g', 8, new Knight(board, color));
		placeNewPiece('c', 8, new Bishop(board, color));
		placeNewPiece('f', 8, new Bishop(board, color));
		placeNewPiece('d', 8, new Queen(board, color));
		placeNewPiece('e', 8, new King(board, color, this));
		for (char c = 'a'; c < 'i'; c++) {
			placeNewPiece(c, 7, new Pawn(board, color, this));
		}

		color = Color.WHITE;
		placeNewPiece('a', 1, new Rook(board, color));
		placeNewPiece('h', 1, new Rook(board, color));
		placeNewPiece('b', 1, new Knight(board, color));
		placeNewPiece('g', 1, new Knight(board, color));
		placeNewPiece('c', 1, new Bishop(board, color));
		placeNewPiece('f', 1, new Bishop(board, color));
		placeNewPiece('d', 1, new Queen(board, color));
		placeNewPiece('e', 1, new King(board, color, this));
		for (int c = 0; c < board.getColumns(); c++) {
			board.placePiece(new Pawn(board, color, this), new Position(6, c));
		}

	}
}
