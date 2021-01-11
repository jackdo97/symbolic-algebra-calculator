package datastructures;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

/**
 * This file should contain any tests that check and make sure your
 * delete method is efficient.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteStress extends TestDoubleLinkedList {
    @Test(timeout=15 * SECOND)
    public void testDeleteFromEndIsEfficient() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 500000;

        for (int i = 0; i < cap; i++) {
            list.add(i);
        }

        for (int i = 0; i < cap; i++) {
            list.add(-1);
            list.delete(list.size() - 1);
        }

        assertEquals(cap, list.size());
    }

    @Test(timeout=15 * SECOND)
    public void testDeleteFromFrontIsEfficient() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 500000;

        for (int i = 0; i < cap; i++) {
            list.add(i);
        }

        for (int i = 0; i < cap; i++) {
            list.add(-1);
            list.delete(0);
        }

        assertEquals(cap, list.size());
    }
}
