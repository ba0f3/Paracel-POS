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

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.gui.widget.OrderList;

/**
 *
 * @author Huy Doan
 */
public class SelectionListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        RootView.getInstance().getOrderScreen().toggleButton(OrderList.QUANTITY_COL);
    }
}
