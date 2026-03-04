package models;

import utilities.MaterialStates;

public class Material {

    private int id;
    private String designation;
    private String specification;
    private String brand;
    private String serialNumber;
    private String acquisitionDate;
    private String state;
    private boolean isAvailable;
    private int typeID;
    private int packID;

    public Material(){ }

    public Material(String designation, String specification, String brand, String serialNumber, String acquisitionDate, int typeID, int packID) {
        this.designation = designation;
        this.specification = specification;
        this.brand = brand;
        this.serialNumber = serialNumber;
        this.acquisitionDate = acquisitionDate;
        this.isAvailable = true;
        this.typeID = typeID;
        this.packID = packID;
        this.state = MaterialStates.FUNCTIONAL;
    }

    public Material(int id, String designation, String specification, String brand, String serialNumber, String acquisitionDate, String state, boolean isAvailable, int typeID, int packID) {
        this.id = id;
        this.designation = designation;
        this.specification = specification;
        this.brand = brand;
        this.serialNumber = serialNumber;
        this.acquisitionDate = acquisitionDate;
        this.state = state;
        this.isAvailable = isAvailable;
        this.typeID = typeID;
        this.packID = packID;
    }

    public Material(String designation) {
        this.designation = designation;
    }

    public int getId() { return id; }

    public String getDesignation() { return designation; }

    public String getSpecification() { return specification; }

    public String getBrand() { return brand; }

    public String getSerialNumber() { return serialNumber; }

    public String getAcquisitionDate() { return acquisitionDate; }

    public String getState() { return state; }

    public boolean isAvailable() { return isAvailable; }

    public int getTypeID() { return typeID; }

    public int getPackID() { return packID; }

    public void setId(int id) { this.id = id; }

    public void setState(String state){ this.state = (state == null) ? MaterialStates.FUNCTIONAL : state; }

    public void setToFunctionalState(){ setState(MaterialStates.FUNCTIONAL); }

    public void setToBrokenDownState(){
        setState(MaterialStates.BROKEN_DOWN);
        setToUnavailableState();
    }

    public void setDesignation(String designation) { this.designation = designation; }

    public void setSpecification(String specification) { this.specification = specification; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public void setAcquisitionDate(String acquisitionDate) { this.acquisitionDate = acquisitionDate; }

    public void setAvailable(boolean available) { isAvailable = available; }

    public void setToAvailableState(){ isAvailable = true; }

    public void setToUnavailableState(){ isAvailable = false; }

    public void setTypeID(int typeID) { this.typeID = typeID; }

    public void setPackID(int packID) { this.packID = packID; }

    public boolean isFunctional(){ return getState().equals(MaterialStates.FUNCTIONAL); }

    public boolean isBrokenDown(){ return getState().equals(MaterialStates.BROKEN_DOWN); }

    public boolean isDefective(){ return getState().equals(MaterialStates.DEFECTIVE); }

    public boolean isFieldsNullOrBlank(){
        return getDesignation() == null ||
                getBrand() == null ||
                getSpecification() == null ||
                getAcquisitionDate() == null ||
                getState() == null ||
                getSerialNumber() == null ||
                getDesignation().isBlank() ||
                getBrand().isBlank() ||
                getSpecification().isBlank() ||
                getAcquisitionDate().isBlank() ||
                getState().isBlank() ||
                getSerialNumber().isBlank();
    }

    public String getAvailabilityToString(){ return isAvailable ? "Disponible" : "Indisponible"; }

    @Override
    public String toString() { return this.designation; }

}