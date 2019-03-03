package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//Above move
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}
		//Below move
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}
		
		//Left move
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		//Up-Left move
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		//Down-Left move
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		//Right move
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		//Up-Right move
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}
		
		//Down-Right move
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		return ChessBoard;
	}
}
