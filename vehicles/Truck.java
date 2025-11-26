package vehicles;
import exceptions.*;
public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable{
  private double fuelLevel;
  private double cargoCapacity; 
  private double currentCargo; 
  private boolean maintenanceNeeded;
  private double lastMaintenanceMileage = 0; 
  public Truck(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, numWheels);
    this.fuelLevel = 0;//given initial value
    this.cargoCapacity = 5000; 
    this.currentCargo = 0; // assuming new truck
    this.maintenanceNeeded = false; // assuming its a new truck no maintainance is req    
    }
      @Override
  public double calculateFuelEfficiency() {
    if (this.currentCargo >2500) return 7.2; //10% loss if cargo more than 50%
    return 8.0; //8.0 km/l
    }
  // interfaces
  @Override
  public void loadCargo(double weight) throws OverloadException{
    if (weight <= 0) return; // security check for negative values
    if (this.currentCargo+weight > this.cargoCapacity) throw new OverloadException("the capacity exceeded by"+((this.currentCargo+weight)- this.cargoCapacity) + "kgs");
    this.currentCargo += weight;
  } 
  @Override
  public void unloadCargo(double weight) throws InvalidOperationException{
    if (weight<=0) throw new InvalidOperationException("weight cannot be negative");
    if (weight>this.currentCargo) throw new InvalidOperationException("cannot deload more than the current cargo");
    this.currentCargo -= weight;
  }
  @Override
  public double getCargoCapacity(){
    return this.cargoCapacity;
  }
  @Override
  public double getCurrentCargo(){
    return this.currentCargo;
  }
  @Override 
  public void refuel(double amount) throws InvalidOperationException{
    if (amount<= 0) throw new InvalidOperationException("fuel amount cannot be 0 or < 0");
    this.fuelLevel += amount;
  }
  @Override
  public double getFuelLevel(){
    return this.fuelLevel;
  }
  @Override
  public double consumeFuel(double distance) throws InsufficientFuelException{
    
    double fuelneeded = distance / calculateFuelEfficiency();
    if (this.fuelLevel < fuelneeded) throw new InsufficientFuelException("Not enough fuel for the journey by this much" + (fuelneeded-this.fuelLevel));
    this.fuelLevel -= fuelneeded;
    return fuelneeded;
  }
  
  @Override
  public void scheduleMaintenance() {
        this.maintenanceNeeded = true; 
        
  }
  @Override 
  public boolean needsMaintenance(){
    return (this.getCurrentMileage() - lastMaintenanceMileage) >= 10000;
  }
  @Override
    public void performMaintenance() {
        this.maintenanceNeeded = false; 
        this.lastMaintenanceMileage = getCurrentMileage();
        System.out.println("Maintenance performed on vehicle " + this.getId() + ". Flag reset."); 
    }
  @Override
  public void move(double distance) throws InvalidOperationException {
    if (distance <= 0) throw new InvalidOperationException("distance cannot be negative or 0");
    try {
      double used = consumeFuel(distance);
      addMileage(distance);
      System.out.println("The truck " + getId() + " moved " + distance + " km and used " + used + " L while carrying " + this.currentCargo + "kgs of cargo");
  } 
  catch (InsufficientFuelException e) {
    System.out.println("The Truck " + getId() + " cannot move: " + e.getMessage());
  }
}
//setters
public void setFuelLevel(double fuelLevel) {
    this.fuelLevel = fuelLevel;
}
public void setCargoCapacity(double cargoCapacity) {
    this.cargoCapacity = cargoCapacity;
}
public void setCurrentCargo(double currentCargo) {
    this.currentCargo = currentCargo;
}
}