/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.listener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import vn.paracel.pos.gui.OrderScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.gui.widget.OrderList;

/**
 *
 * @author Huy Doan
 */
public class OrderListTableChangeListener implements TableModelListener {

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        OrderScreen orderScreen = RootView.getInstance().getOrderScreen();
        if(column != OrderList.TOTAL_COL) {            
            orderScreen.getOrderList().updateTotal(row);
        }
        orderScreen.updateTotal(orderScreen.getOrderList().getAllPrices());
    }

}
