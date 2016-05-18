class Tetris {
	public static void main(String[] args) {
		Board board = new Board();
		boolean gameOver = false;
		while (!gameOver) {
			board.spawnPiece(5);
			board.pieceAnchored = false;
			board.set(5, 1, 2);
			board.print();

			while (!board.pieceAnchored) {
				String input = TetrisUtils.getInput();
				if (input == TetrisUtils.LEFT) {
					board.movePiece('l');
					board.print();
				} else if (input == TetrisUtils.RIGHT) {
					board.movePiece('r');
					board.print();
				} else if (input == TetrisUtils.DOWN) {
					board.movePiece('d');
					board.print();
				} else
					System.out.println(input);
			}
			// board.movePiece('r');
			// board.print();
		}
	}

}

class Board {
	static int WIDTH = 10;
	static int HEIGHT = 10;
	// The board is represented as an array of arrays, with 10 rows and 10
	// columns.
	int[][] board = new int[HEIGHT][WIDTH];
	boolean pieceAnchored;
	
	// keep a Piece item to keep track of the current moving piece
	Piece piece;

	public int[][] getBoard() {
		return board;
	}

	/**
	 * Set the board square to be empty (0), contain part of the current piece
	 * (1), or an anchored piece (2)
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
	 * @return boolean if the piece can't spawn, return false to end game
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
	public void movePiece(char direction) {
		int[][] newCoords = new int[4][2];
		int[][] coords = piece.coords;

		boolean shouldAnchor = false;
		for (int i = 0; i < 4; i++) {
			int x = coords[i][0];
			int y = coords[i][1];

			// left
			if (direction == 'l') {
				// check left bounds
				if (x <= 0 || board[y][x - 1] == 2) {
					return;
				}

				newCoords[i][0] = x - 1;
				newCoords[i][1] = y;
			}

			// right
			else if (direction == 'r') {
				// check right bound
				if (x + 1 >= this.WIDTH || board[y][x + 1] == 2) {
					return;
				}
				newCoords[i][0] = x + 1;
				newCoords[i][1] = y;
			}

			else if (direction == 'd') {
				// check bottom bound
				if (y + 2 >= this.HEIGHT)
					shouldAnchor = true;
				else if (board[y + 1][x] == 2) {
					this.anchorPiece();
					return;
				}
				newCoords[i][0] = x;
				newCoords[i][1] = y + 1;

			}

		}
		// if reached end of loop, not out of bounds, update board and set
		// new coords
		this.unsetPiece(piece.coords);
		piece.coords = newCoords;
		this.setPiece(piece.coords);

		// if necessary, anchor the piece
		if (shouldAnchor)
			this.anchorPiece();
	}

	/**
	 * Used to remove a piece when moving
	 * 
	 * @param p
	 */
	public void unsetPiece(int[][] coords) {
		for (int i = 0; i < 4; i++) {
			int x = coords[i][1];
			int y = coords[i][0];
			board[x][y] = 0;
		}
	}

	public void setPiece(int[][] coords) {
		for (int i = 0; i < 4; i++) {
			int x = coords[i][1];
			int y = coords[i][0];
			board[x][y] = 1;
		}
	}

	/**
	 * Determine whether or not the current piece can move
	 * 
	 * @param direction
	 * @return
	 */
	public boolean canMove(char direction) {
		int[][] coord = piece.coords;

		for (int i = 0; i < 4; i++) {
			int x = coord[i][1];
			int y = coord[i][0];

			switch (direction) {
			case 'l':
				if (x <= 0 || board[x - 1][y] == 2) {
					return false;
				}
				break;

			case 'r':
				if (x >= this.WIDTH || board[x + 1][y] == 2) {
					return false;
				}
				break;

			case 'd':
				if (y >= this.HEIGHT || board[x][y + 1] == 2) {
					return false;
				}
			}

		}

		// if no obstacles detected, return true
		return true;
	}

	/**
	 * Will mark the current piece coordinates as anchored on the board double
	 * array
	 */
	public void anchorPiece() {
		for (int i = 0; i < 4; i++) {
			int x = piece.coords[i][1];
			int y = piece.coords[i][0];
			board[x][y] = 2;
			this.pieceAnchored = true;
		}
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
				if (value == 0)
					System.out.print(" ");
				else if (value == 1)
					System.out.print("#");
				else if (value == 2)
					System.out.print("x");
			}
			System.out.println("|");
		}

		for (int col = 0; col < WIDTH + 2; col++)
			System.out.print("*");
		System.out.println();
	}
}
