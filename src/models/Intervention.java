package models;

public class Intervention {

    private String interventionDate;
    private int materialID;
    private int breakdownID;
    private int technicianID;

    public Intervention(){ }

    public Intervention(String interventionDate, int materialID, int breakdownID, int technicianID) {
        this.interventionDate = interventionDate;
        this.materialID = materialID;
        this.breakdownID = breakdownID;
        this.technicianID = technicianID;
    }

    public String getInterventionDate() { return interventionDate; }

    public int getMaterialID() { return materialID; }

    public int getBreakdownID() { return breakdownID; }

    public int getTechnicianID() { return technicianID; }

    public void setInterventionDate(String interventionDate) { this.interventionDate = interventionDate; }

    public void setMaterialID(int materialID) { this.materialID = materialID; }

    public void setBreakdownID(int breakdownID) { this.breakdownID = breakdownID; }

    public void setTechnicianID(int technicianID) { this.technicianID = technicianID; }
}
