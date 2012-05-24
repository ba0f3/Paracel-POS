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

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.slf4j.Logger;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.impl.*;

/**
 *
 * @author Huy Doan
 */
public class ModelFactory {
    private static JCS cache = ModelFactory.initCache();
    private static Logger logger = AppGlobal.getLogger(ModelFactory.class);

    public static JCS initCache() {
        try {
            return JCS.getInstance( ModelFactory.class.getSimpleName() );
        } catch (CacheException ex) {
            logger.error("Problem initializing cache for region name [" + ModelFactory.class.getSimpleName() + "]", ex);
        }
        return null;
        
    }
    public static JCS getCache() {
        return cache;
    }
    
    public static void removeCache(String key) {
        try {
            cache.remove(key);
        } catch (CacheException ex) {
            logger.error("Unable to remove cache object with key: " + key, ex);
        }
    }

    public static Model getModel(Class c, Integer id) {
        String key = c.getSimpleName() + ":" + String.valueOf(id);
        Model model = (Model)cache.get(key);
        if(model == null) {
            try {
                //logger.info("Loading object: " + c.getSimpleName());
                Constructor constructor = c.getConstructor(Integer.class);
                model = (Model)Proxy.newInstance(constructor.newInstance(id));
                cache.put(key, model);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return model;
    }

    public static Model getModel(Class c) {
        Constructor constructor;
        Model model = null;
        try {
            constructor = c.getConstructor();
            model = (Model)Proxy.newInstance(constructor.newInstance());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return model;
    }

    public static Company getCompany(Integer id) {
        return (Company)getModel(CompanyImpl.class, id);
    }

    public static Employee getEmployee(Integer id) {
        return (Employee)getModel(EmployeeImpl.class, id);
    }

    public static Floor getFloor(Integer id) {
        return (Floor)getModel(FloorImpl.class, id);
    }

    public static Order getOrder(Integer id) {
        return (Order)getModel(OrderImpl.class, id);
    }

    public static OrderLine getOrderLine(Integer id) {
        return (OrderLine)getModel(OrderLineImpl.class, id);
    }

    public static Partner getPartner(Integer id) {
        return (Partner)getModel(PartnerImpl.class, id);
    }

    public static PartnerAddress getPartnerAddress(Integer id) {
        return (PartnerAddress)getModel(PartnerAddressImpl.class, id);
    }

    public static PartnerTitle getPartnerTitle(Integer id) {
        return (PartnerTitle)getModel(PartnerTitleImpl.class, id);
    }

    public static Product getProduct(Integer id) {
        return (Product)getModel(ProductImpl.class, id);
    }

    public static ProductCategory getProductCategory(Integer id) {
        return (ProductCategory)getModel(ProductCategoryImpl.class, id);
    }

    public static Table getTable(Integer id) {
        return (Table)getModel(TableImpl.class, id);
    }

    public static Tax getTax(Integer id) {
        return (Tax)getModel(TaxImpl.class, id);
    }

    public static User getUser(Integer id) {
        return (User)getModel(UserImpl.class, id);
    }
}
