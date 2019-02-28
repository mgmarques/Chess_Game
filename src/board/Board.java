package board;

import board.exception.BoardException;

public class Board {

	private int rows;
	private int columns;
	private Piece pieces[][];
	
	public Board(int rows, int columns) throws BoardException {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at leat 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
		
	public Piece piece(int row, int column) throws BoardException {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board!");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) throws BoardException {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board!");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) throws BoardException {
	
		if (therIsAPiece(position)) {
			throw new BoardException("There is already a piece on the position!");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	private boolean positionExists(int row, int column) {
		return row > -1 && row < rows && column > -1 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean therIsAPiece(Position position) throws BoardException {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board!");
		}
		return piece(position) != null;
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board!");
		}
		if (piece(position) == null) {
			return null;
		}
		else {
			Piece piece = piece(position);
			piece.position = null;
			pieces[position.getRow()][position.getColumn()] = null;
			return piece;
		}
		
	}
}
