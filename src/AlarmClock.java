import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmClock  {

    // time parameters
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private int totalTimeRemaining = 0;

    private boolean isActive = false;

    public AlarmClock() {
    }

    // print status of the alarm clock
    private void status() {
        if(this.isActive) {
            System.out.println("Alarm is active.");
        }
        else {
            System.out.println("Alarm is inactive.");
        }
    }
    public void getTimeOnClock() {
        System.out.println("Alarm countdown currently at: "+this.hours+" hours, "+this.minutes+" minutes, and "+this.seconds+" seconds.");
        this.status();
    }

    // toggle time on clock
    public void setTimeOnClock(int hours, int minutes, int seconds) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;

        System.out.println("Alarm clock has been set to "+this.hours+" hours, "+this.minutes+" minutes, and "+this.seconds+" seconds.");
    }

    // getter and setter for time parameters after subthread conclusion
    private int convertToSeconds(){
        this.totalTimeRemaining = (this.seconds + this.minutes*60 + this.hours*3600);
        return this.totalTimeRemaining;
    }
    private void updateTime(int remainder){
        this.totalTimeRemaining = remainder;
        this.hours = this.totalTimeRemaining / 3600;
        this.minutes = this.totalTimeRemaining / 60 - this.hours*60;
        this.seconds = this.totalTimeRemaining - this.hours*3600 - this.minutes*60;
    }

    // run time countdown in separate thread
    public void startClock() throws InterruptedException {
        this.isActive = !this.isActive;

        if(this.isActive) {
            int currentTime = this.convertToSeconds();

            final AtomicInteger sharedTime = new AtomicInteger(currentTime);    // use atomicinteger to allow for shared variable access
            Thread t = new Thread(new Runnable() {
                public void run() {
                    int currentTime = (int) (System.currentTimeMillis()/1000);  // in seconds
                    int timeElapsed = 0;
                    int totalSeconds = sharedTime.get();
                    try {
                        while(timeElapsed < totalSeconds) {
                            Thread.sleep(1000);
                            ++timeElapsed;
                            sharedTime.getAndDecrement();
                        }
                    } catch(InterruptedException ie) {
                        System.out.println("time interrupted");
                    }
                }
            });

            // start background process of countdown
            // unorthodox, but you can tell when alarm clock finishes countdown with an uncaught exception
            t.start();
            while (t.isAlive()) {
                // allow user to check in on background process
                boolean endClock = false;
                Scanner scan = new Scanner(System.in);
                System.out.println("Check in on time remaining: 1\nPause Timer: 0\nEnd AlarmClock Module: -1");
                String input = scan.nextLine();
                switch(input) {
                    case "1":
                        updateTime(sharedTime.get());
                        break;
                    case "0":
                        this.isActive = !this.isActive;
                        t.interrupt();
                        System.out.println("Time Remaining: "+sharedTime.get());
                        updateTime(sharedTime.get());

                        break;
                    case "-1":
                        endClock = true;
                        break;
                }
                this.getTimeOnClock();
                this.status();
                if(endClock) {
                    break;
                }
            }
        }
    }
}
