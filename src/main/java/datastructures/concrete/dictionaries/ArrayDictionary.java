package datastructures.concrete.dictionaries;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    public static final int NUM_PAIRS = 100;
    private Pair<K, V>[] pairs;
    private int size;

    public ArrayDictionary() {
        pairs = makeArrayOfPairs(NUM_PAIRS);
        size = 0;
    }

    /**
     * Returns a new, empty array of the given size
     * that can contain Pair objects.
     *
     * @param arraySize number of elements
     * @return an array of the given size
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    /**
     * Compares the given key to of the key of the given pair.
     * Returns true if they are equal, false otherwise
     * @param key         given key to be compared against
     * @param currentPair pair's key used to compared against key
     * @return true or false depending on whether the keys are equal or not
     */
    private boolean keysAreEqual(K key, Pair<K, V> currentPair) {
        if (key == null || currentPair.key == null) {
            return currentPair.key == key;
        } else {
            return currentPair.key.equals(key);
        }
    }

    /**
     * Copies all the elements of this array to another bigger array
     * @param length the size of the new array
     * @return a new array occupied with this array's elements
     */
    private Pair<K, V>[] transferPairs(int length) {
        Pair<K, V>[] newPairs = makeArrayOfPairs(length);
        for (int i = 0; i < size; i++) {
            Pair<K, V> currentPair = pairs[i];
            newPairs[i] = new Pair<>(currentPair.key, currentPair.value);
        }
        return newPairs;
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        for (int i = 0; i < size; i++) {
            Pair<K, V> currentPair = pairs[i];
            if (keysAreEqual(key, currentPair)) {
                return currentPair.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (size == pairs.length) {
            pairs = transferPairs(size * 2);
        }
        if (containsKey(key)) {
            boolean found = false;
            int currentIndex = 0;
            while (!found) {
                Pair<K, V> currentPair = pairs[currentIndex];
                if (keysAreEqual(key, currentPair)) {
                    currentPair.value = value;
                    found = true;
                } else {
                    currentIndex++;
                }
            }
        } else {
            pairs[size] = new Pair<>(key, value);
            size++;
        }
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        V result = null;
        boolean found = false;
        int currentIndex = 0;
        while (!found) {
            Pair<K, V> currentPair = pairs[currentIndex];
            if (keysAreEqual(key, currentPair)) {
                result = currentPair.value;
                currentPair.key = pairs[size - 1].key;
                currentPair.value = pairs[size - 1].value;
                found = true;
            } else {
                currentIndex++;
            }
        }
        size--;
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            Pair<K, V> currentPair = pairs[i];
            if (keysAreEqual(key, currentPair)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
