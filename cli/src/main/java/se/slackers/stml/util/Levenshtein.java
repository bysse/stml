package se.slackers.stml.util;

public class Levenshtein {
    public static int calculate(String a, String b) {
        int m = a.length();
        int n = b.length();

        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }

        int[] row0 = new int[n + 1];
        int[] row1 = new int[n + 1];

        for (int i = 0; i < n; i++) {
            row0[i] = i;
        }

        for (int i = 0; i < m; i++) {
            row1[0] = i + 1;

            for (int j = 0; j < n; j++) {
                int delete = row0[j + 1] + 1;
                int insert = row1[j] + 1;
                int subst = row0[j] + (a.charAt(i) == b.charAt(j) ? 0 : 1);
                row1[j+1] = Math.min(delete, Math.min(insert, subst));

            }

            int[] row = row0;
            row0 = row1;
            row1 = row;
        }
        return row0[n];
    }
}
