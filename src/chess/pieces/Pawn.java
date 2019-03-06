package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] ChessBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		int direction = -1;
		int rowPawn = position.getRow();

		if (super.getColor() == Color.BLACK) {
			direction = 1;
		}

		Position p = new Position(0, 0);

		// Forward move
		p.setValues(position.getRow() + (1 * direction), position.getColumn());
		if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
			if (getMoveCount() == 0 && !isTherOpponentPiece(p)) {
				p.setValues(position.getRow() + (2 * direction), position.getColumn());
				if (getBoard().positionExists(p) && (!getBoard().therIsAPiece(p) || isTherOpponentPiece(p))) {
					ChessBoard[p.getRow()][p.getColumn()] = true;
				}
			}
		}

		// Diagonal Capture - Left
		p.setValues(position.getRow() + (1 * direction), position.getColumn() - 1);
		if (getBoard().positionExists(p) && isTherOpponentPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Diagonal Capture - Right
		p.setValues(position.getRow() + (1 * direction), position.getColumn() + 1);
		if (getBoard().positionExists(p) && isTherOpponentPiece(p)) {
			ChessBoard[p.getRow()][p.getColumn()] = true;
		}

		// Possible Capture "en passant"
		boolean enPassat = false;
		if (super.getColor() == Color.BLACK) {
			if (rowPawn == 4)
				enPassat = true;
		} else {
			if (rowPawn == 3) {
				enPassat = true;
			}
		}

		if (enPassat) {
			// enPassat Capture - Left
			Position opponet = new Position(0, 0);
			opponet.setValues(position.getRow(), position.getColumn() - 1);
			if (getBoard().positionExists(opponet) && isTherOpponentPiece(opponet)
					&& getBoard().piece(opponet) == chessMatch.getEnPassantVulnerable()) {
				ChessBoard[opponet.getRow() + (1 * direction)][opponet.getColumn()] = true;
			}

			// enPassat Capture - Right
			opponet.setValues(position.getRow(), position.getColumn() + 1);
			if (getBoard().positionExists(opponet) && isTherOpponentPiece(opponet)
					&& getBoard().piece(opponet) == chessMatch.getEnPassantVulnerable()) {
				ChessBoard[opponet.getRow() + (1 * direction)][opponet.getColumn()] = true;
			}

		}
		return ChessBoard;
	}

}
