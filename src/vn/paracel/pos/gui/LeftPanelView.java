/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Huy Doan
 */
public class LeftPanelView extends JPanel{
    private CardLayout layout = new CardLayout();

    private ProductPanel productPanel;
    private PaymentPanel paymentPanel;

    private static LeftPanelView instance;

    public LeftPanelView() {
        setLayout(layout);
        setBorder(new EmptyBorder(3,3,3,3));

        paymentPanel = new PaymentPanel();
        addView(PaymentPanel.VIEW_NAME, paymentPanel);

        productPanel = new ProductPanel();
        addView(ProductPanel.VIEW_NAME, productPanel);

        showView(ProductPanel.VIEW_NAME);
    }

    public void addView(String viewName, Component view) {
        add(view, viewName);
    }

    public void showView(String viewName) {
        layout.show(this, viewName);
    }

    public synchronized static LeftPanelView getInstance() {
        if(instance == null) {
            instance = new LeftPanelView();
        }
        return instance;
    }

    public ProductPanel getProductPanel() {
        return productPanel;
    }


}
