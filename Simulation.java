import java.util.*;
import javax.swing.*;
import java.awt.*;

/****************************************************
 * Simulates a 2D world of critters that move around
 * and fight if they inhabit the same location.
 * 
 * @author Scott Grissom 
 * @version August 2016
 ***************************************************/
public class Simulation extends JPanel{

    /** a 2D world of critters */
    private GVcritter[][] theWorld;

    /** a collection of all live critters */
    private ArrayList <GVcritter> allCritters;

    /** control size of the world */
    private final int ROWS=50, COLUMNS=70, SIZE=10;

    //Instance variable declarations
    private int steps;
    private int antCount;
    private int birdCount;
    private int hippoCount;
    private int vultCount;
    private int wolfCount;
    private Random randGen = new Random();
    

    /****************************************************
    Constructor instantiates and initializes all 
    instance members.
     ****************************************************/
    public Simulation(){
        theWorld = new GVcritter[ROWS][COLUMNS];
        allCritters = new ArrayList<GVcritter>();   

        steps = 0;
        antCount = 0;
        birdCount = 0;
        hippoCount = 0;
        vultCount = 0;
        wolfCount = 0;

        // set the appropriate size of the invisibile drawing area
        setPreferredSize(new Dimension(COLUMNS*SIZE, ROWS*SIZE));
    }
    
    /**Method to show all stats
     * 
     * @return 
     */
    public String getStats() {
        
        String stats = "Steps: " + steps + "\n" + "Ants: " + antCount + "\n" + "Birds: " + birdCount + "\n" + "Hippos: " + hippoCount + "\n" + "Vultures: " + vultCount + "\n" + "Wolves: " + wolfCount;
        return stats;
    }
    
    /**Method to get open location for a critter to move to
     * 
     * @return 
     */
    private Location getOpenLocation() {
        
        int r = randGen.nextInt(ROWS);
        int c = randGen.nextInt(COLUMNS); 
  
        while(theWorld[r][c] != null) {
            r = randGen.nextInt(ROWS);
            c = randGen.nextInt(COLUMNS);
        }  
        return new Location(r,c);
        }
    
    /**Method to place critter
     * 
     * @param c 
     */
    private void placeCritter(GVcritter c){    
        theWorld[c.getLocation().getRow()][c.getLocation().getCol()] = c;
        allCritters.add(c);
    }
    
    /**Method to get relative location of a critter
     * 
     * @param loc
     * @param d
     * @return 
     */
    private Location getRelativeLocation(Location loc, GVcritter.Direction d) {
        
        int row = loc.getRow();
        int col = loc.getCol();
        
        if(d == GVcritter.Direction.NORTH) {
            row--;
        }
        if(d == GVcritter.Direction.SOUTH) {
            row++;
        }
        if(d == GVcritter.Direction.EAST) {
            col++;
        }
        if(d == GVcritter.Direction.WEST) {
            col--;
        }
        if(row < 0) {
            row = (ROWS - 1);   
        }
        if(col < 0) {
            col = (COLUMNS - 1);   
        }
        if(row > ROWS -1) {
            row = 0;   
        }
        if(col > COLUMNS -1) {
            col = 0;   
        }
        return new Location(row,col);
    }
    
    /**Method to reset the simulation
     * 
     */
    public void reset() {
        
        steps = 0;
        antCount = 0;
        birdCount = 0;
        hippoCount = 0;
        vultCount = 0;
        wolfCount = 0;
        allCritters.clear();
        for(int i = 0; i < ROWS; ++i) {
            for(int y = 0; y < COLUMNS; ++y) {
                theWorld[i][y] = null;
            }
        }
    }
    
    /**Method to simulate when a critter dies
     * 
     * @param c 
     */
    private void critterDies(GVcritter c) {
        
        allCritters.remove(c);
        
        if(c.getSpecies() == GVcritter.Species.ANT) {
            --antCount; 
        }
        if(c.getSpecies() == GVcritter.Species.BIRD) {
            --birdCount;
        }
        if(c.getSpecies() == GVcritter.Species.HIPPO) {
            --hippoCount; 
        }
        if(c.getSpecies() == GVcritter.Species.VULTURE) {
            --vultCount;
        }
        if(c.getSpecies() == GVcritter.Species.WOLF) {
            --wolfCount;
        }
    }
    
