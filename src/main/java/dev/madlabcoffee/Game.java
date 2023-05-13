package dev.madlabcoffee;

import dev.madlabcoffee.drawables.Coconut;
import dev.madlabcoffee.drawables.Drawable;
import dev.madlabcoffee.drawables.Player;
import dev.madlabcoffee.services.AudioService;
import dev.madlabcoffee.services.TextService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    // Controls the delay between each tick in ms
    private final int DELAY = 100;
    public static final int TILE_SIZE = 63;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;
    private Image backgroundImg;
    private final Player player;
    private final Coconut coconut;
    private final AudioService audioService;
    private final TextService textService;
    private static boolean PAUSED = false;
    private final int SECONDS = 3;
    private int countdownCount = SECONDS * 1000;

    public Game() {
        // set the frame size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));

        initBackground();

        // initialize the game state
        player = new Player(
                new Point(10, 10),
                "assets/images/Killer-koala.png"
        );
        coconut = new Coconut(
                new Point(0, 0),
                "assets/images/coconut.png"
        );
        audioService = new AudioService();
        textService = new TextService();

        // this timer will call the actionPerformed() method every DELAY ms
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }  // End of the 'constructor'

    private void initBackground() {
        try {
            backgroundImg = ImageIO.read(new File("assets/images/jungle-palm-trees.png"));
        } catch (IOException exc) {
            System.out.println("Failed to load background image: " + exc.getMessage());
            // set the game frame background
            setBackground(new Color(232, 232, 232));
        }
    }  // End of the 'initBackground' method

    /**
     * This method is called by the timer every DELAY ms.
     * use this space to update the state of the game or animation
     * before the graphics are redrawn.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!isInCountdown()) {
            if (!isPlayerHit() && !PAUSED) {
                // prevent the player from disappearing off the board
                player.tick();
                coconut.tick();

                if (hasCollision(player, coconut)) {
                    player.decrementLives();
                    audioService.playSound();
                }
            } else {
                PAUSED = true;
            }
        }
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }  // End of the 'actionPerformed' method

    /**
     * When calling g.drawImage() we can use 'this' for the ImageObserver
     * because Component implements the ImageObserver interface, and JPanel
     * extends from Component.  So 'this' Frame instance, as a Component, can
     * react to imageUpdate() events triggered by g.drawImage()
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImg != null) {
            g.drawImage(
                    backgroundImg,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    null
            );
        } else {
            System.exit(-1);
        }

        textService.drawScore(g, player.getScore());
        textService.drawLives(g, player.getLives());

        player.draw(g, this);
        // Draw after the player so that it renders on top
        coconut.draw(g, this);

        if (PAUSED) {
            textService.drawPaused(g);
        }
        if (isInCountdown()) {
            textService.drawCountdown(g, (countdownCount / 1000) + 1);
            countdownCount -= DELAY;
        }

        // this smooths out the animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }  // End of the 'paintComponent' method

    boolean isInCountdown() {
        return countdownCount > 0;
    }  // End of the 'isInCountdown' method

    @Override
    public void keyTyped(KeyEvent e) {
        // noop
    }  // End of the 'keyTyped' method

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isInCountdown()) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.out.println("I'll be back");
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                PAUSED = !PAUSED;
                if (audioService.hasMusic()) {
                    if (PAUSED) {
                        audioService.pauseMusic();
                    } else {
                        audioService.resumeMusic();
                    }
                }
            } else {
                player.keyPressed(e);
            }
        }
    }  // End of the 'keyPressed' method

    @Override
    public void keyReleased(KeyEvent e) {
        // noop
    }  // End of the 'keyReleased' method

    private boolean isPlayerHit() {
        return false;
    }  // End of the 'isPlayerHit' method

    public static boolean isPaused() {
        return PAUSED;
    }  // End of the 'isPaused' method

    private boolean hasCollision(Drawable player, Drawable drawable) {
        boolean collision = false;

        double playerX = player.getPosition().getX();
        double playerY = player.getPosition().getY();
        double drawableX = drawable.getPosition().getX();
        double drawableY = drawable.getPosition().getY();

        if (playerX == drawableX || (playerX + 1 == drawableX)) {
            if (drawableY >= playerY) {
                collision = true;
            }
        }

        return collision;
    }  // End of the 'hasCollision' method
}  // End of the 'Frame' class

// END OF FILE
