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
import java.awt.event.ActionListener;
import java.text.DecimalFormatSymbols;
import vn.paracel.pos.gui.OrderScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.gui.widget.OrderList;

/**
 *
 * @author Huy Doan
 */
public class NumpadAction implements ActionListener {
    private String value;
    protected Boolean isDotPressed = false;
    private static String typedValue = "";

    public NumpadAction(String str) {
        this.value = str;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {                
        OrderScreen orderScreen = RootView.getInstance().getOrderScreen();
        OrderList orderList = orderScreen.getOrderList();
        int row = orderList.getSelectedRow();
        int col = orderScreen.getSelectedColumn();
       
        if(orderScreen.isClearField()) {
            orderScreen.setClearField(false);
            typedValue = "";
        }
        
        if(value.equals(".")) {
            char dot = DecimalFormatSymbols.getInstance().getDecimalSeparator();
            typedValue+= (typedValue.equals("")?"0.":".");
            
        } else if(value.equals("")) {
            typedValue = "";
            try {
                orderList.setValueAt("", row, col);
            } catch (Exception ex)  {}
        } else {
            typedValue+= value;
            if(col == OrderList.QUANTITY_COL) {
                orderList.setQuantity(Integer.parseInt(typedValue), row);
            } else if(col == OrderList.PRICE_COL) {
                orderList.setPrice(Double.parseDouble(typedValue), row);
            } else if(col == OrderList.DISCOUNT_COL) {
                orderList.setDiscount(Double.parseDouble(typedValue), row);
            }            
        }
    }

}
