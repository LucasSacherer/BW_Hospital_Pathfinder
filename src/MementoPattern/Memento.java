package MementoPattern;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Memento{

    Timer timer;
    String test;
    Window testWin;

    public Memento(String state){
        test = state;
        timer = new Timer(5, returnToMain);
    }

    ActionListener returnToMain = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Printing: " + test);
        }
    };

    public void run(int delay){
        if(timer.isRunning()){
            timer.stop();
        }
        timer = new Timer(delay, returnToMain);
        timer.setRepeats(false);
        timer.start();
    }

    public String getState(){
        return test;
    }
}
