package stopwatch;

public class StopWatch {

    
    public static void main(String[] args) throws InterruptedException {
        
        timer thisTimer = new timer();
        thisTimer.setSize(400,300);
        thisTimer.setVisible(true);
        
        thisTimer.countDown();
    }
    
}
