package scrabble;
import java.util.ArrayList;

public class TileBag {
    private static ArrayList<Tile> tileBag = new ArrayList<Tile>();


    TileBag(){

    }
    public static void reset(){
        // 1 point tiles
        tileBag.clear();
        //adding tiles to the tilebag according to distribution
        // 1 point tiles
        for (int i = 0; i < 12; i++) {
            tileBag.add(new Tile('e'));
        }
        for (int i = 0; i < 9; i++) {
            tileBag.add(new Tile('a'));
        }
        for (int i = 0; i < 9; i++) {
            tileBag.add(new Tile('i'));
        }
        for (int i = 0; i < 8; i++) {
            tileBag.add(new Tile('o'));
        }
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('n'));
        }
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('r'));
        }
        for (int i = 0; i < 6; i++) {
            tileBag.add(new Tile('t'));
        }
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('l'));
        }
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('s'));
        }
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('u'));
        }

// 2 point tiles
        for (int i = 0; i < 4; i++) {
            tileBag.add(new Tile('d'));
        }
        for (int i = 0; i < 3; i++) {
            tileBag.add(new Tile('g'));
        }

// 3 point tiles
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('b'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('c'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('m'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('p'));
        }

// 4 point tiles
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('f'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('h'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('v'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('w'));
        }
        for (int i = 0; i < 2; i++) {
            tileBag.add(new Tile('y'));
        }

// 5 point tile
        for (int i = 0; i < 1; i++) {
            tileBag.add(new Tile('k'));
        }

// 8 point tiles
        for (int i = 0; i < 1; i++) {
            tileBag.add(new Tile('j'));
        }
        for (int i = 0; i < 1; i++) {
            tileBag.add(new Tile('x'));
        }

// 10 point tiles
        for (int i = 0; i < 1; i++) {
            tileBag.add(new Tile('z'));
        }
        for (int i = 0; i < 1; i++) {
            tileBag.add(new Tile('q'));
        }

        //this was to check if all the tiles are being distributed correctly.
        /*
        for (int i = 0; i < tileBag.size(); i++) {
            System.out.println(Character.toUpperCase(tileBag.get(i).getLetter()));
        }

         */

        System.out.println(tileBag.size());

    }


    //mutator
    public static Tile pullTile(){
        int random = (int)(Math.random()* tileBag.size());
        Tile returnTile = tileBag.get(random);
        tileBag.remove(random);
        //need some sort of failsafe if no tile is pulled
        return(returnTile);
    }

    public static void addTiletoTileBag(Tile tile){
        tileBag.add(tile);
    }

    public static int getTileBagSize(){
        return(TileBag.tileBag.size());
    }

}