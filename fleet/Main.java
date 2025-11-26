package fleet;
import vehicles.*;
import exceptions.*;
import java.util.TreeSet; 
import java.util.Scanner; 
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List; 
import java.util.Collections;
import java.io.FileWriter;
import java.io.IOException;
class Main {
  public static void main(String[] args) {
    FleetManager fleetManager = new FleetManager();
    boolean running = true;
    while (running){
    Scanner scan = new Scanner(System.in);
    System.out.println("\n======== Fleet management system ========"); // new changes for A2
    System.out.println( "1. Add Vehicle \n" + "2. Remove Vehicle \n" + "3. Perform Action on Vehicle \n" + "4. Start Journey \n" + "5. Refuel All \n" + "6. Perform Maintenance \n" + "7. Generate Report \n" + "8. Save Fleet \n" +"9. Load Fleet \n" +"10. Search by Type \n"  +"11. Total fuel consumed by fleet for a journey \n"+"12. Data analysis tools \n" + "13. Exit");

    System.out.println("=========================================");
    System.out.print("Enter your choice :- ");
    
    int choice = scan.nextInt(); 
    scan.nextLine();

    System.out.println("you entered " + choice);  
     switch (choice) {
             case 1:
              System.out.print("Select what type of vehicle to add:- \n 1. Car 2. Truck 3. Bus 4. Airplane 5. CargoShip \nChoice => ");
              int typeofv = scan.nextInt();
              scan.nextLine(); 
              System.out.println("Enter the following info -");
              System.out.print("ID => ");
              String id = scan.nextLine();
              System.out.print("Model => ");
              String model = scan.nextLine();
              System.out.print("Max speed => ");
              double maxspeed = scan.nextDouble();
              System.out.print("Current mileage => ");
              double mileage = scan.nextDouble();
              int wheels = 0;
              double maxalt = 0.0;
              boolean hassail = false;

              if (typeofv == 1 || typeofv == 2 || typeofv == 3) {
                 System.out.print("Number of wheels => ");
                 wheels = scan.nextInt();
            }         
              else if (typeofv == 4) {
                System.out.print("Max altitude => ");
                maxalt = scan.nextDouble();
            }
               else if (typeofv == 5) {
                System.out.print("Does the Ship have sail (true/false) => ");
                hassail = scan.nextBoolean();
            }


            Vehicle newv = null;
            try {
              switch (typeofv) {
              case 1: newv = new Car(id, model, maxspeed, mileage, wheels); break;
              case 2: newv = new Truck(id, model, maxspeed, mileage, wheels); break;
              case 3: newv = new Bus(id, model, maxspeed, mileage, wheels); break;
              case 4: newv = new Airplane(id, model, maxspeed, mileage, maxalt); break;
              case 5: newv = new CargoShip(id, model, maxspeed, mileage, hassail); break;
              default:System.out.println("Invalid vehicle type.");
              }
              }   
           catch (InvalidOperationException e) {
               System.out.println("Error creating vehicle: " + e.getMessage());
                  }

            if (newv != null) {
             try {
             fleetManager.addVehicle(newv);
             System.out.println("Vehicle added successfully!");
             } 
            catch (InvalidOperationException e) {
              System.out.println("Error adding vehicle: " + e.getMessage());
            }
              }
        break;

                    
                case 2:
                  
              System.out.print("ID of the vehicle to be deleted => ");
              String idd = scan.nextLine();
              try{
              fleetManager.removeVehicle(idd);
              System.out.println("deleted vehicle - "+ idd);}
              catch (InvalidOperationException e){
                System.out.println("error =>" +e.getMessage());
              }
                    break;
                case 3: 
                try{
                    System.out.println("Select vehicle type to perform action:");
                    System.out.println("1. Car, 2. Truck, 3. Bus, 4. Airplane, 5. CargoShip");
                    System.out.print("Select index => ");
                    int actionchoice = scan.nextInt();
                    scan.nextLine();

                    Class<?> vehicleClass = null;
                    switch (actionchoice) {
                        case 1: vehicleClass = Car.class; break;
                        case 2: vehicleClass = Truck.class; break;
                        case 3: vehicleClass = Bus.class; break;
                        case 4: vehicleClass = Airplane.class; break;
                        case 5: vehicleClass = CargoShip.class; break;
                        default: System.out.println("Invalid choice"); break;
                    }
                    if (vehicleClass == null) break;

                    List<Vehicle> foundd = fleetManager.searchByType(vehicleClass);
                    if (foundd.isEmpty()) {
                        System.out.println("No vehicles of this type in the fleet");
                        break;
                    }
                    for (int i = 1; i < (foundd.size() + 1); i++) {
                        System.out.println(i + ": " + foundd.get(i-1).getId() + " - " + foundd.get(i-1).getModel());
                    }
                    System.out.print("Select vehicle index => ");
                    int idx = scan.nextInt();
                    scan.nextLine();
                    if (idx < 1 || idx >= (foundd.size()+1)) {
                        System.out.println("Invalid index");
                        break;
                    }
                    Vehicle ve = foundd.get(idx-1);

                    if (ve instanceof Car) {
                        System.out.println("1. Load passengers - 2. Unload passengers");
                        System.out.print("Select choice => ");
                        int act = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter number of passengers: ");
                        int num = scan.nextInt();
                        scan.nextLine();
                        if (act == 1) ((Car)ve).boardPassengers(num);
                        else if (act == 2) ((Car)ve).disembarkPassengers(num);
                        else {System.out.println("Invalid action"); break;}
                    }
                     else if (ve instanceof Truck) {
                        System.out.println("1. Load cargo - 2. Unload cargo");
                        System.out.print("Select index => ");
                        int act = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter amount=> ");
                        double amt = scan.nextDouble();
                        scan.nextLine();
                        if (act == 1) ((Truck)ve).loadCargo(amt);
                        else if (act == 2) ((Truck)ve).unloadCargo(amt);
                        else {System.out.println("Invalid action"); break;}
                    }
                     else if (ve instanceof Bus) {
                        System.out.println("1. Load passengers - 2. Unload passengers - 3. Load cargo - 4. Unload cargo");
                        System.out.print("Select index => ");
                        int act = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter amount=> ");
                        int num = scan.nextInt();
                        scan.nextLine();
                        if (act == 1) ((Bus)ve).boardPassengers(num);
                        else if (act == 2) ((Bus)ve).disembarkPassengers(num);
                        else if (act == 3) ((Bus)ve).loadCargo(num);
                        else if (act == 4) ((Bus)ve).unloadCargo(num);
                        else {System.out.println("Invalid action"); break;}
                    }
                     else if (ve instanceof Airplane) {
                        System.out.println("1. Load passengers - 2. Unload passengers - 3. Load cargo - 4. Unload cargo");
                        System.out.print("Select index => ");
                        int act = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter amount=> ");
                        int num = scan.nextInt();
                        scan.nextLine();
                        if (act == 1) ((Airplane)ve).boardPassengers(num);
                        else if (act == 2) ((Airplane)ve).disembarkPassengers(num);
                        else if (act == 3) ((Airplane)ve).loadCargo(num);
                        else if (act == 4) ((Airplane)ve).unloadCargo(num);
                        else {System.out.println("Invalid action"); break;}
                    } 
                    else if (ve instanceof CargoShip) {
                        System.out.println("1. Load cargo - 2. Unload cargo");
                        System.out.print("Select index => ");
                        int act = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter amount=> ");
                        double amt = scan.nextDouble();
                        scan.nextLine();
                        if (act == 1) ((CargoShip)ve).loadCargo(amt);
                        else if (act == 2) ((CargoShip)ve).unloadCargo(amt);
                        else {System.out.println("Invalid action"); break;}
                    } 
                    System.out.println("action performed successfully");
                    }
                    catch(InvalidOperationException | OverloadException er){
                      System.out.println("error loading into file" + er.getMessage());}
                    break;
                case 4:
                    System.out.print("Enter journey distance=> ");
                    double distance = scan.nextDouble();
                    try {
                      fleetManager.startAllJourneys(distance);
                      System.out.println("Journey started for all vehicles");
                    }
                    catch (InvalidOperationException e) {
                       System.out.println("Error starting journey -" + e.getMessage());
                     }
                    break;
                case 5:
                  int landv = 0, airv = 0, waterv = 0;
                  for (Vehicle v : fleetManager.getFleet()) {
                    if (v instanceof Car || v instanceof Truck || v instanceof Bus) {
                      landv+=1;
                    }
                    else if (v instanceof Airplane) {
                      airv+=1;
                    }
                    else if (v instanceof CargoShip cs && !cs.hasSail()) {
                      waterv+=1;
                    }
                   }
                System.out.println("Currently the fleet consists of:");
                System.out.println("Land vehicles: " + landv);
                System.out.println("Air vehicles: " + airv);
                System.out.println("Water vehicles: " + waterv);

                Double landfuel = null, jetfuel = null, shipfuel = null;
                if (landv > 0) {
                  System.out.print("Enter fuel amount for land vehicles - ");
                  landfuel = scan.nextDouble();
                  }
                if (airv > 0) {
                  System.out.print("Enter fuel amount for air vehicles :- ");
                  jetfuel = scan.nextDouble();
                  }
                if (waterv > 0) {
                  System.out.print("Enter fuel amount for water vehicles :- ");
                  shipfuel = scan.nextDouble();
                  }
                  fleetManager.refuelAll(landfuel, jetfuel, shipfuel);
                    break;
                case 6:
                    List<Vehicle> needingmaintenance = fleetManager.getVehiclesNeedingMaintenance();
                    fleetManager.maintainAll();
                    if (needingmaintenance.isEmpty()) {
                      System.out.println("No vehicles needed maintenance.");
                    } 
                    break;
                case 7:
                    try {
                     String report = fleetManager.generateReport();
                     System.out.println(report);
                    }
                    catch (InvalidOperationException e) {
                    System.out.println("Could not generate report - " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Enter file name to save fleet- ");
                    String savename = scan.nextLine();
                    if (!savename.toLowerCase().endsWith(".csv")) {
                       savename += ".csv";
                    } 
                    System.out.print("Sort by fuel efficiency before saving? (y/n): ");
                    String sortChoice = scan.nextLine().trim().toLowerCase();
                    if (sortChoice.equals("y")) {
                      fleetManager.sortFleetByEfficiency();
                    }
                    
                      fleetManager.saveToFile(savename);
                      System.out.println("Fleet saved successfully to " + savename);
                    
                  
                    break;
                case 9:
                  try{
                    System.out.print("Enter file name to load fleet- ");
                    String loadname = scan.nextLine();
                    if (!loadname.toLowerCase().endsWith(".csv")) {
                       loadname += ".csv";
                    } 
                      fleetManager.loadFromFile(loadname);
                      System.out.println("Fleet loaded successfully from " + loadname);
                      }
                  catch (InvalidOperationException e){
                    System.out.println("error loading into file" + e.getMessage());
                  }
                    break;
                case 10:
                    System.out.println("Select vehicle type to search :-");
                    System.out.println("1. Car, 2. Truck, 3. Bus, 4. Airplane, 5. CargoShip");
                    int typeChoice = scan.nextInt();
                    Class<?> searchClass = null;
                    switch(typeChoice) {
                      case 1: searchClass = Car.class; break;
                      case 2: searchClass = Truck.class; break;
                      case 3: searchClass = Bus.class; break;
                      case 4: searchClass = Airplane.class; break;
                      case 5: searchClass = CargoShip.class; break;
                      default: System.out.println("Invalid choice."); break;
                      }
                      List<Vehicle> found = fleetManager.searchByType(searchClass);
                      if (found.isEmpty()) {
                        System.out.println("No vehicles of that type in the fleet.");
                       }
                      else {
                        System.out.println("Vehicles found =>");
                        for (Vehicle v : found) {
                          System.out.println(v.getId() + " - " + v.getModel());
                        }
                      }
                    
                    break;
        
                case 11:
                  try{
                  System.out.println("Select Journey dist => ");
                  double journeydist = scan.nextDouble();
                  System.out.println("Total fuel consumed by fleet for joruney => " + fleetManager.getTotalFuelConsumption(journeydist) + "L");}
                  catch (InvalidOperationException e){
                    System.out.print("error getting fuel" + e.getMessage());
                  }
                break;
         
                case 12: // new additions for A2
                  System.out.print("=== DATA ANALYSIS TOOLS === \n");
                  System.out.print("1. Print all distinct models \n"+"2. List Vehicles Needing Maintenance \n" + "3. Sort by efficency \n"+ "4. Sort by model \n" + "5. Sort by speed \n" + "======================= \n");
                  
                  
                  int dataact = scan.nextInt();
                  
                  switch (dataact){
                    case 1: 
                  TreeSet<String> allmodel = fleetManager.getmodellist();
                  if (allmodel.isEmpty()) {
                    System.out.println("No models in the fleet/empty fleet");
                    }
                    else {
                      System.out.println("Distinct fleet models:");
                    for (String ii : allmodel) {
                        System.out.println(ii);

                    }
                    }break;
                    case 2:
                    List<Vehicle> maint= fleetManager.getVehiclesNeedingMaintenance();
                    if (maint.isEmpty()) {
                      System.out.println("No vehicles needs maintenance");
                      } 
                    else {
                      System.out.print("Maintenance needed for ");
                      for (Vehicle v : maint) {
                          System.out.print(v.getId() + " ");
                      }
                    }
                      break;
                    case 3:
                      try{
                    fleetManager.sortFleetByEfficiency();
                    List<Vehicle> allv = fleetManager.getFleet();
                    
                    if (allv.isEmpty()) {
                    System.out.println("No vehicles in the fleet.");
                    }
                    else {
                      System.out.println("sorted list");
                      System.out.println("Fleet vehicles:");
                    for (Vehicle vvv : allv) {
                        System.out.println(vvv.getClass().getSimpleName() + " - " + vvv.getId() + " -> " + vvv.calculateFuelEfficiency() + " km/l");

                    }
                    Vehicle vmeff = fleetManager.getMostEffVehicle();
                    Vehicle vleff = fleetManager.getLeastEff();
                    System.out.println("Most efficient vehicle => " + vmeff.getId() + " - " + vmeff.getModel() + " - "+ vmeff.calculateFuelEfficiency() + " km/l");
                    System.out.println("Least efficient vehicle => " +vleff.getId() + " - " + vleff.getModel() + " - "+vleff.calculateFuelEfficiency() + " km/l");
                    }}
                    catch (InvalidOperationException e){
                      System.out.println("Error -" + e);
                    }
                      break;
                    case 4:
                    fleetManager.sortFleetByModel();
                    List<Vehicle> allvv = fleetManager.getFleet();
                       if (allvv.isEmpty()) {
                    System.out.println("No vehicles in the fleet.");
                    }
                    else {
                      System.out.println("sorted list");
                      System.out.println("Fleet vehicles:");
                    for (Vehicle vvvv : allvv) {
                        System.out.println(  vvvv.getModel() + " -> " +vvvv.getId() + " - " + vvvv.getClass().getSimpleName());

                    }
                    }
                      break;
                    case 5:
                      fleetManager.sortFleetBySpeed();
                      List<Vehicle> allvvv = fleetManager.getFleet();
                       if (allvvv.isEmpty()) {
                    System.out.println("No vehicles in the fleet.");
                    }
                    else {
                     
                      System.out.println("sorted list");
                      System.out.println("Fleet vehicles:");
                    for (Vehicle vvvvv : allvvv) {
                        System.out.println(vvvvv.getClass().getSimpleName() + " - " + vvvvv.getId() + " -> " + vvvvv.getmaxSpeed() + " km/h");
                  
                    }
                    Vehicle vfast = fleetManager.getFastestVehicle();
                    Vehicle vslow = fleetManager.getSlowestVehicle();
                    System.out.println("fastest vehicle => " + vfast.getId() + " - " + vfast.getModel() + " - "+ vfast.getmaxSpeed() + " km/h");
                    System.out.println("slowest vehicle => " + vslow.getId() + " - " + vslow.getModel() + " - " + vslow.getmaxSpeed() + " km/h");

                    }break;

                    default:
                    System.out.println("Invalid choice");
                    break;

                    }
                    break;


                case 13:
                    System.out.println("Exiting program...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }

  }}
}