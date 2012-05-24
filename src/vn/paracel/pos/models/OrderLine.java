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

import java.util.Date;

/**
 *
 * @author Huy Doan
 */
public interface OrderLine extends Model {
    Company getComany();
    void setComany(Company comany);

    Date getCreateDate();
    void setCreateDate(Date createDate);

    Double getDiscount();
    void setDiscount(Double discount);

    String getName();
    void setName(String name);

    Order getOrder();
    void setOrder(Order order);

    Double getPriceUnit();
    void setPriceUnit(Double priceUnit);

    Product getProduct();
    void setProduct(Product product);

    Integer getQuantity();
    void setQuantity(Integer quantity);

    Double getSubTotalInclude();
    void setSubTotalInclude(Double subTotalInclude);

    Double getSubtotal();
    void setSubtotal(Double subtotal);
}