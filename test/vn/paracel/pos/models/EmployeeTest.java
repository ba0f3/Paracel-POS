/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import vn.paracel.pos.models.impl.EmployeeImpl;
import org.junit.*;
import static org.junit.Assert.*;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class EmployeeTest {
    private static EmployeeImpl emp;
    
    public EmployeeTest() {
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

    /**
     * Test of setEmployee method, of class EmployeeImpl.
     */
    @Test
    public void testGetEmployeeByID() {
        Integer id = 1;
        emp = new EmployeeImpl(id);
        assertEquals(id, emp.getId());
    }
    
    @Test
    public void testGetUserData() {
        String name = emp.getUser().getName();
        assertNotNull(name);
    }
}
