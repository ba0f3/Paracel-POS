/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.util;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Huy Doan
 */
public class DummyTableCellRenderer extends DefaultTableCellRenderer{

    public DummyTableCellRenderer() {
        super();
        setHorizontalAlignment( SwingConstants.RIGHT );
    }

}
