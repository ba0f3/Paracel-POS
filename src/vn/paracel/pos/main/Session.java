/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.main;

import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import vn.paracel.pos.models.Employee;
import vn.paracel.pos.models.Table;

/**
 *
 * @author rgv151
 */
public class Session {
    private static Boolean loggedIn = false;
    private static Employee employee;
    private static Table table;

    public static Boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(Boolean loggedIn) {
        Session.loggedIn = loggedIn;
    }

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee emp) {
        Session.employee = emp;
    }

    public static Table getTable() {
        return table;
    }

    public static void setTable(Table table) {
        Session.table = table;
    }
}
