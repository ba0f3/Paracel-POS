/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.models;

/**
 *
 * @author Huy Doan
 */
public interface User extends Model {
    
    String getName();
    void setName(String name);

    Boolean isActive();
    void setActive(Boolean active);

    String getEmail();
    void setEmail(String email);

    String getLanguage();
    void setLanguage(String language);

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);
        
    Boolean authenticate();
    
    Boolean changePassword(String password);
}
