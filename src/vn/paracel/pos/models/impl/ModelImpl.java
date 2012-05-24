/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.models.impl;

import com.bremskerl.de.interfaces.openerp.OpenERP;
import com.bremskerl.de.interfaces.openerp.OpenERPDomain;
import com.bremskerl.de.interfaces.openerp.OpenERPRecord;
import com.bremskerl.de.interfaces.openerp.OpenERPRecordSet;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.jcs.access.exception.CacheException;
import org.slf4j.Logger;
import vn.paracel.pos.annotations.*;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.Model;
import vn.paracel.pos.models.ModelFactory;

/**
 *
 * @author rgv151
 */
public abstract class ModelImpl implements Model, Serializable {
    public Logger logger = AppGlobal.getLogger(ModelImpl.class);
    protected Class c = this.getClass();

    /**
     * ModelImpl ID
     */
    protected Integer id;

    protected HashMap<String, HashMap<String, Object>> cacheData;

    /**
     * Constructor
     */
    public ModelImpl() {
        init();
    }

    /**
     * Constructor
     * @param id
     */
    public ModelImpl(Integer id) {
        init();
        this.id = id;
        load(id);
    }

    private void init() {
        cacheData = new  HashMap<String, HashMap<String, Object>>();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public HashMap<String, HashMap<String, Object>> getCacheData() {
        return cacheData;
    }

    @Override
    public void setCacheData(HashMap<String, HashMap<String, Object>> cacheData) {
        this.cacheData = cacheData;
    }

    /**
     * Load object data via XML-RPC
     * @param id
     * @return ModelImpl
     */
    @Override
    public final void load(Integer id) {
        //String key = c.getSimpleName() + ":" + String.valueOf(id);

        OpenERP openerp = AppGlobal.getOpenERPClient();
        OpenERPRecordSet rs = openerp.readRecords(this.getModelName(), new Object[] { id }, this.getFieldList());
        OpenERPRecord r = rs.get(id);
        java.lang.reflect.Field[] fields = c.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            if(field.isAnnotationPresent(DatabaseField.class)) {
                DatabaseField fieldName = (DatabaseField)field.getAnnotation(DatabaseField.class);
                set(field.getName(), r.get(fieldName.name()));
            }
        }
    }

    @Override
    public Integer create() {
        if(save()) {
            return id;
        }
        return null;
    }

    @Override
    public Boolean save() {
        java.lang.reflect.Field[] fields = c.getDeclaredFields();
        HashMap<String, Object> values = new HashMap<String, Object>();
        for(java.lang.reflect.Field field : fields) {
            if(!field.isAnnotationPresent(DatabaseField.class)) continue;
            field.setAccessible(true);
            DatabaseField fieldName = (DatabaseField)field.getAnnotation(DatabaseField.class);

            if(fieldName.readonly()) continue;

            try {
                if(fieldName.required() && field.get(this) == null) {
                    logger.warn("Mandatory field is missing: " + getModelName() + "." + field.getName());
                }
                Method getter = new PropertyDescriptor(field.getName(), getClass()).getReadMethod();
                if(field.isAnnotationPresent(One2Many.class)) {
                    List<Model> objs = (List<Model>)getter.invoke(this);
                    List<Integer> ids = new ArrayList<Integer>();
                    if(objs != null) {
                        Iterator i = objs.iterator();
                        while(i.hasNext()) {
                            Model obj = (Model)i.next();
                            ids.add(obj.getId());
                        }
                    }
                    values.put(fieldName.name(), ids);
                } else if(field.isAnnotationPresent(Many2One.class)) {
                    Model obj =  (Model)getter.invoke(this);
                    if(obj != null) {
                        values.put(fieldName.name(), obj.getId());
                    }
                } else {
                    Object value = field.get(this);
                    Fields type = fieldName.type();
                    switch(type) {
                        case BOOLEAN:
                            value = (value==null)?fieldName.defBoolean():value;
                            break;
                        case INTEGER:
                            value = (value==null)?fieldName.defInt():value;
                            break;
                        case FLOAT:
                            value = (value==null)?fieldName.defFloat():value;
                            break;
                        case DATE:
                        case DATETIME:
                        default:
                            value = field.get(this);
                            break;

                    }
                    values.put(fieldName.name(), value);
                }
            } catch(Exception ex) {
                logger.error(getModelName() + "->save(): " + ex.getMessage());
            }
        }
        OpenERP openerp = AppGlobal.getOpenERPClient();
        Boolean result;
        if(this.getId() != null) {
            logger.debug("Updating " + getModelName() + "[" + getId().toString() +"] with values: " + values);
            result = openerp.write(getModelName(), this.getId(), values);
        } else {
            logger.debug("Creating new " + getModelName() + " record with values: " + values);
            id = openerp.create(getModelName(), values);
            result = true;
        }
        try {
            ModelFactory.getCache().put(c.getSimpleName() + ":" + String.valueOf(id), this);
        } catch (CacheException ex) {
            logger.error(getModelName() + "->save(): " + ex.getMessage());
        }
        return result;
    }

