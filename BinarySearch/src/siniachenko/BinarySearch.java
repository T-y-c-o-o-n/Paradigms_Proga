package siniachenko;

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int l = 0, r = args.length;
        // Pre: args \ {args[0]} отсортирован по невозрастанию
        // Post: r == ansIndex, если существует элемент <= x
        // Inv: l < ansIndex && ansIndex <= r
        while (l + 1 < r) {
            // l + 1 < r && l < ansIndex && ansIndex <= r
            int m = (l + r) / 2;
            // l + 1 < r && l < ansIndex && ansIndex <= r
            if (Integer.parseInt(args[m]) <= x) {
                // l + 1 < r && l < ansIndex && ansIndex <= r
                r = m;
                // l + 1 < r && l < ansIndex && ansIndex <= r
            } else {
                // l + 1 < r && l < ansIndex && ansIndex <= r
                l = m;
                // l < ansIndex && ansIndex <= r
            }
            // l < ansIndex && ansIndex <= r
        }
        // l + 1 >= r && l < ansIndex && ansIndex <= r
        // следовательно
        // args[r] <= x && args[r-1] > x
        if (r == args.length) {
            System.out.println("-1");
        } else {
            System.out.println(r - 1);
        }
    }
}
