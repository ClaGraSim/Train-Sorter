import codedraw.CodeDraw;

import java.awt.*;

// Creates and stores possible train positions and their coordinates, maybe size too
// (Hopefully hides a lot of math from the game-structure)
public class Positioning {
    //TODO: finish documentation, remove redundant variables
    // check what should be public and check for redundancy, especially cd and train size

    /** number of rails */
    private int rails;
    /** number of trains per rail */
    private int trainsPR;
    /** total number of trains in the system */
    private int trainsTotal;
    private double section_width;
    private double section_length;
    private double cw = Start.CW;
    private double ch = Start.CH;
    private Position[][] positions; // all possible train positions
    private Rail[] railStorage; // all rails, rails are stacks
    private Rail trainStorage; // all trains as objects in one list, to be shuffled and sorted into rails
    private Position sortingStationPosition; // the sorting station position
    private Train sortingStationContent; // Content of sorting station
    private CodeDraw cd;

    public static double oldTrainSize;
    public static double trainSize;

    public Positioning(int number_of_rails, int number_of_trains_per_rail, CodeDraw cd) {
        rails = number_of_rails + 1;
        trainsPR = number_of_trains_per_rail;
        trainsTotal = number_of_trains_per_rail * number_of_rails;
        trainStorage = new Rail(trainsTotal);
        positions = new Position[rails][trainsPR];
        calculatePositions();

        oldTrainSize = (int)(ch/rails/4);
        trainSize = section_length * 0.29;
        sortingStationPosition = new Position(cw/2, section_length * (trainsPR + 2) + section_length/4);
        this.cd = cd;
        railStorage = new Rail[rails];
    }

    /** Calculates possible train positions and puts them in an array. */
    private void calculatePositions(){
        section_width = cw/(rails + 1); // spaces between rail positions
        section_length = ch/(trainsPR + 3); // leaves space on top and bottom of screen
        double currentX = section_width;

        // [Rails][Trains]
        for(int i = 0; i < positions.length; i++){
            double currentY = section_length * 2;
            for(int j = 0; j < positions[i].length; j++){
                //System.out.println("position " + i + j );
                positions[i][j] = new Position(currentX, currentY);
                currentY += section_length;
            }
            currentX += section_width;
        }
    }

    /** Initialize empty rail storage, fill with sorted trains */
    public void initializeTrains(){
        // Initialize Rail storage
        System.out.println("Clearing rails...");
        clearRailStorage();

        System.out.println("Generating trains...");
        fillTrainStorage();

        System.out.println("Filling rails with sorted trains...");
        fillRailsFromTrainStorage();
    }

    /** Initialize empty rail storage, fill with shuffled trains */
    public void shuffleTrains(){
        // Initialize Rail storage
        System.out.println("Clearing rails...");
        clearRailStorage();

        System.out.println("Generating trains...");
        fillTrainStorage();

        System.out.println("Shuffling trains...");
        trainStorage.shuffle();

        System.out.println("Filling rails with shuffled trains...");
        fillRailsFromTrainStorage();
    }

    /** clear rail storage and fill with empty rails */
    private void clearRailStorage(){
        for(int i = 0; i < railStorage.length; i++){
            railStorage[i] = new Rail(trainsPR);
        }
    }

    /** Generates all trains for the game */
    private void fillTrainStorage(){
        Color color;
        for(int i = 1; i < positions.length; i++){
            // Random Colors, can be predefined or random colors
            // TODO: (also maybe letters for color blindness mode)
            //color = Colors.getLightRandom(5);
            //color = Colors.getRandom(i*10);
            color = Colors.pick(i - 1);
            for(int j = 0; j < positions[i].length; j++){
                trainStorage.put(new Train(color, i, j * -1 + trainsPR));
            }
        }
    }

