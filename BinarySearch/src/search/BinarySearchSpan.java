package search;

public class BinarySearchSpan {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i-1] = Integer.parseInt(args[i]);
        }
        int l = leftBound(arr, x);
        int r = rightBound(arr, x);
        System.out.print(l + " " + (r - l));
    }

    public static int leftBound(int[] arr, int x) {  // R = first i: a[i] <= x
        int n = arr.length;
        int l = -1, r = arr.length;
        // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
        // Post: r == ansIndex, если существует элемент <= x, иначе: l == n - 1 && r == n
        // Inv: l < ansIndex && ansIndex <= r
        while (l + 1 < r) {
            // l + 1 < r && l < ansIndex && ansIndex <= r
            int m = (l + r) / 2;
            // l + 1 < r && l < ansIndex && ansIndex <= r
            if (arr[m] <= x) {
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
        return r;
    }

    public static int rightBound(int[] arr, int x) {  // R = first i: a[i] < x
        int n = arr.length;
        int l = -1, r = arr.length;
        // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
        // Post: r == ansIndex, если существует элемент <= x, иначе: l == n - 1 && r == n
        // Inv: l < ansIndex && ansIndex <= r
        while (l + 1 < r) {
            // l + 1 < r && l < ansIndex && ansIndex <= r
            int m = (l + r) / 2;
            // l + 1 < r && l < ansIndex && ansIndex <= r
            if (arr[m] < x) {
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
        return r;
    }
}
