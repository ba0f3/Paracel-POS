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
import vn.paracel.pos.models.impl.TaxImpl;
import com.bremskerl.de.interfaces.openerp.OpenERP;
import com.bremskerl.de.interfaces.openerp.OpenERPDomain;
import java.util.ArrayList;
import java.util.List;
import vn.paracel.pos.annotations.DatabaseField;
import vn.paracel.pos.annotations.DatabaseModel;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.annotations.One2Many;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.ModelFactory;
import vn.paracel.pos.models.Product;
import vn.paracel.pos.models.ProductCategory;
import vn.paracel.pos.models.Tax;

/**
 *
 * @author Huy Doan
 */
@DatabaseModel("product.product")
public class ProductImpl extends ModelImpl implements Product {
    
    @DatabaseField(name="name")
    private String name;
    
    @DatabaseField(name="pos_categ_id")
    @Many2One(ProductCategoryImpl.class)
    private ProductCategory category;
    
    @DatabaseField(name="lst_price")
    private Double price;
    
    @DatabaseField(name="code")
    private String code;
    
    @DatabaseField(name="active")
    private Boolean active;
    
    @DatabaseField(name="product_image_small")
    private String image;
    
    @DatabaseField(name="taxes_id")
    @One2Many(model=TaxImpl.class, field="")
    private List<Tax> taxes;
            
    public ProductImpl() {
        super();
    }
            
    public ProductImpl(Integer id) {
        super(id);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }
        
    
    /*
     * return all product in a category & its sub categories
     */
    public static List<Product> getAllProducts(Integer catid) {
        return ProductImpl.searchProduct(catid, "");
    }
    
    public static List<Product> searchProduct(Integer catid, String keyword) {
        List<Product> products = new ArrayList<Product>();
        OpenERP openerp = AppGlobal.getOpenERPClient();
        OpenERPDomain domain = new OpenERPDomain();
        
        List<Integer> categories = ProductCategoryImpl.getRecursiveCategories(catid);
        for(int i = 0; i<categories.size(); i++ ) {
            domain.or();
        }
        if(catid == null) {
            domain.add("pos_categ_id", "!=", false);
        } else {
            domain.add("pos_categ_id", "=", catid);
        }
        
        for(Integer c : categories) {
            domain.add("pos_categ_id", "=", c);
        }
        if(keyword != null && !keyword.isEmpty()) {
            //domain.and();
            domain.add("name", "ilike", keyword);
            
        }
        Object[] result = openerp.search("product.product", domain);
        for(Object r : result) {
            products.add(ModelFactory.getProduct((Integer)r));
        }
        return products;
    }
    
}

