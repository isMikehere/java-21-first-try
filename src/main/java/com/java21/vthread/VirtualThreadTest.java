package com.java21.vthread;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VirtualThreadTest {

    /**
     * @throws InterruptedException
     */
    @Test
    public void virtualThreadFactoryMethod() throws InterruptedException {
        var threadNames = new ConcurrentSkipListSet<>();
        var vThreads = IntStream
                .range(0, 100)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                            var first = i == 0;
                            if (first) threadNames.add(Thread.currentThread().toString());

                            try {
                                Thread.sleep(100l);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            if (first) threadNames.add(Thread.currentThread().toString());

                            try {
                                Thread.sleep(100l);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (first) threadNames.add(Thread.currentThread().toString());

                        })
                ).toList();

        for (Thread t : vThreads) t.start();
        for (Thread t : vThreads) t.join();

        Assertions.assertTrue(threadNames.size() > 1);
        threadNames.forEach(System.out::println);
        /**
         * VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
         * VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
         * we can see the thread name is same, but the actual platform thread has been changed from worker-1 to worker-3
         */
    }

    /***
     *
     * new virtualThread method in Executors
     * @throws InterruptedException
     */
    @Test
    public void virtualThreadWithExecutor() throws InterruptedException {
        ExecutorService vExecutorService = Executors.newVirtualThreadPerTaskExecutor();
        var future1 = vExecutorService
                .submit(() -> {
                    try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Thread name: %s\n", Thread.currentThread());
                });

        var future2 = vExecutorService
                .submit(() -> {
                    try {
                        Thread.sleep(10l);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Thread name: %s\n", Thread.currentThread());
                });

        Thread.sleep(1200); // change the  delay will get different result
        Stream.of(future1, future2).forEach(future -> {
            switch (future.state()) {
                case SUCCESS, FAILED, RUNNING, CANCELLED -> System.out.println(future.state());
                default -> System.out.println("unknown future");
            }
        });
    }
}
