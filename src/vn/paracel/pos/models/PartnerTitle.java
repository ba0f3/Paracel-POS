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

/**
 *
 * @author Huy Doan
 */


public interface  PartnerTitle extends Model {
    String getDomain();
    void setDomain(String domain);

    String getName();
    void setName(String name);

    String getShortcut();
    void setShortcut(String shortcut);
}
