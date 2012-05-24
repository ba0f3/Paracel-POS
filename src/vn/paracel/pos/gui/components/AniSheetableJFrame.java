/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */

package vn.paracel.pos.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author Huy Doan
 */
public class AniSheetableJFrame extends JFrame implements ActionListener {

    public static final int INCOMING = 1;
    public static final int OUTGOING = -1;
    public static final float ANIMATION_DURATION = 500f;
    public static final int ANIMATION_SLEEP = 50;

    JComponent sheet;
    JPanel glass;
    AnimatingSheet animatingSheet;
    boolean animating;
    int animationDirection;
    Timer animationTimer;
    long animationStart;
    BufferedImage offscreenImage;

    public AniSheetableJFrame (String name) {
        super(name);
        glass = (JPanel) getGlassPane( );
        glass.setLayout (new GridBagLayout());
        animatingSheet = new AnimatingSheet( );
        animatingSheet.setBackground(new Color(200, 219, 238));
        animatingSheet.setBorder (new LineBorder(new Color(145, 167, 189), 1));
    }
    public JComponent showJDialogAsSheet (JDialog dialog) {
        sheet = (JComponent) dialog.getContentPane( );
        sheet.setBorder (new LineBorder(new Color(145, 167, 189), 1));
        glass.removeAll( );
        animationDirection = INCOMING;
        startAnimation( );
        return sheet;
    }

    public void hideSheet( ) {
        animationDirection = OUTGOING;
        startAnimation( );
    }

    private void startAnimation( ) {
        glass.repaint( );
        // clear glasspane and set up animatingSheet
        animatingSheet.setSource (sheet);
        glass.removeAll( );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        glass.add (animatingSheet, gbc);
        gbc.gridy=1;
        gbc.weighty = Integer.MAX_VALUE;
        glass.add (Box.createGlue( ), gbc);
        glass.setVisible(true);

        // start animation timer
        animationStart = System.currentTimeMillis( );
        if (animationTimer == null)
                animationTimer = new Timer(ANIMATION_SLEEP, this);
        animating = true;
        animationTimer.start();
    }

    private void stopAnimation( ) {
        animationTimer.stop( );
        animating = false;
    }

    // used by the Timer
    @Override
    public void actionPerformed (ActionEvent e) {
        if (animating) {
            // calculate height to show
            float animationPercent =
            (System.currentTimeMillis( ) - animationStart) /
            ANIMATION_DURATION;
            animationPercent = Math.min (1.0f, animationPercent);
            int animatingHeight = 0;

            if (animationDirection == INCOMING) {
                animatingHeight =  (int) (animationPercent * sheet.getHeight( ));
            } else {
                animatingHeight = (int) ((1.0f - animationPercent) * sheet.getHeight( ));
            }
            // clip off that much from sheet and blit it
            // into animatingSheet
            animatingSheet.setAnimatingHeight (animatingHeight);
            animatingSheet.repaint();

            if (animationPercent >= 1.0f) {
                stopAnimation( );
                if (animationDirection == INCOMING) {
                    finishShowingSheet( );
                } else {
                    glass.removeAll( );
                    glass.setVisible(false);
                }
            }
        }
    }
    private void finishShowingSheet( ) {
        glass.removeAll( );
        GridBagConstraints gbc = new GridBagConstraints( );
        gbc.anchor = GridBagConstraints.NORTH;
        glass.add (sheet, gbc);
        gbc.gridy=1;
        gbc.weighty = Integer.MAX_VALUE;
        glass.add (Box.createGlue( ), gbc);
        glass.revalidate( );
        glass.repaint( );
    }
// inner class AnimatedSheet goes here
}