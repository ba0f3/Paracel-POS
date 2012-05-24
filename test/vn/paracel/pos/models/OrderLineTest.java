/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import org.junit.*;
import static org.junit.Assert.*;
import vn.paracel.pos.common.Main;
import vn.paracel.pos.models.impl.OrderLineImpl;

/**
 *
 * @author rgv151
 */
public class OrderLineTest {
    
    OrderLine orderLine;
    Integer orderId = 8;
            
    public OrderLineTest() {
    }

    @Before
    public void setUp() throws Exception {
        Main.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateOrderLine() {
        Product product = ModelFactory.getProduct(118);
        orderLine = (OrderLine)ModelFactory.getModel(OrderLineImpl.class);
        orderLine.setProduct(product);
        orderLine.setPriceUnit(product.getPrice());        
        orderLine.setOrder(ModelFactory.getOrder(orderId));
        orderLine.save();
        
        assertEquals(orderId, orderLine.getOrder().getId());
    }
}
