/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui;

import java.awt.Dimension;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import org.xnap.commons.i18n.I18n;
import vn.paracel.pos.gui.actions.NumpadAction;
import vn.paracel.pos.gui.widget.OrderList;
import vn.paracel.pos.main.AppGlobal;
import vn.paracel.pos.main.Application;
import vn.paracel.pos.main.Session;
import vn.paracel.pos.models.Order;
import vn.paracel.pos.models.OrderLine;
import vn.paracel.pos.models.Table;
import vn.paracel.pos.order.OrderManager;
import vn.paracel.pos.util.DummyTableCellRenderer;
import vn.paracel.pos.util.NumberRenderer;

/**
 *
 * @author rgv151
 */
public final class OrderScreen extends javax.swing.JPanel{
    public final static String VIEW_NAME = "ORDER_VIEW";
    private I18n i18n = AppGlobal.getI18n(OrderScreen.class);
    private LeftPanelView leftPanelView;

    private int SELECTED_COL;
    private Boolean CLEAR_FIELD = false;

    /**
     * Creates new form OrderScreen
     */
    public OrderScreen() {
        initComponents();

        jPanel5.setPreferredSize(new Dimension(100,100));

        leftPanelView = LeftPanelView.getInstance();
        add(leftPanelView, java.awt.BorderLayout.CENTER);

        initOrderList();
        initNumpad();
        // set default value
        updateTotal(0.0);

        add(FunctionalPanel.getInstance(), java.awt.BorderLayout.PAGE_END);
    }

