
import static java.awt.Color.BLACK;
import java.util.Random;
/**
 *
 * @author Muna
 * @version November 2016
 */
public class Vulture extends GVcritter{
    
    //Declare all instance variables
    private Direction dir;
    private  Random randGen = new Random();
    
    /**Class Constructor
     * 
     * @param loc location 
     */
    public Vulture(Location loc) {
        super(loc);
        setColor(BLACK);
        setSpecies(Species.VULTURE);
        steps = randGen.nextInt(14 - 1 + 1) + 1;
    }
    
    /**Method to execute the vulture's attack
     * 
     * @param opponent opponent
     * @return an attack
     */
    public Attack getAttack(GVcritter opponent) {

    Attack at = null;   

    if(opponent.getSpecies() == Species.HIPPO) {
       at = Attack.SCRATCH;
    }
    else {
       at = Attack.ROAR; 
    }
    return at;
}
   /**Method to get the vulture's move direction 
    * 
    * @return a direction 
    */
   public Direction getMoveDirection() {
       
        steps++;
        
        if(steps % 14 < 4) {
            dir = Direction.NORTH;
        }
        else if(steps % 14 < 7) {
            dir = Direction.WEST;
        }
        else if(steps % 14 < 11) {
            dir = Direction.SOUTH;
        }
        else {
            dir = Direction.EAST;
        }
        return dir;
   }
}
