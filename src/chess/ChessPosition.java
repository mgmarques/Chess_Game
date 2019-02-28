package chess;

import board.Position;
import chess.exceptions.ChessException;

public class ChessPosition {
	
	private char column;
	private int row;

	public ChessPosition(char column, Integer row) throws ChessException {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition: valid values from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public Integer getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	protected ChessPosition fromPosition(Position position) throws ChessException {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
	
}
