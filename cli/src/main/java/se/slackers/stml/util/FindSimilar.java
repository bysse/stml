package se.slackers.stml.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FindSimilar {
    public static Collection<String> findSimilar(String needle, int maxDistance, Collection<String> haystack) {
        final List<String> result = new ArrayList<>();

        for (String straw : haystack) {
            int distance = Levenshtein.calculate(needle, straw);
            if (distance <= maxDistance) {
                result.add(straw);
            }
        }

        return result;
    }
}
