package DAO;

import models.Technician;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO extends BaseDAO<Technician>{

    public TechnicianDAO() {
        super(TableNames.TECHNICIANS);
    }

    @Override
    protected List<Technician> getEntitiesByResultSet(ResultSet resultSet) {

        List<Technician> technicians = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Technician technician = new Technician();
                technician.setId(resultSet.getInt(TableFields.TechnicianFields.ID));
                technician.setLastName(resultSet.getString(TableFields.TechnicianFields.LAST_NAME));
                technician.setFirstNames(resultSet.getString(TableFields.TechnicianFields.FIRST_NAMES));
                technicians.add(technician);
            }
            return technicians.isEmpty() ? null : technicians;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertTechnician(Technician technician) {

        String query = "INSERT INTO " + TableNames.TECHNICIANS + " (" +
                TableFields.TechnicianFields.LAST_NAME + "," +
                TableFields.TechnicianFields.FIRST_NAMES +
                ")" +
                " VALUES (?,?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1,technician.getLastName());
            preparedStatement.setString(2, technician.getFirstNames());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    technician.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
