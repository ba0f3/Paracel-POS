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
public interface ProductCategory extends Model {
    List<ProductCategory> getChildren();
    void setChildren(List<ProductCategory> children);

    String getCompleteName();

    void setCompleteName(String completeName);

    String getName();
    void setName(String name);

    ProductCategory getParent();
    void setParent(ProductCategory parent);

    Integer getSequence();
    void setSequence(Integer sequence);
}

