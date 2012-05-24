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
import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarModel;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbItem;
import org.pushingpixels.flamingo.api.bcb.JBreadcrumbBar;
import vn.paracel.pos.gui.LeftPanelView;
import vn.paracel.pos.gui.RootView;

/**
 *
 * @author Huy Doan
 */
public class CategorySelectorAction extends AbstractAction {
    private Integer id = null;
    private String name = null;

    public CategorySelectorAction(String name, Integer id) {
        this.id = id;
        this.name = name;
        putValue(Action.NAME, name);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       JBreadcrumbBar breadcrumb = LeftPanelView.getInstance().getProductPanel().getBreadcrumb();
       BreadcrumbBarModel model =  breadcrumb.getModel();
       model.getItems();
       model.addLast(new BreadcrumbItem(name, id));
       //model.
    }

}
