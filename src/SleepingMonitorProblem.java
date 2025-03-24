import models.Monitor;
import models.Student;

public class SleepingMonitorProblem {
    private static final int NUM_STUDENTS = 10;

    public static void main(String[] args) {
        // Create and start the monitor thread
        Thread monitorThread = new Thread(new Monitor());
        monitorThread.start();

        // Create and start student threads
        Thread[] students = new Thread[NUM_STUDENTS];
        for (int i = 0; i < NUM_STUDENTS; i++) {
            students[i] = new Thread(new Student(i));
            students[i].start();
        }
    }
}