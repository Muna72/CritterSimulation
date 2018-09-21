
import java.awt.Color;	
import java.util.Random;
/**
 *
 * @author Muna
 * @version November 2016
 */
public class Bird extends GVcritter{
    
    //Declare all instance variables
    private Direction dir;
    private  Random randGen = new Random();
    
    /**Class constructor
     * 
     * @param loc location 
     */
    public Bird(Location loc) {
        super(loc);
        setColor(Color.BLUE);
        setSpecies(Species.BIRD);
        steps = randGen.nextInt(14 - 1 + 1) + 1;    
    }
    
    /**Method to execute bird's attack
     * 
     * @param opponent opponent
     * @return an attack
     */
    public Attack getAttack(GVcritter opponent) {
        return Attack.ROAR;
    }
    
    /**Method to get bird's move direction
     * 
     * @return a direction
     */
    public Direction getMoveDirection() {
        
        steps++;
        
        if(steps % 14 < 3) {
            dir = Direction.NORTH;
        }
        else if(steps % 14 < 7) {
            dir = Direction.EAST;
        }
        else if(steps % 14 < 10) {
            dir = Direction.SOUTH;
        }
        else {
            dir = Direction.WEST;
        }
        return dir;
    }
}
