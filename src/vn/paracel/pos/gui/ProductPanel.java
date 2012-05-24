/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarCallBack;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbBarException;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbItem;
import org.pushingpixels.flamingo.api.bcb.JBreadcrumbBar;
import org.pushingpixels.flamingo.api.common.StringValuePair;
import org.xnap.commons.i18n.I18n;
import vn.paracel.pos.gui.actions.CategorySelectorAction;
import vn.paracel.pos.gui.components.JTextField;
import vn.paracel.pos.gui.widget.ProductSelector;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.models.Product;
import vn.paracel.pos.models.impl.ProductCategoryImpl;
import vn.paracel.pos.models.impl.ProductImpl;

/**
 *
 * @author rgv151
 */
public final class ProductPanel extends javax.swing.JPanel{
    public final static String VIEW_NAME = "PRODUCT_VIEW";
    private I18n i18n = AppGlobal.getI18n(OrderScreen.class);
    protected JBreadcrumbBar breadcrumb;
    protected Integer currentCategory;
    protected ProductSelector productSelector;
    private JPanel subCategoryPanel;

    /**
     * Creates new form OrderScreen
     */
    public ProductPanel() {
        initComponents();

        jPanel4.setPreferredSize(new Dimension(350,30));
        jPanel7.setPreferredSize(new Dimension(150,30));

        subCategoryPanel = new JPanel(new FlowLayout());
        filterPanel.add(subCategoryPanel, BorderLayout.PAGE_END);

        productScroll.getVerticalScrollBar().setPreferredSize(new Dimension(30, 30));

        productSelector = new ProductSelector();
        productSelector.setPreferredSize(productScroll.getPreferredSize());
        productScroll.getViewport().setView(productSelector);


        initBreadcrumb();
        initSearchField();

        add(FunctionalPanel.getInstance(), java.awt.BorderLayout.PAGE_END);
    }

    private void initBreadcrumb() {
        BreadcrumbBarCallBack<Integer> callback = new BreadcrumbBarCallBack<Integer>() {
            @Override
            public List<StringValuePair<Integer>> getPathChoices(List<BreadcrumbItem<Integer>> path) throws BreadcrumbBarException {
                final List list = new ArrayList();
                final Integer parent_id;
                if(path == null) {
                    parent_id = null;
                } else {
                    parent_id = path.get(path.size()-1).getData();
                }
                currentCategory = parent_id;

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        subCategoryPanel.removeAll();
                        Integer[] categories = ProductCategoryImpl.getChildCategories(parent_id);
                        for(Integer c_id : categories) {
                            ProductCategoryImpl pc = new ProductCategoryImpl(c_id);
                            StringValuePair<Integer> pair = new StringValuePair<Integer>(pc.getName(), c_id);
                            list.add(pair);
                            Dimension dimension = new Dimension(70, 40);
                            JButton btn = new JButton(new CategorySelectorAction(pc.getName(), c_id));
                            btn.setSize(dimension);
                            btn.setMinimumSize(dimension);
                            btn.setPreferredSize(dimension);
                            subCategoryPanel.add(btn);
                        }
                        subCategoryPanel.updateUI();
                    }
                });
                // load product in new thread
                searchProducts(null);
                return list;
            }
        };
        breadcrumb = new JBreadcrumbBar(callback);
        jPanel4.add(breadcrumb, BorderLayout.CENTER);
    }

    private void initSearchField() {
        JTextField txtSearch = new JTextField();
        txtSearch.setText(i18n.tr("Search item"));
        txtSearch.setPreferredSize(new Dimension(100, 30));
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                JTextField tf = (JTextField)fe.getSource();
                tf.setText("");
            }

            @Override
            public void focusLost(FocusEvent fe) {
                JTextField tf = (JTextField)fe.getSource();
                if(tf.getText().equals("")) {
                    tf.setText(i18n.tr("Search item"));
                }
            }
        });
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                final JTextField tf = (JTextField)ke.getSource();
                new SwingWorker() {
                    @Override protected Object doInBackground() throws Exception {
                        searchProducts(tf.getText());
                        return null;
                    }
                    @Override
                    protected void done() {
                    }
                }.execute();
            }
        });


        jPanel7.add(txtSearch, BorderLayout.PAGE_START);
    }

    private void searchProducts(final String keyword) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    productSelector.removeAll();
                    List<Product> products = ProductImpl.searchProduct(currentCategory, keyword);
                    for(Product product : products) {
                        productSelector.addProduct(product);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(OrderScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JBreadcrumbBar getBreadcrumb() {
        return breadcrumb;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productPanel = new javax.swing.JPanel();
        productScroll = new javax.swing.JScrollPane();
        filterPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        productPanel.setLayout(new java.awt.BorderLayout());

        productScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        productPanel.add(productScroll, java.awt.BorderLayout.CENTER);

        filterPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());
        filterPanel.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());
        filterPanel.add(jPanel7, java.awt.BorderLayout.LINE_END);

        productPanel.add(filterPanel, java.awt.BorderLayout.PAGE_START);

        add(productPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filterPanel;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel productPanel;
    private javax.swing.JScrollPane productScroll;
    // End of variables declaration//GEN-END:variables

}
