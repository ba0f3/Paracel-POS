/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.net.MalformedURLException;
import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.User;

/**
 *
 * @author rgv151
 */

@DatabaseModel("res.users")
public class UserImpl extends ModelImpl implements User {
    private static Logger logger = AppGlobal.getLogger(UserImpl.class);
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="login")
    private String login;
    
    @DatabaseField(name="password")
    private String password;
    
    @DatabaseField(name="user_email")
    private String email;
    
    @DatabaseField(name="active")
    private Boolean active;
    
    @DatabaseField(name="context_lang")
    private String language;

    public UserImpl() {
    }
      
    
    public UserImpl(Integer id) {
        super(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
    public static Boolean authenticate(String login, String password) {
        Boolean vaild = false;
        try {
            OpenERP openerp = new OpenERP(AppGlobal.getBackendHost(), AppGlobal.getBackendDatabase(), login, password);
            if(openerp.getOpenERPuserid() > 0) vaild = true;
        } catch (MalformedURLException ex) {
            logger.info(ex.getMessage());
        } catch (XmlRpcException ex) {
            logger.error(ex.getMessage());
        } catch (ClassCastException ex) {
            vaild = false;
        }
        return vaild;
    }
    
    @Override
    public Boolean authenticate() {
        return UserImpl.authenticate(login, password);
    }
    
    @Override
    public Boolean changePassword(String password) {
        //TODO: call change_password method
        return false;
    }
}
