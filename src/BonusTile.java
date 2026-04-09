package scrabble;

import java.awt.*;

public class BonusTile extends Tile {
    public int wordMultiplier;
    public int letterMultiplier;

    public boolean skip;

    private Color bonustileColor = new Color(221,222,250,255);

    BonusTile(char letter, int _wordMultiplier, int _letterMultiplier, boolean skipTurn, int addScore, int _wordBonus){
        //does this even count as superclass?
        super(letter, _wordBonus);
        setScore(getScore()*_letterMultiplier);
        wordMultiplier = _wordMultiplier;
        letterMultiplier = _letterMultiplier;
        skip = skipTurn;
        setScore(getScore()+ addScore);
    }
    public void setUsed(){
        setScore(getScore()/letterMultiplier);
        letterMultiplier = 1;
        wordMultiplier = 1;
    }

    public void draw(Graphics2D g, int row, int column, int xdelta, int ydelta) {
        if (getRow() == 7 && getCol() == 7)
            g.setColor(getColor());
        else
            g.setColor(bonustileColor);
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
        if (getLetter()!='i')
            g.drawString("" + Character.toUpperCase(getLetter()), Window.getX(column*xdelta)+11,Window.getY(row*ydelta)+25);
        else
            g.drawString("" + Character.toUpperCase(getLetter()), Window.getX(column*xdelta)+16,Window.getY(row*ydelta)+25);
        //just to make it so I is centered.
        g.setColor(Color.BLACK);
        g.setFont (new Font ("Arial",Font.PLAIN, 12));
        //so tiles with scores of 10 dont have scores appearing off the tile
        if (getScore() < 10)
            g.drawString("" + getScore(), Window.getX(column*xdelta)+30,Window.getY(row*ydelta)+28);
        else
            g.drawString("" + getScore(), Window.getX(column*xdelta)+25,Window.getY(row*ydelta)+28);

        g.setColor(Color.GREEN);
        g.drawString("" + wordBonus, Window.getX(column*xdelta)+3,Window.getY(row*ydelta)+10);

    }


}