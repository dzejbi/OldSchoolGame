package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jan on 2015-03-20.
 */
public class TextListener implements ActionListener {

    public  String string_temp;
    public static boolean pressed = false;
    private JTextField textField;

    @Override
    public void actionPerformed(ActionEvent e) {

        string_temp = textField.getText();
        textField.setText("");
        pressed =true;
    }

    public TextListener(JTextField textField, String string_temp) {
        this.textField = textField;
        this.string_temp = string_temp;
    }
}
