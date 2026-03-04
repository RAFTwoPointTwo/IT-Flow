package handlers;

import DAO.MaterialTypeDAO;
import models.MaterialType;

import java.util.ArrayList;
import java.util.List;

public class MaterialTypeHandler {

    private final MaterialTypeDAO materialTypeDAO;

    public MaterialTypeHandler(){
        materialTypeDAO = new MaterialTypeDAO();
    }

    public boolean saveMaterialType(MaterialType materialType){

        if (materialType == null) return false;
        if (materialType.getName() == null || materialType.getName().isBlank()) return false;

        return materialTypeDAO.insertMaterialType(materialType);

    }

    public MaterialType getMaterialTypeByID(int materialTypeID){

        MaterialType materialTypeToGet = materialTypeDAO.getByID(materialTypeID);

        if (materialTypeToGet == null) return new MaterialType("Type Materiel Inconnu");

        return materialTypeToGet;

    }

    public List<MaterialType> getAllMaterialTypes(){
        List<MaterialType> materialTypes = materialTypeDAO.getAll();
        return (materialTypes == null) ? new ArrayList<>() : materialTypes;
    }

    public int getMaterialTypesCountInDB(){ return materialTypeDAO.getCountInDB(); }

}