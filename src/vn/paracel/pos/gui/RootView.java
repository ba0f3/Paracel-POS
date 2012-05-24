/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.gui;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgv151
 */
public class RootView extends JPanel {
    private CardLayout layout = new CardLayout();

    private LoginScreen loginScreen;
    private MainScreen mainScreen;
    private OrderScreen orderScreen;

    private static RootView instance;

    public RootView() {
        setLayout(layout);
        setBorder(new EmptyBorder(3,3,3,3));

        loginScreen = new LoginScreen();
        addView(LoginScreen.VIEW_NAME, loginScreen);

        mainScreen = new MainScreen();
        addView(MainScreen.VIEW_NAME, mainScreen);

        orderScreen = new OrderScreen();
        addView(OrderScreen.VIEW_NAME, orderScreen);

        showView(MainScreen.VIEW_NAME);
    }

    public void addView(String viewName, Component view) {
        add(view, viewName);
    }

    public void showView(String viewName) {
        layout.show(this, viewName);
    }

    public synchronized static RootView getInstance() {
        if(instance == null) {
            instance = new RootView();
        }
        return instance;
    }

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public OrderScreen getOrderScreen() {
        return orderScreen;
    }
}
