/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.models.impl;

import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.models.Tax;

/**
 *
 * @author Huy Doan
 */
@DatabaseModel("account.tax")
public class TaxImpl extends ModelImpl implements Tax  {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="amount")
    private Double amount;
    
    @DatabaseField(name="price_include")
    private Boolean priceInclude;
    
    @DatabaseField(name="type")
    private String type;


    public TaxImpl() {
    }
    
    public TaxImpl(Integer id) {
        super(id);
    }
    
    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Double amount) {
        this.amount = amount;
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
    public Boolean getPriceInclude() {
        return priceInclude;
    }

    @Override
    public void setPriceInclude(Boolean priceInclude) {
        this.priceInclude = priceInclude;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
