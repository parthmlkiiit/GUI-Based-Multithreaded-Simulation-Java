package vehicles;
import exceptions.*;
public abstract class LandVehicle extends Vehicle{
  private int numWheels;
  // constructor
  public LandVehicle(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        if (numWheels <= 0) {
        throw new InvalidOperationException("Number of wheels must be greater than 0");
        }
        this.numWheels = numWheels; 
    }
  //Add 10% time for traffic (base time × 1.1).
  @Override
  public double estimateJourneyTime(double distance) throws InvalidOperationException{
    if (distance<0) throw new InvalidOperationException("distance cannot be negative");
    double basetime = (distance / getmaxSpeed());
    return (basetime * 1.1);
  }
  public int getWheels(){
    return numWheels;
  }
}