package vehicles;
import exceptions.*;
public interface PassengerCarrier{
  public void boardPassengers(int count) throws OverloadException; //Boards if ≤ capacity; throws OverloadException.
  public void disembarkPassengers(int count) throws InvalidOperationException;// Disembarks; throws InvalidOperationException if count > current passengers.
  public int getPassengerCapacity();// Returns max capacity.
  public int getCurrentPassengers();// Returns current passengers.
}