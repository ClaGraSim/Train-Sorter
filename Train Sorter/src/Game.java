import codedraw.CodeDraw;
import codedraw.events.EventScanner;
import codedraw.events.MouseClickEventArgs;
import codedraw.textformat.HorizontalAlign;
import codedraw.textformat.TextFormat;
import codedraw.textformat.VerticalAlign;

import java.awt.*;

public class Game {

    private final double cw = Start.CW;
    private final double ch = Start.CH;

    private final CodeDraw cd;
    private int number_of_rails; // between 2 and 10, so 3-11 rails are generated
    private  int number_of_trains_per_rail; // between 2 and 10
    Positioning map;

    public Game(CodeDraw codedraw){
        cd = codedraw;
        number_of_rails = 3;
        number_of_trains_per_rail = 3;
    }

    public void startNew(){
        showStartScreen();
        //showOptionsScreen();
        while (true){
            showLayoutOptionsScreen();
            gameLoop();
            checkForMouseClick();
        }
    }

    public void showStartScreen(){
        Positioning startScreen = new Positioning(cd);

        cd.clear(Color.BLACK);
        tripleText(cw/2, ch/4, 80, 0, 0, (int)cw/10, "TRAIN SORTER");
        startScreen.generateStartScreenTrains();
        startScreen.drawRails();
        startScreen.drawTrains();
        cd.show(5000);
    }

    public void showLayoutOptionsScreen(){
        Positioning options = new Positioning(2, 3, cd);
        // Select number of rails
        cd.clear(Color.black);
        centeredText(cw/2, options.section_length(), Color.white, (int)(options.section_width()/8), "Select number of rails(one more rail will be generated)");
        options.drawNumbers();
        cd.show();
        number_of_rails = selectNumber(options);

        // Select number of trains
        cd.clear(Color.black);
        centeredText(cw/2, options.section_length(), Color.white, (int)(options.section_width()/8), "Select number of trains per rail");
        options.drawNumbers();
        cd.show();
        number_of_trains_per_rail = selectNumber(options);
    }

    private int selectNumber(Positioning options){
        while (true){
            Position pos = checkForMouseClick();
            int selected_number = options.checkPositionNumber(pos);
            if(selected_number >= 0){
                return selected_number;
            }
        }
    }

    public void gameLoop(){
        map = new Positioning(number_of_rails, number_of_trains_per_rail, cd);
        map.generateTrains(false);
        updateGame(0);
        bottomText("Shuffling trains...");
        cd.show(2000);
        map.generateTrains(true);
        updateGame(0);
        bottomText("Ready to play");

        while(true){
            Position pos = checkForMouseClick();
            int railPos = map.whichRail(pos);
            if(railPos >= 0){
                map.useSortingStation(railPos);
                updateGame(0);
                if(map.checkRails()){
                    break;
                }
            }
        }
        showWinScreen();
    }

    public void updateGame(int waitMilliseconds){
        cd.clear(Color.black);
        drawTitle();
        map.drawRails();
        map.drawTrains();
        cd.show(waitMilliseconds);
    }

    private Position checkForMouseClick(){
        boolean waitingForInput = true;
        EventScanner es = new EventScanner(cd);
        int x = -1;
        int y = -1;
        while (waitingForInput) {
            while (es.hasEventNow()) {
                if (es.hasMouseClickEvent()) {
                    MouseClickEventArgs a = es.nextMouseClickEvent();
                    x = a.getX();
                    y = a.getY();
                    waitingForInput = false;
                    break;
                } else {
                    es.nextEvent();
                }
            }
        }
        return new Position(x, y);
    }

    public void showWinScreen(){
        tripleText(cw/2, ch/4*3, 0, 0, 80, (int)cw/10, "YOU WON!!!");
        cd.show(1000);
        bottomText("Click anywhere to play again");
    }

    /** Generate centered text at the coordinates.
     * @param x coordinate
     * @param y coordinate
     * @param color Font color */
    public void centeredText(double x, double y, Color color, int FontSize, String text){
        TextFormat format = cd.getTextFormat();
        format.setFontSize(FontSize);
        format.setHorizontalAlign(HorizontalAlign.CENTER);
        format.setVerticalAlign(VerticalAlign.MIDDLE);
        cd.setColor(color);
        cd.drawText(x, y, text);
    }

    public void bottomText(String text){
        centeredText(cw/2, map.section_length() * number_of_trains_per_rail + map.section_length() * 2.5, Color.white, (int)(cw/20), text);
        cd.show();
    }

    public void tripleText(double x, double y, int r, int g, int b, int FontSize, String text){
        centeredText(x+8, y+8, new Color(r, g, b), FontSize, text);
        r *= 1.25;
        g *= 1.25;
        b *= 1.25;
        centeredText(x+4, y+4, new Color(r, g, b), FontSize, text);
        r *= 1.5;
        g *= 1.5;
        b *= 1.5;
        centeredText(x, y, new Color(r, g, b), FontSize, text);
    }

    public void drawTitle(){
        double x = cw/2;
        double y = ch/16;
        centeredText(x+8, y+8, new Color(80, 0, 0), (int)cw/20, "TRAIN SORTER");
        centeredText(x+4, y+4, new Color(100, 0, 0), (int)cw/20, "TRAIN SORTER");
        centeredText(x, y, new Color(150, 0, 0), (int)cw/20, "TRAIN SORTER");
    }

}
