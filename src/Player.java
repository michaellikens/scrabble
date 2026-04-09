package scrabble;


import java.awt.*;
import java.util.ArrayList;


public class Player {
    final private static int numPlayers=2;
    private static Player players[] = new Player[numPlayers];
    private static Player currentPlayer;

    private static final int startTime = 2400;
    private int score = 0;
    public int prevScore = 0;
    public int time;
    //once all tile are exhausted this is used
    final private int DECKLENGTH = 7;
    private ArrayList<Tile> deck = new ArrayList<Tile>();
    private scrabble.Tile currentTile;
    //used for skipping turn
    public boolean skipTurn;

    public boolean trading;


    //Class methods.
    public static void Reset() {
        if (players[0] == null)
        {
            players[0] = new Player();
            players[1] = new Player();
        }
        currentPlayer = players[0];
        players[0].time = startTime;
        players[1].time = startTime;
        players[0].deck.clear();
        players[1].deck.clear();
        players[0].refreshDeck();
        players[1].refreshDeck();
    }

    public void Draw(Graphics2D g) {
        int deckX = 40;
        int deckY = 610;
        int tileSpacing = Window.getWidth2()/9;
        for (int i = 0; i < deck.size(); i++) {

            Tile tile = deck.get(i);
            int tileX = deckX + i * tileSpacing;
            int tileY = deckY;
            int ydelta = Window.getHeight2()/15;
            int xdelta = Window.getWidth2()/15;

            g.setColor(Color.BLACK);

            g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
            if (currentTile == deck.get(i)) {

                g.setColor(Color.yellow);

                g.drawRect(tileX-7,tileY-22,xdelta+2,ydelta+2);

            }
            char letter = deck.get(i).getLetter();
            int score = deck.get(i).getScore();

            g.setColor(new Color(113,25,36,255));

            g.fillRect(tileX-5,tileY-20,xdelta,ydelta);

            g.setColor(Color.BLACK);

            g.setFont (new Font ("Arial",Font.PLAIN, 25));
            if (letter!='i')

                g.drawString("" + Character.toUpperCase(letter), tileX+5,tileY+5);
            else

                g.drawString("" + Character.toUpperCase(letter), tileX+15,tileY+5);

//just to make it so I is centered.

            g.setColor(Color.BLACK);

            g.setFont (new Font ("Arial",Font.PLAIN, 12));

//so tiles with scores of 10 dont have scores appearing off the tile
            if (score < 10)

                g.drawString("" + score, tileX+25,tileY+12);
            else

                g.drawString("" + score, tileX+20,tileY+12);

            g.setColor(Color.GREEN);

            g.drawString("" + tile.wordBonus, tileX-5,tileY-8);
            int hotkey = i+1;

            g.setColor(Color.YELLOW);

            g.setFont (new Font ("Arial",Font.PLAIN, 25));

            g.drawString("" + hotkey + ".", tileX-25,tileY+5);

        }

        g.setColor(Color.CYAN);

        g.setFont (new Font ("Arial",Font.PLAIN, 12));

        g.drawString("Press corresponding keys 1-7", Window.getWidth2() - 125, Window.getHeight2() + 130);

    }

    public static Player getCurrentPlayer() {
        return (currentPlayer);
    }
    public static void switchCurrentPlayer() {
        if (currentPlayer == players[0])
            currentPlayer = players[1];
        else
            currentPlayer = players[0];
    }
    public static Player getPlayer1() {
        return(players[0]);
    }
    public static Player getPlayer2() {
        return(players[1]);
    }
    public static void timeDecrease(){
        if (currentPlayer.time > 0)
            currentPlayer.time--;
        else {
            if (!scrabble.Scrabble.gameOver)
                Board.finishTurn();
        }
    }


    public Player() {
        currentTile = null;
    }
    //accessor methods.
    public int getScore() {
        return (score);
    }
    public double getTimeSeconds(){
        return (time/10.0);
    }
    public scrabble.Tile getCurrentTile()
    {
        return(currentTile);
    }
    public Tile getDeckNumber(int hotKey){
        hotKey--;
        return (deck.get(hotKey));
    }

    public int getDeckSize(){
        return(deck.size());
    }

    //temporary(?) getDeck/printDeck
    public void getDeck(){
        if (Player.getCurrentPlayer() == Player.getPlayer1()) {
            System.out.println("Player 1's Deck:");
        }
        else {
            System.out.println("Player 2's Deck:");
        }
        for (int i =0 ;i<deck.size();i++){
            System.out.print(i+1 + ":" + Character.toUpperCase(deck.get(i).getLetter()) + " | ");
        }
        System.out.println("");
    }

    public ArrayList<Tile> Deck() {
        return deck; // Assuming "deck" is your ArrayList<Tile> in the Player class
    }

    //mutator methods.
    public void setCurrentTile(scrabble.Tile _currentTile){
        currentTile = _currentTile;
        if (Player.getCurrentPlayer().trading)
            Player.getCurrentPlayer().removeCurrentTileFromDeck();
    }
    public void addScore(int _points) {
        score+=_points;
    }
    public void setScore(int _score){
        score = _score;
    }
    public void refreshDeck(){
        for (int i = deck.size(); i<7; i++)
        {
            //if there are no tiles in the tile bag, do not add a tile to the deck
            if (scrabble.TileBag.getTileBagSize()==0) {
                Scrabble.timerStarted = true;
                System.out.println("all tiles in tile bag exhausted, " + Board.gameOverTimer + " turns left");
                break;
            }
            Tile tile = scrabble.TileBag.pullTile();
            deck.add(tile);
        }
        //shows the tilebag size every time we refresh it (failsafe)
        //System.out.println("Tiles left in TileBag: " + TileBag.getTileBagSize());
    }

    public void putTilesinTileBag(){

    }
    public void removeCurrentTileFromDeck(){
        deck.remove(currentTile);
    }
    public void addTiletoDeck(Tile _tile){
        deck.add(_tile);

    }
    public void clearDeck(){
        deck.clear();
    }

}