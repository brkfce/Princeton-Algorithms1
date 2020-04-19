/* *****************************************************************************
 *  Name:   Callum Sutton
 *  Date:   17/04/2020
 *  Description:    Week 2 project on queues
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque() {
    }

    // tracks the size of the linked list (constant time)
    private int listSize = 0;

    // track the ends of the deque
    private Node first = null;
    private Node last = null;

    // creates a node datatype, with data "item" and pointer to next node "next"
    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public void setItem(Item newItem) {
            item = newItem;
        }

        public Item getItem() {
            return (item);
        }

        public void setNext(Node newNext) {
            next = newNext;
        }

        public Node getNext() {
            return (next);
        }

        public void setPrev(Node newPrev) {
            prev = newPrev;
        }

        public Node getPrev() {
            return (prev);
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        return (listSize);
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
        Node newFirst = new Node();
        newFirst.setItem(item);
        newFirst.setNext(first);
        first.setPrev(newFirst);
        first = newFirst;
        if (listSize == 1) {
            last = newFirst;
        }
        listSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
        Node newLast = new Node();
        newLast.setItem(item);
        newLast.setNext(null);
        newLast.setPrev(last);
        last.setNext(newLast);
        last = newLast;
        if (listSize == 1) {
            first = last;
        }
        listSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("The list has 0 elements, cannot remove");
        }
        Item tempItem = first.getItem();
        Node tempNext = first.getNext();
        first.setItem(null);
        first.setNext(null);
        if (size() != 1) {
            first = tempNext;
            first.setPrev(null);
        }
        else {
            first = null;
            last = null;
        }
        listSize--;
        return (tempItem);
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("The list has 0 elements, cannot remove");
        }
        Item tempItem = last.getItem();
        Node tempPrev = last.getPrev();
        last.setItem(null);
        last.setPrev(null);
        if (size() != 1) {
            last = tempPrev;
            last.setNext(null);
        }
        else {
            first = null;
            last = null;
        }
        listSize--;
        return (tempItem);
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // implement an iterator
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return !(current.getNext() == null);
        }

        public Item next() {
            if (current.getNext() == null) {
                throw new NoSuchElementException("End of list reached, no next node");
            }
            Item item = current.getItem();
            current = current.getNext();
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }


}
