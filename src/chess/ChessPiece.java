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

	public ChessPiece(Board board, Color color, int moveCount) {
		super(board);
		this.color = color;
		this.moveCount = moveCount;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}

	public void decreaseMoveCount() {
		moveCount--;
	}

	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isTherOpponentPiece(Position position) {
		ChessPiece opponent = (ChessPiece) getBoard().piece(position);
		return opponent != null && opponent.getColor() != color;
	}
}