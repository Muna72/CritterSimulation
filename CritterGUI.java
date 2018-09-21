import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
/**********************************************************
GUI for a critter simulation.  Impements Runnable to allow
a method to run in the background while the user continues
to click on buttons.

@author Scott Grissom
@version August 2016
 ***********************************************************/
public class CritterGUI extends JFrame implements ActionListener, Runnable{

    /** simulation speed */
    private final int DELAY = 50;

    /** is simulation currently runnning? */
    private boolean isRunning;  

    /** the simulation object that controls everything */
    private Simulation world; 

    /** displays updated statistics */
    JTextArea statsArea;

    //define buttons
    JButton ants;    
    JButton birds;
    JButton hippos;
    JButton vultures;
    JButton wolves;
    JButton start;
    JButton stop;

    //define menu items
    private JMenuBar menu;
    JMenu file;
    JMenuItem clear;
    JMenuItem quit;
    /************************************************************
    Main method displays the simulation GUI
     ************************************************************/
    public static void main(String arg[]){
        try{
        CritterGUI gui = new CritterGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Critter Simulation");
        gui.setSize(600,600);
        gui.pack();
        gui.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /************************************************************
    Create the GUI
     ************************************************************/
    public CritterGUI(){

        // simulation is turned off 
        isRunning = false;

        // create the lay out
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // Place the simulation on the screen
        position.gridx = 0;
        position.gridy = 1;
        position.gridwidth = 6;           
        world = new Simulation();
        add(world, position);
        

        // Place a label
        position.gridx = 6;
        position.gridy = 0;  
        add(new JLabel("Live Stats"),position);

        // Place stats area below the label
        statsArea = new JTextArea(7,12);
        statsArea.setBackground(Color.YELLOW);
        position.gridx = 6;
        position.gridy = 1;    
        position.anchor = GridBagConstraints.PAGE_START;
        add(statsArea, position);  
        statsArea.setText(world.getStats());

        //place each button
        start = new JButton( "Start" );
        position = makeConstraints(2,0,1,1,GridBagConstraints.LINE_START);
        add(start, position);
        
        stop = new JButton( "Stop" );
        position = makeConstraints(4,0,1,1,GridBagConstraints.LINE_START);
        add(stop, position);
        
        ants = new JButton( "Ants" );
        ants.setForeground(Color.RED);
        position = makeConstraints(1,2,1,1,GridBagConstraints.LINE_START); 
        position.insets =  new Insets(0,20,0,20);
        add(ants, position);
        
        birds = new JButton( "Birds" );
        birds.setForeground(Color.BLUE);
        position = makeConstraints(2,2,1,1,GridBagConstraints.LINE_START);  
        position.insets =  new Insets(0,0,0,20);
        add(birds, position);
        
        hippos = new JButton( "Hippos" );
        hippos.setForeground(Color.GRAY);
        position = makeConstraints(3,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,0,0,20);
        add(hippos, position);
        
        vultures = new JButton( "Vultures" );
        vultures.setForeground(Color.BLACK);
        position = makeConstraints(4,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,0,0,20);
        add(vultures, position);
        
        wolves = new JButton( "Wolves" );
        wolves.setForeground(Color.CYAN);
        position = makeConstraints(5,2,1,1,GridBagConstraints.LINE_START);    
        add(wolves, position);
        
        //create and add menu items
        menu = new JMenuBar();
        file = new JMenu("File");
        quit = new JMenuItem("Quit");
        clear = new JMenuItem("Clear");
        menu.add(file);
        file.add(quit);
        file.add(clear);
        setJMenuBar(menu);
        
        //add all action listeners
        ants.addActionListener(this);
        birds.addActionListener(this);
        hippos.addActionListener(this);
        vultures.addActionListener(this);
        wolves.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
        file.addActionListener(this);
        quit.addActionListener(this);
        clear.addActionListener(this);
        
        // Advanced topic! this must be at the end of this method
        // start the simulation in separate thread
        new Thread(this).start();
    }

    /************************************************************
    Respond to button clicks
    @param e action even triggered by user
     ************************************************************/
    public void actionPerformed(ActionEvent e){

        //exit application if QUIT menu item
        if (e.getSource() == quit){
            System.exit(1);
        }
        //set running variable to true if START button
        if (e.getSource() == start){
            isRunning = true;   
        }

        //set running variable to false if STOP button
        if (e.getSource() == stop){
           isRunning = false; 
        }

        //reset simulation if CLEAR menu item
        if (e.getSource() == clear){
            world.reset(); 
        }

        //inject 10 ants if ANTS button
        if(e.getSource() == ants){ 
            world.addAnts(10);
        }

        //inject 10 birds if BIRDS button       
        if(e.getSource() == birds){ 
            world.addBirds(10);
        }
        //inject 10 hippos if HIPPOS button      
        if(e.getSource() == hippos){ 
            world.addHippos(10);
        }
        //inject 10 vultures if VULTURES button    
        if(e.getSource() == vultures){ 
            world.addVultures(10);
        }
        //inject 10 wolves if WOLVES button      
        if(e.getSource() == wolves){ 
            world.addWolves(10);
        }

        // Afterwards, update display and statistics
        world.repaint();
        statsArea.setText(world.getStats());
    }

    /************************************************************
    Once started, this method runs forever in a separate thread
    The simulation only advances and displays if the boolean
    variable is currently true
     ************************************************************/
    public void run(){
        try {

            // run forever
            while(true) {

                // only update simulation if it is running
                if (isRunning) {
                    world.oneStep();
                    statsArea.setText(world.getStats());
                }

                // pause between steps.  Otherwise, the simulation
                // would move too quickly to see
                Thread.sleep(DELAY);
            }
        }
        catch (InterruptedException ex) {
        }
    }
    private GridBagConstraints makeConstraints(int x, int y, int h, int w, int align){ 
        GridBagConstraints rtn = new GridBagConstraints(); 
        rtn.gridx = x; 
        rtn.gridy = y; 
        rtn.gridheight = h; 
        rtn.gridwidth = w; 
        
        // set alignment: LINE_START, CENTER, LINE_END
        rtn.anchor = align; 
        return rtn; 
    }     
}