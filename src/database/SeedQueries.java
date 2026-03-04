package database;

import utilities.GlobalUtilities;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeedQueries {

    private SeedQueries(){ }

    public static void createTables(Statement stmt){

        List<String> queries = new ArrayList<>();

        queries.add("SET FOREIGN_KEY_CHECKS = 1;");

        String createServicesTable = "CREATE TABLE IF NOT EXISTS " + TableNames.SERVICES + " (" +
                TableFields.ServiceFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.ServiceFields.NAME + " VARCHAR(255) NOT NULL, " +
                TableFields.ServiceFields.DESCRIPTION + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createServicesTable);

        String createTechniciansTable = "CREATE TABLE IF NOT EXISTS " + TableNames.TECHNICIANS + " (" +
                TableFields.TechnicianFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.TechnicianFields.LAST_NAME + " VARCHAR(255) NOT NULL, " +
                TableFields.TechnicianFields.FIRST_NAMES + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createTechniciansTable);

        String createBreakdownsTable = "CREATE TABLE IF NOT EXISTS " + TableNames.BREAKDOWNS + " (" +
                TableFields.BreakdownFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.BreakdownFields.DESCRIPTION + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createBreakdownsTable);

        String createComputerPacksTable = "CREATE TABLE IF NOT EXISTS " + TableNames.COMPUTER_PACKS + " (" +
                TableFields.ComputerPackFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.ComputerPackFields.NAME + " VARCHAR(255) NOT NULL, " +
                TableFields.ComputerPackFields.DESCRIPTION + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createComputerPacksTable);

        String createMaterialTypesTable = "CREATE TABLE IF NOT EXISTS " + TableNames.MATERIAL_TYPES + " (" +
                TableFields.MaterialTypeFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.MaterialTypeFields.NAME + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createMaterialTypesTable);

        String createIdentifiersTable = "CREATE TABLE IF NOT EXISTS " + TableNames.ADMINISTRATOR + " (" +
                TableFields.AdministratorFields.ID + " INT PRIMARY KEY CHECK (" + TableFields.AdministratorFields.ID + " = 1), " +
                TableFields.AdministratorFields.LOGIN + " VARCHAR(255) NOT NULL, " +
                TableFields.AdministratorFields.PASSWORD + " VARCHAR(255) NOT NULL) ENGINE=InnoDB;";
        queries.add(createIdentifiersTable);

        String createDBStatusTable = "CREATE TABLE IF NOT EXISTS " + TableNames.DB_STATUS + " (" +
                TableFields.DatabaseStatusFields.ID + " INT PRIMARY KEY CHECK (" + TableFields.DatabaseStatusFields.ID + " = 1), " +
                TableFields.DatabaseStatusFields.DB_INITIALIZED + " INT NOT NULL, " +
                TableFields.DatabaseStatusFields.DB_ADMINISTRATED + " INT NOT NULL) ENGINE=InnoDB;";
        queries.add(createDBStatusTable);

        String createPersonsTable = "CREATE TABLE IF NOT EXISTS " + TableNames.PERSONS + " (" +
                TableFields.PersonFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.PersonFields.LAST_NAME + " VARCHAR(255) NOT NULL, " +
                TableFields.PersonFields.FIRST_NAMES + " VARCHAR(255) NOT NULL, " +
                TableFields.PersonFields.FUNCTION + " VARCHAR(255) NOT NULL, " +
                TableFields.PersonFields.SERVICE_ID + " INT NOT NULL, " +
                "FOREIGN KEY (" + TableFields.PersonFields.SERVICE_ID + ") REFERENCES " + TableNames.SERVICES + "("+ TableFields.ServiceFields.ID + ")) ENGINE=InnoDB;";
        queries.add(createPersonsTable);

        String createMaterialsTable = "CREATE TABLE IF NOT EXISTS " + TableNames.MATERIALS + " (" +
                TableFields.MaterialFields.ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                TableFields.MaterialFields.DESIGNATION + " VARCHAR(255) NOT NULL, " +
                TableFields.MaterialFields.SPECIFICATION + " VARCHAR(255) NOT NULL, " +
                TableFields.MaterialFields.BRAND + " VARCHAR(255) NOT NULL, " +
                TableFields.MaterialFields.SERIAL_NUMBER + " VARCHAR(255) NOT NULL, " +
                TableFields.MaterialFields.ACQUISITION_DATE + " VARCHAR(255) NOT NULL, " +
                TableFields.MaterialFields.STATE + " VARCHAR(255) NOT NULL," +
                TableFields.MaterialFields.IS_AVAILABLE + " INT NOT NULL," +
                TableFields.MaterialFields.TYPE_ID + " INT NOT NULL," +
                TableFields.MaterialFields.PACK_ID + " INT NOT NULL," +
                "FOREIGN KEY (" + TableFields.MaterialFields.TYPE_ID + ") REFERENCES " + TableNames.MATERIAL_TYPES + "("+ TableFields.MaterialTypeFields.ID + "), " +
                "FOREIGN KEY (" + TableFields.MaterialFields.PACK_ID + ") REFERENCES " + TableNames.COMPUTER_PACKS + "("+ TableFields.ComputerPackFields.ID + ")) ENGINE=InnoDB;";
        queries.add(createMaterialsTable);

        String createAssignmentsTable = "CREATE TABLE IF NOT EXISTS " + TableNames.ASSIGNMENTS + " (" +
                TableFields.AssignmentFields.START_DATE + " VARCHAR(255) NOT NULL, " +
                TableFields.AssignmentFields.END_DATE + " VARCHAR(255), " +
                TableFields.AssignmentFields.PERSON_ID + " INT NOT NULL, " +
                TableFields.AssignmentFields.MATERIAL_ID + " INT NOT NULL, " +
                "FOREIGN KEY (" + TableFields.AssignmentFields.PERSON_ID + ") REFERENCES " + TableNames.PERSONS + "("+ TableFields.PersonFields.ID + "), " +
                "FOREIGN KEY (" + TableFields.AssignmentFields.MATERIAL_ID + ") REFERENCES " + TableNames.MATERIALS + "("+ TableFields.MaterialFields.ID + ")) ENGINE=InnoDB;";
        queries.add(createAssignmentsTable);

        String createInterventionsTable = "CREATE TABLE IF NOT EXISTS " + TableNames.INTERVENTIONS + " (" +
                TableFields.InterventionFields.INTERVENTION_DATE + " VARCHAR(255) NOT NULL, " +
                TableFields.InterventionFields.MATERIAL_ID + " INT NOT NULL, " +
                TableFields.InterventionFields.BREAKDOWN_ID + " INT NOT NULL, " +
                TableFields.InterventionFields.TECHNICIAN_ID + " INT NOT NULL, " +
                "FOREIGN KEY (" + TableFields.InterventionFields.MATERIAL_ID + ") REFERENCES " + TableNames.MATERIALS + "("+ TableFields.MaterialFields.ID + "), " +
                "FOREIGN KEY (" + TableFields.InterventionFields.BREAKDOWN_ID + ") REFERENCES " + TableNames.BREAKDOWNS + "("+ TableFields.BreakdownFields.ID + "), " +
                "FOREIGN KEY (" + TableFields.InterventionFields.TECHNICIAN_ID + ") REFERENCES " + TableNames.TECHNICIANS + "("+ TableFields.TechnicianFields.ID + ")) ENGINE=InnoDB;";
        queries.add(createInterventionsTable);

        String createBreakdownOccurrencesTable = "CREATE TABLE IF NOT EXISTS " + TableNames.BREAKDOWN_OCCURRENCES + " (" +
                TableFields.BreakdownOccurrenceFields.BREAKDOWN_DATE + " VARCHAR(100) NOT NULL, " +
                TableFields.BreakdownOccurrenceFields.MATERIAL_ID + " INT NOT NULL, " +
                TableFields.BreakdownOccurrenceFields.BREAKDOWN_ID + " INT NOT NULL, " +
                "FOREIGN KEY (" + TableFields.BreakdownOccurrenceFields.MATERIAL_ID + ") REFERENCES " + TableNames.MATERIALS + "("+ TableFields.MaterialFields.ID + "), " +
                "FOREIGN KEY (" + TableFields.BreakdownOccurrenceFields.BREAKDOWN_ID + ") REFERENCES " + TableNames.BREAKDOWNS + "("+ TableFields.BreakdownFields.ID + ")) ENGINE=InnoDB;";
        queries.add(createBreakdownOccurrencesTable);

        try{

            for (String query : queries) stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la création des tables : " + e.getMessage());
        }

    }

    public static boolean insertBaseData(Statement stmt){

        List<String> queries = new ArrayList<>();

        String insertMaterialTypes = "INSERT INTO " + TableNames.MATERIAL_TYPES + " (" + TableFields.MaterialTypeFields.NAME + ")" +
                " VALUES " +
                "('Ordinateur')," +
                "('Imprimante')," +
                "('Scanner')," +
                "('Routeur')," +
                "('Clavier')," +
                "('Souris')," +
                "('Video Projecteur')," +
                "('Haut-parleur')," +
                "('Disque dur')," +
                "('Unité Centrale')";
        queries.add(insertMaterialTypes);

        String insertComputerPack = "INSERT INTO " + TableNames.COMPUTER_PACKS + " (" +
                TableFields.ComputerPackFields.NAME + "," +
                TableFields.ComputerPackFields.DESCRIPTION +
                ")" +
                " VALUES ('Pack gestion administrative','Pack de gestion de l''administration')";
        queries.add(insertComputerPack);

        String insertMaterial1 = "INSERT INTO " + TableNames.MATERIALS + " (" +
                TableFields.MaterialFields.TYPE_ID + "," +
                TableFields.MaterialFields.PACK_ID + "," +
                TableFields.MaterialFields.DESIGNATION + "," +
                TableFields.MaterialFields.SPECIFICATION + "," +
                TableFields.MaterialFields.BRAND + "," +
                TableFields.MaterialFields.SERIAL_NUMBER + "," +
                TableFields.MaterialFields.ACQUISITION_DATE + "," +
                TableFields.MaterialFields.STATE + "," +
                TableFields.MaterialFields.IS_AVAILABLE +
                ")" +
                " VALUES (1, 1, 'ASUS Zenbook 14 OLED', 'Ryzen 7, 16GB RAM, 512GB SSD', 'ASUS', 'SN-ASUS-998877', '15-05-2024', 'Fonctionnel', 1)";
        queries.add(insertMaterial1);

        String insertMaterial2 = "INSERT INTO " + TableNames.MATERIALS + " (" +
                TableFields.MaterialFields.TYPE_ID + "," +
                TableFields.MaterialFields.PACK_ID + "," +
                TableFields.MaterialFields.DESIGNATION + "," +
                TableFields.MaterialFields.SPECIFICATION + "," +
                TableFields.MaterialFields.BRAND + "," +
                TableFields.MaterialFields.SERIAL_NUMBER + "," +
                TableFields.MaterialFields.ACQUISITION_DATE + "," +
                TableFields.MaterialFields.STATE + "," +
                TableFields.MaterialFields.IS_AVAILABLE +
                ")" +
                " VALUES (6, 1, 'MX Master 3S', '8000 DPI, Bluetooth, Silent Click', 'Logitech', 'SN-LOGI-112233', '02-06-2024', 'Fonctionnel', 1)";
        queries.add(insertMaterial2);

        String insertMaterial3 = "INSERT INTO " + TableNames.MATERIALS + " (" +
                TableFields.MaterialFields.TYPE_ID + "," +
                TableFields.MaterialFields.PACK_ID + "," +
                TableFields.MaterialFields.DESIGNATION + "," +
                TableFields.MaterialFields.SPECIFICATION + "," +
                TableFields.MaterialFields.BRAND + "," +
                TableFields.MaterialFields.SERIAL_NUMBER + "," +
                TableFields.MaterialFields.ACQUISITION_DATE + "," +
                TableFields.MaterialFields.STATE + "," +
                TableFields.MaterialFields.IS_AVAILABLE +
                ")" +
                " VALUES (2, 1, 'LaserJet Pro MFP M283fdw', 'Color, Wi-Fi, 21 ppm, Duplex', 'HP', 'SN-HP-445566', '20-01-2023', 'Fonctionnel', 1)";
        queries.add(insertMaterial3);

        try{
            for (String query : queries) stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de l'insertion des données : " + e.getMessage());
        }
        return false;
    }

    public static void insertBaseDBStatus(Statement stmt){
        String query = "INSERT IGNORE INTO " + TableNames.DB_STATUS + " (" +
                TableFields.DatabaseStatusFields.ID + "," +
                TableFields.DatabaseStatusFields.DB_INITIALIZED + "," +
                TableFields.DatabaseStatusFields.DB_ADMINISTRATED +
                ")" +
                " VALUES (1 , " + GlobalUtilities.getDBFalseStatusFlag() + ", " + GlobalUtilities.getDBFalseStatusFlag() + ") ";

        try { stmt.executeUpdate(query); }
        catch (SQLException e) {
            System.err.println("!! [Insertion de Statut DB] Une erreur : " + e.getMessage());
        }
    }

    public static void insertBaseIdentifiers(Statement stmt){
        String query = "INSERT IGNORE INTO " + TableNames.ADMINISTRATOR + " (" +
                TableFields.AdministratorFields.ID + "," +
                TableFields.AdministratorFields.LOGIN + "," +
                TableFields.AdministratorFields.PASSWORD +
                ")" +
                " VALUES (1 , 'admin' , 'password') ";

        try{ stmt.executeUpdate(query); }
        catch (SQLException e) {
            System.err.println("!! Une erreur lors de l'insertion admin : " + e.getMessage());
        }
    }

}