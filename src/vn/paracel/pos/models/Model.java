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

import java.util.HashMap;

/**
 *
 * @author Huy Doan
 */
public interface Model {
    void setId(Integer id);
    Integer getId();
    
    HashMap<String, HashMap<String, Object>> getCacheData();
    void setCacheData(HashMap<String, HashMap<String, Object>> cacheData);

    void load(Integer id);
    Boolean delete();
    Integer create();
    Boolean save();
    Object[] getFieldList();
    String getModelName();
    String getCacheKey();
}