    @Override
    public Boolean delete() {
        ModelFactory.removeCache(getCacheKey());
        OpenERP openerp = AppGlobal.getOpenERPClient();
        return openerp.delete(getModelName(), id);
    }

    protected void set(String fieldName, Object value) {
        try {
            Field f = c.getDeclaredField(fieldName);
            Method getter = new PropertyDescriptor(fieldName, getClass()).getReadMethod();
            if(f.isAnnotationPresent(One2Many.class)) {
                if(value != null && !(value instanceof Boolean)) {
                    Vector<Integer> objs = new Vector<Integer>();
                    Object[] v = (Object[])value;
                    for(int i = 0; i < v.length; i++) {
                        objs.add((Integer)v[i]);
                    }
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("field", f);
                    map.put("value", objs);
                    cacheData.put(getter.getName(), map);
                }
            } else if(f.isAnnotationPresent(Many2One.class)) {
                if(value != null && !(value instanceof Boolean)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("field", f);
                    map.put("value", (Integer)value);
                    cacheData.put(getter.getName(), map);
                }
            } else {
                Fields type = f.getAnnotation(DatabaseField.class).type();
                if((value instanceof Boolean) && ((Boolean)value == false)  && (f.getType() != Boolean.class)) value = null;
                switch(type) {
                    case BOOLEAN:
                        value = (Boolean)value;
                        break;
                    case INTEGER:
                        if(value != null) {
                            value = (value instanceof Double)?((Double)value).intValue():(Integer)value;
                        }
                        break;
                    case FLOAT:
                        value = (Double)value;
                        break;
                    case DATE:
                        if(value != null) {
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            value = (Date)formatter.parse(value.toString());
                        }
                        break;
                    case DATETIME:
                        if(value != null) {
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                            value = (Date)formatter.parse(value.toString());
                        }
                        break;
                    default:
                        break;

                }
                Method setter = new PropertyDescriptor(fieldName, getClass()).getWriteMethod();
                setter.invoke(this, value);
            }
        } catch (Exception ex) {
            logger.error(getModelName() + "->set(" + fieldName + ", " + value.toString() + "): " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public String getModelName() {
        return getModelName(c);
    }

    public static String getModelName(Class c) {
        String name = "";
        try {
            DatabaseModel model = (DatabaseModel)c.getAnnotation(DatabaseModel.class);
            name = model.value();
        } catch (NullPointerException e) {
            AppGlobal.getLogger(ModelImpl.class).error("No model defined for class: " + c.getCanonicalName());
            System.out.println("No model defined for class: " + c.getCanonicalName());
        }
        return name;
    }

    @Override
    public Object[] getFieldList() {

        Vector<String> v = new Vector<String>();
        java.lang.reflect.Field[] fields = c.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            if(field.isAnnotationPresent(DatabaseField.class)) {
                DatabaseField fieldName = (DatabaseField)field.getAnnotation(DatabaseField.class);
                v.add(fieldName.name());
            }
        }
        return v.toArray(new String[v.size()]);
    }

    @Override
    public String toString() {
        java.lang.reflect.Field[] fields = c.getDeclaredFields();
        Vector<String> fieldList = new Vector<String>();
        Method getter;
        try {
                getter = new PropertyDescriptor("id", getClass()).getReadMethod();
                fieldList.add("id=" + getter.invoke(this));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        for(java.lang.reflect.Field field : fields) {
            if(!field.isAnnotationPresent(DatabaseField.class)) {
                continue;
            }
            try {
                getter = new PropertyDescriptor(field.getName(), getClass()).getReadMethod();
                if(field.isAnnotationPresent(Many2One.class)) {
                    Model obj = (Model)getter.invoke(this);
                    Integer oid = null;
                    if(obj != null) {
                        oid = obj.getId();
                    }
                    fieldList.add(field.getName() + "=" + String.valueOf(oid));
                } else {
                    fieldList.add(field.getName() + "=" + getter.invoke(this));
                }

            } catch (Exception ex) {
                logger.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return getModelName() + "[" + StringUtils.join(fieldList, ", ") + "]";
    }

    @Override
    public String getCacheKey() {
        return (id != null)?getClass().getSimpleName() + ":" + String.valueOf(id):"";
    }

    public static List<Integer> search(Class clazz, OpenERPDomain domain) {
        List<Integer> objs = new ArrayList<Integer>();
        OpenERP openerp = AppGlobal.getOpenERPClient();

        DatabaseModel annotation = (DatabaseModel)clazz.getAnnotation(DatabaseModel.class);
        Object[] result = openerp.search(annotation.value(), domain);
        for(Object r : result) {
            objs.add((Integer)r);
        }
        return objs;
    }

    public static Integer findOne(Class clazz, OpenERPDomain domain) {
        List<Integer> result = search(clazz, domain);
        if(result.size() > 0) {
            return result.get(0);
        }
        return null;
    }
}
