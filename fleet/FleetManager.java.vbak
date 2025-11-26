package fleet;
import vehicles.*;
import exceptions.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List; 
import java.util.Collections;
import java.io.FileWriter;
import java.io.IOException;
public class FleetManager {
  private ArrayList<Vehicle> fleet = new ArrayList<Vehicle>();
  public List<Vehicle> getFleet() {
    return fleet;
}

  public void addVehicle(Vehicle v) throws InvalidOperationException{
    if (v == null) {
      throw new InvalidOperationException("Vehicle cannot be null");
    }
    String tempid = v.getId();
    for (Vehicle vehicleinlist : fleet){
      if (vehicleinlist.getId().equals(tempid)) throw new InvalidOperationException("vehicle-" + vehicleinlist.getId() + " already exists");      
    }
    fleet.add(v);
  }
  public void removeVehicle(String id) throws InvalidOperationException{
     if (id == null || id.isBlank()) {
      throw new InvalidOperationException("ID cannot be empty");
    }
    for (int i = 0; i < fleet.size(); i++){
      if (fleet.get(i).getId().equals(id)){
        fleet.remove(i);
        return;
      }
    }
      throw new InvalidOperationException("vehicle with id-" + id + " doesnt exist in fleet.");
    }
  public void startAllJourneys(double distance) throws InvalidOperationException{
    if (distance <= 0){
      throw new InvalidOperationException("Invalid distance input");
    }
    for (Vehicle vehicleinlist : fleet){
      try {vehicleinlist.move(distance);
      }
      catch (InvalidOperationException e) {
      System.out.println("Failed to move vehicle " + vehicleinlist.getId() + " - " + e.getMessage());
    }
  }
  }
  public double getTotalFuelConsumption(double distance) throws InvalidOperationException{
    if (distance <= 0){
      throw new InvalidOperationException("Invalid distance input");
    }
    double fuelsum = 0.0;
    for (Vehicle vehicleinlist : fleet){

      FuelConsumable tempvar = (FuelConsumable) vehicleinlist;
      try{
        double fuelused = tempvar.consumeFuel(distance); // this changes the vehicle fuel though 
        fuelsum+= fuelused;
      }
      catch (InsufficientFuelException e) {
        // for cases where fuel is insufficient and this error is produced
        System.out.println("error insufficient fuel");
      }
      
    }
    return fuelsum;
  }
  public void maintainAll(){ 
    for (Vehicle vehicleinlist : fleet){
      Maintainable tempvar = (Maintainable) vehicleinlist;
      if (tempvar.needsMaintenance()){tempvar.performMaintenance();}
    }
  }
  public List<Vehicle> searchByType(Class<?> type){
    List<Vehicle> result = new ArrayList<>();
    for (Vehicle vehicleinlist : fleet){
      if (type.isInstance(vehicleinlist)){
        result.add(vehicleinlist);
      }
    }
    return result;
  }
  public void sortFleetByEfficiency(){
    Collections.sort(fleet);
  }
  public String generateReport() throws InvalidOperationException{//total vehicles, count by type, average efficiency, total mileage, maintenance status)
  int totalvehiclecounter = fleet.size();
  List<Integer> counterlist = new ArrayList<>();
  ArrayList<String> maintain = new ArrayList<String>();
  int tempefficiencynum = fleet.size();
  double totmilege = 0.0;
  double totefficiency = 0.0;
  for (int i = 0; i < 5; i++) {
    counterlist.add(0); // counterlist = (0,0,0,0,0) index=> 0 - Car, 1 - Truck, 2 - bus , 3 - airplane, 4 - cargoship
    }
  for (Vehicle currentvehicle : fleet) {
    if (currentvehicle instanceof Car) {
      int currentCount = counterlist.get(0);
      counterlist.set(0, currentCount + 1);}
    else if (currentvehicle instanceof Truck) { 
      int currentCount = counterlist.get(1);
      counterlist.set(1, currentCount + 1);} 
    else if (currentvehicle instanceof Bus) {
      int currentCount = counterlist.get(2);
      counterlist.set(2, currentCount + 1);}
    else if (currentvehicle instanceof Airplane) {
      int currentCount = counterlist.get(3);
      counterlist.set(3, currentCount + 1);} 
    else if (currentvehicle instanceof CargoShip) {
      int currentCount = counterlist.get(4);
      counterlist.set(4, currentCount + 1);}
    
    if (currentvehicle instanceof CargoShip && ((CargoShip) currentvehicle).hasSail()){
      tempefficiencynum -= 1;
    }
    else{
      double tempeff = currentvehicle.calculateFuelEfficiency();
      totefficiency = totefficiency + tempeff;
    }
  totmilege = totmilege + currentvehicle.getCurrentMileage();
  if (((Maintainable) currentvehicle).needsMaintenance()){
    maintain.add(currentvehicle.getId());
  }
  }
  double aveff = totefficiency / tempefficiencynum;
  String outputreport = ("--------FLEET REPORT--------\n Total vehicle => " + totalvehiclecounter + "\n Num of cars => " + counterlist.get(0) + ", Num of Trucks => " + counterlist.get(1) + ", Num of buses => " + counterlist.get(2) + ", Num of airplanes => " + counterlist.get(3) + ", Num of Cargoships => " + counterlist.get(4) + "\n Average efficiecy (doesnt include Sailships) => " + aveff + "\n Total Mileage => " + totmilege + "\n Vehicles that need maintaince => " + String.join(", ", maintain));
  return outputreport;
  }
  public List<Vehicle> getVehiclesNeedingMaintenance(){
    List<Vehicle> templist = new ArrayList<>();
    for (Vehicle vehicleinlist : fleet){
      if (((Maintainable) vehicleinlist).needsMaintenance()){
        templist.add(vehicleinlist);
      }
    }
    return templist;
  }
  // car - (type,id,model,maxspeed,cuurentmilege,wheels,fuellevel,maxpassengers,currentpassengers)
  // truck - (type, id, model, maxSpeed,cuurentmilege, wheels, fuelLevel, maxCargo, currentCargo)
  // bus - (type, id, model, maxSpeed,cuurentmilege, wheels, fuelLevel,maxpassengers,currentpassengers,maxCargo,currentCargo)
  // airplane - (type, id, model, maxSpeed,cuurentmilege, maxalt, fuelLevel,maxpassengers,currentpassengers, maxCargo, currentCargo)
  // ship - (type, id, model, maxSpeed,cuurentmilege, hassail, fuelLevel, maxCargo, currentCargo)
  void saveToFile(String filename){
   try (FileWriter writer = new FileWriter(filename)) {
    for (Vehicle currentvehicle : fleet) {
        StringBuilder line = new StringBuilder();
        line.append(currentvehicle.getClass().getSimpleName()).append(",");
        line.append(currentvehicle.getId()).append(",");
        line.append(currentvehicle.getModel()).append(",");
        line.append(currentvehicle.getmaxSpeed()).append(",");
        line.append(currentvehicle.getCurrentMileage());

        if (currentvehicle instanceof Car car) {
            line.append(",").append(car.getWheels());
            line.append(",").append(car.getFuelLevel());
            line.append(",").append(car.getPassengerCapacity());
            line.append(",").append(car.getCurrentPassengers());
        } 
        else if (currentvehicle instanceof Truck truck) {
            line.append(",").append(truck.getWheels());
            line.append(",").append(truck.getFuelLevel());
            line.append(",").append(truck.getCargoCapacity());
            line.append(",").append(truck.getCurrentCargo());
        } 
        else if (currentvehicle instanceof Bus bus) {
            line.append(",").append(bus.getWheels());
            line.append(",").append(bus.getFuelLevel());
            line.append(",").append(bus.getPassengerCapacity());
            line.append(",").append(bus.getCurrentPassengers());
            line.append(",").append(bus.getCargoCapacity());
            line.append(",").append(bus.getCurrentCargo());
        } 
        else if (currentvehicle instanceof Airplane airplane) {
            line.append(",").append(airplane.getAltitude());
            line.append(",").append(airplane.getFuelLevel());
            line.append(",").append(airplane.getPassengerCapacity());
            line.append(",").append(airplane.getCurrentPassengers());
            line.append(",").append(airplane.getCargoCapacity());
            line.append(",").append(airplane.getCurrentCargo());
        } 
        else if (currentvehicle instanceof CargoShip ship) {
            line.append(",").append(ship.hasSail());
            line.append(",").append(ship.getFuelLevel());
            line.append(",").append(ship.getCargoCapacity());
            line.append(",").append(ship.getCurrentCargo());
        }

        writer.write(line.toString());
        writer.write("\n");
    }
} catch (IOException e) {
    System.out.println("Error saving fleet" + e.getMessage());
}

  }
  public void loadFromFile(String filename) throws InvalidOperationException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // Skippign empty lines
            String[] temp = line.split(",");
            Vehicle veh = createv(temp);
            fleet.add(veh);
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + filename + "error => " + e.getMessage());
      
    }
}
  
