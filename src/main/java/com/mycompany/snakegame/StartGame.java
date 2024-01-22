package com.mycompany.snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class StartGame extends JPanel implements ActionListener {

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / UNIT_SIZE;
//    private static final int DELAY = 75;
    
    private final int[] bodyPositionX = new int[GAME_UNITS];
    private final int[] bodyPositionY = new int[GAME_UNITS];
    
    private int delay = GameFrame.getSelectedLevel();
    private int bodyParts = 6;
    private int foodEaten;
    private int foodXPosition;
    private int foodYPosition;
    private int highscore;
    
    private char direction = 'R';
    private boolean isRunning = false;
    private Timer timer;
    private Random random;
    
    
    public StartGame() {

        random = new Random();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(new Color(51, 51, 51));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        startGame();
        updateScore(foodEaten);
    }
    
    private void startGame() {
        displayNewFood();
        isRunning = true;
        
        int speedLevel = 0;
        switch (delay) {
            case 1 -> speedLevel = 95;
            case 2 -> speedLevel = 90;
            case 3 -> speedLevel = 85;
            case 4 -> speedLevel = 75;
            case 5 -> speedLevel = 70;
        }
        
        highscore = LaunchGame.readHighScore();
        updateHighScore(highscore);
        
        timer = new Timer(speedLevel, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    private void draw(Graphics g) {
        
        if (isRunning) {
            /*
            for (int i = 0; i < (PANEL_HEIGHT / UNIT_SIZE); i++) {
                g.setColor(Color.WHITE);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, PANEL_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, PANEL_WIDTH, i * UNIT_SIZE);
            }
            */
            
            g.setColor(Color.red);
            g.fillOval(foodXPosition, foodYPosition, UNIT_SIZE - 2 , UNIT_SIZE - 2);
        
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(bodyPositionX[i], bodyPositionY[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(Color.yellow);
                    g.fillRect(bodyPositionX[i], bodyPositionY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            
//            g.setColor(Color.red);
//            g.setFont(new Font("Ink Free", Font.BOLD, 40));
//        
//            FontMetrics metrics = getFontMetrics(g.getFont());
//            g.drawString("Score: " + foodEaten, (PANEL_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
        }
        else {
            gameOver(g);
            
        }
    }
    
    private void displayNewFood() {
        foodXPosition = random.nextInt((int) (PANEL_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodYPosition = random.nextInt((int) (PANEL_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    
    private void move() {
        
        for (int i = bodyParts; i > 0; i--) {
            bodyPositionX[i] = bodyPositionX[i - 1];
            bodyPositionY[i] = bodyPositionY[i - 1];
        }
        
        switch (direction) {
            case 'U':
                bodyPositionY[0] = bodyPositionY[0] - UNIT_SIZE;
                break;
            case 'D':
                bodyPositionY[0] = bodyPositionY[0] + UNIT_SIZE;
                break;
            case 'L':
                bodyPositionX[0] = bodyPositionX[0] - UNIT_SIZE;
                break;
            case 'R':
                bodyPositionX[0] = bodyPositionX[0] + UNIT_SIZE;
                break;
        }
    }
    
    private void checkFoodEaten() {
        
        if ((bodyPositionX[0] == foodXPosition) && (bodyPositionY[0] == foodYPosition)) {
            bodyParts++;
            
            switch (delay) {
                case 1:
                    foodEaten += 1;
                    break;
                case 2:
                    foodEaten += 2;
                    break;
                case 3:
                    foodEaten += 3;
                    break;
                case 4:
                    foodEaten += 4;
                    break;
                case 5:
                    foodEaten += 5;
                    break;
            }
            
            displayNewFood();
        }
    }
    
    private void checkCollisions() {
        //Checks if Head collides with Body
        for (int i = bodyParts; i > 0; i--) {
            if ((bodyPositionX[0] == bodyPositionX[i]) && (bodyPositionY[0] == bodyPositionY[i])) {
                isRunning = false;
            }
        }
        
        //Checks if Head touches Left Border
        if (bodyPositionX[0] < 0) {
            isRunning = false;
        }
        
        //Checks if Head touches Right Border
        if (bodyPositionX[0] > PANEL_WIDTH) {
            isRunning = false;
        }
        
        //Checks if Head touches Top Border
        if (bodyPositionY[0] < 0) {
            isRunning = false;
        }
        
        //Checks if Head touches Bottom Border
        if (bodyPositionY[0] > PANEL_HEIGHT) {
            isRunning = false;
        }
        
        if (!isRunning) {
            timer.stop();
        }
    }
    
    private void gameOver(Graphics g) {
        //Score
//        g.setColor(Color.red);
//        g.setFont(new Font("Ink Free", Font.BOLD, 40));
//        
//        FontMetrics ScoreMetrics = getFontMetrics(g.getFont());
//        g.drawString("Score: " + foodEaten, (PANEL_WIDTH - ScoreMetrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
        
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        
        FontMetrics gameoverMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (PANEL_WIDTH - gameoverMetrics.stringWidth("Game Over!")) / 2, PANEL_HEIGHT / 2);
        
        if (foodEaten > highscore) {
            LaunchGame.updateHighScore(foodEaten);
            
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
        
            FontMetrics highScoreMetrics = getFontMetrics(g.getFont());
            g.drawString("New High Score: " + foodEaten, (PANEL_WIDTH - highScoreMetrics.stringWidth("New High Score: " + foodEaten)) / 2, g.getFont().getSize());
        }
        else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
        
            FontMetrics ScoreMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodEaten, (PANEL_WIDTH - ScoreMetrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());           
        }
        
        Timer closeWindow = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame.closeGameFrame();
                GameFrame gameFrame = new GameFrame();
            }
        });
        
        closeWindow.setRepeats(false);
        closeWindow.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (isRunning) {
            move();
            checkFoodEaten();
            checkCollisions();
        }
        
        this.repaint();
        updateScore(foodEaten);
    }
    
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
    
    private void updateScore(int score) {
        LaunchGame.setScore("Score: " + score);
    }
    
    private void updateHighScore(int highScore) {
        LaunchGame.setHighScore("High Score: " + highScore);
    }
}
