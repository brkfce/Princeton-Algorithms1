/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] array;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return (size);
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        if (size == array.length) {
            resizeArray(2 * array.length);
        }
        array[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue an empty array");
        }
        int randomizedIndex = StdRandom.uniform(size);
        Item item = array[randomizedIndex];
        array[randomizedIndex] = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size >= 0 && size == array.length / 4) {
            resizeArray(array.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample an empty array");
        }
        int randomizedIndex = StdRandom.uniform(size);
        return (array[randomizedIndex]);

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Empty
    }

    // PRIVATE FUNCTIONS

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] tempArray;
        private int randomizedRange;

        public RandomizedQueueIterator() {
            randomizedRange = size;
            tempArray = (Item[]) new Object[randomizedRange];
            for (int i = 0; i < randomizedRange; i++) {
                tempArray[i] = array[i];
            }
        }

        public boolean hasNext() {
            return randomizedRange > 0;
        }

        public Item next() {
            if (!(hasNext())) {
                throw new NoSuchElementException("Out of bounds of array");
            }
            else {
                int randomizedIndex = StdRandom.uniform(randomizedRange);
                Item item = tempArray[randomizedIndex];
                tempArray[randomizedIndex] = tempArray[randomizedRange - 1];
                randomizedRange--;
                return item;
            }
        }

        // Unsupported functionality
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

    }

    private void resizeArray(int newSize) {
        Item[] resizedArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            resizedArray[i] = array[i];
        }
        array = resizedArray;
    }
}
