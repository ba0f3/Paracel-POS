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

import java.util.List;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.models.Company;

/**
 *
 * @author Huy Doan
 */
@DatabaseModel("res.company")
public class CompanyImpl extends ModelImpl implements Company {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="parent_id")
    private Company parent;
    
    @DatabaseField(name="child_ids")
    private List<Company> children;

    public CompanyImpl() {
    }

    public CompanyImpl(Integer id) {
        super(id);
    }  
    
    public List<Company> getChildren() {
        return children;
    }

    public void setChildren(List<Company> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }
}
