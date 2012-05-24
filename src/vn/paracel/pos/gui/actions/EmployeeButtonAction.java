/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import vn.paracel.pos.gui.LoginScreen;
import vn.paracel.pos.models.Employee;
import vn.paracel.pos.util.ImageUtil;

/**
 *
 * @author rgv151
 */
public class EmployeeButtonAction extends AbstractAction {
    private JTextField tf;
    private Employee emp;

    public EmployeeButtonAction(Employee emp, JTextField tf) {
        this.tf = tf;
        this.emp = emp;
        
        putValue(Action.SMALL_ICON, ImageUtil.parseImage(emp.getPhoto()));
        putValue(Action.NAME, emp.getName());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        tf.setText(emp.getName());
        LoginScreen.setSelectedEmployee(emp);
    }    
}
