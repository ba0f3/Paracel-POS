/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import vn.paracel.pos.models.impl.FloorImpl;
import org.junit.*;
import org.junit.Before;
import static org.junit.Assert.*;
import vn.paracel.pos.main.Constanst;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class FloorTest {
    public static FloorImpl floor;
    public FloorTest() {
    }

    @Before
    public void setUp() {
        AppGlobal.setBackendHost("localhost");
        AppGlobal.setBackendDatabase("paracelpos");
        AppGlobal.setBackendUsername("admin");
        AppGlobal.setBackendPassword("1");
    }

    @Test
    public void testAddFloor() {
        floor = new FloorImpl();
        floor.setName("Tầng trệt");
        floor.setState(Constanst.STATE.DRAFT);
        //floor.setDescription("aaa");
        Boolean result = floor.save();
        assertTrue(result);
        assertNotNull(floor.getId());
    }
    
    @Test
    public void testUpdateFloor() {
        floor.setName("Tầng 1");
        Boolean result = floor.save();
        assertTrue(result);
    }
    
    @Test
    public void testDeleteFloor() {
        Boolean result = floor.delete();
        assertTrue(result);   
    }
    
    //@Test
    public void testAddFloor1() {
        floor = new FloorImpl();
        floor.setName("Tầng trệt");
        floor.setState(Constanst.STATE.OPENED);
        floor.setDescription("");
        floor.save();
        
        floor.setId(null);
        floor.setName("Tầng 1");
        floor.save();
        
        floor.setId(null);
        floor.setName("Tầng 2");
        floor.setState(Constanst.STATE.CLOSED);
        floor.save();
    }
}
