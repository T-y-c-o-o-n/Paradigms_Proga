package search;

public class BinarySearchMissing {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i-1] = Integer.parseInt(args[i]);
        }
        System.out.println(binSearch(arr, x));
    }

    // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
    // Post: Ret = "индекс вставки", первый элемент <= x, если существует i: arr[i] = x,
    // иначе Ret = (-("индекс вставки") - 1)
    public static int binSearch(int[] arr, int x) {
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
        // (l + 1 = r && arr[l] > x && arr[r] <= x)  =>  r - "индекс вставки", первый элемент <= x
        if (r == arr.length || arr[r] != x) {
            return -r -1;
        } else {
            return r;
        }
    }
}
