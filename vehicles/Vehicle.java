package vehicles;
import exceptions.*;
public abstract class Vehicle implements Comparable<Vehicle>{
    // ---properties---
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    // ---constructor---
    public Vehicle(String id, String model, double maxSpeed, double currentMileage) throws InvalidOperationException{
        if (id == null || id.isBlank()) {
        throw new InvalidOperationException("ID cannot be empty");
    }
        if (model == null || model.isBlank()) {
        throw new InvalidOperationException("Model cannot be empty");
    }
        if (maxSpeed <= 0) {
        throw new InvalidOperationException("Max speed cannot be negative or 0");
    }
        if (currentMileage < 0) {
        throw new InvalidOperationException("Mileage cannot be negative");
    }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = currentMileage;
    }
    
    // ---abstract methods---

    //Updates mileage, prints type-specific movement; throws InvalidOperationException if distance < 0 
    public abstract void move(double distance) throws InvalidOperationException;
    //Returns km per liter (or 0 for non-fuel vehicles).
    public abstract double calculateFuelEfficiency() throws InvalidOperationException;
    //Returns time in hours (distance / maxSpeed, adjusted by type).
    public abstract double estimateJourneyTime(double distance) throws InvalidOperationException;

    // ---concrete methods---
    public void displayInfo(){
        System.out.println("ID="+ id +", model="+model+", Max Speed(in km/h)=" + maxSpeed + ", Mileage(in Km)="+currentMileage);
    }
    public double getCurrentMileage(){
        return currentMileage;
    }
    public String getId(){
        return id;
    }
    public String getModel(){
        return model;
    }
    public double getmaxSpeed(){
        return maxSpeed;
    }


     // Helper for subclasses to update mileage and calc journey time 
    protected void addMileage(double distance) throws InvalidOperationException {
        if (distance < 0) {
            throw new InvalidOperationException("Mileage cannot be increased by a negative value");
    }
        currentMileage += distance;
    }
    // we need to change compare to for Collections.sort(fleet)
    @Override
    public int compareTo(Vehicle secondvehicle) {
        try {
            return Double.compare(secondvehicle.calculateFuelEfficiency(), this.calculateFuelEfficiency()); // descending order
        } catch (InvalidOperationException error) { // if some error it is put at the end
            System.out.println("Error when comparing these two" + secondvehicle.getId()+", "+ this.getId());
            return 1;
        }
    }

}
