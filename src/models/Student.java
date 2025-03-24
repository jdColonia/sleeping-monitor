package models;

import utils.SemaphoreManager;
import java.util.Random;

public class Student implements Runnable {
    private final int id;
    private final Random random = new Random();

    public Student(int id) {
        this.id = id + 1;
    }

    @Override
    public void run() {
        while (true) {
            program();
            seekHelp();
        }
    }

    // Simulates programming time
    private void program() {
        try {
            System.out.println("Student " + id + " is programming.");
            Thread.sleep(random.nextInt(3000)); // Simulated programming time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Seeks help from the monitor
    private void seekHelp() {
        try {
            SemaphoreManager.mutexSemaphore.acquire(); // Exclusive access to check seats

            if (SemaphoreManager.seatSemaphore.tryAcquire()) {
                System.out.println("Student " + id + " sits in a waiting chair.");
                SemaphoreManager.addStudentToQueue(id);
                SemaphoreManager.mutexSemaphore.release(); // Release mutex

                SemaphoreManager.monitorSemaphore.release(); // Wake up the monitor if sleeping
                SemaphoreManager.studentReadySemaphore.acquire(); // Wait until monitor selects this student

                System.out.println("Student " + id + " is receiving help from the monitor.");
                SemaphoreManager.helpCompletedSemaphore.acquire(); // Ensure help is completed before leaving
            } else {
                System.out.println("Student " + id + " found no available seats and returns to the computer lab.");
                SemaphoreManager.mutexSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}