/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.order;

import java.util.HashMap;
import vn.paracel.pos.gui.MainScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.main.Application;
import vn.paracel.pos.main.Constanst;
import vn.paracel.pos.models.ModelFactory;
import vn.paracel.pos.models.Order;
import vn.paracel.pos.models.Table;
import vn.paracel.pos.models.impl.OrderImpl;

/**
 *
 * @author Huy Doan
 */
public class OrderManager {
    /**
     * Order mapping w/ table
     * HashMap<TableId, Order>
     */
    private HashMap<Integer, Order> map;

    /**
     * Constructor
     */
    public OrderManager() {
        map = new HashMap<Integer, Order>();
    }

    public static class OrderManagerHolder {
        public static OrderManager instance = new OrderManager();
    }

    /**
     * Returns OrderManager singleton
     * @return OrderManager
     */
    public static OrderManager getInstance() {
        return OrderManagerHolder.instance;
    }

    /**
     * Search Order associated w/ specified table
     * @param id Table Id
     * @return Order
     */
    public Order findOrderByTable(Integer id) {
        Order order = OrderImpl.searchByTable(id);
        // TODO: update HashMap cache
        /*if(!map.containsKey(id)) {
            order = OrderImpl.searchByTable(id);
        } else {
            order = map.get(id);
        }*/
        return order;
    }

    /**
     * Search Order associated w/ specified table
     * @param table Table
     * @return Order
     */
    public Order findOrderByTable(Table table) {
        return findOrderByTable(table.getId());
    }

    /**
     * Create order
     * @param table Table
     * @return Order
     */
    public Order createOrder(Table table) {
        Order order = (Order)ModelFactory.getModel(OrderImpl.class);
        order.setTable(table);
        order.setState(Constanst.STATE.DRAFT);

        Boolean result = order.save();
        if(result) {
            map.put(table.getId(), order);
        }
        return order;
    }

    /**
     * Create Order
     * @param id Table id
     * @return Order
     */
    public Order createOrder(Integer id) {
        return createOrder((Table)ModelFactory.getTable(id));
    }

    public void removeOrder(Table table) {
        Application.getInstance().getWindow().hideSheet();
        table.setState("open");
        table.save();

        /*
        Order order =  map.get(table.getId());
        if(order != null)
            order.delete();

        map.remove(table.getId());
        */
        OrderImpl.searchByTable(table.getId()).delete();

        RootView.getInstance().showView(MainScreen.VIEW_NAME);
    }
}
