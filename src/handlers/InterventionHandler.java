package handlers;

import DAO.InterventionDAO;
import DAO.MaterialDAO;
import models.Intervention;
import models.Material;

import java.util.ArrayList;
import java.util.List;

public class InterventionHandler {

    private final InterventionDAO interventionDAO;
    private final MaterialDAO materialDAO;

    public InterventionHandler(){
        interventionDAO = new InterventionDAO();
        materialDAO = new MaterialDAO();
    }

    public boolean saveIntervention(Intervention intervention){

        if (intervention == null) return false;
        if (intervention.getInterventionDate() == null || intervention.getInterventionDate().isBlank()) return false;

        Material repairedMaterial = materialDAO.getByID(intervention.getMaterialID());
        if (repairedMaterial == null) return false;
        boolean isBrokenMaterial = repairedMaterial.isBrokenDown();

        boolean inserted = interventionDAO.insertIntervention(intervention);

        if (inserted){
            repairedMaterial.setToFunctionalState();
            if (isBrokenMaterial) repairedMaterial.setToAvailableState();
            materialDAO.updateMaterial(repairedMaterial);
        }

        return inserted;

    }

    public List<Intervention> getAllInterventions(){
        List<Intervention> interventions = interventionDAO.getAll();
        return (interventions == null) ? new ArrayList<>() : interventions;
    }

    public int getInterventionCountInDB(){ return interventionDAO.getCountInDB(); }

}
