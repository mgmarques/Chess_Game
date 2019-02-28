package chess;

import board.Position;

public class ChessPosition {
	
	private char column;
	private Integer row;
	
	public ChessPosition() {
	}

	public ChessPosition(char column, Integer row) {
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
		int col = (int) column;
		Position position = new Position(row, col);
		
		return position;
	}

	protected ChessPosition fromPosition(Position position) {
		char col = 'A'; // position.getColumn()
		ChessPosition chessPosition = new ChessPosition(col, row);
		
		return chessPosition;
	}

	@Override
	public String toString() {
		return row + ", " + column;
	}
	
}
