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
  public static boolean issync = false; // racecondition vs locking
  public static int maxspeed = 1;
  public static volatile boolean running = false; // start/stop
  public static Lock lock = new ReentrantLock();
  public static MyUi ui;        // shared UI instance
  public static volatile boolean paused = false;   // for pause/resume
  public static FleetManager fleetmanager;
  public static Car newv1, newv2, newv3, newv4;


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
  public static void startsimu() throws InvalidOperationException {
    distcovered = 0;
     running = false;
    paused = false;

       // tiny delay so old threads can notice running=false and exit
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        System.out.println(e);
    }
    fleetmanager = new FleetManager();
    newv1 = new Car("id1", "mazda", maxspeed, 0.0, 4);
    newv2 = new Car("id2", "mazda", maxspeed, 0.0, 4);
    newv3 = new Car("id3", "mazda", maxspeed, 0.0, 4);
    newv4 = new Car("id4", "mazda", maxspeed, 0.0, 4);
    fleetmanager.addVehicle(newv1);
    fleetmanager.addVehicle(newv2);
    fleetmanager.addVehicle(newv3);
    fleetmanager.addVehicle(newv4);

    
    fleetmanager.refuelAll(100.00, 100.00, 100.00);
    running = true;

    Thread t1 = new Thread(() -> {
      while (running && (newv1.getFuelLevel() > 0)){
        if (paused) {
          try {
            Thread.sleep(200); // just wait while paused
            continue;
          } 
          catch (InterruptedException e) {
          System.out.println(e);
          break;
          }
    }
        newv1.addmileage(1); // 1 km per tick or sec
        newv1.setFuelLevel(newv1.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        Main.ui.updateCar(0,(int)newv1.getCurrentMileage(),(int)newv1.getFuelLevel(),(newv1.getFuelLevel() > 0) ? "running" : "out of fuel");
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
        if (paused) {
          try {
            Thread.sleep(200); // just wait while paused
            continue;
          } 
          catch (InterruptedException e) {
          System.out.println(e);
          break;
          }
    }
        newv2.addmileage(1); // 1 km per tick or sec
        newv2.setFuelLevel(newv2.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        Main.ui.updateCar(1,(int)newv2.getCurrentMileage(),(int)newv2.getFuelLevel(),(newv2.getFuelLevel() > 0) ? "running" : "out of fuel");
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
        if (paused) {
          try {
            Thread.sleep(200); // just wait while paused
            continue;
          } 
          catch (InterruptedException e) {
          System.out.println(e);
          break;
          }
    }
        newv3.addmileage(1); // 1 km per tick or sec
        newv3.setFuelLevel(newv3.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        Main.ui.updateCar(2,(int)newv3.getCurrentMileage(),(int)newv3.getFuelLevel(),(newv3.getFuelLevel() > 0) ? "running" : "out of fuel");

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
      if (paused) {
          try {
            Thread.sleep(200); // just wait while paused
            continue;
          } 
          catch (InterruptedException e) {
          System.out.println(e);
          break;
          }
    }
        newv4.addmileage(1); // 1 km per tick or sec
        newv4.setFuelLevel(newv4.getFuelLevel() - 1);  //1L per tick or sec
        Main.addresult(1);
        Main.ui.updateCar(3,(int)newv4.getCurrentMileage(),(int)newv4.getFuelLevel(),(newv4.getFuelLevel() > 0) ? "running" : "out of fuel");
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

    Thread monitor = new Thread(() -> {
    while (running) {
        int sumMileage = (int)(newv1.getCurrentMileage() + newv2.getCurrentMileage()+ newv3.getCurrentMileage()+ newv4.getCurrentMileage());
        Main.ui.updateTotals((int)Main.distcovered, sumMileage);

        try {
            Thread.sleep(1000); // update GUI every second
        } catch (InterruptedException e) {
            System.out.println(e);
            break;
        }
      }
    });
    monitor.start();
  }
  public static void main(String[] args){
    fleetmanager = new FleetManager();
    
    // mileage += maxspeed / 3600;  // per second update

    
    ui = new MyUi(); 

  }
}