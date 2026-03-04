package models;

public class BreakdownOccurrence {

    private String breakdownDate;
    private int materialID;
    private int breakdownID;

    public BreakdownOccurrence(){ }

    public BreakdownOccurrence(int materialID, int breakdownID, String breakDownDate) {
        this.materialID = materialID;
        this.breakdownID = breakdownID;
        this.breakdownDate = breakDownDate;
    }

    public String getBreakdownDate() { return breakdownDate; }

    public int getMaterialID() { return materialID; }

    public int getBreakdownID() { return breakdownID; }

    public void setBreakdownDate(String breakdownDate) { this.breakdownDate = breakdownDate; }

    public void setMaterialID(int materialID) { this.materialID = materialID; }

    public void setBreakdownID(int breakdownID) { this.breakdownID = breakdownID; }

}
