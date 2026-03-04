package views;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class AppUI {

    public static void setUpAppUI(boolean darkMode) {

        try {

            if (darkMode) { UIManager.setLookAndFeel(new FlatDarkLaf()); }
            else { UIManager.setLookAndFeel(new FlatLightLaf()); }

            Font baseFont = GlobalUtilities.getMediumAppFont();
            Color baseTextColor = darkMode ? AppColors.WHITE_COLOR : AppColors.DARK_BLUE_COLOR;
            ColorUIResource uiColor = new ColorUIResource(baseTextColor);

            UIManager.put("defaultFont", baseFont);
            UIManager.put("Label.foreground", uiColor);
            UIManager.put("Button.foreground", uiColor);
            UIManager.put("TextField.foreground", uiColor);
            UIManager.put("TextComponent.foreground", uiColor);
            UIManager.put("PasswordField.foreground", uiColor);
            UIManager.put("TextField.caretForeground", uiColor);
            UIManager.put("Button.arc", 18);
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("ScrollBar.width", 7);

            FlatLaf.updateUI();

        } catch (Exception e) { System.err.println("!! Une erreur st subvenue lors du setUp de l'UI de l'application : " + e.getMessage()); }
    }

    public static void initUI(){ setUpAppUI(false); }

}
