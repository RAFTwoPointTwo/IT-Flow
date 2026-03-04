package handlers;

import DAO.ComputerPackDAO;
import DAO.MaterialDAO;
import DAO.MaterialTypeDAO;
import models.ComputerPack;
import models.Material;
import models.MaterialType;

import java.util.ArrayList;
import java.util.List;

public class MaterialHandler {

    private final MaterialDAO materialDAO;
    private final MaterialTypeDAO materialTypeDAO;
    private final ComputerPackDAO computerPackDAO;

    public MaterialHandler() {
        materialDAO = new MaterialDAO();
        materialTypeDAO = new MaterialTypeDAO();
        computerPackDAO = new ComputerPackDAO();
    }

    public boolean saveMaterial(Material material) {

        if (material == null) return false;
        if (material.isFieldsNullOrBlank()) return false;

        MaterialType correspondingType = materialTypeDAO.getByID(material.getTypeID());
        ComputerPack correspondingPack = computerPackDAO.getByID(material.getPackID());
        if (correspondingPack == null || correspondingType == null) return false;

        return materialDAO.insertMaterial(material);

    }

    public boolean updateMaterial(Material material) {

        if (material == null || material.isFieldsNullOrBlank()) return false;

        MaterialType correspondingType = materialTypeDAO.getByID(material.getTypeID());
        ComputerPack correspondingPack = computerPackDAO.getByID(material.getPackID());
        if (correspondingPack == null || correspondingType == null) return false;

        return materialDAO.updateMaterial(material);

    }

    public List<Material> getAllMaterials() {
        List<Material> materials = materialDAO.getAll();
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public Material getMaterialByID(int materialID) {

        Material materialToGet = materialDAO.getByID(materialID);

        if (materialToGet == null) return new Material("Materiel inconnu");

        return materialToGet;

    }

    public List<Material> getFunctionalMaterials() {
        List<Material> materials = materialDAO.getFunctionalMaterials();
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public List<Material> getBrokenDownMaterials() {
        List<Material> materials = materialDAO.getBrokenDownMaterials();
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public List<Material> getDefectiveMaterials() {
        List<Material> materials = materialDAO.getDefectiveMaterials();
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public List<Material> getAvailableMaterials() {
        List<Material> materials = materialDAO.getAvailableMaterials();
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public List<Material> getMaterialsByComputerPackID(int computerPackID) {
        List<Material> materials = materialDAO.getAllMaterialsByComputerPackID(computerPackID);
        return (materials == null) ? new ArrayList<>() : materials;
    }

    public int getMaterialCountInDB(){ return materialDAO.getCountInDB(); }

    public int getFunctionalMaterialsCountInDB(){ return getFunctionalMaterials().size(); }

    public int getDefectiveMaterialsCountInDB(){ return getDefectiveMaterials().size(); }

    public int getBrokenDownMaterialsCountInDB(){ return getBrokenDownMaterials().size(); }

}
