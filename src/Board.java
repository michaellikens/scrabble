package scrabble;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Board {
    private final static int NUM_ROWS = 15;
    private final static int NUM_COLUMNS = 15;
    //idek how i'll do the double/triple/quadruple letter and double word score spaces
    private static scrabble.Tile[][] board = new scrabble.Tile[NUM_ROWS][NUM_COLUMNS];
    //i think i'll need an arraylist/stringbuilder

    private static boolean boardIsEmpty;

    private static boolean vertical;
    private static boolean horizontal;
    static boolean ChallengeFail;
    static public ArrayList<scrabble.Tile> currentTurn = new ArrayList<scrabble.Tile>();
    //last turn played only consists of the tiles played, not the word formed (example: boar vs board or am vs m). Used to remove after a challenge is successful
    static public ArrayList<scrabble.Tile> lastTurn = new ArrayList<scrabble.Tile>();
    //this is what i'll ideally want to change it to
    static ArrayList<ArrayList<scrabble.Tile>> listOfWordsPlayed = new ArrayList<ArrayList<scrabble.Tile>>();

    private static int[][] tripleWord = new int[8][2];

    static public int gameOverTimer;
    int[][] positions = {
            {0, 0}, {0, 7}, {0, 14}, {7, 0}, {14, 7}, {14, 14}, {14, 0}, {7, 14}, // tripleWord
            {0, 3}, {0, 11}, {2, 6}, {2, 8}, {3, 7}, {3, 0}, {3, 14}, {6, 2}, {6, 6}, {6, 8}, {6, 12},
            {7, 3}, {7, 11}, {8, 2}, {8, 6}, {8, 8}, {8, 12}, {11, 0}, {11, 7}, {11, 14}, {12, 6}, {12, 8},
            {14, 3}, {14, 11}, {1, 1}, {2, 2}, {3, 3}, {4, 4}, {1, 13}, {2, 12}, {3, 11}, {4, 10}, {13, 13},
            {12, 12}, {11, 11}, {10, 10}, {13, 1}, {12, 2}, {11, 3}, {10, 4}, {1, 5}, {1, 9}, {5, 1}, {5, 5},
            {5, 9}, {5, 13}, {9, 1}, {9, 5}, {9, 9}, {9, 13}, {13, 5}, {13, 9}, {7, 7} // MiddleStar
    };


    // Middle star
    static SpecialTiles[] specialTiles = {
// Create triple word score tiles
            new SpecialTiles(SpecialTiles.tripleWordScore, 0, 0),
            new SpecialTiles(SpecialTiles.tripleWordScore, 0, 7),
            new SpecialTiles(SpecialTiles.tripleWordScore, 0, 14),
            new SpecialTiles(SpecialTiles.tripleWordScore, 7, 0),
            new SpecialTiles(SpecialTiles.tripleWordScore, 14, 7),
            new SpecialTiles(SpecialTiles.tripleWordScore, 14, 14),
            new SpecialTiles(SpecialTiles.tripleWordScore, 14, 0),
            new SpecialTiles(SpecialTiles.tripleWordScore, 7, 14),
// Create skip turn tiles
            new SpecialTiles(SpecialTiles.skipTurn, 0, 3),
            new SpecialTiles(SpecialTiles.skipTurn, 0, 11),
            new SpecialTiles(SpecialTiles.skipTurn, 14, 3),
            new SpecialTiles(SpecialTiles.skipTurn, 14, 11),
// Create plus 3 tiles
            new SpecialTiles(SpecialTiles.plusThree, 3, 7),
            new SpecialTiles(SpecialTiles.plusThree, 11, 7),
            new SpecialTiles(SpecialTiles.plusThree, 7, 3),
            new SpecialTiles(SpecialTiles.plusThree, 7, 11),
// Create double letter score tiles
            new SpecialTiles(SpecialTiles.doubleLetterScore, 2, 6),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 2, 8),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 3, 0),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 3, 14),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 6, 2),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 6, 6),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 6, 8),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 6, 12),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 8, 2),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 8, 6),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 8, 8),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 8, 12),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 11, 0),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 11, 14),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 12, 6),
            new SpecialTiles(SpecialTiles.doubleLetterScore, 12, 8),
