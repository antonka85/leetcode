package ru.akozlov.leetcode.concurrency;

import java.util.function.IntConsumer;

public class ZeroEvenOdd {

    private int n;
    private int i = 0;

    public ZeroEvenOdd(int n) {
        this.n = n * 2;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (n < 1) {
                    break;
                }

                while (n > 0 && (n & 1) != 0) {
                    wait(1);
                }
                if (n < 1) {
                    break;
                }
                printNumber.accept(0);
                i++;
                n--;
                notifyAll();
                if (n < 1) {
                    break;
                }

            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (n < 1) {
                    break;
                }

                while (n > 0 && ((i & 1) != 0 || (n & 1) == 0)) {
                    wait(1);
                }
                if (n < 1) {
                    break;
                }

                printNumber.accept(i);
                n--;
                notifyAll();
                if (n < 1) {
                    break;
                }

            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (n < 1) {
                    break;
                }

                while (n > 0 && ((i & 1) == 0 || (n & 1) == 0)) {
                    wait(1);
                }
                if (n < 1) {
                    break;
                }

                printNumber.accept(i);
                n--;
                notifyAll();
                if (n < 1) {
                    break;
                }
            }
        }
    }
}
