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
public interface Product extends Model {
    Boolean getActive();
    void setActive(Boolean active);

    ProductCategory getCategory();
    void setCategory(ProductCategory category);

    String getCode();
    void setCode(String code);

    String getName();
    void setName(String name);
    
    Double getPrice();
    void setPrice(Double price);

    String getImage();
    void setImage(String image);

    List<Tax> getTaxes();
    void setTaxes(List<Tax> taxes);    
}

