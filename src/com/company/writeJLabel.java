package com.company;

import javax.swing.*;

public class writeJLabel extends JFrame {

    private JLabel label;

    public writeJLabel(JLabel label){
        this.label = label;
    }

    public void setText(String textToSet){
        label.setText(textToSet);
    }

    public void clear(){
        label.setText("");
    };


} //TODO

