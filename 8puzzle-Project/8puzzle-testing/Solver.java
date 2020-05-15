/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private searchNode finalNode;
    private boolean solvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null board");
        }

        MinPQ<searchNode> gameQueue1 = new MinPQ<searchNode>(new boardComparator());
        MinPQ<searchNode> gameQueue2 = new MinPQ<searchNode>(new boardComparator());
        int moveCount = 0;
        searchNode initialSearchNode1 = new searchNode(initial, moveCount, null);
        searchNode initialSearchNode2 = new searchNode(initial.twin(), moveCount, null);
        searchNode currentNode1 = initialSearchNode1;
        searchNode currentNode2 = initialSearchNode2;

        while (currentNode1.returnManhattan() != 0) {
            for (Board tempBoard : currentNode1.returnBoard().neighbors()) {
                searchNode tempNode = new searchNode(tempBoard, currentNode1.returnMoves() + 1,
                                                     currentNode1);
                if (currentNode1.returnPrevNode() != null && currentNode1.returnPrevNode()
                                                                         .returnBoard()
                                                                         .equals(tempBoard)) {
                    continue;
                }
                else {
                    gameQueue1.insert(tempNode);
                }
            }
            currentNode1 = gameQueue1.delMin();


            for (Board tempBoard : currentNode2.returnBoard().neighbors()) {
                searchNode tempNode = new searchNode(tempBoard, currentNode2.returnMoves() + 1,
                                                     currentNode2);
                if (currentNode2.returnPrevNode() != null && currentNode2.returnPrevNode()
                                                                         .returnBoard()
                                                                         .equals(tempBoard)) {
                    continue;
                }
                else {
                    gameQueue2.insert(tempNode);
                }
            }
            currentNode2 = gameQueue2.delMin();
            if (currentNode2.returnBoard().isGoal()) {
                solvable = false;
                break;
            }
        }
        if (solvable) {
            finalNode = currentNode1;
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return finalNode.returnMoves();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solutionBoardReverse = new Stack<Board>();
        searchNode tempNode = finalNode;
        solutionBoardReverse.push(finalNode.returnBoard());
        for (int i = 0; i < moves(); i++) {
            tempNode = tempNode.returnPrevNode();
            solutionBoardReverse.push(tempNode.returnBoard());
        }
        Queue<Board> solutionBoard = new Queue<Board>();

        for (int i = 0; i < moves() + 1; i++) {
            solutionBoard.enqueue(solutionBoardReverse.pop());
        }
        return solutionBoard;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

    // private methods
    private class boardComparator implements Comparator<searchNode> {
        public int compare(searchNode b1, searchNode b2) {
            return Integer.compare(b1.returnManhattan() + b1.returnMoves(),
                                   b2.returnManhattan() + b2.returnMoves());
        }
    }

    private class searchNode {
        private final Board board;
        private final int moves;
        private final searchNode prevNode;
        private final int manhattanVal;

        private searchNode(Board initBoard, int initMoves, searchNode initPrevNode) {
            board = initBoard;
            moves = initMoves;
            prevNode = initPrevNode;
            manhattanVal = board.manhattan();
        }

        private Board returnBoard() {
            return board;
        }

        private int returnMoves() {
            return moves;
        }

        private searchNode returnPrevNode() {
            return prevNode;
        }

        private int returnManhattan() {
            return manhattanVal;
        }
    }
}
