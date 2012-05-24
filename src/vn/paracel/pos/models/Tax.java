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
public interface Tax extends Model {
    
    //void TaxImpl();
    
    Double getAmount();
    void setAmount(Double amount);

    public String getName();
    public void setName(String name);

    public Boolean getPriceInclude();
    public void setPriceInclude(Boolean priceInclude);

    public String getType();
    public void setType(String type);
}
