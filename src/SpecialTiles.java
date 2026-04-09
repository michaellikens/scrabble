package scrabble;
import java.awt.*;
public class SpecialTiles {
    private int row;
    private int col;
    static final Color tripleLetterScore = new Color(50,187,239,255);
    static final Color tripleWordScore = new Color(244,88,103,255);
    static final Color doubleLetterScore = new Color(227,248,252,255);
    static final Color doubleWordScore = new Color(249,175,190,255);
    static final Color star = new Color(255,102,196,255);
    static final Color skipTurn = new Color(42, 209, 42);
    static final Color plusThree = new Color(179, 89, 240);
    private int wordMultiplier;
    private int letterMultiplier;
    public int addScore;

    public boolean skip;
    Color color;
    public SpecialTiles(Color _color, int _row, int _col) {
        color = _color;
        row = _row;
        col = _col;
        if (color.equals(tripleLetterScore))
        {
            wordMultiplier = 1;
            letterMultiplier = 3;
        }
        else if (color.equals(doubleLetterScore))
        {
            wordMultiplier = 1;
            letterMultiplier = 2;
        }
        else if (color.equals(tripleWordScore))
        {
            wordMultiplier = 3;
            letterMultiplier = 1;
        }
        else if (color.equals(doubleWordScore))
        {
            wordMultiplier = 2;
            letterMultiplier = 1;
        }
        else if (color.equals(skipTurn))
        {
            wordMultiplier = 1;
            letterMultiplier = 1;
            skip = true;
        }
        else if (color.equals(plusThree))
        {
            wordMultiplier = 1;
            letterMultiplier = 1;
            addScore = 3;
        }
        else
        {
            wordMultiplier = 1;
            letterMultiplier = 1;
        }
    }
    //accessor
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getWordMultiplier(){
        return wordMultiplier;
    }
    public int getLetterMultiplier(){
        return letterMultiplier;
    }

    public void draw(Graphics2D g, int xdelta, int ydelta) {

        g.setColor(color);

        if (color.equals(tripleLetterScore)) {
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 9));
            g.drawString("TRIPLE", Window.getX(col * xdelta) + 3, Window.getY(row * ydelta) +10);
            g.drawString("LETTER", Window.getX(col * xdelta)+ 3, Window.getY(row * ydelta) + 20);
            g.drawString("SCORE", Window.getX(col * xdelta)+3, Window.getY(row * ydelta) + 30);
        }
        else if (color.equals(tripleWordScore)) {
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString("TRIPLE", Window.getX(col * xdelta) + 3, Window.getY(row * ydelta) +10);
            g.drawString("WORD", Window.getX(col * xdelta)+ 3, Window.getY(row * ydelta) + 20);
            g.drawString("SCORE", Window.getX(col * xdelta)+3, Window.getY(row * ydelta) + 30);
        }
        else if (color.equals(doubleLetterScore)) {
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 9));
            g.drawString("DOUBLE", Window.getX(col * xdelta) + 3, Window.getY(row * ydelta) +10);
            g.drawString("LETTER", Window.getX(col * xdelta)+ 3, Window.getY(row * ydelta) + 20);
            g.drawString("SCORE", Window.getX(col * xdelta)+3, Window.getY(row * ydelta) + 30);
        }
        else if (color.equals(doubleWordScore)) {
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 9));
            g.drawString("DOUBLE", Window.getX(col * xdelta) + 3, Window.getY(row * ydelta) +10);
            g.drawString("WORD", Window.getX(col * xdelta)+ 3, Window.getY(row * ydelta) + 20);
            g.drawString("SCORE", Window.getX(col * xdelta)+3, Window.getY(row * ydelta) + 30);
        }
        else if (color.equals(star)) {
            g.setColor(doubleWordScore);
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
            g.drawString("★", Window.getX(col * xdelta), Window.getY(row * ydelta) + 33);
        }
        else if (color.equals(skipTurn)) {
            g.setColor(skipTurn);
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 9));
            g.drawString("SKIP", Window.getX(col * xdelta) + 10, Window.getY(row * ydelta) +10);
            g.drawString("OPP", Window.getX(col * xdelta)+ 10, Window.getY(row * ydelta) + 20);
            g.drawString("TURN", Window.getX(col * xdelta)+7, Window.getY(row * ydelta) + 30);
        }
        else if (color.equals(plusThree)) {
            g.setColor(plusThree);
            g.fillRect(Window.getX(col * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 9));
            g.drawString("PLUS", Window.getX(col * xdelta) + 8, Window.getY(row * ydelta) +10);
            g.drawString("THREE", Window.getX(col * xdelta)+ 6, Window.getY(row * ydelta) + 20);
        }
    }
}