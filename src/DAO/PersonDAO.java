package DAO;

import models.Person;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends BaseDAO<Person> {

    public PersonDAO() {
        super(TableNames.PERSONS);
    }

    @Override
    protected List<Person> getEntitiesByResultSet(ResultSet resultSet) {

        List<Person> personsList = new ArrayList<>();

        try{
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(TableFields.PersonFields.ID));
                person.setLastName(resultSet.getString(TableFields.PersonFields.LAST_NAME));
                person.setFirstNames(resultSet.getString(TableFields.PersonFields.FIRST_NAMES));
                person.setFunction(resultSet.getString(TableFields.PersonFields.FUNCTION));
                person.setServiceID(resultSet.getInt(TableFields.PersonFields.SERVICE_ID));
                personsList.add(person);
            }
            return personsList.isEmpty() ? null : personsList;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;

    }

    public boolean insertPerson(Person person) {
        String query = "INSERT INTO " + TableNames.PERSONS + " (" +
                TableFields.PersonFields.FIRST_NAMES + "," +
                TableFields.PersonFields.LAST_NAME + "," +
                TableFields.PersonFields.FUNCTION + "," +
                TableFields.PersonFields.SERVICE_ID +
                ")" +
                " VALUES (?, ?, ?, ?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, person.getFirstNames());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getFunction());
            preparedStatement.setInt(4, person.getServiceID());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    person.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }


}
