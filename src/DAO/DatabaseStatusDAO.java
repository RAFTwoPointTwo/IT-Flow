package DAO;

import utilities.GlobalUtilities;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.*;

public class DatabaseStatusDAO {

    private DatabaseStatusDAO(){ }

    public static int getDBInitializedStatus(Statement stmt) {

        String query = "SELECT * FROM " + TableNames.DB_STATUS + " WHERE id = 1";

        try (ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(TableFields.DatabaseStatusFields.DB_INITIALIZED);
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la requête getDBInitializedStatus sur la table " + TableNames.DB_STATUS + " : " + e.getMessage());
        }

        return GlobalUtilities.getDBErrorFlag();

    }

    public static void setDBInitializedStatusToInitialized(Statement stmt){

        try {

            String query = "UPDATE " + TableNames.DB_STATUS +
                    " SET " + TableFields.DatabaseStatusFields.DB_INITIALIZED + " = " + GlobalUtilities.getDBTrueStatusFlag() +
                    " WHERE " + TableFields.DatabaseStatusFields.ID + " = 1";

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une mise à jour du statut d'initialisation de la base de données : " + e.getMessage());
        }

    }

    public static int getDBAdministratedStatus(Statement stmt){

        String query = "SELECT * FROM " + TableNames.DB_STATUS + " WHERE id = 1";

        try (ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(TableFields.DatabaseStatusFields.DB_ADMINISTRATED);
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la requête getDBAdministratedStatus sur la table " + TableNames.DB_STATUS + " : " + e.getMessage());
        }

        return GlobalUtilities.getDBErrorFlag();

    }

    public static void setDBAdministratedStatusToAdministrated(Statement stmt){

        try {

            String query = "UPDATE " + TableNames.DB_STATUS +
                    " SET " + TableFields.DatabaseStatusFields.DB_ADMINISTRATED + " = " + GlobalUtilities.getDBTrueStatusFlag() +
                    " WHERE " + TableFields.DatabaseStatusFields.ID + " = 1";

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une mise à jour du statut d'administration de la base de données : " + e.getMessage());
        }

    }

}