
import java.awt.Color;
import java.util.Random;
/**
 *
 * @author Muna
 * @version November 2016
 */
public class Wolf extends GVcritter {
    
    //Declare all instance variables
    private Direction dir;
    private  Random randGen = new Random();
    
    /**Class constructor
     * 
     * @param loc location 
     */
    public Wolf(Location loc) {
        super(loc);
        setColor(Color.CYAN);
        setSpecies(Species.WOLF);
        steps = randGen.nextInt(16 - 1 + 1) + 1; 
    }
    
    /**Method to execute the wolfs attack
     * 
     * @param opponent opponent
     * @return at for attack
     */
    public Attack getAttack(GVcritter opponent) {
        
        Attack at;
        
        if(opponent.getSpecies() == Species.ANT) {
            at = Attack.ROAR;
        }
        if(opponent.getSpecies() == Species.BIRD || opponent.getSpecies() == Species.VULTURE) {
            at = Attack.POUNCE;
        }
        else {
            at = Attack.SCRATCH;
        }
        return at;
    }
    
    /**Method to get wolfs move direction
     * 
     * @return a direction
     */
    public Direction getMoveDirection() {
        
        ++steps;
        
        if(steps % 14 < 2) {
            dir = Direction.SOUTH;
        }
        else if(steps % 14 < 8) {
            dir = Direction.EAST;
        }
        else if(steps % 14 < 10) {
            dir = Direction.NORTH;
        }
        else {
            dir = Direction.WEST;
        }
        return dir;
    }
        
}
    

