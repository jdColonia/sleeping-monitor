package models;

import utils.SemaphoreManager;
import java.util.Random;

public class Monitor implements Runnable {
    private final Random random = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                // Wait to be woken up
                System.out.println("Monitor is sleeping... Waiting for students.");
                SemaphoreManager.monitorSemaphore.acquire();

                while (true) {
                    // Acquire mutex to safely check the queue
                    SemaphoreManager.mutexSemaphore.acquire();
                    Integer studentId = SemaphoreManager.getNextStudent();
                    SemaphoreManager.mutexSemaphore.release();

                    if (studentId == null) {
                        System.out.println("No students left, monitor goes back to sleep.");
                        break;
                    }

                    // Release seat before attending the student
                    SemaphoreManager.seatSemaphore.release();

                    System.out.println("Monitor is assisting Student " + studentId + "...");
                    SemaphoreManager.studentReadySemaphore.release(); // Notify student it's their turn
                    Thread.sleep(random.nextInt(2000)); // Simulate assistance time
                    System.out.println("Monitor has finished assisting Student " + studentId + ".");

                    SemaphoreManager.helpCompletedSemaphore.release(); // Notify student help is completed
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
