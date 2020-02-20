package search;

public class BinarySearchSpan {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i-1] = Integer.parseInt(args[i]);
        }
        int l = leftBound(arr, x);
        // l = первый i: arr[i] <= x, arr[i-1] > x
        int r = rightBound(arr, x);
        // r = первый j: arr[j] < x, arr[j-1] >= x
        //
        // l-5  l-4  l-3  l-2  l-1   l   l+1  l+2  l+3  l+4  l+5  l+6  l+7  l+8  l+9
        // r-10 r-9  r-8  r-7  r-6  r-5  r-4  r-3  r-2  r-1   r   r+1  r+2  r+3
        // >x   >x   >x   >x   >x   =x   =x   =x   =x   =x   <x   <x   <x   <x
        //
        // l-5  l-4  l-3  l-2  l-1   l   l+1  l+2  l+3
        // r-10 r-9  r-8  r-7  r-6   r   r+1  r+2  r+3
        // >x   >x   >x   >x   >x   <x   <x   <x   <x
        //
        System.out.print(l + " " + (r - l));
    }

    // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
    // Post: Ret = "индекс вставки", первый элемент <= x
    public static int leftBound(int[] arr, int x) {
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
        return r;
    }

    // Pre: arr[0]..arr[n-1] отсортирован по невозрастанию
    // Post: Ret = "индекс вставки", первый элемент < x
    public static int rightBound(int[] arr, int x) {
        int n = arr.length;
        int l = -1, r = arr.length;
        // INV: l < r && (arr[l] >= x && arr[r] < x)
        while (l + 1 < r) {
            // l + 1 < r && (arr[l] >= x && arr[r] < x)
            int m = (l + r) / 2;
            // l < m < r && (arr[l] >= x && arr[r] < x)
            if (arr[m] < x) {
                // l < m < r && (arr[m] < x) && (arr[l] >= x && arr[r] < x)
                r = m;
                // l < r && (arr[l] >= x && arr[r] < x)
            } else {
                // l < m < r && (arr[m] >= x) && (arr[l] >= x && arr[r] < x)
                l = m;
                // l < r && (arr[l] >= x && arr[r] < x)
            }
        }
        // (l < r && l + 1 >= r)  =>  l + 1 = r
        // (l + 1 = r && arr[l] >= x && arr[r] < x)  =>  r - "индекс вставки", первый элемент < x
        return r;
    }
}
