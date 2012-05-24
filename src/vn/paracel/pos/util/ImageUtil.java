/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */
package vn.paracel.pos.util;

import java.io.IOException;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import sun.misc.BASE64Decoder;
import vn.paracel.pos.main.AppGlobal;

/**
 *
 * @author Huy Doan
 */
public class ImageUtil {
    private static Logger logger = AppGlobal.getLogger(ImageUtil.class);
    
    /**
     * parse image icon from a base64 encoded string
     * @param s
     * @return ImageIcon
     */
    public static ImageIcon parseImage(String s) {
        if(s == null || s.equals("")) return null;
        
        ImageIcon image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(s);
            image = new ImageIcon(bytes);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return image;
    }
    
}
