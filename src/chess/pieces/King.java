package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean testRookCastling(Position position) {
		ChessPiece rook = (ChessPiece) getBoard().piece(position);
		return (rook != null && rook instanceof Rook && rook.getMoveCount() == 0 && rook.getColor() == getColor());
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Above move
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}
		// Below move
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Left move
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Up-Left move
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Down-Left move
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Right move
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Up-Right move
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Down-Right move
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}
		
		// Special Move Castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// Castling King side Rook (right of white)
			Position rook = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(rook)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					ChessBoard[p2.getRow()][p2.getColumn()] = true;
				}
			}
			// Castling Queen side Rook (left of white)
			rook = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(rook)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					ChessBoard[p2.getRow()][p2.getColumn()] = true;
				}
			}
		}

		return ChessBoard;
	}
}