// create double word score tiles
            new SpecialTiles(SpecialTiles.doubleWordScore, 1, 1),
            new SpecialTiles(SpecialTiles.doubleWordScore, 2, 2),
            new SpecialTiles(SpecialTiles.doubleWordScore, 3, 3),
            new SpecialTiles(SpecialTiles.doubleWordScore, 4, 4),
            new SpecialTiles(SpecialTiles.doubleWordScore, 1, 13),
            new SpecialTiles(SpecialTiles.doubleWordScore, 2, 12),
            new SpecialTiles(SpecialTiles.doubleWordScore, 3, 11),
            new SpecialTiles(SpecialTiles.doubleWordScore, 4, 10),
            new SpecialTiles(SpecialTiles.doubleWordScore, 13, 13),
            new SpecialTiles(SpecialTiles.doubleWordScore, 12, 12),
            new SpecialTiles(SpecialTiles.doubleWordScore, 11, 11),
            new SpecialTiles(SpecialTiles.doubleWordScore, 10, 10),
            new SpecialTiles(SpecialTiles.doubleWordScore, 13, 1),
            new SpecialTiles(SpecialTiles.doubleWordScore, 12, 2),
            new SpecialTiles(SpecialTiles.doubleWordScore, 11, 3),
            new SpecialTiles(SpecialTiles.doubleWordScore, 10, 4),

// Create triple letter score tiles
            new SpecialTiles(SpecialTiles.tripleLetterScore, 1, 5),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 1, 9),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 5, 1),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 5, 5),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 5, 9),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 5, 13),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 9, 1),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 9, 5),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 9, 9),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 9, 13),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 13, 5),
            new SpecialTiles(SpecialTiles.tripleLetterScore, 13, 9),