private Vehicle createv(String[] temp) throws InvalidOperationException {
    String type = temp[0].trim();
if (type.equals("Car")) {
    Car car = new Car(
        temp[1].trim(),  
        temp[2].trim(),   
        Double.parseDouble(temp[3].trim()), 
        Double.parseDouble(temp[4].trim()), 
        Integer.parseInt(temp[5].trim())    // wheels
    );
    car.setFuelLevel(Double.parseDouble(temp[6].trim()));
    car.setPassengerCapacity(Integer.parseInt(temp[7].trim()));
    car.setCurrentPassengers(Integer.parseInt(temp[8].trim()));
    return car;
} else if (type.equals("Truck")) {
    Truck truck = new Truck(
        temp[1].trim(),
        temp[2].trim(),
        Double.parseDouble(temp[3].trim()),
        Double.parseDouble(temp[4].trim()), 
        Integer.parseInt(temp[5].trim())    // wheels
    );
    truck.setFuelLevel(Double.parseDouble(temp[6].trim()));
    truck.setCargoCapacity(Double.parseDouble(temp[7].trim())); // FIXED: was Integer
    truck.setCurrentCargo(Double.parseDouble(temp[8].trim()));  // FIXED: was Integer
    return truck;
} else if (type.equals("Bus")) {
    Bus bus = new Bus(
        temp[1].trim(),
        temp[2].trim(),
        Double.parseDouble(temp[3].trim()), 
        Double.parseDouble(temp[4].trim()), 
        Integer.parseInt(temp[5].trim())    // wheels
    );
    bus.setFuelLevel(Double.parseDouble(temp[6].trim()));
    bus.setPassengerCapacity(Integer.parseInt(temp[7].trim()));
    bus.setCurrentPassengers(Integer.parseInt(temp[8].trim()));
    bus.setCargoCapacity(Double.parseDouble(temp[9].trim())); // FIXED: was Integer
    bus.setCurrentCargo(Double.parseDouble(temp[10].trim())); // FIXED: was Integer
    return bus;
} else if (type.equals("Airplane")) {
    Airplane airplane = new Airplane(
        temp[1].trim(),
        temp[2].trim(),
        Double.parseDouble(temp[3].trim()), 
        Double.parseDouble(temp[4].trim()), 
        Double.parseDouble(temp[5].trim())  // maxAltitude
    );
    airplane.setFuelLevel(Double.parseDouble(temp[6].trim()));
    airplane.setPassengerCapacity(Integer.parseInt(temp[7].trim()));
    airplane.setCurrentPassengers(Integer.parseInt(temp[8].trim()));
    airplane.setCargoCapacity(Double.parseDouble(temp[9].trim())); // FIXED: was Integer
    airplane.setCurrentCargo(Double.parseDouble(temp[10].trim())); // FIXED: was Integer
    return airplane;
} else if (type.equals("CargoShip")) {
    CargoShip ship = new CargoShip(
        temp[1].trim(),
        temp[2].trim(),
        Double.parseDouble(temp[3].trim()),  
        Double.parseDouble(temp[4].trim()),  
        Boolean.parseBoolean(temp[5].trim()) // hasSail
    );
    ship.setFuelLevel(Double.parseDouble(temp[6].trim()));
    ship.setCargoCapacity(Double.parseDouble(temp[7].trim())); // FIXED: was Integer
    ship.setCurrentCargo(Double.parseDouble(temp[8].trim()));  // FIXED: was Integer
    return ship;}

    else {
        throw new InvalidOperationException("Unknown vehicle type: " + type);
    }
}
  public void refuelAll(Double landfuel, Double jetfuel, Double shipfuel) {
    try{
    for (Vehicle v : fleet) {
        if (v instanceof Car car && landfuel != null) {
            car.refuel(landfuel);
            System.out.println("Car - " + car.getId() + " fueled with " + landfuel + "L");
        } else if (v instanceof Truck truck && landfuel != null) {
            truck.refuel(landfuel);
            System.out.println("Truck - " + truck.getId() + " fueled with " + landfuel + "L");
        } else if (v instanceof Bus bus && landfuel != null) {
            bus.refuel(landfuel);
            System.out.println("Bus - " + bus.getId() + " fueled with " + landfuel + "L");
        } else if (v instanceof Airplane airplane && jetfuel != null) {
            airplane.refuel(jetfuel);
            System.out.println("Airplane - " + airplane.getId() + " fueled with " + jetfuel + "L");
        } else if (v instanceof CargoShip ship && shipfuel != null && !ship.hasSail()) {
            ship.refuel(shipfuel);
            System.out.println("CargoShip - " + ship.getId() + " fueled with " + shipfuel + "L");
        }
    }}
    catch(InvalidOperationException e){
      System.out.println("Could not generate report: " + e.getMessage());
    }
}
}