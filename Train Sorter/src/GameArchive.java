import codedraw.CodeDraw;
import codedraw.events.EventScanner;
import codedraw.events.MouseClickEventArgs;
import codedraw.events.MouseMoveEventArgs;

import java.awt.*;

// Stuff that would clutter the Game class; basically an archive
// Functions/Variables that weren't used
public class GameArchive extends Game {
    private double cw = Start.CW;
    private double ch = Start.CH;
    private CodeDraw cd;


    public void testGameLoop(){
        boolean sortingTrain = true;
        boolean running = true;
        while(running){
            Position pos = checkForMouseClick();
            if(map.checkIfSortingStation(pos)){
                System.out.println("Is at sorting station");
                if(sortingTrain){
                    sortingTrain = false;
                } else{sortingTrain = true; }
            }else{
                System.out.println("Not at sorting station. End testGameLoop.");
                running = false;
            }
            //cd.clear(Color.black);
            drawTitle();
            map.drawRails();
            map.drawTrainsTest(sortingTrain);
            //cd.show();
        }
    }

    public void oldGameLoop(){
        //TODO: Setup text for sorted/shuffled
        map.initializeTrains();
        update(2000);
        System.out.println("Trains initialized.\n");
        map.shuffleTrains();
        update(0);
        System.out.println("Trains shuffled.\n");

        while(true){
            Position pos = checkForMouseClick();
            /*
            if(map.checkIfSortingStation(pos)){
                System.out.println("Is at sorting station");
                // not needed for gameplay, click on rails instead
                update(0);
            }
            if(map.checkPosition(pos) >= 0){
                // System.out.println(map.checkPosition(pos));
                // check rail instead
                update(0);
            }
             */
            int railPos = map.whichRail(pos);
            if(railPos >= 0){
                /*
                System.out.println("Rail number: " + railPos);
                if(map.sortingStationIsEmpty()){
                    System.out.println("Sorting station is empty");
                } else{
                    System.out.println("Sorting station contains a train");
                }
                 */
                map.useSortingStation(railPos);
                update(0);
                if(map.checkRails()){
                    break;
                }
            }
        }
        showWinScreen();
    }

    public void showStartScreen(){
        //TODO: rework start screen
        cd.clear(Color.BLACK);
        double x = cw/2;
        double y = ch/4;
        centeredText(x+8, y+8, new Color(80, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x+4, y+4, new Color(100, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x, y, new Color(150, 0, 0), (int)cw/10, "TRAIN SORTER");

        /*
        drawTrain(cw/3, ch/2, Color.blue, 3);
        drawTrain(cw/2, ch/2, Color.red, 1);
        drawTrain(cw/3*2, ch/2, Color.green, 2);
        */
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
        double y = ch/4;
        /*
        centeredText(x+8, y+8, new Color(80, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x+4, y+4, new Color(100, 0, 0), (int)cw/10, "TRAIN SORTER");
        centeredText(x, y, new Color(150, 0, 0), (int)cw/10, "TRAIN SORTER");
        */

        x = cw/2;
        y = ch/4*3;
        centeredText(x+8, y+8, new Color(0, 0, 80), (int)cw/10, "YOU WON!!!");
        centeredText(x+4, y+4, new Color(0, 0, 100), (int)cw/10, "YOU WON!!!");
        centeredText(x, y, new Color(0, 0, 150), (int)cw/10, "YOU WON!!!");

        cd.show(1000);
        bottomText("Click anywhere to play again");
    }

    private Position checkForMouseClick(){
        boolean waitingForInput = true;
        EventScanner es = new EventScanner(cd);
        int x = -1;
        int y = -1;
        //System.out.println("\nready for input");

        // DONE: fix bug (shows incorrect mouse coordinates without movement before click)
        while (waitingForInput) {
            while (es.hasEventNow()) {
                //for mouse movement determination
                if (es.hasMouseMoveEvent()) {
                    MouseMoveEventArgs a = es.nextMouseMoveEvent();
                    x = a.getX();
                    y = a.getY();
                }
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
        //System.out.println("Mouse click at: " + x + " " + y);
        return new Position(x, y);
    }

    // Draws a train as coloured rectangle with a number
    private void drawTrain(Position position, Color color, int number){ position.drawTrain(cd, map.trainSize, color, number); }
    private void drawOldTrain(Position position, Color color, int number){ position.drawTrain(cd, map.oldTrainSize(), color, number); }
    private void drawRail(Position position){ position.drawRail(cd, map.trainSize, map.section_length()); }
    private void drawOldRail(Position position){ position.drawOldRail(cd, map.oldTrainSize()); }

    /*
    private void drawTrain(int x, int y, Color color, int number){
        cd.setColor(color);
        cd.fillRectangle(x - cw/40, y - cw/20, cw/20, cw/10);
        centeredText(x, y, Color.white, cw/20, String.valueOf(number));
    }
     */
}
