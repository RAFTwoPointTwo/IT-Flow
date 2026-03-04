package utilities;

import com.formdev.flatlaf.ui.FlatLineBorder;

import java.awt.*;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class GlobalUtilities {

    private GlobalUtilities(){ }


    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final Dimension commonPageDimension = new Dimension(1300 , 600);

    private static final Dimension commonHomePageDimension = new Dimension(1000 , 600);

    private static final Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private static Font inter;


    public static String getCurrentDate(){ return LocalDate.now().format(dateFormat); }

    public static int getDBFalseStatusFlag(){ return 0; }

    public static int getDBTrueStatusFlag(){ return 1; }

    public static int getDBErrorFlag(){ return 10; }

    public static boolean isDBTrueStatusFlag(int flag){ return flag == GlobalUtilities.getDBTrueStatusFlag(); }

    public static int getIntFlagByBooleanFlag(boolean booleanFlag){ return booleanFlag ? 1 : 0; }

    public static boolean getBooleanFlagByIntFlag(int intFlag){ return intFlag == 1; }

    public static String passwordHasher(String password) {
        try {

            MessageDigest baseAlgorithm = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = baseAlgorithm.digest(password.getBytes());

            StringBuilder builder = new StringBuilder();

            for (byte b : hashBytes) { builder.append(String.format("%02x", b)); }

            return builder.toString();

        } catch (NoSuchAlgorithmException e) { throw new RuntimeException("\n !! Une erreur est subvenue lors d'un hashage de mot de passe : " + e.getMessage()); }
    }

    public static boolean checkPasswordWithHashed(String enteredPassword , String hashedPassword){
        if (enteredPassword == null || hashedPassword == null) return false;
        return passwordHasher(enteredPassword).equals(hashedPassword);
    }

    public static Dimension getCommonPageDimension(){ return commonPageDimension; }

    public static Dimension getCommonHomePageDimension(){ return commonHomePageDimension; }

    public static Cursor getHandCursor(){ return handCursor; }

    public static Font getAppFont(float size) {
        if (inter == null) {
            try (InputStream fontStream = GlobalUtilities.class.getResourceAsStream("/fonts/InterVariable.ttf")) {
                if (fontStream == null){
                    inter = new Font("SansSerif", Font.PLAIN, (int) size);
                    return inter;
                }
                inter = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(inter);
            } catch (Exception e) {
                inter = new Font("SansSerif", Font.PLAIN, (int) size);
                System.out.println("\n !! Une erreur est subvenue lors du chargement de la font de l'application : " + e.getMessage());
            }
        }
        return inter.deriveFont(size);
    }

    public static Font getSmallAppFont() { return getAppFont(13f); }

    public static Font getMediumAppFont() { return getAppFont(16f); }

    public static Font getLargeAppFont() { return getAppFont(26f); }

    public static Font getBoldAppFont(float size){ return getAppFont(size).deriveFont(Font.BOLD); }

    public static Font getBoldSmallAppFont(){ return getSmallAppFont().deriveFont(Font.BOLD); }

    public static Font getBoldMediumAppFont(){ return getMediumAppFont().deriveFont(Font.BOLD); }

    public static Font getBoldLargeAppFont(){ return getLargeAppFont().deriveFont(Font.BOLD); }

    public static FlatLineBorder getLightBorderRadius(Color color , int thickness , int radius){ return new FlatLineBorder(new Insets(7, 15, 7, 15), color , thickness, radius); }

    public static FlatLineBorder getCommonLightBorderRadius(Color color){ return new FlatLineBorder(new Insets(30, 10, 30, 10), color , 1, 20); }

    public static boolean isValidDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) { return false; }
    }

}