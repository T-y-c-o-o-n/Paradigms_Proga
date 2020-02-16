package search;

public class BinarySearch {
    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println("0");
            return;
        }
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i-1] = Integer.parseInt(args[i]);
        }
        // System.out.println(iterativeBinSearch(arr, x));
        System.out.println(recursiveBinSearch(arr, x, -1, arr.length));
    }

    public static int iterativeBinSearch(int[] arr, int x) {
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

    public static int recursiveBinSearch(int[] arr, int x, int l, int r) {
        int n = arr.length;
        if (l + 1 == r) {
            return r;
        }
        int m = (l+r)/2;
        if (arr[m] <= x) {
            return recursiveBinSearch(arr, x, l, m);
        }
        return  recursiveBinSearch(arr, x, m, r);
    }
}
