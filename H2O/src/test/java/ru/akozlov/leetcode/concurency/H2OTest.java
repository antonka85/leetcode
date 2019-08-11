package ru.akozlov.leetcode.concurency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class H2OTest {

    private static final String TEST_CASE_1 = "HHHOOHHHOHHOHHO";
    private static final int MOLECULE_COUNT = 3;

    @Test
    public void testCase1() throws IOException, InterruptedException {
        final H2O h2o = new H2O();
        final CountDownLatch latch = new CountDownLatch(TEST_CASE_1.length());
        final List<Thread> workerList = new ArrayList<>(TEST_CASE_1.length());
        PrintStream consoleStream = System.out;
        try {
            try (ByteArrayOutputStream stringWriter = new ByteArrayOutputStream()) {
                System.setOut(new PrintStream(stringWriter));
                for (char ch: TEST_CASE_1.toCharArray()) {
                    Thread workerThread = new Thread(new Worker(h2o, ch, latch));
                    workerThread.start();
                    workerList.add(workerThread);
                    latch.countDown();
                }
                for (Thread workerThread: workerList) {
                    workerThread.join();
                }
                String result = stringWriter.toString(StandardCharsets.UTF_8);
                Assertions.assertEquals(TEST_CASE_1.length(), result.length());
                for (int i = 0; i < result.length(); i = i + MOLECULE_COUNT) {
                    int hCount = 0;
                    int oCoint = 0;
                    for (int j = i; j < i + MOLECULE_COUNT; j++) {
                        if (result.charAt(j) == 'H') {
                            hCount++;
                        } else {
                            oCoint++;
                        }
                    }
                    Assertions.assertEquals(2, hCount);
                    Assertions.assertEquals(1, oCoint);
                }

            }
        } finally {
            System.setOut(consoleStream);
        }
    }

    private static class Worker implements Runnable {

        private H2O h2O;
        private char molecule;
        private CountDownLatch latch;

        private Worker(H2O h2O, char molecule, CountDownLatch latch) {
            this.h2O = h2O;
            this.molecule = molecule;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await();
                doWork();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        private void doWork() throws InterruptedException {
            switch (molecule) {
                case 'H': h2O.hydrogen(() -> System.out.print("H")); break;
                case 'O': h2O.oxygen(()-> System.out.print("O")); break;
                default: throw new IllegalArgumentException("не поддерживаемая молекула " + molecule);
            }
        }
    }
}