    /** fills (should be empty) rails with trains from trainStorage */
    private void fillRailsFromTrainStorage(){
        for(int i = 1; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                railStorage[i].put(trainStorage.take());
            }
        }
    }

    public double trainSize(){
        return trainSize;
    }
    public double oldTrainSize(){
        return oldTrainSize;
    }
    public double section_length(){
        return section_length;
    }
    public double section_width(){
        return section_width;
    }

    /** Check if coordinates are at a valid position, give back that position, else return -1
     * @param position Position(coordinates) of object/mouse click to check
     * @return array index if position is valid, -1 if position isn't valid. Check for >= 0
     */
    public int checkPosition(Position position){
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                if(position.isInsideOf(positions[i][j] , section_width, section_length)){
                    return i * 10 + j;
                }
            }
        }
        return -1;
    }
    /** Check if coordinates are at a valid rail position, give back the rail number, else return -1
     * @param position Position(coordinates) of object/mouse click to check
     * @return array index if position is valid, -1 if position isn't valid. Check for >= 0
     */
    public int whichRail(Position position){
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                if(position.isInsideOf(positions[i][j] , section_width, section_length)){
                    return i;
                }
            }
        }
        return -1;
    }

    /** Draws all rails including the sorting station. */
    public void drawRails(){
        // [Rails][Trains]
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                //System.out.println("rail " + i + j);
                drawRailHere(positions[i][j]);
            }
        }
        drawRailHere(sortingStationPosition);
    }


    public void drawTrainsTest(boolean sortingTrain){
        // [Rails][Trains]
        for(int i = 1; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                positions[i][j].drawTrain(cd, trainSize, Color.blue, j);
            }
        }
        if(sortingTrain){
            sortingStationPosition.drawTrain(cd, trainSize, Color.green, 0);
        }
    }

    /** Draws all trains including the sorting station(if in use). */
    public void drawTrains(){
        // draw trains, draw sorting rail content if there
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                Train selectedTrain = railStorage[i].getFromPosition(j);

                if(selectedTrain != null){
                    selectedTrain.draw(cd, positions[i][j]);
                }
            }
        }
        // draw sorting station content
        if(sortingStationContent != null){
            sortingStationContent.draw(cd, sortingStationPosition);
        }
    }

    /** Checks if the coordinates are at the sorting station
     * @param position Position(coordinates) of object/mouse click to check
     * @return true if true
     */
    public boolean checkIfSortingStation(Position position){
        if(position.isInsideOf(sortingStationPosition, section_width, section_length)){
            return true;
        }
        return false;
    }

    /** Check if sorting station is, in fact, empty.
     * @return true or false
     */
    public boolean sortingStationIsEmpty(){
        return sortingStationContent == null;
    }

    /** Check if trains are sorted. If yes, the game is won.
     * @return true/false
     */
    public boolean checkRails(){
        //TODO: test and fix bugs(winning too early/ not winning when you should)
        boolean answer = false;
        // Only checks if all trains are on rails
        if(sortingStationIsEmpty()){
            for(int i = 0; i < railStorage.length; i++) {
                //System.out.println("Rail " + i + " is being checked");
                if (railStorage[i].checkIfTrainIsCorrect()) {
                    answer = true;
                    //System.out.print(" - correct\n");
                } else {
                    //System.out.print(" - not correct\n");
                    return false;
                }
            }
        }
        return answer;
    }

    /** Move a train from a rail to the sorting station if it is empty
     * or move a train from the sorting station to the specified rail, if that rail has space.
     * @param railIndex index of rail where train is taken from.
     */
    public void useSortingStation(int railIndex){
        if(sortingStationIsEmpty()){
            sortingStationContent = railStorage[railIndex].take();
        } else if(!sortingStationIsEmpty() && railStorage[railIndex].isFull()){
            // nothing happens
        }else{
            railStorage[railIndex].put(sortingStationContent);
            sortingStationContent = null;
        }
    }

    /** Draws a rail at the specified position.
     * @param position Position(coordinates) of rail center
     */
    private void drawRailHere(Position position){
        position.drawRail(cd, trainSize, section_length);
    }

    public void drawNumbers(){
        // TODO
        int number = 2;
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                positions[j][i].drawBoxedNumber(cd, section_length * 0.9, Color.white, number);
                number++;
            }
        }
    }
    public int checkPositionNumber(Position position){
        // TODO
        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                if(position.isInsideOf(positions[j][i], section_width, section_length)){
                    //System.out.println("checkPositionNumber i: " + (i));
                    //System.out.println("checkPositionNumber j: " + (j));
                    return 2 + i * rails + j;
                }
            }
        }
        return -1;
    }


}
