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

public interface Table extends Model {
    Floor getFloor();    
    void setFloor(Floor floor);

    String getName();
    void setName(String name);

    String getState();
    void setState(String state);

    String getIcon();
    void setIcon(String icon);
    
    Integer getX();
    void setX(Integer x);

    Integer getY();
    void setY(Integer y);
}
