package DAO;

import models.ComputerPack;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComputerPackDAO extends BaseDAO<ComputerPack> {

    public ComputerPackDAO() {
        super(TableNames.COMPUTER_PACKS);
    }

    @Override
    public List<ComputerPack> getEntitiesByResultSet(ResultSet resultSet) {

        List<ComputerPack> computerPacksList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ComputerPack computerPack = new ComputerPack();
                computerPack.setId(resultSet.getInt(TableFields.ComputerPackFields.ID));
                computerPack.setName(resultSet.getString(TableFields.ComputerPackFields.NAME));
                computerPack.setDescription(resultSet.getString(TableFields.ComputerPackFields.DESCRIPTION));
                computerPacksList.add(computerPack);
            }
            return computerPacksList.isEmpty() ? null : computerPacksList;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertComputerPack(ComputerPack computerPack) {

        String query = "INSERT INTO " + TableNames.COMPUTER_PACKS + " (" +
                TableFields.ComputerPackFields.NAME + "," +
                TableFields.ComputerPackFields.DESCRIPTION +
                ")" +
                " VALUES (?,?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1,computerPack.getName());
            preparedStatement.setString(2, computerPack.getDescription());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    computerPack.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
