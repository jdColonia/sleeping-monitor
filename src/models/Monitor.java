package models;

import utils.SemaphoreManager;

import java.util.Random;

public class Monitor implements Runnable {
    private final Random random = new Random();

    @Override
    public void run() {
        System.out.println("🌅 Monitor has started their shift. Ready to assist students!");

        while (true) {
            try {
                // Wait to be woken up
                System.out.println("🛌 Monitor is sleeping... Waiting for students.");
                SemaphoreManager.monitorSemaphore.acquire();

                while (true) {
                    // Acquire mutex to safely check the queue
                    SemaphoreManager.mutexSemaphore.acquire();
                    Integer studentId = SemaphoreManager.getNextStudent();
                    SemaphoreManager.mutexSemaphore.release();

                    if (studentId == null) {
                        System.out.println("💤 No students left. Monitor goes back to sleep.");
                        break;
                    }

                    // Release seat before attending the student
                    SemaphoreManager.seatSemaphore.release();

                    System.out.println("🎓 Monitor is assisting Student " + studentId + "...");
                    SemaphoreManager.studentReadySemaphore.release(); // Notify student it's their turn
                    Thread.sleep(1000 + random.nextInt(2000)); // Simulate assistance time (1-3 sec)
                    System.out.println("✅ Monitor has finished assisting Student " + studentId + ". Next!");

                    SemaphoreManager.helpCompletedSemaphore.release(); // Notify student help is completed
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
