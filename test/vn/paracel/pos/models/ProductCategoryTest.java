/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import vn.paracel.pos.models.impl.ProductCategoryImpl;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class ProductCategoryTest {
    
    public ProductCategoryTest() {
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

    /**
     * Test of getRecursiveCategories method, of class ProductCategoryImpl.
     */
    @Test
    public void testGetRecursiveCategories() {
        System.out.println("getRecursiveCategories");
        Integer parent_id = 2;
        List expResult = null;
        List result = ProductCategoryImpl.getRecursiveCategories(parent_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
