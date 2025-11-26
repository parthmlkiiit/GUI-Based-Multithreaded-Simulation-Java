package fleet;
import vehicles.*;
import exceptions.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
  public static double distcovered = 0;
  public static boolean issync = true;
  public static int maxspeed = 1;
  public static boolean running = false;
  public static Lock lock = new ReentrantLock();
  public static void addresult(int amt){
    if (issync){
        lock.lock();
        try{
          distcovered += amt;
        }
        finally{
          lock.unlock();
        }
    }
    else {
      distcovered += amt;
    }
  }
  

  public static void main(String[] args) throws InvalidOperationException {
    FleetManager fleetmanager = new FleetManager();
    
    // mileage += maxspeed / 3600;  // per second update

    Car newv1 = new Car("id1", "mazda", maxspeed, 0.0, 4);
    Car newv2 = new Car("id2", "mazda", maxspeed, 0.0, 4);
    Car newv3 = new Car("id3", "mazda", maxspeed, 0.0, 4);
    Car newv4 = new Car("id4", "mazda", maxspeed, 0.0, 4);
    fleetmanager.addVehicle(newv1);
    fleetmanager.addVehicle(newv2);
    fleetmanager.addVehicle(newv3);
    fleetmanager.addVehicle(newv4);

    fleetmanager.refuelAll(100.00, 100.00, 100.00);

    running = true;

    Thread t1 = new Thread(() -> {
      while (running && (newv1.getFuelLevel() > 0)){
        newv1.addmileage(1); // 1 km per tick or sec
        newv1.setFuelLevel(newv1.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        try{
          Thread.sleep(1000);
        }
        catch (InterruptedException e){
          System.out.println(e);
          break;
        }
      }


    });
    Thread t2 = new Thread(() -> { 
      while (running && (newv2.getFuelLevel() > 0)){
        newv2.addmileage(1); // 1 km per tick or sec
        newv2.setFuelLevel(newv2.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        try{
          Thread.sleep(1000);
        }
        catch (InterruptedException e){
          System.out.println(e);
          break;
        }
      }
    });
    Thread t3 = new Thread(() -> { 
      while (running && (newv3.getFuelLevel() > 0)){
        newv3.addmileage(1); // 1 km per tick or sec
        newv3.setFuelLevel(newv3.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        try{
          Thread.sleep(1000);
        }
        catch (InterruptedException e){
          System.out.println(e);
          break;
        }
      }
     });
    Thread t4 = new Thread(() -> { while (running && (newv4.getFuelLevel() > 0)){
        newv4.addmileage(1); // 1 km per tick or sec
        newv4.setFuelLevel(newv4.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        try{
          Thread.sleep(1000);
        }
        catch (InterruptedException e){
          System.out.println(e);
          break;
        }
      }});
    t1.start();
    t2.start();
    t3.start();
    t4.start();
    
        // simple console monitor: print state every second for 10 seconds
    for (int i = 1; i <= 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        break;
      }

      double sumMileage = newv1.getCurrentMileage() + newv2.getCurrentMileage()
                        + newv3.getCurrentMileage() + newv4.getCurrentMileage();

      System.out.println("t = " + i + " s");
      System.out.println("  distcovered (shared) = " + Main.distcovered);
      System.out.println("  sum of vehicle mileages = " + sumMileage);
      System.out.println("  v1: mileage=" + newv1.getCurrentMileage() + ", fuel=" + newv1.getFuelLevel());
      System.out.println("  v2: mileage=" + newv2.getCurrentMileage() + ", fuel=" + newv2.getFuelLevel());
      System.out.println("  v3: mileage=" + newv3.getCurrentMileage() + ", fuel=" + newv3.getFuelLevel());
      System.out.println("  v4: mileage=" + newv4.getCurrentMileage() + ", fuel=" + newv4.getFuelLevel());
      System.out.println();
    }
    running = false;

    // wait for threads to finish
    try {
      t1.join();
      t2.join();
      t3.join();
      t4.join();
    } catch (InterruptedException e) {
      System.out.println(e);
    }

    System.out.println("Final distcovered = " + distcovered);
    
  

  }
}