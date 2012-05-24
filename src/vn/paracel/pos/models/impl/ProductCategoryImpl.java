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

import vn.paracel.pos.models.impl.ModelImpl;
import com.bremskerl.de.interfaces.openerp.OpenERP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vn.paracel.pos.annotations.*;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.ProductCategory;

/**
 *
 * @author Huy Doan
 */
@DatabaseModel("pos.category")
public class ProductCategoryImpl extends ModelImpl implements ProductCategory {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="completename", readonly=true)
    private String completeName;
    
    @DatabaseField(name="parent_id")
    @Many2One(ProductCategoryImpl.class)
    private ProductCategory parent;
        
    @DatabaseField(name="child_id")
    @One2Many(model=ProductCategoryImpl.class, field="parent")
    private List<ProductCategory> children;
    
    @DatabaseField(name="sequence")
    private Integer sequence;
    
    public ProductCategoryImpl() {
        super();
    }
            
    public ProductCategoryImpl(Integer id) {
        super(id);
    }

    public List<ProductCategory> getChildren() {
        return children;
    }
    
    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getParent() {
        return parent;
    }
    
    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    
    public static Integer[] getChildCategories(Integer parent_id) {
        OpenERP openerp = AppGlobal.getOpenERPClient();
        
        Object[] result;
        if(parent_id == null) {
            result = openerp.search("pos.category", "parent_id", "=", false);
        } else {
            result = openerp.search("pos.category", "parent_id", "=", parent_id);
        }
        Integer[] cat_ids = Arrays.copyOf(result, result.length, Integer[].class);
        return cat_ids;
    }
    
    public static List<Integer> getRecursiveCategories(Integer parent_id) {
        OpenERP openerp = AppGlobal.getOpenERPClient();
        
        List<Integer> list = new ArrayList<Integer>();
        
        Object[] result;
        if(parent_id == null) {
            result = openerp.search("pos.category", "parent_id", "=", false);
        } else {
            result = openerp.search("pos.category", "parent_id", "=", parent_id);
        }
        Integer[] cat_ids = Arrays.copyOf(result, result.length, Integer[].class);
        for (Integer cid : cat_ids) {
            list.add(cid);
            List<Integer> result1 = ProductCategoryImpl.getRecursiveCategories(cid);
            if(result1 == null) 
                continue;
            for(Integer a : result1) {
                list.add(a);
            }
            
        }
        return list;
    }
}

