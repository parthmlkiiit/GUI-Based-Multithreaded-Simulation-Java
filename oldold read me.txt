README.txt

Fleet Management System (Object-Oriented Programming Assignment) 
===============================================================

1. Notes / Expected Behavior
-----------------------------
- IMP directly built on all the code of Assignment 1
- Main data is stored dynamically in an arraylist and modellist as a treeset so both uniqueness and sorting is done 
- Added remove logic for the tree set , suppose 2 vehicle v001,v002 both are made by same model eg mazda but if v002 is deleted, mazda will still stay in the list as v001 is of mazda but if v001 and v002 is deleted then mazda will be deleted from the set
- in 3.2 it should be for unique id not models as 2 cars can be made from toyota but no 2 cars can have the same id but the behavior is implemented as told.
- changed CSV loading, initially if there a was some error it would just end but now it skips the bad line to gracefully handle exception
- changed CSV loading, now whenever a fleet is loaded it does a clean load 
- changed CLI, added all data options like view all vehicles, view maintaince required and new sorting methods in the "12. Data tools"
- used comparator logic for sorting and max/min 

2. How to Compile, Run, and Test Persistence
---------------------------------------------
Compile in terminal -> javac -d bin vehicles/*.java exceptions/*.java fleet/*.java

This compiles all your `.java` files into `bin/`, preserving the package structure.

Run after compilation -> java -cp bin fleet.Main

Test Persistence (CSV save/load)-
- Use “Save Fleet” option in the CLI to export to a `.csv` file (you will be asked for filename).
- Then exit or reset, use “Load Fleet” option with the saved `.csv` file name to load back in.
- The vehicles and data should match those saved in the file (IDs, models, mileage, etc).

3. How to Use the CLI and Demo Features
----------------------------------------
Once you run `fleet.Main`, you will see a menu with numbered choices. Basic flow:


======== Fleet management system ========
1. Add Vehicle => choose type (Car, Truck, Bus, Airplane, CargoShip), enter required info (ID, model, speed, mileage, plus type-specific data like number of wheels, max altitude, has sail, etc.).
2. Remove Vehicle => provide the ID to delete a vehicle.
3. Perform Action on Vehicle => pick a type, select a vehicle, then do actions specific to that vehicle (load/unload passengers or cargo depending on type).
4. Start Journey => simulate all vehicles moving a given distance (which updates mileage).
5. Refuel All => refuel all type wise
6. Perform Maintenance => perform maintenance on all vehicles that need it.
7. Generate Report => shows summary: total number of vehicles, counts by type, average efficiency (excluding some special cases like sail-ships if applicable), total mileage, which vehicles need maintenance.
8. Save Fleet => save current fleet data to CSV file.
9. Load Fleet => load fleet data from a CSV file.
10. Search by Type => list vehicles filtered by their class/type.
11. Total fuel consumed by fleet for a journey => show IDs of vehicles with maintenance needed.
12. Data analysis tools => shows a new menu for data tools
13. Exit =>  quit the program.
=========================================
=== DATA ANALYSIS TOOLS ===
1. Print all distinct models => prints the treeset of all distinct models in current fleet
2. List Vehicles Needing Maintenance => self explainatory
3. Sort by efficency => sort list by efficency and show most/Least efficient vehicle
4. Sort by model => sort list alphabetically by model
5. Sort by speed => sort list by speed and show most/least fast vehicle
=======================

4. Walkthrough / Demo Example & Expected Output
-----------------------------------------------
Here is a sample run you can try:

>> java -cp bin fleet.Main

======== Fleet management system ========
1. Add Vehicle 
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 9
you entered 9
Enter file name to load fleet- test1
invalid line: "CargoShip,s002,,35.0,20.0,false,70.0,50000.0,0.0" -> Model cannot be empty
skipping
Fleet loaded successfully from test1.csv

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 12
you entered 12
=== DATA ANALYSIS TOOLS ===
1. Print all distinct models
2. List Vehicles Needing Maintenance
3. Sort by efficency
4. Sort by model
5. Sort by speed
=======================
1
Distinct fleet models:

airbus
benz
cargo
ford
generic

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 12
you entered 12
=== DATA ANALYSIS TOOLS ===
1. Print all distinct models
2. List Vehicles Needing Maintenance
3. Sort by efficency
4. Sort by model
5. Sort by speed
=======================
3
sorted list
Fleet vehicles:
Car - v001 -> 15.0 km/l
Bus - v003 -> 10.0 km/l
Truck - v002 -> 8.0 km/l
Airplane - a001 -> 5.0 km/l
CargoShip - s001 -> 0.0 km/l
Most efficient vehicle => s001 - cargo - 0.0 km/l
Least efficient vehicle => v001 - ford - 15.0 km/l

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 12
you entered 12
=== DATA ANALYSIS TOOLS ===
1. Print all distinct models
2. List Vehicles Needing Maintenance
3. Sort by efficency
4. Sort by model
5. Sort by speed
=======================
4
sorted list
Fleet vehicles:
airbus -> a001 - Airplane
benz -> v003 - Bus
cargo -> s001 - CargoShip
ford -> v001 - Car
generic -> v002 - Truck

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 12
you entered 12
=== DATA ANALYSIS TOOLS ===
1. Print all distinct models
2. List Vehicles Needing Maintenance
3. Sort by efficency
4. Sort by model
5. Sort by speed
=======================
5
sorted list
Fleet vehicles:
CargoShip - s001 -> 30.0 km/h
Bus - v003 -> 60.0 km/h
Truck - v002 -> 80.0 km/h
Car - v001 -> 120.0 km/h
Airplane - a001 -> 700.0 km/h
fastest vehicle => a001 - airbus - 700.0 km/h
slowest vehicle => s001 - cargo - 30.0 km/h

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. Total fuel consumed by fleet for a journey
12. Data analysis tools
13. Exit
=========================================
Enter your choice :- 13
you entered 13
Exiting program...
