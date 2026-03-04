package DAO;

import database.DatabaseConnection;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdentifiersDAO {

    private Connection connection;

    public IdentifiersDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    private Connection getConnection() {
        connection = DatabaseConnection.getInstance().getConnection();
        return connection;
    }

    public boolean updateIdentifiers(List<String> identifiers){

        String query = "UPDATE " + TableNames.ADMINISTRATOR + " SET " +
                TableFields.AdministratorFields.LOGIN + " = ?," +
                TableFields.AdministratorFields.PASSWORD + " = ?" +
                " WHERE id = 1";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){

            preparedStatement.setString(1 , identifiers.get(0));
            preparedStatement.setString(2, identifiers.get(1));

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une modification de données : " + e.getMessage());
        }
        return false;
    }

    public List<String> getIdentifiers(){
        List<String> identifiers = new ArrayList<>();
        String query = "SELECT * FROM " + TableNames.ADMINISTRATOR + " WHERE id = 1";

        try (Statement statement = getConnection().createStatement()){
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    String login = resultSet.getString(TableFields.AdministratorFields.LOGIN);
                    String password = resultSet.getString(TableFields.AdministratorFields.PASSWORD);
                    identifiers.add(login);
                    identifiers.add(password);
                    return identifiers;
                }
            }
        }
        catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors de la requête getAdminIdentifiers : " + e.getMessage());
        }

        return new ArrayList<>();
    }

}