    @Override
    public void show() {
        super.show();
        FunctionalPanel.getInstance().getMainScreenButton().setEnabled(true);

        ((OrderList)orderList).clear();

        Table table = Session.getTable();
        tableName.setText(table.getName());


        Order order = OrderManager.getInstance().findOrderByTable(table);
        if(order == null) {
            order = OrderManager.getInstance().createOrder(table.getId());
        }
        if(order.getLines() != null) {
            List<OrderLine> orderLines = order.getLines();
            for(OrderLine orderLine : orderLines) {
                ((OrderList)orderList).addItem(orderLine);
            }
        }
        txtOrderName.setText(order.getName());
        if(order.getPartner() != null) {
            txtCustomerName.setText(order.getPartner().getName());
        } else {
            txtCustomerName.setText(i18n.tr("Non-Customer"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("K:m a");
        txtCheckInTime.setText(sdf.format(order.getOrderDate()));
    }

    private void initOrderList(){
        // scrollbars
        jScrollPane3.setHorizontalScrollBar(null);
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(30, 30));
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set size
        orderList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        orderList.getColumnModel().getColumn(OrderList.ITEM_COL).setPreferredWidth(190);
        orderList.getColumnModel().getColumn(OrderList.PRICE_COL).setPreferredWidth(95);
        orderList.getColumnModel().getColumn(OrderList.DISCOUNT_COL).setPreferredWidth(50);
        orderList.getColumnModel().getColumn(OrderList.QUANTITY_COL).setPreferredWidth(35);
        orderList.getColumnModel().getColumn(OrderList.TOTAL_COL).setPreferredWidth(95);

        // display format
        orderList.getColumnModel().getColumn(OrderList.PRICE_COL).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        orderList.getColumnModel().getColumn(OrderList.DISCOUNT_COL).setCellRenderer(new DummyTableCellRenderer());
        orderList.getColumnModel().getColumn(OrderList.TOTAL_COL).setCellRenderer(NumberRenderer.getCurrencyRenderer());
    }

    public void initNumpad() {
        btnNumpad0.addActionListener(new NumpadAction("0"));
        btnNumpad1.addActionListener(new NumpadAction("1"));
        btnNumpad2.addActionListener(new NumpadAction("2"));
        btnNumpad3.addActionListener(new NumpadAction("3"));
        btnNumpad4.addActionListener(new NumpadAction("4"));
        btnNumpad5.addActionListener(new NumpadAction("5"));
        btnNumpad6.addActionListener(new NumpadAction("6"));
        btnNumpad7.addActionListener(new NumpadAction("7"));
        btnNumpad8.addActionListener(new NumpadAction("8"));
        btnNumpad9.addActionListener(new NumpadAction("9"));
        btnNumpadDot.addActionListener(new NumpadAction("."));
        btnNumpadClear.addActionListener(new NumpadAction(""));
    }

    public void toggleButton(int col) {
        setClearField(true);
        SELECTED_COL=col;
        if(col == OrderList.QUANTITY_COL)  {
            btnQuantity.setSelected(true);
            btnPrice.setSelected(false);
            btnDiscount.setSelected(false);
        }

        if(col == OrderList.PRICE_COL)  {
            btnQuantity.setSelected(false);
            btnPrice.setSelected(true);
            btnDiscount.setSelected(false);
        }

        if(col == OrderList.DISCOUNT_COL)  {
            btnQuantity.setSelected(false);
            btnPrice.setSelected(false);
            btnDiscount.setSelected(true);
        }

    }

    public void updateTotal(Double total) {
        NumberFormat formater = NumberFormat.getCurrencyInstance();
        txtSubtotal.setText(formater.format(total));
        txtTax.setText(formater.format(total));
        txtTotal.setText(formater.format(total));
    }

    public void updateTotal(HashMap<String, Double> prices) {
        NumberFormat formater = NumberFormat.getCurrencyInstance();
        txtSubtotal.setText(formater.format(prices.get("priceWithoutTax")));
        txtTax.setText(formater.format(prices.get("tax")));
        txtTotal.setText(formater.format(prices.get("priceWithTax")));
    }

    public JButton getBtnNumpad0() {
        return btnNumpad0;
    }

    public JButton getBtnNumpad1() {
        return btnNumpad1;
    }

    public JButton getBtnNumpad2() {
        return btnNumpad2;
    }

    public JButton getBtnNumpad3() {
        return btnNumpad3;
    }

    public JButton getBtnNumpad4() {
        return btnNumpad4;
    }

    public JButton getBtnNumpad5() {
        return btnNumpad5;
    }

    public JButton getBtnNumpad6() {
        return btnNumpad6;
    }

    public JButton getBtnNumpad7() {
        return btnNumpad7;
    }

    public JButton getBtnNumpad8() {
        return btnNumpad8;
    }

    public JButton getBtnNumpad9() {
        return btnNumpad9;
    }

    public JButton getBtnNumpadClear() {
        return btnNumpadClear;
    }

    public JButton getBtnNumpadDot() {
        return btnNumpadDot;
    }

    public Boolean isClearField() {
        return CLEAR_FIELD;
    }

    public void setClearField(Boolean CLEAR_FIELD) {
        this.CLEAR_FIELD = CLEAR_FIELD;
    }


    public int getSelectedColumn() {
        return SELECTED_COL;
    }

    public void setSelectedCol(int col) {
        SELECTED_COL = col;
    }

    public OrderList getOrderList() {
        return (OrderList)orderList;
    }

    public JLabel getTxtCustomerName() {
        return txtCustomerName;
    }

    public void setTxtCustomerName(JLabel txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orderPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEmployeeName = new javax.swing.JLabel();
        txtLoginTime = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tableName = new javax.swing.JLabel();
        txtOrderName = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JLabel();
        txtCheckInTime = new javax.swing.JLabel();
        controllPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnPrintReciept = new javax.swing.JButton();
        btnOpenDrawer = new javax.swing.JButton();
        btnAddExtraItem = new javax.swing.JButton();
        btnPayOrder = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnRemoveItem = new javax.swing.JButton();
        numpadPanel = new javax.swing.JPanel();
        btnNumpad1 = new javax.swing.JButton();
        btnNumpad2 = new javax.swing.JButton();
        btnNumpad3 = new javax.swing.JButton();
        btnNumpad4 = new javax.swing.JButton();
        btnNumpad5 = new javax.swing.JButton();
        btnNumpad6 = new javax.swing.JButton();
        btnNumpad7 = new javax.swing.JButton();
        btnNumpad8 = new javax.swing.JButton();
        btnNumpad9 = new javax.swing.JButton();
        btnNumpadDot = new javax.swing.JButton();
        btnNumpad0 = new javax.swing.JButton();
        btnNumpadClear = new javax.swing.JButton();
        btnQuantity = new javax.swing.JToggleButton();
        btnPrice = new javax.swing.JToggleButton();
        btnDiscount = new javax.swing.JToggleButton();
        orderListPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orderList = new vn.paracel.pos.gui.widget.OrderList();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTax = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        orderPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(119, 119, 119), 1, true));

        jLabel1.setText("Đăng nhập:");

        jLabel2.setText("Nhân viên:");

        txtEmployeeName.setText("Nguyễn Văn A");

        txtLoginTime.setText("13:00PM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmployeeName, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(txtLoginTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLoginTime)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmployeeName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(119, 119, 119)));

        tableName.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        tableName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableName.setText("Bàn 1");

        txtOrderName.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtOrderName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtOrderName.setText("POS00001");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOrderName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(tableName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrderName)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(119, 119, 119), 1, true));

        jLabel9.setText("Giờ vào:");

        jLabel10.setText("Khách hàng:");

        txtCustomerName.setText("Khách vãng lai");

        txtCheckInTime.setText("13:42PM");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCheckInTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomerName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCheckInTime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        orderPanel.add(infoPanel, java.awt.BorderLayout.PAGE_START);

        controllPanel.setLayout(new java.awt.BorderLayout());

        btnPrintReciept.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        btnPrintReciept.setText("In phiếu thu");
        btnPrintReciept.setMaximumSize(new java.awt.Dimension(70, 25));
        btnPrintReciept.setMinimumSize(new java.awt.Dimension(70, 25));
        btnPrintReciept.setPreferredSize(new java.awt.Dimension(70, 25));

