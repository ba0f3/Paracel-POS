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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Huy Doan
 */
class AnimatingSheet extends JPanel {
    Dimension animatingSize = new Dimension (0, 1);
    JComponent source;
    BufferedImage offscreenImage;
    public AnimatingSheet ( ) {
        super( );
        setOpaque(true);
    }
    public void setSource (JComponent source) {
        this.source = source;
        animatingSize.width = source.getWidth( );
        makeOffscreenImage(source);
    }
    public void setAnimatingHeight (int height) {
        animatingSize.height = height;
        setSize (animatingSize);
    }
    private void makeOffscreenImage(JComponent source) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        offscreenImage = gfxConfig.createCompatibleImage(source.getWidth(), source.getHeight());
        Graphics2D offscreenGraphics =(Graphics2D) offscreenImage.getGraphics( );
        source.paint (offscreenGraphics);
    }
    @Override
    public Dimension getPreferredSize( ) {  return animatingSize; }
    @Override
    public Dimension getMinimumSize( ) { return animatingSize; }
    @Override
    public Dimension getMaximumSize( ) { return animatingSize; }

    public void paint (Graphics2D g) {
        // get the bottom-most n pixels of source and
        // paint them into g, where n is height

        /*BufferedImage fragment = offscreenImage.getSubimage (0, offscreenImage.getHeight(),
                                                        animatingSize.height,
                                                source.getWidth( ),
                                                animatingSize.height);*/
        BufferedImage fragment = offscreenImage.getSubimage(offscreenImage.getHeight(), source.getWidth(), animatingSize.height, animatingSize.width);
        g.drawImage (fragment, 0, 0, this);
    }
}
