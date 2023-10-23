package com.java21;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * https://openjdk.org/jeps/431
 */
public class SequencedCollectionTest {

    @Test
    public void sequencedListTest() {
        var list = List.of(1, 2, 3, 5, 7);
        Assertions.assertEquals(list.reversed(), List.of(7, 5, 3, 2, 1));
    }

    @Test
    public void sequencedMapTest() {
        var map = new TreeMap<>(Map.of("name", "mike", "age", 18));
        Assertions.assertEquals(map.reversed().firstKey(), "name");
    }

    @Test
    public void sequenceSetTest() {
        var orderedSet = new ConcurrentSkipListSet<>(Set.of("B", "C", "A"));
        Assertions.assertEquals(orderedSet.reversed().getLast(), "A");
    }

}
