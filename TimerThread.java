package ass;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class that implements Runnable interface to create a timer thread 
 * to display the time countdown to the user.
 * 
 */

public class TimerThread extends Application implements Runnable {

    public static int timeRemaining = 120; // 2 min timer
    private Label timerLabel;
    private Stage stage;
    private VBox all_vb;
    private TextField tf;
    private Label question_label;
    public int time_taken = 0;

    /**
     * Constructor 
     * @param stage main stage
     * @param all_vb VBox that carries all the elements
     * @param timerLabel label to display timer
     * @param tf textfield which will be passed into another class' function.
     * @param question_label this will also be passed into another class' function.
     */
    public TimerThread(Stage stage, VBox all_vb, Label timerLabel, TextField tf, Label question_label) {
        this.stage = stage;
        this.all_vb = all_vb;
        this.timerLabel = timerLabel;
        this.tf = tf;
        this.question_label = question_label;
    }

    /**
     * method to create time thread 
     */
    public void start(Stage stage) {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    /**
     * run() method from Runnable interface used to update time label or end the quiz. 
     * All run() methods work differently to update the UI. 
     */
    @Override
    public void run() { // background thread
        while (timeRemaining > 0) {
            final int currentTime = timeRemaining; // saved to the final variable so it can be used in the inner class.

            Platform.runLater(new Runnable() { // wrapping up the UI code in another Runnable and using the same run method using Platform.runLater() to update the UI.
                // this is necessary because JavaFX is not thread-safe, and we need to update the UI on the JavaFX Application Thread.
                // in a different way to update the label properly.
                public void run() {
                    timerLabel.setText("Time left: " + currentTime + " seconds");
                }
            });

            try {
                Thread.sleep(1000); // delays/sleeps each second before continuing the loop.
            } catch (InterruptedException e) { // if thread is interrupted, it breaks out of the loop.
                break; 
            }

            timeRemaining--; // decrement time remaining by 1 second.
        }

        if (timeRemaining == 0) {
            
            // end quiz on JavaFX thread
            Platform.runLater(() -> {
                NextEventHandler.end_quiz(all_vb, question_label, stage, tf);
                
            });
        }
        
        
        
    }

    /**
     * 
     * @return timeRemaining - this will be used to calculate how much time the user has taken.
     */
    public static int getTime() {
        return timeRemaining;
    }
}
