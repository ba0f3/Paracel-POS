/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import vn.paracel.pos.models.impl.ProductImpl;
import vn.paracel.pos.models.impl.TaxImpl;
import java.util.List;
import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class ProductTest {
    
    public ProductTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
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
        Product p = ModelFactory.getProduct(118);
        List<Tax> taxes = p.getTaxes();
        assertEquals(1, taxes.size());
        
    }
}
