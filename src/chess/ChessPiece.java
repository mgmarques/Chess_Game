package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean isTherOpponentPiece(Position position) {
		ChessPiece opponent = (ChessPiece) getBoard().piece(position);
		return opponent != null && opponent.getColor() != color;
	}
}