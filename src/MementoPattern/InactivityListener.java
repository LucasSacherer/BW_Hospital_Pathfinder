package MementoPattern;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this is awt though, replace it with something else???

public class InactivityListener implements ActionListener {

    private Pane pane;
    private Action action;
    private int interval;
    private Timer timer = new Timer(0, this);

    Action returnToMain = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("RETURNING TO MAIN");
        }
    };

    public InactivityListener(Pane pane) {
        this.pane = pane;
        setAction(returnToMain);
        setInterval(1);
    }

    /*
     *  The Action to be invoked after the specified inactivity period
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /*
     *  The interval before the Action is invoked specified in minutes
     */
    public void setInterval(int minutes) {
        setIntervalInMillis(minutes * 60000);
    }

    /*
     *  The interval before the Action is invoked specified in milliseconds
     */
    public void setIntervalInMillis(int interval) {
        this.interval = interval;
        timer.setInitialDelay(interval);
    }

    //  Implement ActionListener for the Timer
    //what does this mean....
    public void actionPerformed(ActionEvent e)
    {
        ActionEvent ae = new ActionEvent(pane, ActionEvent.ACTION_PERFORMED, "");
        action.actionPerformed(ae);
    }


    public void startListening(int delay){
        Pane target = pane;

        target.setFocusTraversable(true);

        System.out.println("Here we go!");
        timer.setInitialDelay(interval);
        timer.setRepeats(false);
        timer.start();

        target.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(timer.isRunning()){
                    timer.stop();
                }
                timer = new Timer(delay, returnToMain);
                timer.setRepeats(false);
                timer.start();
                System.out.println("CAUGHT THAT KEY BOI");
            }
        });
        target.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(timer.isRunning()){
                    timer.stop();
                }
                timer = new Timer(delay, returnToMain);
                timer.setRepeats(false);
                timer.start();
                System.out.println("CAUGHT THAT MOUSE BOI");
            }
        });
    }
}
