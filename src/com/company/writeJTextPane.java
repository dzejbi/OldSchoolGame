package com.company;

import javax.swing.*;

public class writeJTextPane extends JFrame {

    private JTextPane textPane;

    public writeJTextPane(JTextPane textPane){
        this.textPane = textPane;
    }

    public void setText(String textToSet){
        textPane.setText(textToSet);
    }

    public void clear() {
        textPane.setText("");
    }
} //TODO

