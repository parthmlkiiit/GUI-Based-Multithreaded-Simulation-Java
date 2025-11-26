package vehicles;
import exceptions.*;
public interface CargoCarrier{
  public void loadCargo(double weight) throws OverloadException;//Loads if ≤ capacity; throws OverloadException if exceeded.
  public void unloadCargo(double weight) throws InvalidOperationException; //throws InvalidOperationException if weight > current cargo.
  public double getCargoCapacity(); 
  public double getCurrentCargo();}