    /**Method to simulate two critters fighting
     * 
     * @param attacker
     * @param defender 
     */
    private void fight(GVcritter attacker, GVcritter defender) {
        
         theWorld[attacker.getLocation().getRow()][attacker.getLocation().getCol()] = null;
        
        if(attacker.getAttack(defender) == GVcritter.Attack.FORFEIT) { 
            critterDies(attacker);
        }
        else if(defender.getAttack(attacker) == GVcritter.Attack.FORFEIT) {  
            attacker.setLocation(defender.getLocation());
            critterDies(defender); 
        }
        else if(attacker.getAttack(defender) == GVcritter.Attack.POUNCE && defender.getAttack(attacker) == GVcritter.Attack.ROAR) {
            attacker.setLocation(defender.getLocation());
            critterDies(defender);
        }
        else if(attacker.getAttack(defender) == GVcritter.Attack.ROAR && defender.getAttack(attacker) == GVcritter.Attack.SCRATCH) {
            attacker.setLocation(defender.getLocation());
            critterDies(defender);
        }
        else if(attacker.getAttack(defender) == GVcritter.Attack.SCRATCH && defender.getAttack(attacker) == GVcritter.Attack.POUNCE) {
            attacker.setLocation(defender.getLocation());
            critterDies(defender);   
        }
        else if(attacker.getAttack(defender) == defender.getAttack(attacker)) {
            if(Math.random() < 0.5) {
                attacker.setLocation(defender.getLocation());
                critterDies(defender);
            }
            else {
                critterDies(attacker);                   
            }
        }
        else {
            critterDies(attacker);    
        }       
    }

    /****************************************************
    Add the requested number of Ants into the simulation.
    Repeatedly ask for a random location that is free.
    Increment the number of Ants in the simulation.

    @param num number of ants
     ****************************************************/ 
    public void addAnts(int num){
        antCount += num;
        for(int i=1;i<=num;i++){
            // create a new Ant at an open location
            Location loc = getOpenLocation();
            Ant c = new Ant(loc);
            placeCritter(c);
        }
    }
    
    /**Method to add birds into the simulation
     * 
     * @param num 
     */
    public void addBirds(int num) {
        birdCount += num;
        
        for(int i = 1; i<= num; ++i) {
            Location loc = getOpenLocation();
            Bird b = new Bird(loc);
            placeCritter(b);
        }
    }
    
    /**Method to add hippos into the simulation
     * 
     * @param num 
     */
    public void addHippos(int num) {
        hippoCount += num;
        
        for(int i = 1; i <= num; ++i) {
            Location loc = getOpenLocation();
            Hippo h = new Hippo(loc);
            placeCritter(h);
        }
    }
    
    /**Method to add vultures into the simulation
     * 
     * @param num 
     */
    public void addVultures(int num) {
        vultCount += num;
        
        for(int i = 1; i <= num; ++i) {
            Location loc = getOpenLocation();
            Vulture v = new Vulture(loc);
            placeCritter(v);
        }
    }
    
    /**Method to add wolves into the simulation
     * 
     * @param num 
     */
    public void addWolves(int num) {
        wolfCount += num;
        
        for(int i = 1; i <= num; ++i) {
            Location loc = getOpenLocation();
            Wolf w = new Wolf(loc);
            placeCritter(w);
        }
    }

    /******************************************************
    Move forward on step of the simulation
     *****************************************************/  
    public void oneStep(){

        // shuffle the arraylist of critters for better performance
        Collections.shuffle(allCritters);
        steps++;

        // step throgh all critters using traditional for loop
        for(int i=0; i<allCritters.size(); i++){
            GVcritter attacker = allCritters.get(i);

            // what location does critter want to move to?
            GVcritter.Direction dir = attacker.getMoveDirection();
            Location previousLoc = attacker.getLocation();
            Location nextLoc = getRelativeLocation(previousLoc, dir);  

            // who is at the next location?
            GVcritter defender = theWorld[nextLoc.getRow()][nextLoc.getCol()];

            // no critters here so OK for critter 1 to move
            if(defender == null){
                theWorld[nextLoc.getRow()][nextLoc.getCol()] = attacker;
                attacker.setLocation(nextLoc);
                theWorld[previousLoc.getRow()][previousLoc.getCol()] = null;

                // both critters the same species so peacefully bypass 
            }else if(attacker.getSpecies() == defender.getSpecies()){

                // update critter locations
                attacker.setLocation(nextLoc);
                defender.setLocation(previousLoc);

                // update positions in the world
                theWorld[nextLoc.getRow()][nextLoc.getCol()] = attacker;
                theWorld[previousLoc.getRow()][previousLoc.getCol()] = defender;

                //different species so they fight at location of critter 2
            }else if(attacker.getSpecies() != defender.getSpecies()){
                fight(attacker, defender);
            }
        }

        // update drawing of the world
        repaint();
    }

    /******************************************************
    Step through the 2D world and paint each location white
    (for no critter) or the critter's color.  The SIZE of 
    each location is constant.

    @param g graphics element used for display
     *****************************************************/      
    public void paintComponent(Graphics g){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                GVcritter c = theWorld[row][col];

                // set color to white if no critter here
                if(c == null){
                    g.setColor(Color.WHITE);
                    // set color to critter color   
                }else{    
                    g.setColor(c.getColor());
                }

                // paint the location
                g.fillRect(col*SIZE, row*SIZE, SIZE, SIZE);
            }
        }
    }
}