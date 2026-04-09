package scrabble;


import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class Scrabble extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    static public boolean freeze;
    static public boolean gameOver;
    //added feature to see board after game has ended
    static public boolean endGame;

    static Image freezeImage;
    static Image Player1Wins;
    static Image Player2Wins;
    static Image TieImage;
    static Image LostChallenge;

    static boolean timerStarted;


    public static void main(String[] args) {
        Scrabble frame = new Scrabble();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public Scrabble() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {


                if (e.BUTTON1 == e.getButton() ) {
                    //'\u0000' means null for chars
                    if (!freeze) {
                        if (scrabble.Player.getCurrentPlayer().getCurrentTile() != null && !endGame)
                            scrabble.Board.AddPiece(e.getX(), e.getY(), scrabble.Player.getCurrentPlayer().getCurrentTile());
                    }
                    if(freeze) {
                        if (gameOver) {
                            endGame = true;
                            return;
                        }
                        freeze = false;
                        scrabble.Board.ChallengeFail =false;
                    }

                }


                if (e.BUTTON3 == e.getButton()) {
                    //reclaim tiles method
                    if (!freeze) {
                        Board.ClearPieces(Board.currentTurn, false);
                        System.out.println("reclaim tiles code ran");
                    }

                }
                repaint();
            }
        });




        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {


                repaint();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {


                repaint();
            }
        });


        addKeyListener(new KeyAdapter() {

            //F = change turn
            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                }else if (e.VK_1 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 1)
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(1));

                }
                else if (e.VK_2 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 2)
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(2));

                }
                else if (e.VK_3 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 3)
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(3));

                }
                else if (e.VK_4 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 4)
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(4));

                }
                else if (e.VK_5 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 5)
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(5));

                }
                else if (e.VK_6 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 6) {
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(6));

                    }

                }
                else if (e.VK_7 == e.getKeyCode()){
                    if (scrabble.Player.getCurrentPlayer().getDeckSize() >= 7) {
                        scrabble.Player.getCurrentPlayer().setCurrentTile(scrabble.Player.getCurrentPlayer().getDeckNumber(7));
                    }

                }
                else if (e.VK_F == e.getKeyCode()){
                    if (!freeze)
                        scrabble.Board.finishTurn();
                }
                else if (e.VK_C == e.getKeyCode()){
                    //challenge
                    if (!freeze) {
                        if (Board.lastTurn  .size() != 0) {
                            Board.ClearPieces(Board.lastTurn, true);
                        }
                    }
                }
                else if (e.VK_R==e.getKeyCode())
                {
                    if (!freeze) {
                        if (Board.currentTurn.size() == 0) {
                            Player.getCurrentPlayer().setCurrentTile(null);
                            Player.getCurrentPlayer().trading = true;
                        }
                    }
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
    ////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
    ////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
    ////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

        }
//fill background


        Color backgroundColor = new Color(177,134,102);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Window.xsize, Window.ysize);


        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);


        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }




        Board.Draw(g);
        Player.getCurrentPlayer().Draw(g);
        //neutral screen or gameOver
        if (freeze) {
            if (!endGame) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
                g.setColor(Color.black);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                if (!gameOver) {
                    g.drawImage(freezeImage, 0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT, this);
                }
                else{
                    if (Player.getPlayer1().getScore() > Player.getPlayer2().getScore()) {
                        g.drawImage(Player1Wins, 0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT, this);
                    } else if (Player.getPlayer1().getScore() < Player.getPlayer2().getScore()) {
                        g.drawImage(Player2Wins, 0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT, this);
                    } else {
                        g.drawImage(TieImage, 0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT, this);
                    }

                }
            }
        }

        if(scrabble.Board.ChallengeFail){
            g.drawImage(LostChallenge, 0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT, this);
        }
        //Timers
        g.setColor(Color.WHITE);
        g.setFont (new Font ("Lucida Calligraphy",Font.PLAIN, 20));
        g.drawString("Player 1: " + Player.getPlayer1().getTimeSeconds(), 40, 60);
        g.drawString("Player 2: " + Player.getPlayer2().getTimeSeconds(), scrabble.Window.getWidth2()-120, 60);
        //displays current players turn
        g.setColor(Color.WHITE);
        if (Player.getCurrentPlayer() == Player.getPlayer1()) {
            g.setFont (new Font ("Lucida Calligraphy",Font.PLAIN, 20));
            g.drawString("Player 1's Turn", scrabble.Window.getWidth2()/2-55, 60);
        }
        else{
            g.setFont (new Font ("Lucida Calligraphy",Font.PLAIN, 20));
            g.drawString("Player 2's Turn", scrabble.Window.getWidth2()/2-55, 60);
        }
        g.setFont (new Font ("Lucida Calligraphy",Font.PLAIN, 20));
        g.drawString("Score " + Player.getPlayer1().getScore(), 40,90);
        g.drawString("Score " + Player.getPlayer2().getScore(), scrabble.Window.getWidth2()-120,90);
        if(scrabble.TileBag.getTileBagSize()==0)
            g.drawString("Turns Left: " + scrabble.Board.gameOverTimer, scrabble.Window.getWidth2()/2-55,90);




        gOld.drawImage(image, 0, 0, null);
    }

    ////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .1;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////
    public void reset() {
        scrabble.TileBag.reset();
        scrabble.Player.Reset();
        Board.Reset();
        freeze = true;
        gameOver = false;
        endGame = false;

    }
    /////////////////////////////////////////////////////////////////////////
    public void animate() {


        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }

            freezeImage = Toolkit.getDefaultToolkit().getImage("./NeutralScreen.jpg");
            Player1Wins = Toolkit.getDefaultToolkit().getImage("./Player1Wins.jpg");
            Player2Wins = Toolkit.getDefaultToolkit().getImage("./Player2Wins.jpg");
            TieImage = Toolkit.getDefaultToolkit().getImage("./TIE.jpg");
            LostChallenge = Toolkit.getDefaultToolkit().getImage("./LostChallenge.jpg");

            reset();


        }
        if (!freeze)
            scrabble.Player.timeDecrease();





    }


    ////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }


}