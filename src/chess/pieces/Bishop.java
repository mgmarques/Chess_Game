package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	private boolean possibleCapture(Position p) {
		return (getBoard().positionExists(p) && isTherOpponentPiece(p));
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//Up-Left move
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (possibleCapture(p)) ChessBoard[p.getRow()][p.getColumn()] = true;
		
		//Down-Left move
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (possibleCapture(p)) ChessBoard[p.getRow()][p.getColumn()] = true;
		
		//Up-Right move
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (possibleCapture(p)) ChessBoard[p.getRow()][p.getColumn()] = true;

		//Down-Right move
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().therIsAPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (possibleCapture(p)) ChessBoard[p.getRow()][p.getColumn()] = true;

		return ChessBoard;
	}
}
