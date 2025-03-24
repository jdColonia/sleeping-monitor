package utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class SemaphoreManager {
    public static final int NUM_CHAIRS = 3;

    public static final Semaphore monitorSemaphore = new Semaphore(0); // Wake up monitor
    public static final Semaphore mutexSemaphore = new Semaphore(1); // Exclusive access
    public static final Semaphore seatSemaphore = new Semaphore(NUM_CHAIRS); // Available seats
    public static final Semaphore studentReadySemaphore = new Semaphore(0); // Ensure students are selected correctly
    public static final Semaphore helpCompletedSemaphore = new Semaphore(0); // Ensure help completion synchronization

    private static final LinkedBlockingQueue<Integer> studentQueue = new LinkedBlockingQueue<>();

    public static synchronized void addStudentToQueue(int id) {
        studentQueue.offer(id);
    }

    public static synchronized Integer getNextStudent() {
        return studentQueue.poll();
    }
}