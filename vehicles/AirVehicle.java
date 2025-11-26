package vehicles;
import exceptions.*;
public abstract class AirVehicle extends Vehicle{
  private double maxAltitude;
  // constructor
  public AirVehicle(String id, String model, double maxSpeed, double currentMileage, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        if (maxAltitude <= 0) {
        throw new InvalidOperationException("max alt cannot be at sea level");
        }
        this.maxAltitude = maxAltitude; 
    }
  //Reduce 5% time for direct paths
  @Override
  public double estimateJourneyTime(double distance) throws InvalidOperationException{
    if (distance<0) throw new InvalidOperationException("distance cannot be negative");
    double basetime = (distance / getmaxSpeed());
    return (basetime * 0.95);
  }
  public double getAltitude(){
    return maxAltitude;
  }

}