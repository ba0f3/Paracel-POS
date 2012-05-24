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
public interface Company extends Model {
    
    List<Company> getChildren();

    void setChildren(List<Company> children);

    String getName();

    void setName(String name);

    Company getParent();

    void setParent(Company parent);
}
