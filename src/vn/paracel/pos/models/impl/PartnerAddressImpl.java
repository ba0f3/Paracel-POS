/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.models.Partner;
import vn.paracel.pos.models.PartnerAddress;
import vn.paracel.pos.models.PartnerTitle;

/**
 *
 * @author rgv151
 */

@DatabaseModel("res.partner.address")
public class PartnerAddressImpl extends ModelImpl implements PartnerAddress {
    
    @DatabaseField(name="partner_id")
    @Many2One(PartnerImpl.class)
    private Partner partner;
    
    @DatabaseField(name="type")
    private String type;
    
    @DatabaseField(name="title")
    @Many2One(PartnerTitleImpl.class)
    private PartnerTitle title;
    
    @DatabaseField(name="name")
    private String name;

    public PartnerAddressImpl() {
    }
    
    
    
    public PartnerAddressImpl(Integer id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public PartnerTitle getTitle() {
        return title;
    }

    public void setTitle(PartnerTitle title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
