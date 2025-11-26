package vehicles;
import exceptions.*;
public abstract class WaterVehicle extends Vehicle{
  private boolean hasSail;
  // constructor
  public WaterVehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        this.hasSail = hasSail; 
    }
  //Add 15% time for currents
  @Override
  public double estimateJourneyTime(double distance) throws InvalidOperationException{
    if (distance<0) throw new InvalidOperationException("distance cannot be negative");
    double basetime = (distance / getmaxSpeed());
    return (basetime * 1.15);
  }
  public boolean hasSail(){
    return hasSail;
  }
}