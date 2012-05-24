/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import vn.paracel.pos.gui.listener.OrderListTableChangeListener;
import vn.paracel.pos.gui.listener.SelectionListener;
import vn.paracel.pos.main.Session;
import vn.paracel.pos.models.*;
import vn.paracel.pos.models.impl.OrderLineImpl;
import vn.paracel.pos.order.OrderManager;

/**
 *
 * @author Huy Doan
 */
public class OrderList extends javax.swing.JTable {
    public final static int ITEM_COL = 0;
    public final static int PRICE_COL = 1;
    public final static int DISCOUNT_COL = 2;
    public final static int QUANTITY_COL = 3;
    public final static int TOTAL_COL = 4;

    // ProductImpl ID | Index
    private HashMap<Integer, Integer> itemIndex = new HashMap<Integer, Integer>();
    // Index | // ProductImpl
    private HashMap<Integer, Product> itemList = new HashMap<Integer, Product>();
    // Index | Order
    private HashMap<Integer, OrderLine> orderItems = new HashMap<Integer, OrderLine>();

    private SelectionListener selectionListener;
    private OrderListTableChangeListener orderListTableChangeListener;

    public OrderList() {
        selectionListener = new SelectionListener();
        orderListTableChangeListener = new OrderListTableChangeListener();

        getSelectionModel().addListSelectionListener(selectionListener);
        getModel().addTableModelListener(orderListTableChangeListener);
    }

    public void addItem(Product product) {
        if(itemIndex.containsKey(product.getId())) {
            Integer index = itemIndex.get(product.getId());
            Integer value = (Integer)getModel().getValueAt(index, QUANTITY_COL);
            setQuantity(++value, index);

            OrderLine orderLine = orderItems.get(index);
            orderLine.setQuantity(value);
            orderLine.save();
            ModelFactory.removeCache(orderLine.getOrder().getCacheKey());
        } else {
            Vector row = new Vector();
            row.add(product.getName()); // ProductImpl name
            row.add(product.getPrice()); // price
            row.add("0%"); // discount
            row.add(1); // quantity
            row.add(product.getPrice()); // subtotal

            addItem(row);

            Order order = OrderManager.getInstance().findOrderByTable(Session.getTable());
            OrderLine orderLine = (OrderLine)ModelFactory.getModel(OrderLineImpl.class);
            orderLine.setOrder(order);
            orderLine.setProduct(product);
            orderLine.setPriceUnit(product.getPrice());
            orderLine.setDiscount(0.0);
            orderLine.setQuantity(1);
            orderLine.create();
            
            ModelFactory.removeCache(order.getCacheKey());

            Integer index = getModel().getRowCount()-1;
            orderItems.put(index, orderLine);
            itemIndex.put(product.getId(), index);
            itemList.put(index, product);
        }   
        
    }

    public void addItem(OrderLine orderLine) {
        if(orderItems.containsValue(orderLine)) {
            Integer index = itemIndex.get(orderLine.getProduct().getId());
            Integer value = (Integer)getModel().getValueAt(index, QUANTITY_COL);

            setPrice(orderLine.getPriceUnit(), index);
            setDiscount(orderLine.getDiscount(), index);
            setQuantity(orderLine.getQuantity(), index);
        } else {
            Vector row = new Vector();
            row.add(orderLine.getProduct().getName()); // ProductImpl name
            row.add(orderLine.getPriceUnit()); // price
            row.add(orderLine.getDiscount() + "%"); // discount
            row.add(orderLine.getQuantity()); // quantity
            row.add(orderLine.getSubTotalInclude()); // subtotal

            addItem(row);

            Integer index = getModel().getRowCount()-1;
            orderItems.put(index, orderLine);
            itemIndex.put(orderLine.getProduct().getId(), index);
            itemList.put(index, orderLine.getProduct());
        }
        ModelFactory.removeCache(orderLine.getOrder().getCacheKey());
    }

    private void addItem(Vector v) {
        ((DefaultTableModel)getModel()).addRow(v);
    }

    /**
     * Remove selected row
     */
    public void deleteSelectedItem() {
        deleteItem(getSelectedRow());
    }

    /**
     * remove row w/ specified index
     * this method will remove the order line associated w/ the row also
     * @param index Row index 
     */
    public void deleteItem(int index) {
        if(index >= 0) {
            ((DefaultTableModel)getModel()).removeRow(index);
            Product product = itemList.get(index);
            if(product != null) {
                itemIndex.remove(product.getId());
            }
            itemList.remove(index);
            if(orderItems.get(index) != null) {
                orderItems.get(index).delete();
                ModelFactory.removeCache(orderItems.get(index).getOrder().getCacheKey());
            }
            orderItems.remove(index);
            
        }
    }

