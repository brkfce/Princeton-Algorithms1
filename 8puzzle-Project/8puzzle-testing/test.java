/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class test {
    private class searchNode {
        private final int data;
        private final searchNode prevNode;

        private searchNode(int inputdata, searchNode previous) {
            data = inputdata;
            prevNode = previous;
        }

        private searchNode returnPrevNode() {
            return prevNode;
        }
    }

    MinPQ<searchNode> gameQueue1 = new MinPQ<searchNode>();

    public test() {
        searchNode node1 = new searchNode(5, null);
        searchNode node2 = new searchNode(6, null);
        searchNode testNode = node1;
        searchNode node6 = new searchNode(6, testNode);
        testNode = node2;
        gameQueue1.insert(node6);
    }

    public void testing() {

        StdOut.println(gameQueue1.size());
        StdOut.println(gameQueue1.delMin().returnPrevNode().data);

    }

    public static void main(String[] args) {
        test newtest = new test();
        newtest.testing();
    }
}
