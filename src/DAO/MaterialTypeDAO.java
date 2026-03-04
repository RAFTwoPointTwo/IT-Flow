package DAO;

import models.MaterialType;
import utilities.TableFields;
import utilities.TableNames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaterialTypeDAO extends BaseDAO<MaterialType> {

    public MaterialTypeDAO() {
        super(TableNames.MATERIAL_TYPES);
    }

    @Override
    public List<MaterialType> getEntitiesByResultSet(ResultSet resultSet) {

        List<MaterialType> materialTypesList = new ArrayList<>();

        try {
            while(resultSet.next()) {
                MaterialType materialType = new MaterialType();
                materialType.setId(resultSet.getInt(TableFields.MaterialTypeFields.ID));
                materialType.setName(resultSet.getString(TableFields.MaterialTypeFields.NAME));
                materialTypesList.add(materialType);
            }

            return materialTypesList.isEmpty() ? null : materialTypesList;

        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une collecte de données : " + e.getMessage());
        }
        return null;
    }

    public boolean insertMaterialType(MaterialType materialType) {

        String query = "INSERT INTO " + TableNames.MATERIAL_TYPES +
                " (" + TableFields.MaterialTypeFields.NAME + ")" +
                " VALUES (?) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, materialType.getName());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    materialType.setId(generatedID);
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("!! Une erreur est subvenue lors d'une insertion de données : " + e.getMessage());
        }
        return false;
    }

}
