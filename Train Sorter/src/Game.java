import codedraw.CodeDraw;
import codedraw.events.EventScanner;
import codedraw.events.MouseClickEventArgs;
import codedraw.textformat.HorizontalAlign;
import codedraw.textformat.TextFormat;
import codedraw.textformat.VerticalAlign;

import java.awt.*;

public class Game {

    //difficulty ?
    private double cw = Start.CW;
    private double ch = Start.CH;

    private CodeDraw cd;
    private int number_of_rails; // between 1 and 10? so 2-11 rails are generated
    private  int number_of_trains_per_rail; // between 2 and 10
    Positioning map;


    // Start new game with specs? -> startNew? no, new game-object can be created
    public Game(CodeDraw cd, int number_of_rails, int number_of_trains_per_rail){
        this.cd = cd;
        this.number_of_rails = number_of_rails;
        this.number_of_trains_per_rail = number_of_trains_per_rail;
    }

    // Default/Test-case
    public Game(){
        cd = new CodeDraw((int)cw, (int)ch);
        number_of_rails = 3;
        number_of_trains_per_rail = 3;
    }
    // TODO: figure out redundant variables like trains size, is it needed in this class?

    public void startNew(){
        //TODO: interface for game layout options
        map = new Positioning(number_of_rails, number_of_trains_per_rail, cd);
        showStartScreen();
        while (true){
            showLayoutOptionsScreen();
            //showOptionsScreen();

            map = new Positioning(number_of_rails, number_of_trains_per_rail, cd);

            cd.clear(Color.black);
            //System.out.println("drawing rails");
            map.drawRails();
            map.drawTrainsTest(true);

            drawTitle();
            cd.show();
            //testGameLoop();
            gameLoop();
            checkForMouseClick();
        }
    }

    public void showLayoutOptionsScreen(){
        //TODO: interface for game layout options
        Positioning options = new Positioning(2, 3, cd);
        // Select number of rails, explain
        cd.clear(Color.black);
        centeredText(cw/2, options.section_length(), Color.white, (int)(options.section_width()/8), "Select number of rails(one more rail will be generated)");
        options.drawNumbers();
        cd.show();
        while (true){
            Position pos = checkForMouseClick();
            int selected_number = options.checkPositionNumber(pos);
            if(selected_number >= 0){
                number_of_rails = selected_number;
                break;
            }
        }
        // Select number of trains, explain
        cd.clear(Color.black);
        centeredText(cw/2, options.section_length(), Color.white, (int)(options.section_width()/8), "Select number of trains per rail");
        options.drawNumbers();
        cd.show();
        while (true){
            Position pos = checkForMouseClick();
            int selected_number = options.checkPositionNumber(pos);
            if(selected_number >= 0){
                number_of_trains_per_rail = selected_number;
                break;
            }
        }
    }


    public void gameLoop(){
        //TODO: Setup text for sorted/shuffled
        map.initializeTrains();
        update(0);
        bottomText("Shuffling trains...");
        cd.show(2000);
        map.shuffleTrains();
        update(0);
        bottomText("Ready to play");

        while(true){
            Position pos = checkForMouseClick();
            int railPos = map.whichRail(pos);
            if(railPos >= 0){
                map.useSortingStation(railPos);
                update(0);
                if(map.checkRails()){
                    break;
                }
            }
        }
        showWinScreen();
    }

    public void update(int waitMilliseconds){
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

    public void showStartScreen(){
        //TODO: rework start screen
        cd.clear(Color.BLACK);
        double x = cw/2;
        double y = ch/4;
        centeredText(x+8, y+8, new Color(80, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x+4, y+4, new Color(100, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x, y, new Color(150, 0, 0), (int)cw/10, "TRAIN SORTER");

        // Change to positioning for actual game
        Position p1 = new Position(cw/3, ch/3*2);
        Position p2 = new Position(cw/2, ch/3*2);
        Position p3 = new Position(cw/3*2, ch/3*2);

        drawOldRail(p1);
        drawOldRail(p2);
        drawOldRail(p3);

        drawOldTrain(p1, Color.blue, 3);
        drawOldTrain(p2, Color.red, 1);
        drawOldTrain(p3, Color.green, 2);

        //Shows screen and waits 10 seconds
        cd.show(3000);
    }

    public void showWinScreen(){
        //cd.clear(Color.black);
        double x = cw/2;
        double y = ch/4*3;

        centeredText(x+8, y+8, new Color(0, 0, 80), (int)cw/10, "YOU WON!!!");
        centeredText(x+4, y+4, new Color(0, 0, 100), (int)cw/10, "YOU WON!!!");
        centeredText(x, y, new Color(0, 0, 150), (int)cw/10, "YOU WON!!!");

        cd.show(1000);
        bottomText("Click anywhere to play again");
    }

    /** Generate centered text at the coordinates.
     *
     * @param x coordinate
     * @param y coordinate
     * @param color of text
     * @param FontSize of text
     * @param text a String
     */
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

    public void drawTitle(){
        double x = cw/2;
        double y = ch/16;
        centeredText(x+8, y+8, new Color(80, 0, 0), (int)cw/20, "TRAIN SORTER");
        centeredText(x+4, y+4, new Color(100, 0, 0), (int)cw/20, "TRAIN SORTER");
        centeredText(x, y, new Color(150, 0, 0), (int)cw/20, "TRAIN SORTER");
    }

    // TODO: remove stuff that might not be needed in this class anymore

    // Draws a train as coloured rectangle with a number
    private void drawOldTrain(Position position, Color color, int number){ position.drawTrain(cd, map.oldTrainSize(), color, number); }
    private void drawOldRail(Position position){ position.drawOldRail(cd, map.oldTrainSize()); }
}
