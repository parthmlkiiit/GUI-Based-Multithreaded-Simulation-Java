package vehicles;
import exceptions.*;
public class Bus extends LandVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable{
  private double fuelLevel;
  private int passengerCapacity;
  private int currentPassengers;
  private double cargoCapacity;
  private double currentCargo;
  private boolean maintenanceNeeded;
  private double lastMaintenanceMileage = 0;
  public Bus(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, numWheels);
    this.fuelLevel = 0;//given initial value
    this.cargoCapacity = 500; 
    this.currentCargo = 0; // assuming new bus
    this.passengerCapacity = 50; 
    this.currentPassengers = 0; // assuming new bus doesnt have any passangers
    this.maintenanceNeeded = false; // assuming its a new bus no maintainance is req    
    }
    @Override
  public double calculateFuelEfficiency() {
    return 10.0; //10.0 km/l
    }
  // interfaces 
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
  public void boardPassengers(int count) throws OverloadException{
    if (count <= 0) return; // min check for negative passengers
    if (passengerCapacity < (count + this.currentPassengers)) throw new OverloadException("Capacity exceeded of passengers -"+this.passengerCapacity);
    this.currentPassengers += count;
  }
  @Override
  public void disembarkPassengers(int count) throws InvalidOperationException{
    if (count <= 0) throw new InvalidOperationException("count of passengers to be deboarded cannot be negative");
    if (count > currentPassengers) throw new InvalidOperationException("number of disembark cannot result into negative passengers left in the vehicle");
    this.currentPassengers -= count;
  }
  @Override
  public int getPassengerCapacity(){
    return this.passengerCapacity;
  }
  @Override 
  public int getCurrentPassengers(){
    return this.currentPassengers;
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
  public void move(double distance) throws InvalidOperationException {
    if (distance <= 0) throw new InvalidOperationException("distance cannot be negative or 0");
    try {
      double used = consumeFuel(distance);
      addMileage(distance);
      System.out.println("The bus " + getId() + " moved " + distance + " km and used " + used + " L while carrying " + this.currentCargo + "kgs of cargo and carrying" + this.currentPassengers + "passengers");
  } 
  catch (InsufficientFuelException e) {
    System.out.println("The bus " + getId() + " cannot move: " + e.getMessage());
  }
}
// setters
public void setFuelLevel(double fuelLevel) {
    this.fuelLevel = fuelLevel;
}
public void setPassengerCapacity(int passengerCapacity) {
    this.passengerCapacity = passengerCapacity;
}
public void setCurrentPassengers(int currentPassengers) {
    this.currentPassengers = currentPassengers;
}
public void setCargoCapacity(double cargoCapacity) {
    this.cargoCapacity = cargoCapacity;
}
public void setCurrentCargo(double currentCargo) {
    this.currentCargo = currentCargo;
}
}