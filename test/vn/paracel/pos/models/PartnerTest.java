/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models;

import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author rgv151
 */
public class PartnerTest {
    public Partner p;
    public OpenERP openerp;
    Integer id;
    
    public PartnerTest() {
        
    }

    @Before
    public void setUp() {
        AppGlobal.setBackendHost("localhost");
        AppGlobal.setBackendDatabase("paracelpos");
        AppGlobal.setBackendUsername("admin");
        AppGlobal.setBackendPassword("1");
        openerp = AppGlobal.getOpenERPClient();
        
        id = 3;
        p = ModelFactory.getPartner(id);
    }
    
    @After
    public void tearDown() {
        id = null;
        p = null;
    }

    @Test
    public void testLoadPartner() {
        System.out.println("PartnerTest.testLoadPartner()");
        assertEquals(id, p.getId());
    }
    
    //@Test
    public void testGetUserName() {
        System.out.println("PartnerTest.testGetUserName()");
        User u = p.getUser();
        assertEquals("Administrator", u.getName());
    }
    
    @Test
    public void testGetAddress() {
        System.out.println("PartnerTest.testGetAddress()");
        List<PartnerAddress> addresses = p.getAddress();
        assertEquals(3, addresses.size());
    }
    
    //@Test
    public void testGetPartnerFromAddress() {
        System.out.println("PartnerTest.testGetPartnerFromAddress()");
        List<PartnerAddress> addresses = p.getAddress();
        for(PartnerAddress pa : addresses) {
            Partner p1 = pa.getPartner();
            assertEquals(p.getId(), p1.getId());
        }           
    }
    
    //@Test
    public void testSavePartner() {
        System.out.println("PartnerTest.save()");
        p.setName("Bruce Doan");
        Boolean result = p.save();
        assertEquals(true, result);
    }
}
