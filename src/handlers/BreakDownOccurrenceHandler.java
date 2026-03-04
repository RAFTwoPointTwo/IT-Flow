package handlers;

import DAO.BreakDownOccurrenceDAO;
import DAO.MaterialDAO;
import models.BreakdownOccurrence;
import models.Material;

import java.util.ArrayList;
import java.util.List;

public class BreakDownOccurrenceHandler {

    private final BreakDownOccurrenceDAO breakDownOccurrenceDAO;
    private final MaterialDAO materialDAO;

    public BreakDownOccurrenceHandler(){
        breakDownOccurrenceDAO = new BreakDownOccurrenceDAO();
        materialDAO = new MaterialDAO();
    }

    public boolean saveBreakDownOccurrence(BreakdownOccurrence breakdownOccurrence){

        if (breakdownOccurrence == null) return false;
        if (breakdownOccurrence.getBreakdownDate() == null || breakdownOccurrence.getBreakdownDate().isBlank()) return false;

        Material brokenMaterial = materialDAO.getByID(breakdownOccurrence.getMaterialID());
        if (brokenMaterial == null) return false;

        boolean inserted = breakDownOccurrenceDAO.insertBreakDownOccurrence(breakdownOccurrence);

        if (inserted){
            brokenMaterial.setToBrokenDownState();
            brokenMaterial.setToUnavailableState();
            materialDAO.updateMaterial(brokenMaterial);
        }

        return inserted;

    }

    public List<BreakdownOccurrence> getAllBreakDownOccurrences(){
        List<BreakdownOccurrence> breakdownOccurrences = breakDownOccurrenceDAO.getAll();
        return (breakdownOccurrences == null) ? new ArrayList<>() : breakdownOccurrences;
    }

}
