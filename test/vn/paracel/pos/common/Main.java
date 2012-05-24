/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.common;

import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author Huy Doan
 */
public class Main {
    public static void init() {
        AppGlobal.setBackendHost("localhost");
        AppGlobal.setBackendDatabase("paracelpos");
        AppGlobal.setBackendUsername("admin");
        AppGlobal.setBackendPassword("1");
    }

}
