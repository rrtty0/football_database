package BaseClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main {

    public static MainFrame mainFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new MainFrame();
                mainFrame.setTitle("Футбольная база данных - [Пользователь]");
                mainFrame.setSize(MainFrame.DEFAULT_WIDTH, MainFrame.DEFAULT_HEIGHT);
                mainFrame.setVisible(true);
                mainFrame.setIconImage(new ImageIcon("src/Images/ball.png").getImage());
                mainFrame.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        MyTableIO.closeDBConnection();
                        System.exit(0);
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {
                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {
                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                    }
                });
            }
        });
    }
}
