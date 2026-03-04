package DAO;

import models.Material;
import utilities.GlobalUtilities;
import utilities.MaterialStates;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO extends BaseDAO<Material> {

    public MaterialDAO() {
        super(TableNames.MATERIALS);
    }

    @Override
    public List<Material> getEntitiesByResultSet(ResultSet resultSet) {

        List<Material> materialsList = new ArrayList<>();

        try {
            while(resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt(TableFields.MaterialFields.ID));
                material.setDesignation(resultSet.getString(TableFields.MaterialFields.DESIGNATION));
                material.setSpecification(resultSet.getString(TableFields.MaterialFields.SPECIFICATION));
                material.setBrand(resultSet.getString(TableFields.MaterialFields.BRAND));
                material.setSerialNumber(resultSet.getString(TableFields.MaterialFields.SERIAL_NUMBER));
                material.setAcquisitionDate(resultSet.getString(TableFields.MaterialFields.ACQUISITION_DATE));
                material.setState(resultSet.getString(TableFields.MaterialFields.STATE));
                material.setAvailable(GlobalUtilities.getBooleanFlagByIntFlag(resultSet.getInt(TableFields.MaterialFields.IS_AVAILABLE)));
                material.setTypeID(resultSet.getInt(TableFields.MaterialFields.TYPE_ID));
                material.setPackID(resultSet.getInt(TableFields.MaterialFields.PACK_ID));
                materialsList.add(material);
            }
            return materialsList.isEmpty() ? null : materialsList;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertMaterial(Material material) {
        String query = "INSERT INTO " + TableNames.MATERIALS + " (" +
                TableFields.MaterialFields.DESIGNATION + "," +
                TableFields.MaterialFields.SPECIFICATION + "," +
                TableFields.MaterialFields.BRAND + "," +
                TableFields.MaterialFields.SERIAL_NUMBER + "," +
                TableFields.MaterialFields.ACQUISITION_DATE + "," +
                TableFields.MaterialFields.STATE + "," +
                TableFields.MaterialFields.IS_AVAILABLE + "," +
                TableFields.MaterialFields.TYPE_ID + "," +
                TableFields.MaterialFields.PACK_ID +
                ")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, material.getDesignation());
            preparedStatement.setString(2, material.getSpecification());
            preparedStatement.setString(3, material.getBrand());
            preparedStatement.setString(4, material.getSerialNumber());
            preparedStatement.setString(5, material.getAcquisitionDate());
            preparedStatement.setString(6, material.getState());
            preparedStatement.setInt(7, GlobalUtilities.getIntFlagByBooleanFlag(material.isAvailable()));
            preparedStatement.setInt(8, material.getTypeID());
            preparedStatement.setInt(9, material.getPackID());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    material.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

    public boolean updateMaterial(Material material) {
        String query = "UPDATE " + TableNames.MATERIALS + " SET " +
                TableFields.MaterialFields.DESIGNATION + " = ?," +
                TableFields.MaterialFields.SPECIFICATION + " = ?," +
                TableFields.MaterialFields.BRAND + " = ?," +
                TableFields.MaterialFields.SERIAL_NUMBER + " = ?," +
                TableFields.MaterialFields.ACQUISITION_DATE + " = ?," +
                TableFields.MaterialFields.STATE + " = ?," +
                TableFields.MaterialFields.IS_AVAILABLE + " = ?," +
                TableFields.MaterialFields.TYPE_ID + " = ?," +
                TableFields.MaterialFields.PACK_ID + " = ?" +
                " WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1, material.getDesignation());
            preparedStatement.setString(2, material.getSpecification());
            preparedStatement.setString(3, material.getBrand());
            preparedStatement.setString(4, material.getSerialNumber());
            preparedStatement.setString(5, material.getAcquisitionDate());
            preparedStatement.setString(6, material.getState());
            preparedStatement.setInt(7, GlobalUtilities.getIntFlagByBooleanFlag(material.isAvailable()));
            preparedStatement.setInt(8, material.getTypeID());
            preparedStatement.setInt(9, material.getPackID());
            preparedStatement.setInt(10, material.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une modification de données : " + e.getMessage());
        }
        return false;
    }

    public List<Material> getAvailableMaterials() {
        String query = "SELECT * FROM " + TableNames.MATERIALS +
                " WHERE " + TableFields.MaterialFields.IS_AVAILABLE + " = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Material> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getAvailableMaterials sur la table " + TableNames.MATERIALS + " : " + e.getMessage());
        }
        return null;
    }

    public List<Material> getFunctionalMaterials() {
        String query = "SELECT * FROM " + TableNames.MATERIALS +
                " WHERE " + TableFields.MaterialFields.STATE + " = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, MaterialStates.FUNCTIONAL);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Material> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getFunctionalMaterials sur la table " + TableNames.MATERIALS + " : " + e.getMessage());
        }
        return null;
    }

    public List<Material> getBrokenDownMaterials() {
        String query = "SELECT * FROM " + TableNames.MATERIALS +
                " WHERE " + TableFields.MaterialFields.STATE + " = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, MaterialStates.BROKEN_DOWN);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Material> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getBrokenDownMaterials sur la table " + TableNames.MATERIALS + " : " + e.getMessage());
        }
        return null;
    }

    public List<Material> getDefectiveMaterials() {
        String query = "SELECT * FROM " + TableNames.MATERIALS +
                " WHERE " + TableFields.MaterialFields.STATE + " = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, MaterialStates.DEFECTIVE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Material> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getDefectiveMaterials sur la table " + TableNames.MATERIALS + " : " + e.getMessage());
        }
        return null;
    }

    public List<Material> getAllMaterialsByComputerPackID(int computerPackID) {
        String query = "SELECT * FROM " + TableNames.MATERIALS + " WHERE " + TableFields.MaterialFields.PACK_ID + " = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, computerPackID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Material> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getAllMaterialsByComputerPackID sur la table " + TableNames.MATERIALS + " : " + e.getMessage());
        }

        return null;
    }

}
