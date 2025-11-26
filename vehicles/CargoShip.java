package vehicles;
import exceptions.*;
public class CargoShip extends WaterVehicle implements FuelConsumable, CargoCarrier, Maintainable {

    private double fuelLevel;
    private double cargoCapacity = 50000;   
    private double currentCargo;
    private boolean maintenanceNeeded;
    private double lastMaintenanceMileage = 0;

    public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, hasSail);
        this.fuelLevel = 0;
        this.currentCargo = 0;
        this.maintenanceNeeded = false;
    }
    @Override
  public double calculateFuelEfficiency() {
    if (this.hasSail()) {
        return 0; // boat uses fuel no sail
    } else {
        return 4.0; // motor with 4.0km/L 
    }
} // interfaces
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
     if (this.hasSail()) throw new InvalidOperationException("This vessel has sail no fuel req");
    if (amount<= 0) throw new InvalidOperationException("fuel amount cannot be 0 or < 0");
    this.fuelLevel += amount;
  }
  @Override
  public double getFuelLevel(){
    if (this.hasSail()){
      return 0;
    }
    return this.fuelLevel;
  }
  @Override
  public double consumeFuel(double distance) throws InsufficientFuelException{
    if (this.hasSail()) return 0.0; // if sail powered
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
        this.lastMaintenanceMileage = getCurrentMileage();
        this.maintenanceNeeded = false; 
        System.out.println("Maintenance performed on vehicle " + this.getId() + ". Flag reset."); 
    }
 @Override
  public void move(double distance) throws InvalidOperationException {
    if (distance <= 0) throw new InvalidOperationException("distance cannot be negative or 0");

    try {
      double used = consumeFuel(distance); 
      addMileage(distance);
      String mode = this.hasSail() ? "sailed" : "travelled";
      System.out.println(
        "The CargoShip " + getId() + " " + mode + " " + distance + " km, used " + used +" L, carrying " + this.currentCargo + " kg of cargo"
      );

    } catch (InsufficientFuelException e) {
      System.out.println("The CargoShip " + getId() + " cannot move: " + e.getMessage());
    }
  }

  // setters
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