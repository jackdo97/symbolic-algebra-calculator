package datastructures;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.fail;

/**
 * This class should contain all the tests you implement to verify that
 * your 'delete' method behaves as specified.
 *
 * This test _extends_ your TestDoubleLinkedList class. This means that when
 * you run this test, not only will your tests run, all of the ones in
 * TestDoubleLinkedList will also run.
 *
 * This also means that you can use any helper methods defined within
 * TestDoubleLinkedList here. In particular, you may find using the
 * 'assertListMatches' and 'makeBasicList' helper methods to be useful.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteFunctionality extends TestDoubleLinkedList {
    @Test(timeout=SECOND)
    public void basicTestDelete() {
        IList<String> list = this.makeBasicList();

        list.add("d");
        list.add("e");

        list.delete(0);
        this.assertListMatches(new String[] {"b", "c", "d", "e"}, list);

        list.delete(1);
        this.assertListMatches(new String[] {"b", "d", "e"}, list);

        list.delete(1);
        this.assertListMatches(new String[] {"b", "e"}, list);
    }

    @Test(timeout=SECOND)
    public void basicTestDeleteDecrementsSize() {
        IList<String> list = makeBasicList();
        int initSize = list.size();
        list.delete(1);

        assertEquals(initSize - 1, list.size());
    }

    @Test(timeout=SECOND)
    public void testSetThenDelete() {
        IList<String> list = this.makeBasicList();

        for (int i = 0; i < list.size(); i++) {
            list.set(i, "" + i);
        }

        list.delete(0);
        this.assertListMatches(new String[] {"1" , "2"}, list);

        list.delete(1);
        this.assertListMatches(new String[] {"1"}, list);

        list.delete(0);
        this.assertListMatches(new String[] {}, list);
    }

    @Test(timeout=SECOND)
    public void testDeleteMany() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 1000;

        for (int i = 0; i < cap; i++) {
            list.add(i);
        }

        assertEquals(cap, list.size());

        for (int i = cap - 1; i >= 0; i--) {
            int value = list.delete(i);
            assertEquals(i, value);
        }

        assertEquals(0, list.size());
    }

    @Test(timeout=SECOND)
    public void testAlternatingAddAndDelete() {
        int iterators = 1000;

        IList<String> list = new DoubleLinkedList<>();

        for (int i = 0; i < iterators; i++) {
            String entry = "" + i;
            list.add(entry);
            assertEquals(1, list.size());

            String out = list.delete(0);
            assertEquals(entry, out);
            assertEquals(0, list.size());
        }
    }

    @Test(timeout=SECOND)
    public void testDeleteOutOfBoundsThrowsException() {
        IList<String> list = this.makeBasicList();

        try {
            list.delete(-10);
            // We didn't throw an exception? Fail now.
            fail("Expected IndexOutOfBoundException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }

        try {
            list.delete(3);
            // We didn't throw an exception? Fail now.
            fail("Expected IndexOutOfBoundException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }

        try {
            list.delete(4);
            // We didn't throw an exception? Fail now.
            fail("Expected IndexOutOfBoundException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
    }

    @Test(timeout=SECOND)
    public void testDeleteOnEmptyListThrowsException() {
        IList<String> list = this.makeBasicList();

        list.add("d");
        list.delete(list.size() - 1);
        list.delete(list.size() - 2);
        list.delete(list.size() - 1);
        list.delete(0);

        assertEquals(0, list.size());

        try {
            list.delete(0);
            // We didn't throw an exception? Fail now.
            fail("Expected IndexOutOfBoundException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
    }
}
