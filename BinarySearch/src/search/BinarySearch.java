package search;

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i-1] = Integer.parseInt(args[i]);
        }
        // System.out.println(iterativeBinSearch(arr, x));
        System.out.println(recursiveBinSearch(arr, x, -1, arr.length));
    }

    // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
    // Post: Ret = "индекс вставки", первый элемент <= x
    public static int iterativeBinSearch(int[] arr, int x) {
        int n = arr.length;
        int l = -1, r = arr.length;
        // INV: l < r && (arr[l] > x && arr[r] <= x)
        while (l + 1 < r) {
            // l + 1 < r && (arr[l] > x && arr[r] <= x)
            int m = (l + r) / 2;
            // l < m < r && (arr[l] > x && arr[r] <= x)
            if (arr[m] <= x) {
                // l < m < r && (arr[m] <= x) && (arr[l] > x && arr[r] <= x)
                r = m;
                // l < r && (arr[l] > x && arr[r] <= x)
            } else {
                // l < m < r && (arr[m] > x) && (arr[l] > x && arr[r] <= x)
                l = m;
                // l < r && (arr[l] > x && arr[r] <= x)
            }
        }
        // (l < r && l + 1 >= r)  =>  l + 1 = r
        // (l + 1 = r && arr[l] > x && arr[r] <= x)  =>  arr[r] - первый элемент <= x
        return r;
    }

    // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию && l < r && (arr[l] > x && arr[r] <= x)
    // Post: Ret = "индекс вставки", первый элемент <= x
    public static int recursiveBinSearch(int[] arr, int x, int l, int r) {
        int n = arr.length;
        // l < r && (arr[l] > x && arr[r] <= x)
        if (l + 1 == r) {
            // (l + 1 = r && arr[l] > x && arr[r] <= x)  =>  arr[r] - первый элемент <= x
            return r;
        }
        // l < r && l + 1 < r
        int m = (l + r) / 2;
        // l < m < r && (arr[l] > x && arr[r] <= x)
        if (arr[m] <= x) {
            // l < m && (arr[l] > x && arr[m] <= x)
            return recursiveBinSearch(arr, x, l, m);
        } else {  // arr[m] > x
            // m < r && (arr[m] > x && arr[r] <= x)
            return recursiveBinSearch(arr, x, m, r);
        }
    }
}