        btnOpenDrawer.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        btnOpenDrawer.setText("Khay tiền");

        btnAddExtraItem.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        btnAddExtraItem.setText("Thêm SP");

        btnPayOrder.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        btnPayOrder.setText("Thanh toán");
        btnPayOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPrintReciept, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(btnPayOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddExtraItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOpenDrawer, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddExtraItem, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(btnPayOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrintReciept, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        controllPanel.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        btnRemoveItem.setText("Xoá SP");
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });

        numpadPanel.setLayout(new java.awt.GridLayout(4, 3, 5, 5));

        btnNumpad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/1_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad1);

        btnNumpad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/2_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad2);

        btnNumpad3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/3_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad3);

        btnNumpad4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/4_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad4);

        btnNumpad5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/5_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad5);

        btnNumpad6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/6_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad6);

        btnNumpad7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/7_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad7);

        btnNumpad8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/8_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad8);

        btnNumpad9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/9_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad9);

        btnNumpadDot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/dot_32.png"))); // NOI18N
        numpadPanel.add(btnNumpadDot);

        btnNumpad0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/0_32.png"))); // NOI18N
        numpadPanel.add(btnNumpad0);

        btnNumpadClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/paracel/pos/resources/images/clear_32.png"))); // NOI18N
        numpadPanel.add(btnNumpadClear);

        btnQuantity.setText("Số lượng");
        btnQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuantityActionPerformed(evt);
            }
        });

        btnPrice.setText("Giá");
        btnPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPriceActionPerformed(evt);
            }
        });

        btnDiscount.setText("Giảm giá");
        btnDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numpadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemoveItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(btnDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addGap(0, 31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numpadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(btnDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel3);

        controllPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        orderPanel.add(controllPanel, java.awt.BorderLayout.PAGE_END);

        orderListPanel.setLayout(new java.awt.BorderLayout());

        orderList.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        orderList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Price", "Disc (%)", "Qty", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderList.setRowHeight(22);
        orderList.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(orderList);

        orderListPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        jLabel3.setText("Total:");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        jLabel4.setText("Subtotal:");

        txtTotal.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtTotal.setText("$0.0");

        txtSubtotal.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtSubtotal.setText("$0.0");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        jLabel7.setText("Tax:");

        txtTax.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtTax.setText("$0.0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(txtTax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        orderListPanel.add(jPanel9, java.awt.BorderLayout.PAGE_END);

        orderPanel.add(orderListPanel, java.awt.BorderLayout.CENTER);

        add(orderPanel, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuantityActionPerformed
        toggleButton(OrderList.QUANTITY_COL);
    }//GEN-LAST:event_btnQuantityActionPerformed

    private void btnPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPriceActionPerformed
        toggleButton(OrderList.PRICE_COL);
    }//GEN-LAST:event_btnPriceActionPerformed

    private void btnDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountActionPerformed
        toggleButton(OrderList.DISCOUNT_COL);
    }//GEN-LAST:event_btnDiscountActionPerformed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
        ((OrderList)orderList).deleteSelectedItem();
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void btnPayOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayOrderActionPerformed
        LeftPanelView.getInstance().showView(PaymentPanel.VIEW_NAME);
    }//GEN-LAST:event_btnPayOrderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddExtraItem;
    private javax.swing.JToggleButton btnDiscount;
    private javax.swing.JButton btnNumpad0;
    private javax.swing.JButton btnNumpad1;
    private javax.swing.JButton btnNumpad2;
    private javax.swing.JButton btnNumpad3;
    private javax.swing.JButton btnNumpad4;
    private javax.swing.JButton btnNumpad5;
    private javax.swing.JButton btnNumpad6;
    private javax.swing.JButton btnNumpad7;
    private javax.swing.JButton btnNumpad8;
    private javax.swing.JButton btnNumpad9;
    private javax.swing.JButton btnNumpadClear;
    private javax.swing.JButton btnNumpadDot;
    private javax.swing.JButton btnOpenDrawer;
    private javax.swing.JButton btnPayOrder;
    private javax.swing.JToggleButton btnPrice;
    private javax.swing.JButton btnPrintReciept;
    private javax.swing.JToggleButton btnQuantity;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JPanel controllPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel numpadPanel;
    private javax.swing.JTable orderList;
    private javax.swing.JPanel orderListPanel;
    private javax.swing.JPanel orderPanel;
    private javax.swing.JLabel tableName;
    private javax.swing.JLabel txtCheckInTime;
    private javax.swing.JLabel txtCustomerName;
    private javax.swing.JLabel txtEmployeeName;
    private javax.swing.JLabel txtLoginTime;
    private javax.swing.JLabel txtOrderName;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTax;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables

}
