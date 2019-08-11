package ru.akozlov.leetcode.concurency;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class H2O {

    private int[] semafor = {0, 0};
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        synchronized (this) {
            semafor[0] = 0;
            semafor[1] = 0;
            notifyAll();
        }
    });

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while (true) {
            synchronized (this) {
                int hCount = semafor[0];
                if (hCount < 2) {
                    hCount++;
                    semafor[0] = hCount;
                    break;
                } else {
                    wait();
                }
            }
        }
        try {
            releaseHydrogen.run();
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while (true) {
            synchronized (this) {
                int oCount = semafor[1];
                if (oCount < 1) {
                    oCount++;
                    semafor[1] = oCount;
                    break;
                } else {
                    wait();
                }
            }
        }
        try {
            releaseOxygen.run();
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
