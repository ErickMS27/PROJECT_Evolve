package src;

import java.awt.Component;
import javax.swing.JFrame;

public class StartGame extends JFrame {
    public static void main(String[] args) {
        new StartGame();
    }

    StartGame() {
        this.add(new SnakeScreen());
        this.setTitle("SNAKE Game - Joga da Cobrinha");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo((Component)null);
    }
}
