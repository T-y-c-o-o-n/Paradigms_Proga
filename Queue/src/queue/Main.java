package queue;

import queue.ArrayQueueModule;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 100; ++i) {
            int type = sc.nextInt();
            if (type == 0) {
                System.out.println(ArrayQueueModule.element());
            }
            if (type == 1) {
                ArrayQueueModule.enqueue(sc.next());
            }
            if (type == 2) {
                System.out.println(ArrayQueueModule.dequeue());
            }
            if (type == 3) {
                System.out.println(ArrayQueueModule.size());
            }
            if (type == 4) {
                System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
            }
        }
    }
}
