/* *****************************************************************************
 *  Name:   Callum Sutton
 *  Date:   19/04/2020
 *  Description:    Client-side implementation of randomized queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomQueue.enqueue(item);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomQueue.dequeue());
        }

    }
}
