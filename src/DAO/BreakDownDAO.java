package DAO;

import models.BreakDown;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BreakDownDAO extends BaseDAO<BreakDown>{

    public BreakDownDAO() {
        super(TableNames.BREAKDOWNS);
    }

    @Override
    protected List<BreakDown> getEntitiesByResultSet(ResultSet resultSet) {

        List<BreakDown> breakDowns = new ArrayList<>();

        try {
            while (resultSet.next()) {
                BreakDown breakDown = new BreakDown();
                breakDown.setId(resultSet.getInt(TableFields.BreakdownFields.ID));
                breakDown.setDescription(resultSet.getString(TableFields.BreakdownFields.DESCRIPTION));
                breakDowns.add(breakDown);
            }
            return breakDowns.isEmpty() ? null : breakDowns;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertBreakDown(BreakDown breakDown) {

        String query = "INSERT INTO " + TableNames.BREAKDOWNS +
                " (" + TableFields.BreakdownFields.DESCRIPTION + ")" +
                " VALUES (?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, breakDown.getDescription());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    breakDown.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