// Create star tile in the middle of board
            new SpecialTiles(SpecialTiles.star, 7, 7) // MiddleStar
    };


    public static void Reset() {
//clear the board.
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null;

        boardIsEmpty = true;
        scrabble.Player.getPlayer1().setScore(0);
        scrabble.Player.getPlayer2().setScore(0);
        gameOverTimer = 3;

    }
    //get the string from the planned tile to place
    public static void AddPiece(int xpixel, int ypixel, Tile currentTile){
        //checks if board is empty to prevent softlocks
        for (int row = 0; row <NUM_ROWS; row++)
            for (int col = 0; col<NUM_COLUMNS; col++)
                if (board[row][col] != null)
                {
                    boardIsEmpty = false;
                }
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        xpixel -= Window.getX(0);
        ypixel -= Window.getY(0);
        if (xpixel < 0 || ypixel < 0 || xpixel > Window.getWidth2() || ypixel > Window.getHeight2())
            return;
        int col = xpixel/xdelta;
        int row = ypixel/ydelta;

        if (board[row][col] != null)
            return;
        if (row > NUM_ROWS || col > NUM_COLUMNS)
            return;
        //will need if statements to see if the tile is made on any of the
        // bonustile spaces, and if so create a bonustile with those properties instead
        for (SpecialTiles tile: specialTiles)
            if (tile.getRow() == row && tile.getCol() == col)
                board[row][col] = new BonusTile(scrabble.Player.getCurrentPlayer().getCurrentTile().getLetter(),
                        tile.getWordMultiplier(), tile.getLetterMultiplier(), tile.skip, tile.addScore,
                        scrabble.Player.getCurrentPlayer().getCurrentTile().wordBonus);
        if (board[row][col] == null)
            board[row][col] = new Tile(scrabble.Player.getCurrentPlayer().getCurrentTile().getLetter(), scrabble.Player.getCurrentPlayer().getCurrentTile().wordBonus);

        board[row][col].setRowandCol(row,col);
        if (!legalMove(board[row][col]))
        {
            board[row][col] = null;
            return;
        }
        //add to the last turn array in order from left to right or top to bottom for easier reading
        if (currentTurn.size() != 0) {
            if (board[row][col].getRow() > currentTurn.get(0).getRow() || board[row][col].getCol() > currentTurn.get(0).getCol())
                currentTurn.add(board[row][col]);
            else
                currentTurn.add(0, board[row][col]);
        }
        else
            currentTurn.add(board[row][col]);
        //set direction
        if (currentTurn.size() == 2){
            if (currentTurn.get(0).getRow() == currentTurn.get(1).getRow())
            {
                horizontal = true;
                vertical = false;
                System.out.println("direction locked, horizontal");
            }
            else {
                vertical = true;
                horizontal = false;
                System.out.println("direction locked, vertical");
            }
        }

        //i can decide the enumeration based off of what value, row or col, is increasing when currentTurn.size == 1.
        scrabble.Player.getCurrentPlayer().removeCurrentTileFromDeck();
        scrabble.Player.getCurrentPlayer().setCurrentTile(null);
    }


    public static void ClearPieces(ArrayList<Tile> _turnArray, boolean challenge){
        //either the last turn or the current turn is passed, and challenge is to check which one. challenge = true
        //means that the last turn's tiles are removed and score.
        if (!challenge) {
            for (Tile ptr : _turnArray) {
                Player.getCurrentPlayer().addTiletoDeck(board[ptr.getRow()][ptr.getCol()]);
                board[ptr.getRow()][ptr.getCol()] = null;
            }
            currentTurn.clear();
            horizontal = false;
            vertical = false;
            //checks if board is empty to prevent softlocks
            for (int row = 0; row <NUM_ROWS; row++) {
                for (int col = 0; col < NUM_COLUMNS; col++) {
                    if (board[row][col] != null) {
                        boardIsEmpty = false;
                        return;
                    }
                }
            }
            boardIsEmpty = true;

        }
        else if (challenge) {
            //you can only challenge if no tiles have been placed
            if (currentTurn.size() == 0) {
                boolean realWord = true;
                int score = 0;
                for (int i = 0; i < listOfWordsPlayed.size(); i++) {
                    StringBuilder word = new StringBuilder();
                    for (int j = 0; j < listOfWordsPlayed.get(i).size(); j++) {
                        word.append(listOfWordsPlayed.get(i).get(j).getLetter());
                    }
                    if (!checkWordValidity(word)) {
                        realWord = false;
                        break;
                    }
                }
                if (!realWord) {
                    //unhighlights
                    for (int i = 0; i<listOfWordsPlayed.size();i++)
                        for (int j = 0; j<listOfWordsPlayed.get(i).size(); j++)
                        {
                            listOfWordsPlayed.get(i).get(j).highlight = false;
                        }
                    for (int i = 0 ; i<lastTurn.size();i++)
                        lastTurn.get(i).playedLastTurn = false;
                    for (Tile ptr : _turnArray) {
                        board[ptr.getRow()][ptr.getCol()] = null;
                    }
                    //subtracts points gained last turn.
                    //if player 1 is current player (player 2 played last turn)
                    if (scrabble.Player.getCurrentPlayer() == scrabble.Player.getPlayer1()) {
                        scrabble.Player.getPlayer2().setScore(scrabble.Player.getPlayer2().prevScore);
                        scrabble.Player.getPlayer2().skipTurn = true;
                        System.out.println("Player 2's new score: " + scrabble.Player.getPlayer2().getScore());
                    } else {
                        scrabble.Player.getPlayer1().setScore(scrabble.Player.getPlayer1().prevScore);
                        scrabble.Player.getPlayer1().skipTurn = true;
                        System.out.println("Player 1's new score: " + scrabble.Player.getPlayer1().getScore());
                    }


                    lastTurn.clear();
                    //idk if i'll need to set the superTile bonus used to false/true here.
                    for (int row = 0; row < NUM_ROWS; row++) {
                        for (int col = 0; col < NUM_COLUMNS; col++) {
                            if (board[row][col] != null) {
                                boardIsEmpty = false;
                                return;
                            }
                        }
                    }
                    boardIsEmpty = true;
                }
                else {
                    //skips turn if word turns out to be real
                    finishTurn();
                }
            }
        }



    }

    public static void determineWords() {
        //surely theres a better way of doing this but this is my masterpiece so ;/

        if (lastTurn.size() == 1)
        {
            vertical = true;
            horizontal = true;
        }
        if (vertical) {
            //produces the vertical word.
            int startRow = lastTurn.get(0).getRow();
            int col = lastTurn.get(0).getCol();
            for (int a = lastTurn.get(0).getRow(); a > -1; a--) {
                if (board[a][col] != null) {
                    startRow = a;
                } else
                    break;
            }
            //might be a problem with this arraylist creation
            listOfWordsPlayed.add(new ArrayList<scrabble.Tile>());
            for (int row = startRow; row < NUM_ROWS; row++) {
                if (board[row][col] != null) {
                    listOfWordsPlayed.get(0).add(board[row][col]);
                } else
                    break;
            }
            boolean verticalWordnotcreated = false;
            if (listOfWordsPlayed.get(0).size() == 1)
            {
                listOfWordsPlayed.get(0).clear();
                verticalWordnotcreated = true;
            }

            //System.out.println(listOfWordsPlayed.get(0).size());
            //produces horizontal words produced
            int iteration = 0;
            for (int d = 0; d<lastTurn.size(); d++)
            {
                int startCol = lastTurn.get(d).getCol();
                int row = lastTurn.get(d).getRow();
                for (int a = lastTurn.get(d).getCol(); a> -1; a--)
                {
                    if (board[row][a] != null)
                    {
                        startCol = a;
                    }
                    else
                        break;
                }
                ArrayList<scrabble.Tile> word = new ArrayList<Tile>();
                for (int k = startCol; k<NUM_COLUMNS; k++){
                    if (board[row][k] != null)
                        word.add(board[row][k]);
                    else
                        break;
                }
                if (word.size() >1) {
                    if (!verticalWordnotcreated) {
                        iteration++;
                        listOfWordsPlayed.add(new ArrayList<Tile>());
                    }
                    for (int i = 0; i < word.size(); i++) {
                        listOfWordsPlayed.get(iteration).add(word.get(i));
                    }
                }

            }

        }
        else if (horizontal)
        {
            //produces the horizontal word.
            int startCol = lastTurn.get(0).getCol();
            int row = lastTurn.get(0).getRow();
            for (int a = lastTurn.get(0).getCol(); a> -1; a--)
            {
                if (board[row][a] != null)
                {
                    startCol = board[row][a].getCol();
                }
                else
                    break;
            }
            //might be a problem with this arraylist creation
            listOfWordsPlayed.add(new ArrayList<scrabble.Tile>());
            for (int col = startCol; col<NUM_COLUMNS; col++)
            {
                if (board[row][col]!=null) {
                    listOfWordsPlayed.get(0).add(board[row][col]);
                }
                else
                    break;
            }
            int iteration = 0;
            //produces vertical words produced
            for (int d = 0; d<lastTurn.size(); d++)
            {
                int startRow = lastTurn.get(d).getRow();
                for (int a = lastTurn.get(d).getRow(); a> -1; a--)
                {
                    if (board[a][lastTurn.get(d).getCol()] != null)
                    {
                        startRow = board[a][lastTurn.get(d).getCol()].getRow();
                    }
                    else
                        break;
                }
                ArrayList<scrabble.Tile> word = new ArrayList<Tile>();
                for (int k = startRow; k<NUM_ROWS; k++){
                    if (board[k][lastTurn.get(d).getCol()] != null)
                        word.add(board[k][lastTurn.get(d).getCol()]);
                    else
                        break;
                }
                if (word.size() >1)
                {
                    iteration++;
                    listOfWordsPlayed.add(new ArrayList<Tile>());
                    for (int i = 0; i < word.size(); i++) {
                        listOfWordsPlayed.get(iteration).add(word.get(i));
                    }
                    System.out.println("");
                }

            }


        }
        for (int i =0 ; i<listOfWordsPlayed.size();i++)
        {
            int wordNumber = i+1;
            System.out.println("word " + wordNumber + " = ");
            for (int j = 0; j<listOfWordsPlayed.get(i).size();j++) {
                System.out.println(listOfWordsPlayed.get(i).get(j).getLetter());
            }
        }
        //to check that the correct amount of words have been made/found.
        System.out.println("");
        System.out.println("Words made this turn: " + listOfWordsPlayed.size());


    }

    public static int getWordValue(int index){
        int score = 0;
        int wordMultiplier = 1;
        //failsafe
        if (listOfWordsPlayed.get(index).size() > 1)
            for (int j = 0; j<listOfWordsPlayed.get(index).size(); j++)
            {
                if (listOfWordsPlayed.get(index).get(j).wordBonus == listOfWordsPlayed.get(index).size())
                    score+=listOfWordsPlayed.get(index).get(j).getScore()*3;
                else
                    score+=listOfWordsPlayed.get(index).get(j).getScore();
                if (listOfWordsPlayed.get(index).get(j) instanceof BonusTile) {
                    wordMultiplier *= ((BonusTile) listOfWordsPlayed.get(index).get(j)).wordMultiplier;
                }
            }
        System.out.println(score*wordMultiplier);
        return (score*wordMultiplier);

    }



    //we'll probably be setting a boolean here to true, and in the draw method of board
    //put a white screen up for neutral screen
    public static void finishTurn(){
        if (Scrabble.timerStarted)
        {
            gameOverTimer--;
            if (gameOverTimer == 0)
            {
                Scrabble.freeze = true;
                Scrabble.gameOver = true;
                return;
            }

        }
        if (boardIsEmpty&&currentTurn.size()==1) {
            if (scrabble.Player.getCurrentPlayer().time!=0) {
                System.out.println("1 letter words not allowed");
                return;
            }
            else
            {
                currentTurn.clear();
                board[7][7] = null;
            }
        }
        if (scrabble.Player.getCurrentPlayer().time == 0)
        {
            for (Tile ptr: currentTurn)
                board[ptr.getRow()][ptr.getCol()] = null;
            currentTurn.clear();
        }

        if (currentTurn.size() > 0 && scrabble.Player.getCurrentPlayer().getTimeSeconds()<10) {
            if (scrabble.Player.getCurrentPlayer() == scrabble.Player.getPlayer1())
                if (scrabble.Player.getPlayer2().time != 0)
                    scrabble.Player.getCurrentPlayer().time += 65;
            if (scrabble.Player.getCurrentPlayer() == scrabble.Player.getPlayer2())
                if (scrabble.Player.getPlayer1().time != 0)
                    scrabble.Player.getCurrentPlayer().time += 65;
        }
        for (int row = 0; row < NUM_ROWS; row++)
            for (int col = 0; col<NUM_COLUMNS; col++)
            {
                if (board[row][col] != null){
                    boardIsEmpty = false;
                }
            }
        //Clears the player's current turn but sets it as the last turn and unhighlights the last words played
        for (int i = 0 ; i<lastTurn.size();i++)
            lastTurn.get(i).playedLastTurn = false;

        for (int i = 0; i<listOfWordsPlayed.size();i++)
            for (int j = 0; j<listOfWordsPlayed.get(i).size(); j++)
            {
                listOfWordsPlayed.get(i).get(j).highlight = false;
            }
        lastTurn.clear();
        listOfWordsPlayed.clear();
        //this is a very bad way for this rn, will try to change later.
        for (scrabble.Tile ptr: currentTurn)
        {
            lastTurn.add(ptr);
        }
        currentTurn.clear();
        //checks if current player has played on a skipturn square
        for (int i = 0; i<lastTurn.size(); i++)
        {
            if (lastTurn.get(i) instanceof BonusTile)
                if (((BonusTile)lastTurn.get(i)).skip == true)
                {
                    if (scrabble.Player.getCurrentPlayer() == scrabble.Player.getPlayer1()) {
                        Player.getPlayer2().skipTurn = true;
                    }
                    else {
                        Player.getPlayer1().skipTurn = true;
                    }
                }
        }
        //freezes game + puts up neutral screen
        Scrabble.freeze = true;
        //sets trading to false;
        Player.getCurrentPlayer().trading=false;
        //sets previous score;
        scrabble.Player.getCurrentPlayer().prevScore = scrabble.Player.getCurrentPlayer().getScore();
        //bingoes (all 7 tiles used)
        if (lastTurn.size() == 7)
        {
            Player.getCurrentPlayer().addScore(15);
        }
        //determines the words played
        if (lastTurn.size() > 0) {
            determineWords();
            //highlights all words played last turn in yellow and all tiles played last turn in blue
            for (int i = 0; i < listOfWordsPlayed.size(); i++)
                for (int j = 0; j < listOfWordsPlayed.get(i).size(); j++) {
                    listOfWordsPlayed.get(i).get(j).highlight = true;
                }
            for (int i = 0; i < lastTurn.size(); i++)
                lastTurn.get(i).playedLastTurn = true;

            //highlight
            //add score based on words
            if (listOfWordsPlayed.size() != 0)
                for (int i = 0; i < listOfWordsPlayed.size(); i++)
                    Player.getCurrentPlayer().addScore(getWordValue(i));

            //sets all bonustiles to not be able to be used again
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLUMNS; j++) {
                    if (board[i][j] != null && board[i][j] instanceof BonusTile) {
                        ((BonusTile) board[i][j]).setUsed();
                    }
                }
            }
            //check if score is working
            if (scrabble.Player.getCurrentPlayer() == scrabble.Player.getPlayer1()) {
                System.out.println("");
                System.out.println("PLayer 1 score: " + scrabble.Player.getCurrentPlayer().getScore());
            } else {
                System.out.println("");
                System.out.println("PLayer 2 score: " + scrabble.Player.getCurrentPlayer().getScore());
            }
        }
        //switches player
        scrabble.Player.switchCurrentPlayer();
        //switch back if time is up, challenge suceeeded, or played on a skipturn tile.
        if (Player.getCurrentPlayer().skipTurn == true) {
            Player.getCurrentPlayer().skipTurn = false;
            scrabble.Player.switchCurrentPlayer();
        }
        if (Player.getCurrentPlayer().getTimeSeconds()==0 && !scrabble.Scrabble.gameOver)
        {
            scrabble.Player.switchCurrentPlayer();
            scrabble.Scrabble.freeze = false;
            if (scrabble.Player.getCurrentPlayer().getTimeSeconds() == 0) {
                scrabble.Scrabble.gameOver = true;
                scrabble.Scrabble.freeze = true;
                return;
            }
        }
        //makes sure that current tile is set to none at the start of each turn.
        scrabble.Player.getCurrentPlayer().setCurrentTile(null);
        //refreshes the new players tiles if they used any during their last turn
        scrabble.Player.getCurrentPlayer().refreshDeck();
        System.out.println(TileBag.getTileBagSize());
        //handles the rest of showing deck in system console, will be removed
        //scrabble.Player.getCurrentPlayer().getDeck();
        horizontal = false;
        vertical = false;
    }

    public static boolean  checkWordValidity(StringBuilder _word) {
        //this code works at home, not at school tho so idk
        String urlString = "https://api.dictionaryapi.dev/api/v2/entries/en/" + _word;
        try {
            // Create URL object and open connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set request headers to allow connection
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code
            int responseCode = connection.getResponseCode();
            // 200 == request made successfully
            if (responseCode != 200) {
                System.out.println("Request failed with status code: " + responseCode);
                System.out.println(_word + " is not valid.");
                ChallengeFail=false;
                return false;
            } else {
                System.out.println("Word " + _word + " is valid.");
                ChallengeFail=true;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            ChallengeFail=false;
            return false;
        }
    }
    public static boolean legalMove(scrabble.Tile tile) {
// Turn thing

        if (boardIsEmpty)
        {
            if (tile.getRow() ==7 && tile.getCol() == 7)
                return true;
            else
                return false;
        }

        // If not the first move, check adjacency to any existing tiles
        int row = tile.getRow();
        int col = tile.getCol();
        boolean isAdjacent = false;
        //check direction, if two tiles are placed we can find out the direction of legal moves
        //System.out.println("Checking tile at (" + row + ", " + col + ")");
        // Check top (row-1, col)
        //check with direction
        if (vertical) {
            if (row > 0 && Board.board[row - 1][col] != null)
            {
                if (currentTurn.get(0).getCol() == col) {
                    //System.out.println("case 1");
                    isAdjacent = true;
                }
            }
            // checks bottom
            if (row < NUM_ROWS - 1 && Board.board[row + 1][col] != null) {
                if (currentTurn.get(0).getCol() == col) {
                    //System.out.println("case 2");
                    isAdjacent = true;
                }
            }
        }
        if (horizontal) {
            //checks left
            if (col > 0 && Board.board[row][col - 1] != null) {
                if (currentTurn.get(0).getRow() == row) {
                    //System.out.println("case 3");
                    isAdjacent = true;
                }
            }
            //checks right
            if (col < NUM_COLUMNS - 1 && Board.board[row][col + 1] != null) {
                if (currentTurn.get(0).getRow() == row) {
                    //System.out.println("case 4");
                    isAdjacent = true;
                }
            }
        }
        //check if direction hasn't been decided yet.
        if (!horizontal && !vertical) {
            if (currentTurn.size() == 0) {
                if (row > 0 && Board.board[row - 1][col] != null) {
                    //System.out.println("case 5");
                    isAdjacent = true;
                }
                // checks bottom
                if (row < NUM_ROWS - 1 && Board.board[row + 1][col] != null) {
                    //System.out.println("case 6");
                    isAdjacent = true;
                }
                //checks left
                if (col > 0 && Board.board[row][col - 1] != null) {
                    //System.out.println("case 7");
                    isAdjacent = true;
                }
                //checks right
                if (col < NUM_COLUMNS - 1 && Board.board[row][col + 1] != null) {
                    //System.out.println("case 8");
                    isAdjacent = true;
                }
            }
            else if (currentTurn.size() == 1)
            {
                //checks left
                if (col > 0 && Board.board[row][col - 1] != null) {
                    if (currentTurn.get(0).getRow() == row) {
                        //System.out.println("case 9");
                        isAdjacent = true;
                    }
                }
                //checks right
                if (col < NUM_COLUMNS - 1 && Board.board[row][col + 1] != null) {
                    if (currentTurn.get(0).getRow() == row) {
                        //System.out.println("case 10");
                        isAdjacent = true;
                    }
                }
                if (row > 0 && Board.board[row - 1][col] != null) {
                    if (currentTurn.get(0).getCol() == col) {
                        //System.out.println("case 11");
                        isAdjacent = true;
                    }
                }
                // checks bottom
                if (row < NUM_ROWS - 1 && Board.board[row + 1][col] != null) {
                    if (currentTurn.get(0).getCol() == col) {
                        //System.out.println("case 12");
                        isAdjacent = true;
                    }
                }

            }
        }

        if (!isAdjacent) {
            return false; // Invalid move if it's not adjacent to any existing tile
        }
        //I literally have no idea why this is working but it is so ????
        return true;
    }



    public static void Draw(Graphics2D g) {
//draw grid
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        Color boardColor = new Color(201,195,169,255);
        g.setColor(boardColor);
        g.fillRect(Window.getX(0), Window.getY(0), Window.getWidth2(), Window.getHeight2());


        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(Window.getX(0),Window.getY(zi*ydelta),
                    Window.getX(Window.getWidth2()),Window.getY(zi*ydelta));
        }
        for (SpecialTiles tile:specialTiles)
            tile.draw(g,xdelta,ydelta);


        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(Window.getX(zi*xdelta),Window.getY(0),
                    Window.getX(zi*xdelta),Window.getY(Window.getHeight2()));
        }
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
            {
                if (board[zrow][zcol] != null)
                    board[zrow][zcol].draw(g, zrow, zcol,xdelta, ydelta);
            }
        }





    }
}


