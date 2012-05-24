/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import org.junit.*;
import vn.paracel.pos.common.Main;
import vn.paracel.pos.main.Constanst;
import vn.paracel.pos.models.impl.OrderImpl;

/**
 *
 * @author rgv151
 */
public class OrderTest {
    
    public OrderTest() {
    }

    @Before
    public void setUp() throws Exception {
        Main.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateOrder() {
        Order order = (Order)ModelFactory.getModel(OrderImpl.class);
        order.setCompany(ModelFactory.getCompany(1));
        order.setPartner(ModelFactory.getPartner(1));
        order.setState(Constanst.STATE.DRAFT);
        order.setTable(ModelFactory.getTable(3));
        order.setUser(ModelFactory.getUser(1));
        
        order.save();
    }
}
