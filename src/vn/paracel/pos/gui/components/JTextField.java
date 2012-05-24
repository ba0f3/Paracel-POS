/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import vn.paracel.pos.gui.keyboard.Keyboard;
import vn.paracel.pos.gui.keyboard.KeyboardAdapter;

/**
 *
 * @author rgv151
 */
public class JTextField extends javax.swing.JTextField {

    public JTextField() {
        super();        
        
        final JTextField tf = this;
        //tf.addMouseListener(new KeyboardAdapter(this));
        tf.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                tf.setBackground(Color.yellow);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                tf.setBackground(Color.white);
            }
        });
    }

}
