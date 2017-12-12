package MementoPattern;

import boundary.GodController;
import boundary.SceneSwitcher;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//this is awt though, replace it with something else???

public class InactivityListener implements ActionListener {

    private Pane pane;
    private Action action;
    private int interval;
    private Timer timer;

    private GodController god;
    private SceneSwitcher sceneSwitcher;

    Action returnToMain = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                System.out.println("RETURNING TO MAIN");
                System.out.println("Pane Name" + pane);
                sceneSwitcher.toMain(god, pane);
            }catch(IOException exception){
                System.out.println("failed");
            }
        }
    };

    public InactivityListener(Pane pane, GodController god,  SceneSwitcher sceneSwitcher) {
        this.pane = pane;
        this.god = god;
        this.sceneSwitcher = sceneSwitcher;
        this.timer = new Timer(0, this);

        setAction(returnToMain);
        setInterval(0.2);
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
    public void setInterval(double minutes) {
        setIntervalInMillis((int)(minutes * 60000));
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

    public void setPane(Pane p){
        pane = p;
        setAction(returnToMain);
    }

    public void startListening(){
        Pane target = pane;

        target.setFocusTraversable(true);

        if(timer.isRunning()){
            timer.stop();
            timer = new Timer(interval, this);;
            System.out.println("STOPPED");
        }
        System.out.println("Here we go!");
        timer.setInitialDelay(interval);
        timer.setRepeats(false);
        timer.start();

        target.addEventFilter(KeyEvent.ANY, e-> {
            timer.restart();
            System.out.println("CAUGHT THAT KEY FILTER BOI");
        });
        target.addEventFilter(MouseEvent.MOUSE_CLICKED, e-> {
            timer.restart();
            System.out.println("CAUGHT THAT MOUSE CLICK FILTER BOI");
        });
        target.addEventFilter(MouseEvent.MOUSE_MOVED, e-> {
            timer.restart();
            System.out.println("CAUGHT THAT MOUSE MOVE FILTER BOI");
        });
    }

    public void stopListening(){
        //How do I cancel a timer without triggering the event?
        System.out.println("Is running? " + timer.isRunning());
        if(timer.isRunning()){
            System.out.println("stop");
            timer.stop();
        }
        System.out.println("Is running? " + timer.isRunning());
    }
}
