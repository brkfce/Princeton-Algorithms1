import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] squares;
    private int gridSize;
    private int gridLength;
    private WeightedQuickUnionUF gridConnect;
    private int noOfOpen;

    public Percolation(int n) {
        squares = new int[n][n];
        gridLength = n;
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "n must be a positive integer"); //An exception to an invalid entry of n
        }
        for (int row = 0; row < n; row++) {   //Creates a 2D array that represents the grid
            for (int col = 0; col < n;
                 col++) {   //If the value in the array is 0, that square is "blocked"
                squares[row][col] = 0;  //At the start of the problem, every square is blocked
            }   //As squares are opened, their value in this array will change to a 1
        }

        gridSize = 2 + (gridLength
                * gridLength); //The two virtual nodes, plus the number of squares in the grid
        gridConnect = new WeightedQuickUnionUF(
                gridSize);   //This datastructure will store the connections

        for (int i = 1; i <= gridLength; i++) {
            gridConnect.union(0, i);    //Unions the top row of the grid with the virtual top node
            gridConnect.union((gridSize - 1), (gridSize - 1 - i));    //Likewise with the bottom row
        }
        noOfOpen = 0;
    }

    public void open(int row,
                     int col) {    //Takes a square, and changes its value such that is open and connected
        validArgs(row, col);
        if (!isOpen(row, col)) {     //Does not need to open, if already open
            squares[row - 1][col - 1] = 1;  //Accounts for the 0 indexing of the 2D array
            noOfOpen += 1;
            int gridPos = findGridPos(row, col);
            int gridTop, gridRight, gridBottom, gridLeft;
            if (row != 1
                    && isOpen((row - 1),
                              col)) {  //Connects the square to its neighbours, as long as it is not on an edge
                gridTop = gridPos - gridLength;
                gridConnect.union(gridPos, gridTop);
            }
            if (col != gridLength && isOpen(row, (col + 1))) {
                gridRight = gridPos + 1;
                gridConnect.union(gridPos, gridRight);
            }
            if (row != gridLength && isOpen((row + 1), col)) {
                gridBottom = gridPos + gridLength;
                gridConnect.union(gridPos, gridBottom);
            }
            if (col != 1 && isOpen(row, (col - 1))) {
                gridLeft = gridPos - 1;
                gridConnect.union(gridPos, gridLeft);
            }
        }
    }

    public boolean isOpen(int row, int col) {   //Returns the state of a square in the grid
        validArgs(row, col);
        row = row - 1;
        col = col - 1;
        return (squares[row][col] == 1);
    }

    public boolean isFull(int row,
                          int col) {   //Checks if the square is connected to the virtual top node
        validArgs(row, col);
        if (!isOpen(row, col)) {
            return (false);
        }
        else {
            int gridPos = findGridPos(row, col);
            return (gridConnect.find(gridPos) == gridConnect.find(0));
        }
    }

    public int numberOfOpenSites() {    //Returns count of open sites
        return noOfOpen;
    }

    public boolean percolates() {   //Percolates if virtual top node connects to virtual bottom node
        return (gridConnect.find(gridSize - 1) == gridConnect.find(0));
    }

    //Bonus functions

    private void validArgs(int row, int col) {  //Throws an error if an invalid row/col is entered
        if (row <= 0 || row > gridLength || col <= 0 || col > gridLength) {
            throw new IllegalArgumentException("Row/Col must be between 1 and n");
        }
    }

    private int findGridPos(int row, int col) {
        return (((row - 1) * gridLength) + col);    //Finds the gridConnect index of the square;
    }
}
