package com.company;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Main extends JFrame implements  Runnable {

    private static JTextPane textPane;
    private static JLabel label;
    private static JTextField textField;


    public static void main(String[] args) throws IOException, InterruptedException {

        Main main = new Main();

        char[][] Board = new char[10][10];
        Board Level_1 = new Board(Board);
        Level_1.buildBattleBoard();
        Monster.myBoard = Level_1;
        boolean create = true;
        Monster Players[] = new Monster[4];
        String string_temp = "";
        String  stats = "Name: ";
        TextListener tListen = new TextListener(textField,string_temp);
        textField.addActionListener(tListen);
        int action =1;

        label.setText("<html><center>Welcome into my castle dr. Hellsing,</center> create yo playaa (all stats should add up to 170. You can create up to 4 players)</html>");

        textPane.setText(stats);
        while (create) {

            switch (action) {
                case 1:
                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        Stats.name = tListen.string_temp;
                        stats += tListen.string_temp;
                        textPane.setText(stats);
                        action++;
                    } else {
                        main.run();
                    }
                    break;
                case 2:
                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        Stats.health = Integer.parseInt(tListen.string_temp);
                        stats += "\nHealth: " + tListen.string_temp;
                        textPane.setText(stats);
                        action++;
                    } else {
                        textPane.setText(stats + "\nHealth: ");
                        main.run();
                    }
                    break;
                case 3:
                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        Stats.skill_at_attack = Integer.parseInt(tListen.string_temp);
                        stats += "\nFighting skill: " + tListen.string_temp;
                        textPane.setText(stats);
                        action++;
                    } else {
                        textPane.setText(stats + "\nFighting skill: ");
                        main.run();
                    }
                    break;
                case 4:
                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        Stats.skill_at_magic = Integer.parseInt(tListen.string_temp);
                        stats += "\nMagic skill: " + tListen.string_temp;
                        textPane.setText(stats);
                        action++;
                    } else {
                        textPane.setText(stats + "\nMagic skill: ");
                        main.run();
                    }
                    break;
                case 5:
                    System.out.println(Stats.health +" "+ Stats.skill_at_attack +" "+ Stats.skill_at_magic);
                    if (Stats.health + Stats.skill_at_attack + Stats.skill_at_magic > 170) {
                        textPane.setText("Wrong amount of skillpoints, create again ");
                        main.run();
                        main.run();
                        stats = "Name :";
                        textPane.setText(stats);
                        action = 1;
                    }
                    else{
                        action++;
                    }
                    break;
                case 6:
                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        Stats.specialization = Integer.parseInt(tListen.string_temp);
                        if(Stats.specialization < 50){
                            stats += "\nSpecialization: Blood Mage ! ";
                            textPane.setText(stats);
                        }
                        else{
                            stats += "\nSpecialization: Warrior ! ";
                            textPane.setText(stats);
                        }
                        action++;
                    } else {
                        textPane.setText(stats + "\nLast question: fighting style of your character (30-70, higher number - melee weapon specialist, lesser number - blood mage):  ");
                        main.run();
                    }
                    break;
                case 7:

                    Players[Stats.players - 1] = new Monster(Stats.health, 2, Stats.name, Stats.skill_at_attack, Stats.skill_at_magic, Stats.specialization, true);
                    stats += "\n\n GOOD LUCK  "+ Stats.name.toUpperCase();
                    textPane.setText(stats);
                    action++;
                    main.run();
                    break;

                case 8:

                    if (TextListener.pressed) {
                        TextListener.pressed = false;
                        if(tListen.string_temp.equals("t")) {
                            stats = "Name: ";
                            textPane.setText(stats);
                            action = 1;
                        }
                        else{
                            textPane.setText("START !!!");
                            label.setText("<html><center>Let's get ready to rumble !</html>");
                            action++;
                            main.run();
                        }
                    } else {
                        textPane.setText(stats + "\nCreat another character? (t - yes, anything alse-no) ");
                        main.run();
                    }
                    break;
                case 9:
                    create=false;
                    break;
            }
        }

        Monster Monsters[] = new Monster[4 + Stats.players];
        Monsters[0] = new Monster(15, 125, 1, "Frank");
        Monsters[1] = new Monster(10, 150, 1, "Paul");
        Monsters[2] = new Monster(20, 100, 1, "George");
        Monsters[3] = new Monster(5, 200, 2, "James");

        for (int n = 1; n < Stats.players + 1; n++) {
            Monsters[3 + n] = Players[n - 1];
        }

        Level_1.setMyMonsters(Monsters);
        textPane.setText(Level_1.redrawBoard_s());


        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 4 + Stats.players; j++) {
                Monsters[j].search();
            }
            textPane.setText(Level_1.redrawBoard_s());
        }
        textPane.setText(Level_1.redrawBoard_s());
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
        textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        MutableAttributeSet standard = new SimpleAttributeSet();

        StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLeftIndent(standard, 50);
        StyleConstants.setRightIndent(standard, 50);
        StyleConstants.setFontSize(standard, 30);
        Color gray = new Color(224, 226, 235);
        doc.setParagraphAttributes(0, 0, standard, true);
        textPane.setBackground(gray);
        textPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        thePanel.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

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
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
        }
    }
}