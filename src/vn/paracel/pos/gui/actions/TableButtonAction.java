/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import vn.paracel.pos.gui.OrderScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.main.Session;
import vn.paracel.pos.models.Table;

/**
 *
 * @author Huy Doan
 */
public class TableButtonAction extends AbstractAction {
    private Table table;

    public TableButtonAction(Table table) {
        this.table = table;
        
        if(table.getState().equals("open")) {
            putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/sad_64.png")));
        } else {
            putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/smile_64.png")));
        }
        putValue(Action.NAME, table.getName());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Session.setTable(table);
        table.setState("close");
        table.save();
        RootView.getInstance().showView(OrderScreen.VIEW_NAME);
    }    
}
