package vehicles;
import exceptions.*;
public interface Maintainable{
  public void scheduleMaintenance();//Sets maintenance flag.
  public boolean needsMaintenance();// True if mileage > 10000 km.
  public void performMaintenance(); //Resets flag, prints message.
}