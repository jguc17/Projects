// this is a helper class for the AlarmClock problem
public class Timer implements Runnable {

    private int totalSeconds = 0;
    private int timeRemaining = 0;


    public Timer(int numSeconds) {
        this.totalSeconds = numSeconds;
        this.timeRemaining = this.totalSeconds;
    }

    public void run() {
        int currentTime = (int) (System.currentTimeMillis()/1000);  // in seconds
        try {
            while(this.timeRemaining > 0) {
                Thread.sleep(1000);
                --this.timeRemaining;
            }
        } catch(InterruptedException ie) {
            // threadMessage("Wasn't done counting yet!"); // how to invoke method from main class?
        }
    }
}
