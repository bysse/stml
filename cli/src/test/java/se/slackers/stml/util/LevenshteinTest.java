package se.slackers.stml.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevenshteinTest {
    @Test
    void testTrivial() {
        assertEquals(0, Levenshtein.calculate("test", "test"));
        assertEquals(4, Levenshtein.calculate("test", ""));
        assertEquals(4, Levenshtein.calculate("", "test"));
    }

    @Test
    void testOther() {
        assertEquals(1, Levenshtein.calculate("test", "text"));
        assertEquals(2, Levenshtein.calculate("test", "texts"));
    }
}