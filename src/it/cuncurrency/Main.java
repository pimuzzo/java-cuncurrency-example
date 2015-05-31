package it.cuncurrency;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    // Ellipsis for arguments instead of an array
    public static void main(final String... args) {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

        Thread producer = new Thread(new Producer(queue));
        Thread consumer1 = new Thread(new Consumer(queue));
        Thread consumer2 = new Thread(new Consumer(queue));

        producer.start();
        consumer1.start();
        consumer2.start();

    }
}
