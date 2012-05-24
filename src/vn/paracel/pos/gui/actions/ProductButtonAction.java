/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import vn.paracel.pos.gui.OrderScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.models.Product;
import vn.paracel.pos.util.ImageUtil;

/**
 *
 * @author Huy Doan
 */
public class ProductButtonAction extends AbstractAction {
    protected Product product;

    public ProductButtonAction(Product product) {
        this.product = product;
        
        putValue(Action.SMALL_ICON, ImageUtil.parseImage(product.getImage()));
        putValue(Action.NAME, product.getName());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        RootView.getInstance().getOrderScreen().getOrderList().addItem(product);
    }

}
