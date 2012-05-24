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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import vn.paracel.pos.annotations.Many2One;
import vn.paracel.pos.annotations.One2Many;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author Huy Doan
 */
public final class Proxy implements InvocationHandler {
    private Logger logger = AppGlobal.getLogger(Proxy.class);
    private Object obj;
    private Field cacheField;

    public Proxy(Object obj) {
        this.obj = obj;
        cacheField = findCacheField();        
    }
    
    public static Object newInstance(Object obj) {
	return java.lang.reflect.Proxy.newProxyInstance(
	    obj.getClass().getClassLoader(),
	    obj.getClass().getInterfaces(),
	    new Proxy(obj));
    }

    @Override
    public Object invoke(Object o, Method m, Object[] args) throws Throwable {
        Object result;
	try {
            //logger.debug("Invoking: " + obj.getClass().getSimpleName() + "." + m.getName() + "(" + args + ")");

            if(getCacheData().containsKey(m.getName())) {
                logger.debug("Lazy loading: " + obj.getClass().getSimpleName() + "." + m.getName() + "(" + args + ")");
                
                HashMap<String, Object> map = (HashMap<String, Object>)getCacheData().get(m.getName());
                
                Field field = (Field)map.get("field");
                Object value = map.get("value");
                Method setter = new PropertyDescriptor(field.getName(), obj.getClass()).getWriteMethod();
                if(field.isAnnotationPresent(One2Many.class)) {
                    if(value != null && !(value instanceof Boolean)) {
                        List<Model> objs = new ArrayList<Model>();
                        List v = (List<Integer>)value;
                        One2Many relation = (One2Many)field.getAnnotation(One2Many.class);
                        Iterator i = v.iterator();
                        while(i.hasNext()) {
                            objs.add(ModelFactory.getModel(relation.model(), (Integer)i.next()));
                        }
                        setter.invoke(obj, objs);
                    }
                } else if(field.isAnnotationPresent(Many2One.class)) {
                    if(value != null && !(value instanceof Boolean)) {
                        Many2One relation = (Many2One)field.getAnnotation(Many2One.class);
                        Model newObj = ModelFactory.getModel(relation.value(), (Integer)value);
                        setter.invoke(obj, newObj);
                    }
                }
                getCacheData().remove(m.getName());
            }            
	    result = m.invoke(obj, args);
        } catch (InvocationTargetException e) {
	    throw e.getTargetException();
        } catch (Exception e) {
	    throw new RuntimeException("unexpected invocation exception: " +
				       e.getMessage());
	} finally {
	    //System.out.println("after method " + m.getName());
	}
	return result;
    }
    
    public HashMap<String, Object> getCacheData() {
        HashMap<String, Object> cache = null;
        try {
            cacheField.setAccessible(true);
            cache = (HashMap<String, Object>)cacheField.get(obj);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return cache;
    }
    
    public Field findCacheField() {
        Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
        for(Field field : fields) {
            if(field.getName().equals("cacheData")) {
                return field;
            }
        }
        return null;
    }
}
