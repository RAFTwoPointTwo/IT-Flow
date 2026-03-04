package DAO;

import models.Assignment;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO extends BaseDAO<Assignment> {

    public AssignmentDAO() {
        super(TableNames.ASSIGNMENTS);
    }

    @Override
    public List<Assignment> getEntitiesByResultSet(ResultSet resultSet) {

        List<Assignment> assignmentsList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Assignment assignment = new Assignment();
                assignment.setStartDate(resultSet.getString(TableFields.AssignmentFields.START_DATE));
                assignment.setEndDate(resultSet.getString(TableFields.AssignmentFields.END_DATE));
                assignment.setMaterialID(resultSet.getInt(TableFields.AssignmentFields.MATERIAL_ID));
                assignment.setPersonID(resultSet.getInt(TableFields.AssignmentFields.PERSON_ID));
                assignmentsList.add(assignment);
            }
            return assignmentsList.isEmpty() ? null : assignmentsList;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertAssignment(Assignment assignment) {
        String query = "INSERT INTO " + TableNames.ASSIGNMENTS + " (" +
                TableFields.AssignmentFields.START_DATE + "," +
                TableFields.AssignmentFields.END_DATE + "," +
                TableFields.AssignmentFields.MATERIAL_ID + "," +
                TableFields.AssignmentFields.PERSON_ID +
                ")" +
                " VALUES (?, ?, ?, ?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1, assignment.getStartDate());
            preparedStatement.setString(2, assignment.getEndDate());
            preparedStatement.setInt(3, assignment.getMaterialID());
            preparedStatement.setInt(4, assignment.getPersonID());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

    public boolean updateAssignment(Assignment assignment) {

        String query = "UPDATE " + TableNames.ASSIGNMENTS + " SET " +
                TableFields.AssignmentFields.END_DATE + " = ?" +
                " WHERE " + TableFields.AssignmentFields.MATERIAL_ID + " = ? AND " +
                TableFields.AssignmentFields.PERSON_ID + " = ? AND " +
                TableFields.AssignmentFields.END_DATE + " IS NULL";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1, assignment.getEndDate());
            preparedStatement.setInt(2, assignment.getMaterialID());
            preparedStatement.setInt(3, assignment.getPersonID());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une modification des données : " + e.getMessage());
        }
        return false;
    }

    public List<Assignment> getAllAssignmentsByMaterialID(int materialID) {

        String query = "SELECT * FROM " + TableNames.ASSIGNMENTS + " WHERE " + TableFields.AssignmentFields.MATERIAL_ID + " = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, materialID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Assignment> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getAssignmentsByMaterialID sur la table " + TableNames.ASSIGNMENTS + " : " + e.getMessage());
        }

        return null;
    }

    public List<Assignment> getActiveAssignments() {

        String query = "SELECT * FROM " + TableNames.ASSIGNMENTS + " WHERE " + TableFields.AssignmentFields.END_DATE + " IS NULL";

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<Assignment> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getActiveAssignments sur la table " + TableNames.ASSIGNMENTS + " : " + e.getMessage());
        }

        return null;
    }

    public List<Assignment> getTerminatedAssignments() {

        String query = "SELECT * FROM " + TableNames.ASSIGNMENTS + " WHERE " + TableFields.AssignmentFields.END_DATE + " IS NOT NULL";

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<Assignment> results = getEntitiesByResultSet(resultSet);
                if (results == null) return null;
                return results.isEmpty() ? null : results;
            }
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une requête getTerminatedAssignments sur la table " + TableNames.ASSIGNMENTS + " : " + e.getMessage());
        }

        return null;
    }

}
