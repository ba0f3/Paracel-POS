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

import java.util.List;

/**
 *
 * @author Huy Doan
 */
public interface Partner extends Model {  
    String getName();
    void setName(String name);
    
    User getUser();
    void setUser(User user);
        
    void setAddress(List<PartnerAddress> address);    
    List<PartnerAddress> getAddress();
}
