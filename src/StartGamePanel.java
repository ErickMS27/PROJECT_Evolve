package src;

import javax.swing.*;
import java.awt.*;

public class StartGamePanel extends JPanel {
    public StartGamePanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Snake Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 50));
        add(titleLabel, BorderLayout.NORTH);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            JFrame gameFrame = new JFrame("Snake Game");
            gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameFrame.setSize(1300, 750);
            gameFrame.add(new SnakeScreen());
            gameFrame.setResizable(false);
            gameFrame.setVisible(true);
        });

        startButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        add(startButton, BorderLayout.CENTER);

        JLabel scoreboardLabel = new JLabel("Scoreboard", JLabel.CENTER);
        scoreboardLabel.setFont(new Font("Ink Free", Font.PLAIN, 30));
        add(scoreboardLabel, BorderLayout.SOUTH);

        JButton optionsButton = new JButton("Options");
        optionsButton.setFont(new Font("Ink Free", Font.PLAIN, 20));
        optionsButton.addActionListener(e -> {
        });

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(optionsButton);
        add(optionsPanel, BorderLayout.EAST);
    }
}
