package database;

import handlers.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseStatus {

    private DatabaseStatus(){ }

    private static boolean initialized;
    private static boolean administrated;

    public static boolean isIsInitialized(Statement stmt) {
        initialized = DatabaseHandler.isDBInitialized(stmt);
        return initialized;
    }

    public static void setStatusToInitialized(Statement stmt){
        DatabaseHandler.updateDBInitializedStatus(stmt);
        initialized = true;
    }

    private static boolean isDatabaseAdministrated(Statement stmt){
        administrated = DatabaseHandler.isDBAdministrated(stmt);
        return administrated;
    }

    private static void setStatusToAdministrated(Statement stmt){
        DatabaseHandler.updateDBAdministratedStatus(stmt);
        administrated = true;
    }

    public static boolean isAppAdministrated() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        if (connection == null) return false;
        try (Statement stmt = connection.createStatement()) {
            return isDatabaseAdministrated(stmt);
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la recherche du statut d'administration : " + e.getMessage());
            return false;
        }
    }

    public static void makeAppAdministrated(){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        if (connection == null) return;
        try (Statement stmt = connection.createStatement()) {
            setStatusToAdministrated(stmt);
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la mise à jour du statut d'administration : " + e.getMessage());
        }
    }



}
