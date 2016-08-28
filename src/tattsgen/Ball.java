
package tattsgen;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author alexey
 */
public class Ball {
    
    private final SimpleIntegerProperty number;
    private final SimpleIntegerProperty position;
    private final SimpleBooleanProperty supplementary;
    
    public Ball(int number) {
        this.number = new SimpleIntegerProperty(number);
        this.position = new SimpleIntegerProperty(0);
        this.supplementary = new SimpleBooleanProperty(false);
}
    
    public Ball (int number, int position, Boolean supplementary){
        
        this.number = new SimpleIntegerProperty(number);
        this.position = new SimpleIntegerProperty(position);
        this.supplementary = new SimpleBooleanProperty(supplementary);
    }

    /**
     * 
     * @return int Number of ball 
     */
    public int getNumber() {
        return number.intValue();
    }

    /**
     * 
     * @return int ball position 
     */
    public int getPosition() {
        return position.intValue();
    }

    /**
     * 
     * @return boolean true if ball is supplementary and false if ball is not supplementary   
     */
    public boolean isSupplementary() {
        return supplementary.getValue();
    }
    
    @Override
    public String toString(){
        return Integer.toString(this.getNumber());
    }
    
    public boolean isEven(){
        return ((this.getNumber()%2) == 0);
    }
}
