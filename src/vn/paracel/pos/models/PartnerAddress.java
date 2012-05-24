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
public interface PartnerAddress extends Model {
    String getName();
    void setName(String name);
    
    Partner getPartner();
    public void setPartner(Partner partner);

    PartnerTitle getTitle();
    void setTitle(PartnerTitle title);

    String getType();
    void setType(String type);
}
