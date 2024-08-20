public class Board {
    private final int[][] board;
    private final int ROWS = 6;
    private final int COLS = 7;


    // Enum representing the players.
    public enum Player {
        PLAYER_ONE(1), PLAYER_TWO(2);

        private final int value;

        Player(int value) {
            this.value = value;
        }


        // Gets the numeric value associated with the player.
        public int getValue() {
            return value;
        }
    }


    // Constructor that initializes the game board.
    public Board() {
        board = new int[ROWS][COLS];
        reset(); // Initialize the board using the reset method
    }


    // Checks if the specified column is full.
    public boolean isFull(int col) {
        col--; // Convert to 0-based index
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Column index out of bounds.");
        }
        return board[0][col] != 0; // If the first cell in the col is initialized, it indicates that the col is full.
    }


    // Places a piece for the specified player in the specified column.
    public int placePiece(int col, Player player) {
        col--; // Convert to 0-based index
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Column index out of bounds.");
        }
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == 0) {
                board[i][col] = player.getValue();
                return i; // Return the row of the placement for further checks
            }
        }
        throw new IllegalStateException("Column is full."); // This should never be reached if you check for full column beforehand
    }


    // Checks if the specified player has won after placing a piece.

    public boolean checkWin(int row, int col, Player player) {
        col--; // Convert to 0-based index
        int playerValue = player.getValue();
        return checkLine(playerValue, row, true) || // Horizontal
                checkLine(playerValue, col, false) || // Vertical
                checkDiagonal(row, col, playerValue);
    }


    /* Checks if there are four consecutive pieces of the specified player
    in a line either horizontally or vertically.*/
    private boolean checkLine(int playerValue, int fixedIndex, boolean isHorizontal) {
        int count = 0;
        int limit = isHorizontal ? COLS : ROWS;

        for (int i = 0; i < limit; i++) {
            int row = isHorizontal ? fixedIndex : i;
            int col = isHorizontal ? i : fixedIndex;

            if (board[row][col] == playerValue) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }


    /* Checks if there are four consecutive pieces of the specified player
    in any diagonal direction.*/
    private boolean checkDiagonal(int row, int col, int playerValue) {
        return checkDiagonalBottomLeftToTopRight(row, col, playerValue) || checkDiagonalTopLeftToBottomRight(row, col, playerValue);
    }


    /* Checks if there are four consecutive pieces of the specified player
     in the bottom-left to top-right diagonal direction.*/
    private boolean checkDiagonalBottomLeftToTopRight(int row, int col, int playerValue) {
        int count = 0;
        int currentRow = row;
        int currentCol = col;

        // Move to the bottom-left end of the diagonal
        while (currentRow < ROWS - 1 && currentCol > 0) {
            currentRow++;
            currentCol--;
        }

        // Check the diagonal
        while (currentRow >= 0 && currentCol < COLS) {
            if (board[currentRow][currentCol] == playerValue) {
                count++;
                if (count >= 4) return true;
            } else {
                count = 0; // Reset count if interrupted
            }
            currentRow--;
            currentCol++;
        }
        return false;
    }


      /* Checks if there are four consecutive pieces of the specified player
     in the top-left to bottom-right diagonal direction.*/

    private boolean checkDiagonalTopLeftToBottomRight(int row, int col, int playerValue) {
        int count = 0;
        int currentRow = row;
        int currentCol = col;

        // Move to the top-left end of the diagonal
        while (currentRow > 0 && currentCol > 0) {
            currentRow--;
            currentCol--;
        }

        // Check the diagonal
        while (currentRow < ROWS && currentCol < COLS) {
            if (board[currentRow][currentCol] == playerValue) {
                count++;
                if (count >= 4) return true;
            } else {
                count = 0; // Reset count if interrupted
            }
            currentRow++;
            currentCol++;
        }
        return false;
    }


    // Resets the game board to its initial state.
    public void reset() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
            }
        }
    }
}
