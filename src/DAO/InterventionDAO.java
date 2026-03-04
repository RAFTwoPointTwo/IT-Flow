package DAO;

import models.Intervention;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterventionDAO extends BaseDAO<Intervention>{

    public InterventionDAO() {
        super(TableNames.INTERVENTIONS);
    }

    @Override
    protected List<Intervention> getEntitiesByResultSet(ResultSet resultSet) {

        List<Intervention> interventions = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Intervention intervention = new Intervention();
                intervention.setInterventionDate(resultSet.getString(TableFields.InterventionFields.INTERVENTION_DATE));
                intervention.setMaterialID(resultSet.getInt(TableFields.InterventionFields.MATERIAL_ID));
                intervention.setBreakdownID(resultSet.getInt(TableFields.InterventionFields.BREAKDOWN_ID));
                intervention.setTechnicianID(resultSet.getInt(TableFields.InterventionFields.TECHNICIAN_ID));
                interventions.add(intervention);
            }
            return interventions.isEmpty() ? null : interventions;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertIntervention(Intervention intervention) {
        String query = "INSERT INTO " + TableNames.INTERVENTIONS + " (" +
                TableFields.InterventionFields.INTERVENTION_DATE + "," +
                TableFields.InterventionFields.MATERIAL_ID + "," +
                TableFields.InterventionFields.BREAKDOWN_ID + "," +
                TableFields.InterventionFields.TECHNICIAN_ID +
                ")" +
                " VALUES (?, ?, ?, ?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1, intervention.getInterventionDate());
            preparedStatement.setInt(2, intervention.getMaterialID());
            preparedStatement.setInt(3, intervention.getBreakdownID());
            preparedStatement.setInt(4, intervention.getTechnicianID());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
