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

import java.util.Date;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Fields;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.models.*;

/**
 *
 * @author Huy Doan
 */
@DatabaseModel("pos.order.line")
public class OrderLineImpl extends ModelImpl implements OrderLine {
    @DatabaseField(name="company_id", required=true, type=Fields.MANY2ONE)
    @Many2One(CompanyImpl.class)
    private Company comany;

    @DatabaseField(name="name", required=true, type=Fields.CHAR, readonly=true)
    private String name;

    @DatabaseField(name="product_id", required=true, type=Fields.MANY2ONE)
    @Many2One(ProductImpl.class)
    private Product product;

    @DatabaseField(name="price_unit", type=Fields.FLOAT)
    private Double priceUnit;

    @DatabaseField(name="qty", type=Fields.INTEGER, defInt=1)
    private Integer quantity;

    @DatabaseField(name="price_subtotal", readonly=true, type=Fields.FLOAT)
    private Double subtotal;

    @DatabaseField(name="price_subtotal_incl", readonly=true, type=Fields.FLOAT)
    private Double subTotalInclude;

    @DatabaseField(name="discount", type=Fields.FLOAT, defFloat=0.0)
    private Double discount;

    @DatabaseField(name="order_id", type=Fields.MANY2ONE)
    @Many2One(OrderImpl.class)
    private Order order;

    @DatabaseField(name="create_date", readonly=true, type=Fields.DATETIME)
    private Date createDate;

    public OrderLineImpl() {
    }

    public OrderLineImpl(Integer id) {
        super(id);
    }

    @Override
    public Company getComany() {
        return comany;
    }

    @Override
    public void setComany(Company comany) {
        this.comany = comany;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(Double discount) {
        this.discount = discount;
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
    public Order getOrder() {
        return order;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Double getPriceUnit() {
        return priceUnit;
    }

    @Override
    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public Double getSubTotalInclude() {
        return subTotalInclude;
    }

    @Override
    public void setSubTotalInclude(Double subTotalInclude) {
        this.subTotalInclude = subTotalInclude;
    }

    @Override
    public Double getSubtotal() {
        return subtotal;
    }

    @Override
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public static OrderLine findByOrderAndProduct(Order order, Product product) {
        return null;
    }
    
}