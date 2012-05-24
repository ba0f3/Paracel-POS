/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.main;

import static javax.swing.SwingUtilities.invokeLater;

/**
 *
 * @author Huy Doan
 */
public class ParacelPOS {
    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                Application.getInstance().launch();
            }
        });
    }
}