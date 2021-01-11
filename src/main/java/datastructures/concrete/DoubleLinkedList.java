package datastructures.concrete;

import datastructures.interfaces.IList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import misc.exceptions.EmptyContainerException;

public class DoubleLinkedList<T> implements IList<T> {
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        front = null;
        back = null;
        size = 0;
    }

    @Override
    public void add(T item) {
        if (front == null) {
            front = new Node<>(item, null, null);
            back = front;
        } else {
            back.next = new Node<>(item, null, back);
            Node<T> temp = back;
            back = back.next;
            back.prev = temp;
        }
        size++;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new EmptyContainerException();
        }
        Node<T> result = back;
        if (front.next == back.next) {
            front = null;
            back = null;
        } else {
            back = back.prev;
            back.next = null;
        }
        size--;
        return result.data;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return front.data;
        } else if (index == size - 1) {
            return back.data;
        } else {
            int count = 0;
            Node<T> current = front;
            while (count < index) {
                current = current.next;
                count++;
            }
            return current.data;
        }
    }

    /**
     * Changes the first node depending on the option.
     * If the option is set, changes the front to the new node
     * and discards the old front node
     * If the option is insert, adds the new Node to the front.
     * @param item   data for the new node
     * @param temp   reference to the next pointer for the new node
     * @param option set or insert the front node of the list
     */
    private void modifyFirstNode(T item, Node<T> temp, String option) {
        front = new Node<>(item, temp, null);
        if (option.equals("set") && (size > 1)) {
            temp.prev = front;
        } else if (option.equals("insert")) {
            temp.prev = front;
            size++;
        }
    }

    /**
     * Changes the middle node depending on the option.
     * If the option is set, changes the node at the given index
     * to the new node and discards the old node.
     * If the option is insert, adds the new node at the given index.
     * @param index  the position for the new node
     * @param item   data for the new node
     * @param option set or insert
     */
    private void modifyMiddleNode(int index, T item, String option) {
        int count = 0;
        Node<T> current = front;
        while (count < index - 1) {
            current = current.next;
            count++;
        }
        Node<T> nextNode = current.next;
        if (option.equals("set")) {
            current.next = new Node<>(item, nextNode.next, current);
            if (nextNode.next != null) {
                nextNode.next.prev = current.next;
            }
        } else {
            current.next = new Node<>(item, nextNode, current);
            nextNode.prev = current.next;
            size++;
        }
    }

    /**
     * Changes the last node depending on the option.
     * If the option is set, changes the back node
     * to the new node and discards the old back node.
     * If the option is insert, adds the new node to the back.
     * @param item   data for the new node
     * @param next   reference to the next node
     * @param prev   reference to the previous node
     * @param option set or insert
     */
    private void modifyLastNode(T item, Node<T> next, Node<T> prev, String option) {
        back.prev.next = new Node<>(item, next, prev);
        if (option.equals("set")) {
            back = back.prev.next;
        } else {
            back.prev = back.prev.next;
            size++;
        }
    }

    @Override
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            modifyFirstNode(item, front.next, "set");
        } else if (index == size - 1) {
            modifyLastNode(item, null, back.prev, "set");
        } else {
            modifyMiddleNode(index, item, "set");
        }
    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            if (isEmpty()) {
                add(item);
            } else {
                modifyFirstNode(item, front, "insert");
            }
        } else if (index == size - 2) {
            back.prev.prev.next = new Node<>(item, back.prev, back.prev.prev);
            back.prev.prev = back.prev.prev.next;
            size++;
        } else if (index == size - 1) {
            modifyLastNode(item, back, back.prev, "insert");
        } else if (index == size) {
            add(item);
        } else {
            modifyMiddleNode(index, item, "insert");
        }
    }

    @Override
    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> deleted = null;
        if (index == 0) {
            deleted = front;
            front = front.next;
            size--;
            return deleted.data;
        } else if (index == size - 1) {
            return remove();
        } else {
            int count = 0;
            Node<T> current = front;
            while (count < index - 1) {
                current = current.next;
                count++;
            }
            deleted = current.next;
            current.next = deleted.next;
            current.next.prev = current;
            size--;
            return deleted.data;
        }
    }

    /**
     * Compares the given item to of the item of the given node.
     * Returns true if they are equal, false otherwise
     * @param item    given item to be compared against
     * @param current current node's item used to compared against the given item
     * @return true or false depending on whether the items are equal or not
     */
    private boolean itemsAreEqual(T item, Node<T> current) {
        if (item == null || current.data == null) {
            return current.data == item;
        } else {
            return item.equals(current.data);
        }
    }

    @Override
    public int indexOf(T item) {
        int index = 0;
        Node<T> current = front;
        while (current != null) {
            if (itemsAreEqual(item, current)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public boolean contains(T other) {
        Node<T> current = front;
        while (current != null) {
            if (itemsAreEqual(other, current)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = (T) current.data;
            current = current.next;
            return result;
        }
    }

    private static class Node<E> {
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
