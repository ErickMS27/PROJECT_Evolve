package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class SnakeScreen extends JPanel implements ActionListener {

    private static final int LARGURA_TELA = 1300;
    private static final int ALTURA_TELA = 750;
    private static final int TAMANHO_BLOCO = 50;
    private static final int UNIDADES = LARGURA_TELA * ALTURA_TELA / (TAMANHO_BLOCO * TAMANHO_BLOCO);
    private static final int INTERVALO = 200;
    private static final String NOME_FONTE = "Ink Free";
    private final int[] axisX = new int[UNIDADES];
    private final int[] axisY = new int[UNIDADES];
    private int snakeBody = 6;
    private int blocksEated;
    private int[] snakeX, snakeY;
    private int[] blocksX, blocksY;
    private int blockX, blockY;
    private char direct = 'D';
    private boolean itsLoading = false;
    private JButton resetButton;
    Timer timer;
    Random random = new Random();

    public SnakeScreen() {
        this.setPreferredSize(new Dimension(1300, 750));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyReaderAdapter());
        createResetButton();
        startGame();
    }

    private void createResetButton() {
        resetButton = new JButton("Restart Game");
        resetButton.setFocusable(false);
        resetButton.addActionListener(new ResetButtonListener());
        resetButton.setVisible(false);
        add(resetButton);
    }

    public void startGame() {
        this.createBlock();
        this.createInitialPositions();
        this.itsLoading = true;
        this.timer = new Timer(INTERVALO, this);
        this.timer.start();

        if (timer != null){
            timer.start();
        }
    }

    private void createInitialPositions() {

        snakeX = new int[snakeBody];
        snakeY = new int[snakeBody];
        blocksX = new int[blocksEated];
        blocksY = new int[blocksEated];

        for (int i = 0; i < snakeBody; i++) {
            snakeX[i] = 50 - i * 50;
            snakeY[i] = 50;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintScreen(g);
    }

    public void paintScreen(Graphics g) {
        if (this.itsLoading) {
            g.setColor(new Color(230, 50, 10));
            g.fillOval(this.blockX, this.blockY, 50, 50);

            for(int i = 0; i < this.snakeBody; ++i) {
                if (i == 0) {
                    g.setColor(new Color(0, 200, 108));
                    g.fillRect(this.axisX[0], this.axisY[0], 50, 50);
                } else {
                    g.setColor(new Color(3, 129, 79));
                    g.fillRect(this.axisX[i], this.axisY[i], 50, 50);
                }

            }

            g.setColor(new Color(230, 50, 10));
            g.setFont(new Font("Ink Free", 1, 40));
            FontMetrics metrics = this.getFontMetrics(g.getFont());
            g.drawString("Pontos: " + this.blocksEated, (1300 - metrics.stringWidth("Pontos: " + this.blocksEated)) / 2, g.getFont().getSize());
            resetButton.setVisible(false);
        } else {
            this.gameOver(g);
            resetButton.setVisible(true);
        }

        resetButton.setBounds(LARGURA_TELA / 2 - 100, ALTURA_TELA - 100, 200, 50);

    }

    private void createBlock() {
        int maxX = (LARGURA_TELA / TAMANHO_BLOCO);
        int maxY = (ALTURA_TELA / TAMANHO_BLOCO);

        do {
            this.blockX = (random.nextInt(maxX)) * TAMANHO_BLOCO;
            this.blockY = (random.nextInt(maxY)) * TAMANHO_BLOCO;
        } while (isBlockInsideSnake());
    }

    private boolean isBlockInsideSnake() {
        for (int i = 0; i < snakeBody; ++i) {
            if (axisX[i] == blockX && axisY[i] == blockY) {
                return true;
            }
        }
        return false;
    }

    public void gameOver(Graphics g) {
        g.setColor(new Color(230, 50, 10));
        g.setFont(new Font("Ink Free", 1, 40));
        FontMetrics fontPoints = this.getFontMetrics(g.getFont());
        g.drawString("Pontos: " + this.blocksEated, (1300 - fontPoints.stringWidth("Pontos: " + this.blocksEated)) / 2, g.getFont().getSize());
        g.setColor(new Color(230, 50, 10));
        g.setFont(new Font("Ink Free", 1, 75));
        FontMetrics fonteFinal = this.getFontMetrics(g.getFont());
        g.drawString("\ud83d\ude1d Game Over", (1300 - fonteFinal.stringWidth("Game Over")) / 2, 375);
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (this.itsLoading) {
            this.crawl();
            this.reachBlock();
            this.validateLimits();
        }

        this.repaint();
    }

    private void crawl() {
        for(int i = this.snakeBody; i > 0; i--) {
            this.axisX[i] = this.axisX[i - 1];
            this.axisY[i] = this.axisY[i - 1];
        }

        switch (this.direct) {
            case 'B':
                this.axisY[0] += 50;
                break;
            case 'C':
                this.axisY[0] -= 50;
                break;
            case 'D':
                this.axisX[0] += 50;
                break;
            case 'E':
                this.axisX[0] -= 50;
        }

    }

    private void reachBlock() {
        if (this.axisX[0] == this.blockX && this.axisY[0] == this.blockY) {
            ++this.snakeBody;
            ++this.blocksEated;
            this.createBlock();
        }

    }

    private void validateLimits() {
        for(int i = this.snakeBody; i > 0; --i) {
            if (this.axisX[0] == this.axisX[i] && this.axisY[0] == this.axisY[i]) {
                this.itsLoading = false;
                break;
            }
        }

        if (this.axisX[0] < 0 || this.axisX[0] > 1300) {
            this.itsLoading = false;
        }

        if (this.axisY[0] < 0 || this.axisY[0] > 750) {
            this.itsLoading = false;
        }

        if (!this.itsLoading) {
            this.timer.stop();
        }

    }

    public class KeyReaderAdapter extends KeyAdapter {
        public KeyReaderAdapter() {
        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37:
                    if (SnakeScreen.this.direct != 'D') {
                        SnakeScreen.this.direct = 'E';
                    }
                    break;
                case 38:
                    if (SnakeScreen.this.direct != 'B') {
                        SnakeScreen.this.direct = 'C';
                    }
                    break;
                case 39:
                    if (SnakeScreen.this.direct != 'E') {
                        SnakeScreen.this.direct = 'D';
                    }
                    break;
                case 40:
                    if (SnakeScreen.this.direct != 'C') {
                        SnakeScreen.this.direct = 'B';
                    }
            }

        }
    }

    private void resetGame() {
        for(int i = 0; i < axisX.length; i++) {
            axisX[i] = 0;
            axisY[i] = 0;
        }
        snakeBody = 6;
        blocksEated = 0;
        itsLoading = true;
        direct = 'D';
        timer.restart();
        createBlock();
        resetButton.setVisible(false);
        repaint();
    }
}