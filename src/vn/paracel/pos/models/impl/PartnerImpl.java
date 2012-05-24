/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import com.bremskerl.de.interfaces.openerp.OpenERPDomain;
import java.util.ArrayList;
import java.util.List;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.annotations.One2Many;
import vn.paracel.pos.main.Constanst;
import vn.paracel.pos.models.ModelFactory;
import vn.paracel.pos.models.Partner;
import vn.paracel.pos.models.PartnerAddress;
import vn.paracel.pos.models.User;

/**
 *
 * @author rgv151
 */

@DatabaseModel("res.partner")
public class PartnerImpl extends ModelImpl implements Partner {

    @DatabaseField(name="name")
    private String name;

    @DatabaseField(name="user_id")
    @Many2One(UserImpl.class)
    private User user;

    @DatabaseField(name="address")
    @One2Many(model=PartnerAddressImpl.class, field="partner")
    private List<PartnerAddress> address;

    public PartnerImpl() {
        super();
    }


    public PartnerImpl(Integer id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAddress(List<PartnerAddress> address) {
        this.address = address;
    }

    public List<PartnerAddress> getAddress() {
        return this.address;
    }

    public static List<Partner> findAllPartner(String name) {
        OpenERPDomain domain = new OpenERPDomain();
        if(!name.equals("")) {
            domain.add("name", "ilike", "%" + name + "%");
        }
        List<Integer> ids = ModelImpl.search(PartnerImpl.class, domain);
        List<Partner> partners = new ArrayList<Partner>();
        for(Integer i : ids) {
            partners.add(ModelFactory.getPartner(i));
        }
        return partners;
    }
}
