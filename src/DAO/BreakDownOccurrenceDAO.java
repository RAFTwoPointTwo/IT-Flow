package DAO;

import models.BreakdownOccurrence;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BreakDownOccurrenceDAO extends BaseDAO<BreakdownOccurrence>{

    public BreakDownOccurrenceDAO() {
        super(TableNames.BREAKDOWN_OCCURRENCES);
    }


    @Override
    protected List<BreakdownOccurrence> getEntitiesByResultSet(ResultSet resultSet) {

        List<BreakdownOccurrence> breakdownOccurrences = new ArrayList<>();

        try {
            while (resultSet.next()) {
                BreakdownOccurrence breakdownOccurrence = new BreakdownOccurrence();
                breakdownOccurrence.setBreakdownDate(resultSet.getString(TableFields.BreakdownOccurrenceFields.BREAKDOWN_DATE));
                breakdownOccurrence.setMaterialID(resultSet.getInt(TableFields.BreakdownOccurrenceFields.MATERIAL_ID));
                breakdownOccurrence.setBreakdownID(resultSet.getInt(TableFields.BreakdownOccurrenceFields.BREAKDOWN_ID));
                breakdownOccurrences.add(breakdownOccurrence);
            }
            return breakdownOccurrences.isEmpty() ? null : breakdownOccurrences;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertBreakDownOccurrence(BreakdownOccurrence breakdownOccurrence) {

        String query = "INSERT INTO " + TableNames.BREAKDOWN_OCCURRENCES + " (" +
                TableFields.BreakdownOccurrenceFields.BREAKDOWN_DATE + "," +
                TableFields.BreakdownOccurrenceFields.MATERIAL_ID + "," +
                TableFields.BreakdownOccurrenceFields.BREAKDOWN_ID +
                ")" +
                " VALUES (?,?,?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1, breakdownOccurrence.getBreakdownDate());
            preparedStatement.setInt(2, breakdownOccurrence.getMaterialID());
            preparedStatement.setInt(3, breakdownOccurrence.getBreakdownID());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
