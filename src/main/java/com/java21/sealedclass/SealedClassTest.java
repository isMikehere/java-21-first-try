package com.java21.sealedclass;

import org.junit.jupiter.api.Test;

/**
 * A sealed class imposes three important constraints on its permitted subclasses:
 * <p>
 * All permitted subclasses must belong to the same module as the sealed class.
 * Every permitted subclass must explicitly extend the sealed class.
 * Every permitted subclass must define a modifier: final, sealed, or non-sealed.
 */
abstract sealed class Puppy permits Chase, Rocky, Marshall, Sky, Rubble, Everrest {
    abstract String name();
}

public class SealedClassTest {

    @Test
    public void testPatternMatching() {
        //TODO:
    }

}
