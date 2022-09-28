import java.awt.*;
import java.util.Random;

// Generates Colors
public class Colors {

    //predefined colors:
    public static Color[] colors_medium = {
            new Color(153, 0, 0),
            new Color(153, 76, 0),
            new Color(153, 153, 0),
            new Color(0, 153, 0),
            new Color(0, 153, 153),
            new Color(0, 76, 153),
            new Color(0, 0, 153),
            new Color(76, 0, 153),
            new Color(153, 0, 153),
            new Color(76, 0, 76),
            new Color(76, 76, 76),
            new Color(76, 153, 0),
            new Color(0, 153, 76),
    };

    public static Color pick(int index){
        if(index < colors_medium.length){
            return colors_medium[index];
        }
        //default
        return Color.white;
    }

    public static Color getRandom(int number_for_randomizer){
        Random rand = new Random(number_for_randomizer);

        // Java 'Color' class takes 3 floats, from 0 to 1.
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return new Color(r, g, b);
    }

    public static Color getLightRandom(int number_for_randomizer){
        Random rand = new Random(number_for_randomizer);

        // Will produce only bright / light colours:
        float r = (float)(rand.nextFloat() / 2f + 0.5);
        float g = (float)(rand.nextFloat() / 2f + 0.5);
        float b = (float)(rand.nextFloat() / 2f + 0.5);

        return new Color(r, g, b);
    }
}
