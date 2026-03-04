package models;

import utilities.GlobalUtilities;

public class Assignment {

    private String startDate;
    private String endDate;
    private int materialID;
    private int personID;

    public Assignment(){ }

    public Assignment(int materialID, int personID) {
        this.startDate = GlobalUtilities.getCurrentDate();
        this.endDate = null;
        this.materialID = materialID;
        this.personID = personID;
    }

    public String getStartDate() { return startDate; }

    public String getEndDate() { return endDate; }

    public int getMaterialID() { return materialID; }

    public int getPersonID() { return personID; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public void updateEndDate(){ endDate = GlobalUtilities.getCurrentDate(); }

    public void setMaterialID(int materialID) { this.materialID = materialID; }

    public void setPersonID(int personID) { this.personID = personID; }

}
