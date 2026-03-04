package handlers;

import DAO.TechnicianDAO;
import models.Technician;

public class TechnicianHandler {

    private final TechnicianDAO technicianDAO;

    public TechnicianHandler(){
        technicianDAO = new TechnicianDAO();
    }

    public boolean saveTechnician(Technician technician){

        if (technician == null) return false;
        if (technician.getLastName() == null || technician.getLastName().isBlank()) return false;
        if (technician.getFirstNames() == null || technician.getFirstNames().isBlank()) return false;

        return technicianDAO.insertTechnician(technician);

    }

    public Technician getTechnicianByID(int technicianID){

        Technician technicianToGet = technicianDAO.getByID(technicianID);

        if (technicianToGet == null) return new Technician("Technicien Inconnu" , "Technicien Inconnu");

        return technicianToGet;

    }

}