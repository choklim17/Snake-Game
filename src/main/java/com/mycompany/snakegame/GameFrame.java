package com.mycompany.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements ActionListener {
    
    private JLabel gameTitle;
    private JLabel gameLevel;
    
    private JButton startGameButton;
    private JButton exitGameButton;
    
    private JComboBox selectedLevelBox;
    
    private static int selectedLevel;
    
    public GameFrame() {
        
        this.setTitle("Snake Game");
        this.setSize(280, 280);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(51, 51, 51));
        
        gameTitle = new JLabel("Snake");
        gameTitle.setFont(new Font("Ink Free", Font.BOLD, 70));
        gameTitle.setPreferredSize(new Dimension(200, 100));
        gameTitle.setHorizontalAlignment(JLabel.CENTER);
        gameTitle.setVerticalAlignment(JLabel.BOTTOM);
        gameTitle.setForeground(new Color(245, 245, 245));
        gameTitle.setBackground(new Color(51, 51, 51));
        gameTitle.setOpaque(true);
        
        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(150, 30));
        startGameButton.setFocusable(false);
        startGameButton.addActionListener(this);
        
        String[] levelChoices = {"Select Level", "1", "2", "3", "4", "5"};
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        
        selectedLevelBox = new JComboBox(levelChoices);
        selectedLevelBox.setFont(new Font("Verdana", Font.PLAIN, 15));
        selectedLevelBox.setRenderer(listRenderer);
        selectedLevelBox.setPreferredSize(new Dimension(150, 30));
        selectedLevelBox.setSelectedItem("Select Level");
        selectedLevelBox.setFocusable(false);
        
        exitGameButton = new JButton("Exit Game");
        exitGameButton.setPreferredSize(new Dimension(150, 30));
        exitGameButton.setFocusable(false);
        exitGameButton.addActionListener(this);
        
        this.add(gameTitle);
        this.add(startGameButton);
        this.add(selectedLevelBox);
        this.add(exitGameButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String levelChoice = (String) selectedLevelBox.getSelectedItem();
        if (e.getSource() == startGameButton) {
            try {
                selectedLevel = Integer.parseInt(levelChoice);
                
                LaunchGame launchGame = new LaunchGame();
                this.dispose();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please Select a Valid Level", "Select Level", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if (e.getSource() == exitGameButton) {
            System.exit(0);
        }
    }
    
    public static int getSelectedLevel() {
        return selectedLevel;
    }
    
}
