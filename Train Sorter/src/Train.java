import codedraw.CodeDraw;
import java.awt.*;

// One train with its specifications.
public class Train {
    private Color color;
    private int train_number;
    private int car_number;

    public Train(Color color, int train_number, int car_number){
        this.color = color;
        this.train_number = train_number;
        this.car_number = car_number;
    }

    // TODO: Needed? check numbers here or in Rails?
    public Color color(){
        return color;
    }
    public int train_number(){ return train_number; }
    public int car_number(){ return car_number; }

    // Draws a train as coloured rectangle with a number
    public void draw(CodeDraw cd, Position pos){
        pos.drawTrain(cd, Positioning.trainSize, color, car_number);
    }

    public boolean belongsInFrontOf(Train train){
        //TODO: check
        if(this.train_number == train.train_number && this.car_number < train.car_number){
            return true;
        }
        return false;
    }

    //TODO: Train lists, check train order, etc

}
