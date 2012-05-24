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
import java.util.List;

/**
 *
 * @author Huy Doan
 */
public interface Order extends Model{
    Company getCompany();
    void setCompany(Company company);

    Shop getShop();
    void setShop(Shop shop);

    List<OrderLine> getLines();
    void setLines(List<OrderLine> lines);

    String getName();
    void setName(String name);

    Date getOrderDate();
    void setOrderDate(Date orderDate);

    Partner getPartner();
    void setPartner(Partner partner);

    Integer getPricelist();
    void setPricelist(Integer pricelist);

    String getState();
    void setState(String state);

    Integer[] getStatement();
    void setStatement(Integer[] statement);

    User getUser();
    void setUser(User user);

    Table getTable();
    void setTable(Table table);

    Integer getInvoice();
    void setInvoice(Integer invoice);

    Integer getSaleJournal();
    void setSaleJournal(Integer saleJournal);
}
