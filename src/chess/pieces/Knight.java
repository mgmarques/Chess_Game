package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// 2Up-Left move
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// 2Down-Left move
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// 2Up-Right move
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// 2Down-Right move
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Up-2Left move
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Down-2Left move
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Up-2Right move
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Down-2Right move
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		return ChessBoard;
	}
}
