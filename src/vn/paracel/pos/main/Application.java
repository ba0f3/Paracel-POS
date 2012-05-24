/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.paracel.pos.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;
import org.slf4j.Logger;
import org.xnap.commons.i18n.I18n;
import vn.paracel.pos.gui.LockScreen;
import vn.paracel.pos.gui.RootView;
import vn.paracel.pos.gui.components.AniSheetableJFrame;

/**
 *
 * @author rgv151
 */
public class Application {
    private static Application instance;
    private static ImageIcon icon;
    private AniSheetableJFrame window;
    private RootView rootView;

    private AppConfig config;
    private I18n i18n = AppGlobal.getI18n(Application.class);
    private Logger logger = AppGlobal.getLogger(Application.class);

    public Application() {
        icon = new ImageIcon(getClass().getResource("/vn/paracel/pos/resources/icons/paracel.png"));

        config = new AppConfig();
        window = new AniSheetableJFrame(i18n.tr("Paracel POS"));
        window.setIconImage(icon.getImage());
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });
    }

    public static Application getInstance() {
        if(instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public AniSheetableJFrame getWindow() {
        return window;
    }

    public void launch() {
        logger.debug("Starting application");
        config.load();

        setApplicationLook();
        setLocale();
        setBackendConfig();

        rootView = RootView.getInstance();

        window.setContentPane(rootView);
        //TODO: set window size in config file
        window.setSize(1024, 768);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(((screenSize.width - window.getWidth()) >> 1), ((screenSize.height - window.getHeight()) >> 1));
        window.setMinimumSize(new Dimension(800, 600));
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setVisible(true);
    }

    public void shutdown() {
        logger.debug("Shutting down the application");
        window.setVisible(false);
        window.dispose();
        System.exit(0);
    }

    private void setApplicationLook() {
        try {
            Object laf = Class.forName(config.getProperty("app.laf")).newInstance();
            UIManager.setLookAndFeel((LookAndFeel) laf);
        } catch (Exception e) {
            logger.info("Cannot set look and feel", e);
        }
    }

    private void setLocale() {
        String slang = config.getProperty("app.language");
        String scountry = config.getProperty("app.country");
        String svariant = config.getProperty("app.variant");
        if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
            Locale.setDefault(new Locale(slang, scountry, svariant));
        }
    }

    private void setBackendConfig() {
        AppGlobal.setBackendHost(config.getProperty("backend.host"));
        AppGlobal.setBackendDatabase(config.getProperty("backend.database"));
        AppGlobal.setBackendUsername(config.getProperty("backend.user"));
        AppGlobal.setBackendPassword(config.getProperty("backend.password"));
    }

}
