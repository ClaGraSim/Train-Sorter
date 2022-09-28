import codedraw.CodeDraw;
import codedraw.textformat.HorizontalAlign;
import codedraw.textformat.TextFormat;
import codedraw.textformat.VerticalAlign;

import java.awt.*;

// One position of a train. x and y coordinates.
public class Position {

    private final double x;
    private final double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    // draws train as a centered, coloured rectangle with centered text
    public void drawTrain(CodeDraw cd, double size, Color color, int number){
        cd.setColor(color);

        cd.fillRectangle(x - size/2, y - size*3/2, size, size * 3);
        cd.setColor(new Color(80,80,80));
        cd.drawRectangle(x - size/2, y - size*3/2, size, size * 3);
        centeredText(cd, Color.white, (int)size, String.valueOf(number));
    }

    public void drawRail(CodeDraw cd, double size, double section_length){
        cd.setLineWidth(section_length/40);

        // Horizontal Lines
        cd.setColor(new Color(100, 50, 0));
        double wood_section = section_length * 0.10;
        for(int i = 1; i <= 5; i++){
            drawRailWood(cd, size, y - wood_section * i + wood_section/2);
            drawRailWood(cd, size, y + wood_section * i - wood_section/2);
        }
        // Vertical Lines
        cd.setColor(new Color(150, 150, 150));
        cd.drawLine(x - size/3, y - section_length/2, x - size/3, y + section_length/2);
        cd.drawLine(x + size/3, y - section_length/2, x + size/3, y + section_length/2);
    }
    private void drawRailWood(CodeDraw cd, double size, double y){
        cd.drawLine(x-size/1.6, y, x+size/1.6, y);
    }

    public void drawBoxedNumber(CodeDraw cd, double size, Color color, int number){
        cd.setColor(color);
        cd.drawSquare(x - size/2, y - size/2, size);
        centeredText(cd, color, (int)(size*0.8), String.valueOf(number));
    }

    private void centeredText(CodeDraw cd, Color color, int FontSize, String text){
        // TODO: width/height instead of font size? calculate font size from that?
        TextFormat format = cd.getTextFormat();
        format.setFontSize(FontSize);
        format.setHorizontalAlign(HorizontalAlign.CENTER);
        format.setVerticalAlign(VerticalAlign.MIDDLE);
        cd.setColor(color);
        cd.drawText(x, y, text);
    }

    /** Checks if a position is inside of another position's hitbox(a rectangle surrounding the position).
     * @param pos center of hitbox
     * @param width of hitbox
     * @param length of hitbox
     * @return true/false */
    public boolean isInsideOf(Position pos, double width, double length){
        if(x < (pos.x + width/2) && x > (pos.x - width/2)){
            //System.out.println("x: " + x + " < " + (pos.x + width/2) + " && " + x + " > " + (pos.x - width/2));
            return y < (pos.y + length / 2) && y > (pos.y - length / 2);
        }
        return false;
    }
}
