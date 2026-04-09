/////////////////////////////////////Tile.java
/////////////////////////////////////Tile.java


package scrabble;
import java.awt.*;
import java.util.HashMap;
import java.util.*;


public class Tile {


    private char letter;
    private int score;
    private Color tileColor = new Color(113,25,36,255);

    private int row;

    private int col;

    public boolean highlight;

    public boolean playedLastTurn;
    /*
        HashMap<Character, Integer> assignValues = new HashMap<Character, Integer>();
    */

    public int wordBonus;



    //maybe add extra parameter for double score and double word score, in add piece
    //double the score if it is true, and for word value I check if any of the
    Tile(char _letter)
    {
        letter = _letter;
        score = assignLetterValue(letter);
        if (wordBonus == 0)
            wordBonus = (int)(Math.random()*6)+3;
        //either a switch statement or multiple else branches. we'd pass in row and col and if it is equal to the
        //place on the board we want, we'd set letterMultiplier to 2 or etc.
    }

    Tile(char _letter, int _wordBonus)
    {
        letter = _letter;
        score = assignLetterValue(letter);
        wordBonus = _wordBonus;
        //either a switch statement or multiple else branches. we'd pass in row and col and if it is equal to the
        //place on the board we want, we'd set letterMultiplier to 2 or etc.
    }
    //bonusTile class
    public int assignLetterValue(char letter) {
        //might be able to switch this to a hashmap later
        //might just put this in the tile constructor later, this just allows me to see if theres errors
        // 1 score
        if (letter == 'e' || letter == 'a' || letter == 'i' || letter == 'o' ||
                letter == 'n' || letter == 'r' || letter == 't' || letter == 'l' ||
                letter == 's' || letter == 'u')
            return 1;
            // 2 score
        else if (letter == 'd' || letter == 'g')
            return 2;
        else if (letter == 'b' || letter == 'c' || letter == 'm' || letter == 'p')
            return 3;
        else if (letter == 'f' || letter == 'h' || letter == 'v' || letter == 'w' || letter == 'y')
            return 4;
        else if (letter == 'k')
            return 5;
        else if (letter == 'j' || letter == 'x')
            return 8;
        else if (letter == 'q' || letter == 'z')
            return 10;

        return 80000;
    }
    //accessor
    public char getLetter() {
        return (letter);
    }

    public int getScore(){
        return score;
    }

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    public Color getColor(){
        return tileColor;
    }
    //mutator
    public void setRowandCol(int _row, int _col){
        row = _row;
        col = _col;
    }
    public void setScore(int _score){
        score = _score;
    }



    public void draw(Graphics2D g,int row,int column,int xdelta,int ydelta) {
        g.setColor(tileColor);
        g.fillRect(Window.getX(column*xdelta),Window.getY(row*ydelta),xdelta,ydelta);
        if (highlight && !playedLastTurn) {
            g.setColor(Color.yellow);
            g.drawRect(Window.getX(column*xdelta + 2),Window.getY(row*ydelta +2 ),xdelta-5,ydelta-5);
        }
        else if (highlight && playedLastTurn) {
            g.setColor(Color.blue);
            g.drawRect(Window.getX(column*xdelta + 2),Window.getY(row*ydelta +2 ),xdelta-5,ydelta-5);
        }
        g.setColor(Color.BLACK);
        g.setFont (new Font ("Arial",Font.PLAIN, 25));
        if (letter!='i')
            g.drawString("" + Character.toUpperCase(letter), Window.getX(column*xdelta)+11,Window.getY(row*ydelta)+25);
        else
            g.drawString("" + Character.toUpperCase(letter), Window.getX(column*xdelta)+16,Window.getY(row*ydelta)+25);
        //just to make it so I is centered.
        g.setColor(Color.BLACK);
        g.setFont (new Font ("Arial",Font.PLAIN, 12));
        //so tiles with scores of 10 dont have scores appearing off the tile
        if (score < 10)
            g.drawString("" + score, Window.getX(column*xdelta)+30,Window.getY(row*ydelta)+28);
        else
            g.drawString("" + score, Window.getX(column*xdelta)+25,Window.getY(row*ydelta)+28);

        g.setColor(Color.GREEN);
        g.drawString("" + wordBonus, Window.getX(column*xdelta)+3,Window.getY(row*ydelta)+10);

    }
}