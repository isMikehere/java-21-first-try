package com.java21;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * https://openjdk.org/jeps/430
 */
public class StringTemplateTest {

    @Test
    public void testStringTemplate() {
        var str = """
                hello, this is a long long string created by string templated.
                it has 2 lines.
                 """;
        Assertions.assertTrue(str.startsWith("hello"));
    }
}
