import codedraw.CodeDraw;

import java.awt.*;
import java.util.Scanner;

public class Start {

    // Canvas width, height
    public static int CW = 800;
    public static int CH = 800;

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static boolean startScreen = false;
    private static boolean chooseGameMode = false;
    private static boolean chooseScreenResolution = false;
    private static int rails = 3;
    private static int trains = 3;

    public static void main(String[] args){

        // Start Game with commands, optional
        startScreen();
        chooseScreenResolution();
        chooseGameMode();

        // Game starts
        if(running){
            System.out.println();
            System.out.println("Game will open in a new window.");
            setWindowSizeFromScreenWidth();
            CodeDraw cd = new CodeDraw(CW, CH);
            cd.setTitle("Train Sorter beta 0.2");
            Game game = new Game(cd, rails, trains);
            game.startNew();
        }
    }

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+");
    }

    private static void startScreen(){
        while(startScreen){
            System.out.println();
            System.out.println("Welcome to Train Sorter! Start new game?");
            System.out.println("type yes/no");

            String input = scanner.nextLine();

            if(input.equals("yes")){
                startScreen = false;
            } else if(input.equals("no")){
                System.out.println("Program will close now, goodbye.");
                startScreen = false;
                running = false;
            } else{
                System.out.println("Input not valid. Please try again.");
            }

        }
    }
    private static void chooseScreenResolution(){
        while(chooseScreenResolution){
            System.out.println();
            System.out.println("How wide(px) should the window be?");
            System.out.println("(enter number between 400 and 1800)");
            String widthInput = scanner.nextLine();
            if(isNumeric(widthInput)){
                CW = Integer.parseInt(widthInput);
                if(CW >= 400 && CW <= 1800){
                    System.out.println();
                    System.out.println("How high(px) should the window be?");
                    System.out.println("(enter number between 400 and 1000)");
                    String heightInput = scanner.nextLine();
                    if(isNumeric(heightInput)){
                        CH = Integer.parseInt(heightInput);
                        if(CH >= 400 && CH <= 1000){
                            System.out.println("A window sized " + CW + "px x " + CH + "px will be generated.");
                            chooseScreenResolution = false;
                        }
                    }
                }
            }
            if(chooseScreenResolution){
                System.out.println("Invalid Input! Please try again.");
            }
        }
    }
    private static void chooseGameMode(){
        // Choose layout
        while(chooseGameMode){
            System.out.println();
            System.out.println("How many rails do you want? (one empty rail will be generated as well");
            System.out.println("(enter number between 2 and 10)");
            String railsInput = scanner.nextLine();
            if(isNumeric(railsInput)){
                rails = Integer.parseInt(railsInput);
                if(rails >= 2 && rails <= 10){
                    System.out.println(rails + " + 1 rails will be generated.");
                    System.out.println();
                    System.out.println("How many trains per rail do you want?");
                    System.out.println("(enter number between 2 and 10)");
                    String trainsInput = scanner.nextLine();
                    if(isNumeric(trainsInput)){
                        trains = Integer.parseInt(trainsInput);
                        if(trains >= 2 && trains <= 10){
                            System.out.println(trains*rails + " trains will be generated.");
                            chooseGameMode = false;
                        }
                    }
                }
            }
            if(chooseGameMode){
                System.out.println("Invalid Input! Please try again.");
            }
        }
    }
    private static void setWindowSizeFromScreenWidth(){
        // getScreenSize() returns the size
        // of the screen in pixels
        Dimension size
                = Toolkit.getDefaultToolkit().getScreenSize();

        // width will store the width of the screen
        int width = (int)size.getWidth();

        // height will store the height of the screen
        int height = (int)size.getHeight();

        System.out.println("Current Screen resolution : "
                + "width : " + width
                + " height : " + height);
        System.out.println(" Train Sorter window will be scaled accordingly.");
        CH = height - 100;
        CW = width - 100;
    }
}
