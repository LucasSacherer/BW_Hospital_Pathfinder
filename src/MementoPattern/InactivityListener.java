package MementoPattern;

import Database.SettingsManager;
import boundary.GodController;
import boundary.SceneSwitcher;
import boundary.sceneControllers.MainSceneController;
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
    private SettingsManager settingsManager;

    Action returnToMain = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            try {
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
        settingsManager = SettingsManager.getInstance();
        setInterval(Double.parseDouble(settingsManager.getSetting("Memento Delay")));
        setAction(returnToMain);
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
        setInterval(Double.parseDouble(settingsManager.getSetting("Memento Delay")));


        target.setFocusTraversable(true);

        if(timer.isRunning()){
            timer.stop();
            timer = new Timer(interval, this);;
        }
        timer.setInitialDelay(interval);
        timer.setRepeats(false);
        timer.start();

        target.addEventFilter(KeyEvent.ANY, e-> {
            timer.restart();
        });
        target.addEventFilter(MouseEvent.MOUSE_CLICKED, e-> {
            timer.restart();
        });
        target.addEventFilter(MouseEvent.MOUSE_MOVED, e-> {
            timer.restart();
        });
    }

    public void stopListening(){
        if(timer.isRunning()){
            timer.stop();
        }
    }
}
