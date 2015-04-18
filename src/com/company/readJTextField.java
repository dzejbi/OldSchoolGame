package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class readJTextField extends JFrame implements ActionListener {

        private  String text = "";
        private  boolean pressed = false;
        private JTextField textField;

        @Override
        public void actionPerformed(ActionEvent e) {
            text = textField.getText();
            textField.setText("");
            pressed =true;
        }

        public boolean isActionPerformed(){
            if(true == pressed){
                pressed = false;
                return true;
            }
            return pressed;
        }

        public String getText(){
            return text;
        }

        public int tryToGetInt() throws NumberFormatException {
                return Integer.parseInt(this.getText());
        }

        public readJTextField(JTextField textField) {
            this.textField = textField;
        }
    }

