import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class AlarmClock  {

    // time params
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    // mode: countdown or set time
    private boolean countdownMode = true;
    private boolean isActive = false;

    public AlarmClock() {
    }

//    public void run() {
//        System.out.println("Alarm clock sequence started!");
//        this.startClock();
//    }

    // print status
    private void status() {
        if(this.isActive) {
            System.out.println("Alarm is active.");
        }
        else {
            System.out.println("Alarm is inactive.");
        }
    }
    public void getTimeOnClock(int hours, int minutes, int seconds) {
        if(this.countdownMode) {
            System.out.println("Alarm countdown currently at: "+this.hours+" hours, "+this.minutes+" minutes, and "+this.seconds+" seconds.");
        }
        else {
            System.out.println("Alarm time currently set to: "+this.hours+":"+this.minutes+":"+this.seconds);
        }
        this.status();
    }

    // toggle time on clock
    public void setTimeOnClock(int hours, int minutes, int seconds) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;

        if(this.countdownMode) System.out.println("Alarm clock has been set to "+this.hours+" hours, "+this.minutes+" minutes, and "+this.seconds+" seconds.");
        else System.out.println("Alarm time has been set to: "+this.hours+":"+this.minutes+":"+this.seconds);
    }

    public int convertToSeconds(){
        return this.seconds + this.minutes*60 + this.hours*3600;
    }

    // toggle alarm clock mode
    public boolean isCountdownMode() {
        whichMode();
        return countdownMode;
    }
    public void setCountdownMode(boolean countdownMode) {
        this.countdownMode = !this.countdownMode;
        whichMode();
    }
    private void whichMode() {
        if(this.countdownMode) System.out.println("Alarm clock is in count down mode.");
        else System.out.println("Alarm clock is in clock time mode.");
    }

    // TODO: in main thread, perform a join on the timer runnable, then periodically check in on Timer and update clock countdown
    // TODO: move the sleep command from runnable to main thread
    public void startClock() {
        this.isActive = !this.isActive;
        if(this.countdownMode) {
            Thread t = new Thread(new Timer(this.convertToSeconds()));
            // start background process of countdown
            t.start();
        }
    }
}
