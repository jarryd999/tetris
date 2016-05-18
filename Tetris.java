class Tetris {
	public static void main(String[] args) {
		Board board = new Board();

		// This example code draws a horizontal bar 4 squares long.
		board.spawnPiece(5);

		board.print();

		String input = TetrisUtils.getInput();
		if (input == TetrisUtils.LEFT)
			System.out.println("LEFT was pressed");
		else if (input == TetrisUtils.UP)
			System.out.println("UP was pressed");
		else
			System.out.println(input);
	}

}

class Board {
	static int WIDTH = 10;
	static int HEIGHT = 10;
	// The board is represented as an array of arrays, with 10 rows and 10
	// columns.
	int[][] board = new int[HEIGHT][WIDTH];

	// keep a Piece item to keep track of the current moving piece
	Piece piece;

	public int[][] getBoard() {
		return board;
	}

	/**
	 * Set the board square to either have a piece or not (0/1)
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public void set(int x, int y, int value) {
		board[y][x] = value;
	}

	/**
	 * Will spawn the different types of pieces by adding values to the board
	 * 
	 * @param pieceType
	 *            an integer value representing which type of piece to spawn
	 */
	public void spawnPiece(int pieceType) {

		// line piece
		if (pieceType == 1) {
			this.set(3, 0, 1);
			this.set(4, 0, 1);
			this.set(5, 0, 1);
			this.set(6, 0, 1);

			int[][] coords = { { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 } };

			// store the coordinates of the new piece;
			piece = new Piece(coords);

		}

		// square piece
		else if (pieceType == 2) {
			this.set(4, 0, 1);
			this.set(5, 0, 1);
			this.set(4, 1, 1);
			this.set(5, 1, 1);

			int[][] coords = { { 4, 0 }, { 5, 0 }, { 4, 1 }, { 5, 1 } };
			piece = new Piece(coords);

		}

		// S-shaped piece
		else if (pieceType == 3) {
			this.set(4, 0, 1);
			this.set(5, 0, 1);
			this.set(3, 1, 1);
			this.set(4, 1, 1);

			int[][] coords = { { 4, 0 }, { 5, 0 }, { 3, 1 }, { 4, 1 } };
			piece = new Piece(coords);
		}

		// T-shaped piece
		else if (pieceType == 4) {
			this.set(3, 0, 1);
			this.set(4, 0, 1);
			this.set(5, 0, 1);
			this.set(4, 1, 1);

			int[][] coords = { { 3, 0 }, { 4, 0 }, { 5, 0 }, { 4, 1 } };
			piece = new Piece(coords);
		}
		
		// L-shaped piece
		else if (pieceType == 5) {
			this.set(3, 0, 1);
			this.set(4, 0, 1);
			this.set(5, 0, 1);
			this.set(3, 1, 1);

			int[][] coords = { { 3, 0 }, { 4, 0 }, { 5, 0 }, { 3, 1 } };
			piece = new Piece(coords);
		}
	}

	/**
	 * Will control the movement of the piece
	 * 
	 * @param direction
	 *            an integer representation of how to move the piece
	 */
	public void movePiece(int direction) {

	}

	/*
	 * Prints the contents of the board and draws a border around it.
	 */
	public void print() {
		for (int col = 0; col < WIDTH + 2; col++)
			System.out.print("*");
		System.out.println();

		char output;
		for (int row = 0; row < HEIGHT; row++) {
			System.out.print("|");
			for (int col = 0; col < WIDTH; col++) {
				int value = board[row][col];
				System.out.print(value == 0 ? " " : "#");
			}
			System.out.println("|");
		}

		for (int col = 0; col < WIDTH + 2; col++)
			System.out.print("*");
		System.out.println();
	}
}
