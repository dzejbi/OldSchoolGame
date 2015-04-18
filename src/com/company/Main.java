package com.company;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Main extends JFrame implements  Runnable {

    private static JTextPane writeTextPane;
    private static JLabel label;
    private static JTextField textField;


    public static void main(String[] args) throws IOException, InterruptedException {

        Main main = new Main();

        readJTextField readConsole = new readJTextField(textField);
        writeJLabel writeHeader = new writeJLabel(label);
        writeJTextPane writeTextPane = new writeJTextPane(Main.writeTextPane);
        createCharacter characterCreator = new createCharacter(writeTextPane,readConsole);

        char[][] Board = new char[10][10];
        Board Level_1 = new Board(Board);
        Level_1.buildBattleBoard();
        Monster.myBoard = Level_1;
        Monster Players[] = new Monster[4];
        textField.addActionListener(readConsole);

        writeHeader.setText("<html><center>Welcome into my castle dr. Hellsing,</center> create yo playaa (all stats should add up to 170. You can create up to 4 players)</html>");
                while(characterCreator.wantToCreateAnother()) {
                    Players[playerStats.players] = characterCreator.createNewCharacter();
                    playerStats.players++;
                }

        writeTextPane.setText("START !!!");
        writeHeader.setText("<html><center>Let's get ready to rumble !</html>");
        main.run(1000);


        Monster Monsters[] = new Monster[4 + playerStats.players];
        Monsters[0] = new Monster(15, 125, 1, "Frank");
        Monsters[1] = new Monster(10, 150, 1, "Paul");
        Monsters[2] = new Monster(20, 100, 1, "George");
        Monsters[3] = new Monster(5, 200, 2, "James");

        for (int n = 1; n < playerStats.players + 1; n++) {
            Monsters[3 + n] = Players[n - 1];
        }

        Level_1.setMyMonsters(Monsters);
        Main.writeTextPane.setText(Level_1.redrawBoard_s());


        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 4 + playerStats.players; j++) {
                Monsters[j].search();
                Main.writeTextPane.setText(Level_1.redrawBoard_s());
                main.run();
            }

        }
        Main.writeTextPane.setText(Level_1.redrawBoard_s());
    }

    public Main() {

        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Old School Game developed by JB & MB");
        JPanel thePanel = new JPanel(new BorderLayout());


        //Label displays information about game
        label = new JLabel();
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200,200));
        thePanel.add(label, BorderLayout.PAGE_START);


        // Text Pane shows map and player attributes
        writeTextPane = new JTextPane();
        StyledDocument doc = writeTextPane.getStyledDocument();
        MutableAttributeSet standard = new SimpleAttributeSet();

        StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLeftIndent(standard, 50);
        StyleConstants.setRightIndent(standard, 50);
        StyleConstants.setFontSize(standard, 30);
        Color gray = new Color(224, 226, 235);
        doc.setParagraphAttributes(0, 0, standard, true);
        writeTextPane.setBackground(gray);
        writeTextPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        thePanel.add(writeTextPane, BorderLayout.CENTER);
        writeTextPane.setEditable(false);

        // Text Field gets information from player
        textField = new JTextField();
        textField.setFont(new Font("Serif", Font.PLAIN, 20));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        thePanel.add(textField, BorderLayout.PAGE_END);


        this.add(thePanel);
        this.setVisible(true);
        textField.requestFocus(true);

    }
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        }
        catch(InterruptedException e){
        }
    }
    public void run(int waitMs) {
        try {
            Thread.sleep(waitMs);
        } catch (InterruptedException e) {
        }
    }

}