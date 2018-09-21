
import java.awt.Color;
import java.util.Random;
/**
 *
 * @author Muna
 * @version November 2016
 */
public class Hippo extends GVcritter{
    
    //Declare all instance variables
    private Direction dir;
    private  Random randGen = new Random();
    private int oldAge;
    
    /**Class constructor
     * 
     * @param loc location 
     */
    public Hippo(Location loc) {
        super(loc);
        setColor(Color.GRAY);
        setSpecies(Species.HIPPO);
        oldAge = randGen.nextInt(500 - 300 + 1) + 300;
        steps = randGen.nextInt(4 - 1 + 1) + 1;
        
        if(Math.random() < 0.25)
            dir = Direction.SOUTH;
        else if(Math.random() < 0.5)
            dir = Direction.EAST;
        else if(Math.random() < 0.75)
            dir = Direction.WEST;
        else
            dir = Direction.NORTH;
    }
    
    /**Method to get the hippos attack
     * 
     * @param opponent opponent
     * @return an attack
     */
    public Attack getAttack(GVcritter opponent) {
        
        Attack a = null;
        
        if(steps <= oldAge) {
            a = Attack.POUNCE;
        }
        if(steps > oldAge) {
            a = Attack.FORFEIT;
        }
        return a; 
    }
    
    /**Method to get the hippos move direction
     * 
     * @return a direction 
     */
    public Direction getMoveDirection() {
        
        steps++;
        int rand = randGen.nextInt(4 - 1 + 1) + 1;
        
        if(steps % 5  != 0) {
            return dir;
        }
        else {
            if(rand == 1) {
                dir = Direction.NORTH;    
            } 
            if(rand == 2) {
                dir = Direction.SOUTH;   
            }
            if(rand == 3) {
                dir = Direction.WEST;    
            }
            if(rand == 4) {
                dir = Direction.EAST;    
            }
        }
        return dir;
    }
    
}

