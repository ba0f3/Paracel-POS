/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import vn.paracel.pos.models.impl.UserImpl;
import vn.paracel.pos.models.impl.TaxImpl;
import org.junit.*;
import static org.junit.Assert.*;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class TaxTest {
    
    public TaxTest() {
    }

    @Before
    public void setUp() {
        AppGlobal.setBackendHost("localhost");
        AppGlobal.setBackendDatabase("paracelpos");
        AppGlobal.setBackendUsername("admin");
        AppGlobal.setBackendPassword("1");
    }

    
    @Test
    public void testCache() {
        Tax t1 = ModelFactory.getTax(4);
        t1.getName();
        Tax t2 = ModelFactory.getTax(4);
        
        User u = ModelFactory.getUser(1);
    }
}
