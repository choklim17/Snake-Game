package com.mycompany.snakegame;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LaunchGame {
    
    private static JFrame gameFrame;
    private JPanel outerPanel;
    
    public static JLabel score;
    private static JLabel highscore;
    private JLabel level;
    
    private Font labelFont;
    private Color labelColor;
    
    private int updateScore;
    
    public LaunchGame() {
        
        gameFrame = new JFrame("Snake");
        gameFrame.setSize(700, 750);
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.getContentPane().setBackground(new Color(75, 75, 75));
        
        labelFont = new Font("Verdana", Font.BOLD, 15);
        labelColor = new Color(245, 245, 245);
        
        score = new JLabel("Score: ");
        score.setBounds(40, 30, 260, 30);
        score.setFont(labelFont);
        score.setForeground(labelColor);
 
        highscore = new JLabel("High Score: ");
        highscore.setBounds(305, 30, 260, 30);
        highscore.setFont(labelFont);
        highscore.setForeground(labelColor);

        level = new JLabel("Level: " + GameFrame.getSelectedLevel());
        level.setBounds(570, 30, 80, 30);
        level.setFont(labelFont);
        level.setForeground(labelColor);

        outerPanel = new JPanel();
        outerPanel.setSize(610, 610);
        outerPanel.setBounds(40, 70, 610, 610);
        outerPanel.setBackground(new Color(30, 30, 30));
        outerPanel.add(new StartGame());
        
        gameFrame.add(outerPanel);
        gameFrame.add(score);
        gameFrame.add(highscore);
        gameFrame.add(level);
        gameFrame.setVisible(true);

//        gameFrame = new JFrame("Snake");
//        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gameFrame.setResizable(false);
//        
//        gameFrame.add(new StartGame());
//        gameFrame.pack();
//        gameFrame.setVisible(true);
    }
    
    public static int readHighScore() {
        int highScore = 0;
        
        try {
            File file = new File("HighScore.txt");
            
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(LaunchGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return highScore;
    }
    
    public static void updateHighScore(int newHighScore) {
        
        try {
            File file = new File("HighScore.txt");
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(String.valueOf(newHighScore));
            }
        }
        catch (IOException ex) {
            Logger.getLogger(LaunchGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setScore(String text) {
        score.setText(text);
    }
    
    public static void setHighScore(String text) {
        highscore.setText(text);
    }
    
    public static void closeGameFrame() {
        gameFrame.dispose();
    }
 }
