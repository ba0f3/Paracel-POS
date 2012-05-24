/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui.keyboard;

import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;
import vn.paracel.pos.gui.components.JPasswordField;
import vn.paracel.pos.gui.components.JTextField;


/**
 *
 * @author rgv151
 */
public final class KeyboardAdapter extends MouseAdapter
{
    private Object textfield;

    public KeyboardAdapter() {
    }    

    public KeyboardAdapter(Object tf) {
        setTextField(tf);
    }
    
    public void setTextField(Object tf) {
        textfield = tf;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        Keyboard keyboard = Keyboard.getInstance();
        if(textfield instanceof JTextField) {
            keyboard.setActiveTextField((JTextField)textfield);
        } else {
            keyboard.setActiveTextField((JPasswordField)textfield);
        }
        keyboard.setVisible(true);
    }
}