    /**
     * Clear all row
     */
    public void clear() {
        // avoid ConcurrentModificationException
        getSelectionModel().removeListSelectionListener(selectionListener);
        getModel().removeTableModelListener(orderListTableChangeListener);
        
        while(getRowCount() > 0) {
            ((DefaultTableModel)getModel()).removeRow(0);
        }
        orderItems.clear();
        itemIndex.clear();
        itemList.clear();

        // Restore listeners
        getSelectionModel().addListSelectionListener(selectionListener);
        getModel().addTableModelListener(orderListTableChangeListener);
    }

    /**
     * Get price on specified row
     * @param index
     * @return the price
     */
    public Double getPrice(int index) {
        return (Double)getModel().getValueAt(index, PRICE_COL);
    }

    /**
     * Set price for specified row
     * @param value the price
     * @param index Row index
     */
    public void setPrice(Double value, int index) {
        getModel().setValueAt(value, index, PRICE_COL);
    }

    public Double getDiscount(int index) {
        String v = String.valueOf(getModel().getValueAt(index, DISCOUNT_COL));
        if(v.equals("")) return 0.00;
        if(v.endsWith("%")) v = v.substring(0, v.length()-1);
        return Double.parseDouble(v);
    }

    public void setDiscount(Double value, int index) {
        String v = String.valueOf(value) + "%";
        getModel().setValueAt(v, index, DISCOUNT_COL);
    }

    public Integer getQuantity(int index) {
        return (Integer)getModel().getValueAt(index, QUANTITY_COL);
    }

    public void setQuantity(Integer value, int index) {
        getModel().setValueAt(value, index, QUANTITY_COL);
    }

    public Double getTotal(int index) {
        return (Double)getModel().getValueAt(index, TOTAL_COL);
    }

    public void setTotal(Double value, int index) {
        getModel().setValueAt(value, index, TOTAL_COL);
    }

    public void updateTotal(int index) {
        if(getRowCount() > 0) {
            Double total = 0.0;
            try {
                Double disc = 1.00 - getDiscount(index)/100.00;
                total = getPrice(index)*getQuantity(index)*disc;
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
            setTotal(total, index);
            
            OrderLine orderLine = orderItems.get(index);
            if(orderLine != null) {
                orderLine.setPriceUnit(getPrice(index));
                orderLine.setDiscount(getDiscount(index));
                orderLine.setQuantity(getQuantity(index));
                orderLine.save();
                orderItems.put(index, orderLine);
            }
        }
    }

    public HashMap<String, Double> getAllPrices() {
        Double totalTax = 0.0,
               totalNoTax = 0.0,
               taxTotal = 0.0;

        for(int i = 0; i< getRowCount(); i++) {
            Double base = getTotal(i),
                   totalTaxLine = base,
                   totalNoTaxLine = base,
                   taxLine = 0.0;

            Product p = itemList.get(i);
            try {
                List<Tax> taxes = p.getTaxes();
                if(taxes == null) continue;

                for(Tax tax : taxes) {
                    Double tmp = 0.0;
                    if(tax.getPriceInclude()) {
                        if(tax.getType().equals("percent")) {
                            tmp =  base - (base / (1 + tax.getAmount()));
                        } else if(tax.getType().equals("fixed")) {
                            tmp = tax.getAmount() * getQuantity(i);
                        } else {
                            //"This type of tax is not supported by the point of sale: " + tax.getType()
                        }
                        taxTotal+=tmp;
                        totalNoTax-=tmp;
                    } else {
                        if (tax.getType().equals("percent")) {
                            tmp = tax.getAmount() * base;
                        } else if (tax.getType().equals("fixed")) {
                            tmp = tax.getAmount() * getQuantity(i);
                        } else {
                            //"This type of tax is not supported by the point of sale: " + tax.getType();
                        }
                        taxTotal+= tmp;
                        totalTax+= tmp;
                    }
                }
            } catch (NullPointerException ex) {}
            totalTax+= totalTaxLine;
            totalNoTax+= totalNoTaxLine;
            taxTotal+= taxLine;
        }
        HashMap<String, Double> hm = new HashMap<String, Double>();
        hm.put("priceWithTax", totalTax);
        hm.put("priceWithoutTax", totalNoTax);
        hm.put("tax", taxTotal);
        return hm;
    }

}
