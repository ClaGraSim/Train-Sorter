import codedraw.CodeDraw;
import java.awt.*;

// One train with its specifications.
public class Train {
    private final Color color;
    private final int train_number;
    private final int car_number;

    /** A colored train. */
    public Train(Color color, int train_number, int car_number){
        this.color = color;
        this.train_number = train_number;
        this.car_number = car_number;
    }

    public Train(){
        color = new Color(80,80,80);
        train_number = 0;
        car_number = 0;
    }
    // Draws a train as coloured rectangle with a number
    public void draw(CodeDraw cd, Position pos){
        pos.drawTrain(cd, Positioning.trainSize, color, car_number);
    }

    public boolean belongsInFrontOf(Train train){
        return this.train_number == train.train_number && this.car_number < train.car_number;
    }
}
