package ru.akozlov.leetcode.concurrency;

class FooBar {
    private int n;
    private boolean isFoo = true;
    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (!isFoo) {
                    wait();
                }
                printFoo.run();
                isFoo = false;
                notify();
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (isFoo) {
                    wait();
                }
                printBar.run();
                isFoo = true;
                notify();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
        }
    }
}