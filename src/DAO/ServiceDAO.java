package DAO;

import models.Service;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends BaseDAO<Service>{

    public ServiceDAO() {
        super(TableNames.SERVICES);
    }

    @Override
    protected List<Service> getEntitiesByResultSet(ResultSet resultSet) {

        List<Service> services = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Service service = new Service();
                service.setId(resultSet.getInt(TableFields.ServiceFields.ID));
                service.setName(resultSet.getString(TableFields.ServiceFields.NAME));
                service.setDescription(resultSet.getString(TableFields.ServiceFields.DESCRIPTION));
                services.add(service);
            }
            return services.isEmpty() ? null : services;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertService(Service service) {

        String query = "INSERT INTO " + TableNames.SERVICES + " (" +
                TableFields.ServiceFields.NAME + "," +
                TableFields.ServiceFields.DESCRIPTION +
                ")" +
                " VALUES (?,?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1,service.getName());
            preparedStatement.setString(2, service.getDescription());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    service.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
