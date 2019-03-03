package board;

public abstract class Piece {

	protected  Position position;
	private Board board;
	
	public Piece() {
	}

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		// hook method on possibleMovies(), like on the template method
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isTherAnyPossibleMove() {
		boolean possibleMoves[][] = possibleMoves();
		for (int r = 0; r < possibleMoves.length; r++) {
			for (int c = 0; c < possibleMoves.length; c++) {
				if (possibleMoves[r][c] == true) {
					return true;
				}
			}
		}
		return false;
	}
}
