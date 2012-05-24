/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.widget;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.pushingpixels.substance.api.UiThreadingViolationException;
import vn.paracel.pos.gui.OrderScreen;
import vn.paracel.pos.gui.actions.ProductButtonAction;
import vn.paracel.pos.models.Product;

/**
 *
 * @author Huy Doan
 */
public class ProductSelector extends JPanel {
    private final int VGAP = 5;
    private final int HGAP = 5;
    private final int W = 122;
    private final int H = 122;
    Dimension dimension = new Dimension(W, H);

    public ProductSelector() {
        setLayout(new FlowLayout(FlowLayout.LEFT, VGAP, HGAP));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                super.componentResized(ce);
                //ProductSelector self = (ProductSelector)ce.getSource();
                handleSize();
            }
        });
    }

    private void handleSize() {
        try {
            int itemCount = getComponentCount();
            int itemsPerRow = getWidth()/(W+HGAP);
            int numberOfColumns = (int)Math.ceil(itemCount/itemsPerRow);
            if(itemCount%itemsPerRow > 0) {
                numberOfColumns++;
            }

            int height = numberOfColumns*(H+VGAP);
            setPreferredSize(new Dimension(getWidth(), height));
        } catch (Exception ex) {
        }
    }

    public void addProduct(Product product) {
        try {
            JButton btn = new JButton(new ProductButtonAction(product));
            btn.setSize(dimension);
            btn.setMaximumSize(dimension);
            btn.setMinimumSize(dimension);
            btn.setPreferredSize(dimension);
            btn.setVerticalTextPosition(JLabel.BOTTOM);
            btn.setHorizontalTextPosition(JLabel.CENTER);
            add(btn);
        } catch (UiThreadingViolationException ex) {
        }

        handleSize();
    }
}
