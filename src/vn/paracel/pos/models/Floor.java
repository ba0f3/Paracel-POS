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

import java.util.List;

/**
 *
 * @author Huy Doan
 */
public interface Floor extends Model{
    String getDescription();

    void setDescription(String description);

    String getName();

    void setName(String name);

    String getState();

    void setState(String state);

    List<Table> getTables();

    void setTables(List<Table> tables);

    String getBackground();

    void setBackground(String background);

    String getIcon();

    void setIcon(String icon);
    
}
