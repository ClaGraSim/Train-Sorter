import codedraw.CodeDraw;

import java.awt.*;
import java.util.Scanner;

public class Start {

    // Canvas width, height
    public static int CW = 800;
    public static int CH = 800;

    public static void main(String[] args){
        setWindowSizeFromScreenWidth();
        CodeDraw cd = new CodeDraw(CW, CH);
        cd.setTitle("Train Sorter beta 0.2");
        Game game = new Game(cd);
        game.startNew();
    }

    private static void setWindowSizeFromScreenWidth(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        CH = height - 100;
        CW = width - 100;
    }
}
