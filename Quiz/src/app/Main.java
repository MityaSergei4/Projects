package app;

import javax.swing.*;

public class Main extends JFrame{
    public static void main(String[] args){
        new Main().setWindow();
    }

    private void setWindow(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(new Controller());
        setUndecorated(true);
        setVisible(true);
        repaint();
    }
}