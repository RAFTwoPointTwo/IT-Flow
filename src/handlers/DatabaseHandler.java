package handlers;

import DAO.DatabaseStatusDAO;
import utilities.GlobalUtilities;

import java.sql.Statement;

public class DatabaseHandler {

    private DatabaseHandler(){ }

    public static boolean isDBInitialized(Statement statement){
        int dbStatus = DatabaseStatusDAO.getDBInitializedStatus(statement);
        if (dbStatus == GlobalUtilities.getDBErrorFlag()){
            System.err.println("!! Une erreur est subvenue lors de la vérification du statut d'initialisation de la base de données !!");
            return false;
        }
        return GlobalUtilities.isDBTrueStatusFlag(dbStatus);
    }

    public static void updateDBInitializedStatus(Statement statement){ DatabaseStatusDAO.setDBInitializedStatusToInitialized(statement); }

    public static boolean isDBAdministrated(Statement statement){
        int dbStatus = DatabaseStatusDAO.getDBAdministratedStatus(statement);
        if (dbStatus == GlobalUtilities.getDBErrorFlag()){
            System.err.println("!! Une erreur est subvenue lors de la vérification du statut d'administration de la base de données !!");
            return false;
        }
        return GlobalUtilities.isDBTrueStatusFlag(dbStatus);
    }

    public static void updateDBAdministratedStatus(Statement statement){ DatabaseStatusDAO.setDBAdministratedStatusToAdministrated(statement); }

}
