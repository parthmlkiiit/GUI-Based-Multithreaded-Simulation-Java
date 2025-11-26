package vehicles;
import exceptions.*;
public interface FuelConsumable{
  public void refuel(double amount) throws InvalidOperationException ;//Adds fuel; throws InvalidOperationException if amount ≤ 0.
  public double getFuelLevel(); // Returns current fuel level.
  public double consumeFuel(double distance) throws InsufficientFuelException;// Reduces fuel based on efficiency; returns consumed amount; throws InsufficientFuelException if not enough fuel.
}