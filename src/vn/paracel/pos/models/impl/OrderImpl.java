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

import com.bremskerl.de.interfaces.openerp.OpenERPDomain;
import java.util.Date;
import java.util.List;
import vn.paracel.pos.annotations.*;
import vn.paracel.pos.main.Constanst;
import vn.paracel.pos.models.*;

/**
 *
 * @author Huy Doan
 */

@DatabaseModel("pos.order")
public class OrderImpl extends ModelImpl implements Order {

    @DatabaseField(name="name", required=true, readonly=true)
    private String name;

    @DatabaseField(name="company_id", type=Fields.MANY2ONE, required=true, readonly=true)
    @Many2One(CompanyImpl.class)
    private Company company;

    @DatabaseField(name="shop_id", type=Fields.MANY2ONE, required=true, readonly=true)
    @Many2One(ShopImpl.class)
    private Shop shop;

    @DatabaseField(name="date_order", readonly=true, type= Fields.DATETIME)
    private Date orderDate;

    @DatabaseField(name="user_id")
    @Many2One(UserImpl.class)
    private User user;

    @DatabaseField(name="lines", readonly=true)
    @One2Many(model=OrderLineImpl.class, field="order_id")
    private List<OrderLine> lines;

    //@DatabaseField("statement_ids")
    //@One2Many(model=OrderLineImpl.class, field="order_id")
    private Integer[] statement;

    @DatabaseField(name="pricelist_id", readonly=true)
    private Integer pricelist;

    @DatabaseField(name="partner_id")
    @Many2One(PartnerImpl.class)
    private Partner partner;

    @DatabaseField(name="state", readonly=true)
    private String state;

    @DatabaseField(name="table_id", type= Fields.MANY2ONE)
    @Many2One(TableImpl.class)
    private Table table;

    @DatabaseField(name="invoice_id", type= Fields.INTEGER, readonly=true)
    private Integer invoice;

    @DatabaseField(name="sale_journal", type= Fields.INTEGER, readonly=true)
    private Integer saleJournal;

    public OrderImpl(Integer id) {
        super(id);
    }

    public OrderImpl() {
    }

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public Shop getShop() {
        return shop;
    }

    @Override
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public List<OrderLine> getLines() {
        return lines;
    }

    @Override
    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
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
    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public Partner getPartner() {
        return partner;
    }

    @Override
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public Integer getPricelist() {
        return pricelist;
    }

    @Override
    public void setPricelist(Integer pricelist) {
        this.pricelist = pricelist;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Integer[] getStatement() {
        return statement;
    }

    @Override
    public void setStatement(Integer[] statement) {
        this.statement = statement;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public Integer getInvoice() {
        return invoice;
    }

    @Override
    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    @Override
    public Integer getSaleJournal() {
        return saleJournal;
    }

    @Override
    public void setSaleJournal(Integer saleJournal) {
        this.saleJournal = saleJournal;
    }

    public static Order searchByTable(Integer id) {
        OpenERPDomain domain = new OpenERPDomain();
        domain.add("table_id", "=", id);
        domain.add("state", "=", Constanst.STATE.DRAFT);
        Integer orderId = ModelImpl.findOne(OrderImpl.class, domain);
        if(orderId != null) {
            return ModelFactory.getOrder(orderId);
        }
        return null;
    }
}
