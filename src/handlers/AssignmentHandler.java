package handlers;

import DAO.AssignmentDAO;
import DAO.MaterialDAO;
import models.Assignment;
import models.Material;

import java.util.ArrayList;
import java.util.List;

public class AssignmentHandler {

    private final AssignmentDAO assignmentDAO;
    private final MaterialDAO materialDAO;

    public AssignmentHandler() {
        assignmentDAO = new AssignmentDAO();
        materialDAO = new MaterialDAO();
    }

    public boolean saveAssignment(Assignment assignment){

        if (assignment == null) return false;

        Material materialToAssign = materialDAO.getByID(assignment.getMaterialID());
        if (materialToAssign == null || !materialToAssign.isAvailable() || materialToAssign.isBrokenDown()) return false;

        boolean inserted = assignmentDAO.insertAssignment(assignment);

        if (inserted){
            materialToAssign.setToUnavailableState();
            materialDAO.updateMaterial(materialToAssign);
        }

        return inserted;

    }

    public boolean endAssignment(Assignment assignment){

        if (assignment == null || assignment.getEndDate() != null) return false;

        Material materialAssigned = materialDAO.getByID(assignment.getMaterialID());
        if (materialAssigned == null) return false;

        assignment.updateEndDate();

        boolean updated = assignmentDAO.updateAssignment(assignment);

        if (updated){
            materialAssigned.setToAvailableState();
            materialDAO.updateMaterial(materialAssigned);
        }

        return updated;

    }

    public List<Assignment> getAllAssignments(){
        List<Assignment> assignments = assignmentDAO.getAll();
        return (assignments == null) ? new ArrayList<>() : assignments;
    }

    public List<Assignment> getActiveAssignments(){
        List<Assignment> assignments = assignmentDAO.getActiveAssignments();
        return (assignments == null) ? new ArrayList<>() : assignments;
    }

    public List<Assignment> getTerminatedAssignments(){
        List<Assignment> assignments = assignmentDAO.getTerminatedAssignments();
        return (assignments == null) ? new ArrayList<>() : assignments;
    }

    public List<Assignment> getAssignmentsByMaterialID(int materialID){
        List<Assignment> assignments = assignmentDAO.getAllAssignmentsByMaterialID(materialID);
        return (assignments == null) ? new ArrayList<>() : assignments;
    }

    public int getAssignmentCountInDB(){ return assignmentDAO.getCountInDB(); }

    public int getTerminatedAssignmentsCountInDB() { return getTerminatedAssignments().size(); }

    public int getActiveAssignmentsCountInDB() { return getActiveAssignments().size(); }

}