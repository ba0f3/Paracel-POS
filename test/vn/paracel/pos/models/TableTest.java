/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import org.junit.*;
import static org.junit.Assert.*;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class TableTest {
    
    public TableTest() {
    }

    @Before
    public void setUp() {
        AppGlobal.setBackendHost("localhost");
        AppGlobal.setBackendDatabase("paracelpos");
        AppGlobal.setBackendUsername("admin");
        AppGlobal.setBackendPassword("1");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        Table t = ModelFactory.getTable(1);
        t.getFloor();
        t.getFloor();
    }
}
