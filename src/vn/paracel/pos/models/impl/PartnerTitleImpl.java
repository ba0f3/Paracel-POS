/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import vn.paracel.pos.models.impl.ModelImpl;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.models.PartnerTitle;

/**
 *
 * @author rgv151
 */

@DatabaseModel("res.partner.title")
public class PartnerTitleImpl extends ModelImpl implements PartnerTitle {

    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="shortcut")
    private String shortcut;
    
    @DatabaseField(name="domain")
    private String domain;

    public PartnerTitleImpl() {
    }
        
    public PartnerTitleImpl(Integer id) {
        super(id);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
}
