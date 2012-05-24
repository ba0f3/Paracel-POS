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

public interface Employee extends Model{
    String getName();
    void setName(String name);
    
    String getLogin();
    void setLogin(String login);

    String getPhoto();
    void setPhoto(String photo);

    User getUser();    
    void setUser(User user);
}