/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int[][] boardTiles;
    private final int boardSize;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        boardTiles = new int[tiles[0].length][tiles[0].length];

        // as the board receives square data, the same length can be used
        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                boardTiles[i][j] = tiles[i][j];
            }
        }
        boardSize = boardTiles[0].length;
    }

    // string representation of this board
    public String toString() {

        StringBuilder boardString = new StringBuilder();

        boardString.append(boardSize + "\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardString.append(String.format("%2d ", boardTiles[i][j]));
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    // board dimension n
    public int dimension() {
        return boardSize;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingVal = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (boardTiles[i][j] != 0) {
                    if (boardTiles[i][j] != ((boardSize * i) + (j + 1))) {
                        hammingVal++;
                    }
                }

            }
        }
        return hammingVal;

    }

    // sum of manhattan distances between tiles and goal
    public int manhattan() {
        int totalmanhattan = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int positionVal = (boardSize * i) + (j + 1);
                if (boardTiles[i][j] != positionVal) {
                    if (boardTiles[i][j] != 0) {
                        int trueRow = (boardTiles[i][j] - 1) / boardSize;
                        int trueCol = boardTiles[i][j] - (boardSize * trueRow) - 1;
                        int rowDiff = trueRow - i;
                        int colDiff = trueCol - j;
                        if (rowDiff < 0) {
                            rowDiff = rowDiff * -1;
                        }
                        if (colDiff < 0) {
                            colDiff = colDiff * -1;
                        }
                        totalmanhattan = totalmanhattan + rowDiff + colDiff;
                    }
                }
            }
        }
        return totalmanhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board compBoard = (Board) y;
        if (compBoard.dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (compBoard.boardTiles[i][j] != boardTiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighbouring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighborBoards = new Queue<Board>();
        int[][] tempTiles = new int[boardSize][boardSize];
        int iZero = 0;
        int jZero = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                tempTiles[i][j] = boardTiles[i][j];
                if (tempTiles[i][j] == 0) {
                    iZero = i;
                    jZero = j;
                }
            }
        }
        if (iZero < boardSize - 1) {
            tempTiles[iZero][jZero] = tempTiles[iZero + 1][jZero];
            tempTiles[iZero + 1][jZero] = 0;
            Board neighborBoard = new Board(tempTiles);
            neighborBoards.enqueue(neighborBoard);
            tempTiles[iZero + 1][jZero] = tempTiles[iZero][jZero];
            tempTiles[iZero][jZero] = 0;
        }
        if (iZero > 0) {
            tempTiles[iZero][jZero] = tempTiles[iZero - 1][jZero];
            tempTiles[iZero - 1][jZero] = 0;
            Board neighborBoard = new Board(tempTiles);
            neighborBoards.enqueue(neighborBoard);
            tempTiles[iZero - 1][jZero] = tempTiles[iZero][jZero];
            tempTiles[iZero][jZero] = 0;
        }
        if (jZero < boardSize - 1) {
            tempTiles[iZero][jZero] = tempTiles[iZero][jZero + 1];
            tempTiles[iZero][jZero + 1] = 0;
            Board neighborBoard = new Board(tempTiles);
            neighborBoards.enqueue(neighborBoard);
            tempTiles[iZero][jZero + 1] = tempTiles[iZero][jZero];
            tempTiles[iZero][jZero] = 0;
        }
        if (jZero > 0) {
            tempTiles[iZero][jZero] = tempTiles[iZero][jZero - 1];
            tempTiles[iZero][jZero - 1] = 0;
            Board neighborBoard = new Board(tempTiles);
            neighborBoards.enqueue(neighborBoard);
            tempTiles[iZero][jZero - 1] = tempTiles[iZero][jZero];
            tempTiles[iZero][jZero] = 0;
        }
        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int firstIndex = 0;
        if (boardTiles[0][0] == 0 || boardTiles[0][1] == 0) {
            firstIndex = 1;
        }
        int[][] tempTiles = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                tempTiles[i][j] = boardTiles[i][j];
            }
        }
        int tempVal = tempTiles[firstIndex][1];
        tempTiles[firstIndex][1] = tempTiles[firstIndex][0];
        tempTiles[firstIndex][0] = tempVal;
        Board twinBoard = new Board(tempTiles);
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 1;
        tiles[0][1] = 2;
        tiles[1][0] = 3;
        tiles[1][1] = 0;
        Board board = new Board(tiles);
        Board board1 = new Board(tiles);
        StdOut.println(board.equals(board1));
    }

}
