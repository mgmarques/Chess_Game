package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	public Queen(Board board, Color color, int moveCount) {
		super(board, color, moveCount);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Above move
		p.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn());
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Below move
		p.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn());
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Left move
		p.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow(), p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Right move
		p.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow(), p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Up-Left move
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Down-Left move
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Up-Right move
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		// Down-Right move
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isTherOpponentPiece(p))
			ChessBoard[p.getRow()][p.getColumn()] = true;

		return ChessBoard;
	}
}
