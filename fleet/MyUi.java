package fleet;
import vehicles.*;
import exceptions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // ActionListener lives here


public class MyUi extends JFrame {
    private JLabel[] mileagelabels = new JLabel[4]; // arrays to keep the label of the cars
    private JLabel[] fuellabels    = new JLabel[4];
    private JLabel[] statuslabels  = new JLabel[4];
    private JLabel statusbar;
    private JLabel statusbar2;

    public MyUi() {
              SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Highway simulator"); // title
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout()); // splitting are into 5 NSEWC

            // creating a topbar for buttons
            JPanel topbar = new JPanel(new  FlowLayout(FlowLayout.LEFT));
            JButton startbtn = new JButton("Start");
            JButton pausebtn = new JButton("Pause");
            JButton stopbtn = new JButton("Stop");
            JButton refuelbtn = new JButton("Refuel");
            startbtn.addActionListener(e -> {try{Main.startsimu();}
            catch(InvalidOperationException x){
            }});
            pausebtn.addActionListener(e -> {
              Main.paused = !Main.paused;
              if (Main.paused){
                pausebtn.setText("resume");
              }
              else{
                pausebtn.setText("Pause");
              }
              
              }); 
            stopbtn.addActionListener(e -> {Main.running = false;});
            refuelbtn.addActionListener(e -> {Main.fleetmanager.refuelAll(15.00, 15.00, 15.00);});
            topbar.add(startbtn);
            topbar.add(pausebtn);
            topbar.add(stopbtn);
            topbar.add(refuelbtn);

            frame.add(topbar, BorderLayout.NORTH); // adding top bar to frame

          // bottom layer 
           JPanel bottombar = new JPanel(new FlowLayout(FlowLayout.LEFT));
          statusbar = new JLabel("Total distance travelled (var)");
           statusbar.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            statusbar2 = new JLabel("Sum of distance of 4 cars");
           statusbar2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
           JCheckBox sync = new JCheckBox("sync");
           bottombar.add(statusbar);
           bottombar.add(statusbar2);
           bottombar.add(sync);
           sync.addItemListener(e -> {Main.issync = sync.isSelected();});

          frame.add(bottombar, BorderLayout.SOUTH);

          // center grid w vehicles

          JPanel vehiclegrid = new JPanel(new GridLayout(2,2,10,10));
          vehiclegrid.add(vehiclepanel("Car 1",0));
          vehiclegrid.add(vehiclepanel("Car 2",1));
          vehiclegrid.add(vehiclepanel("Car 3",2));
          vehiclegrid.add(vehiclepanel("Car 4",3));
          frame.add(vehiclegrid, BorderLayout.CENTER);

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

    }

    private JPanel vehiclepanel(String title,int idx){
      JPanel temppanel = new JPanel(new GridLayout(3,1));// rows, col
      temppanel.setBorder(BorderFactory.createTitledBorder(title));
      mileagelabels[idx] = (new JLabel("Mileage: 0 KM"));
      fuellabels[idx] = (new JLabel("Fuel: 0L"));
      statuslabels[idx] = (new JLabel("Status: out of fuel"));
      temppanel.add(mileagelabels[idx]);
      temppanel.add(fuellabels[idx]);
      temppanel.add(statuslabels[idx]);
      return temppanel;
    }

    // methods to uopdate from main
    public void updateTotals(int total, int sumcar) {
        SwingUtilities.invokeLater(() -> {
            statusbar.setText("Total distance travelled: " + total + " km");
            statusbar2.setText("Sum of distance of 4 cars: " + sumcar + " km");
        });
    }

    // Update a single car labels
    public void updateCar(int idx, int mileage, int fuel, String status) {
        SwingUtilities.invokeLater(() -> {
            mileagelabels[idx].setText("Mileage: " + mileage + " KM");
            fuellabels[idx].setText("Fuel: " + fuel + "L");
            statuslabels[idx].setText("Status: " + status);
        });
    }

}
