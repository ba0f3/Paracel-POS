/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import vn.paracel.pos.models.impl.ModelImpl;
import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.util.ArrayList;
import java.util.List;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.Employee;
import vn.paracel.pos.models.ModelFactory;
import vn.paracel.pos.models.User;

/**
 *
 * @author rgv151
 */

@DatabaseModel("hr.employee")
public class EmployeeImpl extends ModelImpl implements Employee {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="photo")
    private String photo;
    
    @DatabaseField(name="login")
    private String login;
    
    @DatabaseField(name="user_id")
    @Many2One(UserImpl.class)
    private User user;

    public EmployeeImpl() {
        super();
    }
    

    public EmployeeImpl(Integer id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }    
    
    public static List<Employee> getAllEmployees() {
        OpenERP openerp = AppGlobal.getOpenERPClient();
        Object[] result = openerp.search("hr.employee");
        List<Employee> list = new ArrayList<Employee>();
        for(Object r : result) {
            Integer eid = (Integer)r;
            list.add(ModelFactory.getEmployee(eid));
        }
        return list;
    }
